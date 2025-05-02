package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import lombok.Getter;
import lombok.Setter;


public class FlagSaveData implements Json.Serializable {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private StatsSaveData flagStats = new StatsSaveData();

    @Override
    public void write (Json json) {
        json.writeValue("n", name);
        json.writeValue("s", flagStats);
    }

    @Override
    public void read (Json json, JsonValue jsonValue) {
        name = jsonValue.getString("n");
        flagStats = json.readValue(StatsSaveData.class, jsonValue.get("s"));
    }
}
