package wotc.data;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import wotc.models.CardSearch;


public interface CardSearchRepository extends ElasticsearchRepository<CardSearch, Integer> {

}
