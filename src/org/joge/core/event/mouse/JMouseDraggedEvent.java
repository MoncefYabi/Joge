package org.joge.core.event.mouse;


public class JMouseDraggedEvent extends JMouseEvent
{
	private int dX, dY;
	
	protected JMouseDraggedEvent(int x,int y, int button)
	{
		this.button = button;
		dX = x;
		dY = y;
	}

	public int getButton()
	{
		return button;
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
