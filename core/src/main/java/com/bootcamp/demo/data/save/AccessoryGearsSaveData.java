package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.AccessoryGearGameData.Slot;
import lombok.Getter;

@Getter
public class AccessoryGearsSaveData implements Json.Serializable {
    private final ObjectMap<Slot, AccessoryGearSaveData> accessories = new ObjectMap<>();

    @Override
    public void write (Json json) {
        for (ObjectMap.Entry<Slot, AccessoryGearSaveData> entry
            : accessories.entries()) {

            json.writeValue(entry.key.name(), entry.value);
        }
    }

    @Override
    public void read (Json json, JsonValue jsonValue) {
        accessories.clear();

        for (JsonValue entry : jsonValue) {
            final Slot slot = Slot.valueOf(entry.name);

            final AccessoryGearSaveData accessory = json.readValue(AccessoryGearSaveData.class, entry);
            accessories.put(slot, accessory);
        }
    }
}
