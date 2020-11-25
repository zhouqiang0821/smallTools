package com.zq.smallTool.service;

import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.HttpResult;
import com.zq.smallTool.dao.BaseRegionDetailDao;
import com.zq.smallTool.model.BaseRegionDetailModel;
import com.zq.smallTool.property.SpiderPropertyModel;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @program: admin
 * @description: 省市区基本信息
 * @author: zhouQ
 * @create: 2020-11-17 09:46
 **/
@Service
public class BaseRegionDetailService {

    @Autowired
    private SpiderPropertyModel spiderPropertyModel;

    @Autowired
    private BaseRegionDetailDao baseRegionDetailDao;


    private static HTTP http = null;

    private static List<Map<String, Object>> proxyList = null;

    /***
     * 初始化 http
     * @Param isChange 是否发需要切换代理Ip
     * @return
     */
    private void initHttpClint() {
        http = HTTP.builder()
                .config(new HTTP.OkConfig() {
                    @Override
                    public void config(OkHttpClient.Builder builder) {
                        // 配置连接池 最小10个连接（不配置默认为 5）
                        builder.connectionPool(new ConnectionPool(10, 5, TimeUnit.MINUTES));
                        // 配置连接超时时间（默认10秒）
                        builder.connectTimeout(1000, TimeUnit.SECONDS);
                        // 配置 WebSocket 心跳间隔（默认没有心跳）
                        builder.pingInterval(60, TimeUnit.SECONDS);
                        // 配置拦截器
                        builder.addInterceptor((Interceptor.Chain chain) -> {
                            Request request = chain.request();
                            // 必须同步返回，拦截器内无法执行异步操作
                            return chain.proceed(request);
                        });
                        // 其它配置: CookieJar、SSL、缓存、代理、事件监听...
                        // 是否需要开启动态代理Ip
                        if (spiderPropertyModel.getProxyEnable()) {
                            // 从ipLocation 中获取 ipList
                            getProxy(spiderPropertyModel.getIpLocation());
                            Map<String, Object> proxy = proxyList.get(new Random().nextInt(proxyList.size()));
                            String ip = proxy.get("ip").toString();
                            System.out.println("--------代理Ip------------" + ip);
                            int port = Integer.parseInt(proxy.get("port").toString());
                            System.out.println("----------端口------------" + port);
                            builder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port)));
                        }
                    }
                })
                .baseUrl("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/" + spiderPropertyModel.getSpiderYear() + "/")
                .build();
    }

    /***
     * 发送http请求
     * @param childHref 访问得子页面
     * @return
     */
    private String toGet(String childHref) {
        BufferedReader in = null;
        String ret = "";
        HttpResult httpResult = null;// GET请求
        try {
            httpResult = http.sync(childHref)
                    .get();
            if (httpResult.isSuccessful()) {
                try {
                    in = new BufferedReader(new InputStreamReader(httpResult.getBody().toByteStream(), "GB2312"));
                    String line = "";
                    while ((line = in.readLine()) != null) {
                        ret += line;
                    }
                    if (StringUtils.isBlank(ret)) {
                        System.out.println("-----获取到得ret == '' ----");
                        initHttpClint();
                        toGet(childHref);
                    }
                } catch (IOException e) {
                    in.close();
                }
            }
        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("-----连接超时,请求切换代理IP----");
            initHttpClint();
            toGet(childHref);
        }
        return ret;
    }

    /***
     * 获取省信息
     */
    public void getProvince() throws InterruptedException {
        // 初始化 httpClint
        initHttpClint();
        // 从index.html 中获取所有 省、直辖市、自治区 数据
        Document doc = Jsoup.parse(toGet("/index.html"));
        Elements provincetrEles = doc.getElementsByClass("provincetr");
        // 查找数据库中保存的最后一个省的regionCode
        List<String> provinceCodeList = baseRegionDetailDao.getAllProvince();
        for (Element e :
                provincetrEles) {
            Elements tdEles = e.getElementsByTag("td");
            for (Element td :
                    tdEles) {
                Elements a = td.select("a");
                if (!a.isEmpty()) {
                    // 解析数据 获取 指向市的href
                    String href = td.select("a").attr("href");
                    // 获取省份名称
                    String name = td.select("a").text().trim();
                    // 获取省份代码
                    String areaCode = "";
                    areaCode = href.substring(0, href.indexOf(".")) + "00";
                    String last2code = getLast2code(href, 0, 2);

                    // 判断 数据库中 没有省信息 则证明是第一次爬取
                    if (provinceCodeList == null || !provinceCodeList.contains(areaCode)) {
                        System.out.println("--------------省-------------");
                        System.out.println("省: " + name + " 代码:" + areaCode + " href:" + href);
                        // 保存省信息数据
                        ArrayList<BaseRegionDetailModel> list = new ArrayList<>();
                        list.add(createModel(name, areaCode, "0", "0"));
                        baseRegionDetailDao.insert(list);
                        getCity(toGet(href), areaCode, last2code);
                        provinceCodeList.add(areaCode);
                    } else {
                        // 则证明 最后spider 的是个省 不进行保存 直接开始进入 此省的市判断
                        getCity(toGet(href), areaCode, last2code);
                    }
                    System.out.println("--------------省-------------");
                }

            }
        }
    }

    /***
     * 获取城市
     * @param cityHtmlStr http 请求回来的市body
     * @param pid 父Id
     * @param provinceCode 省ID
     */
    private void getCity(String cityHtmlStr, String pid, String provinceCode) throws InterruptedException {
        List<BaseRegionDetailModel> list = null;
        Document doc = Jsoup.parse(cityHtmlStr);
        Elements citytrEles = doc.getElementsByClass("citytr");
        for (Element e :
                citytrEles) {
            // 每个市及市下边的数据 保存一下
            list = new ArrayList<>();
            Elements tdEles = e.getElementsByTag("td");
            Element td = tdEles.last();
            Elements a = td.select("a");
            if (!a.isEmpty()) {
                String href = td.select("a").attr("href");
                String name = td.select("a").text().trim();
                String areaCode = "";
                // href = 14/1401.html
                areaCode = href.substring(href.indexOf("/") + 1, href.indexOf("."));
                System.out.println("市: " + name + " 代码:" + areaCode + " href:" + href);
                // 判断此城市是否已经存在数据库中
                List<BaseRegionDetailModel> detailModels = baseRegionDetailDao.find(areaCode, null, null);
                if (detailModels == null || detailModels.size() == 0) {
                    // 获取 code 末尾两码
                    list.add(createModel(name, areaCode, pid, "1"));
                    // 获取市下边的 县、区 信息
                    getCounty(toGet(href), list, areaCode, provinceCode);
                    if (list.size() > 0) {
                        baseRegionDetailDao.insert(list);
                        if (spiderPropertyModel.getIsCitySleep()) {
                            System.out.println("-----------市级睡眠 " + spiderPropertyModel.getCitySleepTime() + " s 开始---------");
                            TimeUnit.SECONDS.sleep(spiderPropertyModel.getCitySleepTime());
                            System.out.println("-----------市级睡眠结束----------");
                        }
                    }
                }
            }
        }
    }

    /***
     * 获取 区县 代码
     * @param cityHtmlStr
     * @param list
     * @param pid
     * @param provinceCode
     */
    private void getCounty(String cityHtmlStr, List<BaseRegionDetailModel> list, String pid, String provinceCode) throws InterruptedException {
        Document doc = Jsoup.parse(cityHtmlStr);
        Elements countytrEles = doc.getElementsByClass("countytr");
        for (Element tr :
                countytrEles) {
            // 如果没有获取到 a标签
            Elements tdEles = tr.getElementsByTag("td");
            if (tdEles.select("a").isEmpty()) {
                String name = tdEles.last().text();
                String areaCode = tdEles.first().text().substring(0, 6);
                // // href = 01/140105.html 区往街道走 要获取上一级的14/01/140105
                System.out.println("区、县: " + name + " 区、县:" + areaCode);
                list.add(createModel(name, areaCode, pid, "2"));
            } else {
                Element td = tdEles.last();
                Elements a = td.select("a");
                if (!a.isEmpty()) {
                    String href = td.select("a").attr("href");
                    String name = td.select("a").text().trim();
                    String areaCode = "";
                    areaCode = href.substring(href.indexOf("/") + 1, href.indexOf("."));
                    System.out.println("区、县: " + name + " 区、县:" + areaCode + " href:" + href);
                    list.add(createModel(name, areaCode, pid, "2"));
                    String cityCode = getLast2code(href, 0, 2);
                    href = "/" + provinceCode + "/" + href;
                    //  是否获取乡镇、街道信息
                    if (spiderPropertyModel.getGetTowntr()) {
                        getTowntr(toGet(href), list, areaCode, provinceCode, cityCode);
                    }
                }
            }
            if (spiderPropertyModel.getIsCountySleep()) {
                System.out.println("-----------区、县 级睡眠 " + spiderPropertyModel.getCountySleepTime() + " s 开始---------");
                TimeUnit.SECONDS.sleep(spiderPropertyModel.getCountySleepTime());
                System.out.println("-----------区、县 市级睡眠结束----------");

            }
        }
    }

    /***
     * 获取 乡镇 街道办 信息
     */
    private void getTowntr(String towntrHtmlStr, List<BaseRegionDetailModel> list, String pid, String provinceCode, String cityCode) {
        Document doc = Jsoup.parse(towntrHtmlStr);
        Elements countytrEles = doc.getElementsByClass("towntr");
        for (Element tr :
                countytrEles) {
            // 如果没有获取到 a标签
            Elements tdEles = tr.getElementsByTag("td");
            Element td = tdEles.last();
            Elements a = td.select("a");
            if (!a.isEmpty()) {
                String href = td.select("a").attr("href");
                String name = td.select("a").text().trim();
                String areaCode = "";
                areaCode = href.substring(href.indexOf("/") + 1, href.indexOf("."));
                //System.out.println("街道办: " + name + " 代码:" + areaCode + " href:" + href);
                list.add(createModel(name, areaCode, pid, "3"));
                href = "/" + provinceCode + "/" + cityCode + "/" + href;
                //  是否获取村、社区信息
                if (spiderPropertyModel.getGetVillagetr()) {
                    getVillagetr(toGet(href), list, areaCode);
                }
            }
        }
    }

    /***
     * 获取 村 社区 居委会信息
     * @param villagetrHtmlStr
     * @param list
     * @param pid
     */
    private void getVillagetr(String villagetrHtmlStr, List<BaseRegionDetailModel> list, String pid) {
        Document doc = Jsoup.parse(villagetrHtmlStr);
        Elements countytrEles = doc.getElementsByClass("villagetr");
        for (Element tr :
                countytrEles) {
            Elements tdEles = tr.getElementsByTag("td");
            if (tdEles != null && tdEles.size() > 0) {
                Element first = tdEles.get(0);
                Element last = tdEles.get(2);
                // 解析 获取 regionName,regionCode
                list.add(createModel(last.text().trim(), first.text().trim(), pid, tdEles.get(1).text().trim()));
            }
        }
    }


    /***
     * 取herf得前两位
     * @param regionCode
     * @return
     */
    private String getLast2code(String regionCode, int startIndex, int endIndex) {
        return regionCode.substring(startIndex, endIndex);
    }

    /***
     * 获取 id代理 库
     */
    private void getProxy(String ipLocation) {
        // 1，建立联系
        File file = new File(ipLocation);
        //2，选择流
        InputStream is = null;
        try {
            is = new FileInputStream(file);

            BufferedReader in2 = new BufferedReader(new InputStreamReader(is));
            // 设置缓冲区域，就像搬家一样，定义一个车子来搬家
            byte[] data = new byte[1024];
            // 记录实际读取的长度，就像搬家一样，不是说每个都是刚好一车就装满了的
            proxyList = new ArrayList<>();
            String y = "";
            // read 将字节流读取到 定义 的 data 中，len 记录每次读取的长度，当 is 的数据读完之后len的值则为-1
            while ((y = in2.readLine()) != null) {
                HashMap<String, Object> map = new HashMap<>();
                String[] split = y.split(":");
                map.put("ip", split[0]);
                map.put("port", split[1]);
                proxyList.add(map);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    // 4,释放资源
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


    /***
     * 创建 regionModel
     * @param regionName
     * @param regionCode
     * @param pid
     * @param type
     * @return
     */
    private BaseRegionDetailModel createModel(String regionName, String regionCode, String pid, String type) {
        BaseRegionDetailModel model = new BaseRegionDetailModel();
        model.setRegionName(regionName);
        model.setRegionCode(regionCode);
        model.setPid(pid);
        model.setType(type);
        return model;
    }
}
