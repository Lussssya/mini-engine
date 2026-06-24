package com.bootcamp.demo.pages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.data.game.*;
import com.bootcamp.demo.data.save.*;
import com.bootcamp.demo.dialogs.*;
import com.bootcamp.demo.dialogs.core.DialogManager;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.*;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.managers.MissionsManager;
import com.bootcamp.demo.pages.core.APage;
import com.bootcamp.demo.viewmodels.*;
import com.bootcamp.demo.viewmodels.mappers.GearViewModelMapper;
import com.bootcamp.demo.viewmodels.mappers.StatsDialogViewModelMapper;

public class MissionsPage extends APage {
    private MilitaryGearsContainer militaryGearsContainer;
    private AccessoryGearsContainer accessoryGearsContainer;

    @Override
    protected void constructContent (Table content) {
        setBackground(Resources.getDrawable("basics/white-pixel", Color.valueOf("#e09e6b")));

        final Table mainUISegment = constructMainUISegment();
        final Table buttonsSegment = constructButtonsSegment();

        // assemble
        content.add(mainUISegment).growX().expandY().bottom().padBottom(-22);
        content.row();
        content.add(buttonsSegment).growX();
    }

    private Table constructMainUISegment () {
        final Table upgradeSegment = constructUpgradeSegment();
        final Table equipmentsSegment = constructMilitariesSegment();

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35_TOP.getDrawable(Color.valueOf("#f5eae3")));
        segment.pad(30).defaults().space(30).growX();
        segment.add(upgradeSegment);
        segment.row();
        segment.add(equipmentsSegment);

        return segment;
    }

    private Table constructUpgradeSegment () {
        final PressableImageLabel tacticalButton = new PressableImageLabel("lootPage/tactical-icon", "Tactical", 170);
        final PressableImageLabel petButton = new PressableImageLabel("lootPage/pets-icon", "Pets", 170);

        final PressableImageLabel specializationButton = new PressableImageLabel("lootPage/specialization-icon", "Specialization", 180);
        specializationButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                final SpecializationSaveData saveData = API.get(SaveData.class).getSpecializationSaveData();
                if (saveData.getSpecializationType() == null) {
                    API.get(DialogManager.class).show(ChooseSpecializationDialog.class);
                } else {
                    API.get(DialogManager.class).show(SpecializationDialog.class);
                }
            }
        });

        final PressableImageLabel droneButton = new PressableImageLabel("lootPage/drone-icon", "Drone", 170);
        final PressableImageLabel soonButton = new PressableImageLabel("lootPage/lock-icon", "Soon", 100);

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_50.getDrawable(Color.valueOf("#d1c2bc")));
        segment.defaults().expandX().space(30);
        segment.add(tacticalButton).size(150);
        segment.add(petButton).size(150);
        segment.add(specializationButton).size(150);
        segment.add(droneButton).size(150);
        segment.add(soonButton).padLeft(10).size(80);
        return segment;
    }

    private Table constructInfoButtonsSegment () {
        final Image skinImage = new Image(Resources.getDrawable("lootPage/sets-icon"));
        skinImage.setColor(0, 0, 0, 0.3f);
        skinImage.setScaling(Scaling.fit);

        final BorderedTable skinsInfoButton = new BorderedTable();
        skinsInfoButton.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#cfb6a3")));
        skinsInfoButton.add(skinImage).size(100);

        final BorderedTable statsInfoButton = constructStatsInfoButton();

        final Table segment = new Table();
        segment.add(skinsInfoButton).expand();
        segment.row();
        segment.add(statsInfoButton).expand();

        return segment;
    }

    private BorderedTable constructStatsInfoButton () {
        final Image image = new Image(Resources.getDrawable("lootPage/stats-icon"));
        image.setColor(0, 0, 0, 0.3f);
        image.setScaling(Scaling.fit);

        final BorderedTable button = new BorderedTable();
        button.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#cfb6a3")));
        button.add(image).size(100);

        button.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                final StatsSaveData data = API.get(MissionsManager.class).updateStatsContainer();
                final StatsDialogViewModel viewModel = StatsDialogViewModelMapper.map(data);
                final StatsDialog dialog = API.get(DialogManager.class).getDialog(StatsDialog.class);
                dialog.setData(viewModel);

                API.get(DialogManager.class).show(StatsDialog.class);
            }
        });

        return button;
    }

    private Table constructMilitariesSegment () {
        final Table militaryTitle = constructSetInfoSegment();
        final Table accessoryTitle = constructLoadoutTitleSegment();
        final Table militaryContent = constructMilitaryGearsSegment();
        final Table accessoryContent = constructAccessoryGearsSegment();
        final Table infoSection = constructInfoButtonsSegment();

        final Table headers = new Table();
        headers.defaults().space(30);
        headers.defaults().padBottom(-25).growX();
        headers.add(militaryTitle).padRight(170);
        headers.add(accessoryTitle).height(80);

        final Table contents = new Table();
        contents.defaults().space(30).grow();
        contents.add(militaryContent);
        contents.add(accessoryContent);

        final Image dots = new Image(Resources.getDrawable("lootPage/vertical-dashed-line"));
        dots.setColor(0, 0, 0, 0.2f);
        dots.setScaling(Scaling.fillY);

        final Table segment = new Table();
        segment.add(headers).growX();
        segment.row();
        segment.add(contents).grow();
        segment.add(dots).growY().padLeft(10).padRight(10);
        segment.add(infoSection).fillY().expand();

        return segment;
    }

    private Table constructMilitaryGearsSegment () {
        militaryGearsContainer = new MilitaryGearsContainer();

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#d1c2bc")));

        segment.pad(30);
        segment.add(militaryGearsContainer).grow();

        return segment;
    }

    private Table constructAccessoryGearsSegment () {
        accessoryGearsContainer = new AccessoryGearsContainer();

        final Table segment = new Table();

        segment.pad(30);
        segment.add(accessoryGearsContainer).grow();

        return segment;
    }

    private Table constructSetInfoSegment () {
        final Label infoSegmentText = Labels.make(GameFont.BOLD_20, Color.valueOf("#7c6d67"));
        infoSegmentText.setText("Incomplete Set");

        final Table title = new Table();
        title.setBackground(Squircle.SQUIRCLE_35_TOP.getDrawable(Color.valueOf("#d1c2bc")));

        title.add(infoSegmentText).height(infoSegmentText.getPrefHeight() * 1.9f).expand().top().left().padLeft(100);

        return title;
    }

    private Table constructLoadoutTitleSegment () {
        final Image refreshIcon = new Image(Resources.getDrawable("lootPage/refresh-icon"));
        refreshIcon.setScaling(Scaling.fit);

        final BorderedTable button = new BorderedTable(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#3eb0f3")), Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#2467a1")));
        button.add(refreshIcon).pad(2);

        final Label text = Labels.make(GameFont.BOLD_20, Color.valueOf("#7c6d67"));
        text.setText("Loadout 1");

        final Table title = new Table();
        title.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#d1c2bc")));
        title.add(text).expandX();
        title.add(button).size(100);

        return title;
    }

    private Table constructButtonsSegment () {
        final JuicyButton homeButton = constructHomeButton();
        final JuicyButton autoLootButton = constructAutoLootButton();
        final JuicyButton lootButton = constructLootButton();
        final Table upgradeButtonSegment = constructUpgradeButtonSegment();

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#383331")));

        final Table front = new Table();
        front.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#453e3d")));
        front.pad(10, 40, 40, 40);
        front.defaults().pad(10);

        front.add(homeButton).size(180);
        front.add(autoLootButton).size(180);
        front.add(lootButton).size(540, 180).expandX();
        front.add(upgradeButtonSegment).height(180);

        segment.add(front).grow().padBottom(90).padTop(120);

        return segment;
    }

    private JuicyButton constructHomeButton () {
        final Image homeIcon = new Image(Resources.getDrawable("lootPage/home-icon"));
        homeIcon.setScaling(Scaling.fit);

        return new JuicyButton(JuicyButton.Style.ORANGE_35) {
            protected void buildInner (Table container) {
                super.buildInner(container);
                container.add(homeIcon).pad(5);
            }
        };
    }

    private JuicyButton constructAutoLootButton () {
        final Image shovel = new Image(Resources.getDrawable("lootPage/shovel-icon"));
        shovel.setScaling(Scaling.fit);

        return new JuicyButton(JuicyButton.Style.BLUE_35) {
            protected void buildInner (Table container) {
                super.buildInner(container);
                container.add(shovel).pad(5);
            }
        };
    }

    private JuicyButton constructLootButton () {
        final Label lootButtonText = Labels.make(GameFont.STROKE_24, Color.valueOf("#f5eae3"), "LOOT");

        final Table iconTextWrapper = new IconTextWrapper("lootPage/shovel-icon", "2.07k");

        return new JuicyButton(JuicyButton.Style.GREEN_35) {
            protected void buildInner (Table container) {
                super.buildInner(container);
                container.add(lootButtonText).expand();
                container.row();
                container.add(iconTextWrapper).expandX().height(50).width(220).padBottom(10);
            }
        };
    }

    private Table constructUpgradeButtonSegment () {
        final Table segment = new Table();

        final Image shovel = new Image(Resources.getDrawable("lootPage/shovel-icon"));
        shovel.setScaling(Scaling.fit);

        final JuicyButton button = new JuicyButton(JuicyButton.Style.BLUE_35) {
            @Override
            protected void buildInner (Table container) {
                super.buildInner(container);
                container.pad(10);
                container.add(shovel).pad(5);
            }
        };

        // example data
        final Label bladeText = Labels.make(GameFont.STROKE_20, Color.valueOf("#f5eae3"));
        bladeText.setText("lv. 17");

        final Label handleText = Labels.make(GameFont.STROKE_20, Color.valueOf("#f5eae3"));
        handleText.setText("lv. 3");

        final PassiveBorderedTable bladeLevel = new PassiveBorderedTable(Squircle.SQUIRCLE_35_RIGHT.getDrawable(Color.valueOf("#f7bb5a")), Squircle.SQUIRCLE_35_BORDER_RIGHT.getDrawable(Color.valueOf("#97602d")));
        bladeLevel.add(bladeText).pad(20, 10, 20, 30);

        final PassiveBorderedTable handleLevel = new PassiveBorderedTable(Squircle.SQUIRCLE_35_RIGHT.getDrawable(Color.valueOf("#f7bb5a")), Squircle.SQUIRCLE_35_BORDER_RIGHT.getDrawable(Color.valueOf("#97602d")));
        handleLevel.add(handleText).pad(20, 10, 20, 30);

        final Table bladeHandleWrapper = new Table();
        bladeHandleWrapper.defaults().height(60).growX();
        bladeHandleWrapper.add(bladeLevel);
        bladeHandleWrapper.row();
        bladeHandleWrapper.add(handleLevel);

        segment.add(button).padRight(-5).size(180);
        segment.add(bladeHandleWrapper).growX();

        return segment;
    }

    @Override
    public void show (Runnable onComplete) {
        super.show(onComplete);

        militaryGearsContainer.setData(API.get(SaveData.class).getMilitariesSaveData());
        accessoryGearsContainer.setData(API.get(SaveData.class).getAccessoryGearsSaveData());
    }

    public static class StatsContainer extends WidgetsContainer<StatWidget> {

        public StatsContainer (int widgetPerRow) {
            super(widgetPerRow);
            defaults().height(60).grow();

            for (int i = 0; i < PLayerStat.values().length; i++) {
                final StatWidget widget = new StatWidget(i);
                add(widget);
            }
        }

        public void setData (StatsDialogViewModel viewModel) {
            final Array<StatWidget> widgets = getWidgets();

            for (int i = 0; i < widgets.size; i++) {
                widgets.get(i).setData(viewModel.getRows().get(i));
            }
        }
    }

    public static class StatWidget extends Table {
        private final Label title = Labels.make(GameFont.BOLD_20, Color.valueOf("#52483f"));
        private final Label value = Labels.make(GameFont.BOLD_20, Color.valueOf("#fff9f2"));

        public StatWidget (int index) {
            final Color backgroundColor = index % 2 == 0 ? Color.valueOf("#dbcbc3") : Color.valueOf("#e6d6ce");
            setBackground(Resources.getDrawable("basics/white-pixel", backgroundColor));

            add(title).expandX().left().padLeft(20);
            add(value).padRight(20);
        }

        public void setData (StatRowViewModel viewModel) {
            title.setText(viewModel.getTitle());
            value.setText(viewModel.getValue());
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

    public static class MilitaryGearContainer extends GearContainer<MilitaryGearSaveData> {
        private final Label tierLabel = Labels.make(GameFont.BOLD_18, Color.valueOf("#f5eae3"));

        public MilitaryGearContainer () {
            final Table tierLayout = new Table();
            tierLayout.add(tierLabel).pad(15, 20, 15, 15).expand().top().left();
            tierLayout.setFillParent(true);

            addActor(tierLayout);
        }

        @Override
        protected void applyData (MilitaryGearSaveData saveData) {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf(saveData.getRarity().getBackgroundHex())));

            final MilitaryGearsGameData militariesGameData = API.get(GameData.class).getMilitaryGearsGameData();
            final MilitaryGearGameData militaryGameData = militariesGameData.getMilitaries().get(saveData.getName());

            icon.setDrawable(militaryGameData.getIcon());
            levelLabel.setText("Lv." + saveData.getLevel());
            tierLabel.setText(String.valueOf(saveData.getTier()));
        }

        @Override
        protected GearViewModel mapViewModel (MilitaryGearSaveData saveData) {
            return GearViewModelMapper.map(saveData);
        }

        @Override
        public void setEmpty () {
            super.setEmpty();
            levelLabel.setText("");
        }
    }

    public static class AccessoryGearsContainer extends WidgetsContainer<AccessoryGearContainer> {
        public AccessoryGearsContainer () {
            super(2);
            defaults().space(30).size(200);

            for (int i = 0; i < 4; i++) {
                final AccessoryGearContainer widget = new AccessoryGearContainer();
                add(widget);
            }
        }

        public void setData (AccessoryGearsSaveData accessoriesSaveData) {
            final Array<AccessoryGearContainer> widgets = getWidgets();
            final ObjectMap<AccessoryGearGameData.Slot, AccessoryGearSaveData> saveData = accessoriesSaveData.getAccessories();

            for (int i = 0; i < widgets.size; i++) {
                AccessoryGearContainer widget = widgets.get(i);
                AccessoryGearGameData.Slot slot = AccessoryGearGameData.Slot.values()[i];

                AccessoryGearSaveData gearSave = saveData.get(slot);
                widget.setData(gearSave);
            }
        }
    }

    public static class AccessoryGearContainer extends GearContainer<AccessoryGearSaveData> {
        @Override
        protected void applyData (AccessoryGearSaveData saveData) {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf(saveData.getRarity().getBackgroundHex())));

            final AccessoryGearsGameData accessoriesGameData = API.get(GameData.class).getAccessoryGearsGameData();
            final AccessoryGearGameData accessoryGameData = accessoriesGameData.getAccessories().get(saveData.getName());

            icon.setDrawable(accessoryGameData.getIcon());
            levelLabel.setText("Lv." + saveData.getLevel());
        }

        @Override
        protected GearViewModel mapViewModel (AccessoryGearSaveData saveData) {
            return GearViewModelMapper.map(saveData);
        }
    }

    public abstract static class GearContainer<T> extends BorderedTable {
        protected final Image icon = new Image();
        protected final Label levelLabel = Labels.make(GameFont.STROKE_18, Color.valueOf("#f5eae3"));

        public GearContainer () {
            icon.setScaling(Scaling.fit);

            add(icon);

            final Table levelLayout = new Table();
            levelLayout.add(levelLabel).pad(15).expand().bottom().right();
            levelLayout.setFillParent(true);

            addActor(levelLayout);

            setEmpty();
        }

        public final void setData (@Null T saveData) {
            if (saveData == null) {
                setEmpty();
                return;
            }

            applyData(saveData);

            setOnClick(() ->
                showGearDialog(mapViewModel(saveData))
            );
        }

        protected abstract void applyData (T saveData);

        protected abstract GearViewModel mapViewModel (T saveData);

        protected void showGearDialog (GearViewModel vm) {
            final GearDialog dialog = API.get(DialogManager.class).getDialog(GearDialog.class);
            dialog.setData(vm);
            API.get(DialogManager.class).show(GearDialog.class);
        }

        @Override
        public void setEmpty () {
            super.setEmpty();
            icon.setDrawable(Resources.getDrawable("lootPage/empty-gear"));
            levelLabel.setText("");
        }
    }
}
