package com.lautumn.lucene.bean;

/**
 * Created by Administrator on 2017/12/28.
 */
public class Person {
    private String name;
    private String info;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Person(String name, Integer age, String info) {
        this.name = name;
        this.info = info;
        this.age = age;
    }


    public Person() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", age=" + age +
                '}';
    }
}
