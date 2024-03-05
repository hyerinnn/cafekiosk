package sample.cafekiosk.spring.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.controller.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
        String nextProductNumber = createNextProductNumber();
        Product product = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);
        return ProductResponse.of(savedProduct);
    }

    private String createNextProductNumber(){
        String latestProductNumber = productRepository.findLatestProductNumber();
        if(latestProductNumber == null){
            return "001";
        }
        int  latestProductNumberInt =  Integer.parseInt(latestProductNumber);
        int nextProductNumberInt = latestProductNumberInt + 1;
        //왼쪽이 0으로 채워지는.
        return String.format("%03d", nextProductNumberInt);
    }

    // 키오스크에 노출할 상품 리스트
    public List<ProductResponse> getSellingProducts(){
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
                //.map(product -> ProductResponse.of(product)) //아래 코드로 간소화
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

}
