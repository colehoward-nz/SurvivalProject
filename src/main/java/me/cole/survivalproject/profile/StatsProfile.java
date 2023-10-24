package me.cole.survivalproject.profile;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.text.DecimalFormat;
import java.util.UUID;

@Getter
@Setter
public class StatsProfile {
    private final UUID uuid;
    private String rank;
    private int kills;
    private int deaths;
    private int points;
    private int level;
    private double exp;

    public StatsProfile(Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.rank = document.getString("rank");
        this.kills = document.getInteger("kills");
        this.deaths = document.getInteger("deaths");
        this.points = document.getInteger("points");
        this.level = document.getInteger("level");
        this.exp = document.getDouble("exp");
    }

    public StatsProfile(UUID uuid) {
        this.uuid = uuid;
        this.rank = "User";
        this.kills = 0;
        this.deaths = 0;
        this.points = 0;
        this.level = 0;
        this.exp = 0.0;
    }

    public String getKDString() {
        return new DecimalFormat("##.#").format(getKD());
    }

    public double getKD() {
        return (double) kills / deaths;
    }

    public Document toBson() {
        return new Document("_id", this.uuid.toString())
                .append("rank", this.rank)
                .append("kills", this.kills)
                .append("deaths", this.deaths)
                .append("points", this.points)
                .append("level", this.level)
                .append("exp", this.exp);
    }
}
