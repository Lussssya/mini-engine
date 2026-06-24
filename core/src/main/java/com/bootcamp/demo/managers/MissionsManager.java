package com.bootcamp.demo.managers;

import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.*;
import com.bootcamp.demo.data.save.*;

public class MissionsManager {
    private final StatsSaveData statsContainer = new StatsSaveData();

    public StatsSaveData updateStatsContainer () {
        initializeStatsContainer();

        mergeMilitaryGearStats();
        mergeAccessoryGearStats();

        return statsContainer;
    }

    public void initializeStatsContainer () {
        statsContainer.getStats().clear();
        for (PLayerStat stat : PLayerStat.values()) {
            final StatSaveData statSaveData = new StatSaveData(); // create new instance each time
            statSaveData.setName(stat);
            statSaveData.setValue(0);
            statsContainer.getStats().put(stat, statSaveData);
        }
    }

    private void mergeAccessoryGearStats () {
        final AccessoryGearsSaveData accessoryGearsSaveData = API.get(SaveData.class).getAccessoryGearsSaveData();

        for (AccessoryGearSaveData accessoryGearSaveData : accessoryGearsSaveData.getAccessories().values()) {
            mergeStats(accessoryGearSaveData.getGearStats().getStats());
        }
    }

    private void mergeMilitaryGearStats () {
        final MilitaryGearsSaveData militaryGearsSaveData = API.get(SaveData.class).getMilitariesSaveData();

        for (MilitaryGearSaveData militaryGearSaveData : militaryGearsSaveData.getMilitaries().values()) {
            mergeStats(militaryGearSaveData.getGearStats().getStats());
        }
    }

    private void mergeStats (ObjectMap<PLayerStat, StatSaveData> sourceStats) {
        for (ObjectMap.Entry<PLayerStat, StatSaveData> entry : sourceStats.entries()) {
            final PLayerStat stat = entry.key;
            statsContainer.getStats().put(stat, addStats(statsContainer.getStats().get(stat), entry.value));
        }
    }

    private StatSaveData addStats (StatSaveData base, StatSaveData other) {
        if (base == null) return other;
        if (other == null) return base;

        final PLayerStat.StatType typeA = base.getName().getType();
        final PLayerStat.StatType typeB = other.getName().getType();

        float resultValue;
        if (typeA == typeB) {
            resultValue = base.getValue() + other.getValue();
        } else {
            if (typeA == PLayerStat.StatType.ADDITIVE) {
                resultValue = base.getValue() + base.getValue() * other.getValue();
            } else {
                resultValue = other.getValue() + other.getValue() * base.getValue();
            }
        }

        base.setValue(resultValue);
        return base;
    }

    public static float computeCumulativePower (StatsSaveData statsSaveData) {
        float additive = 0;
        float percent = 0;

        for (StatSaveData statData: statsSaveData.getStats().values()) {
            if (statData.getName().getType() == PLayerStat.StatType.ADDITIVE){
                additive += statData.getValue();
            }
            else {
                percent += statData.getValue();
            }
        }
        return additive > 0
            ? additive * (1 + percent / 100f)
            : percent;
    }
}
