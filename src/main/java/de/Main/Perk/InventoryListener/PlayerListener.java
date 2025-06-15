package de.Main.Perk.InventoryListener;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayerListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        createuserdata(player.getUniqueId());
    }
    public static YamlConfiguration createuserdata (UUID UUiD){

        File folder = new File("plugins/Perks/userdata");
        if (!folder.exists()) {
            folder.mkdirs();
        }


        File configFile = new File(folder, UUiD +  ".yml");


        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return YamlConfiguration.loadConfiguration(configFile);
    }
}
