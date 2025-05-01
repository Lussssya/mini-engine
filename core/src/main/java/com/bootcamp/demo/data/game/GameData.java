package com.bootcamp.demo.data.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import lombok.Getter;

public class GameData {
    @Getter
    private final XmlReader xmlReader = new XmlReader();

    @Getter
    private final TacticalsGameData tacticalsGameData;
    @Getter
    private final MilitaryGearsGameData militaryGearsGameData;
    @Getter
    private final PetsGameData petsGameData;
    @Getter
    private final FlagsGameData flagsGameData;

    public GameData () {
        tacticalsGameData = new TacticalsGameData();
        militaryGearsGameData = new MilitaryGearsGameData();
        petsGameData = new PetsGameData();
        flagsGameData = new FlagsGameData();
    }

    public void load () {
        tacticalsGameData.load(xmlReader.parse(Gdx.files.internal("data/tacticals.xml")));
        militaryGearsGameData.load(xmlReader.parse(Gdx.files.internal("data/militaries.xml")));
        petsGameData.load(xmlReader.parse(Gdx.files.internal("data/pets.xml")));
        flagsGameData.load(xmlReader.parse(Gdx.files.internal("data/flags.xml")));
    }
}
