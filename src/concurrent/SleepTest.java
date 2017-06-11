package concurrent;

/**
 * Created by mj on 2017/6/10.
 */
public class SleepTest {
    public synchronized void sleepMethod(){
        System.out.println("Sleep start-------------");
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Sleep end---------------");
    }

    public synchronized void waitMethod(){
        System.out.println("Wait start--------------");
        synchronized (this){
            try {
                wait(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("Wait end----------------");
    }


    public static void main(String[] args){

        final SleepTest test1 = new SleepTest();

        for (int i = 0; i<3; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    test1.sleepMethod();
                }
            }).start();
        }

        try {
            Thread.sleep(10000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("--------------分割线---------------");

        final SleepTest test2 = new SleepTest();
        for (int i = 0;i<3;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    test2.waitMethod();
                }
            }).start();
        }

    }
}
