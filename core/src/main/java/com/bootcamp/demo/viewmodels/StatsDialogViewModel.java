package com.bootcamp.demo.viewmodels;

import com.badlogic.gdx.utils.Array;
import lombok.Getter;

@Getter
public class StatsDialogViewModel {
    private final Array<StatRowViewModel> rows = new Array<>();
}
