package concurrent;

/**
 * Created by mj on 2017/6/10.
 */
public class WaitTest {
    public  synchronized void testWait(){
        System.out.print("Start----------");
        try {
            wait(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("End-------------");
    }

    public  static  void  main(String[] args){
        final WaitTest test = new WaitTest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                test.testWait();
            }
        }).start();
    }
}
