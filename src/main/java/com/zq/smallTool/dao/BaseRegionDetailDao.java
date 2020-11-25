package com.zq.smallTool.dao;

import com.zq.smallTool.model.BaseRegionDetailModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: admin
 * @description: 区划分代码和城乡划分代码Dao
 * @author: zhouQ
 * @create: 2020-11-19 09:21
 **/
@Mapper
public interface BaseRegionDetailDao {

    /***
     * 根据 regionCode 查找
     * @param regionCode 区域CODE
     * @return
     */
    List<BaseRegionDetailModel> find(String regionCode, String regionName, String pid);

    /***
     * 批量插入
     * @param list 皮插入参数
     */
    void insert(List<BaseRegionDetailModel> list);

    /***
     * 获取数据库保存的最后一个省
     * @return
     */
    List<String> getAllProvince();

    /***
     * 获取当前省的最后一个市信息
     * @param areaCode
     * @return
     */
    String getLastCityForPro(String regionCode);

}
