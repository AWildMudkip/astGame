package asteroidGame;

import java.awt.*;
public class BlueSpawn
{
	double x,y;
	double radius;
	public BlueSpawn()
	{
		x=400;
		y=100;
		radius=40;
	}
	public void draw(Graphics g)
	{
 	g.setColor(Color.BLUE); // set color for the spawn
 	// draw the asteroid centered at (x,y)
 	g.drawOval((int)(x-radius+.5),(int)(y-radius+.5),
 	(int)(2*radius),(int)(2*radius));
   }	
   public boolean shipBlue(Ship ship){
   
   
   	{
 // Use the distance formula to check if the ship is touching 


 if(Math.pow(radius+ship.getRadius(),2) >Math.pow(ship.getX()-x,2) + Math.pow(ship.getY()-y,2)
&& ship.isActive())
 	return true;
 return false;
   }
   }
}