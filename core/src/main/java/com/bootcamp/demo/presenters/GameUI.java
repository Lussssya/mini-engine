package com.bootcamp.demo.presenters;

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
import com.bootcamp.demo.events.core.EventListener;
import com.bootcamp.demo.events.core.EventModule;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.core.APage;
import jdk.internal.loader.Resource;
import lombok.Getter;
import lombok.Setter;

@Getter
public class GameUI extends ScreenAdapter implements Disposable, EventListener {

    private final Stage stage;
    private final Table rootUI;
    private final Cell<APage> mainPageCell;

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
        mainPageCell = rootUI.add();

        rootUI.add(constructGrid(3, 4));
//        rootUI.debugAll();
    }

    private void playground() {
        final Table testTable = new Table();
        final Table testTable2 = new Table();
        final Table testTable3 = new Table();
        final Table testTable4 = new Table();
        final Table testTable5 = new Table();

        final Image gift = new Image(Resources.getDrawable("ui/ui-chat-gift-button-icon", Color.GREEN));
        gift.setScaling(Scaling.fit);

        final Table playground = new Table();
        playground.pad(30);
        playground.defaults().size(300);
        playground.add(testTable);
        playground.add(testTable2);
        playground.row();
        playground.add(testTable3);
        playground.add(gift).size(200, 600);

        final Table playground2 = new Table();
        playground2.setBackground(Resources.getDrawable("basics/white-squircle-35", Color.GREEN));
        final Image testImage = new Image();
        testImage.setDrawable(Resources.getDrawable("basics/white-squircle-35", Color.GREEN));
        playground2.add(testImage).size(300);

        rootUI.add(playground2);
        rootUI.debugAll();
    }

    public Table constructGrid(int rows, int cols) {
        final Table grid = new Table();
        grid.defaults().space(20);
        grid.defaults().size(300);
        grid.setBackground(Resources.getDrawable("basics/white-squircle-35", Color.WHITE));
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Table cell = Pools.get(Table.class).obtain();
                cell.setBackground(Resources.getDrawable("basics/white-squircle-35", Color.LIGHT_GRAY));
                Cell<Table> addedCell = grid.add(cell);

                if (col == 0) {
                    addedCell.padLeft(20);
                } else if (col == cols - 1) {
                    addedCell.padRight(20);
                }

                if (row == 0) {
                    addedCell.padTop(20);
                } else if (row == rows - 1) {
                    addedCell.padBottom(20);
                }

            }
            grid.row();
        }
        return grid;
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
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
