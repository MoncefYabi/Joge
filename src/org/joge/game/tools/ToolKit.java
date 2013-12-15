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
package org.joge.game.tools;

import java.io.IOException;
import org.joge.game.j2d.Texture;
import org.joge.game.j2d.TextureLoader;

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
        } catch (IOException e)
        {
            System.err.println("Kann die Datei: " + ref + " nicht finden");
        }
        return texture;
    }

}
