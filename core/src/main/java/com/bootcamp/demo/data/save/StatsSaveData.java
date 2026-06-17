package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.PLayerStat;
import lombok.Getter;

import java.util.Locale;

@Getter
public class StatsSaveData implements Json.Serializable {
    private final ObjectMap<PLayerStat, StatSaveData> stats = new ObjectMap<>();

    @Override
    public void write (Json json) {
        for (ObjectMap.Entry<PLayerStat, StatSaveData> entry : stats.entries()) {
            json.writeValue(entry.key.name(), entry.value);
        }
    }

    @Override
    public void read (Json json, JsonValue jsonValue) {
        stats.clear();

        for (JsonValue value : jsonValue) {
            final PLayerStat statType = PLayerStat.valueOf(value.name().toUpperCase(Locale.ENGLISH));
            final StatSaveData statSaveData = json.readValue(StatSaveData.class, value);
            stats.put(statType, statSaveData);
        }
    }
}
