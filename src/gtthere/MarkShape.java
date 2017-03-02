/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gtthere;

/**
 *
 * @author Hemant Deshpande
 */
public class MarkShape {
    private static boolean isEdge;
    public static boolean Line(int i, int j, int[] rect){
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
            else isEdge = false;
        }
        //horizontal line
        if(Y1==Y2){
            if(i==Y1) isEdge = true;
            else isEdge = false;
        }
        //line with slope
        if(X1!=X2 && Y1!=Y2){
            float slopeX = (fY2-fY1)/(fX2-fX1);
            float interceptX = fY1 - ((fY2-fY1)*fX1)/(fX2-fX1);
            float slopeY = (fX2-fX1)/(fY2-fY1);
            float interceptY = fX1 - ((fX2-fX1)*fY1)/(fY2-fY1);
            int tempx = (int)(j*slopeX+interceptX);
            int tempy = (int)(i*slopeY+interceptY);
            if(Math.abs(i-tempx)<=1 || Math.abs(j-tempy)<=1) isEdge = true;
            else isEdge = false;
        }
        return isEdge;
    }
    public static boolean Rect(int i, int j, int[] rect){
        int X1 = rect[0];
        int Y1 = rect[1];
        int X2 = rect[2];
        int Y2 = rect[3];
        //boolean lineX1Y1X2Y1 = false;
        //boolean lineX1Y1X1Y2 = false;
        //boolean lineX1Y2X2Y2 = false;
        //boolean lineX2Y1X2Y2 = false;
        if(i==Y1 || i==Y2 || j==X1 || j==X2) isEdge = true;
        else isEdge = false;
        return isEdge;
    }
    public static boolean Ellipse(int i, int j, int[] rect){
        int X1 = rect[0];
        int Y1 = rect[1];
        int X2 = rect[2];
        int Y2 = rect[3];
        double Xc = (X1+X2)/2; double Yc = (Y1+Y2)/2;
        double a = (X2-X1)/2; double b = (Y2-Y1)/2;
        a = a*a; b = b*b;
        double x = Math.abs(j-Xc); double y = Math.abs(i-Yc);
        double tmpx = (double)Math.abs(a-y*y*a/b);
        double tmpy = (double)Math.abs(b-x*x*b/a);
        tmpx = Math.sqrt(tmpx); tmpy = Math.sqrt(tmpy);
        if(Math.abs(x-tmpx)<1 || Math.abs(y-tmpy)<1) isEdge = true;
        else isEdge = false;
        return isEdge;
    }
}
