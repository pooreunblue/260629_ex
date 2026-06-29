import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

public class Solution09 {
    public static void main(String[] args) {
        // stream, parallel
//        runStream();
        runParallel();
    }

    record Singer(String name, int age, String country) {
    }

    static void runParallel() {
        List<Long> list = new ArrayList<>();
        int count = 100_000;
//        IntStream
        // range -> 시작점 포함, 끝점 미포함
        // rangeClosed -> '', 끝점 포함
        List<Long> result = LongStream.rangeClosed(1, count)
                .parallel() // 스레드로 병렬처리 -> 경합.
//                .forEach(list::add);
                .boxed()
//                .collect(Collectors.toList());
                .toList(); // JDK16+
        // 메서드 참조 -> 패러미터와 리턴 조건이 맞으면 스태틱/인스턴스 메서드를 람다처럼 쓸 수 있게
        System.out.println("list.size() = " + list.size());
    }

    static void runStream() {
        List<Singer> data = List.of(
                new Singer("수인", 21, "한국"),
                new Singer("가원", 21, "미국"),
                new Singer("안나", 20, "일본"),
                new Singer("나린", 18, "한국"),
                new Singer("엘라", 17, "미국")
        );
        List<String> stream1 = data.stream()
                // *map, *reduce, sort(ed), *filter, forEach
                .map((el) -> el.age > 18 ? "성인" : "미성년자")
                .map((el) -> el.equals("성인") ? "ADULT" : "CHILD")
                // 최종적으로 return한 타입/구조의 데이터로 일괄 변경
                .toList();
        System.out.println("stream1 = " + stream1);
        System.out.println("data = " + data);
        int sum = data.stream()
                // 합쳐지는 것. 로직.
                .reduce(0, (prev, cur) -> prev + cur.age, (el1, el2) -> el1 + el2);
        // reduce 1 : 합쳐질 값. 숫자일 수도 있고, 객체.
        // reduce 2 : 직전 결과와 다음 입력값을 합치는 로직 -> 순차접근시 (직전값, 현재값)
        // reduce 3 : 병렬 처리시, 각각 모든 처리가 된 다음 -> 엮을 때 순번 보장 X. (개별값으로 마치 정렬처럼)
        // 중간에 reduce가 끼면 결과값이 유사 스트림이더라도 다시 시작해야함.
        System.out.println("sum = " + sum);
        // filter
        List<Singer> stream2 = data.stream()
                .filter((el) -> el.age > 18)
                .toList();
        System.out.println("stream2 = " + stream2);
    }
}