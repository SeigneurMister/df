package me.mister.money.managers;

import me.mister.money.MoneyPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class BankManager {

    private final MoneyPlugin plugin;
    private File file;
    private FileConfiguration config;

    public BankManager(MoneyPlugin plugin) {
        this.plugin = plugin;
        load();
    }

    private void load() {
        file = new File(plugin.getDataFolder(), "bank.yml");
        if (!file.exists()) plugin.saveResource("bank.yml", false);
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try { config.save(file); } catch (IOException e) { e.printStackTrace(); }
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public double get(UUID uuid) {
        return config.getDouble(uuid + ".amount", 0.0);
    }

    public void set(UUID uuid, double amount) {
        config.set(uuid + ".amount", amount);
        save();
    }

    public void deposit(UUID uuid, double amount) {
        set(uuid, get(uuid) + amount);
    }

    public void withdraw(UUID uuid, double amount) {
        set(uuid, Math.max(0, get(uuid) - amount));
    }

    // TIMER
    public long getNextInterest(UUID uuid) {
        return config.getLong(uuid + ".nextInterest", System.currentTimeMillis());
    }

    public void setNextInterest(UUID uuid, long time) {
        config.set(uuid + ".nextInterest", time);
        save();
    }

    public void resetInterestTimer(UUID uuid) {
        long next = System.currentTimeMillis() + (1000L * 60 * 60 * 48); // 48h
        setNextInterest(uuid, next);
    }
}
