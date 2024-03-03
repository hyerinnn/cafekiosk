package sample.cafekiosk.unit.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sample.cafekiosk.unit.beverage.Beverage;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class Order {

    // final -> 수정할 수 없는 값이므로 초기화 값이 필수적이다.

    private final LocalDateTime orderDateTime;
    private final List<Beverage> beverages;
}
