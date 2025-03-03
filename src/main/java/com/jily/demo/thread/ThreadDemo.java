package com.jily.demo.thread;

import java.util.concurrent.*;

//线程创建的四种方式
public class ThreadDemo {
    public static void main(String[] args) {

        Thread thread = new MyThread();
        thread.start();

        Thread thread1 = new Thread(new MyRunnable());
        thread1.start();


        try {

            //使用Callable接口创建线程时，使用FutureTask作为中间桥梁
//          1 Callable接口不能直接被Thread使用，因为Thread的构造器只接受Runnable
//          2  FutureTask实现了Runnable接口，所以它可以被传递给Thread
//          3 FutureTask可以包装Callable，使其能够被Thread使用
//          4 FutureTask还提供了获取任务执行结果的能力

            MyCallable myCallable = new MyCallable();
            FutureTask<String> futureTask = new FutureTask<>(myCallable);
            Thread thread2 = new Thread(futureTask);
            thread2.start();
//            可以通过futureTask.get()获取线程执行的结果
            String s = futureTask.get();//获取任务执行结果
            System.out.println("线程返回值：" + s);


            //使用线程池创建线程
            //这段代码创建了一个线程池，核心线程数为3，最大线程数为5，空闲线程存活时间为1秒，任务队列容量为3，使用默认线程工厂和丢弃策略。
            ExecutorService threadPool = new ThreadPoolExecutor(3,5,1L, TimeUnit.SECONDS
            , new LinkedBlockingQueue<Runnable>(3),
                    Executors.defaultThreadFactory(),
                    new ThreadPoolExecutor.DiscardPolicy());
            for (int i = 0; i < 5; i++) {
                threadPool.execute(new MyRunnable2());
            }

        } catch (Exception e) {
           System.out.println(e.getMessage());
        }
    }
}


//方法1：继承Thread类
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("方法1 线程运行中：" + Thread.currentThread().getName());
    }
}

//方法2：实现Runnable接口
class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("方法2 MyRunnable线程运行中：" + Thread.currentThread().getName());
    }
}

//方法3：实现Callable接口
class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("方法3 MyCallable run"+ Thread.currentThread().getName());
        return "MyCallable return string";
    }
}


//为线程池写的Runnable2
class MyRunnable2 implements Runnable {
    @Override
    public void run() {
        System.out.println("方法4 MyRunnable2线程运行中：" + Thread.currentThread().getName());
    }
}
