package com.eheart.repository.search;

import com.eheart.domain.Clinic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Clinic entity.
 */
public interface ClinicSearchRepository extends ElasticsearchRepository<Clinic, Long> {
}
