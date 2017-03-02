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
 * @author Hemant Deshpande
 */
public class AddShape extends Component{
    private byte btMark = (byte)3; //0x03;//3 use with OR to write 1, AND to check 1/0
    private byte bMark = (byte)12; //0x0C;//12 use with OR to write 1, AND to check 1/0
    private byte bEdge = (byte)48; //0x30;//48 use with OR to write 1, AND to check 1/0
    private byte bNtMark = (byte)60; //0x3C;//60 use with AND to write 0
    private byte bNMark = (byte)51; //0x33;//51 use with AND to write 0
    private byte bNEdge = (byte)15; //0x0F;//15 use with AND to write 0
    //private int[] PixData;
    private Image CImg;
    private byte[] Mark;

    public AddShape(int[] Pixels, byte[][] Edge, byte[] OldMark, int SeedX, int SeedY, int width, int height, int del){
        byte[] Rdata = new byte[height*width];
        byte[] Gdata = new byte[height*width];
        byte[] Bdata = new byte[height*width];
        Mark = new byte[height*width];
        if(OldMark!=null) Mark = OldMark;
        int[] PixData = new int[height*width];
        del = del-7;
        if(del<=0) del = 1;
        del = del*del/7;
        for(int row = 0;row < height;row++){
            for(int col = 0; col < width;col++){
                int element = row * width + col;
                //PixData[element] = Pixels[element];
                Rdata[element] = (byte)((Pixels[element] >> 16) & 0xFF);
                Gdata[element] = (byte)((Pixels[element] >> 8) & 0xFF);
                Bdata[element] = (byte)((Pixels[element]) & 0xFF);
            }
        }
        int brush = 2;
        for(int i=-brush; i<=brush; i++){
            for(int j=-brush; j<=brush; j++){
                int rowx = SeedY-i;
                if(rowx >= height) rowx = height-1;
                if(rowx < 0) rowx = 0;
                int colx = SeedX-j;
                if(colx >= width) colx = width-1;
                if(colx < 0) colx = 0;
                int element = rowx*width+colx;
                if(Mark[element]!=1){
                Mark[element] = 1;
                PixData[element] = ((85 << 24) & 0xFF000000)
                  | (((255) << 16) & 0x00FF0000)
                  | (((255) << 8) & 0x0000FF00)
                  | ((255) & 0x000000FF);
                }
            }
        }
        int step=1, corner=0;
        boolean isDone = false;

        while(!isDone){
            for(int incr=0; incr<=step; incr++){
            int row = 0, col = 0;
            for(int octad=1; octad<=8; octad++){
                if(octad==1){
                    row = SeedY+incr;
                    col = SeedX+step;
                }
                if(octad==2){
                    row = SeedY-incr;
                    col = SeedX+step;
                }
                if(octad==3){
                    row = SeedY+incr;
                    col = SeedX-step;
                }
                if(octad==4){
                    row = SeedY-incr;
                    col = SeedX-step;
                }
                if(octad==5){
                    row = SeedY+step;
                    col = SeedX+incr;
                }
                if(octad==6){
                    row = SeedY-step;
                    col = SeedX+incr;
                }
                if(octad==7){
                    row = SeedY+step;
                    col = SeedX-incr;
                }
                if(octad==8){
                    row = SeedY-step;
                    col = SeedX-incr;
                }
                if(row>=0 && row<height && col>=0 && col<width){
                    int element = row*width+col;
                    if(((byte)(Edge[row][col] & bEdge))!=48 && Mark[element]!=1){
                        //start checking with neighbours
                        int colp = col+1, coln = col-1;
                        if(colp >= width) colp = width-1;
                        if(coln < 0) coln = 0;
                        if(coln >= width) coln = width-1;
                        if(colp < 0) colp = 0;
                        int rowp = row+1, rown = row-1;
                        if(rowp >= height) rowp = height-1;
                        if(rown < 0) rown = 0;
                        if(rown >= height) rown = height-1;
                        if(rowp < 0) rowp = 0;
                        int element0n = row*width+coln; int element0p = row*width+colp;
                        int elementn0 = rown*width+col; int elementp0 = rowp*width+col;
                        int elementnn = rown*width+coln; int elementpp = rowp*width+colp;
                        int elementnp = rown*width+colp; int elementpn = rowp*width+coln;
                        if(Mark[element0n]==1 || Mark[element0p]==1
                                || Mark[elementn0]==1 || Mark[elementp0]==1
                                || Mark[elementnn]==1 || Mark[elementpp]==1
                                || Mark[elementnp]==1 || Mark[elementpn]==1){ //point has a neighbor
                            int cnt=0;
                            int tmpR = 0, tmpG =0, tmpB=0; corner = 0;
                            int dRed = 0; int dGreen = 0;
                            int dBlue = 0;
                            tmpR = Rdata[element]; tmpG = Gdata[element]; tmpB = Bdata[element];
                            if(Mark[element0n]==1){
                                cnt++;
                                dRed = dRed + Math.abs(tmpR-Rdata[element0n]);
                                dGreen = dGreen + Math.abs(tmpG-Gdata[element0n]);
                                dBlue = dBlue + Math.abs(tmpB-Bdata[element0n]);
                            }
                            if(Mark[element0p]==1){
                                cnt++;
                                dRed = dRed + Math.abs(tmpR-Rdata[element0p]);
                                dGreen = dGreen + Math.abs(tmpG-Gdata[element0p]);
                                dBlue = dBlue + Math.abs(tmpB-Bdata[element0p]);
                            }
                            if(Mark[elementn0]==1){
                                cnt++;
                                dRed = dRed + Math.abs(tmpR-Rdata[elementn0]);
                                dGreen = dGreen + Math.abs(tmpG-Gdata[elementn0]);
                                dBlue = dBlue + Math.abs(tmpB-Bdata[elementn0]);
                            }
                            if(Mark[elementp0]==1){
                                cnt++;
                                dRed = dRed + Math.abs(tmpR-Rdata[elementp0]);
                                dGreen = dGreen + Math.abs(tmpG-Gdata[elementp0]);
                                dBlue = dBlue + Math.abs(tmpB-Bdata[elementp0]);
                            }
                            if(Mark[elementnn]==1){
                                cnt++;
                                dRed = dRed + Math.abs(tmpR-Rdata[elementnn]);
                                dGreen = dGreen + Math.abs(tmpG-Gdata[elementnn]);
                                dBlue = dBlue + Math.abs(tmpB-Bdata[elementnn]);
                                corner++;
                            }
                            if(Mark[elementpp]==1){
                                cnt++;
                                dRed = dRed + Math.abs(tmpR-Rdata[elementpp]);
                                dGreen = dGreen + Math.abs(tmpG-Gdata[elementpp]);
                                dBlue = dBlue + Math.abs(tmpB-Bdata[elementpp]);
                                corner++;
                            }
                            if(Mark[elementnp]==1){
                                cnt++;
                                dRed = dRed + Math.abs(tmpR-Rdata[elementnp]);
                                dGreen = dGreen + Math.abs(tmpG-Gdata[elementnp]);
                                dBlue = dBlue + Math.abs(tmpB-Bdata[elementnp]);
                                corner++;
                            }
                            if(Mark[elementpn]==1){
                                cnt++;
                                dRed = dRed + Math.abs(tmpR-Rdata[elementpn]);
                                dGreen = dGreen + Math.abs(tmpG-Gdata[elementpn]);
                                dBlue = dBlue + Math.abs(tmpB-Bdata[elementpn]);
                                corner++;
                            }
                            int diff = (int)((dRed+dGreen+dBlue)/(3*cnt));
                            //diff = (int)(diff/(1+(cnt-corner)/16)+corner/8);
                            if(diff<del){
                                Mark[element] = 1;
                                PixData[element] = ((85 << 24) & 0xFF000000)
                                  | (((255) << 16) & 0x00FF0000)
                                  | (((255) << 8) & 0x0000FF00)
                                  | ((255) & 0x000000FF);
                            }
                        }
                    }//
                }
            }
            }
            if(SeedX+step>=width && SeedX-step<=0 && SeedY+step>=height && SeedY-step<=0) isDone = true;
            step++;
        }
        // round two reverse way
        while(step!=0){
            for(int incr=step; incr>=0; incr--){
            int row = 0, col = 0;
            for(int octad=1; octad<=8; octad++){
                if(octad==1){
                    row = SeedY+incr;
                    col = SeedX+step;
                }
                if(octad==2){
                    row = SeedY-incr;
                    col = SeedX+step;
                }
                if(octad==3){
                    row = SeedY+incr;
                    col = SeedX-step;
                }
                if(octad==4){
                    row = SeedY-incr;
                    col = SeedX-step;
                }
                if(octad==5){
                    row = SeedY+step;
                    col = SeedX+incr;
                }
                if(octad==6){
                    row = SeedY-step;
                    col = SeedX+incr;
                }
                if(octad==7){
                    row = SeedY+step;
                    col = SeedX-incr;
                }
                if(octad==8){
                    row = SeedY-step;
                    col = SeedX-incr;
                }
                if(row>=0 && row<height && col>=0 && col<width){
                    int element = row*width+col;
                    if(((byte)(Edge[row][col] & bEdge))!=48 && Mark[element]!=1){
                        //start checking with neighbours
                        int colp = col+1, coln = col-1;
                        if(colp >= width) colp = width-1;
                        if(coln < 0) coln = 0;
                        if(coln >= width) coln = width-1;
                        if(colp < 0) colp = 0;
                        int rowp = row+1, rown = row-1;
                        if(rowp >= height) rowp = height-1;
                        if(rown < 0) rown = 0;
                        if(rown >= height) rown = height-1;
                        if(rowp < 0) rowp = 0;
                        int element0n = row*width+coln; int element0p = row*width+colp;
                        int elementn0 = rown*width+col; int elementp0 = rowp*width+col;
                        int elementnn = rown*width+coln; int elementpp = rowp*width+colp;
                        int elementnp = rown*width+colp; int elementpn = rowp*width+coln;
                        if(Mark[element0n]==1 || Mark[element0p]==1
                                || Mark[elementn0]==1 || Mark[elementp0]==1
                                || Mark[elementnn]==1 || Mark[elementpp]==1
                                || Mark[elementnp]==1 || Mark[elementpn]==1){ //point has a neighbor
                            int cnt=0;
                            int tmpR = 0, tmpG =0, tmpB=0; corner = 0;
                            int dRed = 0; int dGreen = 0;
                            int dBlue = 0;
                            tmpR = Rdata[element]; tmpG = Gdata[element]; tmpB = Bdata[element];
                            if(Mark[element0n]==1){
                                cnt++;
                                dRed = dRed + Math.abs(tmpR-Rdata[element0n]);
                                dGreen = dGreen + Math.abs(tmpG-Gdata[element0n]);
                                dBlue = dBlue + Math.abs(tmpB-Bdata[element0n]);
                            }
                            if(Mark[element0p]==1){
                                cnt++;
                                dRed = dRed + Math.abs(tmpR-Rdata[element0p]);
                                dGreen = dGreen + Math.abs(tmpG-Gdata[element0p]);
                                dBlue = dBlue + Math.abs(tmpB-Bdata[element0p]);
                            }
                            if(Mark[elementn0]==1){
                                cnt++;
                                dRed = dRed + Math.abs(tmpR-Rdata[elementn0]);
                                dGreen = dGreen + Math.abs(tmpG-Gdata[elementn0]);
                                dBlue = dBlue + Math.abs(tmpB-Bdata[elementn0]);
                            }
                            if(Mark[elementp0]==1){
                                cnt++;
                                dRed = dRed + Math.abs(tmpR-Rdata[elementp0]);
                                dGreen = dGreen + Math.abs(tmpG-Gdata[elementp0]);
                                dBlue = dBlue + Math.abs(tmpB-Bdata[elementp0]);
                            }
                            if(Mark[elementnn]==1){
                                cnt++;
                                dRed = dRed + Math.abs(tmpR-Rdata[elementnn]);
                                dGreen = dGreen + Math.abs(tmpG-Gdata[elementnn]);
                                dBlue = dBlue + Math.abs(tmpB-Bdata[elementnn]);
                                corner++;
                            }
                            if(Mark[elementpp]==1){
                                cnt++;
                                dRed = dRed + Math.abs(tmpR-Rdata[elementpp]);
                                dGreen = dGreen + Math.abs(tmpG-Gdata[elementpp]);
                                dBlue = dBlue + Math.abs(tmpB-Bdata[elementpp]);
                                corner++;
                            }
                            if(Mark[elementnp]==1){
                                cnt++;
                                dRed = dRed + Math.abs(tmpR-Rdata[elementnp]);
                                dGreen = dGreen + Math.abs(tmpG-Gdata[elementnp]);
                                dBlue = dBlue + Math.abs(tmpB-Bdata[elementnp]);
                                corner++;
                            }
                            if(Mark[elementpn]==1){
                                cnt++;
                                dRed = dRed + Math.abs(tmpR-Rdata[elementpn]);
                                dGreen = dGreen + Math.abs(tmpG-Gdata[elementpn]);
                                dBlue = dBlue + Math.abs(tmpB-Bdata[elementpn]);
                                corner++;
                            }
                            int diff = (int)((dRed+dGreen+dBlue)/(3*cnt));
                            //diff = (int)(diff/(1+(cnt-corner)/16)+corner/8);
                            if(diff<del){
                                Mark[element] = 1;
                                PixData[element] = ((85 << 24) & 0xFF000000)
                                  | (((255) << 16) & 0x00FF0000)
                                  | (((255) << 8) & 0x0000FF00)
                                  | ((255) & 0x000000FF);
                            }
                        }
                    }//
                }
            }
            }
            step--;
        }

        // round three: to make final push for complex shapes: Bike!
        isDone = false; step=1;
        while(!isDone){
            for(int incr=0; incr<=step; incr++){
            int row = 0, col = 0;
            for(int octad=1; octad<=8; octad++){
                if(octad==1){
                    row = SeedY+incr;
                    col = SeedX+step;
                }
                if(octad==2){
                    row = SeedY-incr;
                    col = SeedX+step;
                }
                if(octad==3){
                    row = SeedY+incr;
                    col = SeedX-step;
                }
                if(octad==4){
                    row = SeedY-incr;
                    col = SeedX-step;
                }
                if(octad==5){
                    row = SeedY+step;
                    col = SeedX+incr;
                }
                if(octad==6){
                    row = SeedY-step;
                    col = SeedX+incr;
                }
                if(octad==7){
                    row = SeedY+step;
                    col = SeedX-incr;
                }
                if(octad==8){
                    row = SeedY-step;
                    col = SeedX-incr;
                }
                if(row>=0 && row<height && col>=0 && col<width){
                    int element = row*width+col;
                    if(((byte)(Edge[row][col] & bEdge))!=48 && Mark[element]!=1){
                        //start checking with neighbours
                        int colp = col+1, coln = col-1;
                        if(colp >= width) colp = width-1;
                        if(coln < 0) coln = 0;
                        if(coln >= width) coln = width-1;
                        if(colp < 0) colp = 0;
                        int rowp = row+1, rown = row-1;
                        if(rowp >= height) rowp = height-1;
                        if(rown < 0) rown = 0;
                        if(rown >= height) rown = height-1;
                        if(rowp < 0) rowp = 0;
                        int element0n = row*width+coln; int element0p = row*width+colp;
                        int elementn0 = rown*width+col; int elementp0 = rowp*width+col;
                        int elementnn = rown*width+coln; int elementpp = rowp*width+colp;
                        int elementnp = rown*width+colp; int elementpn = rowp*width+coln;
                        if(Mark[element0n]==1 || Mark[element0p]==1
                                || Mark[elementn0]==1 || Mark[elementp0]==1
                                || Mark[elementnn]==1 || Mark[elementpp]==1
                                || Mark[elementnp]==1 || Mark[elementpn]==1){ //point has a neighbor
                            int cnt=0;
                            int tmpR = 0, tmpG =0, tmpB=0; corner = 0;
                            int dRed = 0; int dGreen = 0;
                            int dBlue = 0;
                            tmpR = Rdata[element]; tmpG = Gdata[element]; tmpB = Bdata[element];
                            if(Mark[element0n]==1){
                                cnt++;
                                dRed = dRed + Math.abs(tmpR-Rdata[element0n]);
                                dGreen = dGreen + Math.abs(tmpG-Gdata[element0n]);
                                dBlue = dBlue + Math.abs(tmpB-Bdata[element0n]);
                            }
                            if(Mark[element0p]==1){
                                cnt++;
                                dRed = dRed + Math.abs(tmpR-Rdata[element0p]);
                                dGreen = dGreen + Math.abs(tmpG-Gdata[element0p]);
                                dBlue = dBlue + Math.abs(tmpB-Bdata[element0p]);
                            }
                            if(Mark[elementn0]==1){
                                cnt++;
                                dRed = dRed + Math.abs(tmpR-Rdata[elementn0]);
                                dGreen = dGreen + Math.abs(tmpG-Gdata[elementn0]);
                                dBlue = dBlue + Math.abs(tmpB-Bdata[elementn0]);
                            }
                            if(Mark[elementp0]==1){
                                cnt++;
                                dRed = dRed + Math.abs(tmpR-Rdata[elementp0]);
                                dGreen = dGreen + Math.abs(tmpG-Gdata[elementp0]);
                                dBlue = dBlue + Math.abs(tmpB-Bdata[elementp0]);
                            }
                            if(Mark[elementnn]==1){
                                cnt++;
                                dRed = dRed + Math.abs(tmpR-Rdata[elementnn]);
                                dGreen = dGreen + Math.abs(tmpG-Gdata[elementnn]);
                                dBlue = dBlue + Math.abs(tmpB-Bdata[elementnn]);
                                corner++;
                            }
                            if(Mark[elementpp]==1){
                                cnt++;
                                dRed = dRed + Math.abs(tmpR-Rdata[elementpp]);
                                dGreen = dGreen + Math.abs(tmpG-Gdata[elementpp]);
                                dBlue = dBlue + Math.abs(tmpB-Bdata[elementpp]);
                                corner++;
                            }
                            if(Mark[elementnp]==1){
                                cnt++;
                                dRed = dRed + Math.abs(tmpR-Rdata[elementnp]);
                                dGreen = dGreen + Math.abs(tmpG-Gdata[elementnp]);
                                dBlue = dBlue + Math.abs(tmpB-Bdata[elementnp]);
                                corner++;
                            }
                            if(Mark[elementpn]==1){
                                cnt++;
                                dRed = dRed + Math.abs(tmpR-Rdata[elementpn]);
                                dGreen = dGreen + Math.abs(tmpG-Gdata[elementpn]);
                                dBlue = dBlue + Math.abs(tmpB-Bdata[elementpn]);
                                corner++;
                            }
                            int diff = (int)((dRed+dGreen+dBlue)/(3*cnt));
                            //diff = (int)(diff/(1+(cnt-corner)/8)+corner/16);
                            if(diff<del){
                                Mark[element] = 1;
                                PixData[element] = ((85 << 24) & 0xFF000000)
                                  | (((255) << 16) & 0x00FF0000)
                                  | (((255) << 8) & 0x0000FF00)
                                  | ((255) & 0x000000FF);
                            }
                        }
                    }//
                }
            }
            }
            if(SeedX+step>=width && SeedX-step<=0 && SeedY+step>=height && SeedY-step<=0) isDone = true;
            step++;
        }
        // post processing
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
                if(((byte)(Edge[i][j] & bEdge))!=48 && Mark[element]!=1){
                    //start checking with neighbours
                    if(Mark[i*width+coln]==1 && Mark[i*width+colp]==1
                        && Mark[rown*width+j]==1 && Mark[rowp*width+j]==1
                        && Mark[rown*width+coln]==1 && Mark[rowp*width+colp]==1
                        && Mark[rown*width+colp]==1 && Mark[rowp*width+coln]==1){ //point has all neighbors marked
                        Mark[element] = 1;
                                PixData[element] = ((85 << 24) & 0xFF000000)
                                  | (((255) << 16) & 0x00FF0000)
                                  | (((255) << 8) & 0x0000FF00)
                                  | ((255) & 0x000000FF);
                    }
                }
                if(Mark[element]==1 && Mark[i*width+colp]!=1 && Mark[i*width+coln]==1){
                        PixData[element] = 0xFFFFFFFF;
                }
                if(Mark[element]==1 && Mark[i*width+coln]!=1 && Mark[i*width+colp]==1){
                        PixData[element] = 0xFFFFFFFF;
                }
                if(Mark[element]==1 && Mark[rown*width+j]!=1 && Mark[rowp*width+j]==1){
                        PixData[element] = 0xFFFFFFFF;
                }
                if(Mark[element]==1 && Mark[rowp*width+j]!=1 && Mark[rown*width+j]==1){
                        PixData[element] = 0xFFFFFFFF;
                }
            }
        }
        CImg = createImage(new MemoryImageSource(width,height,PixData,0,width));

    }
    public Image GetShapeImage(){
        return CImg;
    }
    public byte[] GetSelected(){
        return Mark;
    }
}


