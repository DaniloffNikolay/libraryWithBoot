package ru.danilov.library.libraryProgect2Boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilov.library.libraryProgect2Boot.models.Book;
import ru.danilov.library.libraryProgect2Boot.repositories.BooksRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public List<Book> findAll(int page, int searchString) {
        return booksRepository.findAll(PageRequest.of(page, searchString)).getContent();
    }

    public List<Book> findAll(int page, int searchString, boolean sortByYear) {
        return booksRepository.findAll(PageRequest.of(page, searchString, Sort.by("year"))).getContent();
    }

    public List<Book> findAllSortByYear() {
        return booksRepository.findAll(Sort.by("year"));
    }

    public Book findOne(int id) {
        Optional<Book> optionalBook = booksRepository.findById(id);
        return optionalBook.orElse(null);
    }

    public List<Book> findBookByNameContains(String name) {
        return booksRepository.findBookByNameContains(name);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        book.setId(id);
        booksRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void clearPersonInBooks(int id) {
        List<Book> books = booksRepository.findBookByPersonId(id);
        for (Book book : books) {
            book.setPerson(null);
        }
    }
}
