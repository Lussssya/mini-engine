package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.save.MilitaryGearSaveData.MilitarySlot;
import lombok.Getter;

@Getter
public class MilitaryGearsSaveData implements Json.Serializable {
    private final ObjectMap<MilitarySlot, MilitaryGearSaveData> militaries = new ObjectMap<>();

    @Override
    public void write(Json json) {
        for (ObjectMap.Entry<MilitarySlot, MilitaryGearSaveData> entry : militaries.entries()) {
            json.writeValue(entry.key.name(), entry.value);
        }
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        militaries.clear();
        for (JsonValue entry : jsonValue) {
            MilitarySlot slot = MilitarySlot.valueOf(entry.name);
            MilitaryGearSaveData gear = json.readValue(MilitaryGearSaveData.class, entry);
            militaries.put(slot, gear);
        }
    }
}
