package me.cole.survivalproject.listeners;

import me.cole.survivalproject.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class WorldListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage(CC.translate("\n&dWOOOOAOOOOOOOWOAOOOOo MYYYYY MOOOMMMM\n &f"));
    }
}
