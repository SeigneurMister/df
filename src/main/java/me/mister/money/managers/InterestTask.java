package me.mister.money.managers;

import me.mister.money.MoneyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class InterestTask {

    private final MoneyPlugin plugin;
    private BukkitTask task;

    // 2 minutes en millisecondes
    private static final long INTERVAL_MS = 120L * 1000L;
    private static final double RATE = 0.0175; // 1.75%

    public InterestTask(MoneyPlugin plugin) {
        this.plugin = plugin;
    }

    public void start() {

        // Vérification toutes les secondes (précision parfaite)
        this.task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {

            long now = System.currentTimeMillis();

            for (Player player : Bukkit.getOnlinePlayers()) {
                UUID uuid = player.getUniqueId();

                long last = plugin.getBankManager().getLastInterest(uuid);

                // Si le joueur n'a jamais reçu d'intérêts, on initialise
                if (last == 0) {
                    plugin.getBankManager().setLastInterest(uuid, now);
                    continue;
                }

                // Si 2 minutes sont passées
                if (now - last >= INTERVAL_MS) {

                    double amount = plugin.getBankManager().get(uuid);
                    if (amount <= 0) continue;

                    double interest = amount * RATE;
                    plugin.getBankManager().deposit(uuid, interest);
                    plugin.getBankManager().setLastInterest(uuid, now);

                    String formatted = String.format("%.2f", interest);
                    player.sendMessage("§aVos intérêts bancaires ont été appliqués : §e" + formated + "€");
                }
            }

        }, 20L, 20L); // toutes les secondes
    }

    public void stop() {
        if (task != null) task.cancel();
    }

    /**
     * Retourne le temps restant avant les prochains intérêts pour un joueur.
     */
    public long getTimeRemaining(UUID uuid) {
        long last = plugin.getBankManager().getLastInterest(uuid);
        long now = System.currentTimeMillis();

        // Si jamais reçu d'intérêts → timer complet
        if (last == 0) {
            return INTERVAL_MS;
        }

        long elapsed = now - last;
        long remaining = INTERVAL_MS - elapsed;

        return Math.max(remaining, 0);
    }
}
