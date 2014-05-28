package org.joge.core.event.mouse;



public class JMousePressedEvent extends JMouseEvent
{
	private int dX, dY;
	
	public JMousePressedEvent(int x,int y,int button)
	{
		super();
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
