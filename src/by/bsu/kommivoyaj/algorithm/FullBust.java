package by.bsu.kommivoyaj.algorithm;

import java.util.*;

/**
 * Created by anyab on 15.04.2017.
 */
public class FullBust {
    public static List<List<Integer>> combinations = new ArrayList<>();
    private static int costMatrix[][];
    private static int size;

    FullBust(){
        super();
    }

    FullBust(int [][] costMatrix){
        this.costMatrix = costMatrix;
        size = costMatrix.length;
    }

    static void generateCombinations(int[] vertexes, int k) {
        if (k == vertexes.length) {
            List<Integer> way = new LinkedList<>();
            way.add(1);
            for (int anA : vertexes) {
                if (anA != 1) {
                    way.add(anA);
                }
            }
            way.add(1);
            combinations.add(way);
        }
        else {
            for (int i = k; i < vertexes.length; i++) {
                int temp = vertexes[k];
                vertexes[k] = vertexes[i];
                vertexes[i] = temp;

                generateCombinations(vertexes, k + 1);

                temp = vertexes[k];
                vertexes[k] = vertexes[i];
                vertexes[i] = temp;
            }
        }
    }

    public static void main(String args[])
    {
        int[][] matrix =
                        {{-1, 4, 4, 3 ,5},
                        {6, -1, 5, 7, 5},
                        {6, 5, -1, 3, 5},
                        {4, 5, 3, -1, 5},
                        {4, 3, 4, 5, -1}};
        FullBust fullBust = new FullBust(matrix);

        int[] sequence = new int[size];
        for (int i = 0; i < sequence.length; i++) {
            sequence[i] = i+1;
        }
        generateCombinations(sequence, 1);
        findMin(costMatrix);
    }


    public static void findMin(int [][] matrix){
        int min = Integer.MAX_VALUE;
        List<Integer> finalCombination = new ArrayList<>();
        for (List<Integer> combination : combinations) {
            System.out.print("Combination = " + combination);
            int cost = 0;
            for (int j = 0; j < combination.size() - 1; j++) {
                cost = cost + matrix[(combination.get(j)-1)][(combination.get(j + 1)-1)];
            }
            if (cost < min) {
                min = cost;
                finalCombination = combination;
            }
            System.out.println("  cost = " + cost);
        }
        System.out.println(finalCombination  +  ", length = " + min  );
    }
}
