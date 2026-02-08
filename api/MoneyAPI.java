package me.mister.money.api;

import me.mister.money.MoneyPlugin;

import java.util.UUID;

public class MoneyAPI {

    private final MoneyPlugin plugin;

    public MoneyAPI(MoneyPlugin plugin) {
        this.plugin = plugin;
    }

    public void addMoney(UUID uuid, double amount) {
        plugin.getMoneyManager().add(uuid, amount);
    }

    public void removeMoney(UUID uuid, double amount) {
        plugin.getMoneyManager().remove(uuid, amount);
    }

    public double getMoney(UUID uuid) {
        return plugin.getMoneyManager().get(uuid);
    }
}
