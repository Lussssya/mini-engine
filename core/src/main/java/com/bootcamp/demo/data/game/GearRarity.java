package com.bootcamp.demo.data.game;

import lombok.Getter;

@Getter
public enum GearRarity {
    RUSTED("#b19986"),
    SCRAP("#6495ca"),
    HARDENED("#b693c7"),
    ELITE("#e4ab56"),
    ASCENDANT("#d85959"),
    NUCLEAR("#aad448"),
    JUGGERNAUT("#7a70ec"),
    DOMINION("#f8667c"),
    OBLIVION("#59fcbe"),
    IMMORTAL("#6cfee3"),
    ETHEREAL("#fcfecd");

    private final String backgroundHex;

    GearRarity (String backgroundHex) {
        this.backgroundHex = backgroundHex;
    }
}
