package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.bootcamp.demo.data.Rarity;
import lombok.Getter;
import lombok.Setter;

public class MilitarySaveData implements Json.Serializable {

    @Getter @Setter
    private String type;
    @Getter @Setter
    private int starCount;
    @Getter @Setter
    private int level;
    @Getter @Setter
    private char tier;
    @Getter @Setter
    private float power;
    @Getter @Setter
    private Rarity rarity;

    @Override
    public void write(Json json) {
        json.writeValue("type", type);
        json.writeValue("stars", starCount);
        json.writeValue("level", level);
        json.writeValue("tier", tier);
        json.writeValue("power", power);
        json.writeValue("rarity", rarity);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        type = jsonValue.getString("type");
        starCount = jsonValue.getInt("stars");
        level = jsonValue.getInt("level");
        tier = jsonValue.getChar("tier");
        power = jsonValue.getFloat("power");
        rarity = Rarity.valueOf(jsonValue.getString("rarity"));
    }
}
