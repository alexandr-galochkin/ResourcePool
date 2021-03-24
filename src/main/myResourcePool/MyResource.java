package main.myResourcePool;

import main.myResourcePool.MyResourcePool;

class MyResource<T extends Runnable> extends Thread {
    private final MyResourcePool<T> myResourcePool;
    int idleTime;

    MyResource(MyResourcePool<T> myResourcePool, int idleTime) {
        this.myResourcePool = myResourcePool;
        this.idleTime = idleTime;
    }

    @Override
    public void run() {
        while (true) {
            long startTime = System.currentTimeMillis();
            long currentTime = startTime;
            while ((myResourcePool.taskQueue.isEmpty()) && ((currentTime - startTime) < idleTime * 1000)) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currentTime = System.currentTimeMillis();
            }
            if (currentTime - startTime < idleTime * 1000) {
                T currentTask = null;
                synchronized (myResourcePool.taskQueue) {
                    if (!myResourcePool.taskQueue.isEmpty()) {
                        currentTask = myResourcePool.taskQueue.poll();
                    }
                }
                if (currentTask != null) {
                    currentTask.run();
                }
            } else {
                break;
            }
        }
        synchronized (myResourcePool.resources){
            myResourcePool.resources.remove(this);
        }
    }
}
