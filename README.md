# Loot Page – IDLE Outpost UI Copy

This project recreates the **Looting Page** UI from the mobile game **IDLE Outpost** by *Rockbite Games*. It mimics the original layout and user interactions, while introducing gameplay concepts such as **Game Data**, **Save Data**, and basic **stat computation**.

---

## 🧩 Functionality

* 🛡️ **Gear Display** – Military gear items are displayed in slot-specific containers.
* 🖱️ **Gear Info Dialog** – Clicking a gear opens a dialog showing its level, tier, rarity, and stats.
* 📈 **Stat Calculation** – Player's overall stats are computed by the `MissionsManager` using gear data.
* 🔘 **Expandable UI** – The layout includes buttons reserved for future functionality.
* 🖼️ **Visual Representation** – Icons and backgrounds are loaded from the `rows/lootPage` assets folder.

---

## 🧱 Project Structure

* `MissionsPage` – The main page that builds the UI and sets data for each gear slot.
* `MilitaryGearDialog` – Displays gear information in a popup dialog.
* `MissionsManager` – Handles computing the player's total stats.
* `data.game.*` – Contains static, predefined game data (e.g., gear definitions).
* `data.save.*` – Holds save-state information tied to the player's progress.
* `rows/lootPage` – Stores all image assets used in the UI.

---

## 🚀 How to Run

1. Open the file:
   ```
   lwjgl3/src/main/java/com/bootcamp/demo/lwjgl3/Lwjgl3Launcher.java
   ```
2. Run `Lwjgl3Launcher.java`.
3. Once the game launches, press **M** on your keyboard to open the Loot Page.

---

## ✅ Status

* Core UI layout completed.
* Gear detail dialog implemented.
* Future features can be added via existing buttons and structure.

---

## 👾 Enjoy!
