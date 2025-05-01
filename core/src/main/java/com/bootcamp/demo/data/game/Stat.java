package com.bootcamp.demo.data.game;

import lombok.Getter;

public enum Stat {
    HP("HP:", StatType.ADDITIVE),
    ATK("ATK:", StatType.ADDITIVE),
    DODGE("DODGE:", StatType.MULTIPLICATIVE),
    COMBO("COMBO:", StatType.MULTIPLICATIVE),
    CRIT("CRIT:", StatType.MULTIPLICATIVE),
    STUN("STUN:", StatType.MULTIPLICATIVE),
    REGEN("REGEN:", StatType.MULTIPLICATIVE),
    STEAL("STEAL:", StatType.MULTIPLICATIVE),
    POISON("POISON:", StatType.MULTIPLICATIVE);

    @Getter
    private final String title;
    @Getter
    private final StatType type;

    Stat(String title, StatType type) {
        this.title = title;
        this.type = type;
    }

    public enum StatType {
        ADDITIVE,
        MULTIPLICATIVE
    }
}
