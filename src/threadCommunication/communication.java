package threadCommunication;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * @author majun
 * @Description: 多线程之间的相互通信
 * @create: 2017-06-13 14:16
 */
public class communication {

    public static void  main(String[] args){
       //demo1();
       //demo2();
       // demo3();
       // demo4();
        demo5();
    }

    /**
     * A  B 两个线程依次进行
     */
    private static void demo1() {
        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                printNumber("A");
            }
        });

        Thread B = new Thread(new Runnable() {
            @Override
            public void run() {
                printNumber("B");
            }
        });

        A.start();
        B.start();
    }

    /**
     * B 在A执行完之后在执行，  使用join方法
     */
    private static void demo2(){

        final Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                printNumber("A");
            }
        });

        Thread B = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("B开始等待A");

                try {
                    A.join();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                printNumber("B");
            }
        });


      B.start();
      A.start();
    }


    /**
     * 让两个线程按照指定方式交叉运行----使用wait和notify方法
     */
    private static void demo3(){
        final Object lock = new Object();

        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    System.out.println("A 1");
                    try {
                        lock.wait();
                    }catch (InterruptedException e ){
                        e.printStackTrace();
                    }

                    System.out.println("A 2");
                    System.out.println("A 3");
                }

            }
        });

        Thread B = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    System.out.println("B 1");
                    System.out.println("B 2");
                    System.out.println("B 3");

                    lock.notify();
                }
            }
        });

        A.start();
        B.start();

    }

    /**
     * D线程等待A ,B , C执行完成后在执行，三个线程同步执行，CountDownlatch试用于一个线程等待多个线程的情况
     * CountDownLatch相当于倒计数器
     */
    private static void demo4(){
        int worker = 3;
        final CountDownLatch countDownLatch = new CountDownLatch(worker);

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("D is waiting for other three threads");
                try {
                    countDownLatch.await();
                    System.out.println("All done, D starts working");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();

        for (char threadName='A';threadName<='C';threadName++){
            final String Tn = String.valueOf(threadName);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Tn + "  is working");
                    try {
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    System.out.println(Tn + " finished");
                    countDownLatch.countDown();

                }
            }).start();
        }
    }

    /**
     * A, B, C三个线程各自准备，直到三者准备完毕，然后再同时进行----线程之间互相等待
     */
    public static void demo5(){
        int runner = 3;
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(runner);

        final Random random = new Random();
        for (char runnerName = 'A';runnerName<= 'C';runnerName++){
            final String Rn = String.valueOf(runnerName);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    long prepareTime = random.nextInt(10000) + 100;
                    System.out.println(Rn + "   is preparing for time :" + prepareTime);
                    try {
                        Thread.sleep(prepareTime);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }

                    try {
                        System.out.println(Rn + "  is prepared, waiting for others");
                        cyclicBarrier.await();
                    }catch (InterruptedException e ){
                        e.printStackTrace();
                    }catch (BrokenBarrierException e){
                        e.printStackTrace();
                    }

                    System.out.println(Rn + " starts running");
                }
            }).start();
        }
    }
    /**
     * 公用打印方法
     * @param threadName
     */
    private static void printNumber(String threadName) {
        int i = 0;
        while (i++ < 3) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(threadName + "print:" + i);
        }
    }
}


