/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gtthere;

/**
 *
 * @author Hemant Deshpande
 */
public class SetOrder {
    private static int[] RectXYWH = new int[6];
    private static int[][] EdRectXYWH = new int[2][6];
    private static int[] RectXY = new int[4];
    private static int[] boundXY = new int[4];
    public static int[] GetBoundXY(int imgCols, int imgRows, int X1, int Y1, int X2, int Y2){
        //Coordinates = new int[6];
        if(X1 > imgCols-1) X1=imgCols-1;
        if(X1 < 0) X1=0;
        if(Y1 > imgRows-1) Y1=imgRows-1;
        if(Y1 < 0) Y1=0;
        if(X2 > imgCols-1) X2=imgCols-1;
        if(X2 < 0) X2=0;
        if(Y2 > imgRows-1) Y2=imgRows-1;
        if(Y2 < 0) Y2=0;
        boundXY[0] = X1;
        boundXY[1] = Y1;
        boundXY[2] = X2;
        boundXY[3] = Y2;
        return boundXY;
    }
    public static int[] GetZBoundXY(int imgCols, int imgRows, int X1, int Y1, int X2, int Y2, double Zoom){
        //Coordinates = new int[6];
        imgCols = (int)(imgCols/Zoom);
        imgRows = (int)(imgRows/Zoom);
        if(X1 > imgCols-1) X1=imgCols-1;
        if(X1 < 0) X1=0;
        if(Y1 > imgRows-1) Y1=imgRows-1;
        if(Y1 < 0) Y1=0;
        if(X2 > imgCols-1) X2=imgCols-1;
        if(X2 < 0) X2=0;
        if(Y2 > imgRows-1) Y2=imgRows-1;
        if(Y2 < 0) Y2=0;
        boundXY[0] = X1;
        boundXY[1] = Y1;
        boundXY[2] = X2;
        boundXY[3] = Y2;
        return boundXY;
    }
    public static int[] GetRectXY(int imgCols, int imgRows, int X1, int Y1, int X2, int Y2){
        //Coordinates = new int[6];
        if(X1 > imgCols-1) X1=imgCols-1;
        if(X1 < 0) X1=0;
        if(Y1 > imgRows-1) Y1=imgRows-1;
        if(Y1 < 0) Y1=0;
        if(X2 > imgCols-1) X2=imgCols-1;
        if(X2 < 0) X2=0;
        if(Y2 > imgRows-1) Y2=imgRows-1;
        if(Y2 < 0) Y2=0;
        int maxX = Math.max(X1, X2);
        int minX = Math.min(X1, X2);
        int maxY = Math.max(Y1, Y2);
        int minY = Math.min(Y1, Y2);
        RectXY[0] = minX;
        RectXY[1] = minY;
        RectXY[2] = maxX;
        RectXY[3] = maxY;
        return RectXY;
    }
    public static int[][] GetEdRectXYWH(int imgCols, int imgRows, int X1, int Y1, int X2, int Y2){
        //Coordinates = new int[6];
        if(X1 > imgCols-1) X1=imgCols-1;
        if(X1 < 0) X1=0;
        if(Y1 > imgRows-1) Y1=imgRows-1;
        if(Y1 < 0) Y1=0;
        if(X2 > imgCols-1) X2=imgCols-1;
        if(X2 < 0) X2=0;
        if(Y2 > imgRows-1) Y2=imgRows-1;
        if(Y2 < 0) Y2=0;
        int width = Math.abs(X1-X2);
        int height = Math.abs(Y1-Y2);
        int maxX = Math.max(X1, X2);
        int minX = Math.min(X1, X2);
        int maxY = Math.max(Y1, Y2);
        int minY = Math.min(Y1, Y2);
        EdRectXYWH[0][0] = minX;
        EdRectXYWH[0][1] = minY;
        EdRectXYWH[0][2] = maxX;
        EdRectXYWH[0][3] = maxY;
        EdRectXYWH[0][4] = width+1;
        EdRectXYWH[0][5] = height+1;
        minX = minX-3;
        maxX = maxX+3;
        minY = minY-3;
        maxY = maxY+3;
        if(minX > imgCols-1) minX=imgCols-1;
        if(minX < 0) minX=0;
        if(minY > imgRows-1) minY=imgRows-1;
        if(minY < 0) minY=0;
        if(maxX > imgCols-1) maxX=imgCols-1;
        if(maxX < 0) maxX=0;
        if(maxY > imgRows-1) maxY=imgRows-1;
        if(maxY < 0) maxY=0;
        width = maxX-minX;
        height = maxY-minY;
        EdRectXYWH[1][0] = minX;
        EdRectXYWH[1][1] = minY;
        EdRectXYWH[1][2] = maxX;
        EdRectXYWH[1][3] = maxY;
        EdRectXYWH[1][4] = width+1;
        EdRectXYWH[1][5] = height+1;
        return EdRectXYWH;
    }
    public static int[] GetRectXYWH(int imgCols, int imgRows, int X1, int Y1, int X2, int Y2){
        //Coordinates = new int[6];
        if(X1 > imgCols-1) X1=imgCols-1;
        if(X1 < 0) X1=0;
        if(Y1 > imgRows-1) Y1=imgRows-1;
        if(Y1 < 0) Y1=0;
        if(X2 > imgCols-1) X2=imgCols-1;
        if(X2 < 0) X2=0;
        if(Y2 > imgRows-1) Y2=imgRows-1;
        if(Y2 < 0) Y2=0;
        int width = Math.abs(X1-X2);
        int height = Math.abs(Y1-Y2);
        int maxX = Math.max(X1, X2);
        int minX = Math.min(X1, X2);
        int maxY = Math.max(Y1, Y2);
        int minY = Math.min(Y1, Y2);
        RectXYWH[0] = minX;
        RectXYWH[1] = minY;
        RectXYWH[2] = maxX;
        RectXYWH[3] = maxY;
        RectXYWH[4] = width+1;
        RectXYWH[5] = height+1;
        return RectXYWH;
    }
}
