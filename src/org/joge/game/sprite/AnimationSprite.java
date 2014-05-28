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
package org.joge.game.sprite;

import org.joge.core.draw.Image;
import org.joge.game.tools.ToolKit;

/**
 *
 * @author Moncef YABI
 */
public class AnimationSprite
{

    private Image image;
    private Image images[];
    private int counter = 0;
    private int delay;
    private int frames = 0;
    private boolean loop;
    private boolean start = true;
    private boolean stoped = false;
    private float x;
    private float y;
    private String name;

    public AnimationSprite(String ref, int delay, float xpos, float ypos, boolean loop)
    {
        image = new Image(ref);
        initValuse(delay, loop, xpos, ypos);
    }

    private void initValuse(int delay, boolean loop, float xpos, float ypos)
    {
        this.delay = delay;
        this.loop = loop;
        this.x = xpos;
        this.y = ypos;
    }

    public AnimationSprite(Image image, int delay, float xpos, float ypos, boolean loop)
    {
        this.image = image;
        initValuse(delay, loop, xpos, ypos);
    }

    public AnimationSprite(Image images[], int delay, float xpos, float ypos, boolean loop)
    {
        this.images = images;
        initValuse(delay, loop, xpos, ypos);
    }

    public void calculateFrames(int spalten, int zeilen)
    {
        images = ToolKit.calculateFrames(image, spalten, zeilen);
    }

    public int getCounter()
    {
        return counter;
    }

    public void setCounter(int counter)
    {
        this.counter = counter;
    }

    public int getDelay()
    {
        return delay;
    }

    public void setDelay(int delay)
    {
        this.delay = delay;
    }

    public Image[] getImages()
    {
        return images;
    }

    public void setImages(Image[] images)
    {
        this.images = images;
    }

    public boolean isLooped()
    {
        return loop;
    }

    public void setLoop(boolean loop)
    {
        this.loop = loop;
    }

    public float getXpos()
    {
        return x;
    }

    public void setXpos(float xpos)
    {
        this.x = xpos;
    }

    public float getYpos()
    {
        return y;
    }

    public void setYpos(float ypos)
    {
        this.y = ypos;
    }

    public void render()
    {
        render(0, 0);
    }

    public void stopAnimation()
    {
        this.start = false;
    }

    public void stopAnimation(int index)
    {
        if (index > images.length - 1 || index < 0)
        {
            return;
        }
        counter = index;
        stopAnimation();
    }

    public void startAnimation()
    {
        this.start = true;
    }

    public void startAnimation(int index)
    {
        if (index > images.length - 1 || index < 0)
        {
            return;
        }
        counter = index;
        startAnimation();
    }

    public void render(float xc, float yc)
    {
        performCount();
        images[counter].draw(x + xc, y + yc);
        frames++;
        animate();
    }

    private void animate()
    {
        if (frames >= delay && start)
        {
            if (loop)
            {
                counter = (counter + 1) % images.length;
                if (counter == 0)
                {
                    stoped = true;
                }
            } else
            {
                counter++;
            }

            frames = 0;
        }
    }

    private void performCount()
    {
        if (counter > images.length - 1)
        {
            counter = images.length - 1;
            stoped = true;
        } else if (counter < 0)
        {
            counter = 0;
        }
    }

    public void setFrame(int index)
    {
        if (index > images.length - 1 || index < 0)
        {
            return;
        }

        counter = index;
    }

    public void nextFrame()
    {
        if (counter > images.length - 1)
        {
            return;
        }

        counter++;
    }

    public void previousFrame()
    {
        if (counter == 0)
        {
            return;
        }

        counter--;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isStoped()
    {
        return stoped;
    }

    public void setStoped(boolean stoped)
    {
        this.stoped = stoped;
    }
}
