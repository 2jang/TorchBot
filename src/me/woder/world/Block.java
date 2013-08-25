package me.woder.world;

public class Block {
    private int id;
    private int x;
    private int y;
    private int z;
    private World world;
    
    public Block(World world, int x, int y, int z, int id){
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }
    
    public Integer getTypeId(){
        return id;
    }
    
    public World getWorld(){
        return world;
    }
    
    public Location getLocation(){
        return new Location(world,x,y,z);
    }
    
    public Integer getX(){
        return x;
    }
    
    public Integer getY(){
        return y;
    }
    
    public Integer getZ(){
        return z;
    }
    
    public Block getRelative(int bx, int by, int bz){
        System.out.println("Getting the block relative to: " + x + " ," + y + " ," + z + " looking at: " + (bx+x) + " , " + (y+by) +  " ," + (z+bz));
        Block b = world.getBlock(x+bx, y+by, z+bz);
        if(b == null){
            System.out.println("B is null, ending the world....");
            System.exit(0);
        }
        return b;
    }

}