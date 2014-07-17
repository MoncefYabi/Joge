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
package org.joge.core.ai.genetic;

import org.joge.core.ai.entity.Creature;
import java.util.ArrayList;
import java.util.List;
import org.joge.core.ai.Brain;
import org.joge.core.ai.Brain;
import org.joge.core.ai.genetic.Chromosome;
import org.joge.core.tools.exception.NeuralNetworkException;

/**
 *
 * @author Moncef YABI
 */
public class GenAlgorithm
{

    private double mutateCount = 0;

    public List<Creature> crossover(Creature mum, Creature dad) throws NeuralNetworkException
    {
        int mumCromeLenght = mum.getBrain().getAllWeights().size();
        int dadCromeLenght = dad.getBrain().getAllWeights().size();

        if (mumCromeLenght != dadCromeLenght)
        {
            throw new NeuralNetworkException("Creatures don't have the same number of chromosomes!");
        }

        Creature baby1 = new Creature(new Brain());
        Creature baby2 = new Creature(new Brain());
        baby1.setName(mum.getName());
        baby2.setName(dad.getName());
        Chromosome babyChromosome1 = new Chromosome();
        Chromosome babyChromosome2 = new Chromosome();
        Chromosome mumChromosome = mum.getBrain().getChromosome();
        Chromosome dadChromosome = dad.getBrain().getChromosome();

        int cutpoint = mumCromeLenght / 2;
        List<Creature> offSpring = new ArrayList<>(2);

        for (int i = 0; i < mumCromeLenght; i++)
        {
            if (i < cutpoint)
            {
                babyChromosome1.getWeights().add(mumChromosome.getWeights().get(i));
                babyChromosome2.getWeights().add(dadChromosome.getWeights().get(i));
            } else
            {
                babyChromosome2.getWeights().add(mumChromosome.getWeights().get(i));
                babyChromosome1.getWeights().add(dadChromosome.getWeights().get(i));
            }
        }
        mutate(babyChromosome1);
        mutate(babyChromosome2);

        baby1.getBrain().setChromosome(babyChromosome1);
        baby2.getBrain().setChromosome(babyChromosome2);

        offSpring.add(baby1);
        offSpring.add(baby2);
        return offSpring;
    }

    private void mutate(Chromosome babyChromosome)
    {
        int babyChromosomeLength = babyChromosome.getWeights().size();
        
        mutateCount++;
        for (int i = 0; i < (babyChromosomeLength/3)+1; i++)
        {
            if (Math.random() < mutateRate())
            {
                int randomIndex = (int) (0.999999 * Math.random() * (double) (babyChromosomeLength));
                babyChromosome.getWeights().set(i, mutateValue()+babyChromosome.getWeights().get(i));
            }
        }
    }

    public double mutateValue()
    {
        return Math.sin((double) mutateCount / 400.0f) / 30.0f;
    }
    public double mutateRate()
    {
        return (Math.cos((double) mutateCount / 400.0f) + 1.0f) / 30.0f;
    }
}
