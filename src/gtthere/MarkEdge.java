/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gtthere;

/**
 *
 * @author Hemant Deshpande
 */
public class MarkEdge{
    private boolean isEdge = false;
    public MarkEdge(int i, int j, int[] rect){
        int X1 = rect[0];
        int Y1 = rect[1];
        int X2 = rect[2];
        int Y2 = rect[3];
        float fX1 = X1;
        float fX2 = X2;
        float fY1 = Y1;
        float fY2 = Y2;
        //vertical line
        if(X1==X2){
            if(j==X1) isEdge = true;
        }
        //horizontal line
        if(Y1==Y2){
            if(i==Y1) isEdge = true;
        }
        //line with slope
        if(X1!=X2 && Y1!=Y2){
            float slopeX = (fY2-fY1)/(fX2-fX1);
            float interceptX = fY1 - ((fY2-fY1)*fX1)/(fX2-fX1);
            float slopeY = (fX2-fX1)/(fY2-fY1);
            float interceptY = fX1 - ((fX2-fX1)*fY1)/(fY2-fY1);
            int tempx = (int)(j*slopeX+interceptX);
            int tempy = (int)(i*slopeY+interceptY);
            if(Math.abs(i-tempx)<1 || Math.abs(j-tempy)<1) isEdge = true;
        }
    }
public boolean ifEdge(){
    return isEdge;
}
}
