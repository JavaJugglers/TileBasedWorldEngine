package byow.TileEngine;

import java.awt.Color;
import java.io.Serializable;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset implements Serializable {
    public static TETile AVATAR = new TETile('@', Color.white, Color.black, "you");
    public static final TETile WALL = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "wall");
    public static final TETile FLOOR = new TETile('·', new Color(128, 192, 128), Color.black,
            "floor");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
    public static TETile lamp = new TETile('*', Color.yellow, Color.black, "lamp");
    public static TETile LampAdvanced = new TETile('*', Color.yellow, Color.PINK.darker(), "lamp1");
    public static TETile FLOORAdvanced0 = new TETile('·', new Color(128, 192, 128), Color.PINK.darker().darker(),
            "floor0");
    public static TETile FLOORAdvanced1 = new TETile('·', new Color(128, 192, 128), Color.PINK.darker().darker().darker(),
            "floor1");
    public static TETile FLOORAdvanced2 = new TETile('·', new Color(128, 192, 128), Color.PINK.darker().darker().darker().darker(),
            "floor2");
    public static TETile FLOORAdvanced3 = new TETile('·', new Color(128, 192, 128), Color.PINK.darker().darker().darker().darker().darker(),
            "floor3");
    public static TETile FLOORAdvanced4 = new TETile('·', new Color(128, 192, 128), Color.PINK.darker().darker().darker().darker().darker().darker(),
            "floor4");
    public static TETile avatar1 = new TETile('K', new Color(128, 192, 128), Color.PINK.darker().darker().darker().darker().darker(),
            "floor3");

}