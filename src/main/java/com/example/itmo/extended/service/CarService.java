package com.example.itmo.extended.service;

import com.example.itmo.extended.model.dto.request.CarInfoReq;
import com.example.itmo.extended.model.dto.response.CarInfoResp;
import com.example.itmo.extended.model.enums.CarType;
import com.example.itmo.extended.model.enums.Color;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarService {
    CarInfoResp getCar(Long id);
    CarInfoResp addCar(CarInfoReq request);
    CarInfoResp updateCar(Long id, CarInfoReq request);
    void dellCar(Long id);
    List<CarInfoResp> getAllCar();
}
