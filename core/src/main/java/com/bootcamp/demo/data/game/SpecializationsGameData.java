package com.bootcamp.demo.data.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import com.bootcamp.demo.data.game.SpecializationGameData.SpecializationType;
import lombok.Getter;

@Getter
public class SpecializationsGameData implements IGameData {
    private final ObjectMap<SpecializationGameData.SpecializationType, SpecializationGameData> specializations = new ObjectMap<>();

    @Override
    public void load (XmlReader.Element rootXml) {
        specializations.clear();

        final Array<XmlReader.Element> specializationsXml = rootXml.getChildrenByName("specialization");

        for (XmlReader.Element specializationXml : specializationsXml) {
            final SpecializationType type = SpecializationGameData.SpecializationType.valueOf(specializationXml.getAttribute("name"));
            final SpecializationGameData specializationGameData = new SpecializationGameData();
            specializationGameData.load(specializationXml);

            specializations.put(type, specializationGameData);
        }
    }
}
