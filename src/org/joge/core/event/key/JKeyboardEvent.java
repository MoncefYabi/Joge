package org.joge.core.event.key;

import org.joge.core.event.JEvent;

public class JKeyboardEvent extends JEvent
{

    private int key;
    private char c;

    public JKeyboardEvent(int key, char c)
    {
        this.key = key;
        this.c = c;
    }

    /**
     * @return the key
     */
    public int getKey()
    {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(int key)
    {
        this.key = key;
    }

    /**
     * @return the c
     */
    public char getC()
    {
        return c;
    }

    /**
     * @param c the c to set
     */
    public void setC(char c)
    {
        this.c = c;
    }
}
