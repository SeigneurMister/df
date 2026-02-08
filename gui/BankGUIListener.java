package me.mister.money.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BankGUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (!e.getView().getTitle().equals("§9Banque")) return;

        e.setCancelled(true);

        switch (e.getRawSlot()) {

            case 11 -> p.performCommand("bank deposit");
            case 15 -> p.performCommand("bank withdraw");
        }
    }
}
