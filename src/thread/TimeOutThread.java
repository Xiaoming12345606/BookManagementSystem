package thread;

import javax.swing.*;

public class TimeOutThread extends Thread {
    boolean flag = false;
    long time1 = System.currentTimeMillis();
    long time2 = time1;
    public TimeOutThread() {
    }

    @Override
    public void run() {
        while (true) {
            if (flag) {
                time2 = System.currentTimeMillis();
            }
            if (time2 - time1 >= 1000 * 60 * 30) {
                JOptionPane.showMessageDialog(new JFrame(),"连接超时！\n若点击页面无反应，请先点击刷新！");
                time1 = time2;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public long getTime2() {
        return time2;
    }

    public void setTime2(long time2) {
        this.time2 = time2;
    }
}
