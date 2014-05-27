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

import org.joge.game.j2d.Graphics;

/**
 *
 * @author Moncef YABI
 */
public interface ISprite
{

    public static int SPRITE_2D = 1;
    public static int SPRITE_ANIMATION = 2;

    public float getX();

    public float getY();

    public void setX(float x);

    public void setY(float y);

    public void moveX(float speed);

    public void moveY(float speed);

    public boolean collide(ISprite oder);

    public void render(Graphics g, float x_pos, float y_pos);

    public void render(Graphics g);

    public float getWidth();

    public float getHeight();

    public void setPoint(float x, float y);

    public void setFrame(int i, int j, int k, boolean loop);

    public int getSpriteArte();

}
