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

public class DemoGame extends Game {

    @Override
    public void create() {
        Gdx.input.setInputProcessor(new InputMultiplexer());

        final GameData gameData = new GameData();
        API.Instance().register(GameData.class, gameData);
        gameData.load();

        loadSaveData();

        final TacticalSaveData tacticalSaveData = new TacticalSaveData();
        tacticalSaveData.setName("present");
        tacticalSaveData.setLevel(3);
        API.get(SaveData.class).getTacticalsSaveData().getTacticals().put(0, tacticalSaveData);

        setupStats();

        setupMilitaryGears();

        savePlayerData();

        setScreen(new GameScreen());
        API.get(EventModule.class).fireEvent(GameStartedEvent.class);
    }

    private void setupStats() {
        final StatsSaveData statsSaveData = new StatsSaveData();
        statsSaveData.getStats().put(0, createStat("hp", "33k"));
        statsSaveData.getStats().put(1, createStat("atk", "12.3k"));
        statsSaveData.getStats().put(2, createStat("dodge", "34.49%"));
        statsSaveData.getStats().put(3, createStat("combo", "17.3%"));
        statsSaveData.getStats().put(4, createStat("crit", "6.06%"));
        statsSaveData.getStats().put(5, createStat("stun", "19.56%"));
        statsSaveData.getStats().put(6, createStat("regen", "12.29%"));
        statsSaveData.getStats().put(7, createStat("steal", "10.53%"));
        statsSaveData.getStats().put(8, createStat("poison", "9.92%"));

        API.get(SaveData.class).setStatsSaveData(statsSaveData);
    }

    private StatSaveData createStat(String name, String value) {
        StatSaveData stat = new StatSaveData();
        stat.setName(name);
        stat.setValue(value);
        return stat;
    }


    private void setupMilitaryGears() {
        final MilitariesSaveData militariesSaveData = new MilitariesSaveData();

        militariesSaveData.getMilitaries().put(0, createMilitary("weapon", 1, 28, 'D'));
        militariesSaveData.getMilitaries().put(1, createMilitary("melee", 2, 28, 'D'));
        militariesSaveData.getMilitaries().put(2, createMilitary("head", 1, 25, 'A'));
        militariesSaveData.getMilitaries().put(3, createMilitary("body", 1, 29, 'C'));
        militariesSaveData.getMilitaries().put(4, createMilitary("gloves", 1, 23, 'C'));
        militariesSaveData.getMilitaries().put(5, createMilitary("shoes", 2, 24, 'B'));

        API.get(SaveData.class).setMilitariesSaveData(militariesSaveData);
    }

    private MilitarySaveData createMilitary(String type, int starCount, int level, char tier) {
        MilitarySaveData military = new MilitarySaveData();
        military.setType(type);
        military.setStarCount(starCount);
        military.setLevel(level);
        military.setTier(tier);
        return military;
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
