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
package org.joge.game.test.nn;

import java.util.List;
import java.util.Random;
import org.joge.core.ai.Brain;
import org.joge.core.draw.Graphics;
import org.joge.core.math.Vector2d;
import org.joge.game.sprite.Sprite;
import org.joge.game.test.Food;

/**
 *
 * @author dell
 */
public class Creature extends Sprite
{

    private Brain brain;
    public static final int RANGE = 640;
    public Food target;

    public Creature(Brain brain)
    {
        this.brain = brain;
        setRandomPosition();
    }

    private void setRandomPosition()
    {
        Random randomGenerator = new Random();
        x = randomGenerator.nextInt(RANGE);
        y = randomGenerator.nextInt(RANGE);
    }

    public Vector2d getClosestFoodObject(List<Food> foods)
    {
        double closest_so_far = 99999;

        Vector2d closestObject = new Vector2d(0, 0);

        //cycle through mines to find closest
        for (Food food : foods)

        {
            double len_to_object = closestObject.vec2dLength(food.x, food.y, x, y);

            if (len_to_object < closest_so_far)
            {
                closest_so_far = len_to_object;
                Vector2d m_vPosition = new Vector2d(x, y);
                closestObject = m_vPosition.min(new Vector2d(food.x, food.y));

                target = food;
            }
        }

        return closestObject;
    }

    public Food checkForMine(List<Food> foods, double size)
    {
        Vector2d m_vPosition = new Vector2d(x, y);
        Vector2d distToObject = m_vPosition.min(new Vector2d(target.x, target.y));

        if (distToObject.vec2dLength(distToObject) < (size + 5))
        {
            return target;
        }

        return null;
    }

    public Brain getBrain()
    {
        return brain;
    }

    public void setBrain(Brain brain)
    {
        this.brain = brain;
    }

    @Override
    public void render(Graphics g)
    {
        g.drawLine(x, y, x, y + 10);
        g.drawLine(x, y + 10, x + 10, y);
        g.drawLine(x + 10, y, x, y);
        g.stop();
        if (this.getName() != null)
        {
            g.drawString("Name: " + this.getName(), this.getX() + 12, this.getY() + 44);
        }
    }
}
