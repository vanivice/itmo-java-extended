package com.example.itmo.extended.model.db.entity;

import com.example.itmo.extended.model.enums.CarStat;
import com.example.itmo.extended.model.enums.CarType;
import com.example.itmo.extended.model.enums.Color;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cars")

public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "color")
    @Enumerated(EnumType.STRING)
    private Color color;

    @Column(name = "year")
    private Integer year;

    @Column(name = "price")
    private Long price;

    @Column(name = "is_new")
    private Boolean isNew;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private CarType type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CarStat status;

    @ManyToOne
    @JsonBackReference(value = "driver_cars")
    private User user;
}
