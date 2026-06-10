package com.bootcamp.demo.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.data.save.SpecializationSaveData.StatType;
import com.bootcamp.demo.dialogs.ChooseSpecializationDialog.PassiveEffectsContainer;
import com.bootcamp.demo.dialogs.core.ADialog;
import com.bootcamp.demo.dialogs.core.DialogManager;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.IconTextWrapper;
import com.bootcamp.demo.engine.widgets.JuicyButton;
import com.bootcamp.demo.engine.widgets.PassiveBorderedTable;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.managers.SaveManager;
import com.bootcamp.demo.managers.SpecializationManager;
import com.bootcamp.demo.viewmodels.SpecializationViewModel;
import com.bootcamp.demo.viewmodels.factories.SpecializationViewModelFactory;

public class SpecializationDialog extends ADialog {
    private SpecializationViewModel viewModel;
    private SpecializationManager manager;
    private SpecializationViewModelFactory factory;

    private Table demonstrationContainer;
    private Table rollingContainer;

    @Override
    protected void constructContent (Table content) {
        final Table statSegment = constructStatSegment();

        demonstrationContainer = new Table();
        demonstrationContainer.add(constructDemonstrationSegment()).grow();

        rollingContainer = new Table();
        rollingContainer.add(constructRollingSegment()).grow();

        content.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#f4e7dd")));
        content.defaults().growX();

        content.add(statSegment).pad(50);
        content.row();
        content.add(demonstrationContainer).pad(50);
        content.row();
        content.add(rollingContainer);

        setCloseButtonVisible(true);
    }

    @Override
    protected void constructTitleSegment (Table titleSegment) {
        factory = new SpecializationViewModelFactory();
        viewModel = factory.create();
        manager = new SpecializationManager();

        final Label title = Labels.make(GameFont.STROKE_26, viewModel.getTitle());

        final PassiveBorderedTable titleContainer = new PassiveBorderedTable(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf(viewModel.getSpecializationType().getBackgroundColor())), Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf(viewModel.getSpecializationType().getBorderColor())));
        titleContainer.add(title).expandX().pad(15);

        titleSegment.setBackground(Squircle.SQUIRCLE_35_TOP.getDrawable(Color.valueOf("#beaea6")));
        titleSegment.add(titleContainer).expandX().pad(20);
    }

    private Table constructStatSegment () {
        final PassiveBorderedTable segment = new PassiveBorderedTable(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#feebdb")), Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#d3b6a8")));
        segment.setTouchable(Touchable.disabled);

        final PassiveEffectsContainer passiveEffectsContainer = new PassiveEffectsContainer(viewModel.getPassiveEffects());
        segment.pad(10, 50, 10, 50);
        segment.add(passiveEffectsContainer).grow();
        return segment;
    }

    private Table constructDemonstrationSegment () {
        final JuicyButton resetButton = constructResetButton();
        final JuicyButton helpButton = constructHelpButton();

        final ActiveStatWrapper atkBonus = new ActiveStatWrapper(StatType.ATK, viewModel.getAtkBonus());
        final ActiveStatWrapper hpBonus = new ActiveStatWrapper(StatType.HP, viewModel.getHpBonus());
        final ActiveStatWrapper defBonus = new ActiveStatWrapper(StatType.DEF, viewModel.getDefBonus());

        final Table firstRow = new Table();
        firstRow.padLeft(30).padRight(30);
        firstRow.add(resetButton).size(220, 120).expandX().left();
        firstRow.add(hpBonus).size(400, 80).growX().padLeft(50).padRight(50);
        firstRow.add(helpButton).size(220, 120).expandX();

        final Table secondRow = new Table();
        secondRow.add(atkBonus).size(400, 80).expandX().left();
        secondRow.add(defBonus).size(400, 80);

        final PassiveBorderedTable segment = new PassiveBorderedTable(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#feebdb")), Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#d3b6a8")));
        segment.pad(20).defaults().growX();
        segment.add(firstRow);
        segment.row();
        segment.add(secondRow).padTop(20);

        return segment;
    }

    private JuicyButton constructResetButton () {
        final Label resetButtonText = Labels.make(GameFont.STROKE_20, Color.valueOf("#f5eae3"), "Reset");

        final JuicyButton resetButton = new JuicyButton(JuicyButton.Style.ORANGE_35) {
            protected void buildInner (Table container) {
                super.buildInner(container);
                container.add(resetButtonText).expand();
            }
        };

        resetButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                API.get(DialogManager.class).show(SpecializationResetDialog.class);
            }
        });

        return resetButton;
    }

    private JuicyButton constructHelpButton () {
        final Label helpButtonText = Labels.make(GameFont.STROKE_20, Color.valueOf("#f5eae3"), "Help");

        final JuicyButton helpButton = new JuicyButton(JuicyButton.Style.BLUE_35) {
            protected void buildInner (Table container) {
                super.buildInner(container);
                container.add(helpButtonText).expand();
            }
        };

        helpButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                API.get(DialogManager.class).show(SpecializationHelpDialog.class);
            }
        });

        return helpButton;
    }

    private Table constructRollingSegment () {
        final Table progressBarSegment = constructProgressBar();
        final Table buttonsSegment = constructButtonsSegment();

        final Table sliderButtonsWrapper = new Table();
        sliderButtonsWrapper.setBackground(Squircle.SQUIRCLE_35_BTM.getDrawable(Color.valueOf("#beaea6")));
        sliderButtonsWrapper.pad(30, 10, 10, 10);
        sliderButtonsWrapper.add(progressBarSegment).growX();
        sliderButtonsWrapper.row();
        sliderButtonsWrapper.add(buttonsSegment).growX().padTop(30);

        final Table segment = new Table();

        if (viewModel.getCurrentRarity() != null) {
            final Table currentStatSegment = constructCurrentStatSegment();

            segment.add(currentStatSegment).width(600);
            segment.row();
        }

        segment.add(sliderButtonsWrapper).grow();

        return segment;
    }

    private Table constructCurrentStatSegment () {
        final Label rarity = Labels.make(GameFont.STROKE_22, Color.valueOf(viewModel.getCurrentRarityColor()), viewModel.getCurrentRarity());
        final Image icon = new Image(Resources.getDrawable(viewModel.getCurrentStatIcon()));
        final Label bonusType = Labels.make(GameFont.STROKE_20, viewModel.getCurrentStatLabel() + ":");
        final Label bonusValue = Labels.make(GameFont.STROKE_24, Color.valueOf("#6dc536"), String.format("+%.2f%%", viewModel.getCurrentBonus()));

        final Table iconBonusWrapper = new Table();
        iconBonusWrapper.add(icon);
        iconBonusWrapper.add(bonusType);
        iconBonusWrapper.add(bonusValue).expandX().right();

        final PassiveBorderedTable segment = new PassiveBorderedTable(Squircle.SQUIRCLE_50.getDrawable(Color.valueOf("#feebdb")), Squircle.SQUIRCLE_50_BORDER.getDrawable(Color.valueOf("#d3b6a8")));

        segment.pad(10, 100, 10, 100);
        segment.add(rarity).padTop(10).expandX();
        segment.row();
        segment.add(iconBonusWrapper).growX();

        final Table segmentWrapper = new Table();
        segmentWrapper.setBackground(Squircle.SQUIRCLE_50_TOP.getDrawable(Color.valueOf("#beaea6")));
        segmentWrapper.pad(5, 30, 0, 30);
        segmentWrapper.add(segment).grow();

        return segmentWrapper;
    }

    private Table constructProgressBar () {
        final Label title = Labels.make(GameFont.BOLD_20, "Roll Points");

        final Table titleContainer = new Table();
        titleContainer.setBackground(Squircle.SQUIRCLE_35_TOP.getDrawable(Color.valueOf("#6b5b53")));
        titleContainer.add(title).expand().top().padTop(5);

        SliderStyle sliderStyle = new SliderStyle();
        sliderStyle.background = Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#f5e7de"));
        sliderStyle.knob = Squircle.SQUIRCLE_35.getDrawable(Color.CLEAR);

        if (viewModel.getRollPoints() == 0) {
            sliderStyle.knobBefore = Squircle.SQUIRCLE_35.getDrawable(Color.CLEAR);
        } else {
            sliderStyle.knobBefore = Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#7ed97b"));
        }

        final Slider progressBar = new Slider(0, viewModel.getMaxRollPoints(), 1, false, sliderStyle);
        progressBar.setValue(viewModel.getRollPoints());
        progressBar.setDisabled(true);

        final Label progressText = Labels.make(GameFont.STROKE_20, String.format("%d/%d", viewModel.getRollPoints(), viewModel.getMaxRollPoints()));
        final Table progressTextWrapper = new Table();
        progressTextWrapper.add(progressText).expand();
        progressTextWrapper.setFillParent(true);

        final PassiveBorderedTable progressBarWrapper = new PassiveBorderedTable(Squircle.SQUIRCLE_50.getDrawable(Color.valueOf("#f5e7de")), Squircle.SQUIRCLE_50_BORDER.getDrawable(Color.valueOf("#3a281b")));
        progressBarWrapper.add(progressBar).grow();
        progressBarWrapper.addActor(progressTextWrapper);

        final Table segment = new Table();

        segment.add(titleContainer).size(500, 60);
        segment.row();
        segment.add(progressBarWrapper).growX().height(90).padLeft(20).padRight(20);
        return segment;
    }

    private Table constructButtonsSegment () {
        final Table rerollButtonSegment = constructRerollButtonSegment("Random stat & value", "Re-Roll", "10", JuicyButton.Style.BLUE_35, () -> {
            manager.reroll();
            refreshUI();
        });
        final Table rerollStatButton = constructRerollButtonSegment("Other stat, same value", "Re-Roll Stat", "10", JuicyButton.Style.ORANGE_35, () -> {
            manager.rerollStat();
            refreshUI();
        });
        final Table acceptButton = constructRerollButtonSegment("Adds 1 roll point", "Accept & Next", "20", JuicyButton.Style.GREEN_35, () -> {
            manager.acceptRollAndNext();
            refreshUI();
        });
        final Table rollButton = constructRerollButtonSegment("Random stat & value", "Roll", "20", JuicyButton.Style.GREEN_35, () -> {
            manager.reroll();
            refreshUI();
        });

        final Table segment = new Table();

        segment.pad(30);

        if (viewModel.getCurrentRarity() == null) {
            segment.add(rollButton).expandX();
        } else {
            segment.defaults().expandX().space(30).width(320);
            segment.add(rerollButtonSegment);
            segment.add(rerollStatButton);
            segment.add(acceptButton);
        }

        return segment;
    }

    private Table constructRerollButtonSegment (String titleText, String buttonText, String costText, JuicyButton.Style buttonStyle, Runnable onClickAction) {
        final Label usageLabel = Labels.make(GameFont.BOLD_16, titleText);
        final Table usageLabelWrapper = new Table();
        usageLabelWrapper.setBackground(Squircle.SQUIRCLE_20_TOP.getDrawable(Color.valueOf("#6b5b53")));
        usageLabelWrapper.add(usageLabel).expand().top().padTop(20);

        final Label labelInsideButton = Labels.make(GameFont.STROKE_20, Color.valueOf("#f5eae3"), buttonText);
        final Table iconTextWrapper = new IconTextWrapper("lootPage/die-icon", costText);

        final JuicyButton button = new JuicyButton(buttonStyle) {
            @Override
            protected void buildInner (Table container) {
                super.buildInner(container);
                container.add(labelInsideButton).expand();
                container.row();
                container.add(iconTextWrapper).size(150, 45).padBottom(10);
            }
        };

        button.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                if (!button.isDisabled()) onClickAction.run();
            }
        });

        final Table segment = new Table();
        segment.add(usageLabelWrapper).growX().padBottom(-50).height(120);
        segment.row();
        segment.add(button).size(320, 180);

        return segment;
    }

    private void refreshUI () {
        API.get(SaveManager.class).savePlayerData();

        viewModel = factory.create();

        demonstrationContainer.clearChildren();
        demonstrationContainer.add(constructDemonstrationSegment()).grow();

        rollingContainer.clearChildren();
        rollingContainer.add(constructRollingSegment()).grow();
    }

    public static class ActiveStatWrapper extends Table {
        final Image icon;
        final Label statTitle;
        final Label valueLabel;

        public ActiveStatWrapper (StatType statType, double value) {
            this.icon = new Image(Resources.getDrawable(statType.getIconPath()));
            this.statTitle = Labels.make(GameFont.STROKE_26, statType.getLabel() + ":");
            this.valueLabel = Labels.make(GameFont.STROKE_26, Color.valueOf("#6dc536"), "");

            updateValue(value);
            build();
        }

        public void updateValue (double updatedValue) {
            this.valueLabel.setText(String.format("+%,d%%", (long) Math.floor(updatedValue)));
        }

        private void build () {
            icon.setScaling(Scaling.fit);
            final Table wrapper = new Table();
            wrapper.add(icon);
            wrapper.add(statTitle);
            wrapper.add(valueLabel);

            setBackground(Squircle.SQUIRCLE_20.getDrawable(Color.valueOf("#00000030")));
            pad(5, 20, 5, 20);
            add(wrapper).expandX();
        }
    }
}
