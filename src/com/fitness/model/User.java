package com.fitness.model;

public class User extends Person {
    private String password;
    private int age;
    private String goal;

    public User() {
    }

    public User(int id, String fullName, String email, String password, int age, String goal) {
        super(id, fullName, email);
        this.password = password;
        this.age = age;
        this.goal = goal;
    }

    public User(String fullName, String email, String password, int age, String goal) {
        super(0, fullName, email);
        this.password = password;
        this.age = age;
        this.goal = goal;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    @Override
    public String getRoleDescription() {
        return "Fitness user";
    }
}
