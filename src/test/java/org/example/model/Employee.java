package org.example.model;

public class Employee {

    String title;
    double salary;

    public Employee(String title, double salary) {
        this.title = title;
        this.salary = salary;
    }

    public String getTitle() {
        return title;
    }

    public double getSalary() {
        return salary;
    }
}
