
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
class Pixel {
   BufferedImage image;
   BufferedImage test;
   int width;
   int height;
   static int red=0;
   static int blue=0;
   static int green=0;
   int n1=0,n2=0;
   String[]colors={"blue","red","green","yellow"};
   int counter=0;
   int interval=5;
   int color=0;
   int limit=11000;
   int stickers[][]=new int[100][4];
   int stickercount=0;
   //boolean stickerstatus=false;
   int colorcode[][]={{0,0,255},{255,0,0},{0,255,0},{255,255,0}};
   int[][]surround={{1,1,1,0,0,0,-1,-1,-1},{1,0,-1,1,0,-1,1,0,-1}};
   void manip(int i,int j)
   {
      //System.out.println(i+","+j);
     System.out.println(counter);
     if (counter<limit) {
      counter++;
      Color newColor = new Color(255,255,255);
      Color newColor2 = new Color(colorcode[color][0],colorcode[color][1],colorcode[color][2]);
      //System.out.println(newColor2.getRed()+","+newColor2.getGreen()+","+newColor2.getBlue());
      test.setRGB(i,j,newColor2.getRGB());
      image.setRGB(i,j,newColor.getRGB());
      for (int k=0;k<9;k++)
      {
      if (i+surround[0][k]>=0 && i+surround[0][k]<width && j+surround[1][k]>=0 && j+surround[1][k]<height) 
      {
         Color abc=new Color(image.getRGB(i+surround[0][k],j+surround[1][k]));
         if (colorTest(abc,color))
         {

            //System.out.println((i+surround[0][k])+","+(j+surround[1][k]));
            manip(i+surround[0][k],j+surround[1][k]);
         } 
      }
      }
   }
   else {
      n1=i; n2=j;
   }
   }

   boolean colorTest(Color cd,int k)
   {
      if (k==0)
      {
      if (cd.getBlue()>50 && cd.getRed()<cd.getBlue()/3 && cd.getGreen()<cd.getBlue())
         return true;
      else return false;
      }
      else if (k==1)
      {
         if (cd.getRed()>50 && cd.getBlue()<cd.getRed()/1.7 && cd.getGreen()<cd.getRed()/1.7)
         return true;
      else return false;
      }
      else if (k==2)
      {
         if (cd.getGreen()>50 && cd.getBlue()<cd.getGreen()/1.5 && cd.getRed()<cd.getGreen()/2)
         return true;
      else return false;
      }
       else if (k==3)
      {
         if (cd.getGreen()>50 && cd.getRed()>50 && cd.getBlue()<cd.getGreen()/2 && cd.getBlue()<cd.getRed()/2)
         return true;
      else return false;
      }
      return false;
   }
   void RGB(int i,int j)
   {
      Color c = new Color(image.getRGB(i,j));
      System.out.println(c.getRed()+","+c.getGreen()+","+c.getBlue());
   }
   public Pixel() {
      try {
         File input = new File("blue17.jpg");
         image = ImageIO.read(input);
         width = image.getWidth();
         height = image.getHeight();
         test= ImageIO.read(input);
         Color newColor=new Color(255,255,255);
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
      File ouptut = new File("blue15.jpg");
      ImageIO.write(image, "jpg", ouptut);
   }
   void noiseRemoval()
   {

   }
   void findColors() throws IOException
   {
      
      for (int k=0;k<4;k++)
      {
      for (int i=0;i<width;i+=interval)
      {
         for (int j=0;j<height;j+=interval)
         {
            counter=0;
            Color rg=new Color(image.getRGB(i,j));
            if (colorTest(rg,k))
            {
               //System.out.println("phase");
               manip(i,j);
               //System.out.println(counter);
               if (counter>800)
               {
                  
                  stickers[stickercount][0]=i;
                  stickers[stickercount][1]=j;
                  stickers[stickercount][2]=k;
                  stickers[stickercount][3]=counter;
                  stickercount++;
                  while (counter==limit)
                  {
                     counter=0;
                     manip(n1,n2);
                  }
               }
            }
         }  
      }
      color++;
      }
      /*if (stickercount>9) {
      for (int i=0;i<stickercount;i++)
      {
         if (stickers[i][3]==11000)
         {
            int j=i+1;
            int check=0;
            do
            {
               check=stickers[j][3];
               stickers[i][3]+=stickers[j][3];
               //j++;
               for (int k=j;k<stickercount-1;k++)
               {
                  for (int f=0;f<4;f++)
                     stickers[k][f]=stickers[k+1][f];
               }
               stickercount--;
            } while (check==11000);
         }
      }
      }*/
      for (int i=0;i<stickercount;i++)
         System.out.println(stickers[i][0]+","+stickers[i][1]+","+stickers[i][2]+","+stickers[i][3]);
      for (int i=0;i<8;i++)
      {
         for (int j=i+1;j<9;j++)
         {
            if (stickers[i][1]>stickers[j][1])
            {
               for (int k=0;k<4;k++)
               {
                  int temp=stickers[i][k];
                  stickers[i][k]=stickers[j][k];
                  stickers[j][k]=temp;
               }
            }
         }
      }
      for (int i=1;i<3;i++)
      {
         for (int j=i+1;j<4;j++)
         {
            if (stickers[i][0]>stickers[j][0])
            {
               for (int k=0;k<4;k++)
               {
                  int temp=stickers[i][k];
                  stickers[i][k]=stickers[j][k];
                  stickers[j][k]=temp;
               }
            }
         }
      }
      for (int i=4;i<8;i++)
      {
         for (int j=i+1;j<9;j++)
         {
            if (stickers[i][0]>stickers[j][0])
            {
               for (int k=0;k<4;k++)
               {
                  int temp=stickers[i][k];
                  stickers[i][k]=stickers[j][k];
                  stickers[j][k]=temp;
               }
            }
         }
      }
      for (int i=0;i<9;i++) 
      {
         System.out.print(colors[stickers[i][2]]+",");
         if (i==0 || i==3 || i==8) System.out.println();
      }
      File ouptut = new File("triangledetect1.jpg");
      ImageIO.write(test, "jpg", ouptut);
   }
   static public void main(String args[]) throws IOException 
   {
      Pixel obj = new Pixel();
      Scanner sc=new Scanner(System.in);
      int i=1,j=1;
      /*i=sc.nextInt();
      j=sc.nextInt();
      obj.RGB(i,j);*/
      obj.findColors();
   }
}
