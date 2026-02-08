package me.mister.money.gui;

import me.mister.money.MoneyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

public class BankGUI {

    public Inventory getBankMenu(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§9Banque");

        UUID uuid = p.getUniqueId();
        double bank = MoneyPlugin.getInstance().getBankManager().get(uuid);

        long nextInterest = MoneyPlugin.getInstance().getBankManager().getNextInterest(uuid);
        long now = System.currentTimeMillis();
        long remaining = Math.max(0, nextInterest - now);

        long hours = remaining / (1000 * 60 * 60);
        long minutes = (remaining / (1000 * 60)) % 60;

        // Coffre central
        ItemStack chest = new ItemStack(Material.CHEST);
        ItemMeta chestMeta = chest.getItemMeta();
        chestMeta.setDisplayName("§eVotre banque");
        chestMeta.setLore(List.of(
                "§7Argent stocké : §a" + bank + "$",
                " ",
                "§7Prochain intérêt dans :",
                "§b" + hours + "h " + minutes + "m"
        ));
        chest.setItemMeta(chestMeta);
        inv.setItem(13, chest);

        // Dépôt
        ItemStack deposit = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta dMeta = deposit.getItemMeta();
        dMeta.setDisplayName("§aDéposer");
        dMeta.setLore(List.of("§7Clique pour déposer de l'argent"));
        deposit.setItemMeta(dMeta);
        inv.setItem(11, deposit);

        // Retrait
        ItemStack withdraw = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta wMeta = withdraw.getItemMeta();
        wMeta.setDisplayName("§cRetirer");
        wMeta.setLore(List.of("§7Clique pour retirer de l'argent"));
        withdraw.setItemMeta(wMeta);
        inv.setItem(15, withdraw);

        return inv;
    }
}
