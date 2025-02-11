package com.example.itmo.extended.service.impl;

import com.example.itmo.extended.model.db.entity.Car;
import com.example.itmo.extended.model.db.repository.CarRepository;
import com.example.itmo.extended.model.dto.request.CarInfoReq;
import com.example.itmo.extended.model.dto.response.CarInfoResp;
import com.example.itmo.extended.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final ObjectMapper mapper;
    private final CarRepository carRepository;

    @Override
    public CarInfoResp addCar(CarInfoReq request) {
        Car car = mapper.convertValue(request, Car.class);

        Car save = carRepository.save(car);
        Long id = save.getId();
        log.info("car {} added", id);
        return mapper.convertValue(save, CarInfoResp.class);
    }

    @Override
    public CarInfoResp getCar(Long id) {
        Car car = getCarFromDB(id);
        return mapper.convertValue(car, CarInfoResp.class);
    }

    private Car getCarFromDB(Long id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        return optionalCar.orElse(new Car());
    }

    @Override
    public CarInfoResp updateCar(Long id, CarInfoReq request) {
        Car carFromDB = getCarFromDB(id);
        if (carFromDB.getId() == null) {
            return mapper.convertValue(carFromDB, CarInfoResp.class);
        }

        Car carReq = mapper.convertValue(request, Car.class);

        carFromDB.setBrand(carReq.getBrand() == null ? carFromDB.getBrand() : carReq.getBrand());
        carFromDB.setModel(carReq.getModel() == null ? carFromDB.getModel() : carReq.getModel());
        carFromDB.setColor(carReq.getColor() == null ? carFromDB.getColor() : carReq.getColor());
        carFromDB.setYear(carReq.getYear() == null ? carFromDB.getYear() : carReq.getYear());
        carFromDB.setPrice(carReq.getPrice() == null ? carFromDB.getPrice() : carReq.getPrice());
        carFromDB.setIsNew(carReq.getIsNew() == null ? carFromDB.getIsNew() : carReq.getIsNew());
        carFromDB.setType(carReq.getType() == null ? carFromDB.getType() : carReq.getType());


        carFromDB = carRepository.save(carFromDB);
        return mapper.convertValue(carFromDB, CarInfoResp.class);
    }

    @Override
    public void dellCar(Long id) {
        Car carFromDB = getCarFromDB(id);
        if (carFromDB.getId() == null) {
            log.error("Car with id {} not found", id);
            return;
        }
        carRepository.deleteById(id);
    }

    @Override
    public List<CarInfoResp> getAllCar() {
        return carRepository.findAll().stream()
                .map(car -> mapper.convertValue(car, CarInfoResp.class))
                .collect(Collectors.toList());
    }
}