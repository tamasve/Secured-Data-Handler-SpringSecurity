package com.secureddatahandlerspringbe.repository;

import com.secureddatahandlerspringbe.entity.Book;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends ListCrudRepository<Book, Long> {

}
