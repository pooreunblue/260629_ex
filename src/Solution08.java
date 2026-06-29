import java.util.List;

public class Solution08 {
    static List<String> names = List.of("Kim", "Park", "Lee", "Choi", "Jung");

    public static void main(String[] args) {
        runLazy();
        // eager vs lazy
    }

    static void runLazy() {
        System.out.println("이것이 스트림이다 희망편");
        names.stream() // collection -> stream
                .filter(name -> {
                    System.out.println("스트림 필터 : %s".formatted(name));
                    return name.length() > 3;
                })
                .limit(1) // 쇼트 서킷
                .map(name -> {
                    System.out.println("스트림 맵 : %s".formatted(name));
                    return name.toUpperCase();
                })
                // 1. 최종 연산이 안들어가면 아예 실행 X
                // 2. 최종 연산이 들어가더라도 중간에 filter 등으로 걸러지거나 특정 데이터만 선택되면 해당 데이터를 안쓰거나 아니면 멈춤.
                .forEach(System.out::println);
        ; // reduce, forEach처럼 최종적으로 스트림으로 하는 '작업'
        // 1. 특정한 값을 리턴하거나 => Collection, 배열. / 특정 타입으로...
        // 2. 소비함. (다른 부작용을 위해서 출력, 호출 등에 쓰임)


    }
}