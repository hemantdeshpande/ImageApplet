# ImageApplet
This applet lets you Select any area of the photo and replace it with any color, while preserving texture, shine and shadow effects.      
This applet also lets you select and replace colors in any portion of image, select objects in image and repaint them,        
change brightness, contrast and apply color filters to image. It works with JPG and PNG file formats and        
lets you save the image in JPG format. The areas of the image can be either selected using line, curve, free-form draws         
in the applet as well as edge detect in the applet, which use Laplace-Canny method to detect edges. To change to colors        
while preserving the light-shadow and texture effects, the applet uses RGB cube properties to separate out white-black       
components and colors. It is entirely done in RGB space to be computationally efficient. 

The code is implemented in Java, using NetBeans platform using java.awt.image, javax.imageio and javax.swing libraries
