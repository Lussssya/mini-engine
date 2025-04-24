package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import lombok.Getter;

@Getter
public class FlagsSaveData implements Json.Serializable {
    private final ObjectMap<String, FlagSaveData> inventory = new ObjectMap<>();
    private final String[] equipped = new String[1];

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
            String name = item.name();
            FlagSaveData data = json.readValue(FlagSaveData.class, item);
            inventory.put(name, data);
        }
    }
}
