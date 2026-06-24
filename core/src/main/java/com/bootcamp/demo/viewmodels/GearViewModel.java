package com.bootcamp.demo.viewmodels;

import lombok.Getter;

@Getter
public class GearViewModel {
    private final String name;
    private final String rarity;
    private final String type;
    private final String power;

    public GearViewModel (String name, String rarity, String type, String power) {
        this.name = name;
        this.rarity = rarity;
        this.type = type;
        this.power = power;
    }
}
