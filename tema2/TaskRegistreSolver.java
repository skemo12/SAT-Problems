import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public final class TaskRegistreSolver extends Task {

    Integer n; // numarul de noduri / numarul de variabile
    Integer m; // numarul de muchii / numarul de relatii
    Integer k; // numarul de culori valabile / numarul de registre
    int[][] graph; // graful generat de datele de intrare
    int[][] variables; // codificarea variabilelor
    String oracleAnswer; // Raspunsul oracolului : True / False
    List<Integer> oracleNumbersDecipher; // Lista de noduri intoarsa de oracol

    /**
     * Metoda principala de apelat pentru solutionarea task-ului
     */
    @Override
    public void solve() throws IOException, InterruptedException {
        readProblemData();
        formulateOracleQuestion();
        askOracle();
        decipherOracleAnswer();
        writeAnswer();
    }
    /**
     * Citirea datelor de intrare
     */
    @Override
    public void readProblemData() throws IOException {

        // Initializeaza scanner-ul
        Scanner s = new Scanner(System.in);

        // Citirea primei linii de tipul n, m, k
        this.n = s.nextInt();
        this.m = s.nextInt();
        this.k = s.nextInt();


        // Citirea grafului
        this.graph = new int[n][n];
        for (int i = 0; i < m; i++) {
            Integer x = s.nextInt();
            Integer y = s.nextInt();
            this.graph[x - 1][y - 1] = 1;
            this.graph[y - 1][x - 1] = 1;
        }

        // Inchidere scanner
        s.close();
    }

    /**
     * Adresarea intrebarii catre oracol
     * Pregatirea fisierul sat.cnf
     */
    @Override
    public void formulateOracleQuestion() throws IOException {

         // Initializare bufferWriter
        BufferedWriter bufferWriter = new BufferedWriter(
                                    new FileWriter(Constants.ORACLE_INPUT));

         // Calculul numarului de variabile si clauze
        int variablesNumber = n * k;
        int clausesNumber = n + n * ( k * (k - 1) ) / 2  + m;

        // Initiererea oracolului
        bufferWriter.write(Constants.ORACLE_CNF_PROBLEM + " "
                                            + variablesNumber + " "
                                            + clausesNumber + "\n");

        // Codificarea variabilelor 
        variables = new int[n][k];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                variables[i][j] = i * k + j + 1;
            }
        }

        // Clauza I: Un nod sa aiba macar o culoare
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                bufferWriter.write(variables[i][j] + " "); 

            }
            bufferWriter.write("0\n");
        }

        // Clauza I continuare: un nod sa aiba cel mult o culoare
        for (int i = 0; i < n; i++) {
            for (int r = 0; r < k - 1; r ++) {
                for (int s = r + 1; s < k; s++) { 
                    bufferWriter.write((-1) * variables[i][r] + " " 
                                        + (-1) * variables[i][s] + " "
                                        + Constants.ORACLE_CLAUSE_END
                                        + "\n");
                }
            }
        }

        // Clauza II: Pentru orice muchiei (i,j), 
        // i si j nu pot avea aceeasi culoare
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (graph[i][j] == 1) {
                    for (int r = 0; r < k; r++) {
                        bufferWriter.write((-1) * variables[i][r] + " "
                                            + (-1) * variables[j][r] + " "
                                            + Constants.ORACLE_CLAUSE_END
                                            + "\n");

                    }
                }
            }
        }

        // Inchiderea bufferWriter
        bufferWriter.close();

    }
    /**
     * Citirea si descifrarea raspunsului oracolului
     */
    public void decipherOracleAnswer() throws IOException {

        //Initializare bufferReader
        BufferedReader bufferReader = new BufferedReader(
                                    new FileReader(Constants.ORACLE_OUTPUT));
        
        // Daca raspunsul oracului este fals, nu mai are rost sa verificam
        // Numerele pentru ca nu exista
        this.oracleAnswer = bufferReader.readLine();
        if (this.oracleAnswer.equalsIgnoreCase(Constants.ORACLE_FALSE_ANS)) {
            bufferReader.close();
            return;
        }

        // Citim size-ul, dar nu ne folosim de acesta
        String oracleLine = bufferReader.readLine();
        oracleLine = bufferReader.readLine(); // Variabilele intoarse de oracol

        // Inchidere bufferReader
        bufferReader.close();

        // Folosim un stringTokenizer pentru obtinerea numerelor
        StringTokenizer stringTokenizer = new StringTokenizer(oracleLine);
        this.oracleNumbersDecipher = new ArrayList<>();
        while (stringTokenizer.hasMoreElements()) {
            String number = stringTokenizer.nextToken();
            if (Integer.parseInt(number) > 0) {
                for(int i = 0; i < n; i++) {
                    for(int j = 0; j < k; j++) {
                        if(variables[i][j] == Integer.parseInt(number)) {
                            this.oracleNumbersDecipher.add(j + 1);
                        }
                    }
                }
            }
            

        }
    }

    /**
     * Afisarea raspunului
     */
    @Override
    public void writeAnswer() throws IOException {

        // Afisam raspunsul oracolului de tipul False daca e cazul
        System.out.println(oracleAnswer);
        if (this.oracleAnswer.equalsIgnoreCase(Constants.ORACLE_FALSE_ANS)) {
            return;
        }
        // Daca este pozitiv, afisam nodurile
        for (Integer node : oracleNumbersDecipher) {
            System.out.print(node + " ");
        }
        System.out.print("\n");
    }
}
    
