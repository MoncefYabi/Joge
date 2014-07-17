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
package org.joge.core.ai.entity;

import org.joge.core.ai.config.NeuralNetConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.joge.core.ai.Brain;
import org.joge.core.draw.Color;
import org.joge.core.draw.Graphics;
import org.joge.core.math.Vector2d;
import org.joge.game.sprite.Sprite;

/**
 *
 * @author dell
 */
public class Creature extends Sprite
{

    private Brain brain;
    
    public Food target;
    private double orientation;
    public final double PI = Math.PI;
    private final Vector2d vectorLookAt = new Vector2d(0, 0);
    public static int BestScore;
    private int score;

    public Creature(Brain brain)
    {
        this.brain = brain;
        setRandomPosition();
    }

    public final void setRandomPosition()
    {
        Random randomGenerator = new Random();
        x = randomGenerator.nextInt(NeuralNetConfig.RANGE);
        y = randomGenerator.nextInt(NeuralNetConfig.RANGE);
        orientation = PI * (2 * Math.random() - 1);
    }

    public boolean eat(List<Food> foods)
    {
        getClosestFoodObject(foods);
        
        List<Double> input = new ArrayList<>();
        input.add(angleToFood(this, target));
        input.add(distToFood(this, target) / NeuralNetConfig.RANGE);
        
        List<Double> output = brain.update(input);
        double leftForce = output.get(0) * 2.0 - 1;
        double rightForce = output.get(1) * 2.0 - 1;
        double speed = output.get(2) * 2 - 0.5;
       
        orientation += (rightForce + leftForce) * PI * 0.5;
        // move
        x += Math.cos(orientation) * speed;
        y += Math.sin(orientation) * speed;
        if (x >= NeuralNetConfig.RANGE)
        {
            x -= NeuralNetConfig.RANGE;
        }
        if (y >= NeuralNetConfig.RANGE)
        {
            y -= NeuralNetConfig.RANGE;
        }
        if (x < 0)
        {
            x += NeuralNetConfig.RANGE;
        }
        if (y < 0)
        {
            y += NeuralNetConfig.RANGE;
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
    
    public boolean eat1(List<Food> foods)
    {
        List<Double> inputs = new ArrayList<>();

        //get vector to closest mine
        Vector2d vClosestFoodObject = getClosestFoodObject(foods);

        //normalise it
        vClosestFoodObject.vec2dNormalize(vClosestFoodObject);

        //add in vector to closest mine
        inputs.add(vClosestFoodObject.getX());
        inputs.add(vClosestFoodObject.getY());

        //add in sweepers look at vector
        inputs.add(vectorLookAt.getX());
        inputs.add(vectorLookAt.getY());

        //update the brain and get feedback
        List<Double> output = brain.update(inputs);

        
        //make sure there were no errors in calculating the 
        //output
        if (output.size() < brain.getNumOutputs())
        {
            return false;
        }
        
        //calculate steering forces
        double leftForce = output.get(0)* 2.0 - 1;
        double rightForce = output.get(1)* 2.0 - 1;	
        double speed = (rightForce + leftForce)* 2 - 0.5;
        orientation += (rightForce + leftForce) * PI * 0.5;
        //update Look At 
	vectorLookAt.setX(Math.cos(orientation)) ;
	vectorLookAt.setY(Math.sin(orientation));

        
        // move
        x += vectorLookAt.getX() * speed;
        y += vectorLookAt.getY() * speed;
        if (x >= NeuralNetConfig.RANGE)
        {
            x -= NeuralNetConfig.RANGE;
        }
        if (y >= NeuralNetConfig.RANGE)
        {
            y -= NeuralNetConfig.RANGE;
        }
        if (x < 0)
        {
            x += NeuralNetConfig.RANGE;
        }
        if (y < 0)
        {
            y += NeuralNetConfig.RANGE;
        }
        
        // eat?
        if (isNearEnough(distToFood(this, target)))
        {
            target.eat();
            BestScore++;
            score++;
            return true;
        }
        return false;
    
    }

    public Vector2d getClosestFoodObject(List<Food> foods)
    {
        double closest_so_far = 99999;

        Vector2d closestObject = new Vector2d(0, 0);

        //cycle through mines to find closest
        for (Food food : foods)

        {
            double len_to_object = distToFood(this,food);

            if (len_to_object < closest_so_far)
            {
                closest_so_far = len_to_object;
//                Vector2d m_vPosition = new Vector2d(x, y);
                closestObject = new Vector2d(food.x, food.y);
//                closestObject = m_vPosition.min(new Vector2d(food.x, food.y));
                
                target = food;
            }
        }

        return closestObject;
    }

    public double distToFood(Creature creature, Food food)
    {
        double d1 = creature.getX() - food.x;
        double d2 = creature.getY() - food.y;
        return Math.sqrt(d1 * d1 + d2 * d2);
    }
    
    public  double angleToFood(Creature creature, Food food)
    {
        Vector2d v1 = new Vector2d(0,0), v2 = new Vector2d(0,0);
        v1.setX(food.x - creature.getX());
        v1.setY(food.y - creature.getY());
        v2.setX(Math.cos(creature.orientation));
        v2.setY(Math.sin(creature.orientation));

        double dotProduct = v1.getX() * v2.getX() + v1.getY() * v2.getY();
        double m = Math.sqrt(v1.getX() * v1.getX() + v1.getY() * v1.getY()); // |v2| = 1

        return Math.acos(dotProduct / m);
    }
    
    private boolean isNearEnough(double dist)
    {
        return dist <= 8;
    }
    
    public Brain getBrain()
    {
        return brain;
    }

    public void setBrain(Brain brain)
    {
        this.brain = brain;
    }

    public Food getTarget()
    {
        return target;
    }

    public double getOrientation()
    {
        return orientation;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }
    
    @Override
    public void render(Graphics g)
    {
        g.setColor(Color.LIGHT_BLUE);
        g.rotate(x, y, (float) Math.toDegrees(orientation));
        g.drawLine(x, y, x, y + 10);
        g.drawLine(x, y + 10, x + 10, y);
        g.drawLine(x + 10, y, x, y);
        g.stop();
        if (this.getName() != null)
        {
            g.drawString(this.getName()+":"+this.getScore(), this.getX()-12, this.getY() + 12);
        }
    }

    @Override
    public String toString()
    {
        return "Creature{" + "score=" +  score+ ", brain=" + brain + '}';
    }
}
