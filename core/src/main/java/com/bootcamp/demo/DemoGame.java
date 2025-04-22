package com.bootcamp.demo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.*;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.save.*;
import com.bootcamp.demo.events.GameStartedEvent;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.events.core.EventModule;
import com.bootcamp.demo.data.save.StatSaveData.StatType;
import com.bootcamp.demo.data.save.MilitaryGearSaveData.MilitarySlot;
import com.bootcamp.demo.data.save.MilitaryGearSaveData.MilitaryRarity;
import com.bootcamp.demo.data.save.TacticalSaveData.TacticalSlot;
import com.bootcamp.demo.data.save.TacticalSaveData.TacticalRarity;

public class DemoGame extends Game {

    @Override
    public void create() {
        Gdx.input.setInputProcessor(new InputMultiplexer());

        final GameData gameData = new GameData();
        API.Instance().register(GameData.class, gameData);
        gameData.load();

        loadSaveData();

        setupStats();
        setupMilitaryGears();
        setupTacticals();

        savePlayerData();

        setScreen(new GameScreen());
        API.get(EventModule.class).fireEvent(GameStartedEvent.class);
    }

    private void setupStats() {
        StatsSaveData statsSaveData = new StatsSaveData();

        statsSaveData.getStats().put(StatType.HP, createStat(StatType.HP, 33f));
        statsSaveData.getStats().put(StatType.ATK, createStat(StatType.ATK, 12.3f));
        statsSaveData.getStats().put(StatType.DODGE, createStat(StatType.DODGE, 34.49f));
        statsSaveData.getStats().put(StatType.COMBO, createStat(StatType.COMBO, 17.3f));
        statsSaveData.getStats().put(StatType.CRIT, createStat(StatType.CRIT, 6.06f));
        statsSaveData.getStats().put(StatType.STUN, createStat(StatType.STUN, 19.56f));
        statsSaveData.getStats().put(StatType.REGEN, createStat(StatType.REGEN, 12.29f));
        statsSaveData.getStats().put(StatType.STEAL, createStat(StatType.STEAL, 10.53f));
        statsSaveData.getStats().put(StatType.POISON, createStat(StatType.POISON, 9.92f));

        API.get(SaveData.class).setStatsSaveData(statsSaveData);
    }

    private StatSaveData createStat(StatType type, float value) {
        StatSaveData stat = new StatSaveData();
        stat.setName(type);
        stat.setValue(value);
        return stat;
    }

    private void setupMilitaryGears() {
        final MilitaryGearsSaveData militariesSaveData = new MilitaryGearsSaveData();

        MilitaryGearSaveData gear1 = createMilitary("angel-bow", MilitarySlot.WEAPON, 1, 28, 'D', MilitaryRarity.ELITE);
        setStats(gear1, new Object[][]{
            {"hp", 2.5f},
            {"atk", 918f},
            {"dodge", 5.67f},
            {"combo", 5.67f},
            {"poison", 5.67f}
        });

        MilitaryGearSaveData gear2 = createMilitary("hard-armor", MilitarySlot.MELEE, 2, 28, 'D', MilitaryRarity.HARDENED);
        setStats(gear2, new Object[][]{
            {"regen", 1.5f}
        });

        MilitaryGearSaveData gear3 = createMilitary("magic-ring", MilitarySlot.HEAD, 1, 25, 'A', MilitaryRarity.OBLIVION);
        setStats(gear3, new Object[][]{
            {"combo", 2f}
        });

        MilitaryGearSaveData gear4 = createMilitary("star-staff", MilitarySlot.BODY, 1, 29, 'C', MilitaryRarity.ETHEREAL);
        setStats(gear4, new Object[][]{
            {"stun", 2f}
        });

        MilitaryGearSaveData gear5 = createMilitary("bloody-grail", MilitarySlot.GLOVES, 1, 23, 'C', MilitaryRarity.NUCLEAR);
        setStats(gear5, new Object[][]{
            {"dodge", 2.5f}
        });

        MilitaryGearSaveData gear6 = createMilitary("mystic-staff", MilitarySlot.SHOES, 2, 24, 'B', MilitaryRarity.JUGGERNAUT);

        militariesSaveData.getMilitaries().put(MilitarySlot.WEAPON, gear1);
        militariesSaveData.getMilitaries().put(MilitarySlot.MELEE, gear2);
        militariesSaveData.getMilitaries().put(MilitarySlot.HEAD, gear3);
        militariesSaveData.getMilitaries().put(MilitarySlot.BODY, gear4);
        militariesSaveData.getMilitaries().put(MilitarySlot.GLOVES, gear5);
        militariesSaveData.getMilitaries().put(MilitarySlot.SHOES, gear6);

        API.get(SaveData.class).setMilitariesSaveData(militariesSaveData);
    }

    private MilitaryGearSaveData createMilitary(String name, MilitarySlot slot, int starCount, int level, char tier, MilitaryRarity rarity) {
        MilitaryGearSaveData military = new MilitaryGearSaveData();
        military.setName(name);
        military.setSlot(slot);
        military.setStarCount(starCount);
        military.setLevel(level);
        military.setTier(tier);
        military.setRarity(rarity);
        return military;
    }

    private void setStats(MilitaryGearSaveData gear, Object[][] values) {
        for (Object[] pair : values) {
            StatSaveData.StatType statType = StatSaveData.StatType.valueOf(((String) pair[0]).toUpperCase());
            float value = (float) pair[1];

            StatSaveData statSaveData = new StatSaveData();
            statSaveData.setName(statType);
            statSaveData.setValue(value);

            gear.getStats().getStats().put(statType, statSaveData);
        }
    }

    private void setupTacticals() {
        final TacticalsSaveData tacticalsSaveData = new TacticalsSaveData();

        TacticalSaveData tactical1 = createTactical("chidori", TacticalSlot.SLOT_1, 4, 1, TacticalRarity.EPIC);
        setStats(tactical1, new Object[][]{
            {"hp", 648f},
            {"atk", 226f}
        });

        TacticalSaveData tactical2 = createTactical("rassengan", TacticalSlot.SLOT_2, 3, 1, TacticalRarity.COMMON);
        setStats(tactical2, new Object[][]{
            {"hp", 586f},
            {"atk", 202f}
        });

        TacticalSaveData tactical3 = createTactical("kunai", TacticalSlot.SLOT_3, 6, 2, TacticalRarity.EXOTIC);
        setStats(tactical3, new Object[][]{
            {"hp", 713f},
            {"atk", 261f}
        });

        TacticalSaveData tactical4 = createTactical("shuriken", TacticalSlot.SLOT_4, 3, 1, TacticalRarity.LEGENDARY);
        setStats(tactical4, new Object[][]{
            {"hp", 586f},
            {"atk", 202f}
        });

        tacticalsSaveData.getTacticals().put(TacticalSlot.SLOT_1, tactical1);
        tacticalsSaveData.getTacticals().put(TacticalSlot.SLOT_2, tactical2);
        tacticalsSaveData.getTacticals().put(TacticalSlot.SLOT_3, tactical3);
        tacticalsSaveData.getTacticals().put(TacticalSlot.SLOT_4, tactical4);

        API.get(SaveData.class).setTacticalsSaveData(tacticalsSaveData);
    }

    private TacticalSaveData createTactical(String name, TacticalSlot slot, int level, int starCount, TacticalRarity rarity) {
        TacticalSaveData tactical = new TacticalSaveData();
        tactical.setName(name);
        tactical.setSlot(slot);
        tactical.setLevel(level);
        tactical.setStarCount(starCount);
        tactical.setRarity(rarity);
        return tactical;
    }

    private void setStats(TacticalSaveData tactical, Object[][] values) {
        for (Object[] pair : values) {
            StatSaveData.StatType statType = StatSaveData.StatType.valueOf(((String) pair[0]).toUpperCase());
            float value = (float) pair[1];

            StatSaveData statSaveData = new StatSaveData();
            statSaveData.setName(statType);
            statSaveData.setValue(value);

            tactical.getStats().getStats().put(statType, statSaveData);
        }
    }

    private void loadSaveData() {
        final FileHandle file = getPlayerDataFileHandler();
        if (file.exists()) {
            createNewSaveData();
            return;
        }

        final JsonReader jsonReader = new JsonReader();
        final Json json = new Json();
        json.setIgnoreUnknownFields(true);

        final String dataString = file.readString();
        final JsonValue jsonValue = jsonReader.parse(dataString);
        final SaveData saveData = json.readValue(SaveData.class, jsonValue);
        API.Instance().register(SaveData.class, saveData);
    }

    private void createNewSaveData() {
        final SaveData saveData = new SaveData();
        API.Instance().register(SaveData.class, saveData);
        savePlayerData();
    }

    public void savePlayerData() {
        final SaveData saveData = API.get(SaveData.class);

        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        json.setIgnoreUnknownFields(true);

        final String dataToPersist = json.toJson(saveData);
        getPlayerDataFileHandler().writeString(dataToPersist, false);
    }

    private FileHandle getPlayerDataFileHandler() {
        final FileHandle playerDataFile = Gdx.files.local("usercache").child("player-data");
        // check if file exists; if not, create an empty file
        if (!playerDataFile.exists()) {
            playerDataFile.writeString("", false);
        }
        return playerDataFile;
    }

    @Override
    public void dispose() {
        super.dispose();
        API.Instance().dispose();
        Gdx.app.exit();
    }
}
