package com.bootcamp.demo.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bootcamp.demo.dialogs.core.ADialog;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.pages.MissionsPage.StatsContainer;
import com.bootcamp.demo.viewmodels.StatsDialogViewModel;

public class StatsDialog extends ADialog {

    private StatsContainer statsContainer;

    @Override
    protected void constructContent (Table content) {
        statsContainer = new StatsContainer(1);

        content.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#f5e6de")));
        content.add(statsContainer).width(1000);
    }

    @Override
    protected void constructTitleSegment (Table titleSegment) {
        final Label title = Labels.make(GameFont.BOLD_28, Color.valueOf("#52483f"), "Stats");

        titleSegment.setBackground(Squircle.SQUIRCLE_35_TOP.getDrawable(Color.valueOf("#f5e6de")));
        titleSegment.add(title).expandX();
    }

    public void setData (StatsDialogViewModel viewModel) {
        statsContainer.setData(viewModel);
    }
}
