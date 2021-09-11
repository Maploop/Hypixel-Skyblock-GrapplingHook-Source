package net.maploop.commands;

import net.maploop.GrapplingHook;
import net.maploop.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ConsoleCommand implements CommandExecutor {
    private final GrapplingHook plugin = GrapplingHook.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (s.equalsIgnoreCase("ghconsolegive")) {
            if (!(sender instanceof Player)) {
                if (args.length == 0) {
                    sender.sendMessage(Util.chat("&cPlease specify a player."));
                } else if (args[0] != null) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null && target.isOnline()) {
                        ItemStack grappling_hook = new ItemStack(Material.FISHING_ROD);
                        ItemMeta grappling_hook_meta = grappling_hook.getItemMeta();
                        grappling_hook_meta.setDisplayName(Util.chat(plugin.getConfig().getString("grapplinghook.displayname")));
                        List<String> grappling_hook_lore = new ArrayList<String>();
                        if (plugin.getConfig().getBoolean("grapplinghook.lore-enabled")) {
                            for (String agrappling_hook_lore : plugin.getConfig().getStringList("grapplinghook.lore")) {
                                grappling_hook_lore.add(Util.chat(agrappling_hook_lore));
                            }
                        }
                        if (plugin.getConfig().getBoolean("grapplinghook.shiny")) {
                            grappling_hook_meta.addEnchant(Enchantment.LUCK, 1, false);
                        }
                        if (plugin.getConfig().getBoolean("grapplinghook.unbreakable")) {
                            grappling_hook_meta.setUnbreakable(true);
                        }
                        grappling_hook_meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                        grappling_hook_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        grappling_hook_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                        grappling_hook_meta.setLore(grappling_hook_lore);
                        grappling_hook.setItemMeta(grappling_hook_meta);
                        target.getInventory().addItem(grappling_hook);
                        sender.sendMessage(Util.chat("&aGave " + target.getName() + " &8x1 &eGRAPPLING_HOOK&a."));
                    } else {
                        sender.sendMessage(Util.chat("&cPlayer not found."));
                    }
                }
            }


        }
        return true;
    }
}
