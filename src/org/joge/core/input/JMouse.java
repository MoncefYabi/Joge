package org.joge.core.input;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class JMouse
{

    private static int lastX_Pos;
    private static int lastY_Pos;
    private static int wheel;
    private static int _height;
    private static boolean displayActive = true;
    private static boolean[] mousePressed = new boolean[10];
    private static JDisplay disp = new JDisplay();

    public static void init(int height)
    {
        _height = height;
        lastX_Pos = getMouseX();
        lastY_Pos = getMouseY();
    }

    public static JDisplay getDisplay()
    {
        return disp;
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
                    disp.fireMousePressedEvent(Mouse.getEventX(),
                            _height - Mouse.getEventY(), Mouse
                            .getEventButton());
                } else
                {
                    mousePressed[Mouse.getEventButton()] = false;
                    disp.fireMouseReleasedEvent(Mouse.getEventX(),
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
                    disp.fireMouseWheel(Mouse.getX(), Mouse.getY(), dwheel > 0, 1);
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
                disp.fireMouseMovedEvent(getMouseX(), getMouseY());
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
