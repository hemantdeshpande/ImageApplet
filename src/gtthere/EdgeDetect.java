/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gtthere;
import java.awt.*;
import java.lang.Math;
//import java.awt.image.*;
import java.io.*;

/**
 *
 * @author Hemant Deshpande
 */
public class EdgeDetect {
    private byte[][] NewEdge;
    private int[] CPixData;
    private byte btMark = (byte)3; //0x03;//3 use with OR to write 1, AND to check 1/0
    private byte bMark = (byte)12; //0x0C;//12 use with OR to write 1, AND to check 1/0
    private byte bEdge = (byte)48; //0x30;//48 use with OR to write 1, AND to check 1/0
    private byte bNtMark = (byte)60; //0x3C;//60 use with AND to write 0
    private byte bNMark = (byte)51; //0x33;//51 use with AND to write 0
    private byte bNEdge = (byte)15; //0x0F;//15 use with AND to write 0

    public EdgeDetect(int[] PixData, byte[][] Edge, int[] Quad, double Threshold){
        int minX = Quad[0];
        int minY = Quad[1];
        int maxX = Quad[2];
        int maxY = Quad[3];
        int width = Quad[4];
        int height = Quad[5];
        NewEdge = new byte[height][width];
        NewEdge = Edge;
        CPixData = new int[height*width];
        CPixData = PixData;
        float num =0;
        float[][] Edata = new float[height][width];
        float[][] LPdata = new float[height][width];
        for(int row = 0;row < height;row++){
            for(int col = 0; col < width;col++){
                        int element = row * width + col;
                        int pix_red = (PixData[element] >> 16) & 0xFF;
                        int pix_green = (PixData[element] >> 8) & 0xFF;
                        int pix_blue = (PixData[element]) & 0xFF;
                        Edata[row][col] = (float)(Math.sqrt(pix_red*pix_red +
                                        +pix_green*pix_green+pix_blue*pix_blue));
                        if(((byte)(NewEdge[row][col] & bEdge))==48){
                         CPixData[element] = 0xFFFFFFFF;
                }
            }//end for loop on col
        }//end for loop on row
        for(int row = 0;row < height;row++){
            for(int col = 0; col < width;col++){
                for(int i = -3; i<4; i++){
                     int rowx = row+i;
                     if(rowx >= height) rowx = height-1;
                     if(rowx < 0) rowx = 0;
                     for(int j = -3; j<4; j++){
                        int colx = col+j;
                        if(colx >= width) colx = width-1;
                        if(colx < 0) colx = 0;
                        int maxW = Math.max(Math.abs(i),Math.abs(j));
                        maxW = maxW*maxW+1;
                        LPdata[row][col] = LPdata[row][col]
                                        +Edata[rowx][colx]/maxW;
                     }
                }
                LPdata[row][col] = LPdata[row][col]/34;// Actually 17 is correct no.
            }//end for loop on col
        }//end for loop on row

        Edata = null;

        float[][] slopex = new float[height][width];
        float[][] slopey = new float[height][width];

        for(int row = 0;row < height;row++){
            for(int col = 0; col < width;col++){
                  int colp = col+1;
                  int coln = col-1;
                  if(colp >= width) colp =width-1;
                  if(coln <0 ) coln = 0;
                  num = LPdata[row][colp]-LPdata[row][coln];
                  slopex[row][col] = Math.abs(num/2);
                  int rowp = row+1;
                  int rown = row-1;
                  if(rowp >= height) rowp =height-1;
                  if(rown <0) rown = 0;
                  num = LPdata[rowp][col]-LPdata[rown][col];
                  slopey[row][col] = Math.abs(num/2);
            }
        }

        for(int row = 4;row < height-4;row++){
            for(int col = 4; col < width-4;col++){
                int cnt = row * width + col;
                if(((byte)(NewEdge[row][col] & bEdge))!=48){
                    int colp = col+1;
                    int coln = col-1;
                    if(colp >= width) colp = width-1;
                    if(coln < 0) coln = 0;
                    if(coln >= width) coln = width-1;
                    if(colp < 0) colp = 0;
                    int rowp = row+1;
                    int rown = row-1;
                    if(rowp >= height) rowp = height-1;
                    if(rown < 0) rown = 0;
                    if(rown >= height) rown = height-1;
                    if(rowp < 0) rowp = 0;
                    if((slopex[row][col] > Threshold && slopex[row][col] > slopex[row][colp] && slopex[row][col] > slopex[row][coln])
                        || (slopey[row][col] > Threshold && slopey[row][col] > slopey[rowp][col] && slopey[row][col] > slopey[rown][col])){
                        NewEdge[row][col]= (byte)(NewEdge[row][col] | bEdge);
                        CPixData[cnt] = 0xFFFFFFFF;
                    }
                    else{
                        double dirSlope = slopex[row][col]*slopex[row][col]+slopey[row][col]*slopey[row][col];
                        double dirSlopexpyn = slopex[rown][colp]*slopex[rown][colp]+slopey[rown][colp]*slopey[rown][colp];
                        double dirSlopexpyp = slopex[rowp][colp]*slopex[rowp][colp]+slopey[rowp][colp]*slopey[rowp][colp];
                        double dirSlopexnyp = slopex[rowp][coln]*slopex[rowp][coln]+slopey[rowp][coln]*slopey[rowp][coln];
                        double dirSlopexnyn = slopex[rown][coln]*slopex[rown][coln]+slopey[rown][coln]*slopey[rown][coln];
                        double thresh = Threshold*Threshold;
                        if((dirSlope > thresh && dirSlope > dirSlopexpyn && dirSlope > dirSlopexnyp)
                            ||(dirSlope > thresh && dirSlope > dirSlopexnyn && dirSlope >dirSlopexpyp)){
                            NewEdge[row][col]= (byte)(NewEdge[row][col] | bEdge);
                            CPixData[cnt] = 0xFFFFFFFF;
                        }
                        else{
                            CPixData[cnt] = PixData[cnt];
                        }
                    }
                }
                cnt++;
            }
        }
        LPdata = null;
        slopex = null;
        slopey = null;
        Edge=null;
        PixData=null;
    }
    public byte[][] GetEdgeData(){
        return NewEdge;
    }
    public int[] GetPixDatawEdge(){
        return CPixData;
    }
}
