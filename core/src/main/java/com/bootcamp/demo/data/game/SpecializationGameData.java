package com.bootcamp.demo.data.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader.Element;
import lombok.Getter;

public class SpecializationGameData implements IGameData {
    @Getter
    public enum SpecializationType {
        GUNNER("#f7b634", "#cc8b29"),
        DENIER("#54e5ed", "#45b3cc"),
        SURVIVOR("#ff4a2e", "#da3e29");

        private final String backgroundColor;
        private final String borderColor;

        SpecializationType (String backgroundColor, String borderColor) {
            this.backgroundColor = backgroundColor;
            this.borderColor = borderColor;
        }
    }

    @Getter
    public enum Rarity {
        GOOD("#7cd934", 0.8),
        GREAT("#57f0de", 0.916),
        LEGENDARY("#f3bc6f", 1.2),
        ULTIMATE("#f15d34", 1.5),
        EXTRADIMENSIONAL("#d22147", 2.0);

        private final String labelColor;
        private final double multiplier;

        Rarity (String labelColor, double multiplier) {
            this.labelColor = labelColor;
            this.multiplier = multiplier;
        }
    }

    @Getter
    private int atkWeight;
    @Getter
    private int hpWeight;
    @Getter
    private int defWeight;
    @Getter
    private int maxRollPoints;

    private Array<PassiveEffectData> effects;

    public Array<PassiveEffectData> getEffects () {
        return new Array<>(effects);
    }

    @Override
    public void load (Element rootXml) {
        final Element weightsXml = rootXml.getChildByName("weights");

        atkWeight = weightsXml.getIntAttribute("atk");
        hpWeight = weightsXml.getIntAttribute("hp");
        defWeight = weightsXml.getIntAttribute("def");

        maxRollPoints = rootXml.getIntAttribute("maxRollPoints", 2500);

        final Array<Element> effectsXml = rootXml.getChildrenByName("effect");

        effects = new Array<>(effectsXml.size);

        for (int i = 0; i < effectsXml.size; i++) {
            final Element effectXml = effectsXml.get(i);
            final PassiveEffectData current = new PassiveEffectData(PLayerStat.valueOf(effectXml.getAttribute("label")), effectXml.getIntAttribute("value"));
            effects.add(current);
        }
    }

    @Getter
    public static class PassiveEffectData {
        private final PLayerStat stat;
        private final int value;

        public PassiveEffectData (PLayerStat stat, int value) {
            this.stat = stat;
            this.value = value;
        }
    }
}
