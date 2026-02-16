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
        this.file = new File(plugin.getDataFolder(), "bank.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void load() {
        if (!file.exists()) {
            plugin.saveResource("bank.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Impossible de sauvegarder bank.yml");
            e.printStackTrace();
        }
    }

    public double get(UUID uuid) {
        return config.getDouble("balances." + uuid, 0.0);
    }

    public void set(UUID uuid, double amount) {
        config.set("balances." + uuid, amount);
        save();
    }

    public void deposit(UUID uuid, double amount) {
        set(uuid, get(uuid) + amount);
    }

    public void withdraw(UUID uuid, double amount) {
        set(uuid, Math.max(0, get(uuid) - amount));
    }

    public long getLastInterest(UUID uuid) {
        return config.getLong("interest." + uuid, 0L);
    }

    public void setLastInterest(UUID uuid, long time) {
        config.set("interest." + uuid, time);
        save();
    }
}
