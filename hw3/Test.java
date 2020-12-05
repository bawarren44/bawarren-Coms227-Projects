package hw3;

import api.Descriptor;
import api.Direction;
import api.StringUtil;
import api.Cell;
import ui.ConsoleUI;

public class Test {
	public static void main(String[] args) {

//		CS227Quell game = new CS227Quell(ConsoleUI.test4, new GameSupport());
//		StringUtil.printGrid(game);
//		int i = game.getNextRow(7, 3, Direction.DOWN, false);
//		int j = game.getNextColumn(7, 3, Direction.RIGHT, false);
//		System.out.println(i);
//		System.out.println(j);
//		System.out.println(game.getCurrentColumn());
//		System.out.println(game.getCurrentRow());
//		Cell[] cells = game.getCellSequence(Direction.LEFT);
//		System.out.println();
//		StringUtil.printCellArray(cells);
		
//		GameSupport support = new GameSupport();
//		 String test = "+.@$o..@.#";
//		 api.Cell[] cells = StringUtil.createFromString(test);
//		 StringUtil.printCellArray(cells);
//		System.out.print(support.findRightmostMovableCell(cells, 3));

		
		//GETCELLSEQUENCE
		
		String[] test = {
				 "##.###",
				 "##$..#",
				 "#.#..#",
				 "#.@@.#",
				 "#....#",
				 "##.###"
				 };
				 CS227Quell game = new CS227Quell(test, new GameSupport());
				 StringUtil.printGrid(game);
				 Cell[] cells = game.getCellSequence(Direction.UP);
				 System.out.println();
				 StringUtil.printCellArray(cells);
				 System.out.println("Expected:" + " " + "$...@#");

		
		
		
//				 GameSupport support = new GameSupport();
//				 String tests = "$.@.o..@.#";
//				 Cell[] cellss = StringUtil.createFromString(tests);
//				 StringUtil.printCellArray(cellss);
//				 support.shiftPlayer(cellss, null, Direction.DOWN);
//				 StringUtil.printCellArray(cellss);

//		CS227Quell game = new CS227Quell(ConsoleUI.test5, new GameSupport());
//		System.out.println();
//		StringUtil.printGrid(game);
//		 Cell[] test = StringUtil.createFromString("xxxOO$#");
//		 game.setCellSequence(test, Direction.LEFT);
//		 System.out.println();
//		 StringUtil.printGrid(game);
		
		
		
//		GameSupport support = new GameSupport();
//		 String test = "$.@.o+@+-+.o";
//		 Cell[] cells = StringUtil.createFromString(test);
//		 StringUtil.printCellArray(cells);
//		 support.shiftMovableBlocks(cells, null);
//		 StringUtil.printCellArray(cells);
	}
}

