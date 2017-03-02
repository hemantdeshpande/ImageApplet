/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gtthere;
import java.awt.*;
import java.lang.Math;
import java.awt.image.*;
import java.awt.color.*;
import java.io.*;
import javax.imageio.*;

/**
 *
 * @author Hemant Deshpande
 */
public class IdentifyColor {
    Color IdColor;
    Color minColor;
    Color maxColor;
    public IdentifyColor(int[] PixData, int imgCols, int imgRows, int X1, int Y1, int X2, int Y2){
        int Count = 0;
        int Ravg=0, Gavg=0, Bavg=0;
        int Rmin=0, Gmin=0, Bmin=0;
        int Rmax=0, Gmax=0, Bmax=0;
        int maxX = Math.max(X1, X2);
        int minX = Math.min(X1, X2);
        int maxY = Math.max(Y1, Y2);
        int minY = Math.min(Y1, Y2);
        if(maxX > imgCols-1) maxX=imgCols-1;
        if(minX < 0) minX=0;
        if(minX > imgCols-1) minX=imgCols-1;
        if(maxX < 0) maxX=0;
        if(maxY > imgRows-1) maxY=imgRows-1;
        if(minY < 0) minY=0;
        if(minY > imgRows-1) minY=imgRows-1;
        if(maxY < 0) maxY=0;
        int temp = minY*imgCols+minX;

        Rmin = (PixData[temp] >> 16) & 0xFF;
        Gmin = (PixData[temp] >> 8) & 0xFF;
        Bmin = (PixData[temp]) & 0xFF;

        Rmax=Rmin; Gmax=Gmin; Bmax=Bmin;

        for(int col = minX;col <= maxX;col++){
            for(int row = minY;row <= maxY;row++){
                  int element = row*imgCols+col;
                  int oldRed = (PixData[element] >> 16) & 0xFF;
                  int oldGreen = (PixData[element] >> 8) & 0xFF;
                  int oldBlue = (PixData[element]) & 0xFF;
                  Ravg = Ravg + oldRed;
                  Gavg = Gavg + oldGreen;
                  Bavg = Bavg + oldBlue;
                  if(oldRed<=Rmin && oldGreen<=Gmin && oldBlue<=Bmin){
                      Rmin=oldRed; Gmin=oldGreen; Bmin=oldBlue;
                  }
                  if(oldRed>=Rmax && oldGreen>=Gmax && oldBlue>=Bmax){
                      Rmax=oldRed; Gmax=oldGreen; Bmax=oldBlue;
                  }
                  Count++;
            }
        }
        Ravg = Ravg/Count;
        Gavg = Gavg/Count;
        Bavg = Bavg/Count;
        if(Ravg<0) Ravg=0;
        if(Ravg>255) Ravg=255;
        if(Gavg<0) Gavg=0;
        if(Gavg>255)Gavg=255;
        if(Bavg<0) Bavg=0;
        if(Bavg>255) Bavg=255;
        IdColor = new Color(Ravg, Gavg, Bavg);
        minColor = new Color(Rmin, Gmin, Bmin);
        maxColor = new Color(Rmax, Gmax, Bmax);
        PixData=null;
    }
public Color getIdentifyColor(){
    return IdColor;
}
public Color getIdColormax(){
    return maxColor;
}
public Color getIdColormin(){
    return minColor;
}
}
