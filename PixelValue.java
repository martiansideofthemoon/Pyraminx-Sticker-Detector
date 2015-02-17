
import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.*;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
class PixelValue {
   BufferedImage image;
   BufferedImage test;
   int width;
   int height;
   static int red=0;
   static int blue=0;
   static int green=0;
   int interval=2;
   int[][]surround={{1,1,1,0,0,0,-1,-1,-1},{1,0,-1,1,0,-1,1,0,-1}};
   void manip(int i,int j)
   {
      //System.out.println(i+","+j);
      Color newColor = new Color(255,255,255);
      test.setRGB(i,j,newColor.getRGB());
      image.setRGB(i,j,newColor.getRGB());
      for (int k=0;k<9;k++)
      {
      if (i+surround[0][k]>=0 && surround[0][k]<width && j+surround[1][k]>=0 && j+surround[1][k]<height) 
      {
         Color abc=new Color(image.getRGB(i+surround[0][k],j+surround[1][k]));
         if (colorTest(abc))
         {
            manip(i+surround[0][k],j+surround[1][k]);
         } 
      }
      }
   }
   boolean colorTest(Color cd)
   {
      if (cd.getBlue()>50 && cd.getRed()<20 && cd.getGreen()<cd.getBlue())
         return true;
      else return false;
   }
   void RGB(int i,int j)
   {
      Color c = new Color(image.getRGB(i,j));
      System.out.println(c.getRed()+","+c.getGreen()+","+c.getBlue());
   }
   public PixelValue() {
      try {
         File input = new File("blue9.jpg");
         image = ImageIO.read(input);
         width = image.getWidth();
         height = image.getHeight();
         test= ImageIO.read(input);
         Color newColor=new Color(0,0,0);
      for (int i=0;i<width;i++)
      {
         for (int j=0;j<height;j++)
         {
            test.setRGB(i,j,newColor.getRGB());
         }
      }
      } catch (Exception e) {}
   }
   void create(int i,int j) throws IOException
   {
      manip(i,j);
      File ouptut = new File("triangledetect.jpg");
      ImageIO.write(image, "jpg", ouptut);
   }
   void findBlue() throws IOException
   {
      
      
      for (int i=0;i<width;i+=interval)
      {
         for (int j=0;j<height;j+=interval)
         {
            Color rg=new Color(image.getRGB(i,j));
            if (colorTest(rg))
            {
               manip(i,j);
            }
         }  
      }
      File ouptut = new File("triangledetect.jpg");
      ImageIO.write(test, "jpg", ouptut);
   }
   static public void main(String args[]) throws Exception 
   {
      PixelValue obj = new PixelValue();
      Scanner sc=new Scanner(System.in);
      int i=1,j=1;
      while (!(i==0 && j==0)) {
      i=sc.nextInt();
      j=sc.nextInt();
      obj.RGB(i,j);
      }
      //obj.findBlue();
   }
}