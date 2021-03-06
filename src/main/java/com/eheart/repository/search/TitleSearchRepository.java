package com.eheart.repository.search;

import com.eheart.domain.Title;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Title entity.
 */
public interface TitleSearchRepository extends ElasticsearchRepository<Title, Long> {
}
