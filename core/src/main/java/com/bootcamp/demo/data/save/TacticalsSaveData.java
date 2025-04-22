package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.save.TacticalSaveData.TacticalSlot;
import lombok.Getter;

@Getter
public class TacticalsSaveData implements Json.Serializable {
    private final ObjectMap<TacticalSlot, TacticalSaveData> tacticals = new ObjectMap<>();

    @Override
    public void write(Json json) {
        for (ObjectMap.Entry<TacticalSlot, TacticalSaveData> entry : tacticals.entries()) {
            json.writeValue(entry.key.name(), entry.value);
        }
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        tacticals.clear();

        for (JsonValue value : jsonValue) {
            TacticalSlot slot = TacticalSlot.valueOf(value.name);
            TacticalSaveData tacticalSaveData = json.readValue(TacticalSaveData.class, value);
            tacticals.put(slot, tacticalSaveData);
        }
    }
}
