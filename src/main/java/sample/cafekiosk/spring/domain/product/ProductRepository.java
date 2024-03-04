package sample.cafekiosk.spring.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    /**
     * select *
     * from product
     * where selling_type in ('SELLING', 'HOLD');
     *
     */
    List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> sellingStatus);

    List<Product> findAllByProductNumberIn(List<String> productNumbers);

    // id기준으로 역순정렬했을 때 가장 상위에 있는 데이터 추출
    // 네이티브쿼리
    @Query(value = "select p.product_number from product p order by id desc limit 1", nativeQuery = true)
    String findLatestProductNumber();
}
