package com.eheart.service.mapper;

import com.eheart.domain.*;
import com.eheart.service.dto.ProductCategoryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ProductCategory and its DTO ProductCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductCategoryMapper {

    ProductCategoryDTO productCategoryToProductCategoryDTO(ProductCategory productCategory);

    List<ProductCategoryDTO> productCategoriesToProductCategoryDTOs(List<ProductCategory> productCategories);

    @Mapping(target = "hasProducts", ignore = true)
    ProductCategory productCategoryDTOToProductCategory(ProductCategoryDTO productCategoryDTO);

    List<ProductCategory> productCategoryDTOsToProductCategories(List<ProductCategoryDTO> productCategoryDTOs);
}
