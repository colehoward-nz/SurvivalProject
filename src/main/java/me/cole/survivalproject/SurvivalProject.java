package me.cole.survivalproject;

import me.cole.survivalproject.commands.ranks.RankListener;
import me.cole.survivalproject.commands.ranks.commands.RankCommand;
import me.cole.survivalproject.listeners.ServerListener;
import me.cole.survivalproject.profile.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class SurvivalProject extends JavaPlugin {
    public static SurvivalProject instance;
    public ProfileManager profileManager;
    public Mongo mongo;

    @Override
    public void onEnable() {
        instance = this;

        // Mongo
        mongo = new Mongo();
        mongo.createConnection();

        // Start-up logic
        profileManager = new ProfileManager();

        // Commands
        Map<String, CommandExecutor> commands = new HashMap<>();

        commands.put("rank", new RankCommand(profileManager));
        commands.forEach((s, commandExecutor) -> {
            Objects.requireNonNull(getCommand(s)).setExecutor(commandExecutor);
        });

        // Listeners
        List<Listener> listeners = new ArrayList<>();

        listeners.add(new ServerListener());
        listeners.add(new RankListener(profileManager));
        listeners.forEach((listener) -> {
            Bukkit.getPluginManager().registerEvents(listener, this);
        });

    }
}
