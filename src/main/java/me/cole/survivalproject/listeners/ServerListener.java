package me.cole.survivalproject.listeners;

import me.cole.survivalproject.utils.CC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListener implements Listener {
    @EventHandler
    public void ServerPing(ServerListPingEvent event) {
        event.setMotd(CC.translate("&5Minecraft Survival Server"));
        event.setMaxPlayers(50);
    }
}
