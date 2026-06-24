package com.bootcamp.demo.viewmodels;

import com.badlogic.gdx.utils.Array;
import com.bootcamp.demo.data.game.SpecializationGameData;
import com.bootcamp.demo.data.game.SpecializationGameData.PassiveEffectData;
import com.bootcamp.demo.data.game.SpecializationGameData.SpecializationType;
import com.bootcamp.demo.data.save.SpecializationSaveData;
import lombok.Getter;

import java.util.Locale;

@Getter
public class SpecializationViewModel {
    private final String title;

    private final SpecializationType specializationType;

    private final double atkBonus;
    private final double hpBonus;
    private final double defBonus;

    private final int atkWeight;
    private final int hpWeight;
    private final int defWeight;

    private final int rollPoints;
    private final int maxRollPoints;

    private final String currentRarity;
    private final String currentRarityColor;

    private final String currentStatLabel;
    private final String currentStatIcon;

    private final double currentBonus;

    private final Array<PassiveEffectData> passiveEffects;

    public SpecializationViewModel (SpecializationSaveData saveData, SpecializationGameData gameData) {
        this.title = saveData.getSpecializationType().toString().toUpperCase(Locale.ENGLISH);

        this.specializationType = saveData.getSpecializationType();

        this.atkBonus = saveData.getAtkBonus();
        this.hpBonus = saveData.getHpBonus();
        this.defBonus = saveData.getDefBonus();

        this.atkWeight = gameData.getAtkWeight();
        this.hpWeight = gameData.getHpWeight();
        this.defWeight = gameData.getDefWeight();

        this.rollPoints = saveData.getRollPoints();
        this.maxRollPoints = gameData.getMaxRollPoints();

        if (saveData.getCurrentRarity() != null) {
            this.currentRarity = saveData.getCurrentRarity().toString().toLowerCase(Locale.ENGLISH);
            this.currentRarityColor = saveData.getCurrentRarity().getLabelColor();

            this.currentStatLabel = saveData.getCurrentStatType().getDisplayName();
            this.currentStatIcon = saveData.getCurrentStatType().getIconPath();

            this.currentBonus = saveData.getCurrentBonus();
        } else {
            this.currentRarity = null;
            this.currentRarityColor = null;

            this.currentStatLabel = null;
            this.currentStatIcon = null;

            this.currentBonus = 0;
        }

        this.passiveEffects = gameData.getEffects();
    }

}
