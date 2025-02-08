package com.example.itmo.extended.service.impl;

import com.example.itmo.extended.model.dto.request.CarInfoReq;
import com.example.itmo.extended.model.dto.response.CarInfoResp;
import com.example.itmo.extended.model.enums.CarType;
import com.example.itmo.extended.model.enums.Color;
import com.example.itmo.extended.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final ObjectMapper mapper;

    @Override
    public CarInfoResp addCar(CarInfoReq request) {
        CarInfoResp carInfoResp = mapper.convertValue(request, CarInfoResp.class);
        carInfoResp.setId(1L);
        return carInfoResp;
    }

    @Override
    public CarInfoResp getCar(Long id) {
        if (id != 1L) {
            log.error("car not found, please repeat!");
            return null;
        }
        return CarInfoResp.builder()
                .id(1L)
                .brand("Lexus")
                .model("IS")
                .color(Color.BLACK)
                .year(2020)
                .price(3500000L)
                .isNew(Boolean.FALSE)
                .type(CarType.SEDAN)
                .build();
    }

    @Override
    public CarInfoResp updateCar(Long id, CarInfoReq request) {
        if (id != 1L) {
            log.error("Car {} not found", id);
            return null;
        }
        return CarInfoResp.builder()
                .id(1L)
                .brand("Toyota")
                .model("GT86")
                .color(Color.WHITE)
                .year(2018)
                .price(2400000L)
                .isNew(Boolean.FALSE)
                .type(CarType.COUPE)
                .build();
    }

    @Override
    public void dellCar(Long id) {
        if (id != 1) {
            log.error("car {} not found", id);
            return;
        }
        log.info("car {} deleted", id);
    }

    @Override
    public List<CarInfoResp> getAllCar() {
        return List.of(CarInfoResp.builder()
                .id(1L)
                .brand("Lexus")
                .model("LX")
                .color(Color.BLACK)
                .year(2024)
                .price(18000000L)
                .isNew(Boolean.TRUE)
                .type(CarType.SUV)
                .build());
    }

    @Override
    public CarInfoResp getCar(Color color, CarType type) {
        return getCar(1L);
    }
}
