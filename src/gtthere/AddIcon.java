/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gtthere;
import java.awt.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.color.ColorSpace;

/**
 *
 * @author Hemant Deshpande
 */
public class AddIcon extends Object{
    private static ImageIcon ii;
    private static BufferedImage bufimg;
    private static BufferedImage reImg;
    private static Color  xorColor = new Color(255,255,255);
    private static BasicStroke stroke = new BasicStroke(1.0f);
    final static float dash1[] = {3.0f};
    final static BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_SQUARE,
                                          BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);

    public static ImageIcon InitialImage(){
        int ImgX = 535;
        int ImgY = 400;
        BufferedImage reSizedImg = new BufferedImage(ImgX, ImgY, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = reSizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        g2.setColor(Color.BLACK);
        g2.fillRect(80, 170, 400, 130);
        g2.setColor(Color.blue);
        g2.fillRect(110, 150, 400, 130);
        Font font = new Font("Arial", Font.PLAIN, 18);
        g2.setFont(font);
        g2.setColor(Color.WHITE);
        g2.setFont(font);
        g2.drawString("This applet is created by", 190,190);
        g2.drawString("-Hemant V. Deshpande", 220,220);
        g2.drawString("www.apicview.com", 220,250);
        g2.dispose();
        ii = new ImageIcon(reSizedImg);
        return ii;
    }

    public static ImageIcon ZoomImageIcon(Image img, int MainImgX, int MainImgY, double Zoom){
        int ImgX = (int)((MainImgX/Zoom));
        int ImgY = (int)(MainImgY/Zoom);
        BufferedImage reSizedImg = new BufferedImage(ImgX, ImgY, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = reSizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        g2.drawImage(img, 0, 0, ImgX, ImgY, null);
        g2.dispose();
        ii = new ImageIcon(reSizedImg);
        return ii;
    }
    public static BufferedImage MergeImage(Image bimg, Image timg, int ImgX, int ImgY){
        BufferedImage reSizedImg= new BufferedImage(ImgX, ImgY, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = reSizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        g2.drawImage(bimg, 0, 0, ImgX, ImgY, null);
        g2.drawImage(timg, 0, 0, ImgX, ImgY, null);
        g2.dispose();
        return reSizedImg;
    }
    public static BufferedImage getBufImg(Image img, int MainImgX, int MainImgY, boolean alpha){
        if (alpha==true) bufimg = new BufferedImage(MainImgX, MainImgY, BufferedImage.TYPE_INT_ARGB);
        else bufimg = new BufferedImage(MainImgX, MainImgY, BufferedImage.TYPE_INT_RGB);
        //bufimg = new BufferedImage(MainImgX, MainImgY, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bufimg.createGraphics();
        g2.drawImage(img, 0, 0, MainImgX, MainImgY, null);
        g2.dispose();
        return bufimg;
    }
    public static BufferedImage getReisezedImg(Image img, int MainImgX, int MainImgY, boolean alpha){
        int type = BufferedImage.TYPE_INT_RGB;
        if (alpha==true) {
                type = BufferedImage.TYPE_INT_ARGB;
        }
        reImg= new BufferedImage(MainImgX/2, MainImgY/2, type);
        Graphics2D g2 = reImg.createGraphics();
        g2.drawImage(img, 0, 0, MainImgX/2, MainImgY/2, null);
        g2.dispose();
        return reImg;
    }
    public static ImageIcon ZoomEImageIcon(Image img, Image edge, int MainImgX, int MainImgY, double Zoom){
        int ImgX = (int)((MainImgX/Zoom));
        int ImgY = (int)(MainImgY/Zoom);
        BufferedImage reSizedImg = new BufferedImage(ImgX, ImgY, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = reSizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        g2.drawImage(img, 0, 0, ImgX, ImgY, null);
        g2.drawImage(edge, 0, 0, ImgX, ImgY, null);
        g2.dispose();
        ii = new ImageIcon(reSizedImg);
        return ii;
    }
    public static ImageIcon AddEdgetoImageIcon(Image img, int MainImgX, int MainImgY, double Zoom, int[] boundRect){
        int ImgY = (int)(MainImgY/Zoom);
        int ImgX = (int)(MainImgX/Zoom);
        int X1 = (int)(boundRect[0]/Zoom);
        int Y1 = (int)(boundRect[1]/Zoom);
        int X2 = (int)(boundRect[2]/Zoom);
        int Y2 = (int)(boundRect[3]/Zoom);
        BufferedImage NewImg = new BufferedImage(ImgX, ImgY, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = NewImg.createGraphics();
        //g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        g2.drawImage(img, 0, 0, ImgX, ImgY, null);
        //g2.drawImage(edge, 0, 0, ImgX, ImgY, null);
        g2.setStroke(stroke);
        g2.draw(new Line2D.Double(X1, Y1, X2, Y2));
        g2.setXORMode(xorColor);
        g2.dispose();
        ii = new ImageIcon(NewImg);
        return ii;
    }
    public static ImageIcon AddRecttoImageIcon(Image img, int MainImgX, int MainImgY, double Zoom, int[] boundRect){
        int ImgY = (int)(MainImgY/Zoom);
        int ImgX = (int)(MainImgX/Zoom);
        int X1 = (int)(boundRect[0]/Zoom);
        int Y1 = (int)(boundRect[1]/Zoom);
        int X2 = (int)(boundRect[2]/Zoom);
        int Y2 = (int)(boundRect[3]/Zoom);
        BufferedImage NewImg = new BufferedImage(ImgX, ImgY, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = NewImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        g2.drawImage(img, 0, 0, ImgX, ImgY, null);
        //g2.drawImage(edge, 0, 0, ImgX, ImgY, null);
        g2.setStroke(stroke);
        g2.setColor(xorColor);
        g2.draw(new Line2D.Double(X1, Y1, X1, Y2));
        g2.draw(new Line2D.Double(X1, Y1, X2, Y1));
        g2.draw(new Line2D.Double(X2, Y1, X2, Y2));
        g2.draw(new Line2D.Double(X1, Y2, X2, Y2));
        //g2.draw(new Rectangle2D.Double(startX, startY, width, height));
        //g2.drawRect(startX, startY, width, height);
        g2.setXORMode(xorColor);
        g2.dispose();
        ii = new ImageIcon(NewImg);
        return ii;
    }
    public static ImageIcon RectCurtoImageIcon(Image img, int MainImgX, int MainImgY, double Zoom, int Xpt, int Ypt, int Brush){
        int ImgY = (int)(MainImgY/Zoom);
        int ImgX = (int)(MainImgX/Zoom);
        int BrushAdjust = (int)((Brush+1)/Zoom);
        int X1 = (int)(Xpt/Zoom-BrushAdjust);
        int Y1 = (int)(Ypt/Zoom-BrushAdjust);
        int X2 = (int)(Xpt/Zoom+BrushAdjust);
        int Y2 = (int)(Ypt/Zoom+BrushAdjust);
        BufferedImage NewImg = new BufferedImage(ImgX, ImgY, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = NewImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        g2.drawImage(img, 0, 0, ImgX, ImgY, null);
        g2.setStroke(dashed);
        g2.setColor(xorColor);
        g2.draw(new Line2D.Double(X1, Y1, X1, Y2));
        g2.draw(new Line2D.Double(X1, Y1, X2, Y1));
        g2.draw(new Line2D.Double(X2, Y1, X2, Y2));
        g2.draw(new Line2D.Double(X1, Y2, X2, Y2));
        g2.setXORMode(xorColor);
        g2.dispose();
        ii = new ImageIcon(NewImg);
        return ii;
    }
    public static ImageIcon AddCirtoImageIcon(Image img, int MainImgX, int MainImgY, double Zoom, int Xpt, int Ypt, int Brush){
        int ImgY = (int)(MainImgY/Zoom);
        int ImgX = (int)(MainImgX/Zoom);
        int BrushAdjust = (int)((Brush+1)/Zoom);
        int Xc = (int)(Xpt/Zoom-BrushAdjust);
        int Yc = (int)(Ypt/Zoom-BrushAdjust);
        int Diameter = 2*BrushAdjust;
        BufferedImage NewImg = new BufferedImage(ImgX, ImgY, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = NewImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        g2.drawImage(img, 0, 0, ImgX, ImgY, null);
        g2.setStroke(dashed);
        g2.setColor(xorColor);
        g2.draw(new Ellipse2D.Double(Xc, Yc, Diameter, Diameter));
        g2.setXORMode(xorColor);
        g2.dispose();
        ii = new ImageIcon(NewImg);
        return ii;
    }
    public static ImageIcon DrawCirtoImageIcon(Image img, int MainImgX, int MainImgY, double Zoom, int[] boundRect){
        int ImgY = (int)(MainImgY/Zoom);
        int ImgX = (int)(MainImgX/Zoom);
        int X1 = (int)(boundRect[0]/Zoom);
        int Y1 = (int)(boundRect[1]/Zoom);
        int X2 = (int)(boundRect[2]/Zoom);
        int Y2 = (int)(boundRect[3]/Zoom);
        BufferedImage NewImg = new BufferedImage(ImgX, ImgY, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = NewImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        g2.drawImage(img, 0, 0, ImgX, ImgY, null);
        g2.setStroke(stroke);
        g2.setColor(xorColor);
        g2.draw(new Ellipse2D.Double(X1, Y1, X2-X1, Y2-Y1));
        g2.setStroke(dashed);
        g2.draw(new Line2D.Double(X1, Y1, X1, Y2));
        g2.draw(new Line2D.Double(X1, Y1, X2, Y1));
        g2.draw(new Line2D.Double(X2, Y1, X2, Y2));
        g2.draw(new Line2D.Double(X1, Y2, X2, Y2));
        g2.setXORMode(xorColor);
        g2.dispose();
        ii = new ImageIcon(NewImg);
        return ii;
    }
    public static ImageIcon DialogImageIcon(Color fillColor, Color maxColor, Color minColor){
        BufferedImage NewImg = new BufferedImage(110, 50, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = NewImg.createGraphics();
        //g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        stroke = new BasicStroke(1.0f);
        //g2.draw(new Rectangle2D.Double(0, 0, 10, 10));
        //g2.setXORMode(xorColor);
        g2.setColor(fillColor);
        g2.fillRect(5, 5, 30, 40);
        g2.setColor(maxColor);
        g2.fillRect(40, 5, 30, 40);
        g2.setColor(minColor);
        g2.fillRect(75, 5, 30, 40);
        g2.dispose();
        ii = new ImageIcon(NewImg);
        return ii;
        }

}
