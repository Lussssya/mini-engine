package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.bootcamp.demo.data.game.PLayerStat;
import com.bootcamp.demo.data.game.SpecializationGameData.Rarity;
import com.bootcamp.demo.data.game.SpecializationGameData;
import com.bootcamp.demo.data.game.SpecializationGameData.SpecializationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecializationSaveData implements Json.Serializable {
    public static final int BASE_RESET_PRICE = 20;
    public static final int MAX_RESET_PRICE = 320;

    private SpecializationGameData.SpecializationType specializationType;
    private int rollPoints;
    private int resetCount;

    private double atkBonus;
    private double hpBonus;
    private double defBonus;

    private double currentBonus;
    private PLayerStat currentStatType;
    private Rarity currentRarity;

    @Override
    public void write (Json json) {
        json.writeValue("speciality", specializationType);
        json.writeValue("rollPoints", rollPoints);
        json.writeValue("resetCount", resetCount);

        json.writeValue("atkBonus", atkBonus);
        json.writeValue("hpBonus", hpBonus);
        json.writeValue("defBonus", defBonus);

        json.writeValue("currentBonus", currentBonus);
        json.writeValue("currentStat", currentStatType);
        json.writeValue("currentRarity", currentRarity);
    }

    @Override
    public void read (Json json, JsonValue jsonValue) {
        specializationType = json.readValue(SpecializationType.class, jsonValue.get("speciality"));
        rollPoints = jsonValue.getInt("rollPoints", 0);
        resetCount = jsonValue.getInt("resetCount", 0);

        atkBonus = jsonValue.getDouble("atkBonus", 0);
        hpBonus = jsonValue.getDouble("hpBonus", 0);
        defBonus = jsonValue.getDouble("defBonus", 0);

        currentBonus = jsonValue.getDouble("currentBonus", 0);
        currentStatType = json.readValue(PLayerStat.class, jsonValue.get("currentStat"));
        currentRarity = json.readValue(Rarity.class, jsonValue.get("currentRarity"));
    }

    public void reset () {
        specializationType = null;

        rollPoints = 0;

        atkBonus = 0;
        hpBonus = 0;
        defBonus = 0;

        currentBonus = 0;
        currentStatType = null;
        currentRarity = null;
    }

    public int getResetPrice () {
        int price = BASE_RESET_PRICE;

        for (int i = 0; i < resetCount && price < MAX_RESET_PRICE; i++) {
            price = Math.min(price * 2, MAX_RESET_PRICE);
        }

        return price;
    }

    public void incrementResetCount () {
        if (getResetPrice() < MAX_RESET_PRICE) {
            resetCount++;
        }
    }
}
