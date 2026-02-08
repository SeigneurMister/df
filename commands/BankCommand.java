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

        if (!(sender instanceof Player p)) return true;

        // Ouvrir la banque
        if (args.length == 0) {
            p.openInventory(new BankGUI().getBankMenu(p));
            return true;
        }

        // Dépôt
        if (args[0].equalsIgnoreCase("deposit")) {
            double amount = plugin.getMoneyManager().get(p.getUniqueId());
            plugin.getMoneyManager().remove(p.getUniqueId(), amount);
            plugin.getBankManager().deposit(p.getUniqueId(), amount);
            p.sendMessage("§aVous avez déposé tout votre argent !");
            return true;
        }

        // Retrait
        if (args[0].equalsIgnoreCase("withdraw")) {
            double amount = plugin.getBankManager().get(p.getUniqueId());
            plugin.getBankManager().withdraw(p.getUniqueId(), amount);
            plugin.getMoneyManager().add(p.getUniqueId(), amount);
            p.sendMessage("§aVous avez retiré tout votre argent !");
            return true;
        }

        // ADMIN : forcer les intérêts
        if (args[0].equalsIgnoreCase("interest") && sender.hasPermission("bank.admin")) {

            for (Player target : Bukkit.getOnlinePlayers()) {

                UUID uuid = target.getUniqueId();
                double amount = plugin.getBankManager().get(uuid);
                double interest = amount * 0.0175;

                plugin.getBankManager().deposit(uuid, interest);
                plugin.getBankManager().resetInterestTimer(uuid);

                target.sendMessage("§aLes intérêts bancaires ont été appliqués !");
            }

            sender.sendMessage("§aIntérêts appliqués à tous les joueurs !");
            return true;
        }

        return true;
    }
}
