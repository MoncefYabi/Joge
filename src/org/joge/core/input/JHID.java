
/*
 * Copyright (C) 2014 Moncef YABI
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
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

/**
 *
 * @author Moncef YABI
 */
public class JHID
{
    private final ArrayList<JComponentListener> mouseListener = new ArrayList<>();
    private final ArrayList<JComponentListener> keyListener = new ArrayList<>();
    
    public static final JHID jHID = new JHID();

    private JHID()
    {
    }

    public static JHID getjHID()
    {
        return jHID;
    }
    
    
    public void fireMousePressedEvent(int mouseX, int mouseY, int button)
    {
        JMousePressedEvent e = new JMousePressedEvent(mouseX, mouseY, button);
        for (int i = 0; i < mouseListener.size(); i++)
        {
            JMouseListener l = (JMouseListener) mouseListener.get(i);
            l.mousePressed(e);
        }
    }

    public void fireMouseReleasedEvent(int mouseX, int mouseY, int button)
    {
        JMouseReleasedEvent e = new JMouseReleasedEvent(mouseX, mouseY, button);
        for (int i = 0; i < mouseListener.size(); i++)
        {
            JMouseListener l = (JMouseListener) mouseListener.get(i);
            l.mouseReleased(e);
        }
    }

    public void fireMouseWheel(int mouseX, int mouseY, boolean up, int rotation)
    {
        JMouseWheelEvent e = new JMouseWheelEvent(mouseX, mouseY, rotation, up);
        for (int i = 0; i < mouseListener.size(); i++)
        {
            JMouseWheelListener l = (JMouseWheelListener) mouseListener.get(i);
            l.mouseWheel(e);
        }
    }

    public void fireMouseMovedEvent(int mouseX, int mouseY)
    {
        JMouseMovedEvent e = new JMouseMovedEvent(mouseX, mouseY);
        for (int i = 0; i < mouseListener.size(); i++)
        {
            JMouseListener l = (JMouseListener) mouseListener.get(i);
            l.mouseMoved(e);
        }
    }

    public void fireKeyPressedEvent(int key, char c)
    {
        JKeyPressedEvent e = new JKeyPressedEvent(key, c);
        for (int i = 0; i < keyListener.size(); i++)
        {
            JKeyboardListener l = (JKeyboardListener) keyListener.get(i);
            l.keyPressed(e);
        }
    }

    public void firekeyReleased(int key, char c)
    {
        JKeyReleasedEvent e = new JKeyReleasedEvent(key, c);
        for (int i = 0; i < keyListener.size(); i++)
        {
            JKeyboardListener l = (JKeyboardListener) keyListener.get(i);
            l.keyReleased(e);
        }
    }

    public void firekeyLongPressed(int key, char c)
    {
        JKeyLongPressedEvent e = new JKeyLongPressedEvent(key, c);
        for (int i = 0; i < keyListener.size(); i++)
        {
            JKeyboardListener l = (JKeyboardListener) keyListener.get(i);
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
