package com.bootcamp.demo.pages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.data.game.*;
import com.bootcamp.demo.data.save.*;
import com.bootcamp.demo.dialogs.TestDialog;
import com.bootcamp.demo.dialogs.core.DialogManager;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.engine.widgets.OffsetButton;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.core.APage;
import com.bootcamp.demo.data.save.StatSaveData.StatType;
import com.bootcamp.demo.data.save.MilitaryGearSaveData.MilitarySlot;
import com.bootcamp.demo.data.save.TacticalSaveData.TacticalSlot;
import com.bootcamp.demo.pages.core.PageManager;
import lombok.Getter;
import sun.jvm.hotspot.debugger.win32.coff.TestParser;


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

            for (int i = 0; i < StatType.values().length; i++) {
                final StatWidget widget = new StatWidget();
                add(widget);
            }
        }

        public void setData(StatsSaveData statsSaveData) {
            final Array<StatWidget> widgets = getWidgets();

            int i = 0;
            for (StatType type : StatType.values()) {
                final StatSaveData statSaveData = statsSaveData.getStats().get(type);
                final StatWidget widget = widgets.get(i++);

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
                setEmpty();
                return;
            }

            final StatType type = statSaveData.getName();

            title.setText(type.getTitle());
            value.setText(statSaveData.getValue() + type.getIdentifier());
        }

        public void setEmpty() {
            title.setText(null);
            value.setText(null);
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

            for (MilitarySlot slot : MilitarySlot.values()) {
                final MilitaryGearContainer widget = new MilitaryGearContainer();
                add(widget);
            }
        }

        public void setData(MilitaryGearsSaveData militariesSaveData) {
            final Array<MilitaryGearContainer> widgets = getWidgets();
            final ObjectMap<MilitarySlot, MilitaryGearSaveData> saveDataMap = militariesSaveData.getMilitaries();

            for (int i = 0; i < widgets.size; i++) {
                MilitaryGearContainer widget = widgets.get(i);
                MilitarySlot slot = MilitarySlot.values()[i];

                MilitaryGearSaveData gearSave = saveDataMap.get(slot);
                widget.setData(gearSave);
            }
        }
    }

    public static class MilitaryGearContainer extends BorderedTable {
        private final Image iconImage;
        private final Label levelLabel;
        private final Label tierLabel;
        private final Table starsTable;
        @Getter
        private final ObjectMap<StatType, StatSaveData> militaryStats;

        public MilitaryGearContainer() {
            iconImage = new Image();
            iconImage.setScaling(Scaling.fit);

            levelLabel = Labels.make(GameFont.BOLD_18, Color.valueOf("#f5eae3"));
            tierLabel = Labels.make(GameFont.BOLD_18, Color.valueOf("#f5eae3"));
            starsTable = new Table();
            militaryStats = new ObjectMap<>();

            add(iconImage);

            final Table starsLayout = new Table();
            starsLayout.pad(15).add(starsTable).expand().top().left();
            starsLayout.setFillParent(true);

            final Table levelLayout = new Table();
            levelLayout.pad(15).add(levelLabel).expand().bottom().left();
            levelLayout.setFillParent(true);

            final Table tierLayout = new Table();
            tierLayout.pad(15).add(tierLabel).expand().bottom().right();
            tierLayout.setFillParent(true);

            addActor(starsLayout);
            addActor(levelLayout);
            addActor(tierLayout);

            setOnClick(() -> {
                final DialogManager dialogManager = API.get(DialogManager.class);
                final TestDialog dialog = API.get(DialogManager.class).getDialog(TestDialog.class);

                dialogManager.show(TestDialog.class);

                final Table currentGear = this.cloneToTable();

                final Table statsTable = new Table();
                statsTable.padBottom(30).defaults().pad(5);
                for (ObjectMap.Entry<StatType, StatSaveData> entry : militaryStats.entries()) {
                    final StatType type = entry.key;
                    final StatSaveData stat = entry.value;

                    final String text = type.name() + ": " + stat.getValue();

                    Label statLabel = Labels.make(GameFont.BOLD_18, Color.BLACK, text);
                    statsTable.row();
                    statsTable.add(statLabel).left();
                }

                final Label exit = Labels.make(GameFont.BOLD_20, Color.valueOf("#f5eae3"), "EXIT");

                final OffsetButton button = new OffsetButton(OffsetButton.Style.GREEN_35) {
                    @Override
                    protected void buildInner(Table container) {
                        super.buildInner(container);
                        container.add(exit);
                    }
                };

                final Table gearStatsWrapper = new Table();
                gearStatsWrapper.pad(20).defaults().space(30);
                gearStatsWrapper.add(currentGear).size(300);
                gearStatsWrapper.add(statsTable);

                final Table window = new Table();
                window.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#f5eae3")));
                window.pad(20).defaults().space(30);
                window.add(gearStatsWrapper).grow();
                window.row();
                window.add(button).expandY().bottom().height(200).growX();

                final Table layout = new Table();
                layout.pad(150);
                layout.add(window).expand();
                layout.setFillParent(true);

                dialog.pad(20);
                dialog.addActor(layout);

                button.setOnClick(() -> {
                    dialogManager.hide(TestDialog.class);
                    dialogManager.dispose();
                });
            });
        }

        public void setData(MilitaryGearSaveData militarySaveData) {
            if (militarySaveData == null) {
                setEmpty();
                return;
            }

            final MilitaryGearsGameData militariesGameData = API.get(GameData.class).getMilitaryGearsGameData();
            final MilitaryGearGameData militaryGameData = militariesGameData.getMilitaries().get(militarySaveData.getName());

            iconImage.setDrawable(militaryGameData.getIcon());
            levelLabel.setText("Lv." + militarySaveData.getLevel());
            tierLabel.setText(String.valueOf(militarySaveData.getTier()));
            for (StatType statType : militarySaveData.getStats().getStats().keys()) {
                StatSaveData stat = militarySaveData.getStats().getStats().get(statType);
                militaryStats.put(statType, stat);
            }

            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf(militarySaveData.getRarity().getBackgroundHex())));
            updateStars(militarySaveData.getStarCount());
        }

        private void updateStars(int count) {
            starsTable.clear();
            for (int i = 0; i < count; i++) {
                Image star = Pools.obtain(Image.class);
                star.setDrawable(Resources.getDrawable("lootPage/star-icon"));
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

        public Table cloneToTable() {
            final Table clone = new Table();

            Image clonedIcon = new Image(iconImage.getDrawable());
            clonedIcon.setScaling(Scaling.fit);

            Label clonedLevel = Labels.make(GameFont.BOLD_18, Color.valueOf("#f5eae3"), levelLabel.getText().toString());
            Label clonedTier = Labels.make(GameFont.BOLD_18, Color.valueOf("#f5eae3"), tierLabel.getText().toString());

            Table clonedStarsTable = new Table();
            for (int i = 0; i < starsTable.getChildren().size; i++) {
                Image star = new Image(Resources.getDrawable("lootPage/star-icon"));
                star.setScaling(Scaling.none);
                clonedStarsTable.add(star).size(30);
            }

            if (getBackground() != null) {
                clone.setBackground(getBackground());
            }

            clone.add(clonedIcon).pad(5).row();

            Table starsLayout = new Table();
            starsLayout.pad(15).add(clonedStarsTable).expand().top().left();
            starsLayout.setFillParent(true);

            Table levelLayout = new Table();
            levelLayout.pad(15).add(clonedLevel).expand().bottom().left();
            levelLayout.setFillParent(true);

            Table tierLayout = new Table();
            tierLayout.pad(15).add(clonedTier).expand().bottom().right();
            tierLayout.setFillParent(true);

            clone.addActor(starsLayout);
            clone.addActor(levelLayout);
            clone.addActor(tierLayout);

            return clone;
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
            container.pad(20).defaults().uniform().space(20).grow();

            for (int i = 0; i < 4; i++) {
                final TacticalContainer widget = new TacticalContainer();
                container.add(widget);
            }

            add(container);
        }

        public void setData(TacticalsSaveData tacticalsSaveData) {
            final Array<TacticalContainer> widgets = container.getWidgets();
            TacticalSlot[] slots = TacticalSlot.values();

            for (int i = 0; i < widgets.size; i++) {
                final TacticalContainer widget = widgets.get(i);
                final TacticalSaveData tacticalSaveData = tacticalsSaveData.getTacticals().get(slots[i]);
                widget.setData(tacticalSaveData);
            }
        }
    }

    public static class TacticalContainer extends Table {
        private final Image iconImage;

        public TacticalContainer() {
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
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf(tacticalSaveData.getRarity().getBackgroundHex())));
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

            final Table buttonLayout = new Table();
            buttonLayout.add(button).expand().bottom().growX().height(150);
            buttonLayout.setFillParent(true);

            addActor(buttonLayout);
            add(cat).expandX().fillX().padTop(30);
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

        return new OffsetButton(OffsetButton.Style.ORANGE_35) {
            @Override
            protected void buildInner(Table container) {
                super.buildInner(container);
                container.pad(10);
                container.add(shovel);
                container.add(bladeHandleWrapper).growX().padRight(10);
            }
        };
    }

    private static OffsetButton constructLootButton() {
        final Label lootButtonText = Labels.make(GameFont.BOLD_22, Color.valueOf("#f5eae3"), "LOOT");

        final Image shovel = new Image(Resources.getDrawable("lootPage/shovel-icon"));
        shovel.setScaling(Scaling.fit);

        return new OffsetButton(OffsetButton.Style.GREEN_35) {
            protected void buildInner(Table container) {
                super.buildInner(container);
                container.add(lootButtonText).expandX().right();
                container.add(shovel);
            }
        };
    }

    private static OffsetButton constructAutoLootButton() {
        final Label autoLootButtonText = Labels.make(GameFont.BOLD_22, Color.valueOf("#f5eae3"), "Auto Loot");

        final Image shovel = new Image(Resources.getDrawable("lootPage/shovel-icon"));
        shovel.setScaling(Scaling.fit);

        return new OffsetButton(OffsetButton.Style.ORANGE_35) {
            protected void buildInner(Table container) {
                super.buildInner(container);
                container.add(autoLootButtonText);
                container.add(shovel);
            }
        };
    }

    @Override
    public void show(Runnable onComplete) {
        super.show(onComplete);
        statsContainer.setData(API.get(SaveData.class).getStatsSaveData());
        militaryGearsContainer.setData(API.get(SaveData.class).getMilitariesSaveData());
        tacticalsContainer.setData(API.get(SaveData.class).getTacticalsSaveData());
    }
}
