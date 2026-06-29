import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Solution03 {
    public static void main(String[] args) {
//        runAtomic();
        runMap();
    }

    static void runMap() {
        Map<String, Long> map = new HashMap<>(); // thread safe X. 유실문제.
        ConcurrentHashMap<String, Long> concurrentMap = new ConcurrentHashMap<>();
        int limit = 1_000;

        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < limit; i++) {
            final int key = i;
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " / key = " + key);
                map.put("key-%d".formatted(key), System.currentTimeMillis());
                concurrentMap.put("key-%d".formatted(key), System.currentTimeMillis());
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("map = " + map);
        System.out.println("map.size() = " + map.size());
        System.out.println("concurrentMap = " + concurrentMap);
        System.out.println("concurrentMap.size() = " + concurrentMap.size());
    }

    static void runAtomic() {
        AtomicInteger atomicCount = new AtomicInteger(0); // 객체
        NonAtomic nonAtomicCount = new NonAtomic();
        int limit = 50_000;
        Runnable task = () -> {
            for (int i = 0; i < limit; i++) {
                atomicCount.incrementAndGet();
                nonAtomicCount.increment();
            }
        };
        Thread t = new Thread(task);
        Thread t2 = new Thread(task);
        t.start();
        t2.start();
        try {
            t.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("atomic : " + atomicCount.get());
        System.out.println("non-atomic : " + nonAtomicCount.getCount());
    }

}

class NonAtomic {
    private int count;

    void increment() {
        count++;
    }

    int getCount() {
        return count;
    }
}