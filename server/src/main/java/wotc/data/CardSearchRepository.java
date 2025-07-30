package wotc.data;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import wotc.models.CardSearch;

import java.util.List;

public interface CardSearchRepository extends ElasticsearchRepository<CardSearch, String> {
}
