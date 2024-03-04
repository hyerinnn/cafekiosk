package sample.cafekiosk.spring.domain.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sample.cafekiosk.spring.domain.order.OrderProduct;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    // 상품번호 리스트로 재고 조회
   List<Stock> findAllByProductNumberIn(List<String> productNumbers);

}