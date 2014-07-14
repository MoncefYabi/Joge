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
public class Brain
{

    private int numInputs;
    private int numOutputs;
    private int numHiddenLayers;
    private int neuronsPerHiddenLyr;

    private List<NeuronLayer> layers = new ArrayList<>();

    public Brain()
    {
        this(4, 2, 1, 6);
    }

    public Brain(int numInputs, int numOutputs, int numHiddenLayers, int neuronsPerHiddenLyr)
    {
        this.numInputs = numInputs;
        this.numOutputs = numOutputs;
        this.numHiddenLayers = numHiddenLayers;
        this.neuronsPerHiddenLyr = neuronsPerHiddenLyr;

        createNet();
    }

    private void createNet()
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

    public List<Double> getWeights()
    {
        List<Double> weights = new ArrayList<>();
        //for each layer
        for (int i = 0; i < numHiddenLayers + 1; ++i)
        {

            //for each neuron
            for (int j = 0; j < layers.get(i).getNeurons().size(); ++j)
            {
                //for each weight
                for (int k = 0; k < layers.get(i).getNeurons().get(j).getNumInputs(); ++k)
                {
                    weights.add(layers.get(i).getNeurons().get(j).getWeights().get(k));
                }
            }
        }
        return weights;
    }

    public void putWeights(List<Double> weights)
    {
        int cWeight = 0;

        //for each layer
        for (int i = 0; i < numHiddenLayers + 1; ++i)
        {

            //for each neuron
            for (int j = 0; j < layers.get(i).getNeurons().size(); ++j)
            {
                //for each weight
                for (int k = 0; k < layers.get(i).getNeurons().get(j).getNumInputs(); ++k)
                {
                    layers.get(i).getNeurons().get(j).getWeights().set(k, weights.get(cWeight++));
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
            for (int j = 0; j < layers.get(i).getNeurons().size(); ++j)
            {
                //for each weight
                for (int k = 0; k < layers.get(i).getNeurons().get(j).getNumInputs(); ++k)

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

        int cWeight = 0;

        //first check that we have the correct amount of inputs
        if (inputs.size() != numInputs)
        {
            //just return an empty vector if incorrect.
            return outputs;
        }

        //For each layer....
        for (int i = 0; i < numHiddenLayers + 1; ++i)
        {
            if (i > 0)
            {
                inputs = outputs;
            }

            outputs.clear();

            cWeight = 0;

		//for each neuron sum the (inputs * corresponding weights).Throw 
            //the total at our sigmoid function to get the output.
            for (int j = 0; j < layers.get(i).getNeurons().size(); ++j)
            {
                double netinput = 0;

                int NumInputs = layers.get(i).getNeurons().get(j).getNumInputs();

                //for each weight
                for (int k = 0; k < NumInputs - 1; ++k)
                {
                    //sum the weights x inputs
                    netinput += layers.get(i).getNeurons().get(j).getWeights().get(k)
                            * inputs.get(cWeight++);
                }

                //add in the bias
                netinput += layers.get(i).getNeurons().get(j).getWeights().get(NumInputs - 1) * (-1);

			//we can store the outputs from each layer as we generate them. 
                //The combined activation is first filtered through the sigmoid 
                //function
                outputs.add(sigmoid(netinput, 1));

                cWeight = 0;
            }
        }

        return outputs;
    }

    public double sigmoid(double netinput, double response)
    {
        return (1 / (1 + Math.exp(-netinput / response)));
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

}
