package sample.cafekiosk.spring.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sample.cafekiosk.spring.api.service.reqeust.OrderCreateRequest;
import sample.cafekiosk.spring.api.service.response.OrderResponse;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();

        List<Product> products = findProductsBy(productNumbers);

        //Order order = new Order() 이런식으로 하지말고, 빌더패턴처럼 Order에 create 메소드에서 생성자를 호출하는 것으로 작업
        Order order = Order.create(products, registeredDateTime);
        Order savedOrder = orderRepository.save(order);
        return OrderResponse.of(savedOrder);
    }

    // 중복상품을 위한 로직
    private List<Product> findProductsBy(List<String> productNumbers) {
        //findAllByProductNumberIn 절이 where in절로 조회를 하게 되므로, 중복된 아이템인 경우 중복제거가 되어버려서 수량이 1로 되는 문제발생
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
        // 그래서 아래코드들 추가.

        //1. product의 넘버로 product를 빨리 찾을 수 있는 map 추가
        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductNumber, p -> p));

        //2. request로 받은 productNumber들을 순회하면서 productMap에서 product를 찾아서 collecting
        List<Product> duplicateProducts = productNumbers.stream()
                //.map(productNumber -> productMap.get(productNumber)) // 아래코드로 단순화
                .map(productMap::get)
                .collect(Collectors.toList());
        return duplicateProducts;
    }
}
