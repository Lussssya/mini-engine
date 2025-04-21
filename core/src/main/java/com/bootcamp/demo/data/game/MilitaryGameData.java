package com.bootcamp.demo.data.game;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.XmlReader;
import com.bootcamp.demo.engine.Resources;
import lombok.Getter;

@Getter
public class MilitaryGameData implements IGameData {
    private String type;
    private Drawable icon;

    @Override
    public void load (XmlReader.Element rootXml) {
        type = rootXml.getAttribute("type");
        icon = Resources.getDrawable(rootXml.getAttribute("icon"));
    }
}
