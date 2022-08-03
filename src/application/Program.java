package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter the file path: ");
		String path = sc.nextLine();

		System.out.println();

		List<Sale> list = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			System.out.println("Total sales by seller:");

			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				list.add(new Sale(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2],
						Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));

				line = br.readLine();
			}

			Set<String> names = new HashSet<>();
			names = list.stream().map(s -> s.getSeller()).collect(Collectors.toSet());

			for (String seller : names) {

				double sum = list.stream()
						.filter(x -> x.getSeller().equals(seller))
						.map(x -> x.getTotal())
						.reduce(0.0,	(x, y) -> x + y);

				System.out.printf("%s - R$ %.2f%n", seller, sum);
			}

		}

		catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}

		sc.close();
	}
}