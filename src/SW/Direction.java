package SW;
public enum Direction
{
	up	      ( 0, 1 ),
	upRight   ( 1, 1 ),
	right     ( 1, 0 ),
	downRight ( 1,-1 ),
	down      ( 0,-1 ),
	downLeft  (-1,-1 ),
	left      (-1, 0 ),
	upLeft    (-1, 1 );
	
	public int x, y;
	
	Direction(int x, int y)
	{ 
		this.x = x;
		this.y = y;
	}
}
