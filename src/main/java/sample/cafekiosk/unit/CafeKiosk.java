package sample.cafekiosk.unit;

import lombok.Getter;
import sample.cafekiosk.unit.beverage.Beverage;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CafeKiosk {

    public static final LocalTime SHOP_OPEN_TIME = LocalTime.of(10,0);
    public static final LocalTime SHOP_CLOSE_TIME = LocalTime.of(22,0);


    private final List<Beverage> beverages = new ArrayList<>();

    public void add(Beverage beverage) {
        beverages.add(beverage);
    }

    public void add(Beverage beverage, int count) {
        if(count <= 0){
            throw new IllegalArgumentException("음료는 1잔 이상 주문하실 수 있습니다");
        }

        for(int i=0; i<count; i++){
            beverages.add(beverage);
        }

    }

    public void remove(Beverage beverage){
        beverages.remove(beverage);
    }

    public void clear(){
        beverages.clear();
    }

    public int calculateTotalPrice() {
        int totalPrice=0;
        for(Beverage beverage : beverages){
            totalPrice += beverage.getPrice();
        }

/*
        //Stream 방식
        beverages.stream()
                .mapToInt(Beverage::getPrice)       //메소드참조 방식 -> Beverage객체에 대해 getPrice 메소드를 호출하여 해당 객체의 가격을 정수로 반환
                .sum();
*/
        return  totalPrice;
    }

    // 현재 일시로 인해 테스트하기 어려운 영역  -> 파라미터로 LocalDateTime를 외부에서 받는 걸로 수정
    public  Order createOrder(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalTime currentTime = currentDateTime.toLocalTime();
        if(currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME) ){
            throw new IllegalArgumentException("주문시간이 아닙니다.");
        }

        return new Order(currentDateTime, beverages);
    }


    public  Order createOrder(LocalDateTime currentDateTime){
        LocalTime currentTime = currentDateTime.toLocalTime();
        if(currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME) ){
            throw new IllegalArgumentException("주문시간이 아닙니다.");
        }

        return new Order(currentDateTime, beverages);
    }
}
