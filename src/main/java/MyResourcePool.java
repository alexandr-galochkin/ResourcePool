import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class MyResourcePool<T extends Runnable> {
    private final int startPoolCapacity = 10;
    private int idleTime;
    private int poolCapacity;
    final BlockingQueue<T> taskQueue;
    final ArrayList<MyResource<T>> resources;

    MyResourcePool(int idleTime) {
        this.idleTime = idleTime;
        poolCapacity = startPoolCapacity;
        taskQueue = new LinkedBlockingDeque<T>();
        resources = new ArrayList<>(poolCapacity);
        for (int i = 0; i < poolCapacity; i++) {
            MyResource<T> newResource = new MyResource<>(this, idleTime);
            newResource.start();
            resources.add(newResource);
        }
    }

    MyResourcePool(int startPoolCapacity, int idleTime) {
        this.idleTime = idleTime;
        poolCapacity = startPoolCapacity;
        taskQueue = new LinkedBlockingDeque<T>();
        resources = new ArrayList<>(poolCapacity);
        for (int i = 0; i < poolCapacity; i++) {
            MyResource<T> newResource = new MyResource<>(this, idleTime);
            newResource.start();
            resources.add(newResource);
        }
    }

    public boolean execute(T task) {
        if (resources.size() < poolCapacity) {
            synchronized (resources) {
                if (resources.size() < poolCapacity) {
                    MyResource<T> newResource = new MyResource<>(this, idleTime);
                    newResource.start();
                    resources.add(newResource);
                }
            }
        }
        return taskQueue.add(task);
    }

    public boolean increasePoolCapacity(int inc) {
        if (inc < 1) {
            return false;
        }
        for (int i = 0; i < inc; i++) {
            MyResource<T> newResource = new MyResource<>(this, idleTime);
            newResource.start();
            resources.add(newResource);
        }
        poolCapacity += inc;
        return true;
    }

    public boolean containsTask() {
        return !taskQueue.isEmpty();
    }

}
