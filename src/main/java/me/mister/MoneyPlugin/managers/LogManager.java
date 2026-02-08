package me.mister.money.managers;

import me.mister.money.MoneyPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LogManager {

    private final MoneyPlugin plugin;
    private File file;
    private FileConfiguration config;

    public LogManager(MoneyPlugin plugin) {
        this.plugin = plugin;
        load();
    }

    private void load() {
        file = new File(plugin.getDataFolder(), "logs.yml");
        if (!file.exists()) plugin.saveResource("logs.yml", false);
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try { config.save(file); } catch (IOException e) { e.printStackTrace(); }
    }

    public void log(String player, String action) {
        config.set(System.currentTimeMillis() + "", player + ": " + action);
        save();
    }
}
