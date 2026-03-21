package me.mister.money.managers;

import me.mister.money.MoneyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class InterestTask {

    private final MoneyPlugin plugin;
    private BukkitTask task;

    // 1 minute en m
    private static final long INTERVAL_MS = 60L * 1000L;
    private static final double RATE = 0.0175; // 1.75%

    public InterestTask(MoneyPlugin plugin) {
        this.plugin = plugin;
    }

    public void start() {

        // Vérification toutes les minutes
        this.task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {

            long now = System.currentTimeMillis();

            for (Player player : Bukkit.getOnlinePlayers()) {
                UUID uuid = player.getUniqueId();

                long last = plugin.getBankManager().getLastInterest(uuid);
                if (now - last >= INTERVAL_MS) {

                    double amount = plugin.getBankManager().get(uuid);
                    if (amount <= 0) continue;

                    double interest = amount * RATE;
                    plugin.getBankManager().deposit(uuid, interest);
                    plugin.getBankManager().setLastInterest(uuid, now);

                    player.sendMessage("§aVos intérêts bancaires ont été appliqués : §e" + interest + "€");
                }
            }

        }, 20L, 20L * 60L);
    }

    public void stop() {
        if (task != null) task.cancel();
    }
}
