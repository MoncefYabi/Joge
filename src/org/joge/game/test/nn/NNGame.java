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
package org.joge.game.test.nn;

import org.joge.core.ai.genetic.GenAlgorithm;
import org.joge.core.ai.entity.Creature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joge.core.ai.Brain;
import org.joge.core.draw.Graphics;
import org.joge.core.draw.font.Font;
import org.joge.core.tools.exception.NeuralNetworkException;
import org.joge.game.Game;
import org.joge.core.ai.entity.Food;

/**
 *
 * @author Moncef YABI
 */
public class NNGame extends Game
{

    private int generation = 0;
    private final int pop = 10;
    private final List<Food> foods = new ArrayList<>();
    private List<Creature> creatures = new ArrayList<>();
    private final GenAlgorithm galgo = new GenAlgorithm();
    private long oldeTime;
    private int timeInS = 0;
    private int bestScore = 0, count = 0;
    private final Random randomGenerator = new Random();

    public static void main(String[] args)
    {
        NNGame game = new NNGame();
        game.start(640, 640, false, "Evolutionary  NN");
    }

    @Override
    protected void init()
    {
        this.setFont(new Font(new java.awt.Font("Verdana", java.awt.Font.BOLD, 10), true));
        Creature temp;

        for (int i = 0; i < pop * 2; i++)
        {
            foods.add(new Food());
        }
        for (int i = 0; i < pop; i++)
        {
            temp = new Creature(new Brain());
            temp.setName(i + "");
            creatures.add(temp);
        }
    }

    @Override
    public void render(Graphics g)
    {
        for (Creature creature : creatures)
        {
            creature.render(g);
            if (creature.eat(foods))
            {
                bestScore = (creature.getScore() > bestScore) ? creature.getScore() : bestScore;
                count++;
            }

        }

        for (Food food : foods)
        {
            food.render(g);
        }
        g.drawString("time: " + timeInS + "s", 10, 585);
        g.drawString("Best scoe: " + bestScore, 10, 600);
        g.drawString("Total count: " + count, 10, 615);
        g.drawString("genration: " + generation, 10, 570);
    }

    @Override
    protected void run(long elapsedTime)
    {

        long t = (getCurrentTime() - oldeTime);
        if (t >= 1000)
        {
            timeInS++;
            oldeTime = getCurrentTime();

        }
        if (timeInS == 40)
        {

            nextGen();
        }
    }

    private void nextGen()
    {
        //sort fish first
        List<Creature> offSprings = new ArrayList<>();
        List<Creature> temp;

        Collections.sort(creatures, new Comparator<Creature>()
        {
            @Override
            public int compare(Creature creature1, Creature creature2)
            {
                return Integer.compare(creature1.getScore(), creature2.getScore());
            }
        });//Sorted

        if (creatures.get(creatures.size() - 1).getScore() > 60 && generation > 8)
        {
            for (int i = 0; i < creatures.size(); i++)
            {
                if (creatures.get(i).getScore() < 40)
                {
                    creatures.remove(i);
                }
            }
        } else
        {
            System.out.println("gernation: " + generation);
            System.out.println(creatures.get(creatures.size() - 1).toString());

            int countMid = (creatures.size() / 2) + 1;
            int i = 0;
            while (offSprings.size() < pop)
            {
                int x = randomGenerator.nextInt((creatures.size() - countMid - 1) + 1) + countMid;
                try
                {
                    if (i == x)
                    {
                        if (x == creatures.size() - 1)
                        {
                            x--;
                        } else
                        {
                            x++;
                        }

                    }
                    temp = galgo.crossover(creatures.get(x), creatures.get(i));
                    offSprings.add(temp.get(0));
                    offSprings.add(temp.get(1));
                } catch (NeuralNetworkException ex)
                {
                    Logger.getLogger(NNGame.class.getName()).log(Level.SEVERE, null, ex);
                }
                i++;
            }
            creatures.clear();
            creatures = new ArrayList<>(offSprings);
            bestScore = 0;
            timeInS = count = 0;
            generation++;
        }

    }
}
