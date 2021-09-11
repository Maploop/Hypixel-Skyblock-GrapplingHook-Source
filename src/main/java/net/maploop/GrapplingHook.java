package net.maploop;

import net.maploop.commands.ConsoleCommand;
import net.maploop.commands.MainCommand;
import net.maploop.listener.EntityDamage;
import net.maploop.listener.PlayerFish;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.*;

public class GrapplingHook extends JavaPlugin implements Listener {
    private static GrapplingHook plugin;
    public static final String PROJECT_VERSION = "1.7";

    public static Map<UUID, Long> FLYING_TIMEOUT = new HashMap<UUID, Long>();

    public static GrapplingHook getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new EntityDamage(), this);
        getServer().getPluginManager().registerEvents(new PlayerFish(), this);

        getCommand("grapplinghook").setExecutor(new MainCommand(this));
        getCommand("gh").setExecutor(new MainCommand(this));
        getCommand("gh").setTabCompleter(new Util());
        getCommand("grapplinghook").setTabCompleter(new Util());
        getCommand("ghconsolegive").setExecutor(new ConsoleCommand());

        ConsoleCommandSender console = Bukkit.getConsoleSender();
        console.sendMessage(Util.chat("&eGrappling Hook&a Plugin loaded successfully! &eVersion " + PROJECT_VERSION));
    }

    @Override
    public void onDisable() {
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        console.sendMessage(Util.chat("&eGrappling Hook&c Plugin was disabled."));
    }
}