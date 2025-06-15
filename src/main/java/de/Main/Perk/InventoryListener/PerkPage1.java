package de.Main.Perk.InventoryListener;

import de.Main.Perk.Main;
import de.Main.Perk.PerkManager;
import de.Main.Perk.gui.PerkGUI2;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static de.Main.Perk.gui.PerkGUI1.perkGUI;
import static de.Main.Perk.gui.PerkGUI1.perkGUItwo;


public class PerkPage1 implements Listener {

    public final int switchstatus[] = {19, 21, 23, 25, 37, 39, 41, 43, 45};
    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (event.getClickedInventory() == null || event.getCurrentItem() == null) return;
        YamlConfiguration config = Main.getuserconfiguration(player.getUniqueId());
        String title = event.getView().getTitle();
        ItemStack clicked = event.getCurrentItem();
        Material type = clicked.getType();
        List<String> activePerks = config.getStringList("activeperks");
        if (activePerks == null || activePerks.isEmpty()) {
            activePerks = new ArrayList<>();
            config.set("activeperks", activePerks);
            Main.saveuserdata(config, player.getUniqueId());
        }
        if (title.equalsIgnoreCase("§cPerks")) {
            event.setCancelled(true);

            if (type == Material.RED_DYE) {

                ItemStack active = new ItemStack(Material.LIME_DYE);
                ItemMeta activeMeta = active.getItemMeta();
                if (activeMeta != null) {
                    activeMeta.setDisplayName("§cDeaktivieren");
                    active.setItemMeta(activeMeta);
                }
                int slotNumber = event.getSlot();
                if (slotNumber == 53) {
                   player.openInventory(perkGUItwo);
                }
                if (Arrays.stream(switchstatus).anyMatch(i -> i == slotNumber)) {

                    if (perkGUI != null) {

                        if (slotNumber == 19 && player.hasPermission("be.perk.strength")) {
                            PerkManager.setPerk(player, "STRENGTH");
                            perkGUI.setItem(slotNumber, active);
                        }
                        if (slotNumber == 21 && player.hasPermission("be.perk.jumpboost")) {
                            PerkManager.setPerk(player, "JUMPBOOST");
                            perkGUI.setItem(slotNumber, active);
                        }
                        if (slotNumber == 23 && player.hasPermission("be.perk.speed")) {
                            perkGUI.setItem(slotNumber, active);
                            PerkManager.setPerk(player, "SPEED");
                        }
                        if (slotNumber == 25 && player.hasPermission("be.perk.nightvision")) {
                            perkGUI.setItem(slotNumber, active);
                            PerkManager.setPerk(player, "NIGHT_VISION");
                        }
                        if (slotNumber == 37 && player.hasPermission("be.perk.regeneration")) {
                            perkGUI.setItem(slotNumber, active);
                            PerkManager.setPerk(player, "REGENERATION");
                        }
                        if (slotNumber == 39 && player.hasPermission("be.perk.fire")) {
                            perkGUI.setItem(slotNumber, active);
                            PerkManager.setPerk(player, "FIRE");
                        }
                        if (slotNumber == 41 && player.hasPermission("be.perk.ghast")) {
                            perkGUI.setItem(slotNumber, active);
                            PerkManager.setPerk(player, "GHAST");
                        }
                        if (slotNumber == 43 && player.hasPermission("be.perk.zombie")) {
                            perkGUI.setItem(slotNumber, active);
                            PerkManager.setPerk(player, "ZOMBIE");
                        }





                    } else {
                        player.sendMessage("§cDie GUI ist nicht korrekt initialisiert.");
                    }
                }
            }  else if (type == Material.LIME_DYE) { // Deaktivieren eines Perks
                ItemStack deactive = new ItemStack(Material.RED_DYE);
                ItemMeta deactivemeta = deactive.getItemMeta();
                if (deactivemeta != null) {
                    deactivemeta.setDisplayName("§aAktivieren");
                    deactive.setItemMeta(deactivemeta);
                }

                int slotNumber = event.getSlot();

                if (Arrays.stream(switchstatus).anyMatch(i -> i == slotNumber) && perkGUI != null) {
                    perkGUI.setItem(slotNumber, deactive);
                    switch (slotNumber) {
                        case 19 -> PerkManager.removePerk(player, "STRENGTH");
                        case 21 -> PerkManager.removePerk(player, "JUMPBOOST");
                        case 23 -> PerkManager.removePerk(player, "SPEED");
                        case 25 -> PerkManager.removePerk(player, "NIGHT_VISION");
                        case 37 -> PerkManager.removePerk(player, "REGENERATION");
                        case 39 -> PerkManager.removePerk(player, "FIRE");
                        case 41 -> PerkManager.removePerk(player, "GHAST");
                        case 43 -> PerkManager.removePerk(player, "ZOMBIE");
                    }
                } else {
                    player.sendMessage("§cDas GUI ist nicht korrekt initialisiert.");
                }
            }
        }
    }
    }

