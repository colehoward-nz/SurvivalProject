package me.cole.survivalproject.utils.world;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

@Getter
@Setter
@AllArgsConstructor
public class Cuboid {

    private int minX;
    private int minY;
    private int minZ;
    private int maxX;
    private int maxY;
    private int maxZ;

    public Cuboid(Location min, Location max) {
        this.minX = min.getBlockX();
        this.minY = min.getBlockY();
        this.minZ = min.getBlockZ();
        this.maxX = max.getBlockX();
        this.maxY = max.getBlockY();
        this.maxZ = max.getBlockZ();
    }

    public Cuboid(Location location, int radius) {
        this(
                location.getBlockX() - radius,
                location.getBlockY() - radius,
                location.getBlockZ() - radius,
                location.getBlockX() + radius,
                location.getBlockY() + radius,
                location.getBlockZ() + radius
        );
    }

    public boolean contains(Location location) {
        return location.getBlockX() >= minX &&
                location.getBlockY() >= minY &&
                location.getBlockZ() >= minZ &&
                location.getBlockX() <= maxX &&
                location.getBlockY() <= maxY &&
                location.getBlockZ() <= maxZ;
    }

}