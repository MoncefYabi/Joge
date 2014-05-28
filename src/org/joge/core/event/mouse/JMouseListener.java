package org.joge.core.event.mouse;

import org.joge.core.event.JComponentListener;


public interface JMouseListener extends JComponentListener
{
	public void mouseMoved (JMouseMovedEvent event);
	 
    public void mousePressed (JMousePressedEvent event);

    public void mouseReleased (JMouseReleasedEvent event);

}
