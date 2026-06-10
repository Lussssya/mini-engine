package com.bootcamp.demo.managers;

import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.game.SpecializationGameData;
import com.bootcamp.demo.data.save.SaveData;
import com.bootcamp.demo.data.save.SpecializationSaveData;
import com.bootcamp.demo.data.game.SpecializationGameData.Rarity;
import com.bootcamp.demo.data.save.SpecializationSaveData.StatType;

import java.util.Random;

public class SpecializationManager {
    private final SpecializationSaveData saveData;
    private final SpecializationGameData specialization;
    private final Random rand = new Random();

    public SpecializationManager () {
        this.saveData = API.get(SaveData .class).getSpecializationSaveData();
        this.specialization =  API.get(GameData .class).getSpecializationsGameData().getSpecializations().get(saveData.getSpecializationType());
    }

    public void reroll () {
        final StatType statType = getRandomStat();

        final Rarity rarity = rollRarity();
        final double value = calculateRollBonus(rarity);

        setCurrentRoll(statType, value, rarity);
    }

    private StatType getRandomStat () {
        final int atkWeight = specialization.getAtkWeight();
        final int hpWeight = specialization.getHpWeight();
        final int defWeight = specialization.getDefWeight();

        final int totalWeight = atkWeight + hpWeight + defWeight;
        final int roll = rand.nextInt(totalWeight);

        if (roll < atkWeight) {
            return StatType.ATK;
        }
        if (roll < atkWeight + hpWeight) {
            return StatType.HP;
        }
        return StatType.DEF;
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

    private double calculateRollBonus (Rarity rarity) {
        final double base = calculateBaseValue();
        final double finalValue = base * rarity.getMultiplier() * randomVariance() / 100.0;

        return roundTo2Decimals(finalValue);
    }

    private double calculateBaseValue () {
        final int rollPoints = saveData.getRollPoints();

        return Math.round(0.0011 * Math.pow(rollPoints, 2.1628) + 440);
    }

    private double randomVariance () {
        return 0.95 + rand.nextDouble() * 0.10;
    }

    private void setCurrentRoll (StatType statType, double bonus, Rarity rarity) {
        saveData.setCurrentStatType(statType);
        saveData.setCurrentBonus(bonus);
        saveData.setCurrentRarity(rarity);
    }

    private double roundTo2Decimals (double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    // TODO: fix later
    public void rerollStat () {
        saveData.setCurrentStatType(getRandomStatExcept(saveData.getCurrentStatType()));
    }

    private StatType getRandomStatExcept (StatType excludedStat) {
        StatType next;

        do {
            next = getRandomStat();
        } while (next == excludedStat);

        return next;
    }

    public void acceptRollAndNext () {
        switch (saveData.getCurrentStatType()) {
            case ATK:
                saveData.setAtkBonus(saveData.getAtkBonus() + saveData.getCurrentBonus());
                break;
            case HP:
                saveData.setHpBonus(saveData.getHpBonus() + saveData.getCurrentBonus());
                break;
            case DEF:
                saveData.setDefBonus(saveData.getDefBonus() + saveData.getCurrentBonus());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + saveData.getCurrentStatType());
        }

        incrementRollPoints();
        reroll();
    }

    public void incrementRollPoints () {
        addRollPoints(1);
    }

    public void addRollPoints (int count) {
        final int cap = getRollPointCap(110); // player level = 110 (example)

        saveData.setRollPoints(
            Math.min(saveData.getRollPoints() + count, cap)
        );
    }

    public int getRollPointCap (int level) {
        return (int) Math.round(0.15015 * Math.pow(level - 100, 2) + 9.68655 * (level - 100) + 30);
    }

}
