package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.bootcamp.demo.data.game.Rarity;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

public class TacticalSaveData implements Json.Serializable {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private int level;
    @Getter
    @Setter
    private int starCount;
    @Getter
    @Setter
    private Rarity rarity;
    @Getter
    @Setter
    private StatsSaveData tacticalStats = new StatsSaveData();

    @Override
    public void write(Json json) {
        json.writeValue("n", name);
        json.writeValue("l", level);
        json.writeValue("sc", starCount);
        json.writeValue("r", rarity.name());
        json.writeValue("s", tacticalStats);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        name = jsonValue.getString("n", "shuriken");
        level = jsonValue.getInt("l", 0);
        starCount = jsonValue.getInt("sc", 0);
        rarity = Rarity.valueOf(jsonValue.getString("r").toUpperCase(Locale.ENGLISH));
        tacticalStats = json.readValue(StatsSaveData.class, jsonValue.get("s"));
    }
}
