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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.joge.core.draw.Color;
import org.joge.core.draw.Graphics;
import org.joge.core.math.Vector2d;
import org.joge.game.sprite.Sprite;

/**
 *
 * @author dell
 */
public class Fish extends Sprite
{

    public int age;
    private Brain brain;
    private Food target;
    private int score;
    private double orientation;
    public static final int RANGE = 640;
    public final double PI = Math.PI;
    public final double DISTMAXRANGE = Math.sqrt((double) RANGE * (double) RANGE);

    public Fish(Brain brain)
    {
        this.brain = brain;
        age = 0;
        setRandomPosition();
        target = null;
    }

    private void setRandomPosition()
    {
        Random randomGenerator = new Random();
        x = randomGenerator.nextInt(RANGE);
        y = randomGenerator.nextInt(RANGE);
        orientation = PI * (2 * Math.random() - 1);
    }

    public boolean eat()
    {
        List<Double> input = new ArrayList<>();
        input.add(angleToFood(this, target));
        input.add(distToFood(this, target) / 640.0f);
        double leftForce = brain.left(input) * 2.0 - 1;
        double rightForce = brain.right(input) * 2.0 - 1;
        double speed = brain.speed(input) * 2 - 0.5;
        orientation += (rightForce + leftForce) * PI * 0.5;
        // move
        x += Math.cos(orientation) * speed;
        y += Math.sin(orientation) * speed;
        if (x >= RANGE)
        {
            x -= RANGE;
        }
        if (y >= RANGE)
        {
            y -= RANGE;
        }
        if (x < 0)
        {
            x += RANGE;
        }
        if (y < 0)
        {
            y += RANGE;
        }
        // eat?
        if (isNearEnough(distToFood(this, target)))
        {
            target.eat();
            score++;
            return true;
        }
        return false;
    }

    public void resetPosition()
    {
        setRandomPosition();
    }

    public double orientation()
    {
        return orientation;
    }

    public int score()
    {
        return score;
    }

    public boolean operator(Fish fish)
    {
        return score / (double) (age + 1) > fish.score / (double) (fish.age + 1);
    }

    public boolean setTarget(Food target)
    {
        if (this.target == null)
        {
            this.target = target;
            return true;
        }
        List<Double> oldInput = new ArrayList<>();
        List<Double> newInput = new ArrayList<>();

        oldInput.add(angleToFood(this, this.target));
        newInput.add(angleToFood(this, target));

        oldInput.add(distToFood(this, this.target) / 200.0f);
        newInput.add(distToFood(this, target) / 200.0f);
        if (brain.evaluateFood(oldInput) < brain.evaluateFood(newInput))
        {
            this.target = target;
            return true;
        }
        return false;
    }

    public void resetTarget(Food target)
    {
        this.target = target;
    }

    public void replaceBrain(Brain brain)
    {
        this.brain = brain;
    }

    public void resetScore()
    {
        score = 0;
    }

    public static double distToFood(Fish fish, Food food)
    {
        double d1 = fish.getX() - food.x;
        double d2 = fish.getY() - food.y;
        return Math.sqrt(d1 * d1 + d2 * d2);
    }

    public static double angleToFood(Fish fish, Food food)
    {
        Vector2d v1 = new Vector2d(), v2 = new Vector2d();
        v1.setX(food.x - fish.getX());
        v1.setY(food.y - fish.getY());
        v2.setX(Math.cos(fish.orientation));
        v2.setY(Math.sin(fish.orientation));

        double dotProduct = v1.getX() * v2.getX() + v1.getY() * v2.getY();
        double m = Math.sqrt(v1.getX() * v1.getX() + v1.getY() * v1.getY()); // |v2| = 1

        return Math.acos(dotProduct / m);
    }

    public Brain getBrain()
    {
        return brain;
    }

    public Food getTarget()
    {
        return target;
    }

    private boolean isNearEnough(double dist)
    {
        return dist <= 8;
    }

    @Override
    public void render(Graphics g)
    {
        g.setColor(new Color(0, score * 5, 255));
        g.rotate(x, y, (float) Math.toDegrees(orientation));
        g.drawLine(x, y, x, y + 10);
        g.drawLine(x, y + 10, x + 10, y);
        g.drawLine(x + 10, y, x, y);
        g.stop();
        //g.drawString("Orientation: " + this.orientation(), this.getX() + 12, this.getY() + 12);
        g.drawString("Score: " + this.score(), this.getX() + 12, this.getY() + 20);
        //g.drawString("Distance to food: " + Fish.distToFood(this, this.getTarget()), this.getX() + 12, this.getY() + 28);
        //g.drawString("Angle to food: " + Fish.angleToFood(this, this.getTarget()), this.getX() + 12, this.getY() + 36);
        if(this.getName()!=null)g.drawString("Name: " + this.getName(), this.getX() + 12, this.getY() + 44);
    }
}