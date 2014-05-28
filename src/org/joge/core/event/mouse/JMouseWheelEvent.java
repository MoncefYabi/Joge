package org.joge.core.event.mouse;


public class JMouseWheelEvent extends JMouseEvent
{
	private boolean up;
	private int rotation;
	private int dX;
	private int dY;
	
	public JMouseWheelEvent(int x,int y,int rotation,boolean up)
	{
		this.rotation=rotation;
		this.up=up;
		dX = x;
		dY = y;
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

	/**
	 * @return the up
	 */
	public boolean isUp()
	{
		return up;
	}

	/**
	 * @return the rotation
	 */
	public int getRotation()
	{
		return rotation;
	}

}
