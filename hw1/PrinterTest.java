package hw1;

public class PrinterTest {
	public static void main(String[] args) {
		Printer p = new Printer(100);

		// adding paper should increase paper in printer
		p.addPaper(10);
		p.addPaper(15);
		System.out.println(p.getCurrentPaper()); // expected 25
		System.out.println(p.isInkOut()); // expected false

		// printing should reduce the paper and increase the total
		p.print(5);
		System.out.println(p.getCurrentPaper()); // expected 20
		p.print(10);
		System.out.println(p.getCurrentPaper()); // expected 10
		System.out.println(p.getTotalPaperUse()); // expected 15

		// paper should not go below zero
		p.print(10);
		System.out.println(p.getCurrentPaper()); // expected 0
		System.out.println(p.getTotalPaperUse()); // expected 25

		// can't add more paper than the maximum
		p.addPaper(150);
		System.out.println(p.getCurrentPaper()); // expected 100

		// this prints only 100 pages so should not run out of ink
		p.print(10000);
		System.out.println(p.isInkOut()); // expected false

		// check running out of ink, should be able to print 869 pages
		Printer p2 = new Printer(2000, 1000);
		p2.print(868);
		System.out.println(p.isInkOut()); // expected false
		p2.print(1);
		System.out.println(p2.isInkOut()); // expected true
		p2.replaceInk();
		System.out.println(p2.isInkOut()); // expected false

		Printer z = new Printer(100, 10);
		z.printTwoSided(10);
		System.out.println(z.getCurrentPaper());

	}
}
