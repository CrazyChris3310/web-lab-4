package com.example.lab4back.controllers;

import com.example.lab4back.beans.PointData;
import com.example.lab4back.data.PointDataRepository;
import com.example.lab4back.utils.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@Slf4j
@RequestMapping("/api")
public class CheckController {

    private final PointDataRepository pointDataRepo;

    public CheckController(PointDataRepository pointDataRepo) {
        this.pointDataRepo = pointDataRepo;
    }

    @CrossOrigin
    @PostMapping("/login")
    public void loging() {}  // needed just to enable cors on /api/login URL

    @CrossOrigin
    @GetMapping("/points")
    public List<PointData> getAllPoints(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);
        String username = Utilities.decodeUsername(header);
        return pointDataRepo.findAllByUsername(username);
    }

    @CrossOrigin
    @PostMapping("/points/clear")
    public void clearTable(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);
        String username = Utilities.decodeUsername(header);

        pointDataRepo.deleteByUsername(username);
    }

    @CrossOrigin
    @PostMapping("/points/check")
    public PointData checkHit(@RequestBody PointData point, HttpServletRequest request) {
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

        String header = request.getHeader(AUTHORIZATION);
        String username = Utilities.decodeUsername(header);
        point.setUsername(username);
        point.setDuration((System.nanoTime() - start) / 1000 + " мкс");
        pointDataRepo.save(point);
        log.info(point.toString());
        return point;
    }

}
