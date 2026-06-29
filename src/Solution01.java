public class Solution01 {
    //    public static void main(String[] args) {
    public static void main(String[] args) throws InterruptedException {
        runIsolation(); // 메서드 시그니처에 throws
        runRaceCondition();
    }

    static void runRaceCondition() throws InterruptedException {
        Counter counter = new Counter();
        Runnable task = () -> {
            for (int i = 0; i < 50_000; i++) {
                counter.increment();
                try {
                    Thread.sleep(Math.round(Math.random()));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
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

    static void runIsolation() throws InterruptedException {
        Runnable task = () -> {
            int localCounter = 0; // 스택 영역
            for (int i = 0; i < 5; i++) {
                localCounter++; // 이 하나의 블록 내부에 있는 건 stack 메모리
                // 이 스택 메모리는 병렬 호출되는 스레드마다 독립적임
                System.out.println(Thread.currentThread().getName() + " / localCounter = " + localCounter);
                try {
                    Thread.sleep((long) (Math.random() * 100)); // 교대 실행을 위한 대기
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}

// 클래스 -> 인스턴스(객체) -> heap
class Counter {
    private int count = 0;

    public void increment() {
        count++; // count를 읽고 -> count+1을 연산하고 -> 그 결과를 count에 저장
        // 'count를 읽은' 시점에 따라서 count+1이 달라짐. -> 결과가 유실될 수 있음
    }

    public int getCount() {
        return count;
    }
}