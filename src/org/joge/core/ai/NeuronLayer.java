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
public class NeuronLayer
{    
    private List<Neuron> neurons = new ArrayList<>();

    public NeuronLayer()
    {
    }
    
    public NeuronLayer(int numNeurons,int numInputsPerNeuron)
    {
        for (int i = 0; i < numNeurons; i++)
        {
            neurons.add(new Neuron(numInputsPerNeuron));
        }
    }
    
    public List<Neuron> getNeurons()
    {
        return neurons;
    }

    public void setNeurons(List<Neuron> neurons)
    {
        this.neurons = neurons;
    }
}
