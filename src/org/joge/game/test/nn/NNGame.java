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

import java.util.ArrayList;
import java.util.List;
import org.joge.core.ai.Brain;
import org.joge.core.ai.Chromosome;
import org.joge.core.ai.GeneticAlgorithm;
import org.joge.core.draw.Graphics;
import org.joge.core.draw.font.Font;
import org.joge.game.Game;
import org.joge.game.test.Food;

/**
 *
 * @author dell
 */
public class NNGame extends Game
{

    ArrayList<Creature> creatures;
    ArrayList<Food> foods = new ArrayList<>();
    GeneticAlgorithm ga;
    List<Chromosome> chromosomes;
    int generation = 0;
    int pop = 20;
    private long oldeTime;
    private int timeInS = 0;
    int bestScore = 0, count = 0;

    public static void main(String[] args)
    {
        NNGame game = new NNGame();
        game.start(640, 640, false, "Evolutionary  NN");
    }

    @Override
    protected void init()
    {
        this.setFont(new Font(new java.awt.Font("Verdana", java.awt.Font.BOLD, 12), true));
//        creatures = new ArrayList<>();
//        ga = new GeneticAlgorithm(pop, 36, 0.1, 0.7);
//        chromosomes = ga.getChromosomes();
//        for (int i = 0; i < pop; i++)
//        {
//            Brain brain = new Brain();
//            Creature creat = new Creature(brain);
//            brain.putWeights(chromosomes.get(i).getWeights());
//
//            creatures.add(creat);
//        }
//        for (int i = 0; i < 40; i++)
//        {
//            foods.add(new Food());
//
//        }
        
        Brain brain = new Brain();
        
        int i=0;
    }

    @Override
    public void render(Graphics g)
    {
//        for (Creature creature : creatures)
//        {
//            creature.render(g);
//            
//             if (creature.eat(foods))
//            {
//                bestScore = (creature.getScore() > bestScore) ? creature.getScore() : bestScore;
//                count++;
//            }
//        }
//
//        //
//        for (Food food : foods)
//        {
//            food.render(g);
//        }
//
//        g.drawString("x:" + creatures.get(0).getX(), 10, 500);
//        g.drawString("y: " + creatures.get(0).getY(), 10, 520);
//        g.drawString("Best Score: " + bestScore, 10, 540);
//        g.drawString("time: " + timeInS + "s", 10, 600);
//        g.drawString("genration: " + generation, 10, 620);
    }

    @Override
    protected void run(long elapsedTime)
    {
//        long t = (getCurrentTime() - oldeTime);
//        if (t >= 1000)
//        {
//            timeInS++;
//            oldeTime = getCurrentTime();
//
//        }
//        if (timeInS == 40)
//        {
//            nextGen();
//            timeInS = 0;
//
//            System.out.println("Generation:" + generation);
//            System.out.println("score:" +bestScore);
//            for (Creature creature : creatures)
//            {
//                if (creature.getScore() == bestScore)
//                {
//                    System.out.println(creature.getBrain().getWeights());
//                }
//                creature.setScore(0);
//            }
//            bestScore = 0;
//            generation++;
//        }
    }

    private void nextGen()
    {

        List<Chromosome> newChromosomes = ga.epoch(chromosomes);
        for (int i = 0; i < creatures.size(); i++)
        {
            creatures.get(i).getBrain().putWeights(newChromosomes.get(i).getWeights());
            creatures.get(i).setRandomPosition();
        }
    }
}
