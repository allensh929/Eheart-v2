package com.eheart.repository.search;

import com.eheart.domain.ThirdMenu;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ThirdMenu entity.
 */
public interface ThirdMenuSearchRepository extends ElasticsearchRepository<ThirdMenu, Long> {
}
