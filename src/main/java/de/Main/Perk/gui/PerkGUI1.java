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

import java.util.*;

import static org.bukkit.Material.*;

public class PerkGUI1 {
    private final Economy economy;
    public static Inventory perkGUItwo;
    public static Inventory perkGUI;
    public final int switchstatus[] = {19, 21, 23, 25, 37, 39, 41, 43, 45};
    public static final int[] Darkglasmaingui = {
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 18, 27, 36,
            17, 26, 35, 44,
            45, 46, 47, 48, 49, 50, 51, 52, 53
    };
    public static final int[] grayglasmainguitwo = {
            11, 20, 29, 38, 31, 40, 13, 22, 15, 24, 33, 42
    };


    public PerkGUI1(Economy economy) {
        this.economy = economy;
    }


    public static void createGUI(Player player) {
        YamlConfiguration config = Main.getuserconfiguration(player.getUniqueId());
        perkGUItwo = Bukkit.createInventory(player, 54, "§cPerks - 2");
        perkGUI = Bukkit.createInventory(player, 54, "§cPerks");

        for (int pos : Darkglasmaingui) {
            perkGUI.setItem(pos, new ItemStack(BLACK_STAINED_GLASS_PANE));
        }
        for (int pos : grayglasmainguitwo) {
            perkGUI.setItem(pos, new ItemStack(GRAY_STAINED_GLASS_PANE));
        }

        ItemStack staerke = new ItemStack(IRON_SWORD);
        ItemMeta meta = staerke.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§aStärke Perk");

            staerke.setItemMeta(meta);
        }

        perkGUI.setItem(10, staerke);

        ItemStack sprung = new ItemStack(RABBIT_FOOT);
        ItemMeta sprungMeta = sprung.getItemMeta();
        if (sprungMeta != null) {
            sprungMeta.setDisplayName("§aSprungkraft Perk");
            sprung.setItemMeta(sprungMeta);
        }
        perkGUI.setItem(12, sprung);

        ItemStack speed = new ItemStack(SUGAR);
        ItemMeta meta1 = speed.getItemMeta();
        if (meta1 != null) {
            meta1.setDisplayName("§fSchnelligkeits Perk");
            speed.setItemMeta(meta1);
        }
        perkGUI.setItem(14, speed);

        ItemStack nachtsicht = new ItemStack(BEACON);
        ItemMeta meta2 = nachtsicht.getItemMeta();
        if (meta2 != null) {
            meta2.setDisplayName("§bNachtsicht Perk");
            nachtsicht.setItemMeta(meta2);
        }
        perkGUI.setItem(16, nachtsicht);

        ItemStack regeneratrion = new ItemStack(GOLDEN_CARROT);
        ItemMeta meta3 = regeneratrion.getItemMeta();
        if (meta3 != null) {
            meta3.setDisplayName("§cRegenerations Perk");
            regeneratrion.setItemMeta(meta3);
        }
        perkGUI.setItem(28, regeneratrion);

        ItemStack fire = new ItemStack(BLAZE_ROD);
        ItemMeta fireItemMeta = fire.getItemMeta();
        if (fireItemMeta != null) {
            fireItemMeta.setDisplayName("§cFeuerschutz Perk");
            fire.setItemMeta(fireItemMeta);
        }
        perkGUI.setItem(30, fire);

        ItemStack ghast = new ItemStack(GHAST_TEAR);
        ItemMeta ghastMeta = fire.getItemMeta();
        if (ghastMeta != null) {
            ghastMeta.setDisplayName("§cGhast Schutz Perk");
            ghast.setItemMeta(ghastMeta);
        }

        perkGUI.setItem(32, ghast);

        ItemStack zombie = new ItemStack(ZOMBIE_HEAD);
        ItemMeta zombieMeta = fire.getItemMeta();
        if (zombieMeta != null) {
            zombieMeta.setDisplayName("§cZombie Schutz Perk");
            zombie.setItemMeta(zombieMeta);
        }
        ItemStack nextpage = new ItemStack(PAPER);
        ItemMeta nextPageMeta = fire.getItemMeta();
        if (zombieMeta != null) {
            zombieMeta.setDisplayName("§cNächste Seite");
            zombie.setItemMeta(zombieMeta);
        }
        perkGUI.setItem(53, nextpage);
        perkGUI.setItem(34, zombie);

        fillGUI1WithRedDyeOrLimeDye(player, 19, "STRENGTH");
        fillGUI1WithRedDyeOrLimeDye(player, 21, "SPRUNGKRAFT");
        fillGUI1WithRedDyeOrLimeDye(player, 23, "SPEED");
        fillGUI1WithRedDyeOrLimeDye(player, 25, "NACHTSICHT");
        fillGUI1WithRedDyeOrLimeDye(player, 37, "SATURATION");
        fillGUI1WithRedDyeOrLimeDye(player, 39, "REGENERATION");
        fillGUI1WithRedDyeOrLimeDye(player, 41, "FIRE");
        fillGUI1WithRedDyeOrLimeDye(player, 43, "GHAST");

    }

    public static void fillGUI1WithRedDyeOrLimeDye(Player player, int slot, String perkName) {
        YamlConfiguration config = Main.getuserconfiguration(player.getUniqueId());
        boolean isActive = config.getStringList("activeperks").contains(perkName);

        ItemStack item;
        ItemMeta meta;

        if (isActive) {
            item = new ItemStack(Material.LIME_DYE);
            meta = item.getItemMeta();
            if (meta != null) {
                meta.setDisplayName("§aAktiviert");
                item.setItemMeta(meta);
            }
        } else {
            item = new ItemStack(Material.RED_DYE);
            meta = item.getItemMeta();
            if (meta != null) {
                meta.setDisplayName("§cDeaktiviert");
                item.setItemMeta(meta);
            }
        }

        perkGUI.setItem(slot, item);
    }

    public static void fillGUI2WithRedDyeOrLimeDye(Player player, int slot, String perkName) {
        YamlConfiguration config = Main.getuserconfiguration(player.getUniqueId());
        boolean isActive = config.getStringList("activeperks").contains(perkName);

        ItemStack item;
        ItemMeta meta;

        if (isActive) {
            item = new ItemStack(Material.LIME_DYE);
            meta = item.getItemMeta();
            if (meta != null) {
                meta.setDisplayName("§aAktiviert");
                item.setItemMeta(meta);
                System.out.println("LIMEDYE");
            }
        } else {
            item = new ItemStack(Material.RED_DYE);
            meta = item.getItemMeta();
            if (meta != null) {
                meta.setDisplayName("§cDeaktiviert");
                item.setItemMeta(meta);
                System.out.println("REDDYE");

            }
        }
        System.out.println("Slot: " + slot + ", PerkName: " + perkName);

        perkGUItwo.setItem(slot, item);
    }
}


