package com.bootcamp.demo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.*;
import com.bootcamp.demo.data.game.*;
import com.bootcamp.demo.data.save.*;
import com.bootcamp.demo.events.GameStartedEvent;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.events.core.EventModule;
import com.bootcamp.demo.data.game.MilitaryGearGameData.Slot;

import java.util.Locale;

public class DemoGame extends Game {

    @Override
    public void create() {
        Gdx.input.setInputProcessor(new InputMultiplexer());

        final GameData gameData = new GameData();
        API.Instance().register(GameData.class, gameData);
        gameData.load();

        loadSaveData();

        setupMilitaryGears();
        setupTacticals();
        setupPets();
        setupFlags();

        savePlayerData();

        setScreen(new GameScreen());
        API.get(EventModule.class).fireEvent(GameStartedEvent.class);
    }

    private void setupStats() {
        StatsSaveData statsSaveData = new StatsSaveData();

        statsSaveData.getStats().put(Stat.HP, createStat(Stat.HP, 33f));
        statsSaveData.getStats().put(Stat.ATK, createStat(Stat.ATK, 12.3f));
        statsSaveData.getStats().put(Stat.DODGE, createStat(Stat.DODGE, 34.49f));
        statsSaveData.getStats().put(Stat.COMBO, createStat(Stat.COMBO, 17.3f));
        statsSaveData.getStats().put(Stat.CRIT, createStat(Stat.CRIT, 6.06f));
        statsSaveData.getStats().put(Stat.STUN, createStat(Stat.STUN, 19.56f));
        statsSaveData.getStats().put(Stat.REGEN, createStat(Stat.REGEN, 12.29f));
        statsSaveData.getStats().put(Stat.STEAL, createStat(Stat.STEAL, 10.53f));
        statsSaveData.getStats().put(Stat.POISON, createStat(Stat.POISON, 9.92f));

        API.get(SaveData.class).setStatsSaveData(statsSaveData);
    }

    private StatSaveData createStat(Stat type, float value) {
        StatSaveData stat = new StatSaveData();
        stat.setName(type);
        stat.setValue(value);
        return stat;
    }

    private void setupMilitaryGears() {
        final MilitaryGearsSaveData militariesSaveData = new MilitaryGearsSaveData();

        MilitaryGearSaveData gear1 = createMilitary("angel-bow", Slot.WEAPON, 1, 28, 'D', MilitaryGearGameData.Rarity.ELITE);
        setMilitaryStats(gear1, new Object[][]{
            {"hp", 2.5f},
            {"atk", 918f},
            {"dodge", 5.67f},
            {"combo", 5.67f},
            {"poison", 5.67f}
        });

        MilitaryGearSaveData gear2 = createMilitary("hard-armor", Slot.MELEE, 2, 28, 'D', MilitaryGearGameData.Rarity.HARDENED);
        setMilitaryStats(gear2, new Object[][]{
            {"regen", 1.5f}
        });

        MilitaryGearSaveData gear3 = createMilitary("magic-ring", Slot.HEAD, 1, 25, 'A', MilitaryGearGameData.Rarity.OBLIVION);
        setMilitaryStats(gear3, new Object[][]{
            {"combo", 2f}
        });

        MilitaryGearSaveData gear4 = createMilitary("star-staff", Slot.BODY, 1, 29, 'C', MilitaryGearGameData.Rarity.ETHEREAL);
        setMilitaryStats(gear4, new Object[][]{
            {"stun", 2f}
        });

        MilitaryGearSaveData gear5 = createMilitary("bloody-grail", Slot.GLOVES, 1, 23, 'C', MilitaryGearGameData.Rarity.NUCLEAR);
        setMilitaryStats(gear5, new Object[][]{
            {"dodge", 2.5f}
        });

        MilitaryGearSaveData gear6 = createMilitary("mystic-staff", Slot.SHOES, 2, 24, 'B', MilitaryGearGameData.Rarity.JUGGERNAUT);

        militariesSaveData.getMilitaries().put(Slot.WEAPON, gear1);
        militariesSaveData.getMilitaries().put(Slot.MELEE, gear2);
        militariesSaveData.getMilitaries().put(Slot.HEAD, gear3);
        militariesSaveData.getMilitaries().put(Slot.BODY, gear4);
        militariesSaveData.getMilitaries().put(Slot.GLOVES, gear5);
        militariesSaveData.getMilitaries().put(Slot.SHOES, gear6);

        API.get(SaveData.class).setMilitariesSaveData(militariesSaveData);
    }

    private MilitaryGearSaveData createMilitary(String name, Slot slot, int starCount, int level, char tier, MilitaryGearGameData.Rarity rarity) {
        MilitaryGearSaveData military = new MilitaryGearSaveData();
        military.setName(name);
        military.setSlot(slot);
        military.setStarCount(starCount);
        military.setLevel(level);
        military.setTier(tier);
        military.setRarity(rarity);
        return military;
    }

    private void setMilitaryStats(MilitaryGearSaveData gear, Object[][] values) {
        for (Object[] pair : values) {
            Stat statType = Stat.valueOf(((String) pair[0]).toUpperCase(Locale.ENGLISH));
            float value = (float) pair[1];

            StatSaveData statSaveData = new StatSaveData();
            statSaveData.setName(statType);
            statSaveData.setValue(value);

            gear.getGearStats().getStats().put(statType, statSaveData);
        }
    }

    private void setupTacticals() {
        final TacticalsSaveData tacticalsSaveData = new TacticalsSaveData();

        TacticalSaveData tactical1 = createTactical("chidori", 4, 1, Rarity.EPIC);
        setTacticalStats(tactical1, new Object[][]{
            {"hp", 648f},
            {"atk", 226f}
        });

        TacticalSaveData tactical2 = createTactical("rassengan", 3, 1, Rarity.COMMON);
        setTacticalStats(tactical2, new Object[][]{
            {"hp", 586f},
            {"atk", 202f}
        });

        TacticalSaveData tactical3 = createTactical("kunai", 6, 2, Rarity.EXOTIC);
        setTacticalStats(tactical3, new Object[][]{
            {"hp", 713f},
            {"atk", 261f}
        });

        TacticalSaveData tactical4 = createTactical("shuriken", 3, 1, Rarity.LEGENDARY);
        setTacticalStats(tactical4, new Object[][]{
            {"hp", 586f},
            {"atk", 202f}
        });

        tacticalsSaveData.getInventory().put(tactical1.getName(), tactical1);
        tacticalsSaveData.getInventory().put(tactical2.getName(), tactical2);
        tacticalsSaveData.getInventory().put(tactical3.getName(), tactical3);
        tacticalsSaveData.getInventory().put(tactical4.getName(), tactical4);

        tacticalsSaveData.getEquipped().put(0, tactical1.getName());
        tacticalsSaveData.getEquipped().put(1, tactical2.getName());
        tacticalsSaveData.getEquipped().put(2, tactical3.getName());
        tacticalsSaveData.getEquipped().put(3, tactical4.getName());

        API.get(SaveData.class).setTacticalsSaveData(tacticalsSaveData);
    }

    private TacticalSaveData createTactical(String name, int level, int starCount, Rarity rarity) {
        TacticalSaveData tactical = new TacticalSaveData();
        tactical.setName(name);
        tactical.setLevel(level);
        tactical.setStarCount(starCount);
        tactical.setRarity(rarity);
        return tactical;
    }

    private void setTacticalStats(TacticalSaveData tactical, Object[][] values) {
        for (Object[] pair : values) {
            Stat statType = Stat.valueOf(((String) pair[0]).toUpperCase(Locale.ENGLISH));
            float value = (float) pair[1];

            StatSaveData statSaveData = new StatSaveData();
            statSaveData.setName(statType);
            statSaveData.setValue(value);

            tactical.getTacticalStats().getStats().put(statType, statSaveData);
        }
    }

    private void setupPets() {
        final PetsSaveData petsSaveData = new PetsSaveData();

        PetSaveData pet1 = createPet("cat", 24, 1, Rarity.EPIC);
        petsSaveData.getInventory().put("cat", pet1);
        setPetStats(pet1, new Object[][]{
            {"hp", 5.79f},
            {"atk", 2.39f},
            {"dodge", 16.5f}
        });

        PetSaveData pet2 = createPet("beetle", 36, 2, Rarity.EXOTIC);
        petsSaveData.getInventory().put("beetle", pet2);
        setPetStats(pet2, new Object[][] {
            {"hp", 600f},
            {"atk", 875f},
            {"combo", 10.3f}
        });

        final PetSaveData pet3 = createPet("dragon", 19, 0, Rarity.LEGENDARY);
        petsSaveData.getInventory().put("dragon", pet3);
        setPetStats(pet3, new Object[][]{
            {"hp", 300f},
            {"atk", 700f},
            {"crit", 8f}
        });

        petsSaveData.setEquipped("dragon");

        API.get(SaveData.class).setPetsSaveData(petsSaveData);
    }

    private PetSaveData createPet(String name, int level, int starCount, Rarity rarity) {
        PetSaveData pet = new PetSaveData();
        pet.setName(name);
        pet.setLevel(level);
        pet.setStarCount(starCount);
        pet.setRarity(rarity);
        return pet;
    }

    private void setPetStats(PetSaveData pet, Object[][] values) {
        for (Object[] pair : values) {
            Stat statType = Stat.valueOf(((String) pair[0]).toUpperCase(Locale.ENGLISH));
            float value = (float) pair[1];

            StatSaveData statSaveData = new StatSaveData();
            statSaveData.setName(statType);
            statSaveData.setValue(value);

            pet.getPetStats().getStats().put(statType, statSaveData);
        }
    }

    private void setupFlags() {
        final FlagsSaveData flagsSaveData = new FlagsSaveData();

        final FlagSaveData common = createFlag("common");
        flagsSaveData.getInventory().put("common", common);
        setFlagStats(common, new Object[][]{
            {"hp", 2.14f},
            {"atk", 345f}
        });

        final FlagSaveData fight = createFlag("fight");
        flagsSaveData.getInventory().put("fight", fight);
        setFlagStats(fight, new Object[][]{
            {"hp", 2.14f},
            {"atk", 345f}
        });

        final FlagSaveData ghost = createFlag("ghost");
        flagsSaveData.getInventory().put("ghost", ghost);
        setFlagStats(common, new Object[][]{
            {"hp", 2.14f},
            {"atk", 345f}
        });

        final FlagSaveData star = createFlag("star");
        flagsSaveData.getInventory().put("star", star);
        setFlagStats(common, new Object[][]{
            {"hp", 2.14f},
            {"atk", 345f}
        });

        flagsSaveData.setEquipped("star");

        API.get(SaveData.class).setFlagsSaveData(flagsSaveData);
    }

    private FlagSaveData createFlag(String name) {
        FlagSaveData flag = new FlagSaveData();
        flag.setName(name);
        return flag;
    }

    private void setFlagStats(FlagSaveData flag, Object[][] values) {
        for (Object[] pair : values) {
            Stat statType = Stat.valueOf(((String) pair[0]).toUpperCase(Locale.ENGLISH));
            float value = (float) pair[1];

            StatSaveData statSaveData = new StatSaveData();
            statSaveData.setName(statType);
            statSaveData.setValue(value);

            flag.getFlagStats().getStats().put(statType, statSaveData);
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
