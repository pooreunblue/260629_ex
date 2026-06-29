import java.util.function.Function;

public class Solution06 {
    public static void main(String[] args) {
//        runOld();
//        runNew();
        final int num = 1;
        final int[] arr = {1};
//        int num = 1; // 명시적으로 final이거나 / 컴파일러가 감지하는 범위 내에서 값 변환이 없어야함 (effectively final, 사실상 상수)
//        Runnable r = () -> System.out.println("러너블이에용");
//        num++; // 값의 전환이 있으면 X.
//        Runnable r = () -> System.out.println(num);
        arr[0] = 100;
//        Runnable r = () -> System.out.println(arr);
        Runnable r = () -> arr[0]++;
        r.run();
        r.run();

        // 메서드 참조
        Runnable r2 = Solution06::runNew; // Solution06 클래스의 runNew 메서드를 참조하는 람다식
        Function<String, Integer> f = Integer::parseInt;
        // 정적(static) 메서드 참조
        // 인스턴스도 할 수 있다!
//        System.out // 나도 인스턴스랍니다
        Runnable r3 = System.out::println; // 인스턴스 메서드 참조
        A a = new A();
        Runnable r4 = a::a;

        // 1. 익명 클래스에 메서드를 직접 구현
        // 2. 익명 함수를 제공하는 람다식
        // 3. 인스턴스/정적 메서드 참조
    }

    static class A {
        void a() {
        }
    }


    static void runNew() {
        CustomPrinter printer = (text) -> System.out.println("람다식에서 %s".formatted(text));
        printer.print("Hello");
    }

    static void runOld() {
        CustomPrinter printer = new CustomPrinter() {
            @Override
            public void print(String msg) {
                System.out.println("익명 클래스에서 %s".formatted(msg));
            }
        };
        printer.print("Hello");
    }
}

@FunctionalInterface // implements하지 않아도 해당 메서드에 대한 구현을 익명함수로 직접해도 인스턴화 가능
interface CustomPrinter {
    void print(String msg);
}