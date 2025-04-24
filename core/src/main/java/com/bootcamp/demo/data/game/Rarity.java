package com.bootcamp.demo.data.game;

import lombok.Getter;

@Getter
public enum Rarity {
    COMMON("#b19986"),
    RARE("#6495ca"),
    EPIC("#b693c7"),
    LEGENDARY("#e4ab56"),
    EXOTIC("#d85959");

    private final String backgroundHex;

    Rarity(String backgroundHex) {
        this.backgroundHex = backgroundHex;
    }
}
