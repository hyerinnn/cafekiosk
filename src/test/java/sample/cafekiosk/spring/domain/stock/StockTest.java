package sample.cafekiosk.spring.domain.stock;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class StockTest {

    @DisplayName("재고수량이 주문상품보다 적은지 확인한다.")
    @Test
    void isQuantityLessThen() {
        // given
        Stock stock = Stock.create("001",1);
        int quantity = 2;

        // when
        boolean result = stock.isQuantityLessThen(quantity);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("주문수량만큼 재고를 차감한다.")
    @Test
    void deductQuantity() {
        // given
        Stock stock = Stock.create("001",2);
        int quantity = 2;

        // when
        stock.deductQuantity(quantity);

        // then
        assertThat(stock.getQuantity()).isZero();
    }

    @DisplayName("재고수량보다 많은 수의 수량으로 차감을 시도하는 경우 예외가 발생한다.")
    @Test
    void deductQuantity2() {
        // given
        Stock stock = Stock.create("001",1);
        int quantity = 2;

        // when
        assertThatThrownBy(()-> stock.deductQuantity(quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("차감할 재고수량이 없습니다.");
    }
}