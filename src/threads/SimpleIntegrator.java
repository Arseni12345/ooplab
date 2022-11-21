package threads;

import functions.Functions;

import java.util.concurrent.Semaphore;

public class SimpleIntegrator implements Runnable{
    private Task task;
    private Semaphore semaphore;

    public SimpleIntegrator(Task task, Semaphore semaphore){
        this.task = task;
        this.semaphore = semaphore;
    }
    @Override
    public void run() {
        for(int i = 0; i < 100; ++i) {
                try {
                    semaphore.acquire();
                    while (SimpleGenerator.flag) semaphore.release();
                    System.out.println("Result: " + task.getLeftDomain() + " " + task.getRightDomain() + " " + task.getSamplingStep() + " " + Functions.integrate(task.getFunction(), task.getLeftDomain(), task.getRightDomain(), task.getSamplingStep()));
                    SimpleGenerator.flag = true;
                    semaphore.release();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        }
    }
}
