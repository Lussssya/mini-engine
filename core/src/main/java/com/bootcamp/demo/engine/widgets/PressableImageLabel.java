package com.bootcamp.demo.engine.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.localization.GameFont;
import lombok.Getter;

public class PressableImageLabel extends PressableTable {
    @Getter
    private final Image icon;
    private final Label text;
    private final int iconSize;

    public PressableImageLabel (Image icon, Label text, int iconSize) {
        this.icon = icon;
        this.text = text;
        this.iconSize = iconSize;

        build();
    }

    public PressableImageLabel (String iconPath, String text, int iconSize) {
        this.icon = new Image(Resources.getDrawable(iconPath));
        this.text = Labels.make(GameFont.STROKE_20, Color.valueOf("#f5eae3"), text);
        this.iconSize = iconSize;

        build();
    }

    public PressableImageLabel (Image icon, Label text) {
        this.icon = icon;
        this.text = text;
        this.iconSize = 0;

        build();
    }

    public PressableImageLabel (String iconPath, String text) {
        this.icon = new Image(Resources.getDrawable(iconPath));
        this.text = Labels.make(GameFont.STROKE_20, Color.valueOf("#f5eae3"), text);
        this.iconSize = 0;

        build();
    }


    private void build () {
        final Table iconLayout = new Table();

        if (iconSize == 0) {
            iconLayout.add(icon);
            icon.setScaling(Scaling.fit);
        } else {
            iconLayout.add(icon).size(iconSize);
            icon.setScaling(Scaling.fill);
        }

        final Table textLayout = new Table();
        textLayout.add(text).expand().bottom();

        final Stack stack = new Stack();
        stack.add(iconLayout);
        stack.add(textLayout);

        add(stack);
    }
}
