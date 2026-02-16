package me.mister.money.commands;

import me.mister.money.MoneyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {

    private final MoneyPlugin plugin;

    public PayCommand(MoneyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player p)) return true;

        if (args.length < 2) {
            p.sendMessage("§cUsage : /pay <joueur> <montant>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            p.sendMessage("§cJoueur introuvable.");
            return true;
        }

        try {
            double amount = Double.parseDouble(args[1]);

            if (amount <= 0) {
                p.sendMessage("§cMontant invalide.");
                return true;
            }

            if (plugin.getMoneyManager().get(p.getUniqueId()) < amount) {
                p.sendMessage("§cVous n'avez pas assez d'argent.");
                return true;
            }

            plugin.getMoneyManager().remove(p.getUniqueId(), amount);
            plugin.getMoneyManager().add(target.getUniqueId(), amount);

            plugin.getLogManager().log(p.getUniqueId(), "PAY_SEND", amount, "to:" + target.getName());
            plugin.getLogManager().log(target.getUniqueId(), "PAY_RECEIVE", amount, "from:" + p.getName());

            p.sendMessage("§aVous avez payé §e" + amount + "€ §aà §e" + target.getName());
            target.sendMessage("§e" + p.getName() + " §avous a payé §e" + amount + "€");

        } catch (NumberFormatException e) {
            p.sendMessage("§cMontant invalide.");
        }

        return true;
    }
}
