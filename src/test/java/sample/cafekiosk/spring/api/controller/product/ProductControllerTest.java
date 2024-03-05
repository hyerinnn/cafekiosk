package sample.cafekiosk.spring.api.controller.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sample.cafekiosk.spring.api.controller.ProductController;
import sample.cafekiosk.spring.api.controller.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.ProductService;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class) //컨트롤러 관련 빈들만 등록해서 테스트함
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean   //ProductService에 대한 가짜 빈을 등록해줌
    private ProductService productService;


    @DisplayName("신규 상품을 등록한다.")
    @Test
    void createProduct() throws Exception {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        // when & then
        mockMvc.perform(
                post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }


}