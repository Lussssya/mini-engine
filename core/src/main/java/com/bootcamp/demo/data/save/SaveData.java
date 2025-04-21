package com.bootcamp.demo.data.save;

import lombok.Getter;
import lombok.Setter;

@Getter
public class SaveData {
    @Setter
    private TacticalsSaveData tacticalsSaveData = new TacticalsSaveData();

    @Setter
    private StatsSaveData statsSaveData = new StatsSaveData();

    @Setter
    private MilitariesSaveData militariesSaveData = new MilitariesSaveData();
}
