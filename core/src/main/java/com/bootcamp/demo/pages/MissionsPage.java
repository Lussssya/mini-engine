package com.bootcamp.demo.pages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
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
        final Table statsSegment = new Table();
        statsSegment.pad(15).defaults().space(30);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                final Table infoCell = new Table();
                infoCell.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#504845")));
                statsSegment.add(infoCell).growX().height(60);
            }
            if (row != 2) {
                statsSegment.row();
            }
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
        final Table table = new Table();
        table.setBackground(Squircle.SQUIRCLE_35.getDrawable());
        table.pad(30).defaults().space(30);
        table.add(constructEquippedGearSegment()).grow();
        table.add(constructSecondaryGearSegment()).grow();
        return table;
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
        final Table button = new Table();
        button.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#cfb6a3")));
        incompleteSet.add(button).expandX().right().size(80);
        return incompleteSet;
    }

    private Table constructEquipment() {
        final Table gearSegment = new Table();
        gearSegment.defaults().space(30);
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 3; col++) {
                final Table gear = new Table();
                gear.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#b9a391")));
                gearSegment.add(gear).size(200);
            }
            gearSegment.row();
        }
        return gearSegment;
    }

    private Table constructSecondaryGearSegment() {
        final Table secondaryGearSegment = new Table();
        final Table firstColumn = new Table();
        firstColumn.defaults().space(30);
        firstColumn.add(constructRelicSegment()).size(200).expand().top();
        firstColumn.row();
        firstColumn.add(constructFlagSegment()).size(200).expand().bottom();
        secondaryGearSegment.pad(30).defaults().space(30);
        secondaryGearSegment.add(firstColumn);
        secondaryGearSegment.add(constructPetSegment()).growY().width(200).padTop(30).padBottom(30);
        return secondaryGearSegment;
    }

    private Table constructRelicSegment() {
        final Table relicSegment = new Table();
        relicSegment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c8c0b9")));
        relicSegment.pad(20).defaults().space(20);
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 2; col++) {
                final Table relic = new Table();
                relic.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#a9a29c")));
                relicSegment.add(relic).grow();
            }
            relicSegment.row();
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
        buttonSegment.setBackground(Squircle.SQUIRCLE_35.getDrawable());
        buttonSegment.pad(30).defaults().space(30).growX().height(150);
        final Table upgradeButton = new Table();
        upgradeButton.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#debc7c")));
        final Table lootButton = new Table();
        lootButton.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#a6d890")));
        final Table autoLootButton = new Table();
        autoLootButton.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c2c2c2")));
        buttonSegment.add(upgradeButton);
        buttonSegment.add(lootButton);
        buttonSegment.add(autoLootButton);

        return buttonSegment;
    }
}
