package de.Main.Perk;

import co.aikar.commands.PaperCommandManager;
import de.Main.Perk.InventoryListener.PerkPage1;
import de.Main.Perk.InventoryListener.PerkPage2;
import de.Main.Perk.command.PerkCommand;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public final class Main extends JavaPlugin {

    public static Economy economy;
    public static File configFile;
    public static YamlConfiguration config;
    private static Main instance;
    public static Main getInstance() {
        return instance;
    }
    @Override
    public void onEnable() {
        PaperCommandManager manager = new PaperCommandManager(this);
        instance = this; // Setzt die Singleton-Instanz
        // Dein bisheriger onEnable-Code...
        if (!setupEconomy()) {
            getLogger().severe("Vault wurde nicht gefunden oder kein Economy-Plugin installiert!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        createperkconfig();
        createUserdataFolder();
        manager.registerCommand(new PerkCommand());
        //-----LISTENER----//
        Bukkit.getPluginManager().registerEvents(new PerkManager(), this);
        Bukkit.getPluginManager().registerEvents(new PerkPage1(), this);
        Bukkit.getPluginManager().registerEvents(new PerkPage2(), this);
        getLogger().info("Perkmanager ist da ");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) return false;
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;
        economy = rsp.getProvider();
        return economy != null;
    }

    @Override
    public void onDisable() {
        getLogger().info("Perk Plugin deaktiviert.");
    }

    public static YamlConfiguration createperkconfig() {
        File folder = new File("plugins/Perks");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        configFile = new File(folder, "Perks.yml");

        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(configFile);

        return config;
    }

    public static void createUserdataFolder() {
        File folder = new File("plugins/Perks/userdata");
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public static YamlConfiguration getuserconfiguration(UUID uuid) {

       File file = getUserFile(uuid);
        return YamlConfiguration.loadConfiguration(file);
    }

    public static void saveuserdata(YamlConfiguration config, UUID uuid) {
        try {
            config.save(getUserFile(uuid));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File getUserFile(UUID uuid) {
        File folder = new File("plugins/Perks/userdata");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(folder, uuid + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
