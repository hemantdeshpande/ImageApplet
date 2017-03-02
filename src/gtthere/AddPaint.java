/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gtthere;
import java.awt.*;
import java.io.*;
import javax.imageio.*;

/**
 * grows seed and returns new Image and selected area Pix array
 * @author Hemant Deshpande
 */
public class AddPaint {
    private int[] CPixData;
    private byte[][] CMarkX;
    private boolean isDone = false;
    private byte btMark = (byte)3; //0x03;//3 use with OR to write 1, AND to check 1/0
    private byte bMark = (byte)12; //0x0C;//12 use with OR to write 1, AND to check 1/0
    private byte bEdge = (byte)48; //0x30;//48 use with OR to write 1, AND to check 1/0
    private byte bNtMark = (byte)60; //0x3C;//60 use with AND to write 0
    private byte bNMark = (byte)51; //0x33;//51 use with AND to write 0
    private byte bNEdge = (byte)15; //0x0F;//15 use with AND to write 0

    public AddPaint(int[] PixData, byte[][] Edge, int X, int Y, int imgCols, int imgRows, Color newColor, double TexX, double Light){
        CPixData = new int[imgRows*imgCols];
        CMarkX = new byte[imgRows][imgCols];
        CMarkX = Edge; //Note: use Edge as Edge, MarkX as to put mark in tempMark field
        CPixData = PixData;
        int Rx = newColor.getRed();
        int Bx = newColor.getBlue();
        int Gx = newColor.getGreen();
        int Ax = newColor.getAlpha();

        //X+, Y+
rowloop:
        for(int row = Y; row < imgRows; row++){
colloop:
            for(int col = X; col < imgCols; col++){
                //if (Edge[row][col]==1){
                if(((byte)(Edge[row][col] & bEdge))==48){
                    //if(MarkX[row][col]==0){ : Actually need to check tempMark field
                    if(((byte)(CMarkX[row][col] & btMark)) == 0){
                        int element = row*imgCols+col;
                        int oldRed = (CPixData[element] >> 16) & 0xFF;
                        int oldGreen = (CPixData[element] >> 8) & 0xFF;
                        int oldBlue = (CPixData[element]) & 0xFF;
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
                    break colloop;
                }
                else {
                    //if(MarkX[row][col]==0){ : Actually need to check tempMark field
                    if(((byte)(CMarkX[row][col] & btMark)) == 0){
                        int element = row*imgCols+col;
                        int oldRed = (CPixData[element] >> 16) & 0xFF;
                        int oldGreen = (CPixData[element] >> 8) & 0xFF;
                        int oldBlue = (CPixData[element]) & 0xFF;
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
                        //CMarkX[row][col]=1;
                        CMarkX[row][col]= (byte)(CMarkX[row][col] | btMark);
                    }
                }
            }
            //if (Edge[row][X]==1) break rowloop;
            if (((byte)(Edge[row][X] & bEdge))==48) break rowloop;
        }
colloopb:
        for(int col = X; col < imgCols; col++){
rowloopb:
            for(int row = Y; row < imgRows; row++){
                //if (Edge[row][col]==1){
                if(((byte)(Edge[row][col] & bEdge))==48){
                    //if(MarkX[row][col]==0){ : Actually need to check tempMark field
                    if(((byte)(CMarkX[row][col] & btMark)) == 0){
                        int element = row*imgCols+col;
                        int oldRed = (CPixData[element] >> 16) & 0xFF;
                        int oldGreen = (CPixData[element] >> 8) & 0xFF;
                        int oldBlue = (CPixData[element]) & 0xFF;
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
                        //CMarkX[row][col]=1;
                        CMarkX[row][col]= (byte)(CMarkX[row][col] | btMark);
                    }
                    break rowloopb;
                }
            else {
                    //if(MarkX[row][col]==0){ : Actually need to check tempMark field
                    if(((byte)(CMarkX[row][col] & btMark)) == 0){
                        int element = row*imgCols+col;
                        int oldRed = (CPixData[element] >> 16) & 0xFF;
                        int oldGreen = (CPixData[element] >> 8) & 0xFF;
                        int oldBlue = (CPixData[element]) & 0xFF;
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
                        //CMarkX[row][col]=1;
                        CMarkX[row][col]= (byte)(CMarkX[row][col] | btMark);
                    }
                }
            }
            //if (Edge[Y][col]==1) break colloopb;
            if (((byte)(Edge[Y][col] & bEdge))==48) break colloopb;
        }

rowloop1:
        for(int row = Y; row >= 0; row--){
colloop1:
            for(int col = X; col >= 0; col--){
                //if (Edge[row][col]==1){
                if(((byte)(Edge[row][col] & bEdge))==48){
                    //if(MarkX[row][col]==0){ : Actually need to check tempMark field
                    if(((byte)(CMarkX[row][col] & btMark)) == 0){
                        int element = row*imgCols+col;
                        int oldRed = (CPixData[element] >> 16) & 0xFF;
                        int oldGreen = (CPixData[element] >> 8) & 0xFF;
                        int oldBlue = (CPixData[element]) & 0xFF;
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
                        //CMarkX[row][col]=1;
                        CMarkX[row][col]= (byte)(CMarkX[row][col] | btMark);
                    }
                    break colloop1;
                }
            else {
                    //if(MarkX[row][col]==0){ : Actually need to check tempMark field
                    if(((byte)(CMarkX[row][col] & btMark)) == 0){
                        int element = row*imgCols+col;
                        int oldRed = (CPixData[element] >> 16) & 0xFF;
                        int oldGreen = (CPixData[element] >> 8) & 0xFF;
                        int oldBlue = (CPixData[element]) & 0xFF;
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
                        //CMarkX[row][col]=1;
                        CMarkX[row][col]= (byte)(CMarkX[row][col] | btMark);
                    }
                }
            }
            //if (Edge[row][X]==1) break rowloop1;
            if(((byte)(Edge[row][X] & bEdge))==48) break rowloop1;
        }

colloop1b:
        for(int col = X; col >= 0; col--){
rowloop1b:
            for(int row = Y; row >= 0; row--){
                //if (Edge[row][col]==1){
                if(((byte)(Edge[row][col] & bEdge))==48){
                    //if(MarkX[row][col]==0){ : Actually need to check tempMark field
                    if(((byte)(CMarkX[row][col] & btMark)) == 0){
                        int element = row*imgCols+col;
                        int oldRed = (CPixData[element] >> 16) & 0xFF;
                        int oldGreen = (CPixData[element] >> 8) & 0xFF;
                        int oldBlue = (CPixData[element]) & 0xFF;
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
                        //CMarkX[row][col]=1;
                        CMarkX[row][col]= (byte)(CMarkX[row][col] | btMark);
                    }
                    break rowloop1b;
                }
            else {
                    //if(MarkX[row][col]==0){ : Actually need to check tempMark field
                    if(((byte)(CMarkX[row][col] & btMark)) == 0){
                        int element = row*imgCols+col;
                        int oldRed = (CPixData[element] >> 16) & 0xFF;
                        int oldGreen = (CPixData[element] >> 8) & 0xFF;
                        int oldBlue = (CPixData[element]) & 0xFF;
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
                        //CMarkX[row][col]=1;
                        CMarkX[row][col]= (byte)(CMarkX[row][col] | btMark);
                    }
                }
            }
            //if (Edge[Y][col]==1) break colloop1b;
            if (((byte)(Edge[Y][col] & bEdge))==48) break colloop1b;
        }

rowloop2:
        for(int row = Y; row >= 0; row--){
colloop2:
            for(int col = X; col < imgCols; col++){
                //if (Edge[row][col]==1){
                if(((byte)(Edge[row][col] & bEdge))==48){
                    //if(MarkX[row][col]==0){ : Actually need to check tempMark field
                    if(((byte)(CMarkX[row][col] & btMark)) == 0){
                        int element = row*imgCols+col;
                        int oldRed = (CPixData[element] >> 16) & 0xFF;
                        int oldGreen = (CPixData[element] >> 8) & 0xFF;
                        int oldBlue = (CPixData[element]) & 0xFF;
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
                        //CMarkX[row][col]=1;
                        CMarkX[row][col]= (byte)(CMarkX[row][col] | btMark);
                    }
                    break colloop2;
                }
            else {
                    //if(MarkX[row][col]==0){ : Actually need to check tempMark field
                    if(((byte)(CMarkX[row][col] & btMark)) == 0){
                        int element = row*imgCols+col;
                        int oldRed = (CPixData[element] >> 16) & 0xFF;
                        int oldGreen = (CPixData[element] >> 8) & 0xFF;
                        int oldBlue = (CPixData[element]) & 0xFF;
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
                        //CMarkX[row][col]=1;
                        CMarkX[row][col]= (byte)(CMarkX[row][col] | btMark);
                    }
                }
            }
            //if (Edge[row][X]==1) break rowloop2;
            if (((byte)(Edge[row][X] & bEdge))==48) break rowloop2;
        }

colloop2b:
        for(int col = X; col < imgCols; col++){
rowloop2b:
            for(int row = Y; row >= 0; row--){
                //if (Edge[row][col]==1){
                if(((byte)(Edge[row][col] & bEdge))==48){
                    //if(MarkX[row][col]==0){ : Actually need to check tempMark field
                    if(((byte)(CMarkX[row][col] & btMark)) == 0){
                        int element = row*imgCols+col;
                        int oldRed = (CPixData[element] >> 16) & 0xFF;
                        int oldGreen = (CPixData[element] >> 8) & 0xFF;
                        int oldBlue = (CPixData[element]) & 0xFF;
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
                        //CMarkX[row][col]=1;
                        CMarkX[row][col]= (byte)(CMarkX[row][col] | btMark);
                    }
                    break rowloop2b;
                }
            else {
                    //if(MarkX[row][col]==0){ : Actually need to check tempMark field
                    if(((byte)(CMarkX[row][col] & btMark)) == 0){
                        int element = row*imgCols+col;
                        int oldRed = (CPixData[element] >> 16) & 0xFF;
                        int oldGreen = (CPixData[element] >> 8) & 0xFF;
                        int oldBlue = (CPixData[element]) & 0xFF;
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
                        //CMarkX[row][col]=1;
                        CMarkX[row][col]= (byte)(CMarkX[row][col] | btMark);
                    }
                }
            }
            //if (Edge[Y][col]==1) break colloop2b;
            if (((byte)(Edge[Y][col] & bEdge))==48) break colloop2b;
        }

rowloop3:
        for(int row = Y; row < imgRows; row++){
colloop3:
            for(int col = X; col >= 0; col--){
                //if (Edge[row][col]==1){
                if(((byte)(Edge[row][col] & bEdge))==48){
                    //if(MarkX[row][col]==0){ : Actually need to check tempMark field
                    if(((byte)(CMarkX[row][col] & btMark)) == 0){
                        int element = row*imgCols+col;
                        int oldRed = (CPixData[element] >> 16) & 0xFF;
                        int oldGreen = (CPixData[element] >> 8) & 0xFF;
                        int oldBlue = (CPixData[element]) & 0xFF;
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
                        //CMarkX[row][col]=1;
                        CMarkX[row][col]= (byte)(CMarkX[row][col] | btMark);
                    }
                    break colloop3;
                }
            else {
                    //if(MarkX[row][col]==0){ : Actually need to check tempMark field
                    if(((byte)(CMarkX[row][col] & btMark)) == 0){
                        int element = row*imgCols+col;
                        int oldRed = (CPixData[element] >> 16) & 0xFF;
                        int oldGreen = (CPixData[element] >> 8) & 0xFF;
                        int oldBlue = (CPixData[element]) & 0xFF;
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
                        //CMarkX[row][col]=1;
                        CMarkX[row][col]= (byte)(CMarkX[row][col] | btMark);
                    }
                }
            }
            //if (Edge[row][X]==1) break rowloop3;
            if (((byte)(Edge[row][X] & bEdge))==48) break rowloop3;
        }

colloop3b:
        for(int col = X; col >= 0; col--){
rowloop3b:
            for(int row = Y; row < imgRows; row++){
                //if (Edge[row][col]==1){
                if(((byte)(Edge[row][col] & bEdge))==48){
                    //if(MarkX[row][col]==0){ : Actually need to check tempMark field
                    if(((byte)(CMarkX[row][col] & btMark)) == 0){
                        int element = row*imgCols+col;
                        int oldRed = (CPixData[element] >> 16) & 0xFF;
                        int oldGreen = (CPixData[element] >> 8) & 0xFF;
                        int oldBlue = (CPixData[element]) & 0xFF;
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
                        //CMarkX[row][col]=1;
                        CMarkX[row][col]= (byte)(CMarkX[row][col] | btMark);
                    }
                    break rowloop3b;
                }
            else {
                    //if(MarkX[row][col]==0){ : Actually need to check tempMark field
                    if(((byte)(CMarkX[row][col] & btMark)) == 0){
                        int element = row*imgCols+col;
                        int oldRed = (CPixData[element] >> 16) & 0xFF;
                        int oldGreen = (CPixData[element] >> 8) & 0xFF;
                        int oldBlue = (CPixData[element]) & 0xFF;
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
                        //CMarkX[row][col]=1;
                        CMarkX[row][col]= (byte)(CMarkX[row][col] | btMark);
                    }
                }
            }
            //if (Edge[Y][col]==1) break colloop3b;
            if (((byte)(Edge[Y][col] & bEdge))==48) break colloop3b;
        }
        Edge = null;
        PixData = null;
        isDone = true;
    }
    public int[] GetPixDatawPaint(){
        return CPixData;
    }
    public byte[][] GetMarkDatawPaint(){
        return CMarkX;
    }
    public boolean GetStatus(){
        return isDone;
    }
}










