package me.woder.world;

public class Location {
    private World world;
    private double x;
    private double y;
    private double z;
    
    
    public Location(World world2, int x, int y, int z) {
        this.world = world2;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Location(World world2, double x, double y, double z) {
        this.world = world2;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public boolean inRange(Location loc, double dist){
        double dx = x - loc.getX();
        double dy = y - loc.getY();
        double dz = z - loc.getZ();
        double result = Math.sqrt(dx*dx + dy*dy + dz*dz);
        if(result<dist)return true; 
        else return false;
    }
    
    /**
     * Translates a location by the given delta x, y and z values
     * @param dX delta X value
     * @param dY delta Y value
     * @param dZ delta Z value
     */
    public void translate(double dX, double dY, double dZ){
    	this.x += dX;
    	this.y += dY;
    	this.z += dZ;
    }
    
    public void setX(double x){
        this.x = x;
    }
    
    public void setY(double y){
        this.y = y;       
    }
    
    public void setZ(double z){
        this.z = z;
    }

    public World getWorld(){
        return this.world;
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public double getZ(){
        return z;
    }
    
    public Integer getBlockX(){
        return (int) Math.floor(x);
    }
    
    public Integer getBlockY(){
        return (int) Math.floor(y);
    }
    
    public Integer getBlockZ(){
        return (int) Math.floor(z);
    }
    
    public Block getBlock(){
        return world.getBlock((int)Math.floor(x), (int)Math.floor(y), (int)Math.floor(z));
    }
    
    @Override
    public String toString(){
    	return "[" + world.getDimension() + ": (" + x + ", " + y + ", " + z + ")]";
    }

}
