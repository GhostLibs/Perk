package de.Main.Perk;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static de.Main.Perk.Main.config;

public class PerkManager implements Listener {
    private final Map<UUID, BukkitRunnable> perkTasks = new HashMap<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        YamlConfiguration config = Main.getuserconfiguration(uuid);

        if (!config.contains("activeperks")) {
            List<String> activePerks = new ArrayList<>();
            config.set("activeperks", activePerks);
            Main.saveuserdata(config, uuid);
        }

        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                YamlConfiguration updatedConfig = Main.getuserconfiguration(player.getUniqueId());
                List<String> activePerks = updatedConfig.getStringList("activeperks");

                if (activePerks.contains("HASTE")) {
                    addPotionEffect(player, PotionEffectType.HASTE, 200, 1);
                }
                if (activePerks.contains("STRENGTH")) {
                    addPotionEffect(player, PotionEffectType.STRENGTH, 200, 1);
                }
                if (activePerks.contains("NIGHT_VISION")) {
                    addPotionEffect(player, PotionEffectType.NIGHT_VISION, 200, 0);
                }
                if (activePerks.contains("SPEED")) {
                    addPotionEffect(player, PotionEffectType.SPEED, 200, 1);
                }
                if (activePerks.contains("JUMPBOOST")) {
                    addPotionEffect(player, PotionEffectType.JUMP_BOOST, 200, 1);
                }
                if (activePerks.contains("REGENERATION")) {
                    addPotionEffect(player, PotionEffectType.REGENERATION, 200, 1);
                }
                if (activePerks.contains("FIRE")) {
                    addPotionEffect(player, PotionEffectType.FIRE_RESISTANCE, 200, 0);
                }
                if (activePerks.contains("WATER_BREATHING")) {
                    addPotionEffect(player, PotionEffectType.WATER_BREATHING, 200, 0);
                }
                if (activePerks.contains("GLOWING")) {
                    addPotionEffect(player, PotionEffectType.GLOWING, 200, 0);
                }
                if (activePerks.contains("INVISIBILITY")) {
                    addPotionEffect(player, PotionEffectType.INVISIBILITY, 200, 0);
                }
                if (activePerks.contains("SLOW_FALLING")) {
                    addPotionEffect(player, PotionEffectType.SLOW_FALLING, 200, 0);
                }
                if (activePerks.contains("LUCK")) {
                    addPotionEffect(player, PotionEffectType.LUCK, 200, 0);
                }
                if (activePerks.contains("SATURATION")) {
                    addPotionEffect(player, PotionEffectType.SATURATION, 200, 0);
                }
                if (activePerks.contains("RESISTANCE")) {
                    addPotionEffect(player, PotionEffectType.RESISTANCE, 200, 1);
                }

            }
        };

        task.runTaskTimer(Main.getInstance(), 0L, 10L);
        perkTasks.put(uuid, task);
    }


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if (perkTasks.containsKey(uuid)) {
            perkTasks.get(uuid).cancel();
            perkTasks.remove(uuid);
        }
    }

    public static void setPerk(Player player, String perkName) {
        YamlConfiguration config = Main.getuserconfiguration(player.getUniqueId());
        List<String> playerPerks = config.getStringList("activeperks");
        if (!playerPerks.contains(perkName)) {
            playerPerks.add(perkName);
            config.set("activeperks", playerPerks);
            Main.saveuserdata(config, player.getUniqueId());
        }
    }

    public static void removePerk(Player player, String perkName) {
        YamlConfiguration config = Main.getuserconfiguration(player.getUniqueId());
        List<String> playerPerks = config.getStringList("activeperks");

        if (playerPerks.contains(perkName)) {
            playerPerks.remove(perkName);
            config.set("activeperks", playerPerks);
            Main.saveuserdata(config, player.getUniqueId());

            // Entfernen des entsprechenden Potion-Effekts
            switch (perkName) {
                case "STRENGTH" -> player.removePotionEffect(PotionEffectType.STRENGTH);
                case "SPEED" -> player.removePotionEffect(PotionEffectType.SPEED);
                case "NIGHT_VISION" -> player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                case "REGENERATION" -> player.removePotionEffect(PotionEffectType.REGENERATION);
                case "HASTE" -> player.removePotionEffect(PotionEffectType.HASTE);
                case "FIRE" -> player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                case "JUMPBOOST" -> player.removePotionEffect(PotionEffectType.JUMP_BOOST);
                case "ZOMBIE" -> {
                    // Logik für Zombie-Perk entfernen (falls nötig, spezifisch hinzufügen)
                }
                case "GHAST" -> {
                    // Logik für Ghast-Perk entfernen (falls nötig, spezifisch hinzufügen)
                }
            }
        }
    }


    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        YamlConfiguration config = Main.getuserconfiguration(player.getUniqueId());
        List<String> playerPerks = config.getStringList("activeperks");
        DamageType damageSource = event.getDamageSource().getDamageType();

        if (event instanceof EntityDamageByEntityEvent damageByEntityEvent) {
            Entity damager = damageByEntityEvent.getDamager();

            if (damager instanceof Fireball fireball) {
                if (playerPerks.contains("GHAST")) {
                    Entity shooter = (Entity) fireball.getShooter();
                    if (shooter instanceof Ghast || shooter instanceof Player) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }

            if (damageSource == DamageType.MOB_ATTACK) {
                if (damager.getType() == EntityType.ZOMBIE && playerPerks.contains("ZOMBIE")) {
                    event.setCancelled(true);
                    return;
                }
                if (damager.getType() == EntityType.GHAST && playerPerks.contains("GHAST")) {
                    event.setCancelled(true);
                    return;
                }
            }
        }

        if (damageSource == DamageType.FALL && playerPerks.contains("FALL_DAMAGE")) {
            event.setCancelled(true);
            return;
        }

        if ((damageSource == DamageType.IN_FIRE || damageSource == DamageType.ON_FIRE || damageSource == DamageType.LAVA)
                && playerPerks.contains("FIRE")) {
            event.setCancelled(true);
        }
    }



    private void addPotionEffect(Player player, PotionEffectType effectType, int duration, int amplifier) {
        PotionEffect current = player.getPotionEffect(effectType);
        if (current == null || current.getDuration() <= 15 * 20) {
            player.addPotionEffect(new PotionEffect(effectType, duration, amplifier, false, false), true);
        }
    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        YamlConfiguration config = Main.getuserconfiguration(player.getUniqueId());
        if (config.getStringList("activeperks").contains("KEEP_INVENTORY")) {
            event.setKeepInventory(true);
            event.getDrops().clear();
        }
    }
    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        Item item = event.getEntity();
        World world = item.getWorld();

        MetadataValue metadataValue = new FixedMetadataValue(Main.getInstance(), true);
        item.setMetadata("droppedByPlayer", metadataValue);

        world.getNearbyEntities(item.getLocation(), 7, 7, 7).stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity)
                .forEach(player -> {
                    YamlConfiguration config = Main.getuserconfiguration(player.getUniqueId());

                    if (config.getStringList("activeperks").contains("MAGNET")) {
                        if (!item.hasMetadata("droppedByPlayer") || !player.getUniqueId().equals(item.getThrower())) {
                            player.getInventory().addItem(item.getItemStack());
                            item.remove();
                        }
                    }
                });
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        YamlConfiguration config = Main.getuserconfiguration(player.getUniqueId());
        if (!config.getStringList("activeperks").contains("AUTO_SMELT")) {return;}
        Material blockType = block.getType();

        ItemStack smeltedDrop = null;


        switch (blockType) {
            case IRON_ORE -> smeltedDrop = new ItemStack(Material.IRON_INGOT);
            case DEEPSLATE_IRON_ORE -> smeltedDrop = new ItemStack(Material.IRON_INGOT);
            case GOLD_ORE -> smeltedDrop = new ItemStack(Material.GOLD_INGOT);
            case DEEPSLATE_GOLD_ORE -> smeltedDrop = new ItemStack(Material.GOLD_INGOT);
            case COPPER_ORE -> smeltedDrop = new ItemStack(Material.COPPER_INGOT);
            case DEEPSLATE_COPPER_ORE -> smeltedDrop = new ItemStack(Material.COPPER_INGOT);
            case ANCIENT_DEBRIS -> smeltedDrop = new ItemStack(Material.NETHERITE_SCRAP);
            case NETHER_GOLD_ORE -> smeltedDrop = new ItemStack(Material.GOLD_INGOT);
            case LAPIS_ORE -> smeltedDrop = new ItemStack(Material.LAPIS_LAZULI, 4);
            case DEEPSLATE_LAPIS_ORE -> smeltedDrop = new ItemStack(Material.LAPIS_LAZULI, 4);
            case DIAMOND_ORE -> smeltedDrop = new ItemStack(Material.DIAMOND);
            case DEEPSLATE_DIAMOND_ORE -> smeltedDrop = new ItemStack(Material.DIAMOND);
            case EMERALD_ORE -> smeltedDrop = new ItemStack(Material.EMERALD);
            case DEEPSLATE_EMERALD_ORE -> smeltedDrop = new ItemStack(Material.EMERALD);
            case REDSTONE_ORE -> smeltedDrop = new ItemStack(Material.REDSTONE, 4);
        }


        if (smeltedDrop != null) {
            event.setDropItems(false);
            block.getWorld().dropItemNaturally(block.getLocation(), smeltedDrop);
        }
    }
}
