package me.mister.money.commands;

import me.mister.money.MoneyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class MoneyAdminCommand implements CommandExecutor {

    private final MoneyPlugin plugin;

    public MoneyAdminCommand(MoneyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!sender.hasPermission("money.admin")) {
            sender.sendMessage("§cVous n'avez pas la permission.");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("§cUsage : /moneyhelp");
            return true;
        }

        String sub = args[0].toLowerCase();

        // /moneyadmin set <joueur> <montant>
        if (sub.equals("set") && args.length == 3) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage("§cJoueur introuvable.");
                return true;
            }

            try {
                double amount = Double.parseDouble(args[2]);
                plugin.getMoneyManager().set(target.getUniqueId(), amount);
                plugin.getLogManager().log(target.getUniqueId(), "ADMIN_SET", amount, "by:" + sender.getName());
                sender.sendMessage("§aArgent défini à §e" + amount + "€ §apour §e" + target.getName());
            } catch (NumberFormatException e) {
                sender.sendMessage("§cMontant invalide.");
            }
            return true;
        }

        // /moneyadmin add <joueur> <montant>
        if (sub.equals("add") && args.length == 3) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage("§cJoueur introuvable.");
                return true;
            }

            try {
                double amount = Double.parseDouble(args[2]);
                plugin.getMoneyManager().add(target.getUniqueId(), amount);
                plugin.getLogManager().log(target.getUniqueId(), "ADMIN_ADD", amount, "by:" + sender.getName());
                sender.sendMessage("§aAjouté §e" + amount + "€ §aà §e" + target.getName());
            } catch (NumberFormatException e) {
                sender.sendMessage("§cMontant invalide.");
            }
            return true;
        }

        // /moneyadmin remove <joueur> <montant>
        if (sub.equals("remove") && args.length == 3) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage("§cJoueur introuvable.");
                return true;
            }

            try {
                double amount = Double.parseDouble(args[2]);
                plugin.getMoneyManager().remove(target.getUniqueId(), amount);
                plugin.getLogManager().log(target.getUniqueId(), "ADMIN_REMOVE", amount, "by:" + sender.getName());
                sender.sendMessage("§aRetiré §e" + amount + "€ §ade §e" + target.getName());
            } catch (NumberFormatException e) {
                sender.sendMessage("§cMontant invalide.");
            }
            return true;
        }

        // /moneyadmin log <joueur|all>
        if (sub.equals("log")) {
            if (args.length == 2 && args[1].equalsIgnoreCase("all")) {
                plugin.getLogManager().exportAll();
                sender.sendMessage("§aLogs exportés (console).");
                return true;
            }

            if (args.length == 2) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage("§cJoueur introuvable.");
                    return true;
                }

                plugin.getLogManager().export(target.getUniqueId());
                sender.sendMessage("§aLogs exportés pour §e" + target.getName() + " §a(en console).");
                return true;
            }

            sender.sendMessage("§cUsage : /moneyadmin log <joueur|all>");
            return true;
        }

        sender.sendMessage("§cCommande inconnue. Faites /moneyhelp");
        return true;
    }
}
