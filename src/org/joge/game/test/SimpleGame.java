/*
 * Copyright (C) 2013 Moncef YABI
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

import org.joge.game.Game;
import org.joge.core.draw.Color;
import org.joge.core.draw.Graphics;
import org.joge.core.draw.font.Font;
import org.joge.game.sprite.Sprite;
import static org.lwjgl.input.Keyboard.*;

/**
 *
 * @author Moncef YABI
 */
public class SimpleGame extends Game
{

    private float xG = 0, yG = 0;
    
    private Font font;
    private Sprite sp= null;
    private long time=0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        SimpleGame run = new SimpleGame();
        run.start(800, 600, false, "My Game");
    }

    @Override
    protected void init()
    {
        sp = new Sprite("images/boss.gif");
        font = new Font(new java.awt.Font("Verdana", java.awt.Font.BOLD, 14), true);
   
    }

    @Override
    protected void run(long elapsedTime)
    {
        time = elapsedTime;
        if (isKeyDown(KEY_LEFT))
        {
            xG -= 0.1*time;
        }

        if (isKeyDown(KEY_RIGHT))
        {
            xG += 0.1*time;
        }

        if (isKeyDown(KEY_UP))
        {
            yG -= 0.1*time;
        }

        if (isKeyDown(KEY_DOWN))
        {
            yG += 0.1*time;
        }

        if (isKeyDown(KEY_ESCAPE))
        {
            end();
        }
        sp.setX(xG);
        sp.setY(yG);
        
    }
    
    
    @Override
    public void render(Graphics g)
    {
        this.clearScreen();
        g.setColor(Color.BLUE);
        sp.render(g);
        String msg = "FPS: "+this.getFPS()+ "x: "+xG+" y:"+ yG;
        g.drawString(msg, font,10, 550);
        
        g.drawString("time: "+time, font, 200, 200);
    }
}
