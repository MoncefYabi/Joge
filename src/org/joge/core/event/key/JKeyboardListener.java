package org.joge.core.event.key;

import org.joge.core.event.JComponentListener;

public interface JKeyboardListener extends JComponentListener
{

    public void keyReleased(JKeyReleasedEvent e);

    public void keyPressed(JKeyPressedEvent e);

    public void keyLongPressed(JKeyLongPressedEvent e);
}
