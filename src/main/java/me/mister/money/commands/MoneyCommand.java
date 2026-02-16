package me.mister.money.commands;

import me.mister.money.MoneyPlugin;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class MoneyCommand implements CommandExecutor {

    private final MoneyPlugin plugin;

    public MoneyCommand(MoneyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player p)) return true;

        double money = plugin.getMoneyManager().get(p.getUniqueId());
        p.sendMessage("§aVotre argent : §e" + money + "€");

        return true;
    }
}
