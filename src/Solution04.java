import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Solution04 {
    public static void main(String[] args) {
        // 스레드 풀 // 커넥션 풀
        // 풀 (Pool) - 미리 준비해놓고 반납하지 않은채로 재활용 (주로 객체)
        // 스레드 풀 (Thread Pool) - 스레드를 미리 준비해놓고 재활용
        // 객체 -> 스레드에 대한 정보
        // 스레드가 아무리 많아도 어차피 배정할 수 있는 건 cpu 여유분
        int cpuCount = Runtime.getRuntime().availableProcessors();
        System.out.println("CPU 개수 : %d".formatted(cpuCount));
        ExecutorService executorService = Executors.newFixedThreadPool(cpuCount / 2);
        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        executorService.shutdown(); // 새로운 작업을 받지 않고 지금까지것들만 처리
        try {
            // 모든 작업이 종료될 때까지 5초 기다리겠다 -> 강제종료
            if (executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                // 정상 실행 완료
                System.out.println("정상 종료");
            } else {
                System.out.println("시간 초과로 강제 종료 필요");
                executorService.shutdownNow();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}