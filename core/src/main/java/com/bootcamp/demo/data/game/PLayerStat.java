package com.bootcamp.demo.data.game;

import lombok.Getter;

@Getter
public enum PLayerStat {
    HP("HP:", StatType.ADDITIVE),
    ATK("ATK:", StatType.ADDITIVE),
    DEF("DEF:", StatType.MULTIPLICATIVE),
    DODGE("DODGE:", StatType.MULTIPLICATIVE),
    COMBO("COMBO:", StatType.MULTIPLICATIVE),
    CRIT("CRIT:", StatType.MULTIPLICATIVE),
    STUN("STUN:", StatType.MULTIPLICATIVE),
    REGEN("REGEN:", StatType.MULTIPLICATIVE),
    STEAL("STEAL:", StatType.MULTIPLICATIVE),
    POISON("POISON:", StatType.MULTIPLICATIVE),
    ANTI_REGEN("ANTI REGEN:", StatType.MULTIPLICATIVE),
    ANTI_STEAL("ANTI STEAL:", StatType.MULTIPLICATIVE),
    ANTI_COMBO("ANTI COMBO:", StatType.MULTIPLICATIVE),
    ANTI_DODGE("ANTI DODGE:", StatType.MULTIPLICATIVE),
    ANTI_STUN("ANTI STUN:", StatType.MULTIPLICATIVE)
    ;

    private final String displayName;
    private final StatType type;

    PLayerStat (String displayName, StatType type) {
        this.displayName = displayName;
        this.type = type;
    }

    public enum StatType {
        ADDITIVE,
        MULTIPLICATIVE
    }

    public String getIconPath () {
        switch (this) {
            case ATK:
                return "lootPage/attack-icon";
            case HP:
                return "lootPage/hp-icon";
            case DEF:
                return "lootPage/def-icon";
            default:
                return null;
        }
    }
}
