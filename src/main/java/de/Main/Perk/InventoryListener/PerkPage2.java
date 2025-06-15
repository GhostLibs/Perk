package de.Main.Perk.InventoryListener;

import de.Main.Perk.Main;
import de.Main.Perk.PerkManager;
import de.Main.Perk.gui.PerkGUI1;
import de.Main.Perk.gui.PerkGUI2;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PerkPage2 implements Listener {

    private static final int[] perkSlots = {10, 12, 14, 16, 28, 30, 32, 38, 40, 42};

    private static final int[] activateSlots = {19, 21, 23, 25, 37, 39, 41, 38, 40, 42};


    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (event.getClickedInventory() == null || event.getCurrentItem() == null) return;

        String title = event.getView().getTitle();
        int clickedSlot = event.getSlot();
        ItemStack clickedItem = event.getCurrentItem();

        if (!title.equalsIgnoreCase("§cPerks - 2")) return;

        event.setCancelled(true);

        if (clickedSlot == 49) {
            player.openInventory(PerkGUI1.perkGUI);
            return;
        }

        for (int i = 0; i < activateSlots.length; i++) {
            if (clickedSlot == activateSlots[i]) {
                togglePerk(player, clickedItem, perkSlots[i]);
                break;
            }
        }
    }

    private void togglePerk(Player player, ItemStack clicked, int perkSlot) {
        YamlConfiguration config = Main.getuserconfiguration(player.getUniqueId());
        List<String> activePerks = config.getStringList("activeperks");
        String perkName = getPerkNameFromSlot(perkSlot);
        String permission = "be.perk." + perkName.toLowerCase();
        if (!player.hasPermission(permission)) {
            return;
        }

        if (clicked.getType() == Material.RED_DYE) {
            if (!activePerks.contains(perkName)) {
                activePerks.add(perkName);
                config.set("activeperks", activePerks);
                Main.saveuserdata(config, player.getUniqueId());
                PerkManager.setPerk(player, perkName);

                clicked.setType(Material.LIME_DYE);
                ItemMeta meta = clicked.getItemMeta();
                if (meta != null) meta.setDisplayName("§cDeaktivieren");
                clicked.setItemMeta(meta);
            }
        } else if (clicked.getType() == Material.LIME_DYE) {
            if (activePerks.contains(perkName)) {
                activePerks.remove(perkName);
                config.set("activeperks", activePerks);
                Main.saveuserdata(config, player.getUniqueId());
                PerkManager.removePerk(player, perkName);

                clicked.setType(Material.RED_DYE);
                ItemMeta meta = clicked.getItemMeta();
                if (meta != null) meta.setDisplayName("§aAktivieren");
                clicked.setItemMeta(meta);
            }
        }
    }

    private String getPerkNameFromSlot(int slot) {
        return switch (slot) {
            case 10 -> "HASTE";
            case 12 -> "WATER_BREATHING";
            case 14 -> "LUCK";
            case 16 -> "SATURATION";
            case 38 -> "RESISTANCE";
            case 40 -> "MAGNET";
            case 42 -> "AUTO_SMELT";
            default -> "UNKNOWN_PERK";
        };
    }

}
