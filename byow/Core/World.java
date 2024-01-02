package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;

//import static byow.Core.Utils.*;

public class World {
    //TBD: check room size
    private static final int MAX_ROOM_WIDTH = 12;
    private static final int MAX_ROOM_HEIGHT = 12;
    private static final long SEED = 1303633;
    private static final Random RANDOM = new Random(SEED);
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;
    static final File CWD = new File(".");
    static final File SEED_COMMANDS_FILE = Utils.join(CWD, "seedCommands.txt");
    static final File TILES_FILE = Utils.join(CWD, "tiles");
    //private room class containing starting/ending coordinates

    // makes a random number of rooms and saves their coordinates into a map
    public static Map<Integer, Room> makeRooms(Random randGen) {
        // randomize number of rooms ( + 2 in case the random number is 0 or 1?)
        // int numRooms = RANDOM.nextInt(8) + 2;
        Map<Integer, Room> rooms = new HashMap<Integer, Room>();
        int numRooms = getRandNumber(8, 16, randGen);
        Room r;
        for (int i = 0; i < numRooms; i++) {
            r = genDiagonalVertices(randGen);
            while (checkOverlap(rooms, r)) {
                r = genDiagonalVertices(randGen);
            }
            rooms.put(i, r);
        }
        System.out.println(rooms);
        return rooms;
    }

    private static Room genDiagonalVertices(Random randGen) {
        int x0, y0, x1, y1, width, height;
        x0 = getRandNumber(1, WIDTH - MAX_ROOM_WIDTH, randGen); // TBD: might need to fix -1 portion
        y0 = getRandNumber(1, HEIGHT - MAX_ROOM_HEIGHT, randGen);
        width = getRandNumber(1, MAX_ROOM_WIDTH, randGen);
        height = getRandNumber(1, MAX_ROOM_HEIGHT, randGen);
        x1 = x0 + width;
        y1 = y0 + height;
        return new Room(x0, y0, x1, y1);
    }

    private static int getRandNumber(int lower, int upper, Random r) {
        // int result = RANDOM.nextInt(upper);
        int result = r.nextInt(upper);
        if (result < lower) {
            return lower;
        }
        return result;
    }

    // checks if existing rooms in the rooms Map overlap with each other
    private static boolean checkOverlap(Map<Integer, Room> rooms, Room r) {
        if (rooms.isEmpty()) { // empty map = no rooms
            return false;
        }
        Room o;
        for (int i = 0; i < rooms.size(); i++) {
            o = rooms.get(i);
            // check if o's vertex is in room r
            if (checkVertexInRoom(o.getX0(), o.getY0(), r)
                    || checkVertexInRoom(o.getX1(), o.getY0(), r)
                    || checkVertexInRoom(o.getX0(), o.getY1(), r)
                    || checkVertexInRoom(o.getX1(), o.getY1(), r)) {
                return true;
            }
            // check if r's vertex is in room o
            if (checkVertexInRoom(r.getX0(), r.getY0(), o)
                    || checkVertexInRoom(r.getX1(), r.getY0(), o)
                    || checkVertexInRoom(r.getX0(), r.getY1(), o)
                    || checkVertexInRoom(r.getX1(), r.getY1(), o)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkVertexInRoom(int x, int y, Room r) {
        int x0 = r.getX0();
        int y0 = r.getY0();
        int x1 = r.getX1();
        int y1 = r.getY1();
        return (x0 <= x && x <= x1) && (y0 <= y && y <= y1);
    }
/*
    private static void randomizedWalking(TETile[][] world, int beginningWidth, int beginningHeight,
                                          int endingWidth, int endingHeight, Random randGen) {
        iterativeCallHelper(world, beginningWidth, beginningHeight,
                endingWidth, endingHeight, randGen);
    }

    private static void iterativeCallHelper(TETile[][] world, int beginningWidth,
            int beginningHeight, int endingWidth, int endingHeight, Random randGen) {
        int finalHeight = beginningHeight + randomizedNumber(randGen);
        int finalWidth = beginningWidth + randomizedNumber(randGen);
        while (!(beginningHeight == endingHeight && beginningWidth == endingWidth)) {
            if ((finalWidth < WIDTH && finalWidth >= 0)
                    && (finalHeight < HEIGHT && finalHeight >= 0)) {
                if ((Math.abs(finalHeight - endingHeight))
                        < (Math.abs(beginningHeight - endingHeight))) {
                    beginningHeight = finalHeight;
                    world[beginningWidth][beginningHeight] = Tileset.FLOOR;
                } else if ((Math.abs(finalWidth - endingWidth))
                        < (Math.abs(beginningWidth - endingWidth))) {
                    beginningWidth = finalWidth;
                    world[beginningWidth][beginningHeight] = Tileset.FLOOR;
                }
            }
            finalHeight = beginningHeight + randomizedNumber(randGen);
            finalWidth = beginningWidth + randomizedNumber(randGen);
        }
    }

    private static int randomizedNumber(Random randGen) {
        int tileNum = randGen.nextInt(2);
        switch (tileNum) {
            case 0:
                return 1;
            case 1:
                return -1;
            default:
                return 0;
        }
    }


    private static void borderize(TETile[][] world) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if ((x + 1 < WIDTH) && world[x + 1][y] == Tileset.FLOOR
                        && world[x][y] != Tileset.FLOOR) {
                    world[x][y] = Tileset.WALL;
                } else if ((x - 1 >= 0) && world[x - 1][y] == Tileset.FLOOR
                        && world[x][y] != Tileset.FLOOR) {
                    world[x][y] = Tileset.WALL;
                } else if ((y - 1 >= 0) && world[x][y - 1] == Tileset.FLOOR
                        && world[x][y] != Tileset.FLOOR) {
                    world[x][y] = Tileset.WALL;
                } else if ((y + 1 < HEIGHT) && world[x][y + 1] == Tileset.FLOOR
                        && world[x][y] != Tileset.FLOOR) {
                    world[x][y] = Tileset.WALL;
                }
            }
        }
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if ((y - 1 >= 0) && (x - 1 >= 0) && (y + 1 < HEIGHT) && (x + 1 < WIDTH)
                        && (world[x][y] == Tileset.FLOOR)
                        && (world[x - 1][y + 1] == Tileset.NOTHING
                        || world[x + 1][y + 1] == Tileset.NOTHING
                        || world[x - 1][y - 1] == Tileset.NOTHING
                        || world[x + 1][y - 1] == Tileset.NOTHING)) {
                    if (world[x - 1][y + 1] == Tileset.NOTHING) {
                        world[x - 1][y + 1] = Tileset.WALL;
                    } else if (world[x + 1][y + 1] == Tileset.NOTHING) {
                        world[x + 1][y + 1] = Tileset.WALL;
                    } else if (world[x - 1][y - 1] == Tileset.NOTHING) {
                        world[x - 1][y - 1] = Tileset.WALL;
                    } else if (world[x + 1][y - 1] == Tileset.NOTHING) {
                        world[x + 1][y - 1] = Tileset.WALL;
                    }
                }
            }
        }
    }
*/

    public static Random createRandomGen(String seed) {
        long seedLong = Long.parseLong(seed);
        Random randGen = new Random(seedLong);
        return randGen;
    }
    public static TETile[][] drawWorld(Map<Integer, Room> rooms, Random randGen) {
        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        //below edited by me
        // Map<Integer, Room> rooms = makeRooms(world, randGen);
        Room o;
        for (int i = 0; i < rooms.size(); i++) {
            o = rooms.get(i);
            for (int x = o.getX0(); x <= o.getX1(); x++) {
                for (int y = o.getY0(); y <= o.getY1(); y++) {
                    world[x][y] = Tileset.FLOOR;
                }
            }
        }
        Room a, b;
        int aX, aY, bX, bY;
        a = rooms.get(0);
        for (int i = 0; i < rooms.size(); i++) {
            b = rooms.get(i);
            aX = (a.getX0() + a.getX1()) / 2;
            aY = (a.getY0() + a.getY1()) / 2;
            bX = (b.getX0() + b.getX1()) / 2;
            bY = (b.getY0() + b.getY1()) / 2;
            PlusWorld.randomizedWalking(world, aX, aY, bX, bY, randGen);
            a = b;
        }
        PlusWorld.borderize(world);

        return world;
    }

    public static int[] moveTo(int[] cur, TETile[][] world, char dir) {
        int[] next = new int[2];
        int deltaX = 0, deltaY = 0;
        switch(dir) {
            case 'w':
            case 'W':
                deltaY = 1;
                break;
            case 's':
            case 'S':
                deltaY = -1;
                break;
            case 'a':
            case 'A':
                deltaX = -1;
                break;
            case 'd':
            case 'D':
                deltaX = 1;
                break;
            default:
                break;
        }
        System.out.println(deltaX + "  " + deltaY);
        int nextX = cur[0] + deltaX;
        int nextY = cur[1] + deltaY;
        if ((world[nextX][nextY] != Tileset.WALL) && (world[nextX][nextY] != Tileset.LOCKED_DOOR)) {
            world[nextX][nextY] = Tileset.AVATAR;
            world[cur[0]][cur[1]] = Tileset.FLOOR;
            next[0] = nextX;
            next[1] = nextY;
        } else {
            next[0] = cur[0];
            next[1] = cur[1];
        }
        return next;
    }

    public static void placeAvatar(TETile[][] world, Map<Integer, Room> rooms,
                                   Random randGen, String movements, String saveQuit) {
        int roomNumber = getRandNumber(0, rooms.size(), randGen);
        Room o = rooms.get(roomNumber);
        int[] cur = new int[2];
        cur[0] = getRandNumber(o.getX0(), o.getX1(), randGen);
        cur[1] = getRandNumber(o.getY0(), o.getY1(), randGen);
        world[cur[0]][cur[1]] = Tileset.AVATAR;
        char dir;
        for (int i = 0; i < movements.length(); i = i + 1) {
            dir = movements.charAt(i);
            cur = moveTo(cur, world, dir);
        }
        if (saveQuit.equals(":Q")) {
            System.out.println("DEBUG **: Should do save and quite here!");
        }
    }

    public static void setupPersistence() {
        if(!SEED_COMMANDS_FILE.exists()) {
            try {
                SEED_COMMANDS_FILE.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String[] separateSeed(String str) { // N1234SWWDD ==> "1234", "WWDD"
        String[] split = new String[3];
        split[0] = "";
        split[1] = "";
        split[2] = "";
        int firstS = str.indexOf('S');
        split[0] = str.substring(1, firstS);
        split[1] = str.substring(firstS + 1);
        return split;
    }

    public static String[] processCommands(String str) {
        String strUpper = str.toUpperCase();
        String[] split = new String[3];
        String prevCommands, curCommands;
        boolean saveQuit = false;
        int indexOfColon = 0, indexOfQ = 0;
        if (strUpper.contains(":Q")) {
            saveQuit = true;
            indexOfColon = strUpper.indexOf(':');
            indexOfQ = strUpper.indexOf('Q');
        }

        if (strUpper.charAt(0) == 'L') {  // 'L'oad command
            prevCommands = Utils.readContentsAsString(SEED_COMMANDS_FILE);
            split = separateSeed(prevCommands.toUpperCase());
            if (saveQuit) {
                if (indexOfColon == 1 && indexOfQ == 2) {
                    curCommands = "";
                } else {
                    curCommands = strUpper.substring(1, indexOfColon);
                }
            } else {
                curCommands = strUpper.substring(1);
            }
            split[1] = split[1] + curCommands;
        }

        if (strUpper.charAt(0) == 'N') {  // 'New' Command
            deletePersistence();
            if (saveQuit) {
                split = separateSeed(strUpper.substring(0, indexOfColon));
            } else {
                split = separateSeed(strUpper);
            }
        }
        if (saveQuit) {
            split[2] = ":Q";
        }
        System.out.println("DEBUG 1. seed " + split[0]);
        System.out.println("DEBUG 2. movements " + split[1]);
        System.out.println("DEBUG 3. saveQuit " + saveQuit);
        if (saveQuit) {
            updatePersistence(split[0], split[1]);
        }
        return split;
    }

    public static void updatePersistence(String seed, String movements) {
        deletePersistence();
        setupPersistence();
        String contents = "N" + seed + "S" + movements;
        Utils.writeContents(SEED_COMMANDS_FILE, contents);
    }

    public static void deletePersistence() {
        if(SEED_COMMANDS_FILE.exists()) {
            SEED_COMMANDS_FILE.delete();
        }
    }

    public static void writeTiles(TETile[][] world) {
        if(!TILES_FILE.exists()) {
            try {
                TILES_FILE.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Utils.writeObject(TILES_FILE, world);
    }

    public static boolean checkWorld(TETile[][] world) {
        TETile[][] refWorld = Utils.readObject(TILES_FILE, TETile[][].class);
        int width = refWorld.length;
        int height = refWorld[0].length;
        for(int i = 0; i < width; i += 1) {
            for(int j = 0; j < height; j += 1) {
                if (refWorld[i][j].character() != world[i][j].character()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void runRenderAndCheck(TETile[][] world, boolean render, boolean check, boolean writeTiles) {
        if (render) {
            TERenderer ter = new TERenderer();
            ter.initialize(WIDTH, HEIGHT);
            ter.renderFrame(world);
        }
        if (writeTiles) {
            writeTiles(world);
        }
        if (check) {
            if (checkWorld(world)) {
                System.out.println("DEBUG 4. Worlds are the same");
            } else {
                System.out.println("DEBUG 4. Worlds are different");
            }
        }
    }

    public static void runBatch(String str, boolean render, boolean check, boolean writeTiles) {
        Engine engine = new Engine();
        TETile[][] world = engine.interactWithInputString(str);
        runRenderAndCheck(world, render, check, writeTiles);
    }

    public static void main(String[] args) {
        boolean checkTiles = false;
        boolean writeTiles = false;
        setupPersistence();
        String str;

        // 1.
        System.out.println("*** 1 ***");
        str = "N999SDDDWWWDDD";
        runBatch(str, true, checkTiles, writeTiles);

        // 2.
        System.out.println("*** 2 ***");
        str = "N999SDDD:Q";
        runBatch(str, false, false, writeTiles);
        str = "LWWWDDD";
        runBatch(str, false, checkTiles, writeTiles);
        /*
        // 3.
        System.out.println("*** 3 ***");
        str = "N999SDDD:Q";
        runBatch(str, false, false, writeTiles);
        str = "LWWW:Q";
        runBatch(str, false, false, writeTiles);
        str = "LDDD:Q";
        runBatch(str, false, checkTiles, writeTiles);

        // 4.
        System.out.println("*** 4 ***");
        str = "N999SDDD:Q";
        runBatch(str, false, false, writeTiles);
        str = "L:Q";
        runBatch(str, false, false, writeTiles);
        str = "L:Q";
        runBatch(str, false, false, writeTiles);
        str = "LWWWDDD";
        runBatch(str, true, checkTiles, writeTiles);
        */
    }

    public static class Room {
        private int _x0;
        private int _y0;
        private int _x1;
        private int _y1;

        private int getX0() {
            return this._x0;
        }
        private int getY0() {
            return this._y0;
        }

        private int getX1() {
            return this._x1;
        }

        private int getY1() {
            return this._y1;
        }
        Room(int x0, int y0, int x1, int y1) {
            this._x0 = x0;
            this._y0 = y0;
            this._x1 = x1;
            this._y1 = y1;
        }

        @Override
        public String toString() {
            return "(" + this._x0 + "," + this._y0 + "-" + this._x1 + "," + this._y1 + ")";

        }
    }
}