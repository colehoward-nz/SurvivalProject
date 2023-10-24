package me.cole.survivalproject.commands.ranks;

import me.cole.survivalproject.profile.ProfileManager;
import me.cole.survivalproject.profile.StatsProfile;
import me.cole.survivalproject.utils.CC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RankListener implements Listener {
    public static RankListener instance;
    private final ProfileManager profileManager;
    private List<Rank> ranks = new ArrayList<>(
            Arrays.asList(Rank.OP, Rank.DEFAULT)
    );

    public RankListener(ProfileManager profileManager){
        instance = this;
        this.profileManager = profileManager;
    }

    public boolean exists(String rank){
        for (Rank check : ranks) {
            if (rank.equalsIgnoreCase(check.name())) return true;
        }
        return false;
    }

    public Rank getRank(String rank) {
        for (Rank check : ranks) {
            if (rank.equalsIgnoreCase(check.name())) return check;
        }
        return Rank.DEFAULT;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        StatsProfile rankProfile = profileManager.getStats(event.getPlayer().getUniqueId());
        String getRank = rankProfile.getRank();

        if (!exists(getRank)){
            getRank = "Default";
        }

        Rank rank = getRank(getRank);
        String prefix = rank.getPrefix();
        String player = event.getPlayer().getDisplayName();


        String newFormat = CC.translate("&7[") + profileManager.getColouredLevel(event.getPlayer()) + CC.translate("&l✩") + CC.translate("&7] ") + CC.translate(prefix) + rank.getColour() + player + CC.translate("&7: ");
        if (event.getPlayer().hasPermission("core.chat.colour")) {
            event.setFormat(newFormat + CC.translate(event.getMessage()));
        }
        else {
            event.setFormat(newFormat + event.getMessage());
        }

        event.getPlayer().setPlayerListName(CC.translate(prefix) + rank.getColour() + player);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        StatsProfile rankProfile = profileManager.getStats(event.getPlayer().getUniqueId());
        String getRank = rankProfile.getRank();
        Rank rank = getRank(getRank);
        String getPrefix = rank.getPrefix();
        String getColour = getRank(rankProfile.getRank()).getColour() + "";
        String format = CC.translate(getPrefix) + getColour + event.getPlayer().getDisplayName() + CC.translate(" &7has just &ajoined");
        event.setJoinMessage(format);

        event.getPlayer().setPlayerListName(CC.translate(getPrefix) + getColour + event.getPlayer().getDisplayName());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        StatsProfile rankProfile = profileManager.getStats(event.getPlayer().getUniqueId());
        String getRank = rankProfile.getRank();
        Rank rank = getRank(getRank);
        String getPrefix = rank.getPrefix();
        String getColour = rank.getColour() + "";
        String format = CC.translate(getPrefix) + getColour + event.getPlayer().getDisplayName() + CC.translate(" &7has just &cquit");
        event.setQuitMessage(format);
    }
}

