package com.example.itmo.extended.service;

import com.example.itmo.extended.exception.CommonBackendException;
import com.example.itmo.extended.model.db.entity.Car;
import com.example.itmo.extended.model.db.entity.User;
import com.example.itmo.extended.model.db.repository.CarRepository;
import com.example.itmo.extended.model.dto.request.CarInfoReq;
import com.example.itmo.extended.model.dto.response.CarInfoResp;
import com.example.itmo.extended.model.dto.response.UserInfoResp;
import com.example.itmo.extended.model.enums.CarStat;
import com.example.itmo.extended.utils.PaginationUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {
    private final ObjectMapper mapper;
    private final CarRepository carRepository;
    private final UserService userService;

    public CarInfoResp addCar(CarInfoReq request) {
        carRepository.findByModelAndYearAndPrice(request.getModel(), request.getYear(), request.getPrice())
                .ifPresent(car -> {
                    throw new CommonBackendException("Car already exists", HttpStatus.CONFLICT);
                });

        Car car = mapper.convertValue(request, Car.class);

        Car save = carRepository.save(car);
        Long id = save.getId();
        log.info("car {} added", id);
        return mapper.convertValue(save, CarInfoResp.class);
    }

    public CarInfoResp getCar(Long id) {
        Car car = getCarFromDB(id);
        return mapper.convertValue(car, CarInfoResp.class);
    }

    private Car getCarFromDB(Long id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        final String errMsg = String.format("car with id : %s not found", id);
        return optionalCar.orElseThrow(() -> new CommonBackendException(errMsg, HttpStatus.NOT_FOUND));
    }

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

    public void dellCar(Long id) {
        Car carFromDB = getCarFromDB(id);
        if (carFromDB.getId() == null) {
            log.error("Car with id {} not found", id);
            return;
        }
        carFromDB.setStatus(CarStat.DELETED);
        carRepository.save(carFromDB);
    }

//    public List<CarInfoResp> getAllCar() {
//        return carRepository.findAll().stream()
//                .map(car -> mapper.convertValue(car, CarInfoResp.class))
//                .collect(Collectors.toList());
//    }

    public Page<CarInfoResp> getAllCar(Integer page, Integer perPage, String sort, Sort.Direction order, String filter) {

        Pageable pageRequest = PaginationUtils.getPageRequest(page, perPage, sort, order);

        Page<Car> cars;

        if (StringUtils.hasText(filter)) {
            cars = carRepository.findAllFiltered(pageRequest, filter);
        } else {
            cars = carRepository.findAll(pageRequest);
        }

        List<CarInfoResp> content = cars.getContent().stream()
                .map(u -> mapper.convertValue(u, CarInfoResp.class))
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageRequest, cars.getTotalElements());
    }

    public CarInfoResp linkCarAndDriver(Long carId, Long userId) {
        Car carFromDB = getCarFromDB(carId);
        User userFromDB = userService.getUserFromDB(userId);

        if (carFromDB.getId() == null || userFromDB.getId() == null) {
            return CarInfoResp.builder().build();
        }

        List<Car> cars = userFromDB.getCars();

        Car existingCar = cars.stream().filter(car -> car.getId().equals(carId)).findFirst().orElse(null);

        if (existingCar != null) {
            return mapper.convertValue(existingCar, CarInfoResp.class);
        }

        cars.add(carFromDB);
        User user = userService.updateCarList(userFromDB);

        carFromDB.setUser(user);
        carRepository.save(carFromDB);

        CarInfoResp carInfoResp = mapper.convertValue(carFromDB, CarInfoResp.class);
        UserInfoResp userInfoResp = mapper.convertValue(user, UserInfoResp.class);

        carInfoResp.setUser(userInfoResp);

        return carInfoResp;
    }

    public List<CarInfoResp> getUserCars(Long id){
        User user = userService.getUserFromDB(id);
        if (user.getId() == null) {
            log.error("User {} not found", id);
            return List.of(CarInfoResp.builder().build());
        }
        return carRepository.getCarUser(id).stream()
                .map(car -> mapper.convertValue(car, CarInfoResp.class))
                .collect(Collectors.toList());
    }
}