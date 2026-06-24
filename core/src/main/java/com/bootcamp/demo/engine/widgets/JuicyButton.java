package com.bootcamp.demo.engine.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.notification.INotificationContainer;

public class JuicyButton extends OffsetButton implements INotificationContainer {
    private Table blinkLayout;
    private Table shadow;

    public JuicyButton (Style style) {
        build(style);
    }

    @Override
    public void build () {
        super.build();

        shadow = new Table();
        shadow.setBackground(Squircle.SQUIRCLE_35.getDrawable(new Color(0f, 0f, 0f, 0.3f)));

        addActor(shadow);
    }

    @Override
    public void layout () {
        super.layout();

        if (shadow != null) {
            shadow.setBounds(-4, -10, getWidth() + 8, getHeight() / 1.5f);
            shadow.toBack();
        }
    }

    public void build (Style style) {
        build();
        setStyle(style);
    }

    public void setStyle (Style style) {
        super.setStyle(style.offsetStyle);
        addBlink(style.blinkColor);
    }

    private void addBlink (Color blinkColor) {
        if (blinkLayout != null) {
            blinkLayout.remove();
        }

        final Table blink = new Table();
        blink.setBackground(Squircle.SQUIRCLE_15.getDrawable(blinkColor));

        blinkLayout = new Table();
        blinkLayout.add(blink).expand().top().left().padLeft(15).padTop(15).height(20);

        blinkLayout.setFillParent(true);

        frontTable.addActor(blinkLayout);
    }

    public enum Style {
        GREEN_35(OffsetButton.Style.GREEN_35, Color.valueOf("#c0e9c0")),
        ORANGE_35(OffsetButton.Style.ORANGE_35, Color.valueOf("#ffd9a4")),
        BLUE_35(OffsetButton.Style.BLUE_35, Color.valueOf("#90d1f6")),
        RED_35(OffsetButton.Style.RED_35, Color.valueOf("#fd9fa5"))
        ;

        private final OffsetButton.Style offsetStyle;
        private final Color blinkColor;

        Style (OffsetButton.Style offsetStyle, Color blinkColor) {
            this.offsetStyle = offsetStyle;
            this.blinkColor = blinkColor;
        }
    }
}
