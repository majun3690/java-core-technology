package concurrent;

/**
 * @author majun
 * @Description:线程共享性
 * @create: 2017-06-09 15:16
 */
public class ShareData {
    public static int count = 0;

    public static void main(String[] args) {
        final ShareData data = new ShareData();
        for (int i = 0;i<10;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                   try {
                       //进入的时候暂停1毫秒，增加并发问题出现的几率
                       Thread.sleep(2);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }

                   for (int j = 0;j<100;j++) {
                       data.addCount();
                   }
                   System.out.print(count + " ");
                }
            }).start();
        }

        try{
            //主程序暂停3秒，以保障上面程序执行完
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 增加synchronized关键字-互斥性，  去掉-共享性
     */
    private synchronized void addCount() {
        count++;
    }
}
