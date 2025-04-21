package com.bootcamp.demo.data.save;

import lombok.Getter;
import lombok.Setter;

public class SaveData {

    @Getter
    private final TacticalsSaveData tacticalsSaveData = new TacticalsSaveData();

    @Setter @Getter
    private StatsSaveData statsSaveData = new StatsSaveData();

    @Setter @Getter
    private MilitariesSaveData militariesSaveData = new MilitariesSaveData();
}
