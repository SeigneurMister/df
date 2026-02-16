package me.mister.money.managers;

import me.mister.money.MoneyPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class LogManager {

    private final MoneyPlugin plugin;
    private File file;
    private FileConfiguration config;

    public LogManager(MoneyPlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "logs.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void load() {
        if (!file.exists()) {
            plugin.saveResource("logs.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Impossible de sauvegarder logs.yml");
            e.printStackTrace();
        }
    }

    public void log(UUID uuid, String action, double amount, String info) {
        String path = "logs." + uuid.toString();
        List<String> list = config.getStringList(path);
        list.add(System.currentTimeMillis() + ";" + action + ";" + amount + ";" + info);
        config.set(path, list);
        save();
    }

    public List<String> getLogs(UUID uuid) {
        return config.getStringList("logs." + uuid.toString());
    }

    public Map<UUID, List<String>> getAllLogs() {
        Map<UUID, List<String>> map = new HashMap<>();
        if (config.getConfigurationSection("logs") == null) return map;
        for (String key : config.getConfigurationSection("logs").getKeys(false)) {
            UUID uuid = UUID.fromString(key);
            map.put(uuid, config.getStringList("logs." + key));
        }
        return map;
    }

    // "Export" simple : pour l'instant, juste log console
    public void export(UUID uuid) {
        plugin.getLogger().info("=== Logs pour " + uuid + " ===");
        for (String line : getLogs(uuid)) {
            plugin.getLogger().info(line);
        }
    }

    public void exportAll() {
        plugin.getLogger().info("=== Tous les logs ===");
        for (Map.Entry<UUID, List<String>> entry : getAllLogs().entrySet()) {
            plugin.getLogger().info("Joueur : " + entry.getKey());
            for (String line : entry.getValue()) {
                plugin.getLogger().info("  " + line);
            }
        }
    }
}
