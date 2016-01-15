/**
 * Models a pawn object for placing on the board ingame. 
 * 
 * @author Jordan Bird
 * @version 2/11/2015
 */
public abstract class Pawn
{
    public Position position;
    
    /**
     * Constructor for objects of class Pawn
     * 
     * @Param posX      the starting X position of the pawn
     * @Param posY      the starting Y position of the pawn
     * @Param walls     the amount of walls the pawn has
     * 
     */
    public Pawn(int posX, int posY)
    {

        position = new Position(posX, posY);
    }
    
     /**
     * Gets the name of the player
     * 
     * @return name the String representing pawn name
     */
    public String getName()
    {
        return name;
    }
    
     
    /**
     * Sets the X position of the Pawn
     * 
     * @param  set   the change to be made to the X co-ordinate
     */
    public void setX(int set)
    {
        position.setX(set);
    }
    
     /**
     * Sets the Y position of the Pawn
     * 
     * @param  set   the change to be made to the Y co-ordinate
     */
    public void setY(int set)
    {
        position.setY(set);
    }
    
         /**
     * Gets the position of the Pawn
     * 
     * @return  position   an Array containing the position
     */
    public int[] getPosition()
    {
        return position.getCoords();
    }
    
     /**
     * Gets the X position of the Pawn
     * 
     * @return  posX   the numeric representation of the Pawn's X co-ordinate
     */
    public int getX()
    {
        return position.getX();
    }
    
     /**
     * Gets the Y position of the Pawn
     * 
     * @return  posY   the numeric representation of the Pawn's Y co-ordinate
     */
    public int getY()
    {
        return position.getY();
    }
    
     
    
   
}
