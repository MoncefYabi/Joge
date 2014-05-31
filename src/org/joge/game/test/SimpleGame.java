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
import org.joge.core.draw.Image;
import org.joge.core.draw.font.Font;
import org.joge.core.draw.tilemap.TiledMap;
import org.joge.core.input.JKeyboard;
import org.joge.game.sprite.FSprite;
import org.joge.core.tools.ToolKit;

/**
 *
 * @author Moncef YABI
 */
public class SimpleGame extends Game
{

    private Font font;
    private FSprite personne = null;
    private TiledMap map;
    private boolean[][] allowed;
    private int x, y;
    private final int SIZE=16;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        SimpleGame run = new SimpleGame();
        run.start(640, 640, false, "My Game");
    }

    @Override
    protected void init()
    {
        font = new Font(new java.awt.Font("Verdana", java.awt.Font.BOLD, 14), true);

        personne = ToolKit.loadFSprit("properties/fsprite2.yml");
        map = new TiledMap("tile/level1.tmx");
        int layerIndex = map.getLayerIndex("Wege");

        allowed = new boolean[map.getWidth()][map.getHeight()];
        for (int xAxis = 0; xAxis < map.getWidth(); xAxis++)
        {
            for (int yAxis = 0; yAxis < map.getHeight(); yAxis++)
            {
                int tileID = map.getTileId(xAxis, yAxis, layerIndex);
                allowed[xAxis][yAxis] = (tileID != 0);

            }
        }
    }

    @Override
    protected void run(long elapsedTime)
    {
        float speed = 0.07f * elapsedTime;

        if (JKeyboard.isKeyDown(JKeyboard.KEY_UP))
        {
            if (isAllowed(x, y-speed))
            {
                personne.moveY(-speed);
            }

            personne.setActiveAnimation("up");
            personne.getActiveAnimation().setLoop(true);
        } else if (JKeyboard.isKeyDown(JKeyboard.KEY_DOWN))
        {
            if (isAllowed(x, y+SIZE+speed))
            {
                personne.moveY(speed);
            }
             
            personne.setActiveAnimation("down");
            personne.getActiveAnimation().setLoop(true);
        } else if (JKeyboard.isKeyDown(JKeyboard.KEY_LEFT))
        {
            if (isAllowed(x-speed, y))
            {
                personne.moveX(-speed);
            }
            personne.setActiveAnimation("right");
            personne.getActiveAnimation().setLoop(true);
        } else if (JKeyboard.isKeyDown(JKeyboard.KEY_RIGHT))
        {
            if (isAllowed(x+SIZE+speed, y))
            {
                personne.moveX(speed);
            }
            personne.setActiveAnimation("left");
            personne.getActiveAnimation().setLoop(true);
        } else
        {
            personne.getActiveAnimation().setStoped(true);
            personne.getActiveAnimation().setLoop(false);
        }
        
        
        x = (int) personne.getActiveAnimation().getXpos();
        y = (int) personne.getActiveAnimation().getYpos();
        x = x >= 0 && x < 640 ? x : -1;
        y = y >= 0 && y < 640 ? y : -1;
    }

    @Override
    public void render(Graphics g)
    {
        this.clearScreen();
        map.render(0, 0);
        personne.render(g);

        g.setColor(Color.BLUE);
        String msg = "FPS: " + this.getFPS();
        g.drawString(msg, font, 10, 550);
        g.drawString("isAllowed " + isAllowed(x, y), font, 10, 530);
        g.drawString("x: " + x + " y: " + y, font, 10, 500);

    }

    private boolean isAllowed(float x, float y)
    {
        if (x == -1 || y == -1)
        {
            return false;
        }
        int xBlock = (int) x / SIZE;
        int yBlock = (int) y / SIZE;
        return allowed[xBlock][yBlock];
    }
}
