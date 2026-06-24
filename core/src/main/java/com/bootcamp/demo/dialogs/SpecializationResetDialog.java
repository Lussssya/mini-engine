package com.bootcamp.demo.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bootcamp.demo.data.save.SaveData;
import com.bootcamp.demo.data.save.SpecializationSaveData;
import com.bootcamp.demo.dialogs.core.ADialog;
import com.bootcamp.demo.dialogs.core.DialogManager;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.IconTextWrapper;
import com.bootcamp.demo.engine.widgets.JuicyButton;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.managers.SaveManager;

public class SpecializationResetDialog extends ADialog {
    private static final String RESET_TEXT = "Resetting the build will reset all roll points to 0, and stat bonuses to 0, you will be able to choose a new class, and 100% (0) of all your dice spent will be refunded.";

    @Override
    protected void constructContent (Table content) {
        final Label text = Labels.make(GameFont.BOLD_22, Color.valueOf("#42413d"), RESET_TEXT);
        final Table buttonsSegment = constructButtonsSegment();
        text.setWrap(true);

        content.setBackground(Squircle.SQUIRCLE_35_BTM.getDrawable(Color.valueOf("#f4e7dd")));

        content.pad(40).defaults().width(1000);

        content.add(text);
        content.row();
        content.add(buttonsSegment);
        setCloseButtonVisible(true);
    }

    @Override
    protected void constructTitleSegment (Table titleSegment) {
        final Label title = Labels.make(GameFont.STROKE_26, "Reset this Build?");

        titleSegment.setBackground(Squircle.SQUIRCLE_35_TOP.getDrawable(Color.valueOf("#beaea6")));
        titleSegment.add(title).expandX().pad(20);
    }

    private Table constructButtonsSegment () {
        final Label warning = Labels.make(GameFont.BOLD_24, Color.valueOf("#42413d"), "With every next reset, price in gem's will double");
        warning.setWrap(true);
        final JuicyButton noButton = constructNoButton();
        final JuicyButton resetButton = constructResetButton();

        final Table buttonsWrapper = new Table();
        buttonsWrapper.defaults().size(300, 170).expandX().uniformX();
        buttonsWrapper.add(noButton).left().padLeft(80);
        buttonsWrapper.add(resetButton).right().padRight(80);

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#beaea6")));

        segment.pad(20);
        segment.add(warning).width(700).padBottom(20);
        segment.row();
        segment.add(buttonsWrapper).growX();
        return segment;
    }

    private JuicyButton constructNoButton () {
        final Label noButtonText = Labels.make(GameFont.STROKE_26, Color.valueOf("#f5eae3"), "No, Thanks");

        final JuicyButton noButton = new JuicyButton(JuicyButton.Style.ORANGE_35) {
            protected void buildInner (Table container) {
                super.buildInner(container);
                container.add(noButtonText).expand();
            }
        };

        noButton.setOnClick(() -> {
            API.get(DialogManager.class).hide(SpecializationResetDialog.class);
        });

        return noButton;
    }

    private JuicyButton constructResetButton () {
        final Label resetButtonText = Labels.make(GameFont.STROKE_26, Color.valueOf("#f5eae3"), "Reset");
        final SpecializationSaveData saveData = API.get(SaveData.class).getSpecializationSaveData();

        final Table iconTextWrapper = new IconTextWrapper("lootPage/die-icon", String.valueOf(saveData.getResetPrice()));

        final JuicyButton resetButton = new JuicyButton(JuicyButton.Style.RED_35) {
            protected void buildInner (Table container) {
                super.buildInner(container);
                container.add(resetButtonText).expand();
                container.row();
                container.add(iconTextWrapper).size(150, 45).padBottom(10);
            }
        };
        resetButton.setOnClick(this::resetSpecialization);

        return resetButton;
    }

    private void resetSpecialization () {
        final SpecializationSaveData saveData = API.get(SaveData.class).getSpecializationSaveData();
        saveData.reset();
        saveData.incrementResetCount();
        API.get(SaveManager.class).savePlayerData();

        final DialogManager dialogManager = API.get(DialogManager.class);
        dialogManager.hide(SpecializationResetDialog.class);
        dialogManager.hide(SpecializationDialog.class);
        dialogManager.removeDialog(SpecializationResetDialog.class);
        dialogManager.removeDialog(SpecializationDialog.class);

        dialogManager.show(ChooseSpecializationDialog.class);
    }
}
