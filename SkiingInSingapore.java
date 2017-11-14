/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skiinginsingapore;
import java.net.*;
import java.io.*;
import java.util.Arrays;

/**
 *
 * @author HP DV6
 */
public class SkiingInSingapore {
    int[][] inputMap;
    int currentNum;
    int rowSize;
    int colSize;
    int longestPathCtr;
    int longestPathStartNum;
    int longestPathEndNum;
    int longestPathDrop;
    

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {        
        SkiingInSingapore pgm = new SkiingInSingapore();
        pgm.LoadMap();
        pgm.FindPath();
    }
    
    void LoadMap() throws Exception {
        String inputLine;
        
        URL inputFile = new URL("http://s3-ap-southeast-1.amazonaws.com/geeks.redmart.com/coding-problems/map.txt");
        BufferedReader in = new BufferedReader(new InputStreamReader(inputFile.openStream()));
        
        // Read first row
        String[] lineArray = in.readLine().split(" ");
        rowSize = Integer.parseInt(lineArray[0]);
        colSize = Integer.parseInt(lineArray[1]);
        inputMap = new int[rowSize][colSize];
        
        // Load map.txt into array
        int rowCtr = 0;        
        while ((inputLine = in.readLine()) != null)
        {            
            inputMap[rowCtr++] = Arrays.stream(inputLine.split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        in.close();
    }
    
    void FindPath() {
        for (int row=0; row<rowSize; row++) {
            for (int col=0;col<colSize; col++) {
                boolean[][] workingMap = new boolean[rowSize][colSize]; 
                String currentPath = null;
                int currentPathCtr = 0;
                int currentPathStartNum = inputMap[row][col];
                FindPath(row, col, currentPath, workingMap, currentPathCtr, currentPathStartNum);
            }
        }
    }
    
    void FindPath(int currentRow, int currentCol, String currentPath, boolean[][] workingMap, int currentPathCtr, int currentPathStartNum) {
        workingMap[currentRow][currentCol] = true;
        currentPathCtr++;
        
        if (currentPath == null) {
            currentPath = Integer.toString(inputMap[currentRow][currentCol]);
        }
        else {
            currentPath = currentPath + "-" + Integer.toString(inputMap[currentRow][currentCol]);
        }        
        
        if (currentRow-1>=0 && inputMap[currentRow-1][currentCol] < inputMap[currentRow][currentCol] && workingMap[currentRow-1][currentCol] != true) {
            FindPath(currentRow-1, currentCol, currentPath, workingMap.clone(), currentPathCtr, currentPathStartNum);
        }
        
        if (currentRow+1<rowSize && inputMap[currentRow+1][currentCol] < inputMap[currentRow][currentCol] && workingMap[currentRow+1][currentCol] != true) {
            FindPath(currentRow+1, currentCol, currentPath, workingMap.clone(), currentPathCtr, currentPathStartNum);
        }
        
        if (currentCol+1<colSize && inputMap[currentRow][currentCol+1] < inputMap[currentRow][currentCol] && workingMap[currentRow][currentCol+1] != true) {
            FindPath(currentRow, currentCol+1, currentPath, workingMap.clone(), currentPathCtr, currentPathStartNum);
        }
        
        if (currentCol-1>=0 && inputMap[currentRow][currentCol-1] < inputMap[currentRow][currentCol] && workingMap[currentRow][currentCol-1] != true) {
            FindPath(currentRow, currentCol-1, currentPath, workingMap.clone(), currentPathCtr, currentPathStartNum);
        }
        
        //System.out.println("Current Path: " + currentPath + ".");
                
        int currentPathEndNum = inputMap[currentRow][currentCol];
        int currentPathDrop = currentPathStartNum - currentPathEndNum;
        if ( (currentPathCtr > longestPathCtr) || (currentPathCtr == longestPathCtr && currentPathDrop > longestPathDrop) ) {
            longestPathCtr = currentPathCtr;
            longestPathStartNum = currentPathStartNum;
            longestPathEndNum = currentPathEndNum;
            longestPathDrop = currentPathDrop;
            
            System.out.print("New longest path found. ");
            System.out.print("Length:" + currentPathCtr + ". ");
            System.out.print("Drop:" + currentPathDrop + ". ");
            System.out.println("Path: " + currentPath + ".");
        }
        workingMap[currentRow][currentCol] = false;
    }
}
