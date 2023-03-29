package com.atcwl.gulicollege;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
public class MyThread {
    public static void main(String[] args) {
        //System.out.println(str1);
        //System.out.println(str1.intern());
        //System.out.println(str1 == str1.intern());
        //
        //System.out.println();
        //
        //String str2 = new String("java");
        //System.out.println(str2);
        //System.out.println(str2.intern());
        //System.out.println(str2 == str2.intern());
        //String str3 = new StringBuilder("java").append(".lang").toString();
        //System.out.println(str3 == str3.intern());
        //Thread t2 = new Thread(() -> {
        //
        //});
        //t1.start();
        //try {
        //    Thread.sleep(1000);
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        //}
        //System.out.println(t1.getState());
        //t2.start();
       //long nowTime = System.currentTimeMillis();
       // System.out.println("当前时间为：" + nowTime);
       // long scheduleTime1 = (nowTime + 5000);
       // long scheduleTime2 = (nowTime + 5000);
       // System.out.println("计划时间1为：" + scheduleTime1);
       // System.out.println("计划时间2为：" + scheduleTime2);
       // MyTask task1 = new MyTask();
       // MyTask task2 = new MyTask();
       // Timer timer = new Timer();
       // timer.schedule(task1, new Date(scheduleTime1));
       // timer.schedule(task2, new Date(scheduleTime2));
       // task1.cancel();
       // try {
       //     Thread.sleep(10000);
       // } catch (InterruptedException e) {
       //     e.printStackTrace();
       // }
       // timer.cancel();
        Thread t1 = new Thread(() -> {
            System.out.println(1);
        });
        t1.start();
        t1.start();
    }
}

class MyTask extends TimerTask {
    private ReentrantLock lock = new ReentrantLock();
    @Override
    public void run() {
        lock.lock();
        System.out.println("任务执行了，时间为："  + System.currentTimeMillis());
        System.out.println("执行线程为：" + Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();
    }
}