package com.bootcamp.demo.pages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.engine.widgets.OffsetButton;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.localization.GameFont;
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
        content.debugAll();
    }

    private Table constructGameUIOverlay() {
        final Table powerSegment = constructPowerSegment();

        final Table upperSegment = new Table();
        upperSegment.setBackground(Resources.getDrawable("basics/white-pixel", Color.valueOf("#e09e6b")));
        upperSegment.add(powerSegment).expandY().bottom();

        return upperSegment;
    }

    private Table constructPowerSegment() {
        final Table powerBorder = new Table();
        powerBorder.setBackground(Squircle.SQUIRCLE_35_BORDER_TOP.getDrawable(Color.valueOf("#f5eae3")));
        powerBorder.setFillParent(true);

        final Image powerImage = new Image(Resources.getDrawable("lootPage/punch-icon"));

        final Label totalPower = Labels.make(GameFont.BOLD_32, Color.valueOf("#f5eae3"));
        totalPower.setText("217k");

        final Table power = new Table();
        power.setBackground(Squircle.SQUIRCLE_35_TOP.getDrawable(Color.valueOf("#a18d7b")));
        power.addActor(powerBorder);
        power.add(powerImage).size(75).space(20);
        power.add(totalPower);

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

        final Image infoButtonImage = new Image(Resources.getDrawable("lootPage/stats-button"));
        infoButtonImage.setScaling(Scaling.stretch);

        final BorderedTable statsDetailedInfoButton = new BorderedTable();
        statsDetailedInfoButton.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#cfb6a3")));
        statsDetailedInfoButton.add(infoButtonImage).size(100);

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#b6a89c")));
        segment.pad(30).defaults().space(30);
        segment.add(statsContainer).growX();
        segment.add(statsDetailedInfoButton).size(150);
        return segment;
    }

    public static class StatsContainer extends WidgetsContainer<StatWidget> {
        private static final String[] TITLES = {"HP:", "ATK:", "DODGE:", "COMBO:", "CRIT:", "STUN:", "REGEN:", "STEAL:", "POISON:"};
        private static final String[] VALUES = {"33k", "12.3k", "34.49%", "17.3%", "6.06%", "19.56%", "12.29%", "10.53%", "9.92%"};

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

            for (int i = 0; i < widgets.size; i++) {
                widgets.get(i).setData(TITLES[i], VALUES[i]);
            }
        }
    }

    public static class StatWidget extends Table {
        final private Label title = Labels.make(GameFont.BOLD_24, Color.valueOf("#52483f"));
        final private Label value = Labels.make(GameFont.BOLD_24, Color.valueOf("#f5eae3"));

        public StatWidget() {
            add(title).expandX().left();
            add(value);
        }

        public void setData(String title, String value) {
            this.title.setText(title);
            this.value.setText(value);
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

    private Table constructSetInfoSegment() {
        final Label infoSegmentText = Labels.make(GameFont.BOLD_20, Color.valueOf("#483f3a"));
        infoSegmentText.setText("Incomplete Set");

        final Image infoButtonImage = new Image(Resources.getDrawable("lootPage/stats-button"));
        infoButtonImage.setScaling(Scaling.fit);

        final BorderedTable button = new BorderedTable();
        button.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#cfb6a3")));
        button.add(infoButtonImage).size(60);

        final Table setInformationButton = new Table();
        setInformationButton.setFillParent(true);
        setInformationButton.add(button).expand().right().size(80);

        final Table setTitleWrapper = new Table();
        setTitleWrapper.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#a9a29c")));
        setTitleWrapper.addActor(setInformationButton);
        setTitleWrapper.add(infoSegmentText);

        return setTitleWrapper;
    }

    public static class EquippedGearsContainer extends WidgetsContainer<EquippedGearContainer> {
        private static final String[] EQUIPMENT_PATH = {"star-staff", "angel-bow", "hard-armor", "magic-ring"};
        private static final String defaultPath = "empty-gear";

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
            for (int i = 0; i < 6; i++) {
                if (i < EQUIPMENT_PATH.length) {
                    widgets.get(i).setData(EQUIPMENT_PATH[i]);
                } else {
                    widgets.get(i).setData(defaultPath);
                }
            }
        }
    }

    public static class EquippedGearContainer extends BorderedTable {

        private final Image image;

        public EquippedGearContainer() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#b9a391")));

            image = new Image();
            image.setScaling(Scaling.fit);
            add(image).grow().pad(10);
        }

        public void setData (String path) {
            final Drawable iconDrawable = Resources.getDrawable("lootPage/" + path);
            image.setDrawable(iconDrawable);
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

    public static class TacticalContainer extends Table {
        public TacticalContainer() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#a9a29c")));
        }

        public void setData() {
        }
    }

    public static class FlagContainer extends BorderedTable {
        public FlagContainer() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c8c0b9")));

            final Image flag = new Image(Resources.getDrawable("lootPage/flag-icon"));
            flag.setScaling(Scaling.stretch);

            add(flag);
        }

        public void setData() {
        }
    }

    public static class PetContainer extends BorderedTable {
        public PetContainer() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c8c0b9")));

            final Image cat = new Image(Resources.getDrawable("lootPage/pet-cat-orange"));
            cat.setScaling(Scaling.stretch);

            final Image home = new Image(Resources.getDrawable("lootPage/home-icon"));
            home.setScaling(Scaling.stretch);

            final OffsetButton button = new OffsetButton(OffsetButton.Style.ORANGE_35) {
                protected void buildInner(Table container) {
                    super.buildInner(container);
                    container.add(home).size(80);
                }
            };

            add(cat).expand().bottom().fillX();
            row();
            add(button).growX().height(150);
        }

        public void setData() {
        }
    }

    private static Table constructButtonsSegment() {
        final OffsetButton upgradeButton = constructUpgradeButton();
        final OffsetButton lootButton = constructLootButton();
        final OffsetButton autoLootButton = constructAutoLootButton();

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#f5eae3")));

        segment.pad(30).defaults().uniform().space(30).height(150).growX();
        segment.add(upgradeButton);
        segment.add(lootButton);
        segment.add(autoLootButton);
        return segment;
    }

    private static OffsetButton constructUpgradeButton() {
        final Label bladeText = Labels.make(GameFont.BOLD_20, Color.valueOf("#f5eae3"));
        bladeText.setText("Lvl 17");

        final Label handleText = Labels.make(GameFont.BOLD_20, Color.valueOf("#f5eae3"));
        handleText.setText("Lvl 3");

        final Table bladeLevel = new Table();
        bladeLevel.setBackground(Squircle.SQUIRCLE_15.getDrawable(Color.valueOf("#9c7e5a")));
        bladeLevel.add(bladeText);

        final Table handleLevel = new Table();
        handleLevel.setBackground(Squircle.SQUIRCLE_15.getDrawable(Color.valueOf("#9c7e5a")));
        handleLevel.add(handleText);

        final Table bladeHandleWrapper = new Table();
        bladeHandleWrapper.defaults().space(10).height(45).growX();
        bladeHandleWrapper.add(bladeLevel);
        bladeHandleWrapper.row();
        bladeHandleWrapper.add(handleLevel);

        final Image shovel = new Image(Resources.getDrawable("lootPage/shovel-icon"));
        shovel.setScaling(Scaling.fit);

        final OffsetButton upgradeButton = new OffsetButton(OffsetButton.Style.ORANGE_35) {
            @Override
            protected void buildInner(Table container) {
                super.buildInner(container);
                container.pad(10);
                container.add(shovel);
                container.add(bladeHandleWrapper).growX().padRight(10);
            }
        };
        return upgradeButton;
    }

    private static OffsetButton constructLootButton() {
        final Label lootButtonText = Labels.make(GameFont.BOLD_22, Color.valueOf("#f5eae3"));
        lootButtonText.setText("LOOT");

        final Image shovel = new Image(Resources.getDrawable("lootPage/shovel-icon"));
        shovel.setScaling(Scaling.fit);

        final OffsetButton lootButton = new OffsetButton(OffsetButton.Style.GREEN_35) {
            protected void buildInner(Table container) {
                super.buildInner(container);
                container.add(lootButtonText).expandX().right();
                container.add(shovel);
            }
        };
        return lootButton;
    }

    private static OffsetButton constructAutoLootButton() {
        final Label autoLootButtonText = Labels.make(GameFont.BOLD_22, Color.valueOf("#f5eae3"));
        autoLootButtonText.setText("Auto Loot");

        final Image shovel = new Image(Resources.getDrawable("lootPage/shovel-icon"));
        shovel.setScaling(Scaling.fit);

        final OffsetButton autoLootButton = new OffsetButton(OffsetButton.Style.ORANGE_35) {
            protected void buildInner(Table container) {
                super.buildInner(container);
                container.add(autoLootButtonText).expandX().right();
                container.add(shovel);
            }
        };

        return autoLootButton;
    }

    @Override
    public void show(Runnable onComplete) {
        super.show(onComplete);
        statsContainer.setData();
        equippedGearsContainer.setData();
        tacticalsContainer.setData();
    }
}
