package com.zq.admin.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: admin
 * @description: 关于 爬虫的配置文件
 * @author: zhouQ
 * @create: 2020-11-19 08:36
 **/
@Data
@Component
@ConfigurationProperties(prefix = "spider")
public class SpiderPropertyModel {

    /***
     * 爬取年份数据
     */
    private String spiderYear = "2020";

    /***
     * 是否开启动态IP代理
     */
    private Boolean proxyEnable = false;

    /***
     * 设置ip代理后ip.txt地址
     */
    private String ipLocation = "111";

    /***
     * 是否开启 市级睡眠
     */
    private Boolean isCitySleep = true;

    /***
     * 市级睡眠默认时间  s级
     */
    private Integer citySleepTime = 30;

    /***
     * 是否开启 区、县 级睡眠
     */
    private Boolean isCountySleep = false;

    /***
     * 区、县 级睡眠默认时间 s级
     */
    private Integer countySleepTime = 30;

    /***
     * 是否获取乡镇、街道信息
     */
    private Boolean getTowntr = false;

    /***
     * 是否获取村、社区信息
     */
    private Boolean getVillagetr = false;
}