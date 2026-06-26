package com.bootcamp.demo.managers;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.MilitaryGearGameData;
import com.bootcamp.demo.data.game.PlayerStat;
import com.bootcamp.demo.data.save.*;

public class MissionsManager {
    MilitaryGearsSaveData militaryGearsSaveData = API.get(SaveData.class).getMilitariesSaveData();
    TacticalsSaveData tacticalsSaveData = API.get(SaveData.class).getTacticalsSaveData();
    PetsSaveData petsSaveData = API.get(SaveData.class).getPetsSaveData();
    FlagsSaveData flagsSaveData = API.get(SaveData.class).getFlagsSaveData();

    private final StatsSaveData statsContainer = new StatsSaveData();

    public void initializeStatsContainer () {
        statsContainer.getStats().clear();
        for (PlayerStat stat : PlayerStat.values()) {
            StatSaveData statSaveData = new StatSaveData(); // create new instance each time
            statSaveData.setStat(stat);
            statSaveData.setValue(0);
            statsContainer.getStats().put(stat, statSaveData);
        }
    }

    public StatsSaveData updateStatsContainer () {
        for (MilitaryGearGameData.Slot name : militaryGearsSaveData.getMilitaries().keys()) {
            MilitaryGearSaveData militaryGearSaveData = militaryGearsSaveData.getMilitaries().get(name);
            for (PlayerStat stat : PlayerStat.values()) {
                if (militaryGearSaveData.getGearStats().getStats().containsKey(stat)) {
                    mergeStats(militaryGearSaveData.getGearStats().getStats());
                }
            }
        }
        for (IntMap.Entry<String> entry : tacticalsSaveData.getEquipped()) {
            TacticalSaveData tacticalSaveData = tacticalsSaveData.getInventory().get(tacticalsSaveData.getEquipped().get(entry.key));
            for (PlayerStat stat : PlayerStat.values()) {
                if (tacticalSaveData.getTacticalStats().getStats().containsKey(stat)) {
                    mergeStats(tacticalSaveData.getTacticalStats().getStats());
                }
            }
        }

        PetSaveData petSaveData = petsSaveData.getInventory().get(petsSaveData.getEquipped());
        FlagSaveData flagSaveData = flagsSaveData.getInventory().get(flagsSaveData.getEquipped());
        for (PlayerStat stat : PlayerStat.values()) {
            if (petSaveData.getPetStats().getStats().containsKey(stat)) {
                mergeStats(petSaveData.getPetStats().getStats());
            }
            if (flagSaveData.getFlagStats().getStats().containsKey(stat)) {
                mergeStats(flagSaveData.getFlagStats().getStats());
            }
        }

        return statsContainer;
    }

    private void mergeStats (ObjectMap<PlayerStat, StatSaveData> sourceStats) {
        for (ObjectMap.Entry<PlayerStat, StatSaveData> entry : sourceStats.entries()) {
            PlayerStat stat = entry.key;
            statsContainer.getStats().put(stat, addStats(statsContainer.getStats().get(stat), entry.value));
        }
    }

    private StatSaveData addStats (StatSaveData base, StatSaveData other) {
        if (base == null) return other;
        if (other == null) return base;

        PlayerStat.StatType typeA = base.getStat().getType();
        PlayerStat.StatType typeB = other.getStat().getType();

        float resultValue;
        if (typeA == typeB) {
            resultValue = base.getValue() + other.getValue();
        } else {
            if (typeA == PlayerStat.StatType.ADDITIVE) {
                resultValue = base.getValue() + base.getValue() * other.getValue();
            } else {
                resultValue = other.getValue() + other.getValue() * base.getValue();
            }
        }

        base.setValue(resultValue);
        return base;
    }
}
