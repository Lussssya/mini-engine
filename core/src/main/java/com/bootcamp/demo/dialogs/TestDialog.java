package com.bootcamp.demo.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.save.StatSaveData;
import com.bootcamp.demo.dialogs.core.ADialog;
import com.bootcamp.demo.dialogs.core.DialogManager;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.OffsetButton;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;

public class TestDialog extends ADialog {

/**    setOnClick(() -> {
        final DialogManager dialogManager = API.get(DialogManager.class);
        final TestDialog dialog = API.get(DialogManager.class).getDialog(TestDialog.class);

        dialogManager.show(TestDialog.class);

        final Table currentGear = this.cloneToTable();

        final Table statsTable = new Table();
        statsTable.padBottom(30).defaults().pad(5);
        for (ObjectMap.Entry<StatSaveData.StatType, StatSaveData> entry : militaryStats.entries()) {
            final StatSaveData.StatType type = entry.key;
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
*/
    @Override
    protected void constructContent (Table content) {

    }
}
