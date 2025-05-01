package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import lombok.Getter;
import lombok.Setter;


public class PetsSaveData implements Json.Serializable {
    @Getter
    private final ObjectMap<String, PetSaveData> inventory = new ObjectMap<>();
    @Getter @Setter
    private String equipped;

    @Override
    public void write(Json json) {
        for (ObjectMap.Entry<String, PetSaveData> entry : inventory.entries()) {
            json.writeValue(entry.key, entry.value);
        }
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        inventory.clear();

        for (JsonValue item : jsonValue) {
            final String name = item.name();
            final PetSaveData data = json.readValue(PetSaveData.class, item);
            inventory.put(name, data);
        }
    }
}
