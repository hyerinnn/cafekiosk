package sample.cafekiosk.spring.domain.stock;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productNumber;

    private int quantity;

    @Builder
    private Stock(String productNumber, int quantity) {
        this.productNumber = productNumber;
        this.quantity = quantity;
    }

    public static Stock create(String productNumber, int quantity){
        return Stock.builder()
                .productNumber(productNumber)
                .quantity(quantity)
                .build();
    }

    // 재고수량보다 주문상품이 많은 경우인지 확인
    public boolean isQuantityLessThen(int quantity) {
        return this.quantity < quantity;
    }

    // 재고차감
    public void deductQuantity(int quantity) {
        if(isQuantityLessThen(quantity)){
            throw new IllegalArgumentException("차감할 재고수량이 없습니다.");
        }
        this.quantity -= quantity;
    }
}
