package me.cole.survivalproject.utils.world;

import lombok.Getter;
import me.cole.survivalproject.SurvivalProject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;

@Getter
public class WorldHandler implements Listener {

    private Cuboid spawnCuboid;
    private Location spawnLoc;

    public WorldHandler() {
        FileConfiguration config = SurvivalProject.instance.getConfig();
        try {
            this.spawnLoc = new Location(
                    Bukkit.getWorlds().get(0),
                    config.getInt("spawn.x"),
                    config.getInt("spawn.y"),
                    config.getInt("spawn.z"),
                    Float.parseFloat(config.getString("spawn.yaw")),
                    Float.parseFloat(config.getString("spawn.pitch"))
            );
            this.spawnCuboid = new Cuboid(this.spawnLoc, 20);
        } catch (Exception e) {
            this.spawnLoc = new Location(Bukkit.getWorlds().get(0), 0.0, 100.0, 0.0);
            this.spawnCuboid = new Cuboid(this.spawnLoc, 20);
        }

        Bukkit.getPluginManager().registerEvents(this, SurvivalProject.instance);
    }

    public Location getSpawn(){
        FileConfiguration config = SurvivalProject.instance.getConfig();
        Location spawn = null;
        try {
            spawn = new Location(
                    Bukkit.getWorlds().get(0),
                    config.getInt("spawn.x"),
                    config.getInt("spawn.y"),
                    config.getInt("spawn.z"),
                    Float.parseFloat(config.getString("spawn.yaw")),
                    Float.parseFloat(config.getString("spawn.pitch"))
            );
        } catch (Exception e) {
            spawn = new Location(Bukkit.getWorlds().get(0), 0.0, 100.0, 0.0);
        }
        return spawn;
    }
    public void setSpawn(Location location) {
        FileConfiguration config = SurvivalProject.instance.getConfig();

        config.set("spawn.x", location.getBlockX());
        config.set("spawn.y", location.getBlockY());
        config.set("spawn.z", location.getBlockZ());
        config.set("spawn.yaw", String.valueOf(location.getYaw()));
        config.set("spawn.pitch", String.valueOf(location.getPitch()));
        SurvivalProject.instance.saveConfig();

        this.spawnCuboid = new Cuboid(location, 29);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) { if (!event.getPlayer().hasPlayedBefore()){ event.getPlayer().teleport(this.spawnLoc); } }
}
