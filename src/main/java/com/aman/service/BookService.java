package com.aman.service;

import com.aman.dto.BookDto;
import com.aman.exception.ResourceNotFoundException;
import com.aman.model.Book;
import com.aman.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book createBook(BookDto bookDto) {
        Book book = Book.builder().title(bookDto.getTitle()).genre(bookDto.getGenre())
                .author(bookDto.getAuthor()).availabilityStatus(bookDto.getAvailabilityStatus()).build();
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, BookDto bookDto) {
        Book book = Book.builder().title(bookDto.getTitle()).author(bookDto.getAuthor()).genre(bookDto.getGenre()).availabilityStatus(bookDto.getAvailabilityStatus())
                .build();
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Book doesn't exist !"));
        bookRepository.delete(book);
    }
}
