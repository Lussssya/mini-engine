package com.bootcamp.demo.viewmodels;

import com.badlogic.gdx.utils.Pool;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StatRowViewModel implements Pool.Poolable {
    private String title;
    private String value;

    @Override
    public void reset () {
        title = null;
        value = null;
    }
}
