package com.bootcamp.demo.data.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import lombok.Getter;

public class FlagsGameData implements IGameData {
    @Getter
    private final ObjectMap<String, FlagGameData> flags = new ObjectMap<>();

    @Override
    public void load(XmlReader.Element rootXml) {
        flags.clear();

        final Array<XmlReader.Element> flagsXml = rootXml.getChildrenByName("flag");

        for (XmlReader.Element flagXml : flagsXml) {
            final FlagGameData flagGameData = new FlagGameData();
            flagGameData.load(flagXml);
            flags.put(flagGameData.getName(), flagGameData);
        }
    }
}
