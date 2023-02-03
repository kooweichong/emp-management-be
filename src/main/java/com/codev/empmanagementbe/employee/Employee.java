package com.codev.empmanagementbe.employee;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table
public class Employee {
    @Id
    @SequenceGenerator(
            name = "employee_sequence",
            sequenceName = "employee_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employee_sequence"
    )
    private Long UUID;
    @Column(unique = true)
    private String id;
    private String name;
    @Column(unique = true)
    private String login;
    private Float salary;

    public Employee() {
    }

    public Employee(Long UUID, String id, String login, String name, Float salary) {
        this.UUID = UUID;
        this.id = id;
        this.login = login;
        this.name = name;
        this.salary = salary;
    }

    public Employee(String id, String login, String name, Float salary) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.salary = salary;
    }

    @JsonIgnore
    public Long getUUID() {
        return UUID;
    }

    public void setUUID(Long UUID) {
        this.UUID = UUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}
