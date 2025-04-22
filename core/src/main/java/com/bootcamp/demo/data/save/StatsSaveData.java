package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.save.StatSaveData.StatType;
import lombok.Getter;

@Getter
public class StatsSaveData implements Json.Serializable {
    private final ObjectMap<StatType, StatSaveData> stats = new ObjectMap<>();

    @Override
    public void write(Json json) {
        for (ObjectMap.Entry<StatType, StatSaveData> entry : stats.entries()) {
            json.writeValue(entry.key.name(), entry.value);
        }
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        stats.clear();

        for (JsonValue value : jsonValue) {
            final StatType statType = StatType.valueOf(value.name);
            final StatSaveData statSaveData = json.readValue(StatSaveData.class, value);
            stats.put(statType, statSaveData);
        }
    }
}
