/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gtthere;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

/* ImageFilter.java is used by FileChooserDemo2.java. */

/**
 *
 * @author Hemant
 */
public class GIFFilter extends FileFilter {

    //Accept all directories and all gif, jpg, tiff, or png files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = Utils.getExtension(f);
        if (extension != null) {
            //if (extension.equals(Utils.tiff) ||
            //    extension.equals(Utils.tif) ||
            //    extension.equals(Utils.gif) ||
            //    extension.equals(Utils.jpeg) ||
            //    extension.equals(Utils.jpg) ||
            //    extension.equals(Utils.png)) {
            if (extension.equals(Utils.gif)
                ) {
                    return true;
            } else {
                return false;
            }
        }

        return false;
    }

    //The description of this filter
    public String getDescription() {
        return "GIF Image";
    }
}