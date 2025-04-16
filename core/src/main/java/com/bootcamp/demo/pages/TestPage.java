package com.bootcamp.demo.pages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.pages.core.APage;

public class TestPage extends APage {
    @Override
    protected void constructContent (Table content) {
        final Table innerSquircle = new Table();
        innerSquircle.setBackground(Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.BLUE));
        innerSquircle.setFillParent(true);

        final Table borderedSquircle = new Table();
        borderedSquircle.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.ORANGE));
        borderedSquircle.addActor(innerSquircle);

        content.add(borderedSquircle).size(300);
    }
}
