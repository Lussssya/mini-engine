package com.bootcamp.demo.viewmodels.mappers;

import com.bootcamp.demo.data.save.AccessoryGearSaveData;
import com.bootcamp.demo.data.save.MilitaryGearSaveData;
import com.bootcamp.demo.data.save.StatsSaveData;
import com.bootcamp.demo.managers.MissionsManager;
import com.bootcamp.demo.viewmodels.GearViewModel;

import java.util.Locale;

public final class GearViewModelMapper {
    public static GearViewModel map (MilitaryGearSaveData data) {
        final String name = data.getName();
        final String rarity = data.getRarity().toString().toLowerCase(Locale.ENGLISH);
        final String type = data.getSlot().toString().toLowerCase(Locale.ENGLISH);

        return map(name, rarity, type, data.getGearStats());
    }

    public static GearViewModel map (AccessoryGearSaveData data) {
        final String name = data.getName();
        final String rarity = data.getRarity().toString().toLowerCase(Locale.ENGLISH);
        final String type = data.getSlot().toString().toLowerCase(Locale.ENGLISH);

        return map(name, rarity, type, data.getGearStats());
    }

    public static GearViewModel map (String name, String rarity, String type, StatsSaveData stats) {
        return new GearViewModel(name, rarity, type, String.format("%.2f", MissionsManager.computeCumulativePower(stats)));
    }
}
