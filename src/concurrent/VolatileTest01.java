package concurrent;

/**
 * Created by mj on 2017/6/10.
 */
public class VolatileTest01 {
    volatile  int i;

    public void addI(){
        i++;
    }

    public static void main(String[] args) throws InterruptedException{
        final VolatileTest01 test01 = new VolatileTest01();
        for (int n = 0; n<1000; n++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    test01.addI();
                }
            }).start();
        }

        Thread.sleep(10000);

        System.out.println(test01.i);
    }
}
