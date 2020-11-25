package com.zq.smallTool.schedule;

import com.zq.smallTool.service.HolidayInfoService;
import com.zq.smallTool.utils.SendMsmUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;

/**
 * @program: admin
 * @description: 定时任务类
 * @author: zhouQ
 * @create: 2020-11-23 10:35
 **/
@Component
@EnableScheduling
public class FissionWantedTask {

    @Autowired
    private HolidayInfoService holidayInfoService;

    /***
     * 每年1月1号0点0分0秒获取当年的节假日信息
     */
    @Scheduled(cron = "23 15 1 1 1 ?")
    public void getAllHolidayTask() {
        try {
            Integer nums = holidayInfoService.insertDb();
            SendMsmUtils.sendEmail("zhouqiang@195@gmail.com",
                    "holidayTimmer运行报告",
                    LocalDate.now().getYear() + "年,共有节假日、星期六日:" + nums + "天.");
        } catch (Exception e) {
            try {
                SendMsmUtils.sendEmail("zhouqiang@195@gmail.com",
                        "holidayTimmer运行报告",
                        "获取节假日异常 :" + "引起异常的原因------>" + e + "\r\n 异常信息------>" + e.getMessage() + "\r\n 异常跟踪栈------>" + getExceptionStrintStackTrace(e));
            } catch (MessagingException ex) {
                ex.printStackTrace();
            }
        }
    }

    private String getExceptionStrintStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
