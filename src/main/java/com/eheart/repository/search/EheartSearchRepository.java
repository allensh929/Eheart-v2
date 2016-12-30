package com.eheart.repository.search;

import com.eheart.domain.Eheart;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Eheart entity.
 */
public interface EheartSearchRepository extends ElasticsearchRepository<Eheart, Long> {
}
