package com.bootcamp.demo.managers;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.MilitaryGearGameData;
import com.bootcamp.demo.data.game.Stat;
import com.bootcamp.demo.data.save.*;

public class MissionsManager {
    MilitaryGearsSaveData militaryGearsSaveData = API.get(SaveData.class).getMilitariesSaveData();
    TacticalsSaveData tacticalsSaveData = API.get(SaveData.class).getTacticalsSaveData();
    PetsSaveData petsSaveData = API.get(SaveData.class).getPetsSaveData();
    FlagsSaveData flagsSaveData = API.get(SaveData.class).getFlagsSaveData();

    private final StatsSaveData statsContainer = new StatsSaveData();

    public void initializeStatsContainer () {
        statsContainer.getStats().clear();
        for (Stat stat : Stat.values()) {
            StatSaveData statSaveData = new StatSaveData(); // create new instance each time
            statSaveData.setName(stat);
            statSaveData.setValue(0);
            statsContainer.getStats().put(stat, statSaveData);
        }
    }

    public StatsSaveData updateStatsContainer () {
        for (MilitaryGearGameData.Slot name : militaryGearsSaveData.getMilitaries().keys()) {
            MilitaryGearSaveData militaryGearSaveData = militaryGearsSaveData.getMilitaries().get(name);
            for (Stat stat : Stat.values()) {
                if (militaryGearSaveData.getGearStats().getStats().containsKey(stat)) {
                    mergeStats(militaryGearSaveData.getGearStats().getStats());
                }
            }
        }
        for (IntMap.Entry<String> entry : tacticalsSaveData.getEquipped()) {
            TacticalSaveData tacticalSaveData = tacticalsSaveData.getInventory().get(tacticalsSaveData.getEquipped().get(entry.key));
            for (Stat stat : Stat.values()) {
                if (tacticalSaveData.getTacticalStats().getStats().containsKey(stat)) {
                    mergeStats(tacticalSaveData.getTacticalStats().getStats());
                }
            }
        }

        PetSaveData petSaveData = petsSaveData.getInventory().get(petsSaveData.getEquipped());
        FlagSaveData flagSaveData = flagsSaveData.getInventory().get(flagsSaveData.getEquipped());
        for (Stat stat : Stat.values()) {
            if (petSaveData.getPetStats().getStats().containsKey(stat)) {
                mergeStats(petSaveData.getPetStats().getStats());
            }
            if (flagSaveData.getFlagStats().getStats().containsKey(stat)) {
                mergeStats(flagSaveData.getFlagStats().getStats());
            }
        }

        return statsContainer;
    }

    private void mergeStats (ObjectMap<Stat, StatSaveData> sourceStats) {
        for (ObjectMap.Entry<Stat, StatSaveData> entry : sourceStats.entries()) {
            Stat stat = entry.key;
            statsContainer.getStats().put(stat, addStats(statsContainer.getStats().get(stat), entry.value));
        }
    }

    private StatSaveData addStats (StatSaveData base, StatSaveData other) {
        if (base == null) return other;
        if (other == null) return base;

        Stat.StatType typeA = base.getName().getType();
        Stat.StatType typeB = other.getName().getType();

        float resultValue;
        if (typeA == typeB) {
            resultValue = base.getValue() + other.getValue();
        } else {
            if (typeA == Stat.StatType.ADDITIVE) {
                resultValue = base.getValue() + base.getValue() * other.getValue();
            } else {
                resultValue = other.getValue() + other.getValue() * base.getValue();
            }
        }

        base.setValue(resultValue);
        return base;
    }
}
