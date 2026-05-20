package tarea_final;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MetodosComunes {

	private static Scanner sc = new Scanner(System.in);
	private static Scanner scLetra = new Scanner(System.in);

	public static int introduceNumero(String txt) {

		int num = 0;
		boolean rep = true;

		do {
			rep = true;
			try {
				System.out.println(txt);
				num = sc.nextInt();
				if (num <= 0) {
					System.err.println("ERROR: El número tiene que ser mayor a 0");
				} else {
					rep = false;
				}
			} catch (InputMismatchException e) {
				System.err.println("ERROR: Debes de introducir un número");
				sc.next();
			} catch (Exception e) {
				System.err.println("ERROR");
			}
		} while (rep);

		return num;
	}

	public static String introduceTexto(String txt) {

		System.out.println(txt);
		String texto = scLetra.nextLine().trim();

		return texto;
	}
}
