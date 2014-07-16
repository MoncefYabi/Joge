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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Moncef YABI
 */
public class GeneticAlgorithm
{

    private List<Chromosome> chromosomes = new ArrayList<>();
    //size of population
    private int m_iPopSize;

    //amount of weights per chromo
    private int m_iChromoLength;

    //total fitness of population
    private double m_dTotalFitness;

    //best fitness this population
    private double m_dBestFitness;

    //average fitness
    private double m_dAverageFitness;

    //worst
    private double m_dWorstFitness;

    //keeps track of the best genome
    private int m_iFittestGenome;

    //probability that a chromosones bits will mutate.
    //Try figures around 0.05 to 0.3 ish
    private double m_dMutationRate;

    //prrivate obability of chromosones crossing over bits
    private double m_dCrossoverRate;

    //generation counter
    private int m_cGeneration;

    public final double dMaxPerturbation = 0.3;

    public GeneticAlgorithm(int m_iPopSize, int m_iChromoLength, double m_dMutationRate, double m_dCrossoverRate)
    {
        this.m_iPopSize = m_iPopSize;
        this.m_iChromoLength = m_iChromoLength;
        this.m_dMutationRate = m_dMutationRate;
        this.m_dCrossoverRate = m_dCrossoverRate;

        //initialise population with chromosomes consisting of random
        //weights and all fitnesses set to zero
        for (int i = 0; i < m_iPopSize; ++i)
        {
            chromosomes.add(new Chromosome());

            for (int j = 0; j < m_iChromoLength; ++j)
            {
                chromosomes.get(i).getWeights().add(2 * Math.random() - 1);
            }
        }
    }

    //---------------------------------Mutate--------------------------------
    //
    //	mutates a chromosome by perturbing its weights by an amount not 
    //	greater than dMaxPerturbation
    //-----------------------------------------------------------------------
    public void mutate(List<Double> chromosomes)
    {
        //traverse the chromosome and mutate each weight dependent
        //on the mutation rate
        double temp;
        for (int i = 0; i < chromosomes.size(); i++)
        {
            //do we perturb this weight?
            if (Math.random() < m_dMutationRate)
            {
                //add or subtract a small value to the weight
                temp = chromosomes.get(i);
                temp += (Math.random() * dMaxPerturbation);
                chromosomes.set(i, temp);

            }
        }
    }

    //----------------------------------GetChromoRoulette()------------------
    //
    //	returns a chromo based on roulette wheel sampling
    //
    //-----------------------------------------------------------------------
    public Chromosome getChromoRoulette()
    {
        //generate a random number between 0 & total fitness count
        double slice = (double) (Math.random() * m_dTotalFitness);

        //this will be set to the chosen chromosome
        Chromosome theChosenOne = null;

        //go through the chromosones adding up the fitness so far
        double fitnessSoFar = 0;

        for (int i = 0; i < m_iPopSize; ++i)
        {
            fitnessSoFar += chromosomes.get(i).getFitness();

            //if the fitness so far > random number return the chromo at 
            //this point
            if (fitnessSoFar >= slice)
            {
                theChosenOne = chromosomes.get(i);

                break;
            }

        }

        return theChosenOne;
    }

    //-------------------------------------Crossover()-----------------------
    //	
    //  given parents and storage for the offspring this method performs
    //	crossover according to the GAs crossover rate
    //-----------------------------------------------------------------------
    public List<Double>[] crossover(List<Double> mum, List<Double> dad)
    {
        List<Double> baby1 = new ArrayList<>();
        List<Double> baby2 = new ArrayList<>();
        List<Double>[] array = new ArrayList[2];
        //just return parents as offspring dependent on the rate
        //or if parents are the same
        if ((Math.random() > m_dCrossoverRate) || comareLists(mum, dad))
        {
            baby1 = new ArrayList<>(mum);
            baby2 = new ArrayList<>(dad);
            array[0] = baby1;
            array[1] = baby2;
            return array;
        }

        Random randomGenerator = new Random();
        //determine a crossover point
        int cp = randomGenerator.nextInt(m_iChromoLength - 1);

        //create the offspring
        for (int i = 0; i < cp; ++i)
        {
            baby1.add(mum.get(i));
            baby2.add(dad.get(i));
        }

        for (int i = cp; i < mum.size(); ++i)
        {
            baby1.add(dad.get(i));
            baby2.add(mum.get(i));
        }
        array[0] = baby1;
        array[1] = baby2;
        return array;
    }

    //-----------------------------------Epoch()-----------------------------
    //
    //	takes a population of chromosones and runs the algorithm through one
    //	 cycle.
    //	Returns a new population of chromosones.
    //
    //-----------------------------------------------------------------------
    public List<Chromosome> epoch(List<Chromosome> old_pop)
    {
        //assign the given population to the classes population
        chromosomes = new ArrayList<>(old_pop);

        //reset the appropriate variables
        reset();

        //sort the population (for scaling and elitism)
        //sort fish first
        Collections.sort(chromosomes, new Comparator<Chromosome>()
        {
            @Override
            public int compare(Chromosome chromo1, Chromosome chromo2)
            {

                return Double.compare(chromo2.getFitness(), chromo1.getFitness());
            }
        });//Sorted

        //calculate best, worst, average and total fitness
        calculateBestWorstAvTot();

        //create a temporary vector to store new chromosones
        List<Chromosome> vecNewPop = new ArrayList<>();

        //Now to add a little elitism we shall add in some copies of the
        //fittest genomes. Make sure we add an EVEN number or the roulette
        //wheel sampling will crash
        grabNBest(4, 1, vecNewPop);

        //now we enter the GA loop
        //repeat until a new population is generated
        while (vecNewPop.size() < m_iPopSize)
        {
            //grab two chromosones
            Chromosome mum = getChromoRoulette();
            Chromosome dad = getChromoRoulette();

            //create some offspring via crossover
            List<Double> baby1 , baby2 ;

            List<Double>[]  array = crossover(mum.getWeights(), dad.getWeights());
            baby1= array[0];
            baby2= array[1];
            //now we mutate
            mutate(baby1);
            mutate(baby2);

            //now copy into vecNewPop population
            vecNewPop.add(new Chromosome(0, baby1));
            vecNewPop.add(new Chromosome(0, baby2));
        }

        //finished so assign new pop back into m_vecPop
        chromosomes = new ArrayList<>(vecNewPop);

        return chromosomes;
    }

    //-------------------------GrabNBest----------------------------------
    //
    //	This works like an advanced form of elitism by inserting NumCopies
    //  copies of the NBest most fittest genomes into a population vector
    //--------------------------------------------------------------------
    public void grabNBest(int nBest, int numCopies, List<Chromosome> pop)
    {
        //add the required amount of copies of the n most fittest 
        //to the supplied vector
        while ((nBest--) != 0)
        {
            for (int i = 0; i < numCopies; ++i)
            {
                pop.add(chromosomes.get((m_iPopSize - 1) - nBest));
            }
        }
    }

//-----------------------CalculateBestWorstAvTot-----------------------	
//
//	calculates the fittest and weakest genome and the average/total 
//	fitness scores
//---------------------------------------------------------------------
    public void calculateBestWorstAvTot()
    {
        m_dTotalFitness = 0;

        double highestSoFar = 0;
        double lowestSoFar = 9999999;

        for (int i = 0; i < m_iPopSize; ++i)
        {
            //update fittest if necessary
            if (chromosomes.get(i).getFitness() > highestSoFar)
            {
                highestSoFar = chromosomes.get(i).getFitness();

                m_iFittestGenome = i;

                m_dBestFitness = highestSoFar;
            }

            //update worst if necessary
            if (chromosomes.get(i).getFitness() < lowestSoFar)
            {
                lowestSoFar = chromosomes.get(i).getFitness();

                m_dWorstFitness = lowestSoFar;
            }

            m_dTotalFitness += chromosomes.get(i).getFitness();

        }//next chromo

        m_dAverageFitness = m_dTotalFitness / m_iPopSize;
    }

    //-------------------------Reset()------------------------------
    //
    //	resets all the relevant variables ready for a new generation
    //--------------------------------------------------------------
    public void reset()
    {
        m_dTotalFitness = 0;
        m_dBestFitness = 0;
        m_dWorstFitness = 9999999;
        m_dAverageFitness = 0;
    }

    public List<Chromosome> getChromosomes()
    {
        return chromosomes;
    }

    public void setChromosomes(List<Chromosome> chromosomes)
    {
        this.chromosomes = chromosomes;
    }

    public int getM_iPopSize()
    {
        return m_iPopSize;
    }

    public void setM_iPopSize(int m_iPopSize)
    {
        this.m_iPopSize = m_iPopSize;
    }

    public int getM_iChromoLength()
    {
        return m_iChromoLength;
    }

    public void setM_iChromoLength(int m_iChromoLength)
    {
        this.m_iChromoLength = m_iChromoLength;
    }

    public double getM_dTotalFitness()
    {
        return m_dTotalFitness;
    }

    public void setM_dTotalFitness(double m_dTotalFitness)
    {
        this.m_dTotalFitness = m_dTotalFitness;
    }

    public double getM_dBestFitness()
    {
        return m_dBestFitness;
    }

    public void setM_dBestFitness(double m_dBestFitness)
    {
        this.m_dBestFitness = m_dBestFitness;
    }

    public double getM_dAverageFitness()
    {
        return m_dAverageFitness;
    }

    public void setM_dAverageFitness(double m_dAverageFitness)
    {
        this.m_dAverageFitness = m_dAverageFitness;
    }

    public double getM_dWorstFitness()
    {
        return m_dWorstFitness;
    }

    public void setM_dWorstFitness(double m_dWorstFitness)
    {
        this.m_dWorstFitness = m_dWorstFitness;
    }

    public int getM_iFittestGenome()
    {
        return m_iFittestGenome;
    }

    public void setM_iFittestGenome(int m_iFittestGenome)
    {
        this.m_iFittestGenome = m_iFittestGenome;
    }

    public double getM_dMutationRate()
    {
        return m_dMutationRate;
    }

    public void setM_dMutationRate(double m_dMutationRate)
    {
        this.m_dMutationRate = m_dMutationRate;
    }

    public double getM_dCrossoverRate()
    {
        return m_dCrossoverRate;
    }

    public void setM_dCrossoverRate(double m_dCrossoverRate)
    {
        this.m_dCrossoverRate = m_dCrossoverRate;
    }

    public int getM_cGeneration()
    {
        return m_cGeneration;
    }

    public void setM_cGeneration(int m_cGeneration)
    {
        this.m_cGeneration = m_cGeneration;
    }

    public double averageFitness()
    {
        return m_dTotalFitness / m_iPopSize;
    }

    public double bestFitness()
    {
        return m_dBestFitness;
    }

    private boolean comareLists(List<Double> list1, List<Double> list2)
    {
        boolean equals = false;
        if (list1.size() != list2.size())
        {
            return false;
        }

        for (int i = 0; i < list1.size(); i++)
        {
            equals = list1.get(i) == list2.get(i);
        }

        return equals;
    }
}
