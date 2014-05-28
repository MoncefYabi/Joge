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
package org.joge.core.sound;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joge.core.tools.ToolKit;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;

/**
 *
 * @author Moncef YABI
 */
public class JogMedia
{

    private Audio audioFile = null;

    public JogMedia(String ref)
    {

        try
        {
            if (ref.endsWith("ogg"))
            {

                audioFile = AudioLoader.getAudio("OGG", ToolKit.getResourceAsStream(ref));
            } else if (ref.endsWith("wav"))
            {
                audioFile = AudioLoader.getAudio("WAV", ToolKit.getResourceAsStream(ref));
            } else
            {
                System.out.println("Audio Error: File format not suported!");
            }
        } catch (IOException ex)
        {
            Logger.getLogger(JogMedia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playMusic(boolean loop)
    {
        audioFile.playAsMusic(1f, 1f, loop);
    }

    public void playEffect(boolean loop)
    {
        audioFile.playAsSoundEffect(1f, 1f, loop);
    }

    public void stop()
    {
        audioFile.stop();
    }

    public void deletSource()
    {
        audioFile.stop();
    }

    public boolean isPlaying()
    {
        return audioFile.isPlaying();
    }

}
