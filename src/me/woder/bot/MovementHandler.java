package me.woder.bot;

import java.io.IOException;
import java.util.ArrayList;

import me.woder.world.Location;

import com.adamki11s.pathing.AStar;
import com.adamki11s.pathing.AStar.InvalidPathException;
import com.adamki11s.pathing.PathingResult;
import com.adamki11s.pathing.Tile;


public class MovementHandler {
    private Client c;
    
    public MovementHandler(Client c){
        this.c = c;
    }

    public void runPathing(final Location start, final Location end, final int range){
        try {
            //create our pathfinder
            AStar path = new AStar(start, end, range);
            //get the list of nodes to walk to as a Tile object
            ArrayList<Tile> route = path.iterate();
            //get the result of the path trace
            PathingResult result = path.getPathingResult();
     
            switch(result){
            case SUCCESS : 
                //Path was successfull. Do something here.
                moveAlong(start, route);
                c.chat.sendMessage("Path was found! :D");
                break;
            case NO_PATH :
                //No path found, throw error.
                System.out.println("No path found!");
                c.chat.sendMessage("No path was found :(");
                break;
             default:
                c.chat.sendMessage("Well... appearently we didn't find a path and we did... at the same time");
                break;
            }
        } catch (InvalidPathException e) {
            //InvalidPathException will be thrown if start or end block is air
            if(e.isStartNotSolid()){
                System.out.println("End block is not walkable");
            }
            if(e.isStartNotSolid()){
                System.out.println("Start block is not walkable");
            }
        }
    }
     
    private void moveAlong(Location start, ArrayList<Tile> tiles){
        for(Tile t : tiles){
            Location loc = t.getLocation(start);
            calcMovement(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY()+1, loc.getBlockZ()));
            c.chat.sendMessage("Current block pos: " + t.getLocation(start).getBlockX() + ", " + t.getLocation(start).getBlockY() + ", " + t.getLocation(start).getBlockZ());
        }
    }
    
    public void applyGravity(){
        int id = c.whandle.getWorld().getBlock(c.location).getRelative(0, -2, 0).getTypeId();
        if(canBlockBeWalkedThrough(id)){
            Location down = getCenter(c.location.getX(), c.location.getY()-2, c.location.getZ());
            sendMovement(down);
        }
    }
    
    public Location getCenter(double x, double y, double z){
        double rx;
        double rz;
        rx = Math.floor(x)+0.5;
        rz = Math.floor(z)+0.5;
        System.out.println("Center is: " + rx + " " + rz);
        return new Location(c.whandle.getWorld(),rx,y,rz);
    }
    
    public boolean calcMovement(Location l){
        boolean canGo = false;
        //checks that the place we are going to is safe
        if(canBlockBeWalkedThrough(l.getBlock().getRelative(0, 1, 0).getTypeId())){//start by checking if we can go there
            //now check if the head is safe
            if(canBlockBeWalkedThrough(l.getBlock().getRelative(0, 2, 0).getTypeId())){
                //yay it seems clear, so now we can go there
                sendMovement(l);
                canGo = true;
            }
        }//if we can't go there then we will return false
        return canGo;
    }
    
    private boolean canBlockBeWalkedThrough(int id) {
        return (id == 0 || id == 6 || id == 50 || id == 63 || id == 30 || id == 31 || id == 32 || id == 37 || id == 38 || id == 39 || id == 40 || id == 55 || id == 66 || id == 75
                || id == 76 || id == 78);
    }
    
    public void sendMovement(Location l){
        c.location = l;
        c.chat.sendMessage("Attempting to move to location: " + l.getX() + "," + l.getY() + "," + l.getZ());
        try {  
            c.out.writeByte(0x0B);
            c.out.writeDouble(l.getX());
            c.out.writeDouble(l.getY());
            System.out.println("Y = " + l.getY());
            c.stance = l.getBlockY()+1.2;
            c.out.writeDouble(l.getY()+1.62);
            c.out.writeDouble(l.getZ());
            c.out.writeBoolean(true);
            c.out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}