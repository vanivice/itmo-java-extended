
package com.example.itmo.extended.controllers;


import com.example.itmo.extended.model.dto.request.CarInfoReq;
import com.example.itmo.extended.model.dto.response.CarInfoResp;
import com.example.itmo.extended.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/car")
@Tag(name = "Автомобили")
public class CarController {
    private final CarService carService;

    @GetMapping("/{id}")
    @Operation(summary = "Получить авто по ID")
    public CarInfoResp getCar(@PathVariable Long id) {
        return carService.getCar(id);
    }

    @PostMapping
    @Operation(summary = "Добавить новое авто")
    public CarInfoResp addCar(@RequestBody @Valid CarInfoReq request) {
        return carService.addCar(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные авто по ID")
    public CarInfoResp updateCar(@PathVariable Long id, @RequestBody CarInfoReq request) {
        return carService.updateCar(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить авто по ID")
    public void dellCar(@PathVariable Long id) {
        carService.dellCar(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Получить список всех авто")
    public Page<CarInfoResp> getAllCar(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer perPage,
                                       @RequestParam(defaultValue = "model") String sort,
                                       @RequestParam(defaultValue = "ASC") Sort.Direction order,
                                       @RequestParam(required = false) String filter

    ) {
        return carService.getAllCar(page, perPage, sort, order, filter);
    }

    @GetMapping("/all/{userId}")
    @Operation(summary = "Получить список авто по ID пользователя")
    public List<CarInfoResp> getAllCars(@PathVariable Long userId) {
        return carService.getUserCars(userId);
    }

    @PostMapping("/linkUserAndDriver/{carId}/{userId}")
    @Operation(summary = "Закрепить авто за пользователем по ID")
    public CarInfoResp linkUserAndDriver(@PathVariable Long carId, @PathVariable Long userId) {
        return carService.linkCarAndDriver(carId, userId);
    }
}
