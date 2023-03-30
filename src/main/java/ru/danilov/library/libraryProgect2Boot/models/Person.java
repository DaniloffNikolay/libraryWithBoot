package ru.danilov.library.libraryProgect2Boot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

import java.util.List;

/**
 * User: Nikolai Danilov
 * Date: 22.03.2023
 */
@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Pattern(regexp = "[A-Z|А-Я]\\W+\\s[A-Z|А-Я]\\W+\\s[A-Z|А-Я]\\W+", message = "Your FIO should be in this format: Surname Name MiddleName")
    @Column(name = "fio")
    private String fio;
    @Min(value = 1900, message = "birth year should be greater than 1900")
    @Max(value = 2023, message = "birth year should be less than 2023")
    @Column(name = "birth_year")
    private int birthYear;

    @OneToMany(mappedBy = "person")
    private List<Book> books;
    public Person() {}

    public Person(String fio, int birth_year) {
        this.fio = fio;
        this.birthYear = birth_year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", fio='" + fio + '\'' +
                ", birthYear=" + birthYear +
                '}';
    }
}
