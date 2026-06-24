package com.bootcamp.demo.engine.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.localization.GameFont;

public class IconTextWrapper extends Table {
    private final Image icon;
    private final Label text;

    public IconTextWrapper (Image icon, Label text) {
        this.icon = icon;
        this.text = text;

        build();
    }

    public IconTextWrapper (String iconPath, String text) {
        this.icon = new Image(Resources.getDrawable(iconPath));
        this.text = Labels.make(GameFont.STROKE_20, Color.valueOf("#f5eae3"), text);

        build();
    }

    private void build () {
        setBackground(Squircle.SQUIRCLE_20.getDrawable(Color.valueOf("#00000030")));

        icon.setScaling(Scaling.fit);
        add(icon).size(40).padLeft(5).expandX();
        add(text).padRight(10);
    }

    @Override
    public void layout () {
        super.layout();

        float size = getHeight() * 1.1f;

        icon.setBounds(size * 0.3f, (getHeight() - size) / 2f, size, size);
    }
}
