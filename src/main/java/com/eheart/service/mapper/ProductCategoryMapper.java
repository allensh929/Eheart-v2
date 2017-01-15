package com.eheart.service.mapper;

import com.eheart.domain.ProductCategory;
import com.eheart.service.dto.ProductCategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity ProductCategory and its DTO ProductCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductCategoryMapper {

    @Mapping(target = "hasProducts", ignore = true)
    ProductCategoryDTO productCategoryToProductCategoryDTO(ProductCategory productCategory);

    List<ProductCategoryDTO> productCategoriesToProductCategoryDTOs(List<ProductCategory> productCategories);

//    @Mapping(target = "hasSubCategories")
    @Mapping(target = "hasProducts", ignore = true)
    ProductCategory productCategoryDTOToProductCategory(ProductCategoryDTO productCategoryDTO);

    List<ProductCategory> productCategoryDTOsToProductCategories(List<ProductCategoryDTO> productCategoryDTOs);
}
