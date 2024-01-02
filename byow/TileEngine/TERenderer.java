package byow.TileEngine;

import byow.Core.PlusWorld;
import byow.Core.RandomUtils;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Utility class for rendering tiles. You do not need to modify this file. You're welcome
 * to, but be careful. We strongly recommend getting everything else working before
 * messing with this renderer, unless you're trying to do something fancy like
 * allowing scrolling of the screen or tracking the avatar or something similar.
 */
public class TERenderer {
    private static final int TILE_SIZE = 16;
    private static final Random RANDOM = new Random(100);
    private int width;
    private int height;
    private int xOffset;
    private int yOffset;
    public int coins;
    public int healthbar;

    private double x = Double.MAX_VALUE;
    private double y = Double.MAX_VALUE;
    private static final int WAIT_TIME1 = 5;
    private static final int WAIT_TIME2 = 10;
    /**
     * Same functionality as the other initialization method. The only difference is that the xOff
     * and yOff parameters will change where the renderFrame method starts drawing. For example,
     * if you select w = 60, h = 30, xOff = 3, yOff = 4 and then call renderFrame with a
     * TETile[50][25] array, the renderer will leave 3 tiles blank on the left, 7 tiles blank
     * on the right, 4 tiles blank on the bottom, and 1 tile blank on the top.
     * @param w width of the window in tiles
     * @param h height of the window in tiles.
     */
    public void initialize(int w, int h, int xOff, int yOff) {
        this.width = w;
        this.height = h;
        this.xOffset = xOff;
        this.yOffset = yOff;
        StdDraw.setCanvasSize(width * TILE_SIZE, height * TILE_SIZE);
        Font font = new Font("Monaco", Font.BOLD, TILE_SIZE - 2);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);

        StdDraw.clear(new Color(0, 0, 0));

        StdDraw.enableDoubleBuffering();
        StdDraw.show();
    }

    /**
     * Initializes StdDraw parameters and launches the StdDraw window. w and h are the
     * width and height of the world in number of tiles. If the TETile[][] array that you
     * pass to renderFrame is smaller than this, then extra blank space will be left
     * on the right and top edges of the frame. For example, if you select w = 60 and
     * h = 30, this method will create a 60 tile wide by 30 tile tall window. If
     * you then subsequently call renderFrame with a TETile[50][25] array, it will
     * leave 10 tiles blank on the right side and 5 tiles blank on the top side. If
     * you want to leave extra space on the left or bottom instead, use the other
     * initializatiom method.
     * @param w width of the window in tiles
     * @param h height of the window in tiles.
     */
    public void initialize(int w, int h) {
        initialize(w, h, 0, 0);
    }

    /**
     * Takes in a 2d array of TETile objects and renders the 2d array to the screen, starting from
     * xOffset and yOffset.
     *
     * If the array is an NxM array, then the element displayed at positions would be as follows,
     * given in units of tiles.
     *
     *              positions   xOffset |xOffset+1|xOffset+2| .... |xOffset+world.length
     *
     * startY+world[0].length   [0][M-1] | [1][M-1] | [2][M-1] | .... | [N-1][M-1]
     *                    ...    ......  |  ......  |  ......  | .... | ......
     *               startY+2    [0][2]  |  [1][2]  |  [2][2]  | .... | [N-1][2]
     *               startY+1    [0][1]  |  [1][1]  |  [2][1]  | .... | [N-1][1]
     *                 startY    [0][0]  |  [1][0]  |  [2][0]  | .... | [N-1][0]
     *
     * By varying xOffset, yOffset, and the size of the screen when initialized, you can leave
     * empty space in different places to leave room for other information, such as a GUI.
     * This method assumes that the xScale and yScale have been set such that the max x
     * value is the width of the screen in tiles, and the max y value is the height of
     * the screen in tiles.
     * @param world the 2D TETile[][] array to render
     */
    //renderframe is basically the drawer function for our project.
    //if you call this then basically it will clear the display we have previously, update it with new informations and then redraw it again.

    public void renderFrame(TETile[][] world) {
        int numXTiles = world.length;
        int numYTiles = world[0].length;
        StdDraw.clear(new Color(0, 0, 0));
        for (int x = 0; x < numXTiles; x += 1) {
            for (int y = 0; y < numYTiles; y += 1) {
                if (world[x][y] == null) {
                    throw new IllegalArgumentException("Tile at position x=" + x + ", y=" + y
                            + " is null.");
                }
                world[x][y].draw(x + xOffset, y + yOffset);
            }
        }
        drawingStuff(PlusWorld.coins,PlusWorld.healthbar);
        mouseStuff(world);
        StdDraw.show();
    }
    //drawingstuff will just draw the HUD on top of screen for us.
    public void drawingStuff(int x, int y) {
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(width/2,height-1,"coins: " + (x) + " health: " + iterations(y));
    }
    public String iterations(int x) {
        String now = "";
        int count = 0;
        while (count < x) {
            now = now + '|';
            count++;
        }
        return now;
    }
    public void winnings() {
        StdDraw.clear(new Color(0, 0, 0));
        StdDraw.setPenColor(Color.RED);
        StdDraw.text(width/2,height-2,
                "THE PHANTOM MENACE");
        StdDraw.text(width/2,height-3,
                "Turmoil has engulfed the\n" +
                        "Galactic Republic. The taxation\n" +
                        "of trade routes to outlying star\n" +
                        "systems is in dispute.");
        StdDraw.text(width/2,height-4,
                "Hoping to resolve the matter\n" +
                        "with a blockade of deadly\n" +
                        "battleships,");
        StdDraw.text(width/2,height-5,
                "the greedy Trade\n" +
                        "Federation has stopped all\n" +
                        "shipping to the small planet\n" +
                        "of Naboo.");
        StdDraw.text(width/2,height-6,
                "While the Congress of the\n" +
                        "Republic endlessly debates\n" +
                        "this alarming chain of events,");
        StdDraw.text(width/2,height-7,
                "the Supreme Chancellor has\n" +
                        "secretly dispatched two Jedi\n" +
                        "Knights, ");
        StdDraw.text(width/2,height-8,
                "the guardians of\n" +
                        "peace and justice in the\n" +
                        "galaxy, to settle the conflict....");
        StdDraw.show();
    }
    //updatingmousepointer basically updates into the class the clicking point when we click.
    public void updatingmousepointer(double x, double y) {
        this.x = x;
        this.y = y;
    }
    //mousestuff writes down the description of what we clicked on screen.
    public void mouseStuff(TETile[][] world) {
        if (this.x != Double.MAX_VALUE) {
            int widthing = (int) Math.round(this.x);
            int heighting = (int) Math.round(this.y);
            String now = world[widthing][heighting].description();
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.text(this.x,this.y,now);
            this.x = Double.MAX_VALUE;
        }
        else {
            return;
        }
    }
    public void startGame() {
        StdDraw.clear(new Color(0, 0, 0));
        StdDraw.setPenColor(Color.RED);
        StdDraw.text(width/2,height-2, "CS61B: The Game");
        StdDraw.text(width/2,height-5, "New game (N)");
        StdDraw.text(width/2,height-6, "Load game (L)");
        StdDraw.text(width/2,height-7, "Quit (Q)");
        StdDraw.show();
    }

    public void inputSeed() {
        StdDraw.clear(new Color(0, 0, 0));
        StdDraw.setPenColor(Color.RED);
        StdDraw.text(width/2,height-2, "Input You Seed Here, Hit 'S' When You Are Done.");
        StdDraw.show();
    }
    public void showSeed(String seed) {
        StdDraw.clear(new Color(0, 0, 0));
        StdDraw.setPenColor(Color.RED);
        StdDraw.text(width/2,height-2, "Input You Seed Here, Hit 'S' When You Are Done.");
        StdDraw.text(width/2,height-5, seed);
        StdDraw.show();
    }
    public void showWelcome() {
        StdDraw.clear(new Color(0, 0, 0));
        StdDraw.setPenColor(Color.RED);
        String starLine    = "*************************************";
        String WelcomeLine = "**         Welcome To BYOW         **";
        String LoadingLine = "Loading ...";
        StdDraw.text(width/2,height-8, starLine);
        StdDraw.text(width/2,height-11, WelcomeLine);
        StdDraw.text(width/2,height-14, starLine);
        StdDraw.text(width/2,height-17, LoadingLine);
        StdDraw.show();
        waitMS(WAIT_TIME1);
        StdDraw.text(width/2,height-20, "####");
        StdDraw.show();
        waitMS(WAIT_TIME2);
        StdDraw.text(width/2,height-20, "########");
        StdDraw.show();
        waitMS(WAIT_TIME2);
        StdDraw.text(width/2,height-20, "############");
        StdDraw.show();
        waitMS(WAIT_TIME2);
        StdDraw.text(width/2,height-20, "####################");
        StdDraw.show();
        waitMS(WAIT_TIME2);
    }

    public static void waitMS(int ms)
    {
        try {
            Thread.sleep(ms);
        } catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
}