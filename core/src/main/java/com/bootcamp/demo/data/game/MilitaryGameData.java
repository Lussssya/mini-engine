package com.bootcamp.demo.data.game;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.XmlReader;
import com.bootcamp.demo.engine.Resources;
import lombok.Getter;

public class MilitaryGameData implements IGameData {
    @Getter
    private String type;
    @Getter
    private Drawable icon;

    @Override
    public void load (XmlReader.Element rootXml) {
        type = rootXml.getAttribute("type");
        icon = Resources.getDrawable(rootXml.getAttribute("icon"));
    }
}
