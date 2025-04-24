package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlagSaveData implements Json.Serializable {
    private String name;
    private StatsSaveData stats = new StatsSaveData();

    @Override
    public void write(Json json) {
        json.writeValue("n", name);
        json.writeValue("s", stats);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        name = jsonValue.getString("n");
        stats = json.readValue(StatsSaveData.class, jsonValue.get("s"));
    }
}
