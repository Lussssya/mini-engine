package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.bootcamp.demo.data.game.MilitaryGearGameData.Slot;
import com.bootcamp.demo.data.game.MilitaryGearGameData.Rarity;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

public class MilitaryGearSaveData implements Json.Serializable {
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private Slot slot;
    @Setter
    @Getter
    private int starCount;
    @Setter
    @Getter
    private int level;
    @Setter
    @Getter
    private char tier;
    @Setter
    @Getter
    private Rarity rarity;
    @Setter
    @Getter
    private StatsSaveData gearStats = new StatsSaveData();

    @Override
    public void write (Json json) {
        json.writeValue("n", name);
        json.writeValue("s", slot.name());
        json.writeValue("sr", starCount);
        json.writeValue("l", level);
        json.writeValue("t", tier);
        json.writeValue("r", rarity.name());
        json.writeValue("st", gearStats);
    }

    @Override
    public void read (Json json, JsonValue jsonValue) {
        name = jsonValue.getString("n", "angel-bow");
        slot = Slot.valueOf(jsonValue.getString("s").toUpperCase(Locale.ENGLISH));
        starCount = jsonValue.getInt("sr", 0);
        level = jsonValue.getInt("l", 0);
        tier = jsonValue.getChar("t", 'F');
        rarity = Rarity.valueOf(jsonValue.getString("r").toUpperCase(Locale.ENGLISH));
        gearStats = json.readValue(StatsSaveData.class, jsonValue.get("st"));
    }
}
