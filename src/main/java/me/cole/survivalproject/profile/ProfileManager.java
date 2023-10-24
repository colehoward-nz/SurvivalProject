package me.cole.survivalproject.profile;

import com.google.common.math.Stats;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import me.cole.survivalproject.SurvivalProject;
import me.cole.survivalproject.utils.CC;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class ProfileManager implements Listener {
    private final Map<UUID, StatsProfile> stats_map = new HashMap<>();
    private final MongoCollection<Document> stats_collection = SurvivalProject.instance.mongo.database.getCollection("stats");



    public void saveStats(StatsProfile user) {
        try {
            stats_collection.replaceOne(Filters.eq("_id", user.getUuid().toString()), user.toBson(), new ReplaceOptions().upsert(true));
        } catch (NullPointerException ignored){}
    }


    public StatsProfile getStats(UUID uuid) {
        if (this.stats_map.containsKey(uuid)) {
            return this.stats_map.get(uuid);
        }

        Document document = null;

        try {
            document = stats_collection.find(Filters.eq("_id", uuid.toString())).first();
        } catch (NullPointerException ignored) {}

        if (document != null) {
            return new StatsProfile(document);
        }

        return new StatsProfile(uuid);
    }



    public double getTargetLevelExp(int level) {
        return Math.ceil(Math.pow((level/0.7), 2));
    }

    public boolean canLevel(Player player) {
        StatsProfile profile = getStats(player.getUniqueId());
        int level = profile.getLevel();
        double exp = profile.getExp();
        if (exp > getTargetLevelExp(level)-1) {
            return true;
        }
        return false;
    }

    public void levelUp(Player player) {
        StatsProfile profile = getStats(player.getUniqueId());
        int level = profile.getLevel();
        double exp = profile.getExp();
        if (canLevel(player)) {
            level += 1;
            profile.setLevel(level);
            profile.setExp(Math.ceil(exp % getTargetLevelExp(level)));

            player.sendMessage(CC.translate("&eCongratulations! You have advanced to &bLevel " + level));

            if (level % 5 == 0) {
                int bonus = (int) Math.floor(Math.random() *(300 - 100 + 100) + 300);
                profile.setPoints(profile.getPoints()+bonus);
                saveStats(profile);

                player.sendMessage(CC.translate("\n&eYou have received a &bBONUS REWARD &eof &2$&a" + bonus));
            }
            saveStats(profile);
        }
    }

    public String getColouredLevel(Player player) {
        String colouredLevel = "";
        StatsProfile profile = getStats(player.getUniqueId());
        int level = profile.getLevel();

        if (0 <= level && level <= 4) {
            colouredLevel = "" + ChatColor.DARK_GRAY + level;
        } else if (5 <= level && level <= 9) {
            colouredLevel = "" + ChatColor.GRAY + level;
        } else if (10 <= level && level <= 14) {
            colouredLevel = "" + ChatColor.DARK_GREEN + level;
        } else if (15 <= level && level <= 19) {
            colouredLevel = "" + ChatColor.GREEN + level;
        } else if (20 <= level && level <= 24) {
            colouredLevel = "" + ChatColor.YELLOW + level;
        } else if (25 <= level && level <= 29) {
            colouredLevel = "" + ChatColor.GOLD + level;
        } else if (30 <= level && level <= 34) {
            colouredLevel = "" + ChatColor.RED + level;
        } else if (35 <= level && level <= 39) {
            colouredLevel = "" + ChatColor.RED + ChatColor.BOLD + level;
        } else {
            colouredLevel = "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + level;
        }
        return colouredLevel;
    }





    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent event) {
        System.out.println("ya herrrd?");
        StatsProfile stats_user = getStats(event.getUniqueId());
        this.stats_map.put(stats_user.getUuid(), stats_user);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        System.out.println("what the fucj uis in my sandmich?");
        StatsProfile stats_user = this.stats_map.remove(event.getPlayer().getUniqueId());
        saveStats(stats_user);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.getEntity();
        if (event.getEntity().getKiller() != null) {
            Player player = event.getEntity();
            StatsProfile playerStats = getStats(player.getUniqueId());

            Player killer = player.getKiller();
            StatsProfile killerStats = getStats(killer.getUniqueId());

            int random;
            try {
                random = Integer.parseInt(String.valueOf(Math.floor(Math.random() * (95 - 20 + 1) + 20)));
            } catch (ClassCastException exception) {
                random = (int) Math.floor(Math.random() * (95 - 20 + 1) + 20);
            }

            int randomBy3;
            try {
                randomBy3 = Integer.parseInt(String.valueOf(Math.floor((double) random / 3)));
            } catch (ClassCastException exception) {
                randomBy3 = (int) (Math.floor((double) random / 3));
            }


            player.sendMessage(CC.translate("&cYou lost $" + randomBy3));
            killer.sendMessage(CC.translate("&eYou have gained &2$&a" + random + " &eand &b" + randomBy3 + "XP"));

            // Increase and reset stats accordingly
            killerStats.setExp(killerStats.getExp() + (randomBy3));
            killerStats.setPoints(killerStats.getPoints() + random);
            playerStats.setPoints(playerStats.getPoints() - (randomBy3));
            killerStats.setKills(killerStats.getKills() + 1);
            playerStats.setDeaths(playerStats.getDeaths() + 1);

            // Save player stats to mongo
            saveStats(killerStats);
            saveStats(playerStats);

            levelUp(killer);
        }
    }
}
