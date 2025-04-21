package com.bootcamp.demo.data.game;

import com.badlogic.gdx.utils.XmlReader;
import lombok.Getter;

@Getter
public class StatGameData implements IGameData {
    private String name;
    private String title;
    private String identifier;

    @Override
    public void load(XmlReader.Element rootXml) {
        name = rootXml.getAttribute("name");
        title = rootXml.getAttribute("title");
        identifier = rootXml.getAttribute("identifier");
    }
}
