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

import java.util.Arrays;
import org.joge.core.draw.Graphics;
import org.joge.core.draw.font.Font;
import org.joge.core.input.JKeyboard;
import org.joge.game.Game;

/**
 *
 * @author dell
 */
public class FishGame extends Game
{

    private Fish fish;
    private Fish fish2;
    private Fish fish3;
    private Fish fish4;
    private Food food;
    private Food food2;
    private Food food3;
    private Food food4;

    public static void main(String[] args)
    {
        FishGame game = new FishGame();
        game.start(640, 640, false, "Evolutionary  Fish");
    }

    @Override
    public void render(Graphics g)
    {
        fish.render(g);
        fish2.render(g);
        fish3.render(g);
        fish4.render(g);
        food.render(g);
        food2.render(g);
        food3.render(g);
        food4.render(g);
        g.drawString("(" + food.x + ", " + food.y + ")", food.x + 12, food.y + 12);

        g.drawString("Mutation: " + Brain.mutateRate(), 10, 620);

    }

    double speed=2;
    @Override
    protected void run(long elapsedTime)
    {
        fish.eat();
        fish2.eat();
        fish3.eat();
        fish4.eat();

        if (JKeyboard.isKeyDown(JKeyboard.KEY_UP))
        {
           food.y-=speed;
        } else if (JKeyboard.isKeyDown(JKeyboard.KEY_DOWN))
        {
           food.y+=speed;
        } else if (JKeyboard.isKeyDown(JKeyboard.KEY_LEFT))
        {
            food.x-=speed;
        } else if (JKeyboard.isKeyDown(JKeyboard.KEY_RIGHT))
        {
            food.x+=speed;
        }
    }

    @Override
    protected void init()
    {
        food = new Food();
        food2 = new Food();
        food3 = new Food();
        food4 = new Food();
        fish = creatOneFish(food, creatBrain());
        fish2 = creatOneFish(food2, creatBrain2());
        fish3 = creatOneFish(food3, creatBrain3());
        fish4 = creatOneFish(food4, creatBrain2());

        fish3.setName("Fish 3");
        fish.setName("Fish 1");
        fish2.setName("Fish 2");
        fish4.setName("Fish 4");
//        
//        Brain brain1 = fish.getBrain();
//        Brain brain2 = fish3.getBrain();
//        fish3.replaceBrain(brain2.crossover(brain1));
        
        fish4.replaceBrain(fish3.getBrain().crossover(fish4.getBrain()));
        System.out.println("fish1" + fish.getBrain().toString());
        System.out.println("fish2" + fish2.getBrain().toString());
        System.out.println("fish3" + fish3.getBrain().toString());
        System.out.println("fish4" + fish4.getBrain().toString());
        this.setFont(new Font(new java.awt.Font("Verdana", java.awt.Font.BOLD, 10), true));
    }

    private Fish creatOneFish(Food food, Brain brain)
    {
        Fish fishy;

        fishy = new Fish(brain);
        fishy.setTarget(food);
        return fishy;
    }

    private Brain creatBrain()// Score 25
    {
        Perceptron leftController = new Perceptron();
        Perceptron rightController = new Perceptron();
        Perceptron speedController = new Perceptron();
        Perceptron foodEvaluator = new Perceptron();

        leftController.getWeights().addAll(Arrays.asList(new Double[]
        {
            -0.5950473440088472, 0.5449849638437335, 0.2967435588639711
        }));
        rightController.getWeights().addAll(Arrays.asList(new Double[]
        {
            0.14109560394933937, -0.12032665026430048, 0.8294733711075202
        }));
        speedController.getWeights().addAll(Arrays.asList(new Double[]
        {
            -0.4185857519561802, 0.6073725903027603, 0.13151726303440858
        }));
        foodEvaluator.getWeights().addAll(Arrays.asList(new Double[]
        {
            -0.1421883788230327, -0.45215656019982764, 0.17388308197278013
        }));

        return new Brain(leftController, rightController, speedController, foodEvaluator);
    }

    private Brain creatBrain2()//Score 26
    {
        Perceptron leftController = new Perceptron();
        Perceptron rightController = new Perceptron();
        Perceptron speedController = new Perceptron();
        Perceptron foodEvaluator = new Perceptron();

        leftController.getWeights().addAll(Arrays.asList(new Double[]
        {
            -0.442355989558427, -0.24529196098250344, 0.16106813407971424
        }));
        rightController.getWeights().addAll(Arrays.asList(new Double[]
        {
            -0.26055468378101193, -0.9712965419517126, -0.7255424406435583
        }));
        speedController.getWeights().addAll(Arrays.asList(new Double[]
        {
            -0.2055978493562577, 0.42332878004412644, 0.14120403163822295
        }));
        foodEvaluator.getWeights().addAll(Arrays.asList(new Double[]
        {
            0.13864078084769926, -0.09725023422722301, -0.768953763179701
        }));

        return new Brain(leftController, rightController, speedController, foodEvaluator);
    }
    
    private Brain creatBrain3()//Score 27
    {
        Perceptron leftController = new Perceptron();
        Perceptron rightController = new Perceptron();
        Perceptron speedController = new Perceptron();
        Perceptron foodEvaluator = new Perceptron();

        leftController.getWeights().addAll(Arrays.asList(new Double[]
        {
            -0.46253780864491767, -0.19670895456167414, 0.16106813407971424
        }));
        rightController.getWeights().addAll(Arrays.asList(new Double[]
        {
            -0.26068971095623894, -0.9341741253089755, -0.735918321913329
        }));
        speedController.getWeights().addAll(Arrays.asList(new Double[]
        {
            -0.21995677761320287, 0.39334121364943025, 0.11575676245062139
        }));
        foodEvaluator.getWeights().addAll(Arrays.asList(new Double[]
        {
            0.13864078084769926, -0.09725023422722301, -0.768953763179701
        }));

        return new Brain(leftController, rightController, speedController, foodEvaluator);
    }
}
