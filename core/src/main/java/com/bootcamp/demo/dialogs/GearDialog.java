package com.bootcamp.demo.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bootcamp.demo.dialogs.core.ADialog;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.viewmodels.GearViewModel;

public class GearDialog extends ADialog {
    private Label name;
    private Label rarityType;
    private Label typeValue;
    private Label powerValueLabel;

    public void setData (GearViewModel viewModel) {
        name.setText(viewModel.getName());
        rarityType.setText(viewModel.getRarity());
        typeValue.setText(viewModel.getType());
        powerValueLabel.setText(viewModel.getPower());
    }

    @Override
    protected void constructContent (Table content) {
        content.setBackground(Squircle.SQUIRCLE_35_BTM.getDrawable(Color.valueOf("#cfb6a3")));

        final Table detailsSegment = constructDetailsSegment();

        content.pad(30);
        content.add(detailsSegment).grow();
    }

    @Override
    protected void constructTitleSegment (Table titleSegment) {
        name = Labels.make(GameFont.BOLD_24, Color.BLACK);

        titleSegment.setBackground(Squircle.SQUIRCLE_35_TOP.getDrawable(Color.valueOf("#cfb6a3")));
        titleSegment.add(name).expandX();
    }

    private Table constructDetailsSegment () {
        final Label powerText = Labels.make(GameFont.BOLD_24, Color.BLACK, "Power:");
        powerValueLabel = Labels.make(GameFont.BOLD_24, Color.BLACK, "0");
        final Table powerLayout = new Table();
        powerLayout.add(powerText).expandX().left();
        powerLayout.add(powerValueLabel).padLeft(40);

        final Label rarityText = Labels.make(GameFont.BOLD_24, Color.BLACK, "Rarity:");
        rarityType = Labels.make(GameFont.BOLD_24, Color.BLACK);
        final Table rarityLayout = new Table();
        rarityLayout.add(rarityText).expandX().left();
        rarityLayout.add(rarityType).padLeft(40);

        final Label typeText = Labels.make(GameFont.BOLD_24, Color.BLACK, "Type:");
        typeValue = Labels.make(GameFont.BOLD_24, Color.BLACK);
        final Table typeLayout = new Table();
        typeLayout.add(typeText).expandX().left();
        typeLayout.add(typeValue).padLeft(40);

        final WidgetsContainer<Table> segment = new WidgetsContainer<>(1);
        segment.pad(30).defaults().space(30).growX();
        segment.add(powerLayout);
        segment.add(rarityLayout);
        segment.add(typeLayout);

        return segment;
    }
}
