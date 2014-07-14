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
package org.joge.game.test;

import java.util.Random;
import org.joge.core.draw.Color;
import org.joge.core.draw.Graphics;

/**
 *
 * @author Moncef YABI
 */
public class Food
{

    public float x, y;
    public static final int RANGE = 620;

    public Food()
    {
        getRandomPosition();
    }

    private void getRandomPosition()
    {
        Random randomGenerator = new Random();
        x = randomGenerator.nextInt(RANGE);
        y = randomGenerator.nextInt(RANGE);
    }

    public Food(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public void eat()
    {
         getRandomPosition();
    }
    
    public void render(Graphics g)
    {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, 5, 5);
        
    }
}
