package com.costcommission.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubCategoryDTO extends BaseDTO{

    private String name;
    private Integer subCategorySeq;
    private Long categoryId;
    private String categoryName;

}
