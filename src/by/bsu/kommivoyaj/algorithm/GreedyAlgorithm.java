package by.bsu.kommivoyaj.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anyab on 09.04.2017.
 */
public class GreedyAlgorithm {
    private List<Integer> way = new ArrayList<>();
    private int wayLength;

    public List<Integer> doAlgorithm(int[][] matrix){
        int size = matrix.length;
        int minPoint = size;
        int minValue = Integer.MAX_VALUE;
        way.add(1);
        int previous = 1;

        while (way.size()!=size){
            for (int j=0 ; j< size; j++){
                if (j + 1!=previous && matrix[previous-1][j]!=-1 && !way.contains(j+1)){
                    if (matrix[previous-1][j] <= minValue){
                        minValue = matrix[previous-1][j];
                        minPoint = j + 1;
                    }
                }
                if (j == size - 1){
                    wayLength += minValue;
                    way.add(minPoint);
                    previous = minPoint;
                    minValue = Integer.MAX_VALUE;
                }
            }
        }

        if (way.size() == size){
            way.add(1);
            wayLength += matrix[previous-1][0];
        }
        return way;
    }

    public int getWayLength(){
        return wayLength;
    }

    public static void main(String[] args) {
        int[][] matrix =
                {{-1, 4, 4, 3 ,5},
                        {6, -1, 5, 7, 5},
                        {6, 5, -1, 3, 5},
                        {4, 5, 3, -1, 5},
                        {4, 3, 4, 5, -1}};
        System.out.println("Greedy algorithm : ");
        GreedyAlgorithm greedyAlgorithm = new GreedyAlgorithm();
        System.out.println(greedyAlgorithm.doAlgorithm(matrix) + ", " + greedyAlgorithm.getWayLength());
    }
}
