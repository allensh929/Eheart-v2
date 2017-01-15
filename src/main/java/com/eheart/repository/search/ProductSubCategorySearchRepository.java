package com.eheart.repository.search;

import com.eheart.domain.ProductSubCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ProductSubCategory entity.
 */
public interface ProductSubCategorySearchRepository extends ElasticsearchRepository<ProductSubCategory, Long> {
}
