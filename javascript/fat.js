y = 5;

function fat(x) {
	base = 1;
	function fat_aux(x1, acum) {
		// if (x1 == 0)
		if (x1 === 0) // Sugestão do https://repl.it para ===
			return acum;
		else
			return fat_aux(x1 - 1, acum*x1);
	}
	return fat_aux(x, base);
}

console.log(fat(y));

