package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.bootcamp.demo.data.game.Stat;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

@Setter @Getter
public class StatSaveData implements Json.Serializable {
    private Stat name;
    private float value;

    @Override
    public void write(Json json) {
        json.writeValue("n", name.name());
        json.writeValue("v", value);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        name = Stat.valueOf(jsonValue.getString("n").toUpperCase(Locale.ENGLISH));
        value = jsonValue.getFloat("v");
    }
}
