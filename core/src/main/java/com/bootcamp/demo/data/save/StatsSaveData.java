package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.Stat;
import lombok.Getter;

import java.util.Locale;

public class StatsSaveData implements Json.Serializable {
    @Getter
    private final ObjectMap<Stat, StatSaveData> stats = new ObjectMap<>();

    @Override
    public void write (Json json) {
        for (ObjectMap.Entry<Stat, StatSaveData> entry : stats.entries()) {
            json.writeValue(entry.key.name(), entry.value);
        }
    }

    @Override
    public void read (Json json, JsonValue jsonValue) {
        stats.clear();

        for (JsonValue value : jsonValue) {
            final Stat statType = Stat.valueOf(value.name().toUpperCase(Locale.ENGLISH));
            final StatSaveData statSaveData = json.readValue(StatSaveData.class, value);
            stats.put(statType, statSaveData);
        }
    }
}
