package com.example.ranklist;

import java.util.Comparator;

public class Student {
    private String name;
    private Integer marks;
    private Integer rank;

    public Student() {
        name = " ";
        marks = 0;
        rank = -1;
    }

    public Student(String name, Integer marks, Integer rank) {
        this.name = name;
        this.marks = marks;
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }


    //if (this.getName().compareTo(student.getName())>1)
    //  result = 1;
    //else if (this.getName().compareTo(student.getName())<1)
    //  result = -1;

}