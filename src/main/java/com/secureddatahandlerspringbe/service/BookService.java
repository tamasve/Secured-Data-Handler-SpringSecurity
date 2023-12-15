package com.secureddatahandlerspringbe.service;

import com.secureddatahandlerspringbe.repository.Book;
import com.secureddatahandlerspringbe.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
