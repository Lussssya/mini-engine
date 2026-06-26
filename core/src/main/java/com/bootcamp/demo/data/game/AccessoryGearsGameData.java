package com.bootcamp.demo.data.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import lombok.Getter;

public class AccessoryGearsGameData implements IGameData {
    @Getter
    private final ObjectMap<String, AccessoryGearGameData> accessories = new ObjectMap<>();

    @Override
    public void load (XmlReader.Element rootXml) {
        accessories.clear();

        final Array<XmlReader.Element> accessoriesXml =
            rootXml.getChildrenByName("accessory");

        for (XmlReader.Element accessoryXml : accessoriesXml) {
            final AccessoryGearGameData accessory = new AccessoryGearGameData();
            accessory.load(accessoryXml);

            accessories.put(accessory.getName(), accessory);
        }
    }
}
