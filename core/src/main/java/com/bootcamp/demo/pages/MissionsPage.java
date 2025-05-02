package com.bootcamp.demo.pages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.*;
import com.bootcamp.demo.data.game.*;
import com.bootcamp.demo.data.save.*;
import com.bootcamp.demo.dialogs.MilitaryGearDialog;
import com.bootcamp.demo.dialogs.core.DialogManager;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.engine.widgets.OffsetButton;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.managers.MissionsManager;
import com.bootcamp.demo.pages.core.APage;
import com.sun.tools.javac.util.DefinedBy;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

public class MissionsPage extends APage {
    @Getter
    private static StatsContainer statsContainer;
    private MilitaryGearsContainer militaryGearsContainer;
    private TacticalsContainer tacticalsContainer;
    private PetContainer petContainer;
    private FlagContainer flagContainer;

    @Override
    protected void constructContent (Table content) {
        setBackground(Resources.getDrawable("basics/white-pixel", Color.valueOf("#e09e6b")));

        final Table gameUIOverlay = constructGameUIOverlay();
        final Table mainUISegment = constructMainUISegment();

        // assemble
        content.add(gameUIOverlay).grow();
        content.row();
        content.add(mainUISegment).growX();
    }

    private Table constructGameUIOverlay () {
        final Table powerSegment = constructPowerSegment();

        final Table upperSegment = new Table();
        upperSegment.add(powerSegment).expandY().bottom();

        return upperSegment;
    }

    private Table constructPowerSegment () {
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

    private Table constructMainUISegment () {
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

    private Table constructStatsSegment () {
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

    private Table constructMilitariesSegment () {
        final Table militaryGearsSegment = constructMilitaryGearsSegment();
        final Table secondaryGearSegment = constructSecondaryGearSegment();

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#f5eae3")));
        segment.pad(30).defaults().space(30);

        segment.add(militaryGearsSegment).grow();
        segment.add(secondaryGearSegment).grow();
        return segment;
    }

    private Table constructMilitaryGearsSegment () {
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

    private Table constructSetInfoSegment () {
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

    private Table constructSecondaryGearSegment () {
        tacticalsContainer = new TacticalsContainer();
        petContainer = new PetContainer();
        flagContainer = new FlagContainer();

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

    private Table constructButtonsSegment () {
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

    private OffsetButton constructUpgradeButton () {
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
            protected void buildInner (Table container) {
                super.buildInner(container);
                container.pad(10);
                container.add(shovel);
                container.add(bladeHandleWrapper).growX().padRight(10);
            }
        };
    }

    private OffsetButton constructLootButton () {
        final Label lootButtonText = Labels.make(GameFont.BOLD_22, Color.valueOf("#f5eae3"), "LOOT");

        final Image shovel = new Image(Resources.getDrawable("lootPage/shovel-icon"));
        shovel.setScaling(Scaling.fit);

        return new OffsetButton(OffsetButton.Style.GREEN_35) {
            protected void buildInner (Table container) {
                super.buildInner(container);
                container.add(lootButtonText).expandX().right();
                container.add(shovel);
            }
        };
    }

    private OffsetButton constructAutoLootButton () {
        final Label autoLootButtonText = Labels.make(GameFont.BOLD_22, Color.valueOf("#f5eae3"), "Auto Loot");

        final Image shovel = new Image(Resources.getDrawable("lootPage/shovel-icon"));
        shovel.setScaling(Scaling.fit);

        return new OffsetButton(OffsetButton.Style.ORANGE_35) {
            protected void buildInner (Table container) {
                super.buildInner(container);
                container.add(autoLootButtonText);
                container.add(shovel);
            }
        };
    }

    @Override
    public void show (Runnable onComplete) {
        super.show(onComplete);
        API.get(MissionsManager.class).initializeStatsContainer();
        statsContainer.setData(API.get(MissionsManager.class).updateStatsContainer());

        militaryGearsContainer.setData(API.get(SaveData.class).getMilitariesSaveData());
        tacticalsContainer.setData(API.get(SaveData.class).getTacticalsSaveData());
        petContainer.setData(API.get(SaveData.class).getPetsSaveData());
        flagContainer.setData(API.get(SaveData.class).getFlagsSaveData());
    }

    public static class StatsContainer extends WidgetsContainer<StatWidget> {

        public StatsContainer () {
            super(3);
            pad(15).defaults().space(30).height(60).growX();

            for (int i = 0; i < Stat.values().length; i++) {
                final StatWidget widget = new StatWidget();
                add(widget);
            }
        }

        public void setData (StatsSaveData statsSaveData) {
            final Array<StatWidget> widgets = getWidgets();

            int i = 0;
            for (Stat type : Stat.values()) {
                final StatSaveData statSaveData = statsSaveData.getStats().get(type);
                final StatWidget widget = widgets.get(i++);

                widget.setData(statSaveData);
            }
        }
    }

    public static class StatWidget extends Table {
        private final Label title = Labels.make(GameFont.BOLD_24, Color.valueOf("#52483f"));
        private final Label value = Labels.make(GameFont.BOLD_24, Color.valueOf("#f5eae3"));

        public StatWidget () {
            add(title).expandX().left();
            add(value);
        }

        public void setData (@Null StatSaveData statSaveData) {
            if (statSaveData == null) {
                setEmpty();
                return;
            }

            final Stat type = statSaveData.getName();

            title.setText(type.getTitle());
            if (type.getType() == Stat.StatType.ADDITIVE) {
                value.setText(formatAdditive(statSaveData.getValue()));
            } else {
                value.setText(String.format("%.2f%%", statSaveData.getValue()));
            }
        }

        private String formatAdditive (float value) {
            if (value >= 1_000_000_000) return String.format("%.1fb", value / 1_000_000_000f);
            if (value >= 1_000_000) return String.format("%.1fm", value / 1_000_000f);
            if (value >= 1_000) return String.format("%.1fk", value / 1_000f);
            return String.format("%.0f", value);
        }

        public void setEmpty () {
            title.setText(null);
            value.setText(null);
        }
    }

    public static class MilitaryGearsContainer extends WidgetsContainer<MilitaryGearContainer> {
        public MilitaryGearsContainer () {
            super(3);
            defaults().space(30).size(200);

            for (int i = 0; i < 6; i++) {
                final MilitaryGearContainer widget = new MilitaryGearContainer();
                add(widget);
            }
        }

        public void setData (MilitaryGearsSaveData militariesSaveData) {
            final Array<MilitaryGearContainer> widgets = getWidgets();
            final ObjectMap<MilitaryGearGameData.Slot, MilitaryGearSaveData> saveData = militariesSaveData.getMilitaries();

            for (int i = 0; i < widgets.size; i++) {
                MilitaryGearContainer widget = widgets.get(i);
                MilitaryGearGameData.Slot slot = MilitaryGearGameData.Slot.values()[i];

                MilitaryGearSaveData gearSave = saveData.get(slot);
                widget.setData(gearSave);
            }
        }
    }

    public static class MilitaryGearContainer extends BorderedTable {
        private final Image icon;
        private final Label levelLabel;
        private final Label tierLabel;
        private final StarContainer starContainer = new StarContainer();

        public MilitaryGearContainer () {
            icon = new Image();
            icon.setScaling(Scaling.fit);

            levelLabel = Labels.make(GameFont.BOLD_18, Color.valueOf("#f5eae3"));
            tierLabel = Labels.make(GameFont.BOLD_18, Color.valueOf("#f5eae3"));

            add(icon);

            final Table starsLayout = new Table();
            starsLayout.pad(15).add(starContainer).expand().top().left();
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
            setEmpty();
        }

        public void setData (@Null MilitaryGearSaveData militarySaveData) {
            if (militarySaveData == null) {
                setEmpty();
                return;
            }

            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf(militarySaveData.getRarity().getBackgroundHex())));

            starContainer.setCount(militarySaveData.getStarCount());
            starContainer.createStars();

            final MilitaryGearsGameData militariesGameData = API.get(GameData.class).getMilitaryGearsGameData();
            final MilitaryGearGameData militaryGameData = militariesGameData.getMilitaries().get(militarySaveData.getName());

            icon.setDrawable(militaryGameData.getIcon());
            levelLabel.setText("Lv." + militarySaveData.getLevel());
            tierLabel.setText(String.valueOf(militarySaveData.getTier()));

            setOnClick(() -> {
                final MilitaryGearDialog dialog = API.get(DialogManager.class).getDialog(MilitaryGearDialog.class);
                dialog.setData(militarySaveData);
                API.get(DialogManager.class).show(MilitaryGearDialog.class);
            });
        }

        @Override
        public void setEmpty () {
            super.setEmpty();
            icon.setDrawable(Resources.getDrawable("lootPage/empty-gear"));
            levelLabel.setText("");
            tierLabel.setText("");
            starContainer.clear();
        }
    }

    public static class TacticalsContainer extends BorderedTable {
        final WidgetsContainer<TacticalContainer> container = new WidgetsContainer<>(2);

        public TacticalsContainer () {
            container.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c8c0b9")));
            container.pad(20).defaults().uniform().space(20).grow();

            for (int i = 0; i < 4; i++) {
                final TacticalContainer widget = new TacticalContainer();
                container.add(widget);
            }

            add(container);
        }

        public void setData (TacticalsSaveData tacticalsSaveData) {
            final Array<TacticalContainer> widgets = container.getWidgets();

            for (int i = 0; i < widgets.size; i++) {
                final TacticalContainer widget = widgets.get(i);
                final String name = tacticalsSaveData.getEquipped().get(i);
                if (tacticalsSaveData.getInventory().containsKey(name)) {
                    widget.setData(tacticalsSaveData.getInventory().get(name));
                } else {
                    widget.setEmpty();
                }
            }
        }
    }

    public static class TacticalContainer extends Table {
        private final Image icon;

        public TacticalContainer () {
            icon = new Image();
            icon.setScaling(Scaling.fit);
            add(icon).grow().pad(10);
            setEmpty();
        }

        public void setData (@Null TacticalSaveData tacticalSaveData) {
            if (tacticalSaveData == null) {
                setEmpty();
                return;
            }

            final TacticalsGameData tacticalsGameData = API.get(GameData.class).getTacticalsGameData();
            final TacticalGameData tacticalGameData = tacticalsGameData.getTacticals().get(tacticalSaveData.getName());

            icon.setDrawable(tacticalGameData.getIcon());
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf(tacticalSaveData.getRarity().getBackgroundHex())));
        }

        private void setEmpty () {
            icon.setDrawable(null);
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.GRAY));
        }
    }

    public static class FlagContainer extends BorderedTable {
        private final Image icon = new Image();

        public FlagContainer () {
            icon.setScaling(Scaling.fit);
            add(icon);

            setEmpty();
        }

        public void setData (@Null FlagsSaveData flagsSaveData) {
            if (flagsSaveData == null) {
                setEmpty();
                return;
            }
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c8c0b9")));
            final FlagsGameData flagsGameData = API.get(GameData.class).getFlagsGameData();
            final FlagGameData flagGameData = flagsGameData.getFlags().get(flagsSaveData.getEquipped());
            icon.setDrawable(flagGameData.getIcon());
        }

        @Override
        public void setEmpty () {
            super.setEmpty();
            icon.setDrawable(null);
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.GRAY));
        }
    }

    public static class PetContainer extends BorderedTable {
        private final Image icon;

        public PetContainer () {
            setPressedScale(0.95f);

            final OffsetButton button = new OffsetButton(OffsetButton.Style.ORANGE_35) {
                protected void buildInner (Table container) {
                    final Image homeIcon = new Image(Resources.getDrawable("lootPage/home-icon"));
                    homeIcon.setScaling(Scaling.fit);

                    super.buildInner(container);
                    container.add(homeIcon).size(80);
                }
            };

            final Table buttonLayout = new Table();
            buttonLayout.add(button).expand().bottom().growX().height(150);
            buttonLayout.setFillParent(true);

            icon = new Image();
            icon.setScaling(Scaling.fit);
            final Table imageLayout = new Table();
            imageLayout.add(icon).growX();
            imageLayout.setFillParent(true);

            addActor(buttonLayout);
            addActor(imageLayout);

            setEmpty();
        }

        public void setData (@Null PetsSaveData petsSaveData) {
            if (petsSaveData == null) {
                setEmpty();
                return;
            }
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c8c0b9")));

            final PetsGameData petsGameData = API.get(GameData.class).getPetsGameData();
            final PetGameData petGameData = petsGameData.getPets().get(petsSaveData.getEquipped());

            icon.setDrawable(petGameData.getIcon());
        }

        @Override
        public void setEmpty () {
            super.setEmpty();
            icon.setDrawable(null);
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.GRAY));
        }
    }

    public static class StarContainer extends WidgetsContainer<StarContainer.StarIcon> {
        @Setter
        private int count = 0;

        public StarContainer () {
            super(Integer.MAX_VALUE);
            reserveCells(false);
            createStars();
        }

        public static class StarIcon extends Table {
            Image star = new Image();

            public StarIcon () {
                star.setDrawable(Resources.getDrawable("lootPage/star-icon"));
                star.setScaling(Scaling.fit);
                add(star).size(30).space(10);
            }
        }

        public void createStars () {
            freeChildren();
            for (int i = 0; i < count; i++) {
                StarIcon starIcon = Pools.obtain(StarIcon.class);
                add(starIcon);
            }
        }
    }
}
