package com.bootcamp.demo.viewmodels;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import lombok.Getter;

@Getter
public class StatsDialogViewModel {
    private final Array<StatRowViewModel> rows = new Array<>();

    public void freeRows () {
        Pools.freeAll(rows);
        rows.clear();
    }
}
