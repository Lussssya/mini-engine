package com.bootcamp.demo.pages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.pages.core.APage;

public class MissionsPage extends APage {

    private StatsContainer statsContainer;
    private EquippedGearsContainer equippedGearsContainer;
    private TacticalsContainer tacticalsContainer;

    @Override
    protected void constructContent(Table content) {
        final Table gameUIOverlay = constructGameUIOverlay();
        final Table mainUISegment = constructMainUISegment();

        // assemble
        content.add(gameUIOverlay).grow();
        content.row();
        content.add(mainUISegment).growX();
    }

    private Table constructGameUIOverlay() {
        final Table powerSegment = constructPowerSegment();

        final Table upperSegment = new Table();
        upperSegment.setBackground(Resources.getDrawable("basics/white-pixel", Color.valueOf("#e09e6b")));
        upperSegment.add(powerSegment).expandY().bottom();

        return upperSegment;
    }

    private Table constructPowerSegment() {
        final Table power = new Table();
        power.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#f5eae3")));

        final Table segment = new Table();
        segment.add(power).width(600).height(125);
        return segment;
    }

    private Table constructMainUISegment() {
        final Table statsSegment = constructStatsSegment();
        final Table equipmentsSegment = constructEquipmentsSegment();
        final Table buttonsSegment = constructButtonsSegment();

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#f5eae3")));
        segment.pad(30).defaults().space(30).growX();
        segment.add(statsSegment);
        segment.row();
        segment.add(equipmentsSegment);
        segment.row();
        segment.add(buttonsSegment);
        return segment;
    }

    private Table constructStatsSegment() {
        statsContainer = new StatsContainer();
        final BorderedTable statsDetailedInfoButton = new BorderedTable();
        statsDetailedInfoButton.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#cfb6a3")));

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#b6a89c")));
        segment.pad(30).defaults().space(30);
        segment.add(statsContainer).growX();
        segment.add(statsDetailedInfoButton).size(150);
        return segment;
    }

    public static class StatsContainer extends WidgetsContainer<StatWidget> {

        public StatsContainer() {
            super(3);
            pad(15).defaults().space(30).height(60).growX();

            for (int i = 0; i < 9; i++) {
                final StatWidget widget = new StatWidget();
                add(widget);
            }
        }

        public void setData() {
            final Array<StatWidget> widgets = getWidgets();

            for (StatWidget widget : widgets) {
                widget.setData();
            }
        }
    }

    public static class StatWidget extends Table {

        public StatWidget() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#504845")));
        }

        public void setData() {

        }
    }

    private Table constructEquipmentsSegment() {
        final Table equippedGearSegment = constructEquippedGearSegment();
        final Table secondaryGearSegment = constructSecondaryGearSegment();

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#f5eae3")));
        segment.pad(30).defaults().space(30);

        segment.add(equippedGearSegment).grow();
        segment.add(secondaryGearSegment).grow();
        return segment;
    }

    private Table constructEquippedGearSegment() {
        final Table setInfoSegment = constructSetInfoSegment();

        equippedGearsContainer = new EquippedGearsContainer();

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#d1cecc")));
        segment.pad(30).defaults().space(30);

        segment.add(setInfoSegment).fillX().height(60);
        segment.row();
        segment.add(equippedGearsContainer);
        return segment;
    }

    private Table constructSetInfoSegment () {
        final BorderedTable button = new BorderedTable();
        button.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#cfb6a3")));

        final Table setInformationButton = new Table();
        setInformationButton.setFillParent(true);
        setInformationButton.add(button).expand().right().size(80);

        final Table setTitleWrapper = new Table();
        setTitleWrapper.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#a9a29c")));
        setTitleWrapper.addActor(setInformationButton);

        return setTitleWrapper;
    }

    public static class EquippedGearsContainer extends WidgetsContainer<EquippedGearContainer> {
        public EquippedGearsContainer() {
            super(3);
            defaults().space(30).size(200);

            for (int i = 0; i < 6; i++) {
                EquippedGearContainer widget = Pools.obtain(EquippedGearContainer.class);
                add(widget);
            }
        }

        public void setData() {
            final Array<EquippedGearContainer> widgets = getWidgets();

            for (EquippedGearContainer widget : widgets) {
                widget.setData();
            }
        }
    }

    public static class EquippedGearContainer extends BorderedTable {
        public EquippedGearContainer() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#b9a391")));
            Image image = new Image(Resources.getDrawable("ui/default-square"));
            image.setScaling(Scaling.stretch);
            add(image).expand().center().size(150);
        }

        public void setData() {
        }
    }

    private Table constructSecondaryGearSegment() {
        tacticalsContainer = new TacticalsContainer();
        final FlagContainer flagContainer = new FlagContainer();
        final PetContainer petContainer = new PetContainer();

        final Table tacticalsFlagWrapper = new Table();
        tacticalsFlagWrapper.defaults().space(30);
        tacticalsFlagWrapper.add(tacticalsContainer).size(200);
        tacticalsFlagWrapper.row();
        tacticalsFlagWrapper.add(flagContainer).size(200);

        final Table secondaryGearSegment = new Table();
        secondaryGearSegment.defaults().space(30);
        secondaryGearSegment.add(tacticalsFlagWrapper);
        secondaryGearSegment.add(petContainer).fillY().width(200);

        return secondaryGearSegment;
    }

    public static class TacticalsContainer extends WidgetsContainer<TacticalContainer> {
        public TacticalsContainer() {
            super(2);
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c8c0b9")));
            pad(20).defaults().space(20).grow();

            for (int i = 0; i < 4; i++) {
                TacticalContainer widget = new TacticalContainer();
                add(widget);
            }
        }

        public void setData() {
            final Array<TacticalContainer> widgets = getWidgets();

            for (TacticalContainer widget : widgets) {
                widget.setData();
            }
        }
    }

    public static class TacticalContainer extends BorderedTable {
        public TacticalContainer() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#a9a29c")));
        }

        public void setData() {
        }
    }

    public static class FlagContainer extends BorderedTable {
        public FlagContainer() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c8c0b9")));
        }

        public void setData() {
        }
    }

    public static class PetContainer extends BorderedTable {
        public PetContainer() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c8c0b9")));

            final Table button = new Table();
            button.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#debc7c")));

            add(button).expand().bottom().growX().height(100);
        }

        public void setData() {
        }
    }

    private Table constructButtonsSegment() {
        final BorderedTable upgradeButton = new BorderedTable();
        upgradeButton.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#debc7c")));

        final BorderedTable lootButton = new BorderedTable();
        lootButton.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#a6d890")));

        final BorderedTable autoLootButton = new BorderedTable();
        autoLootButton.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c2c2c2")));

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#f5eae3")));

        segment.pad(30).defaults().space(30).growX().height(150);
        segment.add(upgradeButton);
        segment.add(lootButton);
        segment.add(autoLootButton);
        return segment;
    }

    @Override
    public void show(Runnable onComplete) {
        super.show(onComplete);
        statsContainer.setData();
        equippedGearsContainer.setData();
        tacticalsContainer.setData();
    }
}
