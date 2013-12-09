package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;
import me.woder.bot.Player;
import me.woder.event.Event;

public class SpawnPlayer12 extends Packet{
    public SpawnPlayer12(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        readVarInt(c.in);
        String uuid = getString(c.in);
        String playern = getString(c.in);
        //c.chat.sendMessage("Player " + ChatColor.stripColor(playername) + " spawned next to me");
        int xi = c.in.readInt();
        int yi = c.in.readInt();
        int zi = c.in.readInt();
        int x = xi / 32;
        int y = yi / 32;
        int z = zi / 32;
        c.in.readByte();
        c.in.readByte();
        short currentitem = c.in.readShort();
        new Player(c, playern, x, y, z);
        c.proc.readWatchableObjects(c.in);
        c.ehandle.handleEvent(new Event("onSpawnPlayer", new Object[] {playern, uuid, x, y, z, currentitem}));
    }

}