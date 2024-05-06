package interviews.maze;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MazeGameTest {
	MazeGame game = new MazeGame();

	/**
	 * 0 0 1
	 * 1 0 1
	 * 1 0 0
	 */
	@Test
	public void solveStartEndSame() {
		MazeGame.Maze maze = game.new Maze();
		maze.startXY=game.new Coord(0,0);
		maze.endXY = game.new Coord(0,0);
		maze.maze = new int[][]{{0,0,1},{1,0,1},{1,0,0}};
		maze.solveBST();
		assertTrue(maze.solved);
	}

	@Test
	public void mazeSolved() {
		MazeGame.Maze maze = game.new Maze();
		maze.startXY=game.new Coord(0,0);
		maze.endXY = game.new Coord(2,2);
		maze.size=game.new Size(3,3);
		maze.maze = new int[][]{{0,0,1},{1,0,1},{1,0,0}};
		maze.solveBST();
		assertTrue(maze.solved);		
	}

/**
 * 0 0 1
 * 1 1 1
 * 1 1 0
 */

	@Test
	public void mazeNotSolved() {
		MazeGame.Maze maze = game.new Maze();
		maze.startXY=game.new Coord(0,0);
		maze.endXY = game.new Coord(2,2);
		maze.size=game.new Size(3,3);
		maze.maze = new int[][]{{0,0,1},{1,1,1},{1,1,0}};
		maze.solveBST();
		assertFalse(maze.solved);
	}


/**
 * 0 0 1
 * 1 0 1
 * 0 0 0
 */

	@Test
	public void eastOfWrapped() {
		MazeGame.Maze maze = game.new Maze();
		maze.size=game.new Size(3,3);
		maze.maze = new int[][]{{0,0,1},{1,0,1},{0,0,0}};
		assertEquals(game.new Coord(0,2),maze.checkPassageEastOf(game.new Coord(2,2)).get());
	}

	@Test
	public void eastOfNotWrapped() {
		MazeGame.Maze maze = game.new Maze();
		maze.size=game.new Size(3,3);
		maze.maze = new int[][]{{0,0,1},{1,0,1},{0,0,0}};
		assertEquals(game.new Coord(2,2),maze.checkPassageEastOf(game.new Coord(1,2)).get());
	}

	@Test
	public void southOfWrapped() {
		MazeGame.Maze maze = game.new Maze();
		maze.size=game.new Size(3,3);
		maze.maze = new int[][]{{0,0,1},{1,0,1},{0,0,0}};
		assertEquals(game.new Coord(0,0),maze.checkPassageSouthOf(game.new Coord(0,2)).get());
	}

	@Test
	public void westOfWrapped() {
		MazeGame.Maze maze = game.new Maze();
		maze.size=game.new Size(3,3);
		maze.maze = new int[][]{{0,0,0},{1,0,1},{0,0,0}};
		assertEquals(game.new Coord(2,0),maze.checkPassageWestOf(game.new Coord(0,0)).get());
	}

}
