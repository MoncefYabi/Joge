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
package org.joge.game;

import org.joge.game.j2d.Graphics;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Moncef YABI
 */
public abstract class Game
{

    private long lastFPS;
    protected int recordedFPS;
    private int fps;
    public float frameInterval = 0.0f;
    private Graphics g;

    /**
     * Initialize the game
     */
    public void start(int width, int height, boolean fullscreen, String title)
    {
        try
        {
            setDisplayMode(width, height, fullscreen);
            Display.create();
            Display.setTitle(title);
            initGL(width, height);

            g = new Graphics();
            // Start the game
            gameLoop();
        } catch (LWJGLException e)
        {
            e.printStackTrace();
            System.exit(-1); // There is no point in running without hardware acceleration right?
        }


    }

    // The gameloop. Runs at 60 fps
    private void gameLoop()
    {
        long lastFrame = getCurrentTime();
        long thisFrame = getCurrentTime();

        init();

        while (!Display.isCloseRequested())
        {
            clearScreen();
            thisFrame = getCurrentTime();
            calculateFPS();
            run(thisFrame - lastFrame);
            render(g);

            Display.update();

            if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
            {

                end();

            }

            Display.sync(60);

            lastFrame = thisFrame;
        }

        end();
    }

    protected void calculateFPS()
    {
        frameInterval = (getCurrentTime() - lastFPS) / 1000f;
        if (getCurrentTime() - lastFPS > 1000)
        {
            lastFPS = getCurrentTime();
            recordedFPS = fps;
            fps = 0;
        }
        fps++;
    }

    /**
     * Switch the fullscreen state.
     */
    public void switchFullscreen()
    {
        setFullscreen(!Display.isFullscreen());
    }

    /**
     * Sets the fullscreen state.
     */
    public void setFullscreen(boolean fullscreen)
    {
        setDisplayMode(Display.getDisplayMode(), fullscreen);
    }

    /**
     * Sets a DisplayMode.
     *
     * @param mode The DisplayMode.
     * @param fullscreen The fullscreen state.
     */
    public boolean setDisplayMode(DisplayMode mode, boolean fullscreen)
    {
        return setDisplayMode(mode.getWidth(), mode.getHeight(), fullscreen);
    }

    /**
     * Sets a DisplayMode.
     */
    public boolean setDisplayMode(DisplayMode mode)
    {
        return setDisplayMode(mode, false);
    }

    /**
     * Sets a windowed DisplayMode.
     *
     * @param width The width of the display.
     * @param height The height of the display.
     */
    public boolean setDisplayMode(int width, int height)
    {
        return setDisplayMode(width, height, false);
    }

    /**
     * Sets a DisplayMode after selecting for a better one.
     *
     * @param width The width of the display.
     * @param height The height of the display.
     * @param fullscreen The fullscreen mode.
     *
     * @return True if switching is successful. Else false.
     */
    public boolean setDisplayMode(int width, int height, boolean fullscreen)
    {
        // return if requested DisplayMode is already set
        if ((Display.getDisplayMode().getWidth() == width)
                && (Display.getDisplayMode().getHeight() == height)
                && (Display.isFullscreen() == fullscreen))
        {
            return true;
        }

        try
        {
            // The target DisplayMode
            DisplayMode targetDisplayMode = null;

            if (fullscreen)
            {
                // Gather all the DisplayModes available at fullscreen
                DisplayMode[] modes = Display.getAvailableDisplayModes();
                int freq = 0;

                // Iterate through all of them
                for (DisplayMode current : modes)
                {
                    // Make sure that the width and height matches
                    if ((current.getWidth() == width) && (current.getHeight() == height))
                    {
                        // Select the one with greater frequency
                        if ((targetDisplayMode == null) || (current.getFrequency() >= freq))
                        {
                            // Select the one with greater bits per pixel
                            if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel()))
                            {
                                targetDisplayMode = current;
                                freq = targetDisplayMode.getFrequency();
                            }
                        }

                        // if we've found a match for bpp and frequency against the 
                        // original display mode then it's probably best to go for this one
                        // since it's most likely compatible with the monitor
                        if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel())
                                && (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency()))
                        {
                            targetDisplayMode = current;
                            break;
                        }
                    }
                }
            } else
            {
                // No need to query for windowed mode
                targetDisplayMode = new DisplayMode(width, height);
            }

            if (targetDisplayMode == null)
            {
                System.out.println("Failed to find value mode: " + width + "x" + height + " fs=" + fullscreen);
                return false;
            }

            // Set the DisplayMode we've found
            Display.setDisplayMode(targetDisplayMode);
            Display.setFullscreen(fullscreen);

            System.out.println("Selected DisplayMode: " + targetDisplayMode.toString());


            return true;
        } catch (LWJGLException e)
        {
            System.out.println("Unable to setup mode " + width + "x" + height + " fullscreen=" + fullscreen + e);
        }

        return false;
    }

    /**
     * @return Current time in milliseconds.
     */
    public long getCurrentTime()
    {
        return Sys.getTime() * 1000 / Sys.getTimerResolution();
    }

    /**
     * Properly terminate the game.
     */
    public void end()
    {
        this.dispose();
        Display.destroy();
        System.exit(0);
    }

    /**
     * Render the game
     */
    public abstract void render(Graphics g);

    /**
     * Dispose the game
     */
    public void dispose()
    {
    }

    protected abstract void run(long elapsedTime);

    protected abstract void init();

    private void initGL(int width, int height)
    {
        glEnable(GL_TEXTURE_2D);
        glShadeModel(GL_SMOOTH);
        glClearColor(0, 0, 0, 1);
        glClearDepth(1.0f);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glViewport(0, 0, width, height);
        glOrtho(0, width, height, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);



    }

    protected void clearScreen()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glLoadIdentity();
    }

    public int getFPS()
    {
        return recordedFPS;
    }
}
