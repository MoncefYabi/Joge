package org.joge.core.event.mouse;

public class JMouseMovedEvent extends JMouseEvent
{
	private int dX, dY;
	
	public JMouseMovedEvent( int x, int y)
	{
		this.dX=x;
		this.dY=y;
	}

	public int getButton()
	{
		return -1;
	}

	public int getDX()
	{
		return dX;
	}

	public int getDY()
	{
		return dY;
	}

}
