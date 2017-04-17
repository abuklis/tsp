package by.bsu.kommivoyaj.algorithm;

import by.bsu.kommivoyaj.util.SolutionUtil;
import by.bsu.kommivoyaj.entity.Solution;

import java.util.*;

/**
 * Created by anyab on 15.04.2017.
 */
public class LocalSearch {
    int matrix[][];

    public LocalSearch(int[][] graph) {
        matrix = graph;
    }

    public Solution doAlgorithm(){
        Solution solution = new Solution();
        List<Integer> way = SolutionUtil.generateRandomPopulation(matrix.length);
        solution.setVertexes(way);
        int currentLength = SolutionUtil.calculateFitness(solution.getVertexes(), matrix);

        List<Integer> vertexes = solution.getVertexes();
        int maxLength = solution.getVertexes().size();
        for (int i = 1 ; i < maxLength; i++) {
            Set<List<Integer>> combinations = new LinkedHashSet<>();
            for (int j = i + 1; j < maxLength - 1; j++) {
                int vertex1 = vertexes.get(i);
                int vertex2 = vertexes.get(j);
                vertexes.set(i, vertex2);
                vertexes.set(j, vertex1);
                combinations.add(vertexes);
            }
            int unsuitableAmount = 0;
            for (List<Integer> combination: combinations){
                int newLength = SolutionUtil.calculateFitness(combination, matrix);
                if (newLength < currentLength) {
                    System.out.println("Good :)");
                    currentLength = newLength;
                    vertexes = combination;
                } else {
                    unsuitableAmount++;
                }
            }
            if (unsuitableAmount==combinations.size()) break;
        }
        return new Solution(SolutionUtil.calculateFitness(vertexes, matrix), vertexes);
    }

    public static void main(String[] args) {
        int[][] matrix =
                {{-1, 4, 4, 3 ,5},
                        {6, -1, 5, 7, 5},
                        {6, 5, -1, 3, 5},
                        {4, 5, 3, -1, 5},
                        {4, 3, 4, 5, -1}};
        LocalSearch search = new LocalSearch(matrix);
        System.out.println(search.doAlgorithm());
    }
}
