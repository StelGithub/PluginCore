package me.nv.playerData;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class PlayerData {
    private final UUID playerUUID;

    private String name;
    private String displayName;
    private String lastOnline;
    private String firstJoinDate;

    private boolean canBeMessaged; // unused

    public PlayerData(UUID playerUUID) {
        this.playerUUID = playerUUID;

        this.canBeMessaged = true;
        this.lastOnline = null;
        this.firstJoinDate = null;
    }
}
