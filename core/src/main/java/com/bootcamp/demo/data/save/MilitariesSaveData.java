package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import lombok.Getter;

@Getter
public class MilitariesSaveData implements Json.Serializable {
    private final IntMap<MilitarySaveData> militaries = new IntMap<>();

    @Override
    public void write (Json json) {
        for (IntMap.Entry<MilitarySaveData> entry : militaries.entries()) {
            json.writeValue(String.valueOf(entry.key), entry.value);
        }
    }

    @Override
    public void read (Json json, JsonValue jsonValue) {
        militaries.clear();

        for (JsonValue value : jsonValue) {
            final Integer slotIndex = Integer.valueOf(value.name);
            final MilitarySaveData militarySaveData = json.readValue(MilitarySaveData.class, value);
            militaries.put(slotIndex, militarySaveData);
        }
    }
}
