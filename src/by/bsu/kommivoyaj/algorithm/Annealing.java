package by.bsu.kommivoyaj.algorithm;
import by.bsu.kommivoyaj.util.SolutionUtil;
import by.bsu.kommivoyaj.entity.Solution;

import java.util.List;
import java.util.Random;
import static by.bsu.kommivoyaj.util.Constant.*;

/**
 * Created by anyab on 12.04.2017.
 */
public class Annealing {
    private static Random random = new Random();
    private static int[][] matrix;

    public Annealing(){}

    public Annealing(int[][] graph) {
        matrix = graph;
    }

    public Solution doAlgorithm(){
        Solution firstSolution = new Solution();
        List<Integer> way = SolutionUtil.generateRandomPopulation(matrix.length);
        int length = SolutionUtil.calculateFitness(firstSolution.getVertexes(), matrix);
        firstSolution.setVertexes(way);
        firstSolution.setLength(length);

        double t = T_MAX;
        Solution solution;
        while (t > T_MIN){
            solution = change(firstSolution);
            int length1 = solution.getLength();
            int length2 = firstSolution.getLength();
            int delta = length1- length2;
            if (delta <= 0){
                firstSolution = solution;
            } else if (delta > 0){
                double power = -(delta/t);
                double p = Math.pow(Math.E, power);
                if (Math.random()<p){
                    firstSolution = solution;
                }
            }
            t = changeTemperature(t);
        }
        return firstSolution;
    }

    public Solution change(Solution solution){
        Solution sol = new Solution();
        sol.setVertexes(solution.getVertexes());
        int index1 = random.nextInt(matrix.length - MIN_VERTEX) + MIN_VERTEX;
        int index2 = index1;
        while (index1 == index2) {
            index2 = random.nextInt(matrix.length - MIN_VERTEX);
        }
        int vertex1 = sol.getVertexes().get(index1);
        int vertex2 = sol.getVertexes().get(index2);
        sol.setVertexAtIndex(index1, vertex2);
        sol.setVertexAtIndex(index2, vertex1);
        SolutionUtil.calculateFitness(sol.getVertexes(), matrix);
        return sol;
    }

    public double changeTemperature(double t){
        return 4*t/5 + 1;
    }

    public static void main(String[] args) {
        int[][] matrix =
                        {{-1, 4, 4, 3 ,5},
                        {6, -1, 5, 7, 5},
                        {6, 5, -1, 3, 5},
                        {4, 5, 3, -1, 5},
                        {4, 3, 4, 5, -1}};

        Annealing annealing = new Annealing(matrix);
        Solution resultSolution = annealing.doAlgorithm();
        System.out.println(resultSolution + "  " + SolutionUtil.calculateFitness(resultSolution.getVertexes(), matrix));
    }
}
