package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StatSaveData implements Json.Serializable {
    private String name;
    private String value;

    @Override
    public void write (Json json) {
        json.writeValue("name", name);
        json.writeValue("value", value);
    }

    @Override
    public void read (Json json, JsonValue jsonValue) {
        name = jsonValue.getString("name");
        value = jsonValue.getString("value");
    }
}

