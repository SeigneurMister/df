package me.mister.money.commands;

import me.mister.money.MoneyPlugin;
import me.mister.money.gui.BankGUI;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BankCommand implements CommandExecutor {

    private final MoneyPlugin plugin;

    public BankCommand(MoneyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player p)) {
            sender.sendMessage("Cette commande est réservée aux joueurs.");
            return true;
        }

        // /bank → ouvre le menu
        if (args.length == 0) {
            p.openInventory(new BankGUI(plugin).getBankMenu(p));
            return true;
        }

        // /bank deposit <montant>
        if (args[0].equalsIgnoreCase("deposit")) {

            if (args.length < 2) {
                p.sendMessage("§cUsage : /bank deposit <montant>");
                return true;
            }

            try {
                double amount = Double.parseDouble(args[1]);

                if (amount <= 0) {
                    p.sendMessage("§cLe montant doit être positif.");
                    return true;
                }

                double money = plugin.getMoneyManager().get(p.getUniqueId());

                if (money < amount) {
                    p.sendMessage("§cVous n'avez pas assez d'argent.");
                    return true;
                }

                plugin.getMoneyManager().remove(p.getUniqueId(), amount);
                plugin.getBankManager().deposit(p.getUniqueId(), amount);

                plugin.getLogManager().log(p.getUniqueId(), "BANK_DEPOSIT", amount, "cli");

                p.sendMessage("§aVous avez déposé §e" + amount + "€ §adans votre banque.");
            } catch (NumberFormatException e) {
                p.sendMessage("§cMontant invalide.");
            }

            return true;
        }

        // /bank withdraw <montant>
        if (args[0].equalsIgnoreCase("withdraw")) {

            if (args.length < 2) {
                p.sendMessage("§cUsage : /bank withdraw <montant>");
                return true;
            }

            try {
                double amount = Double.parseDouble(args[1]);

                if (amount <= 0) {
                    p.sendMessage("§cLe montant doit être positif.");
                    return true;
                }

                double bank = plugin.getBankManager().get(p.getUniqueId());

                if (bank < amount) {
                    p.sendMessage("§cVous n'avez pas assez à la banque.");
                    return true;
                }

                plugin.getBankManager().withdraw(p.getUniqueId(), amount);
                plugin.getMoneyManager().add(p.getUniqueId(), amount);

                plugin.getLogManager().log(p.getUniqueId(), "BANK_WITHDRAW", amount, "cli");

                p.sendMessage("§aVous avez retiré §e" + amount + "€ §ade votre banque.");
            } catch (NumberFormatException e) {
                p.sendMessage("§cMontant invalide.");
            }

            return true;
        }

        // /bank interest (admin)
        if (args[0].equalsIgnoreCase("interest")) {

            if (!sender.hasPermission("bank.admin")) {
                p.sendMessage("§cVous n'avez pas la permission.");
                return true;
            }

            for (Player target : Bukkit.getOnlinePlayers()) {

                UUID uuid = target.getUniqueId();
                double amount = plugin.getBankManager().get(uuid);
                double interest = amount * 0.0175;

                plugin.getBankManager().deposit(uuid, interest);
                plugin.getBankManager().setLastInterest(uuid, System.currentTimeMillis());

                plugin.getLogManager().log(uuid, "BANK_INTEREST", interest, "manual");

                target.sendMessage("§aLes intérêts bancaires ont été appliqués !");
            }

            sender.sendMessage("§aIntérêts appliqués à tous les joueurs !");
            return true;
        }

        p.sendMessage("§cSous-commande inconnue.");
        return true;
    }
}
