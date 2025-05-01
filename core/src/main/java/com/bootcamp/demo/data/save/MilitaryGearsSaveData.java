package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.MilitaryGearGameData.Slot;
import lombok.Getter;

public class MilitaryGearsSaveData implements Json.Serializable {
    @Getter
    private final ObjectMap<Slot, MilitaryGearSaveData> militaries = new ObjectMap<>();

    @Override
    public void write(Json json) {
        for (ObjectMap.Entry<Slot, MilitaryGearSaveData> entry : militaries.entries()) {
            json.writeValue(entry.key.name(), entry.value);
        }
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        militaries.clear();

        for (JsonValue entry : jsonValue) {
            final Slot slot = Slot.valueOf(entry.name);
            final MilitaryGearSaveData gear = json.readValue(MilitaryGearSaveData.class, entry);
            militaries.put(slot, gear);
        }
    }
}
