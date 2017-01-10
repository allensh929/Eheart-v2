package com.eheart.repository.search;

import com.eheart.domain.Department;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Department entity.
 */
public interface DepartmentSearchRepository extends ElasticsearchRepository<Department, Long> {
}
