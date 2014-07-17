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

import org.joge.core.ai.genetic.Chromosome;
import java.util.ArrayList;
import java.util.List;
import org.joge.core.ai.config.NeuralNetConfig;

/**
 *
 * @author Moncef YABI
 */
public class Brain
{

    private int numInputs;
    private int numOutputs;
    private int numHiddenLayers;
    private int neuronsPerHiddenLyr;
    private Chromosome chromosome;
    private List<NeuronLayer> layers = new ArrayList<>();

    public Brain()
    {
        this(NeuralNetConfig.numInputs, NeuralNetConfig.numOutputs, NeuralNetConfig.numHiddenLayers, NeuralNetConfig.neuronsPerHiddenLyr);
    }

    public Brain(int numInputs, int numOutputs, int numHiddenLayers, int neuronsPerHiddenLyr)
    {
        this.numInputs = numInputs;
        this.numOutputs = numOutputs;
        this.numHiddenLayers = numHiddenLayers;
        this.neuronsPerHiddenLyr = neuronsPerHiddenLyr;
        createNetwork();
        chromosome= new Chromosome(0,getAllWeights());
    }

    private void createNetwork()
    {
        //create the layers of the network
        if (numHiddenLayers > 0)
        {
            //create first hidden layer
            layers.add(new NeuronLayer(neuronsPerHiddenLyr, numInputs));

            for (int i = 0; i < numHiddenLayers - 1; ++i)
            {

                layers.add(new NeuronLayer(neuronsPerHiddenLyr, neuronsPerHiddenLyr));
            }

            //create output layer
            layers.add(new NeuronLayer(numOutputs, neuronsPerHiddenLyr));
        } else
        {
            //create output layer
            layers.add(new NeuronLayer(numOutputs, numInputs));
        }
    }

    public final List<Double> getAllWeights()
    {
        List<Double> weights = new ArrayList<>();
        //for each layer
        for (int i = 0; i < numHiddenLayers + 1; ++i)
        {

            //for each neuron
            for(Neuron neuron : layers.get(i).getNeurons())
            {
                //for each weight
                for (int k = 0; k < neuron.getNumInputs(); ++k)
                {
                    weights.add(neuron.getWeights().get(k));
                }
            }
        }
        return weights;
    }

    public void replaceWeights(List<Double> weights)
    {
        int cWeight = 0;
        //for each layer
        for (int i = 0; i < numHiddenLayers + 1; ++i)
        {

            //for each neuron
            for(Neuron neuron : layers.get(i).getNeurons())
            {
                //for each weight
                for (int k = 0; k < neuron.getNumInputs(); ++k)
                {
                    neuron.getWeights().set(k, weights.get(cWeight++));
                }
            }
        }
    }

    public int getNumberOfWeights()
    {

        int weights = 0;

        //for each layer
        for (int i = 0; i < numHiddenLayers + 1; ++i)
        {
            //for each neuron
            for(Neuron neuron : layers.get(i).getNeurons())
            //for (int j = 0; j < layers.get(i).getNeurons().size(); ++j)
            {
                //for each weight
                for (int k = 0; k < neuron.getNumInputs(); ++k)

                {
                    weights++;
                }
            }
        }

        return weights;
    }

    public List<Double> update(List<Double> inputs)
    {
        //stores the resultant outputs from each layer
        List<Double> outputs = new ArrayList<>();

        //first check that we have the correct amount of inputs

        //For each layer....
        for (int i = 0; i < numHiddenLayers + 1; ++i)
        {
            if (i > 0)
            {
                inputs = new ArrayList<>(outputs);
            }

            outputs.clear();

            //for each neuron sum the (inputs * corresponding weights).Throw 
            //the total at our sigmoid function to get the output.
            for(Neuron neuron : layers.get(i).getNeurons())
            {
                outputs.add(neuron.fire(inputs));
            }
        }

        return outputs;
    }

    public Chromosome getChromosome()
    {
        return chromosome;
    }

    public void setChromosome(Chromosome chromosome)
    {
        this.chromosome = chromosome;
        replaceWeights(chromosome.getWeights());
    }
    
    public int getNumInputs()
    {
        return numInputs;
    }

    public void setNumInputs(int numInputs)
    {
        this.numInputs = numInputs;
    }

    public int getNumOutputs()
    {
        return numOutputs;
    }

    public void setNumOutputs(int numOutputs)
    {
        this.numOutputs = numOutputs;
    }

    public int getNumHiddenLayers()
    {
        return numHiddenLayers;
    }

    public void setNumHiddenLayers(int numHiddenLayers)
    {
        this.numHiddenLayers = numHiddenLayers;
    }

    public int getNeuronsPerHiddenLyr()
    {
        return neuronsPerHiddenLyr;
    }

    public void setNeuronsPerHiddenLyr(int neuronsPerHiddenLyr)
    {
        this.neuronsPerHiddenLyr = neuronsPerHiddenLyr;
    }

    public List<NeuronLayer> getLayers()
    {
        return layers;
    }

    public void setLayers(List<NeuronLayer> layers)
    {
        this.layers = layers;
    }

    @Override
    public String toString()
    {
        return "Brain{" + "chromosome=" + chromosome + '}';
    }
}
