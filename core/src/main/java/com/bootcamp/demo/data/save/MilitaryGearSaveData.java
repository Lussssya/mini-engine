package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MilitaryGearSaveData implements Json.Serializable {
    public enum MilitarySlot {
        WEAPON,
        MELEE,
        HEAD,
        BODY,
        GLOVES,
        SHOES
    }

    @Getter
    public enum MilitaryRarity {
        RUSTED("#b19986"),
        SCRAP("#6495ca"),
        HARDENED("#b693c7"),
        EPIC("Color.GREEN"),
        ELITE("#e4ab56"),
        ASCENDANT("#d85959"),
        NUCLEAR("#aad448"),
        JUGGERNAUT("#7a70ec"),
        DOMINION("#f8667c"),
        OBLIVION("#59fcbe"),
        IMMORTAL("#6cfee3"),
        ETHEREAL("#fcfecd");

        private final String backgroundHex;

        MilitaryRarity(String backgroundHex) {
            this.backgroundHex = backgroundHex;
        }
    }

    private String name;
    private MilitarySlot slot;
    private int starCount;
    private int level;
    private char tier;
    private MilitaryRarity rarity;
    private StatsSaveData stats = new StatsSaveData();

    @Override
    public void write(Json json) {
        json.writeValue("name", name);
        json.writeValue("slot", slot.name());
        json.writeValue("stars", starCount);
        json.writeValue("level", level);
        json.writeValue("tier", tier);
        json.writeValue("rarity", rarity);
        json.writeValue("stats", stats);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        name = jsonValue.getString("name");
        slot = MilitarySlot.valueOf(jsonValue.getString("slot").toUpperCase());
        starCount = jsonValue.getInt("stars");
        level = jsonValue.getInt("level");
        tier = jsonValue.getChar("tier");
        rarity = MilitaryRarity.valueOf(jsonValue.getString("rarity"));
        stats = json.readValue(StatsSaveData.class, jsonValue.get("stats"));
    }
}
