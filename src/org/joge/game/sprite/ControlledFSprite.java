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

import org.joge.core.input.JKeyboard;

/**
 *
 * @author Moncef YABI
 */
public class ControlledFSprite extends FSprite
{

    private boolean[][] collisionMap;

    public void move(final float speed, final int SIZE)
    {
        if (JKeyboard.isKeyDown(JKeyboard.KEY_UP))
        {
            if (isAllowed(x, y - speed, SIZE))
            {
                this.moveY(-speed);
            }

            this.setActiveAnimation("up");
            this.getActiveAnimation().setLoop(true);
        } else if (JKeyboard.isKeyDown(JKeyboard.KEY_DOWN))
        {
            if (isAllowed(x, y + SIZE + speed, SIZE))
            {
                this.moveY(speed);
            }

            this.setActiveAnimation("down");
            this.getActiveAnimation().setLoop(true);
        } else if (JKeyboard.isKeyDown(JKeyboard.KEY_LEFT))
        {
            if (isAllowed(x - speed, y, SIZE))
            {
                this.moveX(-speed);
            }
            this.setActiveAnimation("right");
            this.getActiveAnimation().setLoop(true);
        } else if (JKeyboard.isKeyDown(JKeyboard.KEY_RIGHT))
        {
            if (isAllowed(x + SIZE + speed, y, SIZE))
            {
                this.moveX(speed);
            }
            this.setActiveAnimation("left");
            this.getActiveAnimation().setLoop(true);
        } else
        {
            this.getActiveAnimation().setStoped(true);
            this.getActiveAnimation().setLoop(false);
        }
        x = (int) this.getActiveAnimation().getXpos();
        y = (int) this.getActiveAnimation().getYpos();
        x = x >= 0 && x < 640 ? x : -1;
        y = y >= 0 && y < 640 ? y : -1;

    }

    private boolean isAllowed(float x, float y, final int SIZE)
    {
        if (x == -1 || y == -1)
        {
            return false;
        }
        int xBlock = (int) x / SIZE;
        int yBlock = (int) y / SIZE;
        return collisionMap[xBlock][yBlock];
    }

    public boolean[][] getCollisionMap()
    {
        return collisionMap;
    }

    public void setCollisionMap(boolean[][] collisionMap)
    {
        this.collisionMap = collisionMap;
    }
}
