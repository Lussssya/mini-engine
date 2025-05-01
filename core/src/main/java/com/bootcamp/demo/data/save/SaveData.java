package com.bootcamp.demo.data.save;

import lombok.Getter;
import lombok.Setter;


public class SaveData {
    @Setter
    @Getter
    private TacticalsSaveData tacticalsSaveData = new TacticalsSaveData();

    @Setter
    @Getter
    private StatsSaveData statsSaveData = new StatsSaveData();

    @Setter
    @Getter
    private MilitaryGearsSaveData militariesSaveData = new MilitaryGearsSaveData();

    @Setter
    @Getter
    private PetsSaveData petsSaveData = new PetsSaveData();

    @Setter
    @Getter
    private FlagsSaveData flagsSaveData = new FlagsSaveData();
}
