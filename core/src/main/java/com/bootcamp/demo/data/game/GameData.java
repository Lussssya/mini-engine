package com.bootcamp.demo.data.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import lombok.Getter;

@Getter
public class GameData {
    private final XmlReader xmlReader = new XmlReader();

    private final MilitaryGearsGameData militaryGearsGameData;
    private final AccessoryGearsGameData accessoryGearsGameData;
    private final SpecializationsGameData specializationsGameData;

    public GameData () {
        militaryGearsGameData = new MilitaryGearsGameData();
        accessoryGearsGameData = new AccessoryGearsGameData();
        specializationsGameData = new SpecializationsGameData();
    }

    public void load () {
        militaryGearsGameData.load(xmlReader.parse(Gdx.files.internal("data/militaries.xml")));
        accessoryGearsGameData.load(xmlReader.parse(Gdx.files.internal("data/accessories.xml")));
        specializationsGameData.load(xmlReader.parse(Gdx.files.internal("data/specializations.xml")));
    }
}
