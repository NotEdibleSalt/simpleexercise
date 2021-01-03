package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: xush
 * @Date: 2020-12-29
 * @Version: v1.0
 */
@Data
@AllArgsConstructor
public class Student extends Thread {
    private String name;
    private LinkedList<Task> tasks;

    public Student(String name, LinkedList<Task> tasks) {
        //调用Thread构造方法，设置threadName
        super(name);
        this.name = name;
        this.tasks = tasks;
    }

    public void copyWord() throws InterruptedException {
        String threadName = Thread.currentThread().getName();

        while (true) {
            Task task = null;

            synchronized (tasks) {
                if (tasks.size() > 0) {
                    task = tasks.removeFirst();
                    sleep(100);
                    tasks.notifyAll();
                } else {
                    System.out.println(threadName+"开始等待");
                    tasks.wait();
                    System.out.println("学生线程 "+threadName + "线程-" + name + "等待结束");
                }
            }

            if (task != null) {
                for (int i = 1; i <= task.getLeftCopyCount(); i++) {
                    System.out.println(threadName + "线程-" + name + "抄写" + task.getWordToCopy() + "。已经抄写了" + i + "次");
                }
            }
        }
    }

    //重写run方法，完成任务。
    @Override
    public void run() {
        try {
            copyWord();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
