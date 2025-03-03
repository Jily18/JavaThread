# 1，获得线程的方式主要有以下几种：

### 1. 继承 `Thread` 类
- **原理**：定义一个类继承自 `Thread` 类，并重写 `run()` 方法，然后创建该类的对象并调用 `start()` 方法启动线程。
- **示例代码**：
```java
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("线程运行中：" + Thread.currentThread().getName());
    }
}

public class Main {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();
    }
}
```

### 2. 实现 `Runnable` 接口
- **原理**：定义一个类实现 `Runnable` 接口，实现 `run()` 方法，然后创建该类的对象，并将其作为参数传递给 `Thread` 类的构造函数，最后调用 `Thread` 对象的 `start()` 方法启动线程。
- **示例代码**：
```java
class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("线程运行中：" + Thread.currentThread().getName());
    }
}

public class Main {
    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable);
        thread.start();
    }
}
```

### 3. 实现 `Callable` 接口
- **原理**：定义一个类实现 `Callable` 接口，实现 `call()` 方法，该方法可以有返回值。然后创建 `FutureTask` 对象，将 `Callable` 对象作为参数传递给 `FutureTask` 的构造函数，再将 `FutureTask` 对象作为参数传递给 `Thread` 类的构造函数，最后调用 `Thread` 对象的 `start()` 方法启动线程。可以通过 `FutureTask` 的 `get()` 方法获取线程的返回值。
- **示例代码**：
```java
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class MyCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("线程运行中：" + Thread.currentThread().getName());
        return 100;
    }
}

public class Main {
    public static void main(String[] args) {
        MyCallable myCallable = new MyCallable();
        FutureTask<Integer> futureTask = new FutureTask<>(myCallable);
        Thread thread = new Thread(futureTask);
        thread.start();
        try {
            Integer result = futureTask.get();
            System.out.println("线程返回值：" + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
```

### 4. 使用线程池
- **原理**：通过 `ExecutorService` 线程池来管理和复用线程。可以使用 `Executors` 工厂类创建不同类型的线程池，如 `FixedThreadPool`、`CachedThreadPool` 等，然后通过 `execute()` 方法执行 `Runnable` 任务，或通过 `submit()` 方法执行 `Callable` 任务。
- **示例代码**：
```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        // 创建一个固定大小的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {
                System.out.println("线程运行中：" + Thread.currentThread().getName());
            });
        }
        // 关闭线程池
        executorService.shutdown();
    }
}
```

### 总结
- 继承 `Thread` 类和实现 `Runnable` 接口是最基本的创建线程的方式，它们的 `run()` 方法没有返回值。
- 实现 `Callable` 接口可以让线程有返回值，并且可以抛出异常。
- 使用线程池可以更高效地管理和复用线程，避免频繁创建和销毁线程带来的开销。
