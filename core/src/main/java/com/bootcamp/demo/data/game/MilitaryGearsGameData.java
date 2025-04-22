package com.bootcamp.demo.data.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import lombok.Getter;

@Getter
public class MilitaryGearsGameData implements IGameData {
    private final ObjectMap<String, MilitaryGearGameData> militaries = new ObjectMap<>();

    @Override
    public void load(XmlReader.Element rootXml) {
        militaries.clear();

        final Array<XmlReader.Element> militaryGearsXml = rootXml.getChildrenByName("military");

        for (XmlReader.Element tacticalXml : militaryGearsXml) {
            final MilitaryGearGameData militaryGearGameData = new MilitaryGearGameData();
            militaryGearGameData.load(tacticalXml);
            militaries.put(militaryGearGameData.getName(), militaryGearGameData);
        }
    }
}
