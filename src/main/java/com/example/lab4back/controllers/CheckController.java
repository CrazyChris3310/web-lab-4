package com.example.lab4back.controllers;

import com.example.lab4back.beans.PointData;
import com.example.lab4back.data.PointDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class CheckController {

    private final PointDataRepository pointDataRepo;

    public CheckController(PointDataRepository pointDataRepo) {
        this.pointDataRepo = pointDataRepo;
    }

    @CrossOrigin
    @GetMapping("/home/points")
    public List<PointData> getAllPoints() {
        return pointDataRepo.findAll();
    }

    @CrossOrigin
    @PostMapping("/home/points/save")
    public PointData checkHit(@RequestBody PointData point) {
        long start = System.nanoTime();
        point.setDate(new Date());

        if (point.getX() > 0) {
                point.setHit(point.getY() <= point.getRadius() / 2 - point.getX() && point.getY() >= 0);
        } else {
                point.setHit(point.getY() <= 0 && point.getY() >= -point.getRadius()
                        && point.getX() >= -point.getRadius() ||
                        point.getY() * point.getY() + point.getX() * point.getX() <=
                                point.getRadius() * point.getRadius() / 4);
        }
        pointDataRepo.addPoint(point);
        point.setDuration((System.nanoTime() - start) / 1000 + " мс");
        log.info(point.toString());
        return point;
    }

}
