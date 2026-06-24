package com.bootcamp.demo.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.data.game.PLayerStat;
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
        final Table graphicalDemonstrationSegment = constructGraphicalDemonstrationSegment();

        final ActiveStatWrapper atkBonus = new ActiveStatWrapper(PLayerStat.ATK, viewModel.getAtkBonus());
        final ActiveStatWrapper hpBonus = new ActiveStatWrapper(PLayerStat.HP, viewModel.getHpBonus());
        final ActiveStatWrapper defBonus = new ActiveStatWrapper(PLayerStat.DEF, viewModel.getDefBonus());

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
        segment.row();
        segment.add(graphicalDemonstrationSegment).height(700).growX();
        return segment;
    }

    private Table constructGraphicalDemonstrationSegment () {
        final Image triangleImage = new Image(Resources.getDrawable("lootPage/specialization_triangle"));
        triangleImage.setScaling(Scaling.fill);

        final Stack triangleStack = new Stack();
        triangleStack.add(triangleImage);
        triangleStack.add(new BonusGraphActor(viewModel.getAtkBonus(), viewModel.getHpBonus(), viewModel.getDefBonus(), viewModel.getMaximumBonus()));

        final Table segment = new Table();
        segment.add(triangleStack).grow().minSize(0);
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

        final Table sliderButtonsWrapper = new Table();
        sliderButtonsWrapper.setBackground(Squircle.SQUIRCLE_35_BTM.getDrawable(Color.valueOf("#beaea6")));
        sliderButtonsWrapper.pad(30, 10, 10, 10);
        sliderButtonsWrapper.add(progressBarSegment).growX();

        if (!viewModel.isRollPointsMaxed()) {
            final Table buttonsSegment = constructButtonsSegment();
            sliderButtonsWrapper.row();
            sliderButtonsWrapper.add(buttonsSegment).growX().padTop(30);
        }

        final Table segment = new Table();

        if (viewModel.getCurrentRarity() != null && !viewModel.isRollPointsMaxed()) {
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
        final Label bonusType = Labels.make(GameFont.STROKE_20, viewModel.getCurrentStatLabel());
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

        final Label progressText = Labels.make(GameFont.STROKE_20, String.format("%d/%d", viewModel.getRollPoints(), viewModel.getMaxRollPoints()));
        final Table progressTextWrapper = new Table();
        progressTextWrapper.add(progressText).expand();
        progressTextWrapper.setFillParent(true);

        final PassiveBorderedTable progressBarWrapper = new PassiveBorderedTable(Squircle.SQUIRCLE_50.getDrawable(Color.valueOf("#f5e7de")), Squircle.SQUIRCLE_50_BORDER.getDrawable(Color.valueOf("#3a281b")));
        progressBarWrapper.add(new RollPointsProgressActor(viewModel.getRollPoints(), viewModel.getMaxRollPoints())).grow().pad(8);
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

        public ActiveStatWrapper (PLayerStat statType, double value) {
            this.icon = new Image(Resources.getDrawable(statType.getIconPath()));
            this.statTitle = Labels.make(GameFont.STROKE_26, statType.getDisplayName());
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

    private static class BonusGraphActor extends Actor {
        private static final ShapeRenderer SHAPE_RENDERER = new ShapeRenderer();
        private static final Color FILL_COLOR = Color.valueOf("#78f477bb");
        private static final int EDGE_SEGMENTS = 12;

        private static final float CENTER_X = 0.5f;
        private static final float CENTER_Y = 0.405f;
        private static final float ATK_X = 0.215f;
        private static final float ATK_Y = 0.25f;
        private static final float HP_X = 0.5f;
        private static final float HP_Y = 0.68f;
        private static final float DEF_X = 0.785f;
        private static final float DEF_Y = 0.25f;

        private final float atkBonus;
        private final float hpBonus;
        private final float defBonus;
        private final float maximumBonus;

        private final Vector2 tmp = new Vector2();
        private final Vector2 fillCenter = new Vector2();
        private final Vector2[] vertices = makeVectors(3);
        private final Vector2[] roundedPoints = makeVectors(3 * EDGE_SEGMENTS);

        public BonusGraphActor (double atkBonus, double hpBonus, double defBonus, double maximumBonus) {
            this.maximumBonus = Math.max(1f, (float) maximumBonus);
            this.atkBonus = normalizeBonus(atkBonus);
            this.hpBonus = normalizeBonus(hpBonus);
            this.defBonus = normalizeBonus(defBonus);
            setTouchable(Touchable.disabled);
        }

        private float normalizeBonus (double bonus) {
            return Math.min(1f, Math.max(0f, (float) bonus / maximumBonus));
        }

        @Override
        public void draw (Batch batch, float parentAlpha) {
            final float centerX = getStageX(CENTER_X);
            final float centerY = getStageY(CENTER_Y);
            final float atkX = approach(centerX, getStageX(ATK_X), atkBonus);
            final float atkY = approach(centerY, getStageY(ATK_Y), atkBonus);
            final float hpX = approach(centerX, getStageX(HP_X), hpBonus);
            final float hpY = approach(centerY, getStageY(HP_Y), hpBonus);
            final float defX = approach(centerX, getStageX(DEF_X), defBonus);
            final float defY = approach(centerY, getStageY(DEF_Y), defBonus);
            final int pointCount = buildRoundedGraph(atkX, atkY, hpX, hpY, defX, defY);

            batch.end();
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            SHAPE_RENDERER.setProjectionMatrix(batch.getProjectionMatrix());
            SHAPE_RENDERER.begin(ShapeRenderer.ShapeType.Filled);
            SHAPE_RENDERER.setColor(FILL_COLOR.r, FILL_COLOR.g, FILL_COLOR.b, FILL_COLOR.a * parentAlpha);
            for (int i = 0; i < pointCount; i++) {
                final Vector2 current = roundedPoints[i];
                final Vector2 next = roundedPoints[(i + 1) % pointCount];
                SHAPE_RENDERER.triangle(fillCenter.x, fillCenter.y, current.x, current.y, next.x, next.y);
            }
            SHAPE_RENDERER.end();

            batch.begin();
        }

        private int buildRoundedGraph (float atkX, float atkY, float hpX, float hpY, float defX, float defY) {
            vertices[0].set(atkX, atkY);
            vertices[1].set(hpX, hpY);
            vertices[2].set(defX, defY);
            fillCenter.set(0f, 0f);

            int index = 0;
            for (int i = 0; i < vertices.length; i++) {
                final Vector2 previous = vertices[(i + vertices.length - 1) % vertices.length];
                final Vector2 current = vertices[i];
                final Vector2 next = vertices[(i + 1) % vertices.length];
                final Vector2 afterNext = vertices[(i + 2) % vertices.length];

                for (int segment = 0; segment < EDGE_SEGMENTS; segment++) {
                    final float t = segment / (float) EDGE_SEGMENTS;
                    final Vector2 point = roundedPoints[index++];
                    point.set(
                        catmullRom(previous.x, current.x, next.x, afterNext.x, t),
                        catmullRom(previous.y, current.y, next.y, afterNext.y, t)
                    );
                    fillCenter.add(point);
                }
            }

            fillCenter.scl(1f / index);
            return index;
        }

        private static float approach (float from, float to, float amount) {
            return from + (to - from) * amount;
        }

        private static float catmullRom (float p0, float p1, float p2, float p3, float t) {
            final float t2 = t * t;
            final float t3 = t2 * t;
            return 0.5f * (
                2f * p1
                    + (-p0 + p2) * t
                    + (2f * p0 - 5f * p1 + 4f * p2 - p3) * t2
                    + (-p0 + 3f * p1 - 3f * p2 + p3) * t3
            );
        }

        private static Vector2[] makeVectors (int count) {
            final Vector2[] vectors = new Vector2[count];
            for (int i = 0; i < count; i++) {
                vectors[i] = new Vector2();
            }
            return vectors;
        }

        private float getStageX (float normalizedX) {
            tmp.set(getWidth() * normalizedX, 0f);
            localToStageCoordinates(tmp);
            return tmp.x;
        }

        private float getStageY (float normalizedY) {
            tmp.set(0f, getHeight() * normalizedY);
            localToStageCoordinates(tmp);
            return tmp.y;
        }
    }

    private static class RollPointsProgressActor extends Actor {
        private static final float MIN_VISIBLE_PROGRESS_WIDTH = 45f;

        private final int value;
        private final int maxValue;
        private final Drawable fillDrawable = Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#7ed97b"));

        public RollPointsProgressActor (int value, int maxValue) {
            this.value = value;
            this.maxValue = maxValue;
            setTouchable(Touchable.disabled);
        }

        @Override
        public void draw (Batch batch, float parentAlpha) {
            if (value <= 0 || maxValue <= 0) return;

            final float progress = Math.min(1f, value / (float) maxValue);
            final float fillWidth = Math.min(getWidth(), Math.max(MIN_VISIBLE_PROGRESS_WIDTH, getWidth() * progress));

            fillDrawable.draw(batch, getX(), getY(), fillWidth, getHeight());
        }
    }
}
