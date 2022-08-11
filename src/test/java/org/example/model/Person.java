package org.example.model;

import java.util.Collection;

public class Person {
    private int age;
    private String lastName;

    public Person(int age, String name) {
        this.age = age;
        this.lastName = name;
    }

    public int getAge() {
        System.out.println(this);
        return age;
    }

    public String getLastName() {
        System.out.println(this);
        return lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}