package com.zq.admin;

import com.alibaba.fastjson.JSONObject;
import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.HttpResult;
import com.zq.admin.dao.HolidayInfoDao;
import com.zq.admin.model.HolidayInfoModel;
import com.zq.admin.service.HolidayInfoService;
import com.zq.admin.utils.SendMsmUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class AdminApplicationTests {
    @Autowired
    private HolidayInfoDao holidayInfoDao;

    @Autowired
    private HolidayInfoService holidayInfoService;

    @Test
    public void test() {
        ArrayList<HolidayInfoModel> list = new ArrayList<>();
        HolidayInfoModel model1 = new HolidayInfoModel();
        model1.setHolidayDate("2020-10-31");
        model1.setHolidayType("星期六");
        model1.setYear(2020);
        list.add(model1);

        HolidayInfoModel model2 = new HolidayInfoModel();
        model2.setHolidayDate("2020-11-01");
        model2.setHolidayType("星期日");
        model2.setYear(2020);
        list.add(model2);

        HolidayInfoModel model3 = new HolidayInfoModel();
        model3.setHolidayDate("2020-11-07");
        model3.setHolidayType("星期六");
        model3.setYear(2020);
        list.add(model3);

        HolidayInfoModel model4 = new HolidayInfoModel();
        model4.setHolidayDate("2020-11-08");
        model4.setHolidayType("星期日");
        model4.setYear(2020);
        list.add(model4);
//        holidayInfoDao.insert(list);
    }

    public HttpResult get(String url) {
        HTTP http = HTTP.builder().build();
        return http.sync(url).get();
    }

    @Test
    public void find() throws InterruptedException {
        List<String> urlList = new ArrayList<>();
        urlList.add("http://jtyst.shanxi.gov.cn/qgyw/177974.jhtml");
        urlList.add("http://jtyst.shanxi.gov.cn/qgyw/177973.jhtml");
        for (int i = 0; i < urlList.size(); i++) {
            HttpResult httpResult = get(urlList.get(i));
            if (httpResult.isSuccessful()) {
                System.out.println("访问:" + urlList.get(i) + "  成功!!!");
                TimeUnit.MINUTES.sleep(1);
            }
            if (i == urlList.size()) {
                i = 0;
            }
        }


    }

    @Test
    public void equals() {
        String json= "{\n" +
                "  \"name\": \"vuepress-start\",\n" +
                "  \"version\": \"1.0.0\",\n" +
                "  \"description\": \"vuepress描述文件\",\n" +
                "  \"main\": \"index.js\",\n" +
                "  \"author\": \"zhouq\",\n" +
                "  \"license\": \"MIT\"\n" +
                "  \"scripts\": {\n" +
                "    \"docs:dev\": \"vuepress dev docs\",\n" +
                "    \"docs:build\": \"vuepress build docs\"\n" +
                "  },\n" +
                "  \"devDependencies\": {\n" +
                "    \"vuepress\": \"^1.7.1\"\n" +
                "  }\n" +
                "}\n";
        try {
            Map<String,String> parse = (Map<String,String>)JSONObject.parse(json);
            String s = parse.get("name");
            System.out.println(s);
        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("异常类型------>"+e);
//            System.out.println("异常信息------>"+e.getMessage());
              System.out.println("异常跟踪栈------>"+getExceptionStrintStackTrace(e));

        }

    }

    private String getExceptionStrintStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
