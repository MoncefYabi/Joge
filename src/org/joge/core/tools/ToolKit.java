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
package org.joge.core.tools;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joge.core.draw.Image;
import org.joge.core.draw.texture.Texture;
import org.joge.core.draw.texture.TextureLoader;
import org.joge.game.animation.Frame;
import org.joge.game.sprite.ControlledFSprite;
import org.joge.game.sprite.FSprite;



/**
 *
 * @author Moncef YABI
 */
public abstract class ToolKit
{

    private static Texture texture;

    public static Texture getTexture(String ref)
    {
        try
        {
            texture = TextureLoader.get().getTexture(ref);
        } catch (IOException ex)
        {
            Logger.getLogger(ToolKit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return texture;
    }

    public static Image[] calculateFrames(Image image, int spalten, int zeilen)
    {
        Image images[] = new Image[spalten * zeilen];
        int xi = image.getWidth() / spalten;
        int yi = image.getHeight() / zeilen;
        int z = -1;
        for (int i = 0; i < zeilen; i++)
        {
            for (int j = 0; j < spalten; j++)
            {
                z++;
                if (z < spalten * zeilen)
                {
                    images[z] = image.getSubImage(j * xi, i * yi, xi, yi);
                }
            }
        }
        return images;
    }

    public static URL getResource(String ref)
    {
        return ToolKit.class.getClassLoader().getResource(ref);
    }

    public static InputStream getResourceAsStream(String ref)
    {
        InputStream in = ToolKit.class.getClassLoader().getResourceAsStream(ref);

        if (in == null)
        {
            throw new RuntimeException("Resource not found: " + ref);
        }

        return new BufferedInputStream(in);
    }

    public static FSprite loadFSprit(String ref)
    {
        FSprite animation = null;
        try
        {
            URL url = ToolKit.getResource(ref);
            YamlReader reader = new YamlReader(new FileReader(url.getFile()));
            reader.getConfig().setPropertyElementType(FSprite.class, "frames", Frame.class);
            reader.getConfig().setPropertyElementType(FSprite.class, "animationKeys", Integer.class);
            animation = (FSprite) reader.read(FSprite.class);

            animation.init();

        } catch (YamlException | FileNotFoundException ex)
        {
            Logger.getLogger(ToolKit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return animation;
    }
    public static  ControlledFSprite loadControlledFSprite(String ref)
    {
         ControlledFSprite animation = null;
        try
        {
            URL url = ToolKit.getResource(ref);
            YamlReader reader = new YamlReader(new FileReader(url.getFile()));
            reader.getConfig().setPropertyElementType(ControlledFSprite.class, "frames", Frame.class);
            reader.getConfig().setPropertyElementType(ControlledFSprite.class, "animationKeys", Integer.class);
            animation = ( ControlledFSprite) reader.read(ControlledFSprite.class);

            animation.init();

        } catch (YamlException | FileNotFoundException ex)
        {
            Logger.getLogger(ToolKit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return animation;
    }

}
