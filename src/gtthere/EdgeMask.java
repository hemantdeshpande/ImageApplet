/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gtthere;
import java.io.*;
/**
 *
 * @author Hemant Deshpande
 */
public class EdgeMask implements Serializable{
    private byte[][] EMask;
    private String ImageName;
    private int ImgWidth;
    private int ImgHeight;
    //private byte bNtMark = (byte)60; //0x3C;//60 use with AND to write 0
    //private byte bNMark = (byte)51; //0x33;//51 use with AND to write 0
    //private byte bNEdge = (byte)15; //0x0F;//15 use with AND to write 0
    private byte bMask = (byte)48; //0x30; use with AND to write 0 in both Mark and tempMark fields

  public EdgeMask(String filename, byte[][] Edge, int Width, int Height){
      ImageName = filename;
      ImgWidth = Width;
      ImgHeight = Height;
      EMask = Edge;
    }
    public String getImgName(){
        return ImageName;
    }
    public int getImgWidth(){
        return ImgWidth;
    }
    public int getImgHeight(){
        return ImgHeight;
    }
    public byte[][] getEdgeData(){
        //write 0's to tempMark and Mark fields
        for(int i = 0; i<ImgHeight; i++){
            for(int j=0; j<ImgWidth; j++){
                EMask[i][j] = (byte)(EMask[i][j] & bMask);
            }
        }
        return EMask;
    }
}
