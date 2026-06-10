package com.bootcamp.demo.viewmodels.factories;

import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.game.SpecializationGameData;
import com.bootcamp.demo.data.save.SaveData;
import com.bootcamp.demo.data.save.SpecializationSaveData;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.viewmodels.SpecializationViewModel;

public class SpecializationViewModelFactory {
    public SpecializationViewModel create () {
        SpecializationSaveData saveData = API.get(SaveData.class).getSpecializationSaveData();
        SpecializationGameData gameData = API.get(GameData.class).getSpecializationsGameData().getSpecializations().get(saveData.getSpecializationType());
        return new SpecializationViewModel(saveData, gameData);
    }
}
