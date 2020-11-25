package com.zq.admin.controller;

import com.zq.admin.service.HolidayInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;

/**
 * @program: admin
 * @description: 节假日信息Controller
 * @author: zhouQ
 * @create: 2020-11-19 16:48
 **/
@Controller
@RequestMapping("/baseUtils")
public class HolidayInfoController {

    @Autowired
    private HolidayInfoService holidayInfoService;

    /***
     * 全国节假日信息保存更新
     */
    @RequestMapping("/getHoliday")
    public void getAllYearHoliday() {
        try {
            holidayInfoService.insertDb();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
