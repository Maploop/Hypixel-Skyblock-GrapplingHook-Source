package me.deemu.grapplinghook;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.deemu.grapplinghook.Commands.mainCommand;

import java.util.HashMap;

public class main extends JavaPlugin implements Listener{
    private HashMap<String, Long> cooldown = new HashMap<String, Long>();
    private int cooldowntime = getConfig().getInt("cooldown-amount");

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        getCommand("grapplinghook").setExecutor(new mainCommand(this));
        getCommand("gh").setExecutor(new mainCommand(this));
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        console.sendMessage(utils.chat("&eGrappling Hook&a Plugin loaded successfully! &eVersion 1.2"));
    }

    @Override
    public void onDisable() {
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        console.sendMessage(utils.chat("&eGrappling Hook&c Plugin was disabled."));
    }


    @EventHandler()
    public void onRightClick(PlayerFishEvent e){
        Player p = e.getPlayer();
        ItemStack item = p.getItemInHand();
        if(e.getState() == PlayerFishEvent.State.REEL_IN || e.getState() == PlayerFishEvent.State.IN_GROUND){
            if(item.getItemMeta().getItemFlags().contains(ItemFlag.HIDE_ATTRIBUTES)){
                if(getConfig().getBoolean("cooldown-enabled") == true){
                    // adding cooldown
                    if(cooldown.containsKey(p.getName())){
                        // if player is inside our hashmap ::
                        if(cooldown.get(p.getName()) > System.currentTimeMillis()){
                            // they still have time in cooldown
                            p.sendMessage(utils.chat(getConfig().getString("messages.on-cooldown")));
                            if(getConfig().getBoolean("grapplinghook.error-sound-enabled") == true) {
                                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 2F, 0);
                            }
                            return;
                        }
                    }
                    cooldown.put(p.getName(), System.currentTimeMillis() + (cooldowntime * 1000));
                    Location loc1 = p.getLocation();
                    Location loc2 = e.getHook().getLocation();

                    Vector v = new Vector(loc2.getX() - loc1.getX(), 1, loc2.getZ() - loc1.getZ());
                    p.setVelocity(v);


                }
                else if (getConfig().getBoolean("cooldown-enabled") == false){
                    Location loc1 = p.getLocation();
                    Location loc2 = e.getHook().getLocation();

                    Vector v = new Vector(loc2.getX() - loc1.getX(), 1, loc2.getZ() - loc1.getZ());
                    p.setVelocity(v);
                }


            }


        }
    }



}
//Location loc1 = p.getLocation();
//Location loc2 = e.getHook().getLocation();
//
//                Vector v = new Vector(loc2.getX() - loc1.getX(), 1, loc2.getZ() - loc1.getZ());
//                p.setVelocity(v);
//
//
//
//
