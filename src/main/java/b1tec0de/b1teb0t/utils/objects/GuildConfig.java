package b1tec0de.b1teb0t.utils.objects;

/**
 * GuildConfig Object.
 *
 * @author Kaufisch
 */

public class GuildConfig {

    private String guildId;
    private String prefix;
    private boolean warnSystem;
    private int warnSystemMaxWarns;
    private int warnSystemTimeoutLength;

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean isWarnSystem() {
        return warnSystem;
    }

    public void setWarnSystem(boolean warn_system) {
        this.warnSystem = warn_system;
    }

    public int getWarnSystemMaxWarns() {
        return warnSystemMaxWarns;
    }

    public void setWarnSystemMaxWarns(int warnSystemMaxWarns) {
        this.warnSystemMaxWarns = warnSystemMaxWarns;
    }

    public int getWarnSystemTimeoutLength() {
        return warnSystemTimeoutLength;
    }

    public void setWarnSystemTimeoutLength(int warnSystemTimeoutLength) {
        this.warnSystemTimeoutLength = warnSystemTimeoutLength;
    }
}
