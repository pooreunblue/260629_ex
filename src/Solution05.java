import java.util.concurrent.CompletableFuture;

public class Solution05 {
    public static void main(String[] args) {
        runChain();
    }

    static void runChain() {
        CompletableFuture<Void> pipeline = CompletableFuture
                .supplyAsync(() -> {
                    try {
                        Thread.sleep(1000);
                        throw new RuntimeException("Error");
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return "Hello!"; // String
                })
                .thenApply(s -> {
                    System.out.println(s);
                    return s;
                })
                .exceptionally(e -> "Error : " + e.getMessage())
                .thenAccept(s -> System.out.println("Result : " + s));
        pipeline.join(); // join, shutdown
    }
}