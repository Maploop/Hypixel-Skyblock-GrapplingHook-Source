package net.maploop;

import net.maploop.commands.MainCommand;
import net.maploop.listener.EntityDamage;
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

public class GrapplingHook extends JavaPlugin implements Listener
{
	public static Map<UUID, Long> FLYING_TIMEOUT = new HashMap<UUID, Long>();

	private final HashMap<String, Long> cooldown = new HashMap<String, Long>();
	private final int cooldowntime = getConfig().getInt("cooldown-amount");
	private final int flyingTimeout = getConfig().getInt("cancellation-timeout");

	@Override
	public void onEnable()
	{
		saveDefaultConfig();
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getPluginManager().registerEvents(new EntityDamage(), this);
		getCommand("grapplinghook").setExecutor(new MainCommand(this));
		getCommand("gh").setExecutor(new MainCommand(this));
		getCommand("gh").setTabCompleter(new Util());
		getCommand("grapplinghook").setTabCompleter(new Util());
		ConsoleCommandSender console = Bukkit.getConsoleSender();
		console.sendMessage(Util.chat("&eGrappling Hook&a Plugin loaded successfully! &eVersion 1.2"));
	}

	@Override
	public void onDisable()
	{
		ConsoleCommandSender console = Bukkit.getConsoleSender();
		console.sendMessage(Util.chat("&eGrappling Hook&c Plugin was disabled."));
	}


	@EventHandler()
	public void onRightClick(PlayerFishEvent e)
	{
		Player p = e.getPlayer();
		ItemStack item = p.getItemInHand();
		if (e.getState() == PlayerFishEvent.State.REEL_IN || e.getState() == PlayerFishEvent.State.IN_GROUND)
		{
			if (item.getItemMeta().getItemFlags().contains(ItemFlag.HIDE_ATTRIBUTES))
			{
				if (getConfig().getBoolean("cooldown-enabled"))
				{
					// adding cooldown
					if (cooldown.containsKey(p.getName()))
					{
						// if player is inside our hashmap ::
						if (cooldown.get(p.getName()) > System.currentTimeMillis())
						{
							// they still have time in cooldown
							p.sendMessage(Util.chat(getConfig().getString("messages.on-cooldown")));
							if (getConfig().getBoolean("grapplinghook.error-sound-enabled"))
								p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 2F, 0);
							return;
						}
					}
					cooldown.put(p.getName(), System.currentTimeMillis() + (cooldowntime * 1000L));
					Location loc1 = p.getLocation();
					Location loc2 = e.getHook().getLocation();

					Vector v = new Vector(loc2.getX() - loc1.getX(), 1, loc2.getZ() - loc1.getZ());
					p.setVelocity(v);

				}
				else if (!getConfig().getBoolean("cooldown-enabled"))
				{
					Location loc1 = p.getLocation();
					Location loc2 = e.getHook().getLocation();

					Vector v = new Vector(loc2.getX() - loc1.getX(), 1, loc2.getZ() - loc1.getZ());
					p.setVelocity(v);
				}
				if (getConfig().getBoolean("cancel-damage"))
					FLYING_TIMEOUT.put(p.getUniqueId(), System.currentTimeMillis() + (flyingTimeout * 1000L));
			}


		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args)
	{
		if (s.equalsIgnoreCase("ghconsolegive"))
		{
			if (!(sender instanceof Player))
			{
				if (args.length == 0)
				{
					sender.sendMessage(Util.chat("&cPlease specify a player."));
				}
				else if (args[0] != null)
				{
					Player target = Bukkit.getPlayer(args[0]);
					if (target != null && target.isOnline())
					{
						ItemStack grappling_hook = new ItemStack(Material.FISHING_ROD);
						ItemMeta grappling_hook_meta = grappling_hook.getItemMeta();
						grappling_hook_meta.setDisplayName(Util.chat(getConfig().getString("grapplinghook.displayname")));
						List<String> grappling_hook_lore = new ArrayList<String>();
						if (getConfig().getBoolean("grapplinghook.lore-enabled"))
						{
							for (String agrappling_hook_lore : getConfig().getStringList("grapplinghook.lore"))
							{
								grappling_hook_lore.add(Util.chat(agrappling_hook_lore));
							}
						}
						if (getConfig().getBoolean("grapplinghook.shiny"))
						{
							grappling_hook_meta.addEnchant(Enchantment.LUCK, 1, false);
						}
						if (getConfig().getBoolean("grapplinghook.unbreakable"))
						{
							grappling_hook_meta.setUnbreakable(true);
						}
						grappling_hook_meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
						grappling_hook_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
						grappling_hook_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						grappling_hook_meta.setLore(grappling_hook_lore);
						grappling_hook.setItemMeta(grappling_hook_meta);
						target.getInventory().addItem(grappling_hook);
						sender.sendMessage(Util.chat("&aGave " + target.getName() + " &8x1 &eGRAPPLING_HOOK&a."));
					}
					else
					{
						sender.sendMessage(Util.chat("&cPlayer not found."));
					}
				}
			}


		}
		return true;
	}
}