build: retele reclame registre

run_retele:
	java Retele

run_reclame:
	java Reclame

run_registre:
	java Registre

retele: Retele.java TaskReteleSolver.java Task.java
	javac $^

reclame: Reclame.java TaskReclameSolver.java Task.java
	javac $^

registre: Registre.java TaskRegistreSolver.java Task.java
	javac $^

clean:
	rm -f *.class

.PHONY: build clean
