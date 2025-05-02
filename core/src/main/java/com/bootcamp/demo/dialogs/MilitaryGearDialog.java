package com.bootcamp.demo.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.Stat;
import com.bootcamp.demo.data.save.MilitaryGearSaveData;
import com.bootcamp.demo.data.save.StatSaveData;
import com.bootcamp.demo.dialogs.core.ADialog;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.pages.MissionsPage;

import java.util.Locale;

public class MilitaryGearDialog extends ADialog {
    private Label name;
    private Label rarityType;
    private Label typeValue;

    public void setData (MilitaryGearSaveData data) {
        name.setText(data.getName());
        rarityType.setText(data.getRarity().toString().toLowerCase(Locale.ENGLISH));
        typeValue.setText(data.getSlot().toString().toLowerCase(Locale.ENGLISH));
    }

    @Override
    protected void constructContent (Table content) {
        content.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#cfb6a3")));
        content.setFillParent(true);

        content.pad(30);
        content.add(constructInformationSegment()).grow();
    }

    private Table constructInformationSegment () {
        name = Labels.make(GameFont.BOLD_24, Color.BLACK);

        final Table segment = new Table();
        segment.add(name).expandX();
        segment.row();
        segment.add(constructDetailsSegment()).grow();
        return segment;
    }

    private Table constructDetailsSegment () {
        final Label powerText = Labels.make(GameFont.BOLD_24, Color.BLACK, "Power:");
        final Label powerValueLabel = Labels.make(GameFont.BOLD_24, Color.BLACK, "0");
        final Table powerLayout = new Table();
        powerLayout.add(powerText).expandX().left();
        powerLayout.add(powerValueLabel);

        final Label rarityText = Labels.make(GameFont.BOLD_24, Color.BLACK, "Rarity:");
        rarityType = Labels.make(GameFont.BOLD_24, Color.BLACK);
        final Table rarityLayout = new Table();
        rarityLayout.add(rarityText).expandX().left();
        rarityLayout.add(rarityType);

        final Label typeText = Labels.make(GameFont.BOLD_24, Color.BLACK, "Type:");
        typeValue = Labels.make(GameFont.BOLD_24, Color.BLACK);
        final Table typeLayout = new Table();
        typeLayout.add(typeText).expandX().left();
        typeLayout.add(typeValue);

        final WidgetsContainer<Table> segment = new WidgetsContainer<>(1);
        segment.pad(30).defaults().space(30).growX();
        segment.add(powerLayout);
        segment.add(rarityLayout);
        segment.add(typeLayout);

        return segment;
    }
}
