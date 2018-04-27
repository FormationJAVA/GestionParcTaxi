package Geometry;

public class Point {
	
	public int x;
	public int y;
	
	public Point ()
	{
		this.x = 0;
		this.y=0;
	}

	public Point (int x, int y)
	{
		this.x = x;
		this.y=y;
	}
	public Point (Point p)
	{
		this.x = p.x;
		this.y=p.y;
	}
	public void add(Point P)
	{
		this.x+=P.x;
		this.y+=P.y;

	}
	public boolean isEqual(Point P)
	{
		return (this.x == P.x) &&(this.y==P.y);  
	}
	public double getDistance(Point P)
	{
		return Math.sqrt(Math.pow(this.x-P.x, 2) + Math.pow(this.y-P.y, 2));
	}
	public void setPoint (Point P)
	{
		this.x = P.x;
		this.y=P.y;
	}
	public String toString()
	{
		return "("+this.x+", "+ this.y   +")";
	}
}
