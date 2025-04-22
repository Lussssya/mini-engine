package com.bootcamp.demo.data.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import lombok.Getter;

public class GameData {
    private final XmlReader xmlReader = new XmlReader();

    @Getter
    private final TacticalsGameData tacticalsGameData;
    @Getter
    private final MilitaryGearsGameData militaryGearsGameData;

    public GameData () {
        tacticalsGameData = new TacticalsGameData();
        militaryGearsGameData = new MilitaryGearsGameData();
    }

    public void load () {
        tacticalsGameData.load(xmlReader.parse(Gdx.files.internal("data/tacticals.xml")));
        militaryGearsGameData.load(xmlReader.parse(Gdx.files.internal("data/militaries.xml")));
    }
}
