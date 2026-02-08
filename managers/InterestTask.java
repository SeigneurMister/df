package me.mister.money.managers;

import me.mister.money.MoneyPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class InterestTask extends BukkitRunnable {

    private final MoneyPlugin plugin;

    public InterestTask(MoneyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {

        BankManager bank = plugin.getBankManager();

        for (String key : bank.getConfig().getKeys(false)) {

            UUID uuid = UUID.fromString(key);

            long next = bank.getNextInterest(uuid);
            long now = System.currentTimeMillis();

            if (now >= next) {

                double amount = bank.get(uuid);
                double interest = amount * 0.0175;

                bank.deposit(uuid, interest);
                bank.resetInterestTimer(uuid);
            }
        }

        plugin.getLogger().info("Intérêts bancaires appliqués !");
    }
}
