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

import org.joge.core.draw.Graphics;
import org.joge.core.draw.Image;
import org.joge.core.math.Rectangle;

/**
 * @author Moncef YABI
 */
public class Sprite implements ISprite
{

    protected Image spriteImage;
    protected float x = 0;
    protected float y = 0;
    protected Rectangle me = new Rectangle();
    protected Rectangle him = new Rectangle();
    protected String name;
    protected String imageurl;
    protected String type;

    public Sprite()
    {
    }

    public Sprite(String ref)
    {
        this(ref, 0, 0);
    }

    public Sprite(String ref, float x, float y)
    {
        initSprite(ref, x, y);
    }

    public final void initSprite(String ref, float x, float y)
    {
        spriteImage = new Image(ref);
        this.x = x;
        this.y = y;
    }

    @Override
    public void setX(float x)
    {
        this.x = x;
    }

    @Override
    public void setY(float y)
    {
        this.y = y;
    }

    @Override
    public void setPoint(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public float getX()
    {
        return x;
    }

    @Override
    public float getY()
    {
        return y;
    }

    @Override
    public void moveX(float speed)
    {
        x += speed;
    }

    @Override
    public void moveY(float speed)
    {
        y += speed;
    }

    @Override
    public void render(Graphics g, float x_pos, float y_pos)
    {
        spriteImage.draw(x + x_pos, y + y_pos);
    }

    @Override
    public void render(Graphics g)
    {
        spriteImage.draw(x, y);
    }

    @Override
    public float getHeight()
    {
        return spriteImage.getHeight() - (spriteImage.getHeight() / 11);
    }

    @Override
    public float getWidth()
    {
        return spriteImage.getWidth() - (spriteImage.getWidth() / 11);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getImageurl()
    {
        return imageurl;
    }

    public void setImageurl(String imageurl)
    {
        this.imageurl = imageurl;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    @Override
    public boolean collide(Sprite oder)
    {
        me.setBounds(x, y, getWidth(), getHeight());
        him.setBounds(oder.getX(), oder.getY(), oder.getWidth(), oder.getHeight());
        return me.intersects(him);
    }
}
