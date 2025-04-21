package com.bootcamp.demo.data.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.bootcamp.demo.managers.API;
import lombok.Getter;

public class GameData {

    private final XmlReader xmlReader = new XmlReader();

    @Getter
    private final TacticalsGameData tacticalsGameData;
    @Getter
    private final StatsGameData statsGameData;
    @Getter
    private final MilitariesGameData militariesGameData;

    public GameData () {
        tacticalsGameData = new TacticalsGameData();
        statsGameData = new StatsGameData();
        militariesGameData = new MilitariesGameData();
    }

    public void load () {
        tacticalsGameData.load(xmlReader.parse(Gdx.files.internal("data/tacticals.xml")));
        statsGameData.load(xmlReader.parse(Gdx.files.internal("data/stats.xml")));
        militariesGameData.load(xmlReader.parse(Gdx.files.internal("data/militaries.xml")));
    }
}
