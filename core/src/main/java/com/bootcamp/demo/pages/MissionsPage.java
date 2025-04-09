package com.bootcamp.demo.pages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Pools;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.WidgetsList;
import com.bootcamp.demo.pages.core.APage;

public class MissionsPage extends APage {
    @Override
    protected void constructContent(Table content) {
        content.add(constructUpperSegment()).grow();
        content.row();
        content.add(constructLowerSegment()).growX();
//        content.debugAll();
    }

    private Table constructUpperSegment() {
        final Table upperSegment = new Table();
        upperSegment.setBackground(Resources.getDrawable("basics/white-pixel", Color.valueOf("#e09e6b")));
        upperSegment.add(constructBar()).expandY().bottom();
        return upperSegment;
    }

    private Table constructBar() {
        final Table layout = new Table();
        final Table bar = new Table();
        bar.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#f5eae3")));
        layout.add(bar).width(600).height(125);
        return layout;
    }

    private Table constructLowerSegment() {
        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#f5eae3")));

        segment.pad(30).defaults().space(30).growX();

        segment.add(constructInformationSegment());
        segment.row();
        segment.add(constructEquipmentSegment());
        segment.row();
        segment.add(constructButtonSegment());
        return segment;
    }

    private Table constructInformationSegment() {
        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#b6a89c")));
        segment.pad(30).defaults().space(30);

        segment.add(constructStatsSegment()).expand().growX();
        segment.add(constructStatsButtonSegment()).size(150);
        return segment;
    }

    private Table constructStatsSegment() {
        final StatsWidget segment = new StatsWidget();
        segment.setData();
        return segment;
    }

    private Table constructStatsButtonSegment() {
        final Table button = new Table();
        button.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#cfb6a3")));
        return button;
    }

    public static class StatsWidget extends WidgetsList<StatWidget> {
        public StatsWidget() {
            super(3);
            pad(15).defaults().space(30).height(60).growX();
        }

        public void setData() {
            freeChildren();

            for (int i = 0; i < 9; i++) {
                StatWidget widget = Pools.obtain(StatWidget.class);
                add(widget);
            }
        }
    }

    public static class StatWidget extends Table {
        public StatWidget() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#504845")));
        }

        public void setData() {
        }
    }

    private Table constructEquipmentSegment() {
        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#f5eae3")));
        segment.pad(30).defaults().space(30);

        segment.add(constructEquippedGearSegment()).grow();
        segment.add(constructSecondaryGearSegment()).grow();
        return segment;
    }

    private Table constructEquippedGearSegment() {
        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#d1cecc")));
        segment.pad(30).defaults().space(30);

        segment.add(constructIncompleteSet()).fillX().center().height(60);
        segment.row();
        segment.add(constructEquipment());
        return segment;
    }

    public static class EquippedGearContainer extends WidgetsList<EquippedGearWidget> {
        public EquippedGearContainer() {
            super(3);
            defaults().space(30).size(200);
        }

        public void setData() {
            freeChildren();

            for (int i = 0; i < 6; i++) {
                EquippedGearWidget widget = Pools.obtain(EquippedGearWidget.class);
                add(widget);
            }
        }
    }

    public static class EquippedGearWidget extends Table {
        public EquippedGearWidget() {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#b9a391")));
        }

        public void setData() {
        }
    }

    private Table constructIncompleteSet() {
        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#a9a29c")));

        segment.addActor(constructSetButtonActor());
        return segment;
    }

    private Table constructSetButtonActor() {
        final Table button = new Table();
        button.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#cfb6a3")));

        final Table layout = new Table();
        layout.setFillParent(true);
        layout.add(button).expand().right().size(80);
        return layout;
    }

    private Table constructEquipment() {
        final EquippedGearContainer container = new EquippedGearContainer();
        container.setData();
        return container;
    }

    private Table constructSecondaryGearSegment() {
        final Table firstColumn = new Table();
        firstColumn.defaults().space(30);
        firstColumn.add(constructRelicSegment()).size(200);
        firstColumn.row();
        firstColumn.add(constructFlagSegment()).size(200);

        final Table secondaryGearSegment = new Table();
        secondaryGearSegment.pad(30).defaults();

        final Table container = new Table();
        container.defaults().space(30);
        container.add(firstColumn);
        container.add(constructPetSegment()).growY().width(200);
        secondaryGearSegment.add(container);

        return secondaryGearSegment;
    }

    private Table constructRelicSegment() {
        final RelicContainer container = new RelicContainer();
        container.setData();
        return container;
    }

    public static class RelicContainer extends WidgetsList<Table> {
        public RelicContainer (){
            super(2);
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c8c0b9")));
            pad(20).defaults().space(20).grow();
        }

        public void setData() {
            for (int i = 0; i < 4; i++) {
                final Table relic = new Table();
                relic.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#a9a29c")));
                add(relic);
            }
        }
    }

    private Table constructFlagSegment() {
        final FlagWidget widget = new FlagWidget();
        widget.setData();
        return widget;
    }

    public static class FlagWidget extends Table {
        public FlagWidget () {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c8c0b9")));
        }

        public void setData() {}
    }

    private Table constructPetSegment() {
        final PetWidget widget = new PetWidget();
        widget.setData();
        return widget;
    }

    public static class PetWidget extends Table {
        public PetWidget () {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c8c0b9")));
        }

        public void setData() {
            final Table button = new Table();
            button.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#debc7c")));

            add(button).expand().bottom().growX().height(100);
        }
    }

    private Table constructButtonSegment() {
        final Table upgradeButton = new Table();
        upgradeButton.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#debc7c")));

        final Table lootButton = new Table();
        lootButton.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#a6d890")));

        final Table autoLootButton = new Table();
        autoLootButton.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c2c2c2")));

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#f5eae3")));

        segment.pad(30).defaults().space(30).growX().height(150);
        segment.add(upgradeButton);
        segment.add(lootButton);
        segment.add(autoLootButton);
        return segment;
    }
}
