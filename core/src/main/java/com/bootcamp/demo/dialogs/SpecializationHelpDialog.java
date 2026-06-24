package com.bootcamp.demo.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bootcamp.demo.dialogs.core.ADialog;
import com.bootcamp.demo.dialogs.core.DialogManager;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.JuicyButton;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;

public class SpecializationHelpDialog extends ADialog {
    private static final String HELP_TEXT =
        "Rolls spend Class Dice to give a bonus to HP, ATK, or DEF.\n\n" +
            "Your class bias makes some stats more likely than others. " +
            "Gunner favors ATK, Survivor favors HP, and Denier is more balanced.\n\n" +
            "Each full Roll also adds Roll Points, which make future rolls stronger. " +
            "Roll quality can be Good, Great, Legendary, Ultimate, or Extradimensional - " +
            "higher tiers give bigger bonuses.\n\n" +
            "If you dislike the result, Re-roll Stat changes the stat, while Re-roll " +
            "replaces the whole roll with a new one. Both cost extra dice, but do not " +
            "add Roll Points.";

    @Override
    protected void constructContent (Table content) {
        final Label text = Labels.make(GameFont.BOLD_28, Color.valueOf("#42413d"), HELP_TEXT);
        final JuicyButton okayButton = constructOkayButton();
        text.setWrap(true);

        content.setBackground(Squircle.SQUIRCLE_35_BTM.getDrawable(Color.valueOf("#f4e7dd")));

        content.pad(20);

        content.add(text).grow().width(1000);
        content.row();
        content.add(okayButton).size(300, 150).expandX();
    }

    @Override
    protected void constructTitleSegment (Table titleSegment) {
        final Label title = Labels.make(GameFont.BOLD_26, Color.valueOf("#42413d"), "Help");

        titleSegment.setBackground(Squircle.SQUIRCLE_35_TOP.getDrawable(Color.valueOf("#beaea6")));
        titleSegment.add(title).expandX().pad(20);
    }

    private JuicyButton constructOkayButton () {
        final Label okayButtonText = Labels.make(GameFont.STROKE_26, Color.valueOf("#f5eae3"), "Okay");

        final JuicyButton okayButton = new JuicyButton(JuicyButton.Style.GREEN_35) {
            protected void buildInner (Table container) {
                super.buildInner(container);
                container.add(okayButtonText).expand();
            }
        };

        okayButton.setOnClick(() -> {
            API.get(DialogManager.class).hide(SpecializationHelpDialog.class);
        });

        return okayButton;
    }
}
