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

/**
 *
 * @author Moncef YABI
 */
public class Perceptron
{

    public static int type = 2;

    private List<Double> weights;

    public Perceptron()
    {
        weights = new ArrayList<>();
    }

    public static double sigmoid(double x)
    {
        switch (type)
        {
            case 1:
                return 1.0f / (1.0f + Math.exp(-x));
            case 2:
                return (Math.tanh(x * 100) + 1.0) * 0.5;
            default:
                return (Math.cos(x * Math.PI) + 1) * 0.5;
        }
    }

    public double execute(List<Double> input)
    {
        if (input.size() + 1 != weights.size())
        {
            return 0.0;
        }
        double answer = 0.0;

        for (int i = 0; i < input.size(); i++)
        {
            answer += input.get(i) * weights.get(i);

        }
        answer += weights.get(weights.size() - 1);
        return sigmoid(answer);
    }

    public List<Double> getWeights()
    {
        return weights;
    }

    public void setWeights(List<Double> weights)
    {
        this.weights = weights;
    }

    @Override
    public String toString()
    {
        return "Perceptron{" + "weights=" + weights + '}';
    }
    
    

}
