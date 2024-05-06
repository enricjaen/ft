package interviews.maze;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;


public class MazeGame {

	public static void main(String[] args) {
		String input = "";
		try {
			input = new String ( Files.readAllBytes( Paths.get(args[0]) ) );
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		Maze maze = new MazeGame().new Maze();
		maze.build(input);
		maze.solveBST();
		maze.buildSolution();
		maze.printSolution();
	}

	public class Maze {
		String input;
		int[][] maze;  //[y][x]
		Coord startXY;
		Coord endXY;
		Size size;
		Deque<Coord> solution;
		// Deque supports insertion at beg and end. Can be FIFO, or LIFO stack
		// removeFirst removeLast poll(removes head of queue) add
		char[][] output;
		boolean solved;

        class VisitStatus {
            Map<Coord, Coord> cellsVisited = new HashMap<>();  // pairs of cells visited (cell, parent)
            Queue<Coord> cellsToExplore = new LinkedList<Coord>();  // queue of cells to explore
            Coord visit;

            public VisitStatus() {
                cellsToExplore.add(startXY);
                cellsVisited.put(startXY,null);
            }
        }

        public void build(String input) {
			this.input = input;
			String[] lines = input.split("\r\n");
			size = new Size(lines[0].split(" "));
			startXY = new Coord(lines[1].split(" "));
			endXY = new Coord(lines[2].split(" "));

			maze = new int[size.height][size.width];

			for (int i=0; i<size.height; i++) {
				String[] line = lines[i+3].split(" ");
				for (int j=0; j<size.width; j++) {
					maze[i][j] = Integer.valueOf(line[j]);
				}
			}
		} 


		/**
		 we use a BFS approach. TODO We could structure the code to solve either by BFS or DFS

		 BST
		 map of cell visited and its parent.
		 init a queue. put start.

		 1. visit cell
		    - if first in queue is end, is solved
		 2. explore east, south, west, north (clock wise)
		 	- is is passage and not visited yet, put as visited and add it to cells to explore

		 We build the solution list by traversing the parents from end to start.
 		 */

		public void solveBST() {
            VisitStatus status = new VisitStatus();
			while(!status.cellsToExplore.isEmpty()) {
				// visit the first in queue
				status.visit = status.cellsToExplore.poll();
				// if is the exit, finish
				if (status.visit.equals(endXY)) {
					solved = true;
					break;
				}
                exploreAdjacent(status, cell -> checkPassageEastOf(cell));
                exploreAdjacent(status, cell -> checkPassageSouthOf(cell));
                exploreAdjacent(status, cell -> checkPassageWestOf(cell));
                exploreAdjacent(status, cell -> checkPassageNorthOf(cell));
            }
            buildSolutionTraversingParents(status.cellsVisited);
        }

        public void buildSolution() {
            if (!solved) return;
            output = new char[size.height][size.width];
            for (int y=0; y<size.height; y++) {
                for (int x=0; x<size.width; x++) {
                    if (maze[y][x]==1) output[y][x]='#';
                    else output[y][x]=' ';
                }
            }
            solution.removeFirst();
            output[startXY.y][startXY.x]='S';
            solution.removeLast();
            output[endXY.y][endXY.x]='E';
            while (!solution.isEmpty()) {
                Coord coord = solution.poll();
                output[coord.y][coord.x] = 'X';
            }
        }


        public void printMaze(){
			for (int i = 0; i < maze.length; i++) {         
				for (int j = 0; j < maze[i].length; j++) {  
					System.out.print(maze[i][j] + " ");
				}
				System.out.println(); 
			}
		}

		public void printSolution(){
			if (!solved) {
				System.out.println("No solution found");
				return;
			}
			for (int y = 0; y < output.length; y++) {         
				for (int x = 0; x < output[y].length; x++) {  
					System.out.print(output[y][x] + " ");
				}
				System.out.println(); 
			}
		}

        // explore, putting adjacent cells in queue
        private void exploreAdjacent(VisitStatus status, Function<Coord, Optional<Coord>> checkAdjacent)  {
            Optional<Coord> adjacentWithPassage = checkAdjacent.apply(status.visit);
            if (adjacentWithPassage.isPresent() && !status.cellsVisited.containsKey(adjacentWithPassage.get())) {
                status.cellsToExplore.add(adjacentWithPassage.get());
                status.cellsVisited.put(adjacentWithPassage.get(), status.visit);
            }
        }


        private void buildSolutionTraversingParents(Map<Coord, Coord> visited) {
            solution = new ArrayDeque<>();

            if (!solved) return;
            // we build the solution list by traversing the parents from end to start.
            solution.add(endXY);
            Coord parent = visited.remove(endXY);
            while (parent!=null) {
                solution.add(parent);
                parent = visited.remove(parent);
            }
        }


        protected boolean isPassage(Coord coord) {
			return maze[coord.y][coord.x] == 0;
		}

		protected Optional<Coord> checkPassageEastOf(Coord coord) {
			Coord east = null;
			if (coord.x+1 >= size.width) {
				east = new Coord(0,coord.y);
				if (isPassage(east)) return Optional.of(east);
				return Optional.empty();
			}
			east = new Coord(coord.x+1, coord.y);
			if (isPassage(east)) return Optional.of(east);
			return Optional.empty();
		} 

		protected Optional<Coord> checkPassageSouthOf(Coord coord) {
			Coord south = null;
			if (coord.y+1 >= size.height) {
				south = new Coord(coord.x,0);
				if (isPassage(south)) return Optional.of(south);
				return Optional.empty();
			}
			south = new Coord(coord.x, coord.y+1);
			if (isPassage(south)) return Optional.of(south);
			return Optional.empty();
		} 
		
		protected Optional<Coord> checkPassageWestOf(Coord coord) {
			Coord west = null;
			if (coord.x-1 < 0) {
				west = new Coord(size.width-1,coord.y);
				if (isPassage(west)) return Optional.of(west);
				return Optional.empty();
			}
			west = new Coord(coord.x-1, coord.y);
			if (isPassage(west)) return Optional.of(west);
			return Optional.empty();
		} 

		protected Optional<Coord> checkPassageNorthOf(Coord coord) {
			Coord north = null;
			if (coord.y-1 < 0) {
				north = new Coord(coord.x,size.height-1);
				if (isPassage(north)) return Optional.of(north);
				return Optional.empty();
			}
			north = new Coord(coord.x, coord.y-1);
			if (isPassage(north)) return Optional.of(north);
			return Optional.empty();
		} 

	}



	class Size {
		int height;
		int width;

		public Size(String[] line1) {
			width = Integer.valueOf(line1[0]);
			height = Integer.valueOf(line1[1]);
		}

		public Size(int width, int height) {
			this.width = width;
			this.height = height;
		}
	}

	class Coord {
		int x;
		int y;
		public Coord(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public Coord(String[] coords) {
			x = Integer.valueOf(coords[0]);
			y = Integer.valueOf(coords[1]);
		}
		public Coord(Coord coord) {
			this.x = coord.x;
			this.y = coord.y;
		}
		@Override
		public boolean equals(Object object) {
			Coord coord = (Coord)object;
			return x == coord.x && y == coord.y;
		}
		@Override
		public int hashCode() {
			return x | (y << 15);
		}

		public String toString() {
			return "[x=" + x + ", y=" + y + "]";
		}

	}


}
