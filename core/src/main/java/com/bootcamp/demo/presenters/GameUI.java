package com.bootcamp.demo.presenters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.events.core.EventListener;
import com.bootcamp.demo.events.core.EventModule;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.TestPage;
import com.bootcamp.demo.pages.core.APage;
import com.bootcamp.demo.pages.core.PageManager;
import lombok.Getter;
import lombok.Setter;

@Getter
public class GameUI extends ScreenAdapter implements Disposable, EventListener {

    private final Stage stage;
    private final Table rootUI;
    @Getter
    private Cell<APage> mainPageCell;

    @Setter
    private boolean buttonPressed;

    public GameUI(Viewport viewport) {
        API.Instance().register(GameUI.class, this);
        API.get(EventModule.class).registerListener(this);

        rootUI = new Table();
        rootUI.setFillParent(true);

        // init stage
        stage = new Stage(viewport);
        stage.addActor(rootUI);

        // construct
        mainPageCell = rootUI.add().grow();
    }

    public Table constructGrid(int rows, int cols) {
        final Table grid = new Table();
        grid.pad(30).defaults().space(30);
        grid.defaults().size(300);
        grid.setBackground(Resources.getDrawable("basics/white-squircle-35", Color.WHITE));
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Table cell = Pools.get(Table.class).obtain();
                cell.setBackground(Resources.getDrawable("basics/white-squircle-35", Color.LIGHT_GRAY));
                grid.add(cell);
            }
            grid.row();
        }
        return grid;
    }

    @Override
    public void render(float delta) {
        if(Gdx.app.getInput().isKeyJustPressed(Input.Keys.M)) {
            API.get(PageManager.class).show(MissionsPage.class);
        }
        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            API.get(PageManager.class).show(TestPage.class);
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
