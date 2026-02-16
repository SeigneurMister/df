package me.mister.money.commands;

import org.bukkit.command.*;

public class MoneyHelpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        sender.sendMessage("§6=== Aide Money ===");
        sender.sendMessage("§e/money §7→ Voir votre argent");
        sender.sendMessage("§e/pay <joueur> <montant> §7→ Payer un joueur");
        sender.sendMessage("§e/bank §7→ Ouvrir la banque");
        sender.sendMessage("§e/bank deposit <montant> §7→ Déposer");
        sender.sendMessage("§e/bank withdraw <montant> §7→ Retirer");
        sender.sendMessage("");
        sender.sendMessage("§c=== Admin ===");
        sender.sendMessage("§c/moneyadmin set <joueur> <montant>");
        sender.sendMessage("§c/moneyadmin add <joueur> <montant>");
        sender.sendMessage("§c/moneyadmin remove <joueur> <montant>");
        sender.sendMessage("§c/moneyadmin log <joueur|all>");

        return true;
    }
}
