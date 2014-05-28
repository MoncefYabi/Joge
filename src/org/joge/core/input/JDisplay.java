package org.joge.core.input;

import java.util.ArrayList;

import org.joge.core.event.JComponentListener;
import org.joge.core.event.key.JKeyLongPressedEvent;
import org.joge.core.event.key.JKeyPressedEvent;
import org.joge.core.event.key.JKeyReleasedEvent;
import org.joge.core.event.key.JKeyboardListener;
import org.joge.core.event.mouse.JMouseListener;
import org.joge.core.event.mouse.JMouseMovedEvent;
import org.joge.core.event.mouse.JMousePressedEvent;
import org.joge.core.event.mouse.JMouseReleasedEvent;
import org.joge.core.event.mouse.JMouseWheelEvent;
import org.joge.core.event.mouse.JMouseWheelListener;


public class JDisplay 
{
	private ArrayList<JComponentListener> mouseListener = new ArrayList<JComponentListener>();
	private ArrayList<JComponentListener>keyListener = new ArrayList<JComponentListener>();

	public void fireMousePressedEvent(int mouseX, int mouseY, int button)
	{
		JMousePressedEvent e = new JMousePressedEvent(mouseX, mouseY,button);
		for (int i = 0; i < mouseListener.size(); i++)
		{
			JMouseListener l = (JMouseListener) mouseListener.get(i);
			l.mousePressed(e);
		}
	}

	public void fireMouseReleasedEvent(int mouseX, int mouseY, int button)
	{
		JMouseReleasedEvent e = new JMouseReleasedEvent(mouseX, mouseY,button);
		for (int i = 0; i < mouseListener.size(); i++)
		{
			JMouseListener l = (JMouseListener) mouseListener.get(i);
			l.mouseReleased(e);
		}
	}

	public void fireMouseWheel(int mouseX, int mouseY, boolean up, int rotation)
	{
		JMouseWheelEvent e = new JMouseWheelEvent( mouseX, mouseY,rotation, up);
		for (int i = 0; i < mouseListener.size(); i++)
		{
			JMouseWheelListener l = (JMouseWheelListener) mouseListener.get(i);
			l.mouseWheel(e);
		}
	}

	public void fireMouseMovedEvent(int mouseX, int mouseY)
	{
		JMouseMovedEvent e = new JMouseMovedEvent( mouseX, mouseY);
		for (int i = 0; i < mouseListener.size(); i++)
		{
			JMouseListener l = (JMouseListener) mouseListener.get(i);
			l.mouseMoved(e);
		}
	}
	public void fireKeyPressedEvent(int key,char c)
	{
		JKeyPressedEvent e= new JKeyPressedEvent(key,c);
		for (int i = 0; i < keyListener.size(); i++)
		{
			JKeyboardListener l=(JKeyboardListener) keyListener.get(i);
			l.keyPressed(e);
		}
	}
	public void firekeyReleased(int key,char c)
	{
		JKeyReleasedEvent e= new JKeyReleasedEvent(key,c);
		for (int i = 0; i < keyListener.size(); i++)
		{
			JKeyboardListener l=(JKeyboardListener) keyListener.get(i);
			l.keyReleased(e);
		}
	}
	public void firekeyLongPressed(int key,char c)
	{
		JKeyLongPressedEvent e= new JKeyLongPressedEvent(key,c);
		for (int i = 0; i < keyListener.size(); i++)
		{
			JKeyboardListener l=(JKeyboardListener) keyListener.get(i);
			l.keyLongPressed(e);
		}
	}
	public void addMouseEventListener(JComponentListener listener)
	{
		mouseListener.add(listener);
	}

	public void removeMouseEventListener(JComponentListener listener)
	{
		mouseListener.remove(listener);
	}
	
	public void addKeyEventListener(JComponentListener listener)
	{
		keyListener.add(listener);
	}

	public void removeKeyEventListener(JComponentListener listener)
	{
		keyListener.remove(listener);
	}
}
