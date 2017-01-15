package com.eheart.service.mapper;

import com.eheart.domain.ProductCategory;
import com.eheart.service.dto.ProductCategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity ProductCategory and its DTO ProductCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface ProductCategoryMapper {

    ProductCategoryDTO productCategoryToProductCategoryDTO(ProductCategory productCategory);

    List<ProductCategoryDTO> productCategoriesToProductCategoryDTOs(List<ProductCategory> productCategories);

    @Mapping(target = "hasProducts")
    ProductCategory productCategoryDTOToProductCategory(ProductCategoryDTO productCategoryDTO);

    List<ProductCategory> productCategoryDTOsToProductCategories(List<ProductCategoryDTO> productCategoryDTOs);
}
