package net.maploop.listener;

import net.maploop.GrapplingHook;
import net.maploop.Util;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import static net.maploop.GrapplingHook.FLYING_TIMEOUT;

import java.util.HashMap;

public class PlayerFish implements Listener {
    private final GrapplingHook plugin = GrapplingHook.getInstance();

    private final HashMap<String, Long> cooldown = new HashMap<String, Long>();
    private final int cooldowntime = plugin.getConfig().getInt("cooldown-amount");
    private final int flyingTimeout = plugin.getConfig().getInt("cancellation-timeout");

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRightClick(PlayerFishEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getItemInHand();
        if (e.getState() == PlayerFishEvent.State.REEL_IN || e.getState() == PlayerFishEvent.State.IN_GROUND) {
            if (item.getItemMeta().getItemFlags().contains(ItemFlag.HIDE_ATTRIBUTES)) {
                if (plugin.getConfig().getBoolean("cooldown-enabled")) {
                    // adding cooldown
                    if (cooldown.containsKey(p.getName())) {
                        // if player is inside our hashmap ::
                        if (cooldown.get(p.getName()) > System.currentTimeMillis()) {
                            // they still have time in cooldown
                            p.sendMessage(Util.chat(plugin.getConfig().getString("messages.on-cooldown")));
                            if (plugin.getConfig().getBoolean("grapplinghook.error-sound-enabled"))
                                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 2F, 0);
                            return;
                        }
                    }
                    cooldown.put(p.getName(), System.currentTimeMillis() + (cooldowntime * 1000L));
                    Location loc1 = p.getLocation();
                    Location loc2 = e.getHook().getLocation();

                    Vector v = new Vector(loc2.getX() - loc1.getX(), 1, loc2.getZ() - loc1.getZ());
                    p.setVelocity(v);

                } else if (!plugin.getConfig().getBoolean("cooldown-enabled")) {
                    Location loc1 = p.getLocation();
                    Location loc2 = e.getHook().getLocation();

                    Vector v = new Vector(loc2.getX() - loc1.getX(), 1, loc2.getZ() - loc1.getZ());
                    p.setVelocity(v);
                }
                if (plugin.getConfig().getBoolean("cancel-damage"))
                    FLYING_TIMEOUT.put(p.getUniqueId(), System.currentTimeMillis() + (flyingTimeout * 1000L));
            }


        }
    }
}
