package com.lbsj.es.service;

import com.lbsj.es.entity.Book;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ESBookRepository extends ElasticsearchRepository<Book, String> {

    List<Book> findByName(String name);

    @Highlight(fields = {
            @HighlightField(name = "title")
    })
    @Query("{\"match\":{\"name\":\"?0\"}}")
    SearchHits<Book> find(String keyword);
}
