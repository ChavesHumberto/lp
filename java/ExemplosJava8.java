package br.uff.puro.labides.java8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

class Pessoa {
	public final String nome;
	public final int idade;

	public Pessoa (String n, int i) {
		this.nome = n;
		this.idade = i;
	}

	public String getNome() {
		return this.nome;
	}

	public int getIdade() {
		return this.idade;
	}

	public String toString() {
		return this.nome + ", " + this.idade;
	}

	public int compareTo(Pessoa p) {
		return this.nome.compareTo(p.nome);
	}
}

public class ExemplosJava8 {
	public static void main(String[] args) {
		List <Pessoa>pessoas = new ArrayList<Pessoa>();
		pessoas.add(new Pessoa("Fulano", 40));
		pessoas.add(new Pessoa("Ciclano", 25));
		pessoas.add(new Pessoa("Beltrano", 70));

		System.out.println("=> Vers�o Desordenada");
		for (Pessoa p : pessoas)
			System.out.println(p);

		
		// Uso do m�todo sort da classe utilit�ria Collections
		System.out.println("=> Vers�o Ordenada - Pr� Java 8");
		List <Pessoa> copia = new ArrayList<Pessoa>(pessoas);
		Collections.sort(copia, new Comparator<Pessoa>() {
			public int compare(Pessoa a, Pessoa b) {
				return a.compareTo(b);
			}
		});
		for (Pessoa p : copia)
			System.out.println(p);

		
		// Uso do m�todo default sort() da interface lista com classe aninhada
		System.out.println("=> Vers�o Ordenada");
		copia = new ArrayList<Pessoa>(pessoas);
		copia.sort(new Comparator<Pessoa>() {
			public int compare(Pessoa a, Pessoa b) {
				return a.compareTo(b);
			}
		});
		for (Pessoa p : copia)
			System.out.println(p);


		// Uso do m�todo default sort() da interface List com express�o lambda
		System.out.println("=> Vers�o Ordenada (Java 8)");
		copia = new ArrayList<Pessoa>(pessoas);
		copia.sort((Pessoa a, Pessoa b) -> a.compareTo(b));
		
		
		// Uso do m�todo default foreach() da interface Iterable com Refer�ncia ao m�todo println
		copia.forEach(System.out::println);
		
		
		// Uso do m�todo default sort() da interface List com express�o lambda com Infer�ncia de tipos
		copia = new ArrayList<Pessoa>(pessoas);
		copia.sort((a, b) -> a.compareTo(b));
		copia.forEach(System.out::println);
		// .. usando Refer�ncias a m�todos
		copia = new ArrayList<Pessoa>(pessoas);
		copia.sort(Pessoa::compareTo);
		copia.forEach(System.out::println);
				
		
		// Predicados (interfaces funcionais que retornam booleanos)
		System.out.println("=> Trabalhando com interfaces funcionais (Predicados)");
		copia = new ArrayList<Pessoa>(pessoas);
		Predicate<Pessoa> pred1 = x -> "Fulano".equals(x.nome);
		Predicate<Pessoa> pred2 = x -> x.idade == 40;
		copia.removeIf(pred1.and(pred2));
		copia.forEach(System.out::println);


		// Comparadores (interfaces funcionais) usando express�es lambda
		System.out.println("=> Trabalhando com interfaces funcionais (Comparadores)");
		copia = new ArrayList<Pessoa>(pessoas);
		Comparator<Pessoa> comp = Comparator.comparing(x -> x.nome);
		comp = comp.thenComparing(name -> name.idade);
		copia.sort(comp);
		copia.forEach(System.out::println);
		// .. concatenando comparadores (decorator)
		Comparator<Pessoa> comp1 = Comparator.comparing((Pessoa x) -> x.nome)
				.thenComparing(y -> y.idade); 
		copia.sort(comp1);
		copia.forEach(System.out::println);

		
		// Comparadores usando Refer�ncias � M�todos
		System.out.println("=> Usando refer�ncias a m�todos");
		copia = new ArrayList<Pessoa>(pessoas);
		Comparator<Pessoa> comp2 = Comparator.comparing(Pessoa::getNome)
				.thenComparing(Pessoa::getIdade);
		copia.sort(comp2);
		copia.forEach(System.out::println);
		// .. concatena��o de Comparadores
		copia = new ArrayList<Pessoa>(pessoas);
		copia.sort(Comparator.comparing(Pessoa::getNome).thenComparing(Pessoa::getIdade));
		copia.forEach(System.out::println);
		
		
		// Uso de Stream
		copia = new ArrayList<Pessoa>(pessoas);
		System.out.println("M�dia de idade: " + copia.stream().mapToInt(x -> x.getIdade()).average());
		System.out.println("M�dia de idade: " + copia.stream().mapToInt(Pessoa::getIdade).average());

		
		// Optional
		List <Pessoa>copia2 = new ArrayList<Pessoa>();
		System.out.println("M�dia de idade: " + copia2.stream().mapToInt(Pessoa::getIdade).average());
		System.out.println("M�dia de idade: " + copia2.stream().mapToInt(Pessoa::getIdade).average().orElse(0));

		
		// Filter
		System.out.println("M�dia de idade: " + copia.stream().filter(x -> x.nome.length() > 6).mapToInt(Pessoa::getIdade).average());

		
		// Parallel version
		System.out.println("M�dia de idade: " + copia.parallelStream().filter(x -> x.nome.length() > 6).mapToInt(Pessoa::getIdade).average());
	}
}
