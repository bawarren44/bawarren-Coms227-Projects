package hw1;

/**
 * The purpose of this class is to simulate operations done by a printer, with
 * the usage of paper and ink in printer.
 * 
 * @author Brad Warren: bawarren@iastate.edu
 *
 */
public class Printer {
	/**
	 * Capacity, in ounces, of a new ink cartridge.
	 */
	public static final double INK_CAPACITY = 2.0;

	/**
	 * Amount of ink, in ounces, used per printed page.
	 */
	public static final double INK_USAGE = 0.0023;

	/**
	 * Amount of paper the printer can hold.
	 */
	private int capacityPaper;

	/**
	 * Amount of paper used by the printer.
	 */
	private int paperUse;

	/**
	 * Amount of sheets within the printer.
	 */
	private int sheets;

	/**
	 * Amount of ink within the printer.
	 */
	private double ink;

	/**
	 * Constructor: it creates a printer at a given capacity of sheets and a full
	 * ink cartridge.
	 * 
	 * @param givenCapacity given amount of paper the printer can hold.
	 */
	public Printer(int givenCapacity) {
		capacityPaper = givenCapacity;
		ink = INK_CAPACITY;
		sheets = 0;
	}

	/**
	 * Constructor: it creates a printer at a given capacity of sheets, a full ink
	 * cartridge as well as given number of sheets inside.
	 * 
	 * @param givenCapacity       given amount of paper the printer can hold.
	 * @param givenNumberOfSheets given amount of paper provided within printer.
	 */
	public Printer(int givenCapacity, int givenNumberOfSheets) {
		capacityPaper = givenCapacity;
		sheets = givenNumberOfSheets;
		ink = INK_CAPACITY;
	}

	/**
	 * Adding sheets to the printer without exceeding its capacity.
	 * 
	 * @param additionalSheets amount of sheets being added.
	 */
	public void addPaper(int additionalSheets) {
		int paper;
		paper = sheets + additionalSheets;
		sheets = Math.min(capacityPaper, paper);
	}

	/**
	 * Returning the number of sheets currently in the printer.
	 * 
	 * @return returns sheets.
	 */
	public int getCurrentPaper() {
		return sheets;
	}

	/**
	 * Returns the total amount of sheets used since construction of the printer.
	 * 
	 * @return returns total paper used.
	 */
	public int getTotalPaperUse() {
		return paperUse;
	}

	/**
	 * Determines whether there is enough ink to print another page, represented by
	 * if ink is lower than INK_USAGE then return true.
	 * 
	 * @return returns whether ink is out or not.
	 */
	public boolean isInkOut() {
		return ink < 0.0023;
	}

	/**
	 * Restores ink to capacity if ink is out.
	 */
	public void replaceInk() {
		ink = INK_CAPACITY;
	}

	/**
	 * Prints pages in one sided mode, using the appropriate number of sheets and a
	 * corresponding amount of ink.
	 * 
	 * @param numberOfPages pages requested to print.
	 */
	public void print(int numberOfPages) {
		int numberOfSheetsRequested;
		numberOfSheetsRequested = Math.min(sheets, numberOfPages);
		sheets -= numberOfSheetsRequested;
		paperUse += numberOfSheetsRequested;
		ink = ink - (INK_USAGE * numberOfSheetsRequested);
	}

	/**
	 * Prints pages in two sided mode, using the appropriate number of sheets and a
	 * corresponding amount of ink.
	 * 
	 * @param numberOfPages pages requested to print.
	 */
	public void printTwoSided(int numberOfPages) {
		int page;
		page = numberOfPages / 2 + numberOfPages % 2;
		int numberOfSheetsRequested;
		numberOfSheetsRequested = Math.min(sheets, page);
		sheets -= numberOfSheetsRequested;
		paperUse += numberOfSheetsRequested;
		ink = ink - (INK_USAGE * numberOfSheetsRequested * 2);
	}

}
