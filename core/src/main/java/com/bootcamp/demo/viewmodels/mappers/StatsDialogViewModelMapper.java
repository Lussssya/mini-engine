package com.bootcamp.demo.viewmodels.mappers;

import com.bootcamp.demo.data.game.PLayerStat;
import com.bootcamp.demo.data.game.PLayerStat.StatType;
import com.bootcamp.demo.data.save.StatSaveData;
import com.bootcamp.demo.data.save.StatsSaveData;
import com.bootcamp.demo.viewmodels.StatRowViewModel;
import com.bootcamp.demo.viewmodels.StatsDialogViewModel;

public class StatsDialogViewModelMapper {

    public static StatsDialogViewModel map (StatsSaveData data) {
        final StatsDialogViewModel viewModel = new StatsDialogViewModel();

        for (PLayerStat PLayerStat : PLayerStat.values()) {
            final StatSaveData statData = data.getStats().get(PLayerStat);

            if (statData == null) {
                continue;
            }

            viewModel.getRows().add(mapRow(PLayerStat, statData));
        }

        return viewModel;
    }

    private static StatRowViewModel mapRow (PLayerStat PLayerStat, StatSaveData statData) {
        final StatRowViewModel row = new StatRowViewModel();
        row.setTitle(PLayerStat.getDisplayName());

        if (PLayerStat.getType() == StatType.ADDITIVE) {
            row.setValue(formatAdditive(statData.getValue()));
        } else {
            row.setValue(String.format("%.2f%%", statData.getValue()));
        }

        return row;
    }

    private static String formatAdditive (float value) {
        if (value >= 1_000_000_000) return String.format("%.1fb", value / 1_000_000_000f);

        if (value >= 1_000_000) return String.format("%.1fm", value / 1_000_000f);

        if (value >= 1_000) return String.format("%.1fk", value / 1_000f);

        return String.format("%.0f", value);
    }
}
