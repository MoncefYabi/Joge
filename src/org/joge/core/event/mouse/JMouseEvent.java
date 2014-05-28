package org.joge.core.event.mouse;

import org.joge.core.event.JEvent;

public abstract class JMouseEvent extends JEvent
{
	protected int button=-1;
	public JMouseEvent()
	{
	}
	
	public abstract int getDX();
	
	public abstract int getDY();
	public abstract int getButton();
	public int getLocalX()
	{
		return getDX();
	}
	public int getLocalY()
	{
		return getDY();
	}
}
