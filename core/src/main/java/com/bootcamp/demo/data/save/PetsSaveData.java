package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PetsSaveData implements Json.Serializable {
    private final ObjectMap<String, PetSaveData> inventory = new ObjectMap<>();
    private final String[] equipped = new String[1];

    @Override
    public void write(Json json) {
        for (ObjectMap.Entry<String, PetSaveData> entry : inventory.entries()) {
            json.writeValue(entry.key, entry.value);
        }
        json.writeObjectEnd();
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        inventory.clear();

        for (JsonValue item : jsonValue) {
            String name = item.name();
            PetSaveData data = json.readValue(PetSaveData.class, item);
            inventory.put(name, data);
        }
    }
}
