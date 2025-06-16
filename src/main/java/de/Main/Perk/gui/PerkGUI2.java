package de.Main.Perk.gui;

import de.Main.Perk.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static de.Main.Perk.gui.PerkGUI1.fillGUI2WithRedDyeOrLimeDye;
import static de.Main.Perk.gui.PerkGUI1.perkGUItwo;

public class PerkGUI2 {
    private final Economy economy;
    private static final Map<UUID, Inventory> playerPerkGUIs = new HashMap<>();
    private static final Map<UUID, Inventory> playerPerkGUItwos = new HashMap<>();
    private static final int[] darkglasmainguitwo = {
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 18, 27, 36,
            17, 26, 35, 44,
            45, 46, 47, 48, 49, 50, 51, 52, 53
    };
    private static final int[] grayglasmainguitwo = {
            28, 37, 11, 20, 30, 39, 13, 22, 32, 41, 15, 24, 34, 43};

    public PerkGUI2(Economy economy) {
        this.economy = economy;
    }

    public static void createGUIs(Player player) {
        UUID playerId = player.getUniqueId();

        Inventory perkGUI = Bukkit.createInventory(player, 54, "§cPerks");
        ItemStack staerke = new ItemStack(Material.IRON_SWORD);
        ItemMeta staerkeMeta = staerke.getItemMeta();
        if (staerkeMeta != null) {
            staerkeMeta.setDisplayName("§aStärke Perk");
            staerke.setItemMeta(staerkeMeta);
        }
        perkGUI.setItem(10, staerke);
        playerPerkGUIs.put(playerId, perkGUI);

        ItemStack Glas = new ItemStack(Material.PAPER);
        ItemMeta GlasMeta = Glas.getItemMeta();
        if (GlasMeta != null) {
            GlasMeta.setDisplayName(" ");
            GlasMeta.setCustomModelData(1000);
            Glas.setItemMeta(GlasMeta);
        }

        for (int pos : darkglasmainguitwo) {
            perkGUItwo.setItem(pos, Glas);
        }
        for (int pos : grayglasmainguitwo) {
            perkGUItwo.setItem(pos, Glas);
        }

        addItem(perkGUItwo, Material.DIAMOND_PICKAXE, "§6Haste Perk", 10);
        addItem(perkGUItwo, Material.TRIDENT, "§bWater Breathing Perk", 12);
        addItem(perkGUItwo, Material.FISHING_ROD, "§aLuck Perk", 14);
        addItem(perkGUItwo, Material.GOLDEN_APPLE, "§eSaturation Perk", 16);
        addItem(perkGUItwo, Material.TOTEM_OF_UNDYING, "§cResistance Perk", 29);
        addItem(perkGUItwo, Material.MAGMA_BLOCK, "§6Magnet Perk", 31);
        addItem(perkGUItwo, Material.COAL, "§7Auto Smelt Perk", 33);
        fillGUI2WithRedDyeOrLimeDye(player, 19, "HASTE");
        fillGUI2WithRedDyeOrLimeDye(player, 21, "WATER_BREATHING");
        fillGUI2WithRedDyeOrLimeDye(player, 23, "LUCK");
        fillGUI2WithRedDyeOrLimeDye(player, 25, "SATURATION");
        fillGUI2WithRedDyeOrLimeDye(player, 38, "RESISTANCE");
        fillGUI2WithRedDyeOrLimeDye(player, 40, "MAGNET");
        fillGUI2WithRedDyeOrLimeDye(player, 42, "AUTO_SMELT");
        ItemStack nextpage = new ItemStack(Material.PAPER);
        ItemMeta nextPageMeta = nextpage.getItemMeta();
        if (nextPageMeta != null) {
            nextPageMeta.setDisplayName("§cZurück");
            nextPageMeta.setCustomModelData(1058);
        }
        playerPerkGUItwos.put(playerId, perkGUItwo);
        playerPerkGUItwos.put(playerId, perkGUItwo);
    }

    private static void addItem(Inventory inventory, Material material, String name, int slot) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            item.setItemMeta(meta);
        }
        inventory.setItem(slot, item);
    }


}


