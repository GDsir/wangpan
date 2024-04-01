package com.hjk.wangpan.utils;

public class SpeedLimiter {

    /**
     * 速度上限(KB/s), 0=不限速
     */
    private int maxRate = 1024;

    private long getMaxRateBytes() {
        return this.maxRate * 1024;
    }

    private long getLessCountBytes() {
        long lcb = getMaxRateBytes() / 10;
        if (lcb < 10240) lcb = 10240;
        return lcb;
        //return 1024l * 20;
    }

    public SpeedLimiter(int maxRate) {
        this.setMaxRate(maxRate);
    }

    public synchronized void setMaxRate(int maxRate) {
        this.maxRate = maxRate < 0 ? 0 : maxRate;
    }

    private long totalBytes = 0;
    private long tmpCountBytes = 0;
    private long lastTime = System.currentTimeMillis();

    /**
     * @param len send bytes
     * @author Ethan
     * @date 2022-11-18
     * @description 计算线程延时
     * sendTime(Ms) = nowTime - lastTime;
     * workTime(Ms) = (totalBytes*1000)/(maxRate*1024)
     * delayTime(Ms) = workTime-sendTime
     **/
    public synchronized void delayNextBytes(int len) {
        if (maxRate <= 0) return;
        totalBytes += len;
        tmpCountBytes += len;
        //未达到指定字节数跳过...
        if (tmpCountBytes < getLessCountBytes()) {
            return;
        }
        long nowTime = System.currentTimeMillis();
        long sendTime = nowTime - lastTime;
        long workTime = (totalBytes * 1000) / getMaxRateBytes();
        long delayTime = workTime - sendTime;
        if (delayTime > 0) {
            try {
                Thread.sleep(delayTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tmpCountBytes = 0;
        }
    }
}
