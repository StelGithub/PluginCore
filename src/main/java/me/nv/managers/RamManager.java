package me.nv.managers;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.UUID;

@Getter
@Setter
public class RamManager {

    public HashMap<UUID, UUID> privateMessages = new HashMap<>();

}
