package me.mister.money.api;

import me.mister.money.MoneyPlugin;

import java.util.UUID;

public class MoneyAPI {

    private static MoneyPlugin plugin;

    public static void init(MoneyPlugin pl) {
        plugin = pl;
    }

    public static double getMoney(UUID uuid) {
        return plugin.getMoneyManager().get(uuid);
    }

    public static void addMoney(UUID uuid, double amount) {
        plugin.getMoneyManager().add(uuid, amount);
    }

    public static void removeMoney(UUID uuid, double amount) {
        plugin.getMoneyManager().remove(uuid, amount);
    }

    public static void setMoney(UUID uuid, double amount) {
        plugin.getMoneyManager().set(uuid, amount);
    }
}
