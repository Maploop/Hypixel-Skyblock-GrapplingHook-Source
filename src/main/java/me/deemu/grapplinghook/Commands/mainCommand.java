package me.deemu.grapplinghook.Commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.deemu.grapplinghook.main;
import me.deemu.grapplinghook.utils;

import java.util.ArrayList;
import java.util.List;

public class mainCommand implements CommandExecutor {
    public mainCommand(main plugin) {
        this.plugin = plugin;
    }

    main plugin;


    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Player p = (Player) sender;
        TextComponent discord = new TextComponent("[SUPPORT DISCORD]");
        discord.setColor(net.md_5.bungee.api.ChatColor.GOLD);
        discord.setBold(true);
        discord.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to join Derporino's discord!").
                color(net.md_5.bungee.api.ChatColor.BLUE).create()));
        discord.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/xSutrwe"));
                        if(s.equalsIgnoreCase("ghconsolegive")){
                            if(args.length == 0){
                                sender.sendMessage(utils.chat("&cPlease specify a player."));
                            }
                            else if(args[0] != null){
                                Player target = Bukkit.getPlayer(args[0]);
                                if (target != null && target.isOnline()) {
                                    ItemStack grappling_hook = new ItemStack(Material.FISHING_ROD);
                                    ItemMeta grappling_hook_meta = grappling_hook.getItemMeta();
                                    grappling_hook_meta.setDisplayName(utils.chat(plugin.getConfig().getString("grapplinghook.displayname")));
                                    List<String> grappling_hook_lore = new ArrayList<String>();
                                    if (plugin.getConfig().getBoolean("grapplinghook.lore-enabled") == true) {
                                        grappling_hook_lore.add(ChatColor.GRAY + "Travel in style using");
                                        grappling_hook_lore.add(ChatColor.GRAY + "this Grappling Hook.");
                                        grappling_hook_lore.add("");
                                        grappling_hook_lore.add(ChatColor.GREEN + "" + ChatColor.BOLD + "UNCOMMON");
                                    }
                                    if (plugin.getConfig().getBoolean("grapplinghook.shiny") == true) {
                                        grappling_hook_meta.addEnchant(Enchantment.LUCK, 1, false);
                                    }
                                    if (plugin.getConfig().getBoolean("grapplinghook.unbreakable") == true) {
                                        grappling_hook_meta.setUnbreakable(true);
                                    }
                                    grappling_hook_meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                                    grappling_hook_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                                    grappling_hook_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                                    grappling_hook_meta.setLore(grappling_hook_lore);
                                    grappling_hook.setItemMeta(grappling_hook_meta);
                                    target.getInventory().addItem(grappling_hook);
                                    p.sendMessage(utils.chat("&aGave " + target.getName() + " &8x1 &eGRAPPLING_HOOK&a."));
                                } else {
                                    p.sendMessage(utils.chat("&cPlayer not found."));
                                }
                            }
                        }
                        if (s.equalsIgnoreCase("grapplinghook") || s.equalsIgnoreCase("gh")) {
                            if (p.hasPermission("grapplinghook.admin")) {
                                if (args.length == 0) {
                                    p.sendMessage(utils.chat("&d&m------------------------------------------------"));
                                    p.sendMessage(utils.chat(""));
                                    p.sendMessage(utils.chat("&a&lHypixel Skyblock Grappling Hook:"));
                                    p.sendMessage(utils.chat("&eA simple plugin made by &cDeemu&e!"));
                                    p.sendMessage(utils.chat("&aCommands:"));
                                    p.sendMessage(utils.chat("&d/grapplinghook help &7- Shows this page."));
                                    p.sendMessage(utils.chat("&d/grapplinghook give <player> &7- Give the grappling hook item to a player."));
                                    p.sendMessage(utils.chat("&d/grappling hook get &7- Get the grappling hook item for yourself."));
                                    p.sendMessage(utils.chat("&d/grapplinghook reload &7- Reload the plugin config."));
                                    p.sendMessage("");
                                    p.spigot().sendMessage(discord);
                                    p.sendMessage("");
                                    p.sendMessage(utils.chat("&8Version 1.4 by Deemu"));
                                    p.sendMessage(utils.chat("&d&m------------------------------------------------"));
                                    return true;
                                }
                                if (args[0].equalsIgnoreCase("help")) {
                                    p.sendMessage(utils.chat("&d&m------------------------------------------------"));
                                    p.sendMessage(utils.chat(""));
                                    p.sendMessage(utils.chat("&a&lHypixel Skyblock Grappling Hook:"));
                                    p.sendMessage(utils.chat("&eA simple plugin made by &cDeemu&e!"));
                                    p.sendMessage(utils.chat("&aCommands:"));
                                    p.sendMessage(utils.chat("&d/grapplinghook help &7- Shows this page."));
                                    p.sendMessage(utils.chat("&d/grapplinghook give <player> &7- Give the grappling hook item to a player."));
                                    p.sendMessage(utils.chat("&d/grappling hook get &7- Get the grappling hook item for yourself."));
                                    p.sendMessage(utils.chat("&d/grapplinghook reload &7- Reload the plugin config."));
                                    p.sendMessage("");
                                    p.spigot().sendMessage(discord);
                                    p.sendMessage("");
                                    p.sendMessage(utils.chat("&8Version 1.4 by Deemu"));
                                    p.sendMessage(utils.chat("&d&m------------------------------------------------"));
                                }
                                if (args[0].equalsIgnoreCase("give")) {
                                    if (args[1] != null) {
                                        Player target = Bukkit.getPlayer(args[1]);
                                        if (target != null && target.isOnline()) {
                                            ItemStack grappling_hook = new ItemStack(Material.FISHING_ROD);
                                            ItemMeta grappling_hook_meta = grappling_hook.getItemMeta();
                                            grappling_hook_meta.setDisplayName(utils.chat(plugin.getConfig().getString("grapplinghook.displayname")));
                                            List<String> grappling_hook_lore = new ArrayList<String>();
                                            if (plugin.getConfig().getBoolean("grapplinghook.lore-enabled") == true) {
                                                grappling_hook_lore.add(ChatColor.GRAY + "Travel in style using");
                                                grappling_hook_lore.add(ChatColor.GRAY + "this Grappling Hook.");
                                                grappling_hook_lore.add("");
                                                grappling_hook_lore.add(ChatColor.GREEN + "" + ChatColor.BOLD + "UNCOMMON");
                                            }
                                            if (plugin.getConfig().getBoolean("grapplinghook.shiny") == true) {
                                                grappling_hook_meta.addEnchant(Enchantment.LUCK, 1, false);
                                            }
                                            if (plugin.getConfig().getBoolean("grapplinghook.unbreakable") == true) {
                                                grappling_hook_meta.setUnbreakable(true);
                                            }
                                            grappling_hook_meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                                            grappling_hook_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                                            grappling_hook_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                                            grappling_hook_meta.setLore(grappling_hook_lore);
                                            grappling_hook.setItemMeta(grappling_hook_meta);
                                            target.getInventory().addItem(grappling_hook);
                                            p.sendMessage(utils.chat("&aGave " + target.getName() + " &8x1 &eGRAPPLING_HOOK&a."));
                                        } else {
                                            p.sendMessage(utils.chat("&cPlayer not found."));
                                        }
                                    }
                                }
                                if (args[0].equalsIgnoreCase("get")) {
                                    ItemStack grappling_hook = new ItemStack(Material.FISHING_ROD);
                                    ItemMeta grappling_hook_meta = grappling_hook.getItemMeta();
                                    grappling_hook_meta.setDisplayName(utils.chat(plugin.getConfig().getString("grapplinghook.displayname")));
                                    List<String> grappling_hook_lore = new ArrayList<String>();
                                    if (plugin.getConfig().getBoolean("grapplinghook.lore-enabled") == true) {
                                        grappling_hook_lore.add(ChatColor.GRAY + "Travel in style using");
                                        grappling_hook_lore.add(ChatColor.GRAY + "this Grappling Hook.");
                                        grappling_hook_lore.add("");
                                        grappling_hook_lore.add(ChatColor.GREEN + "" + ChatColor.BOLD + "UNCOMMON");
                                    }
                                    if (plugin.getConfig().getBoolean("grapplinghook.shiny") == true) {
                                        grappling_hook_meta.addEnchant(Enchantment.LUCK, 1, false);
                                    }
                                    if (plugin.getConfig().getBoolean("grapplinghook.unbreakable") == true) {
                                        grappling_hook_meta.setUnbreakable(true);
                                    }
                                    grappling_hook_meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                                    grappling_hook_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                                    grappling_hook_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                                    grappling_hook_meta.setLore(grappling_hook_lore);
                                    grappling_hook.setItemMeta(grappling_hook_meta);
                                    p.getInventory().addItem(grappling_hook);
                                    p.sendMessage(utils.chat("&aGave " + p.getName() + " &8x1 &eGRAPPLING_HOOK&a."));
                                }
                                if (args[0].equalsIgnoreCase("reload")) {
                                    plugin.reloadConfig();
                                    p.sendMessage(ChatColor.GREEN + "Config file successfully reloaded.");
                                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2F, 2);
                                }

                            } else {
                                p.sendMessage(utils.chat(plugin.getConfig().getString("messages.no-permission")));
                            }
                        }
                    return true;
                }

}
