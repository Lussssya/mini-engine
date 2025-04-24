package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.bootcamp.demo.data.game.Rarity;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

@Getter
@Setter
public class PetSaveData implements Json.Serializable {
    private String name;
    private int level;
    private int starCount;
    private Rarity rarity;
    private StatsSaveData stats = new StatsSaveData();

    @Override
    public void write(Json json) {
        json.writeValue("n", name);
        json.writeValue("l", level);
        json.writeValue("sc", starCount);
        json.writeValue("r", rarity.name());
        json.writeValue("s", stats);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        name = jsonValue.getString("n", "pet-orange-cat");
        level = jsonValue.getInt("l", 0);
        starCount = jsonValue.getInt("sc", 0);
        rarity = Rarity.valueOf(jsonValue.getString("r").toUpperCase(Locale.ENGLISH));
        stats = json.readValue(StatsSaveData.class, jsonValue.get("s"));
    }
}
