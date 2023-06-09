package com.kh.product.web;

import com.kh.product.dao.Product;
import com.kh.product.svc.ProductSVC;
import com.kh.product.web.form.AddForm;
import com.kh.product.web.form.DetailForm;
import com.kh.product.web.form.UpdateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor  // final멤버 필드를 매개값으로하는 생성자를 자동 생성
public class ProductController {
  private final ProductSVC productSVC;

  //등록양식
  @GetMapping("/add")
  public String addForm(Model model){

    AddForm addForm = new AddForm();
    model.addAttribute("form",addForm);

    return "product/addForm";
  }

  //등록처리
  @PostMapping("/add")
  public String add(

      @Valid @ModelAttribute("form") AddForm addForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ){
    log.info("addForm={}",addForm);

    //어노테이션 기반 검증
    if(bindingResult.hasErrors()){
      log.info("bindingResult={}",bindingResult);
      return "product/addForm";
    }



    // 글로벌오류
    // 총액(상품수량*단가) 1억 이상 금지
    if(addForm.getQuantity() * addForm.getPrice() >= 100_000_000L){
      bindingResult.reject("",null,"총액(상품수량*단가) 1억원 이상 할 수 없습니다!");
    }


    if(bindingResult.hasErrors()){
      log.info("bindingResult={}",bindingResult);
      return "product/addForm";
    }
    //등록
    Product product = new Product();
    product.setPname(addForm.getPname());
    product.setQuantity(addForm.getQuantity());
    product.setPrice(addForm.getPrice());

    Long savedProductId = productSVC.add(product);
    redirectAttributes.addAttribute("id",savedProductId);
    return "redirect:/products/{id}/detail";
  }

  //조회
  @GetMapping("/{id}/detail")
  public String findById(
      @PathVariable("id") Long id,
      Model model
  ){
    Optional<Product> searchProduct = productSVC.search(id);
    Product product = searchProduct.orElseThrow();

    DetailForm detailForm = new DetailForm();
    detailForm.setPid(product.getPid());
    detailForm.setPname(product.getPname());
    detailForm.setQuantity(product.getQuantity());
    detailForm.setPrice(product.getPrice());

    model.addAttribute("form",detailForm);
    return "product/detailForm";
  }

  //수정양식
  @GetMapping("/{id}/edit")
  public String updateForm(
      @PathVariable("id") Long id,
      Model model
  ){
    Optional<Product> searchProduct = productSVC.search(id);
    Product product = searchProduct.orElseThrow();

    UpdateForm updateForm = new UpdateForm();
    updateForm.setPid(product.getPid());
    updateForm.setPname(product.getPname());
    updateForm.setQuantity(product.getQuantity());
    updateForm.setPrice(product.getPrice());

    model.addAttribute("form",updateForm);
    return "product/updateForm";
  }

  //수정
  @PostMapping("/{id}/edit")
  public String update(
      @PathVariable("id") Long id,
      @Valid @ModelAttribute("form") UpdateForm updateForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ){

    //데이터 검증
    if(bindingResult.hasErrors()){
      log.info("bindingResult={}",bindingResult);
      return "product/updateForm";
    }

    Product product = new Product();
    product.setPid(id);
    product.setPname(updateForm.getPname());
    product.setQuantity(updateForm.getQuantity());
    product.setPrice(updateForm.getPrice());

    productSVC.update(id,product);

    redirectAttributes.addAttribute("id",id);
    return "redirect:/products/{id}/detail";
  }

  //삭제
  @GetMapping("/{id}/del")
  public String deleteById(@PathVariable("id") Long id){

    productSVC.delete(id);

    return "redirect:/products";
  }

  //목록
  @GetMapping
  public String list(Model model){

    List<Product> products = productSVC.list();
    model.addAttribute("products",products);

    return "product/list";
  }

}
