package com.example.itmo.extended.controllers;


import com.example.itmo.extended.model.dto.request.CarInfoReq;
import com.example.itmo.extended.model.dto.response.CarInfoResp;
import com.example.itmo.extended.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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
    public Page<CarInfoResp> getAllCar(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer perPage,
                                        @RequestParam(defaultValue = "model") String sort,
                                        @RequestParam(defaultValue = "ASC") Sort.Direction order,
                                        @RequestParam(required = false) String filter

    ) {
        return carService.getAllCar(page, perPage, sort, order, filter);
    }

    @GetMapping("/all/{userId}")
    public List<CarInfoResp> getAllCars(@PathVariable Long userId) {
        return carService.getUserCars(userId);
    }

    @PostMapping("/linkUserAndDriver/{carId}/{userId}")
    public CarInfoResp linkUserAndDriver(@PathVariable Long carId, @PathVariable Long userId) {
        return carService.linkCarAndDriver(carId, userId);
    }
}