package com.example.lab4back.beans;

import lombok.Data;

import java.util.Date;

@Data
public class PointData {

    private Double x;
    private Double y;
    private Double radius;
    private Date date;
    private String duration;
    private boolean hit;
}
