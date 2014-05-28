package org.joge.core.event;


public interface JEventListener extends JComponentListener
{
	public void processEvent(JEvent event);
}
