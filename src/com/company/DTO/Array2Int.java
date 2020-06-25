package com.company.DTO;

import java.util.Arrays;

public class Array2Int
{
    private int array[][];

    public int[][] getArray() {
        return array;
    }

    public void setArray(int[][] array) {
        this.array = array;
    }

    public Array2Int(int[][] array) {
        this.array = array;
    }

    @Override
    public String toString() {
        return "Array2Int{" +
                "array=" + Arrays.toString(array) +
                '}';
    }
}
