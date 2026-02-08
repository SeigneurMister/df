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

        Player target = Bukkit.getPlayer(args[0]);
        double amount = Double.parseDouble(args[1]);

        if (plugin.getMoneyManager().get(p.getUniqueId()) < amount) {
            p.sendMessage("§cVous n'avez pas assez d'argent !");
            return true;
        }

        plugin.getMoneyManager().remove(p.getUniqueId(), amount);
        plugin.getMoneyManager().add(target.getUniqueId(), amount);

        p.sendMessage("§aVous avez payé " + target.getName() + " §e" + amount + "$");
        target.sendMessage("§aVous avez reçu §e" + amount + "$ §ade " + p.getName());

        return true;
    }
}
