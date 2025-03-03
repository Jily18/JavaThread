package com.jily.demo.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

//线程创建的四种方式
public class ThreadDemo {
    public static void main(String[] args) {

        Thread thread = new MyThread();
        thread.start();

        Thread thread1 = new Thread(new MyRunnable());
        thread1.start();


        try {

            //使用Callable接口创建线程时，使用FutureTask作为中间桥梁
//            基本概念
            //1.Thread类只能接收Runnable接口的实现类作为参数
            //2.Callable接口是一个可以有返回值的任务接口
            //3.FutureTask是一个桥接器，它同时实现了Runnable和Future接口

//            为什么需要FutureTask:
//            Callable接口不能直接被Thread使用，因为Thread的构造器只接受Runnable
//            FutureTask实现了Runnable接口，所以它可以被传递给Thread
//            FutureTask可以包装Callable，使其能够被Thread使用
//           FutureTask还提供了获取任务执行结果的能力

            MyCallable myCallable = new MyCallable();
            FutureTask<String> futureTask = new FutureTask<>(myCallable);
            Thread thread2 = new Thread(futureTask);
            thread2.start();
//            可以通过futureTask.get()获取线程执行的结果
            String s = futureTask.get();//获取任务执行结果
            System.out.println(s);



            //使用线程池创建线程

            ThreadPoolDemo.testThreadPool();
        } catch (Exception e) {
           System.out.println(e.getMessage());
        }
    }
}


//方法1：继承Thread类
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("MyThread run");
    }
}

//方法2：实现Runnable接口
class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("MyRunnable run");
    }
}

//方法3：实现Callable接口
class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("MyCallable run");
        return "MyCallable return string";
    }
}

