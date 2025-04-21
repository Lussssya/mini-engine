package com.bootcamp.demo.data.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import lombok.Getter;

public class MilitariesGameData implements IGameData {
    @Getter
    private final ObjectMap<String, MilitaryGameData> militaries = new ObjectMap<>();

    @Override
    public void load (XmlReader.Element rootXml) {
        militaries.clear();
        final Array<XmlReader.Element> militariesXml = rootXml.getChildrenByName("militaries");
        for (XmlReader.Element militaryXml : militariesXml) {
            final MilitaryGameData militaryGameData = new MilitaryGameData();
            militaryGameData.load(militaryXml);
            militaries.put(militaryGameData.getType(), militaryGameData);
        }
    }
}
