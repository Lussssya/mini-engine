package com.bootcamp.demo.viewmodels;

import com.bootcamp.demo.data.game.SpecializationGameData;
import com.bootcamp.demo.data.game.SpecializationGameData.SpecializationType;
import com.bootcamp.demo.data.game.SpecializationsGameData;
import com.bootcamp.demo.data.save.SpecializationSaveData;
import com.bootcamp.demo.dialogs.ChooseSpecializationDialog;
import com.bootcamp.demo.dialogs.SpecializationDialog;
import com.bootcamp.demo.dialogs.core.DialogManager;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.managers.SaveManager;
import lombok.Setter;

public class ChooseSpecializationViewModel {
    private final SpecializationsGameData specializationsGameData;
    private final SpecializationSaveData specializationSaveData;
    @Setter
    private SpecializationType selectedSpecializationType;

    public ChooseSpecializationViewModel (SpecializationsGameData specializationsGameData, SpecializationSaveData specializationSaveData) {
        this.specializationsGameData = specializationsGameData;
        this.specializationSaveData = specializationSaveData;

        selectedSpecializationType = specializationSaveData.getSpecializationType();
    }

    public SpecializationGameData getSpecialization (SpecializationType specializationType) {
        return specializationsGameData.getSpecializations().get(specializationType);
    }

    public boolean isSelected (SpecializationGameData.SpecializationType specializationType) {
        return selectedSpecializationType == specializationType;
    }

    public void confirmSelection () {
        if (selectedSpecializationType != null) {
            specializationSaveData.setSpecializationType(selectedSpecializationType);
            API.get(SaveManager.class).savePlayerData();
            API.get(DialogManager.class).hide(ChooseSpecializationDialog.class);
            API.get(DialogManager.class).show(SpecializationDialog.class);
        }
    }
}
