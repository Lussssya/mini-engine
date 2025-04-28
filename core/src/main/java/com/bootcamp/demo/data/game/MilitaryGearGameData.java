package com.bootcamp.demo.data.game;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.XmlReader;
import com.bootcamp.demo.engine.Resources;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MilitaryGearGameData implements IGameData {
    public enum Slot {
        WEAPON,
        MELEE,
        HEAD,
        BODY,
        GLOVES,
        SHOES
    }

    @Getter
    public enum Rarity {
        RUSTED("#b19986"),
        SCRAP("#6495ca"),
        HARDENED("#b693c7"),
        ELITE("#e4ab56"),
        ASCENDANT("#d85959"),
        NUCLEAR("#aad448"),
        JUGGERNAUT("#7a70ec"),
        DOMINION("#f8667c"),
        OBLIVION("#59fcbe"),
        IMMORTAL("#6cfee3"),
        ETHEREAL("#fcfecd");

        private final String backgroundHex;

        Rarity(String backgroundHex) {
            this.backgroundHex = backgroundHex;
        }
    }

    private String name;
    private Drawable icon;

    @Override
    public void load(XmlReader.Element rootXml) {
        name = rootXml.getAttribute("name");
        icon = Resources.getDrawable(rootXml.getAttribute("icon"));
    }
}
