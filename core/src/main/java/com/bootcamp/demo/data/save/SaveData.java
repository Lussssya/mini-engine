package com.bootcamp.demo.data.save;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SaveData {
    private TacticalsSaveData tacticalsSaveData = new TacticalsSaveData();

    private StatsSaveData statsSaveData = new StatsSaveData();

    private MilitaryGearsSaveData militariesSaveData = new MilitaryGearsSaveData();

    private PetsSaveData petsSaveData = new PetsSaveData();

    private FlagsSaveData flagsSaveData = new FlagsSaveData();
}
