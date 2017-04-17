package by.bsu.kommivoyaj.util;

import by.bsu.kommivoyaj.entity.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by anyab on 15.04.2017.
 */
public class SolutionUtil {
    private static final int MIN_VERTEX = 2;
    private static final Random rand = new Random();

    public static int calculateFitness(List<Integer> vertexes, int[][] matrix) {
        int fitness = 0;
        int previous = 1;
        for (int i= 1 ; i< vertexes.size(); i++){
            int j = vertexes.get(i);
            fitness += matrix[previous-1][j-1];
            previous = j;
        }
        return fitness;
    }

    public static Solution getFittest(List<Solution> population) {
        Solution fittest = population.get(0);
        for (Solution solution : population) {
            if (fittest.getLength() <= solution.getLength()) {
                fittest = solution;
            }
        }
        return fittest;
    }

    public static List<Integer> generateRandomPopulation(int size) {
        List<Integer> vertexes = new ArrayList<>();
        vertexes.add(1);
        while (vertexes.size()!=size) {
            int randomVertexNumber = rand.nextInt(size - MIN_VERTEX + 1) + MIN_VERTEX;
            if (vertexes.contains(randomVertexNumber)) {
                while (vertexes.contains(randomVertexNumber)) {
                    randomVertexNumber = rand.nextInt(size - MIN_VERTEX + 1) + MIN_VERTEX;
                }
            }
            vertexes.add(randomVertexNumber);
        }
        vertexes.add(1);
        return vertexes;
    }
}
