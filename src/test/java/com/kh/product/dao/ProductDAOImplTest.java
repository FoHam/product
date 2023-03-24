package com.kh.product.dao;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductDAOImplTest {
  @Autowired
  private ProductDAO productDAO;
  private static final int COUNT = 3;
  private static List<Long> productIds = new ArrayList<>();

  @Test
  @Order(1)
  @DisplayName("등록")
  void add(){
    List<Product> products = new ArrayList<>();
    for(int i=1; i<=COUNT; i++){
      products.add(new Product(null,"밥시간"+i,1L*i,10000L*i));
    }
    products.stream().forEach(product -> productIds.add(productDAO.add(product)));

    products.stream().forEach(product -> log.info("product={}",product));
  }
  @Test
  @Order(2)
  @DisplayName("조회")
  void search(){
    int idx = 0;
    Optional<Product> searchProduct = productDAO.search(productIds.get(idx));
    Product product = searchProduct.orElseThrow();
    Assertions.assertThat(product.getPname()).isEqualTo("밥시간"+(idx+1));
    Assertions.assertThat(product.getQuantity()).isEqualTo(1L*(idx+1));
    Assertions.assertThat(product.getPrice()).isEqualTo(10000L*(idx+1));

    log.info("product={}",product);
  }
  @Test
  @Order(3)
  @DisplayName("수정")
  void update(){
    int idx = ProductDAOImplTest.COUNT-1;
    Product product = new Product(null,"밥시간_수정",9L,90000L);
    int updatedRowCnt = productDAO.update(productIds.get(idx), product);

    Assertions.assertThat(updatedRowCnt).isEqualTo(1);
    Optional<Product> searchProduct = productDAO.search(productIds.get(idx));
    Product p = searchProduct.orElseThrow();
    Assertions.assertThat(p.getPname()).isEqualTo("밥시간_수정");
    Assertions.assertThat(p.getQuantity()).isEqualTo(9L);
    Assertions.assertThat(p.getPrice()).isEqualTo(90000L);

    log.info("p={}",p);
  }
  @Test
  @Order(4)
  @DisplayName("전체조회")
  void list(){
    List<Product> list = productDAO.list();
    list.stream().forEach(product -> log.info("product={}",product));
    Assertions.assertThat(list.size()).isEqualTo(ProductDAOImplTest.COUNT);
  }
  @Test
  @Order(5)
  @DisplayName("삭제")
  void delete(){
    int idx = 0;
    int deleteRowCnt = productDAO.delete(productIds.get(idx));
    Assertions.assertThat(deleteRowCnt).isEqualTo(1);

    boolean exist = productDAO.isExist(productIds.get(idx));
    Assertions.assertThat(exist).isFalse();

    List<Product> list = productDAO.list();
    list.stream().forEach(product -> log.info("product={}",product));
    Assertions.assertThat(list.size()).isEqualTo(ProductDAOImplTest.COUNT-1);
  }
}
