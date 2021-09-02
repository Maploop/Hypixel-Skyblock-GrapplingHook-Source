package net.maploop;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class Util implements TabCompleter
{
	public static String chat(String s)
	{
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	List<String> arguments = new ArrayList<String>();

	public List<String> onTabComplete(CommandSender sender, Command c, String s, String[] args)
	{
		if (arguments.isEmpty())
		{
			if (sender.hasPermission("grapplinghook.admin"))
			{
				arguments.add("help");
				arguments.add("give");
				arguments.add("get");
				arguments.add("reload");
			}
		}
		List<String> result = new ArrayList<String>();
		if (args.length == 1)
		{
			for (String a : arguments)
			{
				if (a.toLowerCase().startsWith(args[0].toLowerCase())) result.add(a);
			}
			return result;
		}

		return null;
	}
}
