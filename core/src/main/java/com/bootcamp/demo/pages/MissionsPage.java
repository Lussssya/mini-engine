package com.bootcamp.demo.pages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.WidgetsList;
import com.bootcamp.demo.pages.core.APage;

public class MissionsPage extends APage {

    @Override
    protected void constructContent(Table content) {
        content.add(constructUpperSegment()).grow();
        content.row();
        content.add(constructLowerSegment()).growX();
//        content.debugAll();
    }

    private Table constructUpperSegment() {
        final Table upperSegment = new Table();
        upperSegment.setBackground(Resources.getDrawable("basics/white-pixel", Color.valueOf("#e09e6b")));
        return upperSegment;
    }

    private Table constructLowerSegment() {
        final Table lowerSegment = new Table();
        lowerSegment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#f5eae3")));

        lowerSegment.pad(30).defaults().space(30).growX();

        lowerSegment.add(constructInformationSegment());
        lowerSegment.row();
        lowerSegment.add(constructEquipmentSegment());
        lowerSegment.row();
        lowerSegment.add(constructButtonSegment());
        return lowerSegment;
    }

    // information segment
    private Table constructInformationSegment() {
        final Table informationSegment = new Table();
        informationSegment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#b6a89c")));
        informationSegment.pad(30).defaults().space(30);

        informationSegment.add(constructStatsSegment()).expand().growX();
        informationSegment.add(constructStatsButtonSegment()).size(150);
        return informationSegment;
    }

    private Table constructStatsSegment() {
        final WidgetsList<Table> statsSegment = new WidgetsList<>(3);
        statsSegment.pad(15).defaults().space(30).height(60).growX();

        for (int i = 0; i < 9; i++) {
            final Table infoCell = new Table();
            infoCell.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#504845")));
            statsSegment.add(infoCell);
        }
        return statsSegment;
    }

    private Table constructStatsButtonSegment() {
        final Table button = new Table();
        button.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#cfb6a3")));
        return button;
    }

    // gear segment
    private Table constructEquipmentSegment() {
        final Table equipmentSegment = new Table();
        equipmentSegment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#f5eae3")));
        equipmentSegment.pad(30).defaults().space(30);

        equipmentSegment.add(constructEquippedGearSegment()).grow();
        equipmentSegment.add(constructSecondaryGearSegment()).grow();
        return equipmentSegment;
    }

    private Table constructEquippedGearSegment() {
        final Table gearSegment = new Table();
        gearSegment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#d1cecc")));
        gearSegment.pad(30).defaults().space(30);

        gearSegment.add(constructIncompleteSet()).fillX().center().height(60);
        gearSegment.row();
        gearSegment.add(constructEquipment());
        return gearSegment;
    }

    private Table constructIncompleteSet() {
        final Table incompleteSet = new Table();
        incompleteSet.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#a9a29c")));

        incompleteSet.addActor(constructSetButtonActor());
        return incompleteSet;
    }

    private Table constructSetButtonActor() {
        final Table button = new Table();
        button.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#cfb6a3")));

        final Table layout = new Table();
        layout.setFillParent(true);
        layout.add(button).expand().right().size(80);
        return layout;
    }

    private Table constructEquipment() {
        final WidgetsList<Table> gearSegment = new WidgetsList<>(3);
        gearSegment.defaults().space(30).size(200);

        for (int i = 0; i < 6; i++) {
            final Table gear = new Table();
            gear.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#b9a391")));
            gearSegment.add(gear);
        }
        return gearSegment;
    }

    private Table constructSecondaryGearSegment() {
        final Table firstColumn = new Table();
        firstColumn.defaults().space(30);
        firstColumn.add(constructRelicSegment()).size(200);
        firstColumn.row();
        firstColumn.add(constructFlagSegment()).size(200);

        final Table secondaryGearSegment = new Table();
        secondaryGearSegment.pad(30).defaults();

        final Table container = new Table();
        container.defaults().space(30);
        container.add(firstColumn);
        container.add(constructPetSegment()).growY().width(200);
        secondaryGearSegment.add(container);

        return secondaryGearSegment;
    }

    private Table constructRelicSegment() {
        final WidgetsList<Table> relicSegment = new WidgetsList<>(2);
        relicSegment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c8c0b9")));
        relicSegment.pad(20).defaults().space(20).grow();

        for (int i = 0; i < 4; i++) {
            final Table relic = new Table();
            relic.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#a9a29c")));
            relicSegment.add(relic);
        }

        return relicSegment;
    }

    private Table constructFlagSegment() {
        final Table flagSegment = new Table();
        flagSegment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c8c0b9")));
        return flagSegment;
    }

    private Table constructPetSegment() {
        final Table petSegment = new Table();
        petSegment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c8c0b9")));

        final Table button = new Table();
        button.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#debc7c")));

        petSegment.add(button).expand().bottom().growX().height(100);
        return petSegment;
    }

    // button segment
    private Table constructButtonSegment() {
        final Table buttonSegment = new Table();
        buttonSegment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#f5eae3")));
        buttonSegment.pad(30).defaults().space(30).growX().height(150);

        final Table upgradeButton = new Table();
        upgradeButton.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#debc7c")));
        buttonSegment.add(upgradeButton);

        final Table lootButton = new Table();
        lootButton.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#a6d890")));
        buttonSegment.add(lootButton);

        final Table autoLootButton = new Table();
        autoLootButton.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c2c2c2")));
        buttonSegment.add(autoLootButton);

        return buttonSegment;
    }
}
