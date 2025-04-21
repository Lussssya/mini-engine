package com.bootcamp.demo.pages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.data.game.*;
import com.bootcamp.demo.data.save.*;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.engine.widgets.OffsetButton;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.core.APage;

public class MissionsPage extends APage {

    private StatsContainer statsContainer;
    private MilitaryGearsContainer militaryGearsContainer;
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
        final Table equipmentsSegment = constructMilitariesSegment();
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
        infoButtonImage.setScaling(Scaling.fit);

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

        public StatsContainer() {
            super(3);
            pad(15).defaults().space(30).height(60).growX();

            for (int i = 0; i < 9; i++) {
                final StatWidget widget = new StatWidget();
                add(widget);
            }
        }

        public void setData(StatsSaveData statsSaveData) {
            final Array<StatWidget> widgets = getWidgets();

            for (int i = 0; i < widgets.size; i++) {
                final StatWidget widget = widgets.get(i);
                final StatSaveData statSaveData = statsSaveData.getStats().get(i);

                widget.setData(statSaveData);
            }
        }
    }

    public static class StatWidget extends Table {
        private final Label title = Labels.make(GameFont.BOLD_24, Color.valueOf("#52483f"));
        private final Label value = Labels.make(GameFont.BOLD_24, Color.valueOf("#f5eae3"));

        public StatWidget() {
            add(title).expandX().left();
            add(value);
        }

        public void setData(StatSaveData statSaveData) {
            if (statSaveData == null) {
                return;
            }

            final StatsGameData statsGameData = API.get(GameData.class).getStatsGameData();
            final StatGameData statGameData = statsGameData.getStats().get(statSaveData.getName());

            title.setText(statGameData.getTitle());
            value.setText(statSaveData.getValue());
        }

    }

    private Table constructMilitariesSegment() {
        final Table militaryGearsSegment = constructMilitaryGearsSegment();
        final Table secondaryGearSegment = constructSecondaryGearSegment();

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#f5eae3")));
        segment.pad(30).defaults().space(30);

        segment.add(militaryGearsSegment).grow();
        segment.add(secondaryGearSegment).grow();
        return segment;
    }

    private Table constructMilitaryGearsSegment() {
        final Table setInfoSegment = constructSetInfoSegment();

        militaryGearsContainer = new MilitaryGearsContainer();

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#d1cecc")));
        segment.pad(30).defaults().space(30);

        segment.add(setInfoSegment).fillX().height(60);
        segment.row();
        segment.add(militaryGearsContainer);
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

    public static class MilitaryGearsContainer extends WidgetsContainer<MilitaryGearContainer> {
        public MilitaryGearsContainer() {
            super(3);
            defaults().space(30).size(200);

            for (int i = 0; i < 6; i++) {
                final MilitaryGearContainer widget = new MilitaryGearContainer();
                add(widget);
            }
        }

        public void setData(MilitariesSaveData militariesSaveData) {
            final Array<MilitaryGearContainer> widgets = getWidgets();

            for (int i = 0; i < widgets.size; i++) {
                final MilitaryGearContainer widget = widgets.get(i);
                final MilitarySaveData militarySaveData = militariesSaveData.getMilitaries().get(i);
                widget.setData(militarySaveData);
            }
        }
    }

    public static class MilitaryGearContainer extends BorderedTable {
        private final Image iconImage;
        private final Label levelLabel;
        private final Label tierLabel;
        private final Table starsTable;

        public MilitaryGearContainer() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#b9a391")));

            iconImage = new Image();
            iconImage.setScaling(Scaling.fit);

            levelLabel = Labels.make(GameFont.BOLD_18, Color.valueOf("#f5eae3"));
            tierLabel = Labels.make(GameFont.BOLD_18, Color.valueOf("#f5eae3"));
            starsTable = new Table();

            add(iconImage);

            // create layout with stars, level and tier
            final Table layout = new Table();
            layout.pad(15);
            layout.add(starsTable).expand().top().left();
            layout.row();
            layout.add(levelLabel).expand().bottom().left();
            layout.add(tierLabel).bottom();
            layout.setFillParent(true);

            addActor(layout);
        }

        public void setData(MilitarySaveData militarySaveData) {
            if (militarySaveData == null) {
                setEmpty();
                return;
            }

            final MilitariesGameData militariesGameData = API.get(GameData.class).getMilitariesGameData();
            final ObjectMap<String, MilitaryGameData> militaries = militariesGameData.getMilitaries();
            final MilitaryGameData militaryGameData = militaries.get(militarySaveData.getType());

            iconImage.setDrawable(militaryGameData.getIcon());
            levelLabel.setText("Lv." + militarySaveData.getLevel());
            tierLabel.setText(String.valueOf(militarySaveData.getTier()));
            updateStars(militarySaveData.getStarCount());
        }

        private void updateStars(int count) {
            starsTable.clear();
            for (int i = 0; i < count; i++) {
                Image star = new Image(Resources.getDrawable("lootPage/star-icon"));
                star.setScaling(Scaling.none);
                starsTable.add(star).size(30);
            }
        }

        public void setEmpty() {
            iconImage.setDrawable(Resources.getDrawable("empty-gear"));
            levelLabel.setText("");
            tierLabel.setText("");
            starsTable.clear();
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

    public static class TacticalsContainer extends BorderedTable {
        final WidgetsContainer<TacticalContainer> container = new WidgetsContainer<>(2);

        public TacticalsContainer() {
            container.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c8c0b9")));
            container.pad(20).defaults().space(20).grow();

            for (int i = 0; i < 4; i++) {
                final TacticalContainer widget = new TacticalContainer();
                container.add(widget);
            }

            add(container);
        }

        public void setData(TacticalsSaveData tacticalsSaveData) {
            final Array<TacticalContainer> widgets = container.getWidgets();

            for (int i = 0; i < widgets.size; i++) {
                final TacticalContainer widget = widgets.get(i);
                final TacticalSaveData tacticalSaveData = tacticalsSaveData.getTacticals().get(i);
                widget.setData(tacticalSaveData);
            }
        }
    }

    public static class TacticalContainer extends Table {

        private final Image iconImage;

        public TacticalContainer() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#a9a29c")));

            iconImage = new Image();
            iconImage.setScaling(Scaling.fit);
            add(iconImage).grow().pad(10);
        }

        public void setData(TacticalSaveData tacticalSaveData) {
            if (tacticalSaveData == null) {
                setEmpty();
                return;
            }

            final TacticalsGameData tacticalsGameData = API.get(GameData.class).getTacticalsGameData();
            final ObjectMap<String, TacticalGameData> tacticals = tacticalsGameData.getTacticals();
            final TacticalGameData tacticalGameData = tacticals.get(tacticalSaveData.getName());
            iconImage.setDrawable(tacticalGameData.getIcon());
        }

        private void setEmpty() {
            iconImage.setDrawable(null);
        }
    }

    public static class FlagContainer extends BorderedTable {
        public FlagContainer() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c8c0b9")));

            final Image flag = new Image(Resources.getDrawable("lootPage/flag-icon"));
            flag.setScaling(Scaling.fit);

            add(flag);
        }

        public void setData() {
        }
    }

    public static class PetContainer extends BorderedTable {
        public PetContainer() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c8c0b9")));

            final Image cat = new Image(Resources.getDrawable("lootPage/pet-cat-orange"));
            cat.setScaling(Scaling.fit);

            final Image home = new Image(Resources.getDrawable("lootPage/home-icon"));
            home.setScaling(Scaling.fit);

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
        statsContainer.setData(API.get(SaveData.class).getStatsSaveData());
        militaryGearsContainer.setData(API.get(SaveData.class).getMilitariesSaveData());
        tacticalsContainer.setData(API.get(SaveData.class).getTacticalsSaveData());
    }
}
