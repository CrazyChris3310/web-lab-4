package com.example.lab4back.data;

import com.example.lab4back.beans.PointData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PointDataRepository {

    private final List<PointData> points = new ArrayList<>();

    public List<PointData> findAll() {
        return points;
    }

    public void addPoint(PointData point) {
        points.add(point);
    }
}
