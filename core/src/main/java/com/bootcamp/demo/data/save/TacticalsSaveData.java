package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import lombok.Getter;

@Getter
public class TacticalsSaveData implements Json.Serializable {
    private final ObjectMap<String, TacticalSaveData> inventory = new ObjectMap<>();
    private final IntMap<String> equipped = new IntMap<>();

    @Override
    public void write(Json json) {
        for (ObjectMap.Entry<String, TacticalSaveData> entry : inventory.entries()) {
            json.writeValue(entry.key, entry.value);
        }
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        inventory.clear();

        for (JsonValue item : jsonValue) {
            final String name = item.name();
            final TacticalSaveData data = json.readValue(TacticalSaveData.class, item);
            inventory.put(name, data);
        }
    }
}
