package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TacticalSaveData implements Json.Serializable {
    public enum TacticalSlot {
        SLOT_1,
        SLOT_2,
        SLOT_3,
        SLOT_4
    }

    @Getter
    public enum TacticalRarity {
        COMMON("#b19986"),
        RARE("#6495ca"),
        EPIC("#b693c7"),
        LEGENDARY("#e4ab56"),
        EXOTIC("#d85959");

        private final String backgroundHex;

        TacticalRarity(String backgroundHex) {
            this.backgroundHex = backgroundHex;
        }
    }

    private String name;
    private TacticalSlot slot;
    private int level;
    private int starCount;
    private TacticalRarity rarity;
    private StatsSaveData stats = new StatsSaveData();

    @Override
    public void write(Json json) {
        json.writeValue("name", name);
        json.writeValue("slot", slot.name());
        json.writeValue("level", level);
        json.writeValue("starCount", starCount);
        json.writeValue("rarity", rarity);
        json.writeValue("stats", stats);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        name = jsonValue.getString("name");
        slot = TacticalSlot.valueOf(jsonValue.getString("slot").toUpperCase());
        level = jsonValue.getInt("level");
        starCount = jsonValue.getInt("starCount");
        rarity = TacticalRarity.valueOf(jsonValue.getString("rarity"));
        stats = json.readValue(StatsSaveData.class, jsonValue.get("stats"));
    }
}
