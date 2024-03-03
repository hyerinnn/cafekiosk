package sample.cafekiosk.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverage.Americano;
import sample.cafekiosk.unit.beverage.Latte;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class CafeKioskTest {

    @Test
    void add_basic_test(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());
        System.out.println("음료 수: " + cafeKiosk.getBeverages().size());
    }

    @Test
    @DisplayName("음료 1개를 추가하면 주문목록에 담긴다.")
    void add(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());
        assertThat(cafeKiosk.getBeverages().size()).isEqualTo(1);
        assertThat(cafeKiosk.getBeverages()).hasSize(1);
        assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    void addSeveralBeverages(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano, 2);

        assertThat(cafeKiosk.getBeverages()).hasSize(2);
        assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);
    }

    @Test
    void addZeroBeverages(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        assertThatThrownBy(()-> cafeKiosk.add(americano,0))
                .isInstanceOf(IllegalArgumentException.class)   // 주어진 상황에서 이 오류가 발생하는가
                .hasMessage("음료는 1잔 이상 주문하실 수 있습니다");  // 어떤 메세지를 던지는 예외인가
    }

    @Test
    void remove(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);

        assertThat(cafeKiosk.getBeverages()).hasSize(1);

        cafeKiosk.remove(americano);
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @Test
    void createOrder(){
        //given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);

        //when
        Order order = cafeKiosk.createOrder();

        //then
        assertThat(order.getBeverages()).hasSize(1);
    }

    @Test
    void createOrderWithCurrentTime(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);

        //Order order = cafeKiosk.createOrder(LocalDateTime.of(2023,3,3,10,0));
        Order order = cafeKiosk.createOrder(LocalDateTime.of(2023,3,3,22,0));

        assertThat(order.getBeverages()).hasSize(1);
    }

    @Test
    void createOrderWithOutsideOpenTime(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);

        assertThatThrownBy(()->cafeKiosk.createOrder(LocalDateTime.of(2023,3,3,23,0)))
                .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("주문시간이 아닙니다.");
    }

}