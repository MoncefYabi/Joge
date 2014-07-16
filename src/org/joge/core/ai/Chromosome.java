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
public class Chromosome
{
    private List<Double> weights = new ArrayList<>();
    private double fitness;

    public Chromosome()
    {
        fitness=0;
    }

    public Chromosome(double score,List<Double> weights)
    {
        this.fitness = score;
        this.weights=weights;
    }    
    
    
    public List<Double> getWeights()
    {
        return weights;
    }

    public void setWeights(List<Double> weights)
    {
        this.weights = weights;
    }

    public double getFitness()
    {
        return fitness;
    }

    public void setFitness(double fitness)
    {
        this.fitness = fitness;
    }
    
    public static boolean compaireChromosome(Chromosome l, Chromosome r)
    {
        return l.fitness<r.fitness;
    }
    
}
