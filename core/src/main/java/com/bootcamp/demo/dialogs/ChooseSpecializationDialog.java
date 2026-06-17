package com.bootcamp.demo.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.game.PLayerStat;
import com.bootcamp.demo.data.game.SpecializationGameData;
import com.bootcamp.demo.data.game.SpecializationGameData.PassiveEffectData;
import com.bootcamp.demo.data.game.SpecializationGameData.SpecializationType;
import com.bootcamp.demo.data.save.SaveData;
import com.bootcamp.demo.dialogs.core.ADialog;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.engine.widgets.JuicyButton;
import com.bootcamp.demo.engine.widgets.PassiveBorderedTable;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.viewmodels.ChooseSpecializationViewModel;

import java.util.Locale;

public class ChooseSpecializationDialog extends ADialog {
    private ChooseSpecializationViewModel viewModel;
    private ObjectMap<SpecializationGameData.SpecializationType, BorderedTable> segments;
    private ObjectMap<SpecializationGameData.SpecializationType, Image> ticks;

    @Override
    protected void constructContent (Table content) {
        viewModel = new ChooseSpecializationViewModel(API.get(GameData.class).getSpecializationsGameData(), API.get(SaveData.class).getSpecializationSaveData());
        segments = new ObjectMap<>();
        ticks = new ObjectMap<>();

        final Stack gunnerSegment = constructSegment(SpecializationGameData.SpecializationType.GUNNER);
        final Stack denierSegment = constructSegment(SpecializationType.DENIER);
        final Stack survivorSegment = constructSegment(SpecializationGameData.SpecializationType.SURVIVOR);

        final Table buttonSegment = constructConfirmButtonSegment();

        content.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#f5e6de")));

        content.defaults().grow().spaceBottom(50);
        content.add(gunnerSegment).pad(100, 100, 0, 100);
        content.row();
        content.add(denierSegment).padLeft(100).padRight(100);
        content.row();
        content.add(survivorSegment).padLeft(100).padRight(100).spaceBottom(100);
        content.row();
        content.add(buttonSegment);

        setCloseButtonVisible(true);

        refreshSelectionVisuals();
    }

    @Override
    protected void constructTitleSegment (Table titleSegment) {
        final Table emptyTable = new Table();

        final Label title = Labels.make(GameFont.STROKE_26, "Choose Class");

        final PassiveBorderedTable titleContainer = new PassiveBorderedTable(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#ffbc57")), Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#a45829")));
        titleContainer.add(title).expandX().pad(15);

        final Table titleWrapper = new Table();
        titleWrapper.add(titleContainer).expandY().top().width(600);
        titleWrapper.setPosition(0, 20);
        titleWrapper.setFillParent(true);

        titleSegment.setBackground(Squircle.SQUIRCLE_35_TOP.getDrawable(Color.valueOf("#beaea6")));
        titleSegment.add(emptyTable).height(150);
        titleSegment.addActor(titleWrapper);
    }

    public Stack constructSegment (SpecializationGameData.SpecializationType specializationType) {
        final Label title = Labels.make(GameFont.STROKE_26, specializationType.toString().toUpperCase(Locale.ENGLISH));

        final Label biasTitle = Labels.make(GameFont.BOLD_22, Color.valueOf("#42413d"), "Roll Bias");
        final Table biasSection = constructBiasSection(specializationType);

        final Label statsTitle = Labels.make(GameFont.BOLD_22, Color.valueOf("#42413d"), "Stats");
        final Table statsSection = constructStatsSection(specializationType);

        final BorderedTable segment = new BorderedTable(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf(specializationType.getBackgroundColor())), Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf(specializationType.getBorderColor())));
        segments.put(specializationType, segment);

        segment.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                viewModel.setSelectedSpecializationType(specializationType);
                refreshSelectionVisuals();
            }
        });
        segment.pad(40);
        segment.add(title).expandX();
        segment.row();
        segment.add(biasTitle).expandX();
        segment.row();
        segment.add(biasSection).grow();
        segment.row();
        segment.add(statsTitle).expandX();
        segment.row();
        segment.add(statsSection).grow();

        final Image tick = new Image(Resources.getDrawable("ui/checked-specializationType"));
        ticks.put(specializationType, tick);
        tick.setVisible(false);

        final Table tickWrapper = new Table();
        tickWrapper.bottom().right();
        tickWrapper.add(tick).size(60).pad(20);

        final Stack stack = new Stack();
        stack.add(segment);
        stack.add(tickWrapper);

        return stack;
    }

    private void refreshSelectionVisuals () {
        for (ObjectMap.Entry<SpecializationGameData.SpecializationType, Image> entry : ticks.entries()) {
            entry.value.setVisible(viewModel.isSelected(entry.key));
        }
    }

    private Table constructBiasSection (SpecializationGameData.SpecializationType specializationType) {
        final SpecializationGameData specializationGameData = viewModel.getSpecialization(specializationType);

        final int atkWeight = specializationGameData.getAtkWeight();
        final int hpWeight = specializationGameData.getHpWeight();
        final int defWeight = specializationGameData.getDefWeight();
        final int maxWeight = Math.max(atkWeight, Math.max(hpWeight, defWeight));

        final Table atkWrapper = constructBiasRow(PLayerStat.ATK, atkWeight, maxWeight);
        final Table hpWrapper = constructBiasRow(PLayerStat.HP, hpWeight, maxWeight);
        final Table defWrapper = constructBiasRow(PLayerStat.DEF, defWeight, maxWeight);

        final Table section = new Table();
        section.pad(20).defaults().expandX().uniformX().spaceLeft(10);
        section.add(atkWrapper);
        section.add(hpWrapper);
        section.add(defWrapper);

        return section;
    }

    private Table constructBiasRow (PLayerStat statType, int value, int maxValue) {
        final Image icon = new Image(Resources.getDrawable(statType.getIconPath()));
        final Label title = Labels.make(GameFont.STROKE_24, statType.getDisplayName());
        final Label valueLabel = Labels.make(GameFont.STROKE_24, "+" + value);

        if (value == maxValue) {
            title.setColor(Color.valueOf("#6dc536"));
            valueLabel.setColor(Color.valueOf("#6dc536"));
        }

        final Table wrapper = new Table();
        wrapper.add(icon);
        wrapper.add(title).padLeft(5);
        wrapper.add(valueLabel).padLeft(5);

        return wrapper;
    }

    private Table constructStatsSection (SpecializationGameData.SpecializationType specializationType) {
        final SpecializationGameData specializationGameData = viewModel.getSpecialization(specializationType);
        final PassiveEffectsContainer passiveEffectsContainer = new PassiveEffectsContainer(specializationGameData.getEffects());

        final Table section = new Table();
        section.add(passiveEffectsContainer).grow();

        return section;
    }

    private Table constructConfirmButtonSegment () {
        final Label confirmButtonText = Labels.make(GameFont.STROKE_20, Color.valueOf("#f5eae3"), "Confirm");

        final JuicyButton confirmButton = new JuicyButton(JuicyButton.Style.GREEN_35) {
            @Override
            protected void buildInner (Table container) {
                super.buildInner(container);
                container.add(confirmButtonText).expand();
            }
        };

        confirmButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                viewModel.confirmSelection();
            }
        });

        final Table buttonWrapper = new Table();
        buttonWrapper.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#beaea6")));
        buttonWrapper.add(confirmButton).expand().fill().pad(20, 30, 0, 30);

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35_BTM.getDrawable(Color.valueOf("#beaea6")));

        segment.add(buttonWrapper).size(400, 220).padTop(-80).padBottom(40);

        return segment;
    }

    public static class PassiveEffectsContainer extends WidgetsContainer<Table> {
        public PassiveEffectsContainer (Array<PassiveEffectData> effects) {
            super(2);
            defaults().growX().space(20, 100, 20, 100);

            for (int i = 0; i < effects.size; i++) {
                final Label effectTitle = Labels.make(GameFont.STROKE_22, effects.get(i).getStat().getDisplayName());
                final Label effectValue = Labels.make(GameFont.STROKE_22, "+" + effects.get(i).getValue());

                final Table widget = new Table();
                widget.add(effectTitle).expandX().left();
                widget.add(effectValue);
                add(widget);
            }
        }
    }
}
