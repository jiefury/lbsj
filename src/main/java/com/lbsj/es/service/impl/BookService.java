package com.lbsj.es.service.impl;

import com.lbsj.es.entity.Book;
import com.lbsj.es.service.ESBookRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class BookService {
    private final ESBookRepository esBookRepository;
//    private final TransactionTemplate transactionTemplate;

    @Autowired
    public BookService(
            ESBookRepository esBookRepository) {
        this.esBookRepository = esBookRepository;
    }

    public void addBook(Book book) {
//        final Book saveBook = transactionTemplate.execute((status) ->
//                esBookRepository.save(book)
//        );
//        assert saveBook != null;
//        Book esBook = new Book();
//        BeanUtils.copyProperties(saveBook, esBook);
//        esBook.setId(saveBook.getId());
        try {
            book.setCreateTime(new Date());
            book.setUpdateTime(new Date());
            Book save = esBookRepository.save(book);
        } catch (Exception e) {
            log.error(String.format("保存ES错误！%s", e.getMessage()));
        }
    }

    public List<Book> searchBook(String keyword) {
        return esBookRepository.findByName(keyword);
    }

    public SearchHits<Book> find(String keyword) {
        return esBookRepository.find(keyword);
    }
}
