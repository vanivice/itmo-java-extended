package com.example.itmo.extended.controllers;


import com.example.itmo.extended.model.dto.request.CarInfoReq;
import com.example.itmo.extended.model.dto.response.CarInfoResp;
import com.example.itmo.extended.model.enums.CarType;
import com.example.itmo.extended.model.enums.Color;
import com.example.itmo.extended.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/car")
public class CarController {
    private final CarService carService;

    @GetMapping("/{id}")
    public CarInfoResp getCar(@PathVariable Long id) {
        return carService.getCar(id);
    }

    @PostMapping
    public CarInfoResp addCar(@RequestBody CarInfoReq request) {
        return carService.addCar(request);
    }

    @PutMapping("/{id}")
    public CarInfoResp updateCar(@PathVariable Long id, @RequestBody CarInfoReq request) {
        return carService.updateCar(id, request);
    }

    @DeleteMapping("/{id}")
    public void dellCar(@PathVariable Long id) {
        carService.dellCar(id);
    }

    @GetMapping("/all")
    public List<CarInfoResp> getAllUsers() {
        return carService.getAllCar();
    }

    @GetMapping
    public CarInfoResp getCarWithParams(@RequestParam(required = false) Color color,
                                        @RequestParam(required = false) CarType type) {
        return carService.getCar(color, type);
    }
}