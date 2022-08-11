package org.example.model;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (age != person.age) return false;
        return lastName != null ? lastName.equals(person.lastName) : person.lastName == null;
    }

    @Override
    public int hashCode() {
        int result = age;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }
}