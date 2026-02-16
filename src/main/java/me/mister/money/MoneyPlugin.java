package me.mister.money;

import me.mister.money.api.MoneyAPI;
import me.mister.money.commands.*;
import me.mister.money.managers.*;
import org.bukkit.plugin.java.JavaPlugin;

public class MoneyPlugin extends JavaPlugin {

    private MoneyManager moneyManager;
    private BankManager bankManager;
    private LogManager logManager;
    private InterestTask interestTask;

    @Override
    public void onEnable() {
        saveDefaultConfigFiles();

        this.moneyManager = new MoneyManager(this);
        this.bankManager = new BankManager(this);
        this.logManager = new LogManager(this);

        this.moneyManager.load();
        this.bankManager.load();
        this.logManager.load();

        this.interestTask = new InterestTask(this);
        this.interestTask.start();

        MoneyAPI.init(this);

        getCommand("money").setExecutor(new MoneyCommand(this));
        getCommand("pay").setExecutor(new PayCommand(this));
        getCommand("bank").setExecutor(new BankCommand(this));
        getCommand("moneyadmin").setExecutor(new MoneyAdminCommand(this));
        getCommand("moneyhelp").setExecutor(new MoneyHelpCommand());

        getLogger().info("MoneyPlugin activé.");
    }

    @Override
    public void onDisable() {
        if (interestTask != null) interestTask.stop();
        if (moneyManager != null) moneyManager.save();
        if (bankManager != null) bankManager.save();
        if (logManager != null) logManager.save();
        getLogger().info("MoneyPlugin désactivé.");
    }

    private void saveDefaultConfigFiles() {
        saveResource("money.yml", false);
        saveResource("bank.yml", false);
        saveResource("logs.yml", false);
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
}
