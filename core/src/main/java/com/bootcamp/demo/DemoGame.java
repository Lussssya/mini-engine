package com.bootcamp.demo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.bootcamp.demo.data.game.*;
import com.bootcamp.demo.data.save.*;
import com.bootcamp.demo.events.GameStartedEvent;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.events.core.EventModule;
import com.bootcamp.demo.data.game.MilitaryGearGameData.Slot;
import com.bootcamp.demo.managers.SaveManager;

import java.util.Locale;

public class DemoGame extends Game {

    @Override
    public void create () {
        Gdx.input.setInputProcessor(new InputMultiplexer());

        final GameData gameData = new GameData();
        API.Instance().register(GameData.class, gameData);
        gameData.load();

        API.Instance().register(SaveManager.class, new SaveManager());
        API.get(SaveManager.class).loadSaveData();

        setupMilitaryGears();
        setupAccessoryGears();

        setScreen(new GameScreen());
        API.get(EventModule.class).fireEvent(GameStartedEvent.class);
    }

    private static void setupMilitaryGears () {
        final MilitaryGearsSaveData militariesSaveData = new MilitaryGearsSaveData();

        MilitaryGearSaveData gear1 = createMilitary("angel-bow", Slot.WEAPON, 1, 'D', GearRarity.ELITE);
        setGearStats(gear1.getGearStats(), new Object[][]{
            {"hp", 2.5f},
            {"atk", 918f},
            {"dodge", 5.67f},
            {"combo", 5.67f},
            {"poison", 5.67f}
        });

        MilitaryGearSaveData gear2 = createMilitary("hard-armor", Slot.MELEE, 2, 'D', GearRarity.HARDENED);
        setGearStats(gear2.getGearStats(), new Object[][]{
            {"regen", 1.5f}
        });

        MilitaryGearSaveData gear3 = createMilitary("magic-ring", Slot.HEAD, 1, 'A', GearRarity.OBLIVION);
        setGearStats(gear3.getGearStats(), new Object[][]{
            {"combo", 2f}
        });

        MilitaryGearSaveData gear4 = createMilitary("star-staff", Slot.BODY, 1, 'C', GearRarity.ETHEREAL);
        setGearStats(gear4.getGearStats(), new Object[][]{
            {"stun", 2f}
        });

        MilitaryGearSaveData gear5 = createMilitary("bloody-grail", Slot.GLOVES, 1, 'C', GearRarity.NUCLEAR);
        setGearStats(gear5.getGearStats(), new Object[][]{
            {"dodge", 2.5f}
        });

        MilitaryGearSaveData gear6 = createMilitary("mystic-staff", Slot.SHOES, 2, 'B', GearRarity.JUGGERNAUT);

        militariesSaveData.getMilitaries().put(Slot.WEAPON, gear1);
        militariesSaveData.getMilitaries().put(Slot.MELEE, gear2);
        militariesSaveData.getMilitaries().put(Slot.HEAD, gear3);
        militariesSaveData.getMilitaries().put(Slot.BODY, gear4);
        militariesSaveData.getMilitaries().put(Slot.GLOVES, gear5);
        militariesSaveData.getMilitaries().put(Slot.SHOES, gear6);

        API.get(SaveData.class).setMilitariesSaveData(militariesSaveData);
    }

    private static MilitaryGearSaveData createMilitary (String name, Slot slot, int level, char tier, GearRarity rarity) {
        MilitaryGearSaveData military = new MilitaryGearSaveData();
        military.setName(name);
        military.setSlot(slot);
        military.setLevel(level);
        military.setTier(tier);
        military.setRarity(rarity);
        return military;
    }

    private static void setupAccessoryGears () {
        final AccessoryGearsSaveData accessoriesSaveData = new AccessoryGearsSaveData();

        AccessoryGearSaveData accessory1 =
            createAccessory("pink-cape", AccessoryGearGameData.Slot.NECK, 2, GearRarity.ELITE);
        setGearStats(accessory1.getGearStats(), new Object[][]{
            {"atk", 750f},
            {"combo", 4.5f}
        });

        AccessoryGearSaveData accessory2 =
            createAccessory("thor-hammer", AccessoryGearGameData.Slot.WRIST, 1, GearRarity.NUCLEAR);
        setGearStats(accessory2.getGearStats(), new Object[][]{
            {"dodge", 3f}
        });

        AccessoryGearSaveData accessory3 =
            createAccessory("dragon-ring", AccessoryGearGameData.Slot.PANTS, 3, GearRarity.HARDENED);
        setGearStats(accessory3.getGearStats(), new Object[][]{
            {"hp", 1500f}
        });

        AccessoryGearSaveData accessory4 =
            createAccessory("elf-staff", AccessoryGearGameData.Slot.BELT, 2, GearRarity.JUGGERNAUT);
        setGearStats(accessory4.getGearStats(), new Object[][]{
            {"regen", 2f}
        });

        accessoriesSaveData.getAccessories().put(AccessoryGearGameData.Slot.NECK, accessory1);
        accessoriesSaveData.getAccessories().put(AccessoryGearGameData.Slot.WRIST, accessory2);
        accessoriesSaveData.getAccessories().put(AccessoryGearGameData.Slot.PANTS, accessory3);
        accessoriesSaveData.getAccessories().put(AccessoryGearGameData.Slot.BELT, accessory4);

        API.get(SaveData.class).setAccessoryGearsSaveData(accessoriesSaveData);
    }

    private static AccessoryGearSaveData createAccessory (String name, AccessoryGearGameData.Slot slot, int level, GearRarity rarity) {
        final AccessoryGearSaveData accessory = new AccessoryGearSaveData();

        accessory.setName(name);
        accessory.setSlot(slot);
        accessory.setLevel(level);
        accessory.setRarity(rarity);
        return accessory;
    }

    private static void setGearStats (StatsSaveData stats, Object[][] values) {
        for (Object[] pair : values) {
            PlayerStat statType = PlayerStat.valueOf(((String) pair[0]).toUpperCase(Locale.ENGLISH));
            float value = (float) pair[1];

            StatSaveData statSaveData = new StatSaveData();
            statSaveData.setStat(statType);
            statSaveData.setValue(value);

            stats.getStats().put(statType, statSaveData);
        }
    }

    @Override
    public void dispose () {
        super.dispose();
        API.Instance().dispose();
        Gdx.app.exit();
    }
}
