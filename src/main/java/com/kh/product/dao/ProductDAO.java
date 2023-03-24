package com.kh.product.dao;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {
  /**
   * 등록
   * @param product 상품
   * @return 상품아이디
   */
  Long add(Product product);

  /**
   * 조회
   * @param pid 상품아이디
   * @return 상품
   */
  Optional<Product> search(Long pid);

  /**
   * 수정
   * @param pid 상품아이디
   * @param product 상품
   * @return 결과
   */
  int update(Long pid,Product product);

  /**
   * 삭제
   * @param pid 상품아이디
   * @return 결과
   */
  int delete(Long pid);

  /**
   * 전체조회
   * @return 상품목록
   */
  List<Product> list();

  /**
   * 상품존재유무
   * @param pid 상품아이디
   * @return
   */
  boolean isExist(Long pid);
}
