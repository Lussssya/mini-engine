package com.bootcamp.demo.data.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import lombok.Getter;

@Getter
public class GameData {
    private final XmlReader xmlReader = new XmlReader();

    private final TacticalsGameData tacticalsGameData;
    private final MilitaryGearsGameData militaryGearsGameData;
    private final PetsGameData petsGameData;
    private final FlagsGameData flagsGameData;
    private final SpecializationsGameData specializationsGameData;

    public GameData () {
        tacticalsGameData = new TacticalsGameData();
        militaryGearsGameData = new MilitaryGearsGameData();
        petsGameData = new PetsGameData();
        flagsGameData = new FlagsGameData();
        specializationsGameData = new SpecializationsGameData();
    }

    public void load () {
        tacticalsGameData.load(xmlReader.parse(Gdx.files.internal("data/tacticals.xml")));
        militaryGearsGameData.load(xmlReader.parse(Gdx.files.internal("data/militaries.xml")));
        petsGameData.load(xmlReader.parse(Gdx.files.internal("data/pets.xml")));
        flagsGameData.load(xmlReader.parse(Gdx.files.internal("data/flags.xml")));
        specializationsGameData.load(xmlReader.parse(Gdx.files.internal("data/specializations.xml")));
    }
}
