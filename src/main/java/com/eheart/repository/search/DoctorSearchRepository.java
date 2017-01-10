package com.eheart.repository.search;

import com.eheart.domain.Doctor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Doctor entity.
 */
public interface DoctorSearchRepository extends ElasticsearchRepository<Doctor, Long> {
}
