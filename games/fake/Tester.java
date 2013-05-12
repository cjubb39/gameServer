package games.fake;

import java.io.PrintWriter;
import java.util.Scanner;

import server.Game;

public class Tester implements Game {

	Scanner in;
	PrintWriter output;

	public Tester(Scanner in, PrintWriter out) {
		this.in = in;
		this.output = out;
		//this.in.useDelimiter(System.getProperty("line.separator"));
	}

	@Override
	public void play() {
		System.out.println("System (on S) Print Hello World");
		this.output.println("Hello World!");

		
		boolean indicator = true;
		while (indicator) {
			while (this.in.hasNext()) {
System.out.println(1);
				String next = this.in.next();
				System.out.println("MARK, DUDE! " + next);
				this.output.println("FROM SERVER: " + next);
				//indicator = false;
			}
		}
		
		
	}

}
