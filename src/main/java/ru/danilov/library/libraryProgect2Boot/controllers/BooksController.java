package ru.danilov.library.libraryProgect2Boot.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.danilov.library.libraryProgect2Boot.models.Book;
import ru.danilov.library.libraryProgect2Boot.models.Person;
import ru.danilov.library.libraryProgect2Boot.services.BooksService;
import ru.danilov.library.libraryProgect2Boot.services.PeopleService;

import java.util.List;

/**
 * User: Nikolai Danilov
 * Date: 22.03.2023
 */
@Controller
@RequestMapping("/books")
public class BooksController {

    private final PeopleService peopleService;
    private final BooksService booksService;

    @Autowired
    public BooksController(PeopleService peopleService, BooksService booksService) {
        this.peopleService = peopleService;
        this.booksService = booksService;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "page", required=false) Integer page,
                        @RequestParam(value = "books_per_page", required=false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required=false) boolean sortByYear) {

        if (sortByYear) {
            if (page != null && booksPerPage != null)
                model.addAttribute("books", booksService.findAll(page, booksPerPage, true));
            else
                model.addAttribute("books", booksService.findAllSortByYear());
        } else {
            if (page != null && booksPerPage != null)
                model.addAttribute("books", booksService.findAll(page, booksPerPage));
            else
                model.addAttribute("books", booksService.findAll());
        }





        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        Book book = booksService.findOne(id);
        model.addAttribute("book", book);
        if (book.getPerson() != null) {
            Person personHasBook = book.getPerson();
            model.addAttribute("personHasBook", personHasBook);
        } else {
            List<Person> people = peopleService.findAll();
            model.addAttribute("people", people);
        }
        return "books/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        booksService.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/add")
    public String addPerson(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        Book book = booksService.findOne(id);
        book.setPerson(person);
        booksService.update(book.getId(), book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/clear")
    public String clearPerson(@PathVariable("id") int id) {
        booksService.clearPersonInBooks(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(value = "searchString", required=false) String searchString) {
        if (searchString != null)
            model.addAttribute("books", booksService.findBookByNameContains(searchString));
        else
            model.addAttribute("notFind", "Книг не найдено");

        return "books/search";
    }
}
