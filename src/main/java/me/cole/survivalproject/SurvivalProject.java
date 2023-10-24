
package me.cole.survivalproject;

import me.cole.survivalproject.commands.admin.FlyCommand;
import me.cole.survivalproject.commands.admin.GamemodeCommand;
import me.cole.survivalproject.commands.admin.SetSpawnCommand;
import me.cole.survivalproject.commands.admin.TeleportCommand;
import me.cole.survivalproject.commands.ranks.RankListener;
import me.cole.survivalproject.commands.ranks.commands.RankCommand;
import me.cole.survivalproject.commands.user.SpawnCommand;
import me.cole.survivalproject.listeners.ServerListener;
import me.cole.survivalproject.listeners.WorldListener;
import me.cole.survivalproject.profile.ProfileManager;
import me.cole.survivalproject.utils.world.WorldHandler;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class SurvivalProject extends JavaPlugin {
    public static SurvivalProject instance;
    public ProfileManager profileManager;
    public WorldHandler worldHandler;
    public Mongo mongo;

    @Override
    public void onEnable() {
        instance = this;

        // Config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // Mongo
        mongo = new Mongo();
        mongo.createConnection();

        // Start-up logic
        profileManager = new ProfileManager();
        worldHandler = new WorldHandler();

        // Commands
        Map<String, CommandExecutor> commands = new HashMap<>();

        commands.put("rank", new RankCommand(profileManager));
        commands.put("gamemode", new GamemodeCommand());
        commands.put("teleport", new TeleportCommand(this));
        commands.put("spawn", new SpawnCommand());
        commands.put("fly", new FlyCommand());
        commands.put("setspawn", new SetSpawnCommand());
        commands.forEach((s, commandExecutor) -> {
            Objects.requireNonNull(getCommand(s)).setExecutor(commandExecutor);
        });

        // Listeners
        List<Listener> listeners = new ArrayList<>();

        listeners.add(new ServerListener());
        listeners.add(new WorldListener());
        listeners.add(new RankListener(profileManager));
        listeners.forEach((listener) -> {
            Bukkit.getPluginManager().registerEvents(listener, this);
        });

    }
}
