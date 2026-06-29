import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Solution02 {
    public static void main(String[] args) throws InterruptedException {
//        runSync();
//        runLock();
        runTryLock();
    }

    static void runTryLock() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Runnable task = () -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " / lock");
                Thread.sleep(Math.round(Math.random() * 3000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                System.out.println(Thread.currentThread().getName() + " / unlock");
                lock.unlock();
            }
        };
        Runnable task2 = () -> {
            // 1초는 기다림
            try {
                System.out.println("1초 내 락 획득 시도");
                boolean getLock = lock.tryLock(1, TimeUnit.SECONDS);
                if (getLock) {
                    try {
                        System.out.println("1초 내 락 획득 성공");
                    } finally {
                        lock.unlock();
                    }
                } else {
                    System.out.println("1초 내 락 획득 실패");
                    System.out.println("다른 작업한다");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
        Thread t = new Thread(task);
        Thread t2 = new Thread(task2);

        t.start();
        t2.start();
        t.join();
        t2.join();
    }

    static void runLock() throws InterruptedException {
        LockCounter counter = new LockCounter();
        Runnable task = () -> {
            for (int i = 0; i < 50_000; i++) {
                counter.increment();
            }
        };
        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("counter.getCount() = " + counter.getCount());
    }

    static void runSync() throws InterruptedException {
        SyncCounter counter = new SyncCounter();
        Runnable task = () -> {
            for (int i = 0; i < 50_000; i++) {
                counter.increment();
            }
        };
        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        thread1.start();
        thread2.start(); // join -> 스레드 처리까지 대기
        thread1.join();
        thread2.join();
        System.out.println("counter.getCount() = " + counter.getCount());
    }
}

class LockCounter {
    private int count = 0;
    private final Lock lock = new ReentrantLock();

    public void increment() {
        lock.lock(); // 락을 획득하고, 획득할 때 대기
        try {
            count++;
            if (count % 10000 == 0) {
                System.out.println(Thread.currentThread().getName() + " / count = " + count);
            }
        } finally {
            lock.unlock(); // 무조건 언락해야함 -> 안하면 deadlock
        }
    }

    public int getCount() {
        return count;
    }
}

class SyncCounter {
    private int count = 0;

    //    public void increment() {
    public synchronized void increment() {
        count++;
        if (count % 10000 == 0) {
            System.out.println(Thread.currentThread().getName() + " / count = " + count);
        }
    }

    public int getCount() {
        return count;
    }
}