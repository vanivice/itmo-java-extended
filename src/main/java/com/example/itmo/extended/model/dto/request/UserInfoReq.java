package com.example.itmo.extended.model.dto.request;

import com.example.itmo.extended.model.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInfoReq {
    @NotEmpty
    @Schema(description = "Почта")
    private String email;
    @NotNull
    @Size(min = 6, max = 20)
    @Schema(description = "Пароль")
    private String password;
    @Schema(description = "Имя")
    private String firstName;
    @Schema(description = "Фамилия")
    private String lastName;
    @Schema(description = "Отчество")
    private String middleName;
    @Schema(description = "Возраст")
    private Integer age;
    @Schema(description = "Пол")
    private Gender gender;
}