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
    private Image image;

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

//        JogMedia media2= new JogMedia("data/Kevtech.ogg");
//        media2.playMusic(false);
    }

    @Override
    protected void run(long elapsedTime)
    {
        float t = 0.07f * elapsedTime;
        if (JKeyboard.isKeyDown(JKeyboard.KEY_UP))
        {
            personne.moveY(-t);
            
            personne.setActiveAnimation("1");
            personne.getActiveAnimation().setLoop(true);
        } else if (JKeyboard.isKeyDown(JKeyboard.KEY_DOWN))
        {
            personne.moveY(t);
            personne.setActiveAnimation("2");
            personne.getActiveAnimation().setLoop(true);
        } else if (JKeyboard.isKeyDown(JKeyboard.KEY_LEFT))
        {
            personne.moveX(-t);
            personne.setActiveAnimation("3");
            personne.getActiveAnimation().setLoop(true);
        } else if (JKeyboard.isKeyDown(JKeyboard.KEY_RIGHT))
        {
            personne.moveX(t);
            personne.setActiveAnimation("4");
            personne.getActiveAnimation().setLoop(true);
        } else
        {
            personne.getActiveAnimation().setStoped(true);
            personne.getActiveAnimation().setLoop(false);
        }
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

    }
}
