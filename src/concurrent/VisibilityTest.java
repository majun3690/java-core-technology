package concurrent;

/**
 * @author majun
 * @Description: 线程-可见性
 * @create: 2017-06-09 15:38
 */
public class VisibilityTest {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        public void run(){
            try {

                Thread.sleep(10);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            if (!ready) {
                System.out.println(ready);
            }
            System.out.println(number);
        }
    }

    private static class WriterThread extends Thread {
        public void run (){
            try {

                Thread.sleep(10);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            number = 100;
            ready = true;
        }
    }

    public static void main(String[] args) {
        new WriterThread().start();
        new ReaderThread().start();

    }
}
