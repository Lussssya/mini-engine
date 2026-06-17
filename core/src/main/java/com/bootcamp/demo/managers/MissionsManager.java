package com.bootcamp.demo.managers;

import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.*;
import com.bootcamp.demo.data.save.*;

public class MissionsManager {
    MilitaryGearsSaveData militaryGearsSaveData = API.get(SaveData.class).getMilitariesSaveData();
    AccessoryGearsSaveData accessoryGearsSaveData = API.get(SaveData.class).getAccessoryGearsSaveData();

    private final StatsSaveData statsContainer = new StatsSaveData();

    public void initializeStatsContainer () {
        statsContainer.getStats().clear();
        for (PLayerStat PLayerStat : PLayerStat.values()) {
            final StatSaveData statSaveData = new StatSaveData(); // create new instance each time
            statSaveData.setName(PLayerStat);
            statSaveData.setValue(0);
            statsContainer.getStats().put(PLayerStat, statSaveData);
        }
    }

    public StatsSaveData updateStatsContainer () {
        initializeStatsContainer();

        for (MilitaryGearSaveData militaryGearSaveData : militaryGearsSaveData.getMilitaries().values()) {
            mergeStats(militaryGearSaveData.getGearStats().getStats());
        }

        for (AccessoryGearSaveData accessoryGearSaveData : accessoryGearsSaveData.getAccessories().values()) {
            mergeStats(accessoryGearSaveData.getGearStats().getStats());
        }

        applySpecializationEffects();

        return statsContainer;
    }

    private void mergeStats (ObjectMap<PLayerStat, StatSaveData> sourceStats) {
        for (ObjectMap.Entry<PLayerStat, StatSaveData> entry : sourceStats.entries()) {
            final PLayerStat PLayerStat = entry.key;
            statsContainer.getStats().put(PLayerStat, addStats(statsContainer.getStats().get(PLayerStat), entry.value));
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

    private void applySpecializationEffects () {
        final SpecializationGameData.SpecializationType specializationType = API.get(SaveData.class).getSpecializationSaveData().getSpecializationType();
        if (specializationType == null) {
            return;
        }

        final SpecializationGameData specialization = API.get(GameData.class).getSpecializationsGameData().getSpecializations().get(specializationType);

        for (SpecializationGameData.PassiveEffectData effect : specialization.getEffects()) {
            final PLayerStat stat = effect.getStat();
            StatSaveData statSaveData = statsContainer.getStats().get(stat);

            if (statSaveData == null) {
                statSaveData = new StatSaveData();
                statSaveData.setName(stat);
                statSaveData.setValue(0);

                statsContainer.getStats().put(stat, statSaveData);
            }

            statSaveData.setValue(statSaveData.getValue() + effect.getValue());
        }
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
