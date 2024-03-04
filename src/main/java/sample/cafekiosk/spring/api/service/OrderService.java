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
import sample.cafekiosk.spring.domain.product.ProductType;
import sample.cafekiosk.spring.domain.stock.Stock;
import sample.cafekiosk.spring.domain.stock.StockRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();
        List<Product> products = findProductsBy(productNumbers);

        //재고관련 로직
        //1. 주문상품 중 재고 차감 체크가 필요한 상품들 filter
        List<String> stockProductNumbers = products.stream()
                .filter(product -> ProductType.containsStockType(product.getType()))
                .map(product -> product.getProductNumber())
                .collect(Collectors.toList());

        //2. 위 목록의 각 재고를 조회
        List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);
        //아래 for문때문에 list를 map으로 가공(list를 순회하는 것 보다 map을 순회해서 차감하는게 빠르므로)
        Map<String, Stock> stockMap = stocks.stream().collect(Collectors.toMap(Stock::getProductNumber, s->s));

        //3. 1번 목록의 상품별 수량 조회(2번에서 중복 상품이 생략되므로)
        Map<String, Long> productCountingMap = stockProductNumbers.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));

        //4. 재고차감시도
        for (String stockProductNumber : stockProductNumbers){
            Stock stock = stockMap.get(stockProductNumber);
            int quantity = productCountingMap.get(stockProductNumber).intValue();
            //재고수량보다 주문상품이 많은 경우
            if(stock.isQuantityLessThen(quantity)){
                throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
            }
            //재고차감
            stock.deductQuantity(quantity);
        }

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