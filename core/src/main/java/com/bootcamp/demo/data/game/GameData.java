package com.bootcamp.demo.data.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import lombok.Getter;

@Getter
public class GameData {
    private final XmlReader xmlReader = new XmlReader();

    private final MilitaryGearsGameData militaryGearsGameData;
    private final AccessoryGearsGameData accessoryGearsGameData;

    public GameData () {
        militaryGearsGameData = new MilitaryGearsGameData();
        accessoryGearsGameData = new AccessoryGearsGameData();
    }

    public void load () {
        militaryGearsGameData.load(xmlReader.parse(Gdx.files.internal("data/militaries.xml")));
        accessoryGearsGameData.load(xmlReader.parse(Gdx.files.internal("data/accessories.xml")));
    }
}
