package com.zq.admin.controller;

import com.zq.admin.service.BaseRegionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: admin
 * @description: 获取省市区详细数据信息
 * @author: zhouQ
 * @create: 2020-11-19 11:22
 **/
@Controller("/utils")
public class BaseRegionDetailsController {
    @Autowired
    BaseRegionDetailService baseRegionDetailService;

    /***
     * 批量插入
     */
    @RequestMapping("/getAllRegion")
    public void insert() {
        try {
            baseRegionDetailService.getProvince();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
