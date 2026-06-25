package com.fastlearner.project0.utils;

import java.util.Scanner;

public class InputParser
{
    public static int[] readIntArray(Scanner input)
    {
        int size = input.nextInt();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++)
            arr[i] = input.nextInt();
        return arr;
    }
    public static String[] readStringArray(Scanner input)
    {
        int size = input.nextInt();
        String[] arr = new String[size];
        for (int i = 0; i < size; i++)
            arr[i] = input.next();
        return arr;
    }
    public static double[] readDoubleArray(Scanner input)
    {
        int size = input.nextInt();
        double[] arr = new double[size];
        for (int i = 0; i < size; i++)
            arr[i] = input.nextDouble();
        return arr;
    }
    public static char[] readCharArray(Scanner input)
    {
        int size = input.nextInt();
        char[] arr = new char[size];
        for (int i = 0; i < size; i++)
            arr[i] = input.next().charAt(0);
        return arr;
    }
    public static long[] readLongArray(Scanner input)
    {
        int size = input.nextInt();
        long[] arr = new long[size];
        for (int i = 0; i < size; i++)
            arr[i] = input.nextLong();
        return arr;
    }
    public static int[][]  readIntMatrix(Scanner input)
    {
        int row = input.nextInt();
        int col = input.nextInt();
        int[][] arr = new int[row][col];
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                arr[i][j] = input.nextInt();
        return arr;
    }
    public static String[][] readStringMatrix(Scanner input)
    {
        int row = input.nextInt();
        int col = input.nextInt();
        String[][] arr = new String[row][col];
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                arr[i][j] = input.next();
        return arr;
    }
    public static double[][] readDoubleMatrix(Scanner input)
    {
        int row = input.nextInt();
        int col = input.nextInt();
        double[][] arr = new double[row][col];
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                arr[i][j] = input.nextDouble();
        return arr;
    }
    public static char[][] readCharMatrix(Scanner input)
    {
        int row = input.nextInt();
        int col = input.nextInt();
        char[][] arr = new char[row][col];
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                arr[i][j] = input.next().charAt(0);
            }
        }
        return arr;
    }
    public static long[][] readLongMatrix(Scanner input)
    {
        int row = input.nextInt();
        int col = input.nextInt();
        long[][] arr = new long[row][col];
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                arr[i][j] = input.nextLong();
            }
        }
        return arr;
    }
}
