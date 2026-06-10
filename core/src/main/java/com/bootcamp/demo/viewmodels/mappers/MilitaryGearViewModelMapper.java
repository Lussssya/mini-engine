package com.bootcamp.demo.viewmodels.mappers;

import com.bootcamp.demo.data.save.MilitaryGearSaveData;
import com.bootcamp.demo.managers.MissionsManager;
import com.bootcamp.demo.viewmodels.MilitaryGearViewModel;

import java.util.Locale;

public class MilitaryGearViewModelMapper {

    public static MilitaryGearViewModel map (MilitaryGearSaveData data) {
        final String name = data.getName();
        final String rarity = data.getRarity().toString().toLowerCase(Locale.ENGLISH);
        final String type = data.getSlot().toString().toLowerCase(Locale.ENGLISH);
        final String power = String.valueOf(MissionsManager.computeCumulativePower(data.getGearStats()));

        return new MilitaryGearViewModel(name, rarity, type, power);
    }
}
