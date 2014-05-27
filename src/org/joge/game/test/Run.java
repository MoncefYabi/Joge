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
import org.joge.game.j2d.Color;
import org.joge.game.j2d.Font;
import org.joge.game.j2d.Graphics;
import org.joge.game.sprite.Sprite;
import static org.lwjgl.input.Keyboard.*;

/**
 *
 * @author Moncef YABI
 */
public class Run extends Game
{

    private float xG = 0, yG = 0;
    
    private Font font;
    private Sprite sp= null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Run run = new Run();
        run.start(800, 600, false, "My Game");
    }

    @Override
    protected void init()
    {
        sp = new Sprite("images/boss.gif");
        font= new Font("font/verdana.gif");
        
    }

    @Override
    protected void run(long elapsedTime)
    {
        if (isKeyDown(KEY_LEFT))
        {
            xG -= 4;
        }

        if (isKeyDown(KEY_RIGHT))
        {
            xG += 4;
        }

        if (isKeyDown(KEY_UP))
        {
            yG -= 4;
        }

        if (isKeyDown(KEY_DOWN))
        {
            yG += 4;
        }

        if (isKeyDown(KEY_ESCAPE))
        {
            end();
        }
    }
    
    
    @Override
    public void render(Graphics g)
    {
        this.clearScreen();
        
        g.setColor(Color.BLUE);
        String msg = "FPS: "+this.getFPS()+ "x: "+xG+" y:"+ yG;
        g.drawString(msg, font, xG, yG);     
        g.drawString("Hello THIS IS JOGE", font, 10, 10);     
        sp.render(g);
        
    }
}
