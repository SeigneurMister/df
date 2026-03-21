package me.mister.money.commands;

import me.mister.money.managers.InterestTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BankInteretCommand implements CommandExecutor {

    private final InterestTask interestTask;

    public BankInteretCommand(InterestTask interestTask) {
        this.interestTask = interestTask;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Commande réservée aux joueurs.");
            return true;
        }

        Player player = (Player) sender;

        long remaining = interestTask.getTimeRemaining(player.getUniqueId());

        long seconds = remaining / 1000;
        long minutes = seconds / 60;
        long sec = seconds % 60;

        player.sendMessage("§eVos intérêts arriveront dans §6" + minutes + " minutes §eet §6" + sec + " secondes§e.");
        return true;
    }
}
