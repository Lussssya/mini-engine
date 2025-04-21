package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.bootcamp.demo.data.Rarity;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class TacticalSaveData implements Json.Serializable {

    private String name;
    private int level;
    private int starCount;
    private Rarity rarity;

    @Override
    public void write (Json json) {
        json.writeValue("name", name);
        json.writeValue("level", level);
        json.writeValue("starCount", starCount);
        json.writeValue("rarity", rarity);
    }

    @Override
    public void read (Json json, JsonValue jsonValue) {
        name = jsonValue.getString("name");
        level = jsonValue.getInt("level");
        starCount = jsonValue.getInt("starCount");
        rarity = Rarity.valueOf(jsonValue.getString("rarity"));
    }
}
