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
package org.joge.core.ai;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Moncef YABI
 */
public class Neuron
{

    public static int type = 1;

    private List<Double> weights;

    private int numInputs;

    public Neuron(int numInputs)
    {
        this.numInputs= numInputs;
        weights = new ArrayList<>(numInputs);
        for (int i = 0; i < numInputs; i++)
        {
            weights.add(2 * Math.random() - 1);
        }
    }

    public List<Double> getWeights()
    {
        return weights;
    }

    public void setWeights(List<Double> weights)
    {
        this.weights = weights;
    }

    public int getNumInputs()
    {
        return numInputs;
    }

    public void setNumInputs(int numInputs)
    {
        this.numInputs = numInputs;
    }
    
    @Override
    public String toString()
    {
        return "Perceptron{" + "weights=" + weights + '}';
    }

}
