package com.eheart.repository.search;

import com.eheart.domain.Hospital;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Hospital entity.
 */
public interface HospitalSearchRepository extends ElasticsearchRepository<Hospital, Long> {
}
