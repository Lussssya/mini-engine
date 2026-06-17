package com.bootcamp.demo.data.save;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveData {
    private StatsSaveData statsSaveData = new StatsSaveData();

    private MilitaryGearsSaveData militariesSaveData = new MilitaryGearsSaveData();

    private AccessoryGearsSaveData accessoryGearsSaveData = new AccessoryGearsSaveData();

    private SpecializationSaveData specializationSaveData = new SpecializationSaveData();
}
