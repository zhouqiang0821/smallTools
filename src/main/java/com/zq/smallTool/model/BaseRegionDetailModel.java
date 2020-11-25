package com.zq.smallTool.model;

import lombok.Data;

/**
 * @program: admin
 * @description: 区划代码和城乡划分代码
 * @author: zhouQ
 * @create: 2020-11-19 09:18
 **/
@Data
public class BaseRegionDetailModel {
    /***
     * 区域代码
     */
    private String regionCode;

    /***
     * 区域名称
     */
    private String regionName;

    /***
     * 父级区域代码
     */
    private String pid;

    /***
     * 区域类型
     */
    private String type;
}
