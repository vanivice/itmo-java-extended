package com.example.itmo.extended.service;

import com.example.itmo.extended.exception.CommonBackendException;
import com.example.itmo.extended.model.db.entity.User;
import com.example.itmo.extended.model.db.repository.UserRepository;
import com.example.itmo.extended.model.dto.request.UserInfoReq;
import com.example.itmo.extended.model.dto.response.UserInfoResp;
import com.example.itmo.extended.model.enums.UserStat;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final ObjectMapper mapper;
    private final UserRepository userRepository;

    public UserInfoResp addUser(UserInfoReq req) {
        if (!EmailValidator.getInstance().isValid(req.getEmail())) {
            throw new CommonBackendException("Email invalid", HttpStatus.BAD_REQUEST);
        }

        userRepository.findByEmail(req.getEmail()).ifPresent(user -> {
            throw new CommonBackendException("User already exists", HttpStatus.CONFLICT);
        });

        User user = mapper.convertValue(req, User.class);
        user.setStatus(UserStat.CREATED);

        User save = userRepository.save(user);
        Long id = save.getId();
        log.info("user {} added", id);
        return mapper.convertValue(save, UserInfoResp.class);
    }

    public UserInfoResp getUser(Long id) {
        User user = getUserFromDB(id);
        return mapper.convertValue(user, UserInfoResp.class);
    }

    public User getUserFromDB(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        final String errMsg = String.format("User with id: %s not found", id);
        return optionalUser.orElseThrow(() -> new CommonBackendException(errMsg, HttpStatus.NOT_FOUND));
    }

    public UserInfoResp updateUser(Long id, UserInfoReq req) {
        User userFromDB = getUserFromDB(id);
        if (userFromDB.getId() == null) {
            return mapper.convertValue(userFromDB, UserInfoResp.class);
        }

        User userReq = mapper.convertValue(req, User.class);

        userFromDB.setEmail(userReq.getEmail() == null ? userFromDB.getEmail() : userReq.getEmail());
        userFromDB.setPassword(userReq.getPassword() == null ? userFromDB.getPassword() : userReq.getPassword());
        userFromDB.setFirstName(userReq.getFirstName() == null ? userFromDB.getFirstName() : userReq.getFirstName());
        userFromDB.setLastName(userReq.getLastName() == null ? userFromDB.getLastName() : userReq.getLastName());
        userFromDB.setMiddleName(userReq.getMiddleName() == null ? userFromDB.getMiddleName() : userReq.getMiddleName());
        userFromDB.setAge(userReq.getAge() == null ? userFromDB.getAge() : userReq.getAge());
        userFromDB.setGender(userReq.getGender() == null ? userFromDB.getGender() : userReq.getGender());


        userFromDB = userRepository.save(userFromDB);
        return mapper.convertValue(userFromDB, UserInfoResp.class);
    }

    public void deleteUser(Long id) {
        User userFromDB = getUserFromDB(id);
        if (userFromDB.getId() == null) {
            log.error("User with id {} not found", id);
            return;
        }

        userFromDB.setStatus(UserStat.DELETED);
        userRepository.save(userFromDB);
    }

    public List<UserInfoResp> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> mapper.convertValue(user, UserInfoResp.class))
                .collect(Collectors.toList());
    }

    public User updateCarList(User updatedUser) {
        return userRepository.save(updatedUser);
    }


}