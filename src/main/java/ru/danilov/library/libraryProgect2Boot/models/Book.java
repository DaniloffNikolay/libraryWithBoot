package ru.danilov.library.libraryProgect2Boot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;

import java.util.Date;

/**
 * User: Nikolai Danilov
 * Date: 22.03.2023
 */
@Entity
@Table(name = "Books")
public class Book {

    @Id
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
    @Column(name = "name")
    private String name;
    @Column(name = "author")
    private String author;
    @Max(value = 2023, message = "birth year should be less than 2023")
    @Column(name = "year")
    private int year;
    @Column(name = "date_of_taking")
    private Date dateOfTaking;

    @Transient
    private boolean overdue;

    public Book() {}

    public Book(int id, Person person, String name, String author, int year) {
        this.id = id;
        this.person = person;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getDateOfTaking() {
        return dateOfTaking;
    }

    public void setDateOfTaking(Date dateOfTaking) {
        this.dateOfTaking = dateOfTaking;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue() {
        overdue = dateOfTaking.getDate() + 10 < (new Date()).getDate();
    }
}
