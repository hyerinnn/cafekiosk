package sample.cafekiosk.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 키오스크에 노출할 상품 리스트
    public List<ProductResponse> getSellingProducts(){
        List<Product> products = productRepository.findBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
                //.map(product -> ProductResponse.of(product)) //아래 코드로 간소화
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

}
