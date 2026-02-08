package me.mister.money.commands;

import me.mister.money.MoneyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class MoneyCommand implements CommandExecutor {

    private final MoneyPlugin plugin;

    public MoneyCommand(MoneyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        // /money
        if (args.length == 0) {
            if (!(sender instanceof Player p)) {
                sender.sendMessage("§cCommande réservée aux joueurs.");
                return true;
            }
            double money = plugin.getMoneyManager().get(p.getUniqueId());
            p.sendMessage("§eVotre argent : §a" + money + "$");
            return true;
        }

        // /money help
        if (args[0].equalsIgnoreCase("help")) {

            sender.sendMessage(" ");
            sender.sendMessage("§6§l===== AIDE MONEY =====");
            sender.sendMessage(" ");
            sender.sendMessage("§eCommandes Joueur :");
            sender.sendMessage("§f/money §7→ Voir votre argent");
            sender.sendMessage("§f/pay <joueur> <montant> §7→ Payer un joueur");
            sender.sendMessage("§f/bank §7→ Ouvrir votre banque");
            sender.sendMessage("§f/bank deposit §7→ Déposer votre argent");
            sender.sendMessage("§f/bank withdraw §7→ Retirer votre argent");
            sender.sendMessage(" ");
            sender.sendMessage("§cCommandes Admin :");
            sender.sendMessage("§f/money set <joueur> <montant> §7→ Définir l'argent");
            sender.sendMessage("§f/money add <joueur> <montant> §7→ Ajouter de l'argent");
            sender.sendMessage("§f/money remove <joueur> <montant> §7→ Retirer de l'argent");
            sender.sendMessage("§f/money log <joueur|all> §7→ Voir les logs");
            sender.sendMessage("§f/bank interest §7→ Forcer les intérêts bancaires");
            sender.sendMessage(" ");
            sender.sendMessage("§6§l========================");
            sender.sendMessage(" ");
            return true;
        }

        // /money set <joueur> <montant>
        if (args[0].equalsIgnoreCase("set") && sender.hasPermission("money.admin")) {

            if (args.length < 3) {
                sender.sendMessage("§cUsage : /money set <joueur> <montant>");
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage("§cJoueur introuvable.");
                return true;
            }

            double amount = Double.parseDouble(args[2]);
            plugin.getMoneyManager().set(target.getUniqueId(), amount);

            sender.sendMessage("§aArgent défini pour " + target.getName());
            return true;
        }

        // /money add <joueur> <montant>
        if (args[0].equalsIgnoreCase("add") && sender.hasPermission("money.admin")) {

            if (args.length < 3) {
                sender.sendMessage("§cUsage : /money add <joueur> <montant>");
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage("§cJoueur introuvable.");
                return true;
            }

            double amount = Double.parseDouble(args[2]);
            plugin.getMoneyManager().add(target.getUniqueId(), amount);

            sender.sendMessage("§aArgent ajouté à " + target.getName());
            return true;
        }

        // /money remove <joueur> <montant>
        if (args[0].equalsIgnoreCase("remove") && sender.hasPermission("money.admin")) {

            if (args.length < 3) {
                sender.sendMessage("§cUsage : /money remove <joueur> <montant>");
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage("§cJoueur introuvable.");
                return true;
            }

            double amount = Double.parseDouble(args[2]);
            plugin.getMoneyManager().remove(target.getUniqueId(), amount);

            sender.sendMessage("§cArgent retiré à " + target.getName());
            return true;
        }

        // /money log <joueur|all>
        if (args[0].equalsIgnoreCase("log") && sender.hasPermission("money.admin")) {

            if (args.length < 2) {
                sender.sendMessage("§cUsage : /money log <joueur|all>");
                return true;
            }

            sender.sendMessage("§eFonction logs à implémenter (fichier logs.yml).");
            return true;
        }

        return true;
    }
}
