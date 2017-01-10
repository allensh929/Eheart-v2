package com.eheart.repository.search;

import com.eheart.domain.Domain;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Domain entity.
 */
public interface DomainSearchRepository extends ElasticsearchRepository<Domain, Long> {
}
