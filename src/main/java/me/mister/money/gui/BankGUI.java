package me.mister.money.gui;

import me.mister.money.MoneyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BankGUI {

    private final MoneyPlugin plugin;

    public BankGUI(MoneyPlugin plugin) {
        this.plugin = plugin;
    }

    public Inventory getBankMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "§6Banque");
        // Tu pourras remplir plus tard avec des items
        return inv;
    }
}
