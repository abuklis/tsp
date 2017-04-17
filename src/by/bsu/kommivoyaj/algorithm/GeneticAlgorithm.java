package by.bsu.kommivoyaj.algorithm;

import by.bsu.kommivoyaj.util.SolutionUtil;
import by.bsu.kommivoyaj.entity.Solution;
import static by.bsu.kommivoyaj.util.Constant.*;
import java.util.*;

/**
 * Created by anyab on 12.04.2017.
 */
public class GeneticAlgorithm {
    List<Solution> population = new LinkedList<>();
    private static final Random rand = new Random();
    private static int[][] matrix;

    public GeneticAlgorithm() {
    }

    public GeneticAlgorithm(int[][] graph) {
        matrix = graph;
        for (int i = 0; i < POPULATION_SIZE; i++) {
            List<Integer> way = SolutionUtil.generateRandomPopulation(matrix.length);
            int fitness = SolutionUtil.calculateFitness(way,matrix);
            Solution solution = new Solution(fitness, way);
            population.add(solution);
        }
        Collections.sort(population);
    }

    public void evolve() {
        System.out.println("Current population: ");
        printPopulation();
        Solution parent1 = new Solution();
        Solution parent2 = parent1;
        while (parent1 == parent2){
            parent1 = tournamentSelection(population);
            parent2 = tournamentSelection(population);
        }
        System.out.println("Parents : " + parent1 + ", " + parent2);

        Solution child1, child2;
        child1 = crossover(parent1, parent2);
        child2 = crossover(parent2, parent1);
        System.out.println("Children : " + child1 + ", " + child2);
        mutate(child1);
        mutate(child2);

        substitution(child1, child2, parent1, parent2);
        System.out.println("New population :");
        Collections.sort(population);
        printPopulation();
    }

    private Solution tournamentSelection(List<Solution> population) {
        List<Solution> tournament = new ArrayList<>();
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            int randomIndex = rand.nextInt(POPULATION_SIZE);
            tournament.add(i, population.get(randomIndex));
        }
        return SolutionUtil.getFittest(tournament);
    }


    private void substitution(Solution child1, Solution child2, Solution parent1, Solution parent2){
        if (isChildGood(child1, parent1) && isChildGood(child2, parent2) ){
            population.set(POPULATION_SIZE - 1, child1);
            population.set(POPULATION_SIZE - 2, child2);
        } else if (isChildGood(child1, parent1)) {
            population.set(POPULATION_SIZE - 1, child1);
        } else if (isChildGood(child2, parent2)){
            population.set(POPULATION_SIZE - 1, child2);
        }
    }

    private Solution crossover(Solution parent1, Solution parent2) {
        Solution child = new Solution();
        List<Integer> newWay = new ArrayList<>();
        for (int i = 0; i< PIVOT; i++){
            int parentValue = parent1.getVertexes().get(i);
            newWay.add(parentValue);
        }
        for (int j = PIVOT; j< parent1.getVertexes().size() -1; j++){
            int parent2Value = parent2.getVertexes().get(j);
            int parent1Value = parent1.getVertexes().get(j);
            if (!newWay.contains(parent2Value)){
                newWay.add(parent2Value);
            } else if (newWay.contains(parent2Value) && !newWay.contains(parent1Value)){
                newWay.add(parent1Value);
            } else {
                int vertex = rand.nextInt(matrix.length - MIN_VERTEX + 1) + MIN_VERTEX;
                while (newWay.contains(vertex)){
                    vertex = rand.nextInt(matrix.length - MIN_VERTEX + 1) + MIN_VERTEX;
                }
                newWay.add(vertex);
            }
        }
        newWay.add(1);
        child.setVertexes(newWay);
        SolutionUtil.calculateFitness(child.getVertexes(), matrix);
        return child;
    }

    private void mutate(Solution solution) {
        if (Math.random() <= MUTATION_RATE) {
            System.out.println("Mutation of solution " + solution + " ...");
            int index1 = rand.nextInt(matrix.length - MIN_VERTEX + 1) + MIN_VERTEX;
            int index2 = index1;
            while (index1 == index2) {
                index2 = rand.nextInt(matrix.length - MIN_VERTEX + 1) + MIN_VERTEX;
            }
            int index3 = index2;
            while (index3 == index2) {
                index3 = rand.nextInt(matrix.length - MIN_VERTEX + 1) + MIN_VERTEX;
            }
            int vertex1 = solution.getVertexes().get(index1);
            int vertex2 = solution.getVertexes().get(index2);
            solution.setVertexAtIndex(index1, vertex2);
            solution.setVertexAtIndex(index2, vertex1);
            System.out.println("Mutation result " + solution);
        }
    }

    private void printPopulation() {
        population.forEach(System.out::println);
    }

    private boolean isChildGood(Solution child, Solution parent1){
        return child.getLength() <= parent1.getLength();
    }

    public void doAlgorithm() {
        int generationCount = 0;
        while (generationCount != MAX_ITERATIONS) {
            generationCount++;
            System.out.println("==========================================");
            System.out.println("Generation №" + generationCount);
            evolve();
            System.out.println("==========================================");
        }

        System.out.println("\nResult");
        printPopulation();
    }

    public static void main(String[] args) {
        int[][] matrix =
                        {{-1, 4, 4, 3 ,5},
                        {6, -1, 5, 7, 5},
                        {6, 5, -1, 3, 5},
                        {4, 5, 3, -1, 5},
                        {4, 3, 4, 5, -1}};

        System.out.println("Genetic algorithm : ");
        GeneticAlgorithm genetic = new GeneticAlgorithm(matrix);
        genetic.doAlgorithm();
    }
}
