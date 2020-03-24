package com.scheduled;


import com.service.MeetService;

import java.util.TimerTask;

/**
 * @auth admin
 * @date 2020/3/23 15:45
 * @Description
 */
public class MeetingTask extends TimerTask {

    private boolean isRunning = false;
    private MeetService meetService = new MeetService();

    @Override
    public void run() {
        if (!isRunning) {
            isRunning = true;
            //开始执行
            meetService.meetStatusChange();
            //执行结束
            isRunning = false;
        }
    }

}
