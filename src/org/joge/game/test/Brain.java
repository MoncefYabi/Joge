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

/**
 *
 * @author Moncef YABI
 */
public class Brain
{

    public static int mutateCount = 0;

    private final Perceptron leftController;
    private final Perceptron rightController;
    private final Perceptron speedController;
    private final Perceptron foodEvaluator;

    public Brain()
    {
        leftController = new Perceptron();
        rightController = new Perceptron();
        speedController = new Perceptron();
        foodEvaluator = new Perceptron();
        ArrayList<Double> w1 = new ArrayList<>();
        ArrayList<Double> w2 = new ArrayList<>();
        ArrayList<Double> w3 = new ArrayList<>();
        ArrayList<Double> w4 = new ArrayList<>();
        for (int i = 0; i < 3; i++)
        {
            w1.add(2 * Math.random() - 1);
            w2.add(2 * Math.random() - 1);
            w3.add(2 * Math.random() - 1);
            w4.add(2 * Math.random() - 1);
        }
        leftController.setWeights(w1);
        rightController.setWeights(w2);
        speedController.setWeights(w3);
        foodEvaluator.setWeights(w4);
    }

    public Brain(Perceptron leftController, Perceptron rightController, Perceptron speedController, Perceptron foodEvaluator)
    {
        this.leftController = leftController;
        this.rightController = rightController;
        this.speedController = speedController;
        this.foodEvaluator = foodEvaluator;
    }

    public double left(List<Double> input)
    {
        return leftController.execute(input);
    }

    public double right(List<Double> input)
    {
        return rightController.execute(input);
    }

    public double speed(List<Double> input)
    {
        return speedController.execute(input);
    }

    public double evaluateFood(List<Double> input)
    {
        return foodEvaluator.execute(input);
    }
    
     public Perceptron getLeftController()
    {
        return leftController;
    }

    public Perceptron getRightController()
    {
        return rightController;
    }

    public Perceptron getSpeedController()
    {
        return speedController;
    }

    public Perceptron getFoodEvaluator()
    {
        return foodEvaluator;
    }

    public Brain crossover(Brain other)
    {
        mutateCount++;
        Double gene1[] = new Double[12];
        Double gene2[] = new Double[12];
        List<Double> l1, l2, r1, r2, s1, s2, f1, f2;
        l1 = new ArrayList<>(leftController.getWeights());
        l2 = new ArrayList<>(other.getLeftController().getWeights());
        r1 = new ArrayList<>(rightController.getWeights());
        r2 = new ArrayList<>(other.getRightController().getWeights());
        s1 = new ArrayList<>(speedController.getWeights());
        s2 = new ArrayList<>(other.getSpeedController().getWeights());
        f1 = new ArrayList<>(foodEvaluator.getWeights());
        f2 = new ArrayList<>(other.getFoodEvaluator().getWeights());

        for (int i = 0; i < 3; i++)
        {
            gene1[i] = l1.get(i);
            gene1[3 + i] = r1.get(i);
            gene1[6 + i] = s1.get(i);
            gene1[9 + i] = f1.get(i);
            gene2[i] = l2.get(i);
            gene2[3 + i] = r2.get(i);
            gene2[6 + i] = s2.get(i);
            gene2[9 + i] = f2.get(i);
        } // for

        Random randomGenerator = new Random();
        int breakPoint = randomGenerator.nextInt(99999) % 10 + 1;
        // re-use l1, r1
        for (int i = 0; i < 9; i++)
        {
            double v = (i <= breakPoint) ? gene1[i] : gene2[i];
            double m = Math.random();
            if (m < mutateRate())
            {
                v += mutateValue();
                if (v > 1.0f)
                {
                    v -= 2.0f;
                } else if (v < -1.0f)
                {
                    v += 2.0f;
                }
            }
            if (i < 3)
            {
                l1.set(i, v);
            } else if (i < 6)
            {
                r1.set(i - 3, v);
            } else if (i < 9)
            {
                s1.set(i - 6, v);
            } else
            {
                f1.set(i - 9, v);
            }
        }

        Perceptron left = new Perceptron(), right = new Perceptron(), speed = new Perceptron(), food = new Perceptron();
        left.setWeights(l1);
        right.setWeights(r1);
        speed.setWeights(s1);
        food.setWeights(f1);

        return new Brain(left, right, speed, food);
    }

    public static double mutateRate()
    {
        return (Math.cos((double) mutateCount / 400.0f) + 1.0f) / 30.0f;
    }

    public static double mutateValue()
    {
        return Math.sin((double) mutateCount / 400.0f) / 30.0f;
    }

    @Override
    public String toString()
    {
        return "Brain{" + "leftController=" + leftController + ", rightController=" + rightController + ", speedController=" + speedController + ", foodEvaluator=" + foodEvaluator + '}';
    }

    
}
