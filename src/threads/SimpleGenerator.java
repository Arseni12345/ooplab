package threads;

import functions.basic.Log;

import java.util.concurrent.Semaphore;

public class SimpleGenerator implements Runnable{

    private Semaphore semaphore;

    private Task task;

    public static boolean flag;

    public SimpleGenerator(Task task, Semaphore semaphore){
        this.task = task;
        this.semaphore = semaphore;
        flag = true;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; ++i) {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                while (!flag) semaphore.release();
                task.setFunction(new Log(Math.random() * 9 + 1));
                task.setLeftDomain(Math.random() * 100);
                task.setSamplingStep(Math.random());
                task.setRightDomain(Math.random() * 100 + 100);
                System.out.println("Source: " + task.getLeftDomain() + " " + task.getRightDomain() + " " + task.getSamplingStep());
                flag = false;
                semaphore.release();
        }
    }
}
