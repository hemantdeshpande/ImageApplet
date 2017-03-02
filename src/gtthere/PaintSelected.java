/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gtthere;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.color.ColorSpace;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.*;

/**
 *
 * @author Hemant
 */
public class PaintSelected extends Component{
    private byte btMark = (byte)3; //0x03;//3 use with OR to write 1, AND to check 1/0
    private byte bMark = (byte)12; //0x0C;//12 use with OR to write 1, AND to check 1/0
    private byte bEdge = (byte)48; //0x30;//48 use with OR to write 1, AND to check 1/0
    private byte bNtMark = (byte)60; //0x3C;//60 use with AND to write 0
    private byte bNMark = (byte)51; //0x33;//51 use with AND to write 0
    private byte bNEdge = (byte)15; //0x0F;//15 use with AND to write 0
    private Image CImg;
    private byte[] Mark;
    private int[] CPixData;
    private byte[][] CMarkX;

    public PaintSelected(int[] PixData, byte[][] MarkX, byte[] Select, int width, int height, Color newColor, double TexX, double Light){
        CPixData = new int[height*width];
        CMarkX = new byte[height][width];
        CPixData = PixData;
        CMarkX = MarkX;
        int Rx = newColor.getRed();
        int Bx = newColor.getBlue();
        int Gx = newColor.getGreen();
        int Ax = newColor.getAlpha();
        for(int col = 0;col < width;col++){
            for(int row = 0;row < height;row++){
                    int element = row*width+col;
                    if ((Select[element]==1) && (((byte)(CMarkX[row][col] & btMark)) == 0)){
                        int oldRed = (CPixData[element] >> 16) & 0xFF;
                        int oldGreen = (CPixData[element] >> 8) & 0xFF;
                        int oldBlue = (CPixData[element]) & 0xFF;
                        double Texture = (Math.sqrt(oldRed*oldRed + oldGreen*oldGreen
                                + oldBlue*oldBlue)-238)/Math.sqrt(3);
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
        PixData=null;
        MarkX=null;
}
    public PaintSelected(int[] PixData, byte[] Select, int width, int height){
    for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                int element = i*width+j;
                    int colp = j+1, coln = j-1;
                    if(colp >= width) colp = width-1;
                    if(coln < 0) coln = 0;
                    if(coln >= width) coln = width-1;
                    if(colp < 0) colp = 0;
                    int rowp = i+1, rown = i-1;
                    if(rowp >= height) rowp = height-1;
                    if(rown < 0) rown = 0;
                    if(rown >= height) rown = height-1;
                    if(rowp < 0) rowp = 0;
                if(Select[element]==1){
                    //start checking with neighbours
                    PixData[element] = ((85 << 24) & 0xFF000000)
                      | (((255) << 16) & 0x00FF0000)
                      | (((255) << 8) & 0x0000FF00)
                      | ((255) & 0x000000FF);
                }
                if(Select[element]==1 && Select[i*width+colp]!=1 && Select[i*width+coln]==1){
                        PixData[element] = 0xFFFFFFFF;
                }
                if(Select[element]==1 && Select[i*width+coln]!=1 && Select[i*width+colp]==1){
                        PixData[element] = 0xFFFFFFFF;
                }
                if(Select[element]==1 && Select[rown*width+j]!=1 && Select[rowp*width+j]==1){
                        PixData[element] = 0xFFFFFFFF;
                }
                if(Select[element]==1 && Select[rowp*width+j]!=1 && Select[rown*width+j]==1){
                        PixData[element] = 0xFFFFFFFF;
                }
            }
        }
        CImg = createImage(new MemoryImageSource(width,height,PixData,0,width));
    }
public int[] GetPixDatawPaint(){
    return CPixData;
}
public byte[][] GetMarkDatawPaint(){
    return CMarkX;
}
public Image GetShapeImage(){
    return CImg;
}
}
