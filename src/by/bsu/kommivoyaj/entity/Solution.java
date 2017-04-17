package by.bsu.kommivoyaj.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by anyab on 12.04.2017.
 */
public class Solution implements Comparable<Solution>{
    private List<Integer> vertexes;
    private int length;

    public Solution() {
    }

    public Solution(int length, List<Integer> vertexes) {
        this.length = length;
        this.vertexes = vertexes;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<Integer> getVertexes() {
        return vertexes;
    }

    public void setVertexes(List<Integer> vertexes) {
        this.vertexes = vertexes;
    }

    public void setVertexAtIndex(int index, int vertex){
        vertexes.set(index, vertex);
    }

    public int compareTo(Solution o) {
        return this.length - o.length;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "length=" + length +
                ", vertexes=" + vertexes +
                '}';
    }
}

