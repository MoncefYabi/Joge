package org.joge.core.event.mouse;



public class JMouseReleasedEvent extends JMouseEvent
{
	private int dX, dY;

	public JMouseReleasedEvent( int x, int y, int button)
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
