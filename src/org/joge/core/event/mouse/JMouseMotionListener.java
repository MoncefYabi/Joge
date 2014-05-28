package org.joge.core.event.mouse;

import org.joge.core.event.JComponentListener;

public interface JMouseMotionListener extends JComponentListener
{
	public void mouseDragged(JMouseDraggedEvent event);
}
