/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gtthere;

/**
 *
 * @author Hemant Deshpande
 */
public class EraseEdge {
    private byte[][] NewEdge;
    public EraseEdge(byte[][] Edge, int imgCols, int imgRows, int X1, int Y1, int X2, int Y2){

        NewEdge = new byte[imgRows][imgCols];
        NewEdge = Edge;
        int maxX = Math.max(X1, X2);
        int minX = Math.min(X1, X2);
        int maxY = Math.max(Y1, Y2);
        int minY = Math.min(Y1, Y2);
        if(maxX > imgCols-1) maxX=imgCols-1;
        if(minX > imgCols-1) minX=imgCols-1;
        if(minX < 0) minX=0;
        if(maxX < 0) maxX=0;
        if(maxY > imgRows-1) maxY=imgRows-1;
        if(minY > imgRows-1) minY=imgRows-1;
        if(maxY < 0) maxY=0;
        if(minY < 0) minY=0;
        int centx = (X1+X2)/2;
        int centy = (Y1+Y2)/2;
        int maxrad = (X1-X2)*(X1-X2)/4;
        for(int col = minX;col <= maxX;col++){
            for(int row = minY;row <= maxY;row++){
                int radius = (col-centx)*(col-centx)+(row-centy)*(row-centy);
                if(NewEdge[row][col]==1 && radius<=maxrad){
                    NewEdge[row][col] =0;
                }
            }
        }
    }
    public byte[][] GetEdgeDatawEraseEdge(){
        return NewEdge;
    }
    public void destroyEraseEdge(){
        NewEdge=null;
    }
}
