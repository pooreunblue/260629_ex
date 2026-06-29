import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solution07 {
    public static void main(String[] args) {
        // 정렬 -> sort (하나의 차원 -> 데이터들을 나열하는 방식을 결정), align (화면 내 배치. 표현하려는 데이터들간의 관계. 화면상의 상대적 위치)
//        runSort();
        runCustomSort();
    }

    static void runCustomSort() {
//        List<Integer> list = new ArrayList<>(List.of(-1, 3, -5, 7, -9)); // 절대값 기준으로 정렬가능? (부호가 없어진 것)
        List<Container> list = new ArrayList<>(List.of(new Container(99, -99), new Container(1, 2), new Container(2, 1))); // 절대값 기준으로 정렬가능? (부호가 없어진 것)
        // [e1, e2, ...]
//        list.sort((e1, e2) -> e1 - e2); // e2가 더 크면 무조건 -가 되겠죠. (0). e1 +
        // -> 두 값을 비교할 때... 두 값의 차이를 반환하는데 +면 두 위치를 바꾼다. 0이나 -면 그대로 둔다.
//        list.sort((e1, e2) -> e2 - e1); // 내림차순
//        list.sort((e1, e2) -> Math.abs(e1) - Math.abs(e2));
//        list.sort(Comparator.comparingInt(Math::abs));
        list.sort((e1, e2) -> e1.a() - e2.a()); // a 기준 오름차순
//        list.sort(Comparator.comparing(Container::a));
//        list.sort(Comparator.comparing(Container::b));
        list.sort((e1, e2) -> e2.b() - e1.b()); // b 기준 내림차순
        System.out.println(list);
    }

    static record Container(int a, int b) {
    }

    static void runSort() {
        List<String> list = new ArrayList<>(List.of("참치", "꽁치", "멸치"));
        System.out.println(list);
//        Collections.sort(list, Comparator.naturalOrder()); // 가나다순, 오름차순.
//        list.sort(Comparator.naturalOrder());
        list.sort(Comparator.reverseOrder()); // 원본 힙주소에 연결된 객체에 영향을 준다
        // 1. 우리는 주어진 정렬 방법만 쓸 수 있는 건가? // 오름차순, 내림차순 외에는 정렬이 없나?
        // - 실은 '정렬'은 인터페이스를 만족시키는 '비교를 해주는 함수'로 2개의 원소를 처리한 다음 +냐 -냐 등으로 처리를 함.
        // 2. 일반적으로 '비교 가능한' (숫자, 숫자로 표현 가능한 문자) 값들은 제외한 참조 타입들 (객체) 들은 어떻게 비교하지?
        // - 메서드/함수로 정렬을 하는 거라면, 패러미터와 비교 연산자 등을 어떻게 쓸것인가? 기준점을 뭐로 잡을 거냐?
        System.out.println(list);
    }
}