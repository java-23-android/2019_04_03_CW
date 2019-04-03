package com.sheygam.java_23_03_04_19_cw;

import android.os.Handler;
import android.os.Message;

public class Worker implements Runnable {
    public static final int STATE_START = 0x01;
    public static final int STATE_END = 0x02;
    public static final int STATE_UPDATE = 0x03;
    private final Handler handler;

    public Worker(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        handler.sendEmptyMessage(STATE_START);
        for (int i = 0; i < 100; i++) {
            Message message = handler.obtainMessage();
            message.what = STATE_UPDATE;
            message.arg1 = i + 1;
            handler.sendMessage(message);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        handler.sendEmptyMessage(STATE_END);

    }
}
