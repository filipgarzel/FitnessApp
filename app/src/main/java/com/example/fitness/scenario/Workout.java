package com.example.fitness.scenario;

public class Workout {
    String workOutName;
    String description;
    //String days;
    int level;
    int reps;
    int series;

    public Workout(String workOutName, int level, int reps, int series) {
        this.workOutName = workOutName;
        //this.description = description;
        this.level = level;
        this.reps = reps;
        this.series = series;
        //this.days = days;
    }

    public String getWorkOutName() {
        return workOutName;
    }

    public void setWorkOutName(String workOutName) {
        this.workOutName = workOutName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //public String getDays() {
     //   return days;
    //}

   // public void setDays(String days) {
     //   this.days = days;
    //}
}
