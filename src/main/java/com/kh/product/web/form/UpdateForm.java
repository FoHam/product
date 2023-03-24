package com.kh.product.web.form;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateForm {
  private Long pid;
  @NotBlank // null 빈문자열("")을 허용 안함, 문자열 타입에만 사용
  private String pname;
  @NotNull // 모든 타입에 대해 null 허용 안함
  @Positive //양수
  private Long quantity;
  @NotNull
  @Min(1000)
  private Long price;
//  @NotEmpty null 빈문자열("") 공백문자(" ") 허용안함
//  문자열,컬렉션타입(요소가 1개이상 존재)에 사용
}