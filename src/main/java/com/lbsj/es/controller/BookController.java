package com.lbsj.es.controller;


import com.lbsj.common.model.RequestResult;
import com.lbsj.es.entity.Book;
import com.lbsj.es.service.impl.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author geng
 * 2020/12/20
 */
@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public RequestResult addBook(@RequestBody Book book) {
        bookService.addBook(book);
        return RequestResult.e();
    }

    @GetMapping("/search")
    public SearchHits<Book> search(String key) {
        return bookService.find(key);
    }


}
