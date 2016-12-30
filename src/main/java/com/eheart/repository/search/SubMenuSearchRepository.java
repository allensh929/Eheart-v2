package com.eheart.repository.search;

import com.eheart.domain.SubMenu;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SubMenu entity.
 */
public interface SubMenuSearchRepository extends ElasticsearchRepository<SubMenu, Long> {
}
