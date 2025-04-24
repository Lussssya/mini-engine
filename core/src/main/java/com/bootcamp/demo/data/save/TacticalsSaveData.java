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
        json.writeObjectStart("inventory");
        for (ObjectMap.Entry<String, TacticalSaveData> entry : inventory.entries()) {
            json.writeValue(entry.key, entry.value);
        }
        json.writeObjectEnd();

        json.writeObjectStart("equipped");
        for (IntMap.Entry<String> entry : equipped.entries()) {
            json.writeValue(String.valueOf(entry.key), entry.value);
        }
        json.writeObjectEnd();
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        inventory.clear();
        equipped.clear();

        JsonValue inventoryJson = jsonValue.get("inventory");
        for (JsonValue item : inventoryJson) {
            String name = item.name();
            TacticalSaveData data = json.readValue(TacticalSaveData.class, item);
            inventory.put(name, data);
        }

        JsonValue equippedJson = jsonValue.get("equipped");
        for (JsonValue item : equippedJson) {
            int index = Integer.parseInt(item.name());
            String name = item.asString();
            equipped.put(index, name);
        }
    }
}
