package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.bootcamp.demo.data.game.AccessoryGearGameData.Slot;
import com.bootcamp.demo.data.game.GearRarity;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

@Setter
@Getter
public class AccessoryGearSaveData implements Json.Serializable {
    private String name;
    private Slot slot;
    private int level;
    private GearRarity rarity;
    private StatsSaveData gearStats = new StatsSaveData();

    @Override
    public void write (Json json) {
        json.writeValue("n", name);
        json.writeValue("s", slot.name());
        json.writeValue("l", level);
        json.writeValue("r", rarity.name());
        json.writeValue("st", gearStats);
    }

    @Override
    public void read (Json json, JsonValue jsonValue) {
        name = jsonValue.getString("n");
        slot = Slot.valueOf(jsonValue.getString("s").toUpperCase(Locale.ENGLISH));
        level = jsonValue.getInt("l", 0);
        rarity = GearRarity.valueOf(jsonValue.getString("r").toUpperCase(Locale.ENGLISH));
        JsonValue statsValue = jsonValue.get("st");
        gearStats = statsValue == null ? new StatsSaveData() : json.readValue(StatsSaveData.class, statsValue);
    }
}
