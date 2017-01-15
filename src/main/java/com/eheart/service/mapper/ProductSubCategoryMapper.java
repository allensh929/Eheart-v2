package com.eheart.service.mapper;

import com.eheart.domain.*;
import com.eheart.service.dto.ProductSubCategoryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ProductSubCategory and its DTO ProductSubCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductCategoryMapper.class})
public interface ProductSubCategoryMapper {

    @Mapping(source = "superCategory.id", target = "superCategoryId")
    ProductSubCategoryDTO productSubCategoryToProductSubCategoryDTO(ProductSubCategory productSubCategory);

    List<ProductSubCategoryDTO> productSubCategoriesToProductSubCategoryDTOs(List<ProductSubCategory> productSubCategories);

    @Mapping(source = "superCategoryId", target = "superCategory")
    ProductSubCategory productSubCategoryDTOToProductSubCategory(ProductSubCategoryDTO productSubCategoryDTO);

    List<ProductSubCategory> productSubCategoryDTOsToProductSubCategories(List<ProductSubCategoryDTO> productSubCategoryDTOs);

    default ProductCategory productCategoryFromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(id);
        return productCategory;
    }
}
