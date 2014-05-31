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

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

/**
 * Klasse JKeyboard behandelt alle an sie gesendete Tastaure-Events
 *
 * @author Moncef YABI
 */
public class JMouse
{

    private static int lastX_Pos;
    private static int lastY_Pos;
    private static int wheel;
    private static int _height;
    private static boolean displayActive = true;
    private static boolean[] mousePressed = new boolean[10];

    public static void init(int height)
    {
        _height = height;
        lastX_Pos = getMouseX();
        lastY_Pos = getMouseY();
    }

    public static void setCursors(boolean visible)
    {
        Mouse.setGrabbed(visible);
    }

    public static int getMouseX()
    {
        return Mouse.getX();
    }

    public static int getMouseY()
    {
        return _height - Mouse.getY();
    }

    public static void fireMouse()
    {
        while (Mouse.next())
        {
            if (Mouse.getEventButton() >= 0)
            {
                if (Mouse.getEventButtonState())
                {
                    mousePressed[Mouse.getEventButton()] = true;
                    JHID.getjHID().fireMousePressedEvent(Mouse.getEventX(),
                            _height - Mouse.getEventY(), Mouse
                            .getEventButton());
                } else
                {
                    mousePressed[Mouse.getEventButton()] = false;
                    JHID.getjHID().fireMouseReleasedEvent(Mouse.getEventX(),
                            _height - Mouse.getEventY(), Mouse
                            .getEventButton());
                }
            } else
            {
//				if (Mouse.isGrabbed())
//				{
//					if ((Mouse.getEventDX() != 0) || (Mouse.getEventDY() != 0))
//					{
//						disp.fireMouseMovedEvent( Mouse.getEventDX(), -Mouse.getEventDY());
//					}
//				}

                int dwheel = Mouse.getEventDWheel();
                wheel += dwheel;
                if (dwheel != 0)
                {
                    JHID.getjHID().fireMouseWheel(Mouse.getX(), Mouse.getY(), dwheel > 0, 1);
                }
            }
        }
        if (!displayActive)
        {
            lastX_Pos = getMouseX();
            lastY_Pos = getMouseY();
        } else
        {
            if ((lastX_Pos != getMouseX()) || (lastY_Pos != getMouseY()))
            {
                JHID.getjHID().fireMouseMovedEvent(getMouseX(), getMouseY());
                lastX_Pos = getMouseX();
                lastY_Pos = getMouseY();
            }
        }
        if (Display.isCreated())
        {
            displayActive = Display.isActive();
        }
    }
}
