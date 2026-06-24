package com.bootcamp.demo.managers;

import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.game.PLayerStat;
import com.bootcamp.demo.data.game.SpecializationGameData;
import com.bootcamp.demo.data.save.SaveData;
import com.bootcamp.demo.data.save.SpecializationSaveData;
import com.bootcamp.demo.data.game.SpecializationGameData.Rarity;

import java.util.Random;

public class SpecializationManager {
    private static final double MAXIMUM_BONUS_EPSILON = 0.0001;

    private final SpecializationSaveData saveData;
    private final SpecializationGameData specialization;
    private final Random rand = new Random();

    public SpecializationManager () {
        this.saveData = API.get(SaveData .class).getSpecializationSaveData();
        this.specialization =  API.get(GameData .class).getSpecializationsGameData().getSpecializations().get(saveData.getSpecializationType());
    }

    public void reroll () {
        if (isRollPointsMaxed()) {
            clearCurrentRoll();
            return;
        }

        final PLayerStat stat = getRandomStat();

        final Rarity rarity = rollRarity();
        final double rollBaseValue = calculateRollBaseValue(rarity);
        final double value = calculateRollBonus(rollBaseValue, stat);

        setCurrentRoll(stat, value, rollBaseValue, rarity);
    }

    private PLayerStat getRandomStat () {
        final int atkWeight = getAvailableWeight(PLayerStat.ATK, specialization.getAtkWeight());
        final int hpWeight = getAvailableWeight(PLayerStat.HP, specialization.getHpWeight());
        final int defWeight = getAvailableWeight(PLayerStat.DEF, specialization.getDefWeight());

        final int totalWeight = atkWeight + hpWeight + defWeight;

        if (totalWeight == 0) {
            return getRandomStatIgnoringMaximum();
        }

        return rollStat(atkWeight, hpWeight, defWeight);
    }

    private PLayerStat rollStat (int atkWeight, int hpWeight, int defWeight) {
        final int roll = rand.nextInt(atkWeight + hpWeight + defWeight);

        if (roll < atkWeight) {
            return PLayerStat.ATK;
        }
        if (roll < atkWeight + hpWeight) {
            return PLayerStat.HP;
        }
        return PLayerStat.DEF;
    }

    private int getAvailableWeight (PLayerStat statType, int weight) {
        return getRemainingBonus(statType) > MAXIMUM_BONUS_EPSILON ? weight : 0;
    }

    private PLayerStat getRandomStatIgnoringMaximum () {
        final int atkWeight = specialization.getAtkWeight();
        final int hpWeight = specialization.getHpWeight();
        final int defWeight = specialization.getDefWeight();

        return rollStat(atkWeight, hpWeight, defWeight);
    }

    private Rarity rollRarity () {
        final double roll = rand.nextDouble();

        if (roll < 0.02) {
            return Rarity.EXTRADIMENSIONAL;
        }
        if (roll < 0.07) {
            return Rarity.ULTIMATE;
        }
        if (roll < 0.27) {
            return Rarity.LEGENDARY;
        }
        if (roll < 0.80) {
            return Rarity.GREAT;
        }

        return Rarity.GOOD;
    }

    private double calculateRollBaseValue (Rarity rarity) {
        final double base = calculateBaseValue();

        return base * rarity.getMultiplier() * randomVariance() / 100.0;
    }

    private double calculateRollBonus (double rollBaseValue, PLayerStat statType) {
        final double finalValue = rollBaseValue * getStatValueMultiplier(statType);
        final double remainingBonus = getRemainingBonus(statType);

        if (finalValue >= remainingBonus) {
            return remainingBonus;
        }

        return roundTo2Decimals(finalValue);
    }

    private double calculateBaseValue () {
        return calculateBaseValue(saveData.getRollPoints());
    }

    private double calculateBaseValue (int rollPoints) {
        return Math.round(0.0011 * Math.pow(rollPoints, 2.1628) + 440);
    }

    private double randomVariance () {
        return 0.95 + rand.nextDouble() * 0.10;
    }

    private double getStatValueMultiplier (PLayerStat statType) {
        final double averageWeight = (specialization.getAtkWeight() + specialization.getHpWeight() + specialization.getDefWeight()) / 3.0;

        return getStatWeight(statType) / averageWeight;
    }

    private int getStatWeight (PLayerStat statType) {
        switch (statType) {
            case ATK:
                return specialization.getAtkWeight();
            case HP:
                return specialization.getHpWeight();
            case DEF:
                return specialization.getDefWeight();
            default:
                throw new IllegalStateException("Unexpected value: " + statType);
        }
    }

    private void setCurrentRoll (PLayerStat statType, double bonus, double rollBaseValue, Rarity rarity) {
        saveData.setCurrentStatType(statType);
        saveData.setOriginalCurrentStatType(statType);
        saveData.setCurrentBonus(bonus);
        saveData.setCurrentRollBaseValue(rollBaseValue);
        saveData.setCurrentRarity(rarity);
    }

    private void clearCurrentRoll () {
        saveData.setCurrentStatType(null);
        saveData.setOriginalCurrentStatType(null);
        saveData.setCurrentBonus(0);
        saveData.setCurrentRollBaseValue(0);
        saveData.setCurrentRarity(null);
    }

    private double roundTo2Decimals (double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    public void rerollStat () {
        if (saveData.getCurrentRarity() == null || isRollPointsMaxed()) {
            clearCurrentRoll();
            return;
        }

        final PLayerStat stat = getRandomStatExceptOriginal();
        if (stat == null) {
            clearCurrentRoll();
            return;
        }

        saveData.setCurrentStatType(stat);
        saveData.setCurrentBonus(calculateRollBonus(getCurrentRollBaseValue(), stat));
    }

    private double getCurrentRollBaseValue () {
        if (saveData.getCurrentRollBaseValue() > 0) {
            return saveData.getCurrentRollBaseValue();
        }

        return saveData.getCurrentBonus() / getStatValueMultiplier(saveData.getCurrentStatType());
    }

    private PLayerStat getRandomStatExceptOriginal () {
        final PLayerStat excludedStat = saveData.getOriginalCurrentStatType() == null
            ? saveData.getCurrentStatType()
            : saveData.getOriginalCurrentStatType();
        PLayerStat stat = getRandomStatExcept(excludedStat, saveData.getCurrentStatType());

        if (stat == null) {
            stat = getRandomStatExcept(excludedStat, null);
        }

        return stat;
    }

    private PLayerStat getRandomStatExcept (PLayerStat excludedStat, PLayerStat secondExcludedStat) {
        final int atkWeight = isExcluded(PLayerStat.ATK, excludedStat, secondExcludedStat) ? 0 : getAvailableWeight(PLayerStat.ATK, specialization.getAtkWeight());
        final int hpWeight = isExcluded(PLayerStat.HP, excludedStat, secondExcludedStat) ? 0 : getAvailableWeight(PLayerStat.HP, specialization.getHpWeight());
        final int defWeight = isExcluded(PLayerStat.DEF, excludedStat, secondExcludedStat) ? 0 : getAvailableWeight(PLayerStat.DEF, specialization.getDefWeight());
        final int totalWeight = atkWeight + hpWeight + defWeight;

        if (totalWeight > 0) {
            return rollStat(atkWeight, hpWeight, defWeight);
        }

        return null;
    }

    private boolean isExcluded (PLayerStat statType, PLayerStat excludedStat, PLayerStat secondExcludedStat) {
        return statType == excludedStat || statType == secondExcludedStat;
    }

    public void acceptRollAndNext () {
        switch (saveData.getCurrentStatType()) {
            case ATK:
                saveData.setAtkBonus(addBonus(saveData.getAtkBonus(), saveData.getCurrentBonus()));
                break;
            case HP:
                saveData.setHpBonus(addBonus(saveData.getHpBonus(), saveData.getCurrentBonus()));
                break;
            case DEF:
                saveData.setDefBonus(addBonus(saveData.getDefBonus(), saveData.getCurrentBonus()));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + saveData.getCurrentStatType());
        }

        incrementRollPoints();

        if (isRollPointsMaxed()) {
            clearCurrentRoll();
        } else {
            reroll();
        }
    }

    private double addBonus (double currentBonus, double bonusToAdd) {
        final double updatedBonus = currentBonus + bonusToAdd;

        if (updatedBonus >= getMaximumBonus() - MAXIMUM_BONUS_EPSILON) {
            return getMaximumBonus();
        }

        return updatedBonus;
    }

    private double getRemainingBonus (PLayerStat statType) {
        switch (statType) {
            case ATK:
                return getRemainingBonus(saveData.getAtkBonus());
            case HP:
                return getRemainingBonus(saveData.getHpBonus());
            case DEF:
                return getRemainingBonus(saveData.getDefBonus());
            default:
                throw new IllegalStateException("Unexpected value: " + statType);
        }
    }

    private double getRemainingBonus (double bonus) {
        final double remainingBonus = getMaximumBonus() - bonus;

        return remainingBonus <= MAXIMUM_BONUS_EPSILON ? 0 : remainingBonus;
    }

    private boolean isRollPointsMaxed () {
        return saveData.getRollPoints() >= specialization.getMaxRollPoints();
    }

    private double getMaximumBonus () {
        return calculateBaseValue(specialization.getMaxRollPoints());
    }

    public void incrementRollPoints () {
        addRollPoints(1);
    }

    public void addRollPoints (int count) {
        saveData.setRollPoints(Math.min(saveData.getRollPoints() + count, specialization.getMaxRollPoints()));
    }

}
