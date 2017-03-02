/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gtthere;
import java.awt.*;
import java.lang.Math;
//import java.awt.image.*;
import java.io.*;
//import javax.imageio.*;

/**
 *
 * @author Hemant Deshpande
 */
public class ReplacePaint {
    private int[] CPixData;
    private byte[][] CMarkX;
    private byte btMark = (byte)3; //0x03;//3 use with OR to write 1, AND to check 1/0
    private byte bMark = (byte)12; //0x0C;//12 use with OR to write 1, AND to check 1/0
    private byte bEdge = (byte)48; //0x30;//48 use with OR to write 1, AND to check 1/0
    private byte bNtMark = (byte)60; //0x3C;//60 use with AND to write 0
    private byte bNMark = (byte)51; //0x33;//51 use with AND to write 0
    private byte bNEdge = (byte)15; //0x0F;//15 use with AND to write 0

    public ReplacePaint(int[] PixData, byte[][] MarkX, Color[] reColor, int[] RectXYWH, int Brush, double TexX, double Light){
        int X1 = RectXYWH[0];
        int Y1 = RectXYWH[1];
        int X2 = RectXYWH[2];
        int Y2 = RectXYWH[3];
        int width = RectXYWH[4];
        int height = RectXYWH[5];
        CPixData = new int[height*width];
        CMarkX = new byte[height][width];
        CPixData = PixData;
        CMarkX = MarkX;
        //reColor[0] = newColor;********DONOT DELETE, NEED for reference*****
        //reColor[1] = avgColorId;
        //reColor[2] = maxColorId;
        //reColor[3] = minColorId;
        Color newColor = reColor[0];
        Color maxColor = reColor[2];
        Color minColor = reColor[3];

        int Rx = newColor.getRed();
        int Bx = newColor.getBlue();
        int Gx = newColor.getGreen();
        int Ax = newColor.getAlpha();
        int redMin = minColor.getRed() -2;
        int greenMin = minColor.getGreen() -2;
        int blueMin = minColor.getBlue()- 2;
        int redMax = maxColor.getRed() + 5;
        int greenMax = maxColor.getGreen()+ 5;
        int blueMax = maxColor.getBlue() + 5;
        int centx = (X2-X1)/2;
        int centy = (Y2-Y1)/2;
        int maxrad = Brush*Brush;
        for(int col = 0;col < width;col++){
            for(int row = 0;row < height;row++){
                int radius = (col-centx)*(col-centx)+(row-centy)*(row-centy);
                    //if ((radius<=maxrad) && (CMarkX[row][col]==0)){ Check the tempMarkX field
                    if ((radius<=maxrad) && (((byte)(CMarkX[row][col] & btMark)) == 0)){
                        int element = row*width+col;
                        int oldRed = (CPixData[element] >> 16) & 0xFF;
                        int oldGreen = (CPixData[element] >> 8) & 0xFF;
                        int oldBlue = (CPixData[element]) & 0xFF;
                        //double ColorShift = Texture*Texture;
                        if((redMin<=oldRed && greenMin<=oldGreen && blueMin<=oldBlue)
                                &&
                                (oldRed<=redMax && oldGreen<=greenMax && oldBlue<=blueMax)){
                            double Texture = (Math.sqrt(oldRed*oldRed + oldGreen*oldGreen
                                + oldBlue*oldBlue)-238)/Math.sqrt(3);
                            //if(Texture<=0) Texture = TexX*Texture;
                            //else Texture = Light*Texture;
                            Texture = TexX*Texture+Light;
                            double RTx = Rx+Texture;
                            double GTx = Gx+Texture;
                            double BTx = Bx+Texture;
                            if(RTx<0) RTx=0;
                            if(RTx>255) RTx=255;
                            if(GTx<0) GTx=0;
                            if(GTx>255)GTx=255;
                            if(BTx<0) BTx=0;
                            if(BTx>255) BTx=255;
                            int newAlpha = (byte)Ax;
                            int newRed = (byte)RTx;
                            int newGreen = (byte)GTx;
                            int newBlue = (byte)BTx;
                            CPixData[element] = ((newAlpha << 24) & 0xFF000000)
                                | ((newRed << 16) & 0x00FF0000)
                                | ((newGreen << 8) & 0x0000FF00)
                                | ((newBlue) & 0x000000FF);
                             //CMarkX[row][col]=1; actually writing tempMark field
                            CMarkX[row][col]= (byte)(CMarkX[row][col] | btMark);
                        }
                }
            }
        }
        PixData=null;
        MarkX=null;
}

public int[] GetPixwRePaint(){
    return CPixData;
}
public byte[][] GetMarkwRePaint(){
    return CMarkX;
}
public void destroyRePaint(){
    CPixData=null;
    CMarkX=null;
}

}
