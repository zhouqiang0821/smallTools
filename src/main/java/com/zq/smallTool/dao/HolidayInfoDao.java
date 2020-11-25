package com.zq.smallTool.dao;

import com.zq.smallTool.model.HolidayInfoModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * @program: admin
 * @description: 节假日Dao
 * @author: zhouQ
 * @create: 2020-11-19 14:15
 **/
@Mapper
public interface HolidayInfoDao {
    /***
     * 获取全部节假日信息
     * @return
     */
    List<HolidayInfoModel> getAll();

    /***
     * 插入节假日信息
     * @param list
     */
    void insert(Set<HolidayInfoModel> list);

    /***
     * 根据year获取当年的全部节假日信息
     * @return
     */
    List<HolidayInfoModel> find(int year);

    /***
     * 根据year获取当年和下一年的全部节假日信息
     * @return
     */
    List<HolidayInfoModel> findNowAndNext(int year);
}
