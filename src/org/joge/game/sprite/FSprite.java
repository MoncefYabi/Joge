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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.joge.core.draw.Graphics;
import org.joge.core.draw.Image;
import org.joge.game.animation.Frame;
import org.joge.game.tools.ToolKit;

/**
 *
 * @author Moncef YABI
 */
public class FSprite extends Sprite
{

    private Image images[];
    private ArrayList<AnimationSprite> animationframes = new ArrayList<>();
    private List<Integer> animationKeys;
    private List<Frame> frames;
    private int spritecountX;
    private int spritecountY;
    private int delay;
    private AnimationSprite activeAnimation;
    private String activeAnimationName;
    private Queue<Integer> keyQueue;

    public FSprite()
    {
    }

    public void init()
    {
        AnimationSprite _anim;
        Image tempImageArray[] = null;
        int index = 0;
        for (Frame frame : frames)
        {
            this.initSprite(imageurl, x, y);
            images = ToolKit.calculateFrames(this.spriteImage, spritecountX, spritecountY);
            if (frame.getCount() > 1)
            {
                tempImageArray = Arrays.copyOfRange(images, index, index + frame.getCount());
                index = index + frame.getCount();
            } else if (frame.getCount() == 1)
            {
                tempImageArray = new Image[1];
                tempImageArray[0] = images[index + frame.getCount() - 1];
                index = index + frame.getCount();
            }
            _anim = new AnimationSprite(tempImageArray, delay, x, y, frame.isLoop());
            _anim.setName(frame.getName());
            animationframes.add(_anim);
        }
        keyQueue = new LinkedList<>(animationKeys);
        setActiveAnimation(activeAnimationName);
    }

    public Image[] getImages()
    {
        return images;
    }

    @Override
    public void moveX(float speed)
    {
        super.moveX(speed);
        this.activeAnimation.setXpos(x);
    }

    @Override
    public void moveY(float speed)
    {
        super.moveY(speed);
        this.activeAnimation.setYpos(y);
    }

    @Override
    public void render(Graphics g, float xc, float yc)
    {
        animate();
        this.activeAnimation.render(xc, yc);
    }

    @Override
    public void render(Graphics g)
    {
        animate();
        this.activeAnimation.render();
    }

    public ArrayList<AnimationSprite> getAnimatioframes()
    {
        return animationframes;
    }

    public void setAnimatioframes(ArrayList<AnimationSprite> animatioframes)
    {
        this.animationframes = animatioframes;
    }

    public List getFrames()
    {
        return frames;
    }

    public void setFrames(List frames)
    {
        this.frames = frames;
    }

    public int getSpritecountX()
    {
        return spritecountX;
    }

    public void setSpritecountX(int spritecountX)
    {
        this.spritecountX = spritecountX;
    }

    public int getSpritecountY()
    {
        return spritecountY;
    }

    public void setSpritecountY(int spritecountY)
    {
        this.spritecountY = spritecountY;
    }

    public int getDelay()
    {
        return delay;
    }

    public void setDelay(int delay)
    {
        this.delay = delay;
    }

    public AnimationSprite getActiveAnimation()
    {
        return activeAnimation;
    }

    public void setActiveAnimation(int index)
    {
        this.activeAnimation = animationframes.get(index);

    }

    public void setActiveAnimation(String name)
    {
        for (AnimationSprite anim : animationframes)
        {
            if (anim.getName().equals(name))
            {
                this.activeAnimation = anim;
            }
        }
    }

    private void animate()
    {
        if (this.activeAnimation.isStoped())
        {
            Integer index = keyQueue.poll();

            if (index != null)
            {
                this.activeAnimation = animationframes.get(index.intValue() - 1);
                //System.out.println(this.activeAnimation.getName());
            }
        }
    }

    public String getActiveAnimationName()
    {
        return activeAnimationName;
    }

    public void setActiveAnimationName(String activeAnimationName)
    {
        this.activeAnimationName = activeAnimationName;
    }

    public List<Integer> getAnimationKeys()
    {
        return animationKeys;
    }

    public void setAnimationKeys(List<Integer> animationKeys)
    {
        this.animationKeys = animationKeys;
    }

    @Override
    public float getHeight()
    {
        return activeAnimation.getImages()[0].getHeight();
    }

    @Override
    public float getWidth()
    {
        return activeAnimation.getImages()[0].getWidth();
    }
}
