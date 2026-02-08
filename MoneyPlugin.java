package me.mister.money;

import me.mister.money.api.MoneyAPI;
import me.mister.money.commands.BankCommand;
import me.mister.money.commands.MoneyCommand;
import me.mister.money.commands.PayCommand;
import me.mister.money.managers.BankManager;
import me.mister.money.managers.InterestTask;
import me.mister.money.managers.LogManager;
import me.mister.money.managers.MoneyManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MoneyPlugin extends JavaPlugin {

    private static MoneyPlugin instance;

    private MoneyManager moneyManager;
    private BankManager bankManager;
    private LogManager logManager;
    private MoneyAPI api;

    @Override
    public void onEnable() {
        instance = this;

        moneyManager = new MoneyManager(this);
        bankManager = new BankManager(this);
        logManager = new LogManager(this);
        api = new MoneyAPI(this);

        getCommand("money").setExecutor(new MoneyCommand(this));
        getCommand("pay").setExecutor(new PayCommand(this));
        getCommand("bank").setExecutor(new BankCommand(this));
        getServer().getPluginManager().registerEvents(new BankGUIListener(), this);


        new InterestTask(this).runTaskTimer(this, 20L, 20L * 60 * 60 * 24 * 2); // Tous les 2 jours

        getLogger().info("MoneyPlugin activé !");
    }

    @Override
    public void onDisable() {
        moneyManager.save();
        bankManager.save();
        logManager.save();
    }

    public static MoneyPlugin getInstance() {
        return instance;
    }

    public MoneyManager getMoneyManager() {
        return moneyManager;
    }

    public BankManager getBankManager() {
        return bankManager;
    }

    public LogManager getLogManager() {
        return logManager;
    }

    public MoneyAPI getAPI() {
        return api;
    }
}
