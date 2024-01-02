## Overview

This project involves creating a 2D tile-based world exploration engine. The worlds generated will be composed of a grid of tiles, offering an overhead perspective similar to games like "Zelda II". Users can explore these worlds, interacting with various objects and environments. The project is designed to manage complexity in building large systems and consists of two major deadlines: first for generating random worlds and second for enabling user interaction and exploration.

### Tile Engine (`byow.TileEngine`)

- **TERenderer.java**: Contains rendering-related methods.
- **TETile.java**: Represents tiles in the world. Do not change its character field or character() method.
- **Tileset.java**: A library of provided tiles.

### Core Package (`byow.Core`)

- **RandomUtils.java**: Utility methods for randomness.
- **Main.java**: Starts the system, reads command line arguments.
- **Engine.java**: Contains methods for system interaction.

### Features

- Pseudo-random 2D world with rooms, hallways, and possibly outdoor spaces.
- Rectangular rooms and hallways with turns or intersections.
- Random number and placement of rooms/hallways.
- Visually distinct walls and floors.
- All rooms reachable.
- Unique world generation each time.

### Tileset and Rendering

- TETile[][] world: 2D array of tiles, where `world[0][0]` is the bottom left tile.
- Support for graphical tiles: 16x16 PNG format images.
- Each TETile must have a unique character representation.

### Starting the Program

- **Main method in Main class**: Determines interaction mode based on user input.
- **Phase 1**: Focus on `interactWithInputString()` for generating worlds.
- **Phase 2**: Implement `interactWithKeyboard()` for interactivity.

### User Interface (UI)

- Display a 2D grid of tiles.
- Heads Up Display (HUD) showing tile information and possibly other game elements.

### User Interaction

- Avatar control using W, A, S, D keys.
- Deterministic system: Same key-press sequence yields the same results.
- Support for saving and loading game states.
- StdDraw limitations: No key combinations, support for unicode characters only.

### Saving and Loading

- Capability to save and load the world state, including the state of the random number generator.
- The system must return the exact same TETile[][] for a given sequence of inputs.

### Interacting with Input Strings

- Support for movement commands within input strings (e.g., N543SWWWWAA).
- Handling of saving and loading in replay strings.

