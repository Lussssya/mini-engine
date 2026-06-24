package com.bootcamp.demo.engine.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.bootcamp.demo.engine.Squircle;
import lombok.Getter;

public class PassiveBorderedTable extends Table {
    protected Drawable emptyBorder = Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("81776e"));
    protected Drawable emptyBackground = Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("c2b8b0"));

    @Getter
    protected final Image borderImage;

    protected int borderSize = 8;

    public PassiveBorderedTable () {
        this(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("c2b8b0")), Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("81776e")));
    }

    public PassiveBorderedTable (Drawable backgroundDrawable, Drawable borderDrawable) {
        borderImage = new Image(borderDrawable);
        borderImage.setFillParent(true);
        borderImage.setTouchable(Touchable.disabled);

        addActor(borderImage);

        setBackground(backgroundDrawable);
        pad(borderSize);
    }

    public void set (Drawable backgroundDrawable, Drawable borderDrawable) {
        setBackground(backgroundDrawable);
        setBorderDrawable(borderDrawable);
    }

    public void setBorderDrawable (Drawable borderDrawable) {
        borderImage.setDrawable(borderDrawable);
    }

    public void setEmpty () {
        setBackground(emptyBackground);
        borderImage.setDrawable(emptyBorder);
    }

    public void bringBorderFront () {
        borderImage.toFront();
    }
}
