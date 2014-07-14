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
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import org.joge.core.draw.Graphics;
import org.joge.core.draw.font.Font;
import org.joge.game.Game;

/**
 *
 * @author Moncef YABI
 */
public class GAGame extends Game
{

    private long oldeTime;
    private final ArrayList<Fish> fishList = new ArrayList<>();
    private final ArrayList<Food> foodList = new ArrayList<>();
    private int bestScore = 0;
    private boolean nextGen = false;
    private int genartionNumber = 0;
    private final Random randomGenerator = new Random();
    private final int avg = 40;

    public static void main(String[] args)
    {
        GAGame game = new GAGame();
        game.start(640, 640, false, "Evolutionary  Game");

//        int total = 1000;
//        int mid = (total / 2)+1;
//
//        Random randomGenerator = new Random();
//        for (int i = mid; i < total; i++)
//        {
//            int x = randomGenerator.nextInt(total);
//            x = x < mid ? x : randomGenerator.nextInt(mid);
//            System.out.println(x);
//            x = randomGenerator.nextInt(total);
//            x = x < mid ? x + mid - 2 : x;
//
//            System.out.println(x);
//            System.out.println("----");
//            if (x >= 1000)
//            {
//                System.out.println("*********************************");
//                return;
//            }
//        }
    }

    @Override
    protected void init()
    {
        this.setFont(new Font(new java.awt.Font("Verdana", java.awt.Font.BOLD, 12), true));
        for (int j = 0; j < avg*2; j++)
        {
            creatFood();
        }
        creatFish(avg);
        
        oldeTime = getCurrentTime();
    }
    long timeInS = 0;
    long time = 0;
    long count = 0;

    @Override
    public void render(Graphics g)
    {
        for (Fish fish : fishList)
        {
            fish.render(g);
            if (fish.eat())
            {
                bestScore = (fish.score() > bestScore) ? fish.score() : bestScore;
                count++;
            }
        }

        for (Food food : foodList)
        {
            food.render(g);
        }

        long t = (getCurrentTime() - oldeTime);
        if (t >= 1000)
        {
            timeInS++;
            oldeTime = getCurrentTime();

        }
        g.drawString("Best score: " + bestScore,  10, 590);
        g.drawString("Count: " + count,  10, 600);
        g.drawString("Genration: " + genartionNumber,  10, 610);
        g.drawString("Time left: " + timeInS + " s",  10, 620);

        if (timeInS == 40)
        {
            nextGen();
            nextGen = false;
            timeInS = 0;
            bestScore = 0;
            count = 0;
        }

    }

    @Override
    protected void run(long elapsedTime)
    {
        //chaotiches Verhalten
        if ((++time) % foodList.size() == 0)
        {
            for (Fish fish : fishList)
            {
                for (int j = 0; j < foodList.size(); j++)
                {

                    int x = randomGenerator.nextInt(foodList.size() - 1);
                    fish.setTarget(foodList.get(x));
                }
            }
        }
    }

    private void nextGen()
    {
        //sort fish first

        Collections.sort(fishList, new Comparator<Fish>()
        {
            @Override
            public int compare(Fish fish1, Fish fish2)
            {

                return Integer.compare(fish2.score(), fish1.score());
            }
        });//Sorted

        //Remove hungry fish
        for (int i = 0; i < fishList.size(); i++)
        {
            if(fishList.get(i).score()==bestScore)
            {
                System.out.println("Generation: "+genartionNumber );
                System.out.println("Score: "+bestScore );
                System.out.println("Brain: "+fishList.get(i).getBrain().toString() );
            }
//            else if (fishList.get(i).score() == 0)
//            {
//                Fish fish = creatOneFish();
//
//                for (Food food : foodList)
//                {
//                    fish.setTarget(food);
//                }
//                fishList.set(i, fish);
//            }
        }
        for (Fish fish : fishList)
        {
            fish.resetPosition();
            fish.age++;
            fish.resetScore();
        }

        int total = fishList.size();
        int mid = (total / 2) + 1;

        for (int i = mid; i < total; i++)
        {
            
            int x = randomGenerator.nextInt(mid/2);
            Brain brain1 = fishList.get(x).getBrain();
            x = randomGenerator.nextInt(mid);
            Brain brain2 = fishList.get(x).getBrain();
            fishList.get(i).replaceBrain(brain1.crossover(brain2));
            fishList.get(i).age = 0;
//            int x = randomGenerator.nextInt(total);
//            x = x < mid ? x : randomGenerator.nextInt(mid);
//            Brain brain1 = fishList.get(x).getBrain();
//            x = randomGenerator.nextInt(total);
//            x = x < mid ? x + mid - 2 : x;
//            Brain brain2 = fishList.get(x).getBrain();
//            fishList.get(i).replaceBrain(brain1.operator(brain2));
//            fishList.get(i).age = 0;

        }
        genartionNumber++;
    }

    private void creatFish(int num)
    {

        for (int j = 0; j < num; j++)
        {
            Fish fish = creatOneFish();

            fishList.add(fish);
            for (Food food : foodList)
            {
                fish.setTarget(food);
            }
        }

    }

    private Fish creatOneFish()
    {
        Fish fish;
        Brain brain;
        brain = new Brain();
        fish = new Fish(brain);
        return fish;
    }

    private void creatFood()
    {
        Food food;
        food = new Food();
        foodList.add(food);
    }

}
