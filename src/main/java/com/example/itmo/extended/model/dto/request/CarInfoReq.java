package com.example.itmo.extended.model.dto.request;

import com.example.itmo.extended.model.enums.CarType;
import com.example.itmo.extended.model.enums.Color;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CarInfoReq {
    @Schema(description = "Бренд")
    private String brand;
    @NotEmpty
    @Schema(description = "Модель")
    private String model;
    @Schema(description = "Цвет")
    private Color color;
    @NotNull
    @Schema(description = "Год выпуска")
    private Integer year;
    @NotNull
    @Schema(description = "Стоимость")
    private Long price;
    @Schema(description = "Новая / Подержанная")
    private Boolean isNew;
    @Schema(description = "Тип кузова")
    private CarType type;
}