package com.example.itmo.extended.controllers;

import com.example.itmo.extended.model.dto.request.UserInfoReq;
import com.example.itmo.extended.model.dto.response.UserInfoResp;
import com.example.itmo.extended.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Пользователи")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по ID")
    public UserInfoResp getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping
    @Operation(summary = "Добавить нового пользователя")
    public UserInfoResp addUser(@RequestBody @Valid UserInfoReq req) {
        return userService.addUser(req);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные пользователя по ID")
    public UserInfoResp updateUser(@PathVariable Long id, @RequestBody UserInfoReq req) {
        return userService.updateUser(id, req);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя пользователя по ID")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Получить список всех пользователей")
    public List<UserInfoResp> getAllUsers() {
        return userService.getAllUsers();
    }
}