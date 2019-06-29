package b1tec0de.b1teb0t.utils.objects;

import java.util.ArrayList;

/**
 * GuildConfig Object.
 *
 * @author Kaufisch
 */

public class GuildConfig {

    private String guildId;
    private String prefix;
    private boolean warnSystem;
    private String warnSystemLog;
    private int warnSystemMaxWarns;
    private int warnSystemTimeoutLength;
    private ArrayList<String> supportChannel;
    private String supportChannelWaitingRoom;
    private String supportNews;
    private String supportRole;

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

    public ArrayList<String> getSupportChannel() {
        return supportChannel;
    }

    public void setSupportChannel(ArrayList<String> supportChannel) {
        this.supportChannel = supportChannel;
    }

    public String getSupportChannelWaitingRoom() {
        return supportChannelWaitingRoom;
    }

    public void setSupportChannelWaitingRoom(String supportChannelWaitingRoom) {
        this.supportChannelWaitingRoom = supportChannelWaitingRoom;
    }

    public String getSupportNews() {
        return supportNews;
    }

    public void setSupportNews(String supportNews) {
        this.supportNews = supportNews;
    }

    public String getSupportRole() {
        return supportRole;
    }

    public void setSupportRole(String supportRole) {
        this.supportRole = supportRole;
    }

    public String getWarnSystemLog() {
        return warnSystemLog;
    }

    public void setWarnSystemLog(String warnSystemLog) {
        this.warnSystemLog = warnSystemLog;
    }

    public boolean isWarnSystem() {
        return warnSystem;
    }

    public void setWarnSystem(boolean warnSystem) {
        this.warnSystem = warnSystem;
    }
}
