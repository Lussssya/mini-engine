package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import lombok.Getter;
import lombok.Setter;

public class FlagsSaveData implements Json.Serializable {
    @Getter
    private final ObjectMap<String, FlagSaveData> inventory = new ObjectMap<>();
    @Getter @Setter
    private String equipped;

    @Override
    public void write(Json json) {
        for (ObjectMap.Entry<String, FlagSaveData> entry : inventory.entries()) {
            json.writeValue(entry.key, entry.value);
        }
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        inventory.clear();

        for (JsonValue item : jsonValue) {
            final String name = item.name();
            final FlagSaveData data = json.readValue(FlagSaveData.class, item);
            inventory.put(name, data);
        }
    }
}
