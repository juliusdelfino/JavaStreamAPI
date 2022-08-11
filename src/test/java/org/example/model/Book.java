package org.example.model;

public class Book {
    String name;

    public String getName() {
        return name;
    }

    public Book(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                '}';
    }
}