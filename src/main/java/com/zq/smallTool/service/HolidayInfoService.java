package com.zq.smallTool.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.HttpResult;
import com.zq.smallTool.dao.HolidayInfoDao;
import com.zq.smallTool.model.HolidayInfoModel;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: admin
 * @description: 节假日service
 * @author: zhouQ
 * @create: 2020-11-19 14:19
 **/
@Service
public class HolidayInfoService {

    @Autowired
    private HolidayInfoDao holidayInfoDao;

    /***
     * 将查询到的 休息日批量插入到数据库中
     * @throws ParseException
     */
    public Integer insertDb() throws ParseException {
        Calendar instance = Calendar.getInstance();
        // 获取当前的年份
        int year = instance.get(Calendar.YEAR);
        // 数据库查找是否已经有今年的节假日信息
        List<HolidayInfoModel> list = holidayInfoDao.find(year);
        int num = 0;
        // 没有查到本年的数据 才进行添加
        if (list.isEmpty()) {
            // 全年节假日星期六集合
            Set<HolidayInfoModel> holidayDateSet = new TreeSet<HolidayInfoModel>();
            // 节假日调休加班 信息
            Set<HolidayInfoModel> holidayWorkSet = new TreeSet<HolidayInfoModel>();
            // 从网上获取 全年的节假日信息
            getHolidayToDb(holidayDateSet, holidayWorkSet, year);
            // 获取全年的周六日
            getSatAndSun(holidayDateSet, year);
            // 移除节假日 调休信息
            holidayDateSet.removeAll(holidayWorkSet);
            num = holidayDateSet.size();
            // 批处理插入数据库
            holidayInfoDao.insert(holidayDateSet);
        }
        return num;
    }

    /***
     * 获取今年的节假日信息 status 1.放假 2.补班 / 去掉相同的 周六日 并且减去需要补班的周六日
     * @param holidayDateSet 节假日 休息 set
     * @param holidayWorkSet 因节假日需求调休的日期set
     * @param year 当前年
     */
    private void getHolidayToDb(Set<HolidayInfoModel> holidayDateSet, Set<HolidayInfoModel> holidayWorkSet, int year) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 向百度APP发送请求 获取节假日List  包括调休
        String url = "http://opendata.baidu.com/api.php?query=" + year + "&resource_id=6018&format=json";
        // 发送http请求获取数据
        HTTP http = HTTP.builder().build();
        HttpResult httpResult = http.sync(url).get();
        if (httpResult.isSuccessful()) {
            // 获取数据
            String originJson = httpResult.getBody().toString();
            // 解析获取到的数
            JSONObject dayObj = JSONObject.parseObject(originJson);
            String data = dayObj.getString("data");
            JSONArray dataArray = JSONObject.parseArray(data);
            if (dataArray != null && dataArray.size() > 0) {
                String data0 = dataArray.getString(0);

                JSONObject data0Obj = JSONObject.parseObject(data0);

                String holidayArrStr = data0Obj.getString("holiday");

                JSONArray holidayArrObj = JSONObject.parseArray(holidayArrStr);

                if (holidayArrObj != null && holidayArrObj.size() > 0) {
                    for (int i = 0; i < holidayArrObj.size(); i++) {
                        String holidayDescJson = holidayArrObj.getString(i);
                        JSONObject holidayDescObj = JSONObject.parseObject(holidayDescJson);
                        // 获取节假日名称
                        String holidayType = holidayDescObj.getString("name");
                        // 获取 存放 节假日日期的list
                        String holidayList = holidayDescObj.getString("list");
                        JSONArray holidayListJsonArr = JSONObject.parseArray(holidayList);
                        if (holidayListJsonArr != null && holidayListJsonArr.size() > 0) {
                            for (int j = 0; j < holidayListJsonArr.size(); j++) {
                                String holidayJson = holidayListJsonArr.getString(j);
                                JSONObject holidayObj = JSONObject.parseObject(holidayJson);
                                String date = holidayObj.getString("date");
                                // 从接口中获取的日期格式是 2020-1-1 从 calendar中获取的是2020-01-01 所以必须格式化一下方便 使用set移除
                                Date parse = sdf.parse(date);
                                String formatDate = sdf.format(parse);
                                String status = holidayObj.getString("status");
                                // 如果是放假 则将 date 放入set中自动去重
                                if (ObjectUtils.equals("1", status)) {
                                    HolidayInfoModel holidayModel = createHolidayModel(formatDate, year, holidayType, 1);
                                    holidayDateSet.add(holidayModel);
                                } else if (ObjectUtils.equals("2", status)) {
                                    // 如果是 加班 则 从set中移除对应的日期
                                    HolidayInfoModel holidayModel = createHolidayModel(formatDate, year, holidayType, 1);
                                    holidayWorkSet.add(holidayModel);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /***
     * 获取全年的周六日 并放在 周六日肯定不重复
     * @param dateSet 已经保存的全年 节假日休息日set
     * @param year 当前年份
     */
    private void getSatAndSun(Set<HolidayInfoModel> dateSet, int year) {
        // 创建的日期为 2020-01-01  因为 公历 少一个月 所以用0月
        Calendar calendar = new GregorianCalendar(year, 0, 1);
        // 格式化为 日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int i = 1;
        // 循环 使得一直在 year年内遍历
        while (calendar.get(Calendar.YEAR) < year + 1) {
            // 获取 2020 年的 第i个周
            calendar.set(Calendar.WEEK_OF_YEAR, i++);
            // 获取 2020 年的 第i个周的星期日
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            //如果 这个周日还在year年 则加入List
            if (calendar.get(Calendar.YEAR) == year) {
                // 根据给的参数创建 model
                HolidayInfoModel holidayModel = createHolidayModel(sdf.format(calendar.getTime()), year, "星期日", 0);
                // 因为已经从写了model 的equals和hashCode 方法,直接添加set会自动去重
                dateSet.add(holidayModel);
            }
            // 获取 2020 年的 第i个周的星期六
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            //如果 这个周六还在year年 则加入List
            if (calendar.get(Calendar.YEAR) == year) {
                // 根据给的参数创建 model
                HolidayInfoModel holidayModel = createHolidayModel(sdf.format(calendar.getTime()), year, "星期六", 0);
                // 因为已经从写了model 的equals和hashCode 方法,直接添加set会自动去重
                dateSet.add(holidayModel);
            }
        }
    }

    /***
     * 创建 holidayInfoModel
     * @param date 日期 2020-11-11
     * @param year  2020
     * @param holidayType  星期六 or 星期日 or 春节
     * @param type  0 星期六、日/1  节假日
     * @return
     */
    private HolidayInfoModel createHolidayModel(String date, Integer year, String holidayType, Integer type) {
        HolidayInfoModel holidayInfoModel = new HolidayInfoModel();
        holidayInfoModel.setYear(year);
        holidayInfoModel.setHolidayDate(date);
        holidayInfoModel.setHolidayType(holidayType);
        holidayInfoModel.setType(type);
        return holidayInfoModel;
    }


    /***
     * 获取截止日期
     * @param startDate 开始时间
     * @param days 期限 "工作日" 时间
     * @return
     */
    public String getEndTime(String startDate, Integer days) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(startDate);
        // 将起始时间设置成 calendar
        Calendar startTime = Calendar.getInstance();
        startTime.setTime(date);
        // 循环需要增加的工作日
        for (int i = 0; i < days; i++) {
            // 起始时间 加 1 天
            startTime.add(Calendar.DAY_OF_MONTH, 1);
            // 开始时间加一天后 判断是否为工作日
            if (checkHoliday(startTime)) {
                // 如果不是 则不算
                i--;
            }
        }
        // 最终为 添加的时间
        return sdf.format(startTime.getTime());
    }

    /***
     * 判断下一天是否为工作日
     * @param calendar
     * @param
     * @return
     * @throws ParseException
     */
    private boolean checkHoliday(Calendar calendar) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // Date date = null;
        // 获取今年的年份 用以从数据库查询对应的休息日期
        int year = calendar.get(Calendar.YEAR);
        // 用以将数据库中的查出的 日期于档期那日期比价
        // Calendar holidaycCa = Calendar.getInstance();
        // 从数据库查询 今年所有的休息日期
        List<String> holidayDateList = initHolidayList(year);
        //遍历查询出来的休息日期是否是当天
        String nextDate = sdf.format(calendar.getTime());
        return holidayDateList.contains(nextDate);
    }

    /***
     * 冲数据库中查询今年所有的休息日
     * @param year 年份
     * @return
     */
    private List<String> initHolidayList(int year) {
        List<String> holidayDateList = new ArrayList<String>();
        // 从数据库中获取对应的所有节假日并组成list
        List<HolidayInfoModel> holidayList = holidayInfoDao.findNowAndNext(year);
        if (holidayList != null && holidayList.size() > 0) {
            for (HolidayInfoModel holiday :
                    holidayList) {
                holidayDateList.add(holiday.getHolidayDate());
            }
        }
        return holidayDateList;
    }
}
