package com.bootcamp.demo.data.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import lombok.Getter;

@Getter
public class FlagsGameData implements IGameData {
    private final ObjectMap<String, FlagGameData> flags = new ObjectMap<>();

    @Override
    public void load(XmlReader.Element rootXml) {
        flags.clear();

        final Array<XmlReader.Element> flagsXml = rootXml.getChildrenByName("flag");

        for (XmlReader.Element petXml : flagsXml) {
            final FlagGameData flagGameData = new FlagGameData();
            flagGameData.load(petXml);
            flags.put(flagGameData.getName(), flagGameData);
        }
    }
}
