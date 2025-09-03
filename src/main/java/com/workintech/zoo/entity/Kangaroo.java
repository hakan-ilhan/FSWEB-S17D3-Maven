package com.workintech.zoo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Kangaroo {
    private int id;
    private String name;
    private double height;
    private double weight;
    private String gender;
    private boolean isAggressive;



    public boolean getIsAggressive() {
        return isAggressive;
    }

    public void setIsAggressive(boolean b) {
        this.isAggressive = b;
    }
}
