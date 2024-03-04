package sample.cafekiosk.spring.domain.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.awt.*;

import static org.assertj.core.api.Assertions.*;


class ProductTypeTest {

    @DisplayName("상품타입이 재고관련 타입이 아닌지를 체크한다.")
    @Test
    void containsStockType() {
        // given
        ProductType givenType = ProductType.HANDMADE;

        // when
        boolean result = ProductType.containsStockType(givenType);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("상품타입이 재고관련 타입이 맞는지 체크한다.")
    @Test
    void containsStockTypeTrue() {
        // given
        ProductType givenType = ProductType.BOTTLE;

        // when
        boolean result = ProductType.containsStockType(givenType);

        // then
        assertThat(result).isTrue();
    }
}