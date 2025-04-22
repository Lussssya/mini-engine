package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class StatSaveData implements Json.Serializable {
    private StatType name;
    private float value;

    @Getter
    public enum StatType {
        HP("HP:", ""),
        ATK("ATK:", ""),
        DODGE("DODGE:", "%"),
        COMBO("COMBO:", "%"),
        CRIT("CRIT:", "%"),
        STUN("STUN:", "%"),
        REGEN("REGEN:", "%"),
        STEAL("STEAL:", "%"),
        POISON("POISON:", "%");

        private final String title;
        private final String identifier;

        StatType(String title, String identifier) {
            this.title = title;
            this.identifier = identifier;
        }
    }

    @Override
    public void write(Json json) {
        json.writeValue("name", name.name());
        json.writeValue("value", value);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        name = StatType.valueOf(jsonValue.getString("name"));
        value = jsonValue.getFloat("value");
    }
}
