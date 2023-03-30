package ru.danilov.library.libraryProgect2Boot.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.danilov.library.libraryProgect2Boot.models.Book;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findBookByPersonId(int id);
    List<Book> findBookByNameContains(String name);

}
