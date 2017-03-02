/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gtthere;

/**
 *
 * @author Hemant Deshpande
 */
public class AddEdgetoPic {
    private int[] CPixData;
    private byte btMark = (byte)3; //0x03;//3 use with OR to write 1, AND to check 1/0
    private byte bMark = (byte)12; //0x0C;//12 use with OR to write 1, AND to check 1/0
    private byte bEdge = (byte)48; //0x30;//48 use with OR to write 1, AND to check 1/0
    private byte bNtMark = (byte)60; //0x3C;//60 use with AND to write 0
    private byte bNMark = (byte)51; //0x33;//51 use with AND to write 0
    private byte bNEdge = (byte)15; //0x0F;//15 use with AND to write 0

    public AddEdgetoPic(int[] PixData, byte[][] Edge, int imgCols, int imgRows){
        CPixData = new int[imgRows*imgCols];
        for(int row = 0, cnt = 0;row < imgRows;row++){
            for(int col = 0; col < imgCols;col++){
                //if(Edge[row][col]==1){
                if(((byte)(Edge[row][col] & bEdge))==48){
                    CPixData[cnt] = (0xFFFFFFFF);
                }
                else{
                        //CPixData[cnt] = (PixData[cnt] & 0x00FFFFFF);
                        CPixData[cnt] = PixData[cnt];
                }
                cnt++;
            }
        }
        Edge = null;
        PixData =null;
    }
    public AddEdgetoPic(int[] PixData, byte[][] Edge, int imgCols, int imgRows, boolean Neg){
        CPixData = new int[imgRows*imgCols];
        for(int row = 0, cnt = 0;row < imgRows;row++){
            for(int col = 0; col < imgCols;col++){
                //if(Edge[row][col]==1){
                if(((byte)(Edge[row][col] & bEdge))==48){
                    CPixData[cnt] = (0xFFFFFFFF);
                }
                else{
                    if(Neg==true){
                        CPixData[cnt] = (0xFF000000);
                    }
                    else{
                        //CPixData[cnt] = (PixData[cnt] & 0x00FFFFFF);
                        CPixData[cnt] = PixData[cnt];
                    }
                }
                cnt++;
            }
        }
        Edge = null;
        PixData =null;
    }
    public int[] GetPixDatawNewEdge(){
        return CPixData;
    }
    public void destroyAddEdgetoPic(){
        CPixData=null;
    }

}
