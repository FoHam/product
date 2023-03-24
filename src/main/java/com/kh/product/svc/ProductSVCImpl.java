package com.kh.product.svc;

import com.kh.product.dao.Product;
import com.kh.product.dao.ProductDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductSVCImpl implements ProductSVC{
  private final ProductDAO productDAO;

  /**
   * 등록
   *
   * @param product 상품
   * @return 상품아이디
   */
  @Override
  public Long add(Product product) {
    return productDAO.add(product);
  }

  /**
   * 조회
   *
   * @param pid 상품아이디
   * @return 상품
   */
  @Override
  public Optional<Product> search(Long pid) {
    return productDAO.search(pid);
  }

  /**
   * 수정
   *
   * @param pid     상품아이디
   * @param product 상품
   * @return 결과
   */
  @Override
  public int update(Long pid, Product product) {
    return productDAO.update(pid,product);
  }

  /**
   * 삭제
   *
   * @param pid 상품아이디
   * @return 결과
   */
  @Override
  public int delete(Long pid) {
    return productDAO.delete(pid);
  }

  /**
   * 전체조회
   *
   * @return 상품목록
   */
  @Override
  public List<Product> list() {
    return productDAO.list();
  }

  /**
   * 상품존재유무
   *
   * @param pid 상품아이디
   * @return
   */
  @Override
  public boolean isExist(Long pid) {
    return productDAO.isExist(pid);
  }
}
