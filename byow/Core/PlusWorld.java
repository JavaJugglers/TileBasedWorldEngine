package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
/**
 * Draws a world consisting of plus shaped regions.
 */
public class PlusWorld {
    private static final long SEED = 2873123;
    public static TETile now;
    private static TETile prev;
    private static int[] nucleation;
    private static final Random RANDOM = new Random(SEED);
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;
    public static int coins;
    public static int healthbar = 10;
    static final File CWD = new File(".");
    static final File PERSISTENCE_FILE = Utils.join(CWD, "persistence");
    private static int randomTile_width() {
        return RANDOM.nextInt(WIDTH-1);
    }

    private static int randomTile_height() {
        return RANDOM.nextInt(HEIGHT-1);
    }
    public static void randomizedWalking(TETile[][] world, int beginningWidth, int beginningHeight,
                                         int endingWidth, int endingHeight, Random randGen) {
        iterativecallhelper(world,beginningWidth,beginningHeight,endingWidth,endingHeight, randGen);
    }
    private static void iterativecallhelper(TETile[][] world, int beginningWidth, int beginningHeight,
                                            int endingWidth, int endingHeight, Random randGen) {
        int finalHeight = beginningHeight + randomizedNumber(randGen);
        int finalWidth = beginningWidth + randomizedNumber(randGen);
        int x;
        while (!(beginningHeight == endingHeight && beginningWidth == endingWidth)) {
            if ((finalWidth < WIDTH && finalWidth >= 0) && (finalHeight < HEIGHT && finalHeight >= 0)) {
                if ((Math.abs(finalHeight - endingHeight)) < (Math.abs(beginningHeight - endingHeight)) ) {
                    beginningHeight = finalHeight;
                    world[beginningWidth][beginningHeight] = Tileset.FLOOR;
                }
                else if ((Math.abs(finalWidth - endingWidth)) < (Math.abs(beginningWidth - endingWidth)) ) {
                    beginningWidth = finalWidth;
                    world[beginningWidth][beginningHeight]= Tileset.FLOOR;
                }
                //  else {
                //     x = randomized_numbers(finalHeight,finalWidth);
                //     if (x == finalHeight) {
                //         world[beginningWidth][finalHeight]= Tileset.FLOOR;
                //        beginningHeight = finalHeight;
                //    }
                //    else if (x == finalWidth) {
                //        world[finalWidth][beginningHeight]= Tileset.FLOOR;
                //        beginningWidth = finalWidth;
                //   }
                // }
                finalHeight = beginningHeight + randomizedNumber(randGen);
                finalWidth = beginningWidth + randomizedNumber(randGen);
            }
            else {
                finalHeight = beginningHeight + randomizedNumber(randGen);
                finalWidth = beginningWidth + randomizedNumber(randGen);
            }
        }
        System.out.println("yes");
    }

    private static int randomizedNumber(Random randGen) {
        int tileNum = randGen.nextInt(2);
        switch (tileNum) {
            case 0: return 1;
            case 1: return -1;
            default: return 0;
        }
    }
    public static void borderize(TETile[][] world) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if ((x + 1 < WIDTH) && world[x+1][y] == Tileset.FLOOR && world[x][y] != Tileset.FLOOR) {
                    world[x][y] = Tileset.WALL;
                }
                else if ((x-1 >= 0) && world[x-1][y] == Tileset.FLOOR && world[x][y] != Tileset.FLOOR) {
                    world[x][y] = Tileset.WALL;
                }
                else if ((y-1 >= 0) && world[x][y-1] == Tileset.FLOOR && world[x][y] != Tileset.FLOOR) {
                    world[x][y] = Tileset.WALL;
                }
                else if ((y+1 < HEIGHT) && world[x][y+1] == Tileset.FLOOR && world[x][y] != Tileset.FLOOR) {
                    world[x][y] = Tileset.WALL;
                }
            }
        }

        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if ((y - 1 >= 0) && (x-1 >= 0) && (y + 1 < HEIGHT) && (x + 1 < WIDTH) && (world[x][y] == Tileset.FLOOR) && (world[x-1][y+1] == Tileset.NOTHING || world[x+1][y+1] == Tileset.NOTHING || world[x-1][y-1] == Tileset.NOTHING || world[x+1][y-1] == Tileset.NOTHING)) {
                    if (world[x-1][y+1] == Tileset.NOTHING) {
                        world[x-1][y+1] = Tileset.WALL;
                    }
                    else if (world[x+1][y+1] == Tileset.NOTHING) {
                        world[x+1][y+1] = Tileset.WALL;
                    }
                    else if (world[x-1][y-1] == Tileset.NOTHING) {
                        world[x-1][y-1] = Tileset.WALL;
                    }
                    else if (world[x+1][y-1] == Tileset.NOTHING) {
                        world[x+1][y-1] = Tileset.WALL;
                    }
                }
                // else if ((y - 1 >= 0) && (x + 1 < WIDTH) && (x-1 >= 0) && (y + 1 < HEIGHT) && (world[x-1][y] == Tileset.WALL && world[x][y+1] == Tileset.WALL) && world[x][y] != Tileset.FLOOR && (world[x][y-1] == Tileset.NOTHING) && (world[x+1][y] == Tileset.NOTHING)) {
                //      world[x][y] = Tileset.WALL;
                // }
                //  else if ((x + 1 < WIDTH) && (y - 1 >= 0) && (world[x+1][y] == Tileset.WALL && world[x][y-1] == Tileset.WALL) && world[x][y] != Tileset.FLOOR) {
                //       world[x][y] = Tileset.WALL;
                //   }
                //   else if ((x-1 >= 0) && (y-1 >= 0) && (world[x-1][y] == Tileset.WALL && world[x][y-1] == Tileset.WALL) && world[x][y] != Tileset.FLOOR) {
                //      world[x][y] = Tileset.WALL;
                //  }
            }
        }
    }
    public static void trackMoves(int[] coordinate, TETile[][] world, TERenderer ter) {
        int width = coordinate[0];
        int height = coordinate[1];
        while (true) {
            if (PlusWorld.coins == 0) {
                ter.winnings();
            }
            if (StdDraw.isMousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                ter.updatingmousepointer(x,y);
                ter.renderFrame(world);
            }
            if (StdDraw.hasNextKeyTyped()) {
                char x = StdDraw.nextKeyTyped();
                if (x == 'w' && world[width][height + 1] == Tileset.WALL) {
                    System.out.println("PlusWorld.trackMoves");
                    PlusWorld.healthbar--;
                    ter.renderFrame(world);
                }
                if (x == 'a' && world[width-1][height] == Tileset.WALL) {
                    PlusWorld.healthbar--;
                    ter.renderFrame(world);
                }
                if (x == 'd' && world[width+1][height] == Tileset.WALL) {
                    PlusWorld.healthbar--;
                    ter.renderFrame(world);
                }
                if (x == 's' && world[width][height-1] == Tileset.WALL) {
                    PlusWorld.healthbar--;
                    ter.renderFrame(world);
                }
                if (x == 'r' && (world[width][height+1] == Tileset.TREE || world[width][height-1] == Tileset.TREE || world[width+1][height] == Tileset.TREE || world[width-1][height] == Tileset.TREE)) {
                    PlusWorld.coins = PlusWorld.coins-1;
                    if (world[width][height+1] == Tileset.TREE) {
                        world[width][height+1] = Tileset.FLOOR;
                    }
                    if (world[width][height-1] == Tileset.TREE) {
                        world[width][height-1] = Tileset.FLOOR;
                    }
                    if (world[width+1][height] == Tileset.TREE) {
                        world[width+1][height] = Tileset.FLOOR;
                    }
                    if (world[width-1][height] == Tileset.TREE) {
                        world[width-1][height] = Tileset.FLOOR;
                    }
                    ter.renderFrame(world);
                    System.out.println("PlusWorld.trackMovement");
                }
                if (x == 'w' && (world[width][height+1] != Tileset.WALL) && (world[width][height+1] != Tileset.lamp)) {
                    if (world[width][height+1] == Tileset.LOCKED_DOOR) {
                        return;
                    }
                    System.out.println("PlusWorld.trackMovement");
                    world[width][height] = PlusWorld.prev;
                    height = height + 1;
                    PlusWorld.prev = world[width][height];
                    world[width][height] = new TETile('@', Color.white, PlusWorld.prev.backgroundColor, "you");
                    ter.renderFrame(world);
                }
                else if (x == 'a' && (world[width-1][height] != Tileset.WALL) && (world[width-1][height] != Tileset.LOCKED_DOOR)&& (world[width-1][height] != Tileset.lamp)) {
                    System.out.println("PlusWorld.trackMoves");
                    world[width][height] = PlusWorld.prev;
                    width = width - 1;
                    PlusWorld.prev = world[width][height];
                    world[width][height] = new TETile('@', Color.white, PlusWorld.prev.backgroundColor, "you");
                    ter.renderFrame(world);
                }
                else if (x == 'd' && (world[width+1][height] != Tileset.WALL) && (world[width+1][height] != Tileset.LOCKED_DOOR)&& (world[width+1][height] != Tileset.lamp)) {
                    world[width][height] = PlusWorld.prev;
                    width = width + 1;
                    PlusWorld.prev = world[width][height];
                    world[width][height] = new TETile('@', Color.white, PlusWorld.prev.backgroundColor, "you");
                    ter.renderFrame(world);
                }
                else if (x == 's' && (world[width][height-1] != Tileset.WALL) && (world[width][height-1] != Tileset.lamp)) {
                    if (world[width][height-1] == Tileset.LOCKED_DOOR) {
                        generate_new_world();
                    }
                    world[width][height] = PlusWorld.prev;
                    height = height - 1;
                    PlusWorld.prev = world[width][height];
                    world[width][height] = new TETile('@', Color.white, PlusWorld.prev.backgroundColor, "you");
                    ter.renderFrame(world);
                }
                else if (x == 'f' && ((world[width][height+1] == Tileset.LampAdvanced) || (world[width][height-1] == Tileset.LampAdvanced) || (world[width+1][height] == Tileset.LampAdvanced) || (world[width-1][height] == Tileset.LampAdvanced) || (world[width][height+1] == Tileset.lamp) || (world[width][height-1] == Tileset.lamp) || (world[width+1][height] == Tileset.lamp) || (world[width-1][height] == Tileset.lamp))) {
                    if (world[width][height+1] == Tileset.LampAdvanced) {
                        int[] lights = new int[2];
                        lights[0] = width;
                        lights[1] = height+1;
                        //avatar place
                        int[] avatars = new int[2];
                        avatars[0] = width;
                        avatars[1] = height;
                        TurnOff(lights,world,avatars);
                        ter.renderFrame(world);
                    }
                    else if (world[width][height-1] == Tileset.LampAdvanced) {
                        int[] lights = new int[2];
                        lights[0] = width;
                        lights[1] = height-1;
                        //avatar place
                        int[] avatars = new int[2];
                        avatars[0] = width;
                        avatars[1] = height;
                        TurnOff(lights,world,avatars);
                        ter.renderFrame(world);
                    }
                    else if (world[width+1][height] == Tileset.LampAdvanced) {
                        int[] lights = new int[2];
                        lights[0] = width+1;
                        lights[1] = height;
                        //avatar place
                        int[] avatars = new int[2];
                        avatars[0] = width;
                        avatars[1] = height;
                        TurnOff(lights,world,avatars);
                        ter.renderFrame(world);
                    }
                    else if (world[width-1][height] == Tileset.LampAdvanced) {
                        int[] lights = new int[2];
                        lights[0] = width-1;
                        lights[1] = height;
                        //avatar place
                        int[] avatars = new int[2];
                        avatars[0] = width;
                        avatars[1] = height;
                        TurnOff(lights,world,avatars);
                        ter.renderFrame(world);
                    }
                    else if (world[width][height+1] == Tileset.lamp) {
                        int[] lights = new int[2];
                        lights[0] = width;
                        lights[1] = height+1;
                        //avatar place
                        int[] avatars = new int[2];
                        avatars[0] = width;
                        avatars[1] = height;
                        FiatLux(lights,world,avatars);
                        ter.renderFrame(world);
                    }
                    else if (world[width][height-1] == Tileset.lamp) {
                        int[] lights = new int[2];
                        lights[0] = width;
                        lights[1] = height-1;
                        //avatar place
                        int[] avatars = new int[2];
                        avatars[0] = width;
                        avatars[1] = height;
                        FiatLux(lights,world,avatars);
                        ter.renderFrame(world);
                    }
                    else if (world[width+1][height] == Tileset.lamp) {
                        int[] lights = new int[2];
                        lights[0] = width+1;
                        lights[1] = height;
                        //avatar place
                        int[] avatars = new int[2];
                        avatars[0] = width;
                        avatars[1] = height;
                        FiatLux(lights,world,avatars);
                        ter.renderFrame(world);
                    }
                    else if (world[width-1][height] == Tileset.lamp) {
                        int[] lights = new int[2];
                        lights[0] = width-1;
                        lights[1] = height;
                        //avatar place
                        int[] avatars = new int[2];
                        avatars[0] = width;
                        avatars[1] = height;
                        FiatLux(lights,world,avatars);
                        ter.renderFrame(world);
                    }
                }
            }
        }
    }

    public static int checkSaveQuit(char key, int saveQuit) {
        //System.out.println("CHECK" + " " + key);
        if (saveQuit == 1) {
            if ((key == 'Q') || (key == 'q')) {
                // do SaveQuit (persistence control)
                System.out.println("Save and Quit");
                return 2;
            } else {
                return 0;
            }
        } else if (key == ':') {
            System.out.println("Seen colon");
            return 1;
        }
        return 0;
    }

    //this tracks movement of our mouse and keys. we input coordinate of our avatar, input world and a ter which is the game engine used to run the game.
    //if mouse is pressed on a tile it will display information on the screen
    //if you press w,a,s,d then it will move the avatar accordingly.
    //this will run forever, in a true game this is not the case, but we have not specified stopping point yet.
    //for example we can change the truth value of while if, for example enough coins is collected and we win the game.
    public static void trackMovement(int[] coordinate, TETile[][] world, TERenderer ter, PersistenceData pd) {
        int width = coordinate[0];
        int height = coordinate[1];
        int saveQuit = 0; // 0: normal, 1: user input ':', 2: user input ':Q' or ':q', do save and quit
        while (true) {
            //usually @
            if (PlusWorld.now.character() != world[width][height].character()) {
                TETile right = world[width][height];
                world[width][height] = PlusWorld.now;
                world[width][height].backgroundColor = right.backgroundColor;
                ter.renderFrame(world);
                world[width][height] = right;
            }
            if (PlusWorld.coins == 0 || PlusWorld.healthbar == 0) {
                ter.winnings();
                return;
            }
            if (StdDraw.isMousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                ter.updatingmousepointer(x,y);
                ter.renderFrame(world);
            }
            if (StdDraw.hasNextKeyTyped()) {
                char x = StdDraw.nextKeyTyped();
                //below if is changed.
                if (x == 'p') {
                    System.out.println("PlusWorld.trackMovement");
                    makeSpikes(100,world);
                    ter.renderFrame(world);
                }

                saveQuit = checkSaveQuit(x, saveQuit);
                if (saveQuit == 2) {
                    pd.coins = PlusWorld.coins;
                    pd.healthbar = PlusWorld.healthbar;
                    pd.prev = PlusWorld.prev;
                    System.out.println("DEBUG 6" + pd.world.length + " " + pd.world[0].length + " " + pd.coins);
                    checkNull(world, 6);
                    checkNull(pd.world, 7);
                    updatePersistence(pd);
                    //StdDraw.clear();
                    break;
                }

                //spikes
                //below if is changed
                if (x == 'w' && world[width][height + 1].description.equals(Tileset.MOUNTAIN.description)) {
                    PlusWorld.healthbar = 0;
                }
                //below if is changed
                if (x == 'a' && world[width-1][height].description.equals(Tileset.MOUNTAIN.description)) {
                    PlusWorld.healthbar = 0;
                }
                //below if is changed
                if (x == 'd' && world[width+1][height].description.equals(Tileset.MOUNTAIN.description)) {
                    PlusWorld.healthbar = 0;
                }
                //below if is changed
                if (x == 's' && world[width][height-1].description.equals(Tileset.MOUNTAIN.description)) {
                    PlusWorld.healthbar = 0;
                }
                //
                if (x == 'w' && world[width][height + 1].description.equals(Tileset.WALL.description)) {
                    PlusWorld.healthbar--;
                    ter.renderFrame(world);
                }
                if (x == 'a' && world[width-1][height].description.equals(Tileset.WALL.description)) {
                    PlusWorld.healthbar--;
                    ter.renderFrame(world);
                }
                if (x == 'd' && world[width+1][height].description.equals(Tileset.WALL.description)) {
                    PlusWorld.healthbar--;
                    ter.renderFrame(world);
                }
                if (x == 's' && world[width][height-1].description.equals(Tileset.WALL.description)) {
                    PlusWorld.healthbar--;
                    ter.renderFrame(world);
                }
                if (x == 'r' && (world[width][height+1].description.equals(Tileset.TREE.description) || world[width][height-1].description.equals(Tileset.TREE.description) || world[width+1][height].description.equals(Tileset.TREE.description) || world[width-1][height].description.equals(Tileset.TREE.description))) {
                    PlusWorld.coins = PlusWorld.coins-1;
                    if (world[width][height+1].description.equals(Tileset.TREE.description)) {
                        world[width][height+1] = Tileset.FLOOR;
                    }
                    if (world[width][height-1].description.equals(Tileset.TREE.description)) {
                        world[width][height-1] = Tileset.FLOOR;
                    }
                    if (world[width+1][height].description.equals(Tileset.TREE.description)) {
                        world[width+1][height] = Tileset.FLOOR;
                    }
                    if (world[width-1][height].description.equals(Tileset.TREE.description)) {
                        world[width-1][height] = Tileset.FLOOR;
                    }
                    ter.renderFrame(world);
                    System.out.println("PlusWorld.trackMovement");
                }
                if (x == 'w' && (!world[width][height+1].description.equals(Tileset.WALL.description)) && (!world[width][height+1].description.equals(Tileset.LOCKED_DOOR.description)) && (!world[width][height+1].description.equals(Tileset.lamp.description))) {
                    System.out.println("PlusWorld.trackMovement");
                    world[width][height] = PlusWorld.prev;
                    height = height + 1;
                    PlusWorld.prev = world[width][height];
                    System.out.println(world[width][height].description);
                    world[width][height] = new TETile('@', Color.white, PlusWorld.prev.backgroundColor, "you");

                    ter.renderFrame(world);
                }
                else if (x == 'a' && (!world[width-1][height].description.equals(Tileset.WALL.description)) && (!world[width-1][height].description.equals(Tileset.LOCKED_DOOR.description))&& (!world[width-1][height].description.equals(Tileset.lamp.description))) {
                    world[width][height] = PlusWorld.prev;
                    width = width - 1;
                    PlusWorld.prev = world[width][height];
                    world[width][height] = new TETile('@', Color.white, PlusWorld.prev.backgroundColor, "you");

                    ter.renderFrame(world);
                }
                else if (x == 'd' && (!world[width+1][height].description.equals(Tileset.WALL.description)) && (!world[width+1][height].description.equals(Tileset.LOCKED_DOOR.description))&& (!world[width+1][height].description.equals(Tileset.lamp.description))) {
                    world[width][height] = PlusWorld.prev;
                    width = width + 1;
                    PlusWorld.prev = world[width][height];
                    world[width][height] = new TETile('@', Color.white, PlusWorld.prev.backgroundColor, "you");

                    ter.renderFrame(world);
                }
                else if (x == 's' && (!world[width][height-1].description.equals(Tileset.WALL.description)) && (!world[width][height-1].description.equals(Tileset.lamp.description))) {
                    world[width][height] = PlusWorld.prev;
                    height = height - 1;
                    PlusWorld.prev = world[width][height];
                    world[width][height] = new TETile('@', Color.white, PlusWorld.prev.backgroundColor, "you");

                    ter.renderFrame(world);
                }
                else if (x == 'f' && ((world[width][height+1].description.equals(Tileset.LampAdvanced.description)) || (world[width][height-1].description.equals(Tileset.LampAdvanced.description)) || (world[width+1][height].description.equals(Tileset.LampAdvanced.description)) || (world[width-1][height].description.equals(Tileset.LampAdvanced.description)) || (world[width][height+1].description.equals(Tileset.lamp.description)) || (world[width][height-1].description.equals(Tileset.lamp.description)) || (world[width+1][height].description.equals(Tileset.lamp.description)) || (world[width-1][height].description.equals(Tileset.lamp.description)))) {
                    if (world[width][height+1].description.equals(Tileset.LampAdvanced.description)) {
                        int[] lights = new int[2];
                        lights[0] = width;
                        lights[1] = height+1;
                        //avatar place
                        int[] avatars = new int[2];
                        avatars[0] = width;
                        avatars[1] = height;
                        TurnOff(lights,world,avatars);
                        ter.renderFrame(world);
                    }
                    else if (world[width][height-1].description.equals(Tileset.LampAdvanced.description)) {
                        int[] lights = new int[2];
                        lights[0] = width;
                        lights[1] = height-1;
                        //avatar place
                        int[] avatars = new int[2];
                        avatars[0] = width;
                        avatars[1] = height;
                        TurnOff(lights,world,avatars);
                        ter.renderFrame(world);
                    }
                    else if (world[width+1][height].description.equals(Tileset.LampAdvanced.description)) {
                        int[] lights = new int[2];
                        lights[0] = width+1;
                        lights[1] = height;
                        //avatar place
                        int[] avatars = new int[2];
                        avatars[0] = width;
                        avatars[1] = height;
                        TurnOff(lights,world,avatars);
                        ter.renderFrame(world);
                    }
                    else if (world[width-1][height].description.equals(Tileset.LampAdvanced.description)) {
                        int[] lights = new int[2];
                        lights[0] = width-1;
                        lights[1] = height;
                        //avatar place
                        int[] avatars = new int[2];
                        avatars[0] = width;
                        avatars[1] = height;
                        TurnOff(lights,world,avatars);
                        ter.renderFrame(world);
                    }
                    else if (world[width][height+1].description.equals(Tileset.lamp.description)) {
                        int[] lights = new int[2];
                        lights[0] = width;
                        lights[1] = height+1;
                        //avatar place
                        int[] avatars = new int[2];
                        avatars[0] = width;
                        avatars[1] = height;
                        FiatLux(lights,world,avatars);
                        ter.renderFrame(world);
                    }
                    else if (world[width][height-1].description.equals(Tileset.lamp.description)) {
                        int[] lights = new int[2];
                        lights[0] = width;
                        lights[1] = height-1;
                        //avatar place
                        int[] avatars = new int[2];
                        avatars[0] = width;
                        avatars[1] = height;
                        FiatLux(lights,world,avatars);
                        ter.renderFrame(world);
                    }
                    else if (world[width+1][height].description.equals(Tileset.lamp.description)) {
                        int[] lights = new int[2];
                        lights[0] = width+1;
                        lights[1] = height;
                        //avatar place
                        int[] avatars = new int[2];
                        avatars[0] = width;
                        avatars[1] = height;
                        FiatLux(lights,world,avatars);
                        ter.renderFrame(world);
                    }
                    else if (world[width-1][height].description.equals(Tileset.lamp.description)) {
                        int[] lights = new int[2];
                        lights[0] = width-1;
                        lights[1] = height;
                        //avatar place
                        int[] avatars = new int[2];
                        avatars[0] = width;
                        avatars[1] = height;
                        FiatLux(lights,world,avatars);
                        ter.renderFrame(world);
                    }
                }
            }
        }
    }

    //place an avatar in the map and also returns its coordinate.
    public static int[] randomNucleationPoint(TETile[][] world) {
        int width = RANDOM.nextInt(WIDTH);
        int height = RANDOM.nextInt(HEIGHT);
        while (width < WIDTH && height < HEIGHT && world[width][height] != Tileset.FLOOR) {
            width = RANDOM.nextInt(WIDTH);
            height = RANDOM.nextInt(HEIGHT);
        }
        PlusWorld.prev = world[width][height];
        world[width][height] = Tileset.AVATAR;
        int[] stuff = new int[2];
        stuff[0] = width;
        stuff[1] = height;
        PlusWorld.nucleation = stuff;
        return stuff;
    }
    //we might not need this, but this is to place a random locked key on our map. Input is world and output a size 2 array coordinate of where the lock is placed.
    public static int[] randomLockedKey(TETile[][] world) {
        int width = RANDOM.nextInt(WIDTH);
        int height = RANDOM.nextInt(HEIGHT);
        while (true) {
            if ((width < WIDTH) && (height < HEIGHT) && (world[width][height] == Tileset.FLOOR)) {
                if (world[width+1][height] == Tileset.WALL) {
                    world[width+1][height] = Tileset.LOCKED_DOOR;
                    width = width + 1;
                    int[] x = new int[2];
                    x[0] = width;
                    x[1] = height;
                    return x;
                }
                if (world[width-1][height] == Tileset.WALL) {
                    world[width-1][height] = Tileset.LOCKED_DOOR;
                    width = width - 1;
                    int[] x = new int[2];
                    x[0] = width;
                    x[1] = height;
                    return x;
                }
                if (world[width][height+1] == Tileset.WALL) {
                    world[width][height+1] = Tileset.LOCKED_DOOR;
                    height = height + 1;
                    int[] x = new int[2];
                    x[0] = width;
                    x[1] = height;
                    return x;
                }
                if (world[width][height-1] == Tileset.WALL) {
                    world[width][height-1] = Tileset.LOCKED_DOOR;
                    height = height - 1;
                    int[] x = new int[2];
                    x[0] = width;
                    x[1] = height;
                    return x;
                }
                width = RANDOM.nextInt(WIDTH);
                height = RANDOM.nextInt(HEIGHT);
            }
            else {
                width = RANDOM.nextInt(WIDTH);
                height = RANDOM.nextInt(HEIGHT);
            }
        }
    }
    //this is the getter method for avatar, input the world and it will output coordinate of size 2 array (width,height).
    public static int[] getAvatar(TETile[][] world) {
        int[] result = new int[2];
        for (int x = 0; x < WIDTH;x++) {
            for (int y = 0; y < HEIGHT;y++) {
                if (world[x][y].description().equals("you")) {
                    result[0] = x;
                    result[1] = y;
                    return result;
                }
            }
        }
        return result;
    }
    //find best place to place lamp, should be 10 square units away from each other as to not disturb the other's light.
    public static ArrayList<int[]> createLightsource(int x, TETile[][] world, int[] avatars) {
        ArrayList<int[]> result = new ArrayList<>();
        for (int y = 0; y < x; y++) {
            int[] shows = FindlampPlace(world,result,avatars);
            result.add(shows);
        }
        return result;
    }
    //this is createlightsource's helper method.
    public static int[] FindlampPlace(TETile[][] world,ArrayList<int[]> result, int[] avatars) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                //initialize int
                boolean realize = false;
                for (int z = 0;z < result.size();z++) {
                    if (Math.abs(x-result.get(z)[0]) < 10 && Math.abs(y-result.get(z)[1]) < 10) {
                        realize = true;
                    }
                }
                if (realize == true) {
                    //nothing
                }
                else if ((x-1 >= 0) && (x + 1 < WIDTH) && (y - 1 >= 0) && (y + 1 < HEIGHT)) {
                    if ((world[x+1][y] == Tileset.FLOOR) && (world[x-1][y] == Tileset.FLOOR) && (world[x][y + 1] == Tileset.FLOOR) && (world[x][y-1] == Tileset.FLOOR) && (world[x+1][y+1] == Tileset.FLOOR) && (world[x+1][y-1] == Tileset.FLOOR) && (world[x-1][y+1] == Tileset.FLOOR) && (world[x-1][y-1] == Tileset.FLOOR)) {
                        world[x][y] = Tileset.lamp;
                        int[] newer = new int[2];
                        newer[0] = x;
                        newer[1] = y;
                        return newer;
                    }
                }
            }
        }
        return null;
    }
    //fiatlux: let there be light, turns on all lamp in map.
    public static void FiatLux(int[] coordinate, TETile[][] world, int[] avatarCoordinate) {
        world[coordinate[0]][coordinate[1]] = Tileset.LampAdvanced;
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                int[] now = new int[2];
                now[0] = x;
                now[1] = y;
                if (squareRoots(now,coordinate) <= 5 && (world[x][y].description.equals(Tileset.FLOOR.description) || world[x][y].description.equals("you"))) {
                    if (squareRoots(now,coordinate) == 1) {
                        world[x][y] = Tileset.FLOORAdvanced0;
                    }
                    else if (squareRoots(now,coordinate) == 2) {
                        world[x][y] = Tileset.FLOORAdvanced1;
                    }
                    else if (squareRoots(now,coordinate) == 3) {
                        world[x][y] = Tileset.FLOORAdvanced2;
                    }
                    else if (squareRoots(now,coordinate) == 4) {
                        world[x][y] = Tileset.FLOORAdvanced3;
                    }
                    else {
                        world[x][y] = Tileset.FLOORAdvanced4;
                    }
                }
            }
        }
        if (avatarCoordinate.equals(PlusWorld.nucleation)) {
            return;
        }
        int x = squareRoots(avatarCoordinate,coordinate);
        int avatar1 = avatarCoordinate[0];
        int avatar2 = avatarCoordinate[1];
        if (x==1) {
            PlusWorld.prev = Tileset.FLOORAdvanced0;
            world[avatar1][avatar2] = new TETile('@', Color.white, Tileset.FLOORAdvanced0.backgroundColor, "you");;
        }
        else if (x==2) {
            PlusWorld.prev = Tileset.FLOORAdvanced1;
            world[avatar1][avatar2] = new TETile('@', Color.white, Tileset.FLOORAdvanced1.backgroundColor, "you");;
        }
        else if (x==3) {
            PlusWorld.prev = Tileset.FLOORAdvanced2;
            world[avatar1][avatar2] = new TETile('@', Color.white, Tileset.FLOORAdvanced2.backgroundColor, "you");;
        }
        else if (x==4) {
            PlusWorld.prev = Tileset.FLOORAdvanced3;
            world[avatar1][avatar2] = new TETile('@', Color.white, Tileset.FLOORAdvanced3.backgroundColor, "you");;
        }
        else {
            PlusWorld.prev = Tileset.FLOORAdvanced4;
            world[avatar1][avatar2] = new TETile('@', Color.white, Tileset.FLOORAdvanced4.backgroundColor, "you");;
        }
    }
    //need to call updating after we first set up lights(after calling fiatlux).
    public static void updating(int[] x, TETile[][] world) {
        if (world[x[0]][x[1]] == Tileset.AVATAR) {
            return;
        }
        PlusWorld.prev = world[x[0]][x[1]];
        world[x[0]][x[1]] = new TETile('@', Color.white, PlusWorld.prev.backgroundColor, "you");
    }
    //turns on or off light, must be in front of the light source to turn off.
    public static void TurnOff(int[] coordinate, TETile[][] world, int[] avatarCoordinate) {
        world[coordinate[0]][coordinate[1]] = Tileset.lamp;
        for (int x = avatarCoordinate[0] - 8; x < avatarCoordinate[0] + 8; x++) {
            for (int y = avatarCoordinate[1] - 8; y < avatarCoordinate[1] + 8; y++) {
                if (x < 0 || y < 0 || x > WIDTH || y > HEIGHT) {
                    continue;
                }
                int[] now = new int[2];
                now[0] = x;
                now[1] = y;
                if (squareRoots(now,coordinate) <= 5 && (world[x][y].description.equals(Tileset.FLOORAdvanced0.description)) || (world[x][y].description.equals(Tileset.FLOORAdvanced1.description)) || (world[x][y].description.equals(Tileset.FLOORAdvanced2.description)) || (world[x][y].description.equals(Tileset.FLOORAdvanced3.description)) || (world[x][y].description.equals(Tileset.FLOORAdvanced4.description))) {
                    if (squareRoots(now,coordinate) == 1) {
                        world[x][y] = Tileset.FLOOR;
                    }
                    else if (squareRoots(now,coordinate) == 2) {
                        world[x][y] = Tileset.FLOOR;
                    }
                    else if (squareRoots(now,coordinate) == 3) {
                        world[x][y] = Tileset.FLOOR;
                    }
                    else if (squareRoots(now,coordinate) == 4) {
                        world[x][y] = Tileset.FLOOR;
                    }
                    else {
                        world[x][y] = Tileset.FLOOR;
                    }
                }
            }
        }
        world[avatarCoordinate[0]][avatarCoordinate[1]] = Tileset.AVATAR;
        PlusWorld.prev = Tileset.FLOOR;

    }
    //finds point distance between now and source.
    public static int squareRoots(int[] now, int[] source) {
        int x = (now[0] - source[0]) * (now[0] - source[0]);
        int y = (now[1] - source[1]) * (now[1] - source[1]);
        int result = (int) Math.sqrt(x + y);
        return result;
    }
    public static int menuLine(TERenderer ter) {
        ter.startGame();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char x = StdDraw.nextKeyTyped();
                if ((x == 'n') || (x == 'N')) {
                    System.out.println("new game");
                    return 0;
                }
                else if ((x == 'l') || (x == 'L')) {
                    System.out.println("load game");
                    return 1;
                }
                else if ((x == 'q') || (x == 'Q')) {
                    System.out.println("quit");
                    return 2;
                }
            }
        }
    }

    public static String getSeed(TERenderer ter) {
        ter.inputSeed();
        String seed = "";
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char x = StdDraw.nextKeyTyped();
                // TBD: need to filter out non-digits
                if ((x == 's') || (x == 'S')) {
                    System.out.println(seed);
                    ter.showWelcome();
                    return seed;
                } else {
                    seed = seed + x;
                    ter.showSeed(seed);
                }
            }
        }
    }


    public static ArrayList<int[]> make_coins(int x, TETile[][] world) {
        PlusWorld.coins = PlusWorld.coins + x;
        ArrayList<int[]> result = new ArrayList<>();
        int width = RANDOM.nextInt(WIDTH);
        int height = RANDOM.nextInt(HEIGHT);
        while (x != 0) {
            if (width < WIDTH && height < HEIGHT && world[width][height] == Tileset.FLOOR && (world[width+1][height-1] != Tileset.TREE && world[width+1][height+1] != Tileset.TREE && world[width-1][height-1] != Tileset.TREE && world[width-1][height+1] != Tileset.TREE && world[width][height+1] != Tileset.TREE && world[width][height-1] != Tileset.TREE && world[width+1][height] != Tileset.TREE && world[width-1][height] != Tileset.TREE)) {
                world[width][height] = Tileset.TREE;
                int[] temp = {width, height};
                result.add(temp);
                x--;
            }
            else {
                width = RANDOM.nextInt(WIDTH);
                height = RANDOM.nextInt(HEIGHT);
            }
        }
        return result;
    }
    //below method makespikes is changed.
    public static ArrayList<int[]> makeSpikes(int x, TETile[][] world) {
        ArrayList<int[]> result = new ArrayList<>();
        int width = RANDOM.nextInt(WIDTH);
        int height = RANDOM.nextInt(HEIGHT);
        int y = 10000;
        while (x != 0) {
            if (y == 0) {
                break;
            }
            if (width < WIDTH && height < HEIGHT && world[width][height].description.equals(Tileset.FLOOR.description) && (!world[width+1][height-1].description.equals(Tileset.TREE.description) && !world[width+1][height+1].description.equals(Tileset.TREE.description) && !world[width-1][height-1].description.equals(Tileset.TREE.description) && !world[width-1][height+1].description.equals(Tileset.TREE.description) && !world[width][height+1].description.equals(Tileset.TREE.description) && !world[width][height-1].description.equals(Tileset.TREE.description) && !world[width+1][height].description.equals(Tileset.TREE.description) && !world[width-1][height].description.equals(Tileset.TREE.description)) && (!world[width+1][height-1].description.equals(Tileset.WALL.description) && !world[width+1][height+1].description.equals(Tileset.WALL.description) && !world[width-1][height-1].description.equals(Tileset.WALL.description) && !world[width-1][height+1].description.equals(Tileset.WALL.description) && !world[width][height+1].description.equals(Tileset.WALL.description) && !world[width][height-1].description.equals(Tileset.WALL.description) && !world[width+1][height].description.equals(Tileset.WALL.description) && !world[width-1][height].description.equals(Tileset.WALL.description)) && (!world[width+1][height-1].description.equals(Tileset.AVATAR.description) && !world[width+1][height+1].description.equals(Tileset.AVATAR.description) && !world[width-1][height-1].description.equals(Tileset.AVATAR.description) && !world[width-1][height+1].description.equals(Tileset.AVATAR.description) && !world[width][height+1].description.equals(Tileset.AVATAR.description) && !world[width][height-1].description.equals(Tileset.AVATAR.description) && !world[width+1][height].description.equals(Tileset.AVATAR.description) && !world[width-1][height].description.equals(Tileset.AVATAR.description))) {
                world[width][height] = Tileset.MOUNTAIN;
                int[] temp = {width, height};
                result.add(temp);
                x--;
            }
            else {
                width = RANDOM.nextInt(WIDTH);
                height = RANDOM.nextInt(HEIGHT);
            }
            y--;
        }
        return result;
    }
    public static void generate_new_world() {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        for (int x = 2; x < 10; x += 1) {
            for (int y = HEIGHT-10; y < HEIGHT-5; y += 1) {
                world[x][y] = Tileset.FLOOR;
            }
        }
        borderize(world);
        randomLockedKey(world);
        int[] find_nucleation_point = randomNucleationPoint(world);
        trackMoves(find_nucleation_point,world,ter);
    }

    public static void mainUsingDrawWorld() {
        PersistenceData pd = null;
        String seed = "N999S";
        //StdAudio.play("grand-final-orchestral-tutti-9927.mp3");
        /*
        str = "N999SDDDWWWDDD";
        String[] splitCommands = World.processCommands(str);
        seed = splitCommands[0];
        String movements = splitCommands[1];
        String saveQuit = splitCommands[2];
        */
        int menuOptions = 0;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        menuOptions = menuLine(ter);
        TETile[][] world = null;
        int[] find_nucleation_point = null;
        if (menuOptions == 0) {
            seed = getSeed(ter);
            deletePersistence();
            setupPersistence();
            Random randGen = World.createRandomGen(seed);
            Map<Integer, World.Room> rooms = World.makeRooms(randGen);
            world = World.drawWorld(rooms, randGen);
            //System.out.println(world[1][13].character());
            randomLockedKey(world);
            //before doing this, find out what the player want avatar as.
            find_nucleation_point = randomNucleationPoint(world);
            checkNull(world, 1);
            pd = new PersistenceData();
            pd.world = world;
            pd.find_nucleation_point = find_nucleation_point;

            int[] x = getAvatar(world);
            ArrayList<int[]> things = createLightsource(3 ,world,x);
            for (int b = 0;b < things.size();b++) {
                FiatLux(things.get(b),world,find_nucleation_point);
            }
            updating(x,world);
            checkNull(world, 2);
            make_coins(20,world);
            makeSpikes(2, world);
            checkNull(world, 3);
            ter.renderFrame(world);
            PlusWorld.now = Tileset.avatar1;
            trackMovement(find_nucleation_point, world, ter, pd);
        } else if (menuOptions == 1) {
            // TBD: do loading here (world, seed, and the room behind locked door, ...)
            System.out.println("load persistence");
            pd = loadPersistence();
            System.out.println("DEBUG " + pd.world.length + " " + pd.world[0].length + " " + pd.coins);
            world = pd.world;
            checkNull(world, 4);
            int[] x = getAvatar(world);
            PlusWorld.coins = pd.coins;
            PlusWorld.healthbar = pd.healthbar;
            PlusWorld.prev = pd.prev;
            ter.renderFrame(world);
            trackMovement(x, world, ter, pd);
            System.out.println("PlusWorld.mainUsingDrawWorld");
            //System.out.println(world[1][13].character());
        }

        //if (menuOptions < 2) {

        //}

    }

    //play with main to find out in detail about implementations.
    public static void main(String[] args) {
        PersistenceData pd = null;
        int menuOptions = 0;
        Random randGen = new Random(SEED);
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        menuOptions = menuLine(ter);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        for (int x = 5; x < 10; x += 1) {
            for (int y = 6; y < 10; y += 1) {
                world[x][y] = Tileset.FLOOR;
            }
        }

        for (int x = 30; x < WIDTH-5; x += 1) {
            for (int y = 15; y < HEIGHT-5; y += 1) {
                world[x][y] = Tileset.FLOOR;
            }
        }

        for (int x = 2; x < 10; x += 1) {
            for (int y = HEIGHT-10; y < HEIGHT-5; y += 1) {
                world[x][y] = Tileset.FLOOR;
            }
        }

        randomizedWalking(world,7,8,30,15, randGen);
        randomizedWalking(world,6,HEIGHT-7,7,8, randGen);
        borderize(world);
        randomLockedKey(world);

        int[] find_nucleation_point = randomNucleationPoint(world);
        int[] x = getAvatar(world);
        ArrayList<int[]> things = createLightsource(3,world,x);
        for (int b = 0;b < things.size();b++) {
            FiatLux(things.get(b),world,find_nucleation_point);
        }
        updating(x,world);

        make_coins(20,world);
        makeSpikes(2, world);
        ter.renderFrame(world);
        trackMovement(find_nucleation_point,world,ter, pd);




        // draws the world to the screen

    }

    // below are persistence control
    public static void setupPersistence() {
        if (!PERSISTENCE_FILE.exists()) {
            try {
                PERSISTENCE_FILE.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void deletePersistence() {
        if (PERSISTENCE_FILE.exists()) {
            PERSISTENCE_FILE.delete();
        }
    }

    public static void updatePersistence(PersistenceData pd) {
        Utils.writeObject(PERSISTENCE_FILE, pd);
    }

    public static PersistenceData loadPersistence() {
        PersistenceData pd = Utils.readObject(PERSISTENCE_FILE, PersistenceData.class);
        return pd;
    }

    public static void checkNull(TETile[][] world, int i) {
        int numXTiles = world.length;
        int numYTiles = world[0].length;
        boolean found = false;
        for (int x = 0; x < numXTiles; x += 1) {
            for (int y = 0; y < numYTiles; y += 1) {
                if (world[x][y] == null) {
                    System.out.println(i + " found null tile at " + "(" + x + ", " + y + ")");
                    found = true;
                }
            }
        }
        System.out.println(i + " DEBUG found flag is " + found);
    }
    public static class PersistenceData implements Serializable {
        TETile[][] world;
        TETile now;
        TETile prev;
        int[] find_nucleation_point;
        int coins;
        int healthbar;
    }


}