package com.zq.smallTool.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * @program: admin
 * @description: 节假日Model
 * @author: zhouQ
 * @create: 2020-11-19 14:09
 **/
@Data
public class HolidayInfoModel implements Comparable {
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    /***
     * 自增Id
     */
    private Integer id;

    /***
     * 节假日日期
     */
    @JsonProperty(value = "holiday_date")
    private String holidayDate;

    /***
     * 节假日类型 星期六、日 春节
     */
    @JsonProperty(value = "holiday_type")
    private String holidayType;

    /***
     * 节假日所属年份
     */
    private Integer year;

    /***
     * 0 星期六、日/1  节假日
     */
    private Integer type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final HolidayInfoModel model = (HolidayInfoModel) o;
        return Objects.equals(holidayDate, model.holidayDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(holidayDate);
    }

    /***
     * 重新定义按照 节假日日期排序
     * @param o
     * @return -1 从小到搭/1  从大到小
     */
    @Override
    public int compareTo(Object o) {

        try {
            HolidayInfoModel b = (HolidayInfoModel) o;
            return df.parse(this.getHolidayDate()).compareTo(df.parse(b.getHolidayDate()));
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }

    }
}
