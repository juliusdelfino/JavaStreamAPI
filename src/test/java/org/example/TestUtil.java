package org.example;

import org.example.model.Book;
import org.example.model.Employee;
import org.example.model.Person;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestUtil {
    public List<Person> generateGroupOfPeople() {
        List<String> names = Arrays.asList(
                "Liam","Noah","Oliver","Elijah","James","William","Benjamin","Lucas","Henry","Theodore");
        List<Integer> ages = Arrays.asList(34,22,63,21,12,16,37,25,28,15);
        return IntStream.range(0, names.size())
                .mapToObj(i -> new Person(ages.get(i), names.get(i)))
                .collect(Collectors.toList());
    }


    public <I, T, E extends SQLException> Function<I, T> unchecked(Function_WithExceptions<I, T, E> func) {
        return t -> {
            try {
                return func.apply(t);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    public <E extends Exception> Runnable unchecked(Runnable_WithException<E> func) {
        return () -> {
            try {
                func.run();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    public Stream<Book> generateStreamOfBooks() {
        return Stream.of(new Book("Clean Code"), new Book("Refactoring"), new Book("TDD"));
    }

    public List<Employee> generateEmployeeList() {
        return Arrays.asList(new Employee("Dev", 10), new Employee("Dev", 23),
                new Employee("Manager", 15), new Employee("Manager", 51),
                new Employee("HR", 61));
    }
}

@FunctionalInterface
interface Function_WithExceptions<T, R, E extends Exception> {
    R apply(T t) throws E;
}

@FunctionalInterface
interface Runnable_WithException<E extends Exception> {
    void run() throws E;
}