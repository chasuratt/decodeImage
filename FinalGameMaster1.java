import java.util.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class FinalGameMaster1 { 
    public static void main(String[] args) {

        ImageManager im1 = new ImageManager() ;
        im1.read("FinalDIP64.bmp") ;
        im1.averagingFilter(3);
        //im1.write("avg.bmp") ;
        im1.thresholding(205);

        // find conner
        int minY = 1000;
        int minX = 1000;
        int maxY = 0;
        int maxX = 0;
        Point p1 = new Point();
        // Point p2 = new Point();
        // Point p3 = new Point();
        // Point p4 = new Point();
        
        // point 1
        for (int x = 0; x < im1.width; x++) { // minX
            for (int y = 0; y < im1.height; y++) {
      
                if (im1.getColor(x, y) == 0xffffff) { // 
                  if (x < minX) {
                    minX = x ;
                 //   p3 = new Point(minX,y) ;
                    p1.x = minX ;
                    p1.y = y ;
                    im1.setColor(x, y, 255, 0, 0); //mask point
                  }  
                }
            }
          }

        //  im1.write("conner.bmp") ;
    

        // ImageManager im2 = new ImageManager() ;

        im1.img = im1.rotateImg(31, im1.width/2, im1.height/2) ;
       // im1.write("Threshold_205_1.bmp") ;

        for (int x = 0; x < im1.width; x++) {  
            for (int y = 0; y < im1.height; y++) {
                if (im1.getColor(x, y) == 0xFF0000) { //
                    p1.x = x ;
                    p1.y = y ; 
                }

            }
        }
        
        p1.x =p1.x+50 ;
        p1.y =p1.y+100 ;

        System.out.println(p1);

        im1.setColor(p1.x, p1.y, 0, 255, 0);
        

        im1.restoreToOriginal();
        im1.averagingFilter(5);
        im1.img = im1.rotateImg(31, im1.width/2, im1.height/2) ;
        im1.img = im1.img.getSubimage(p1.x, p1.y,325, 85);
        im1.write("Crop.bmp") ;
        double[] laplacian = {1,1,1,1,-8,1,1,1,1} ;

        System.out.println(im1.height+" "+im1.width);

        ImageManager im2 = new ImageManager() ;
       
        
        im2.read("Crop.bmp") ;
        im2.convertToGray();
        im2.averagingFilter(5);
        im2.thresholding(200);
        System.out.println(im2.height+" "+im2.width);

        im2.write("thresholding200.bmp") ;

        ImageManager num1 = new ImageManager() ;
        ImageManager num2 = new ImageManager() ;
        ImageManager num3 = new ImageManager() ;
        ImageManager num4 = new ImageManager() ;
        ImageManager num5 = new ImageManager() ;
        ImageManager num6 = new ImageManager() ;
        ImageManager temp = new ImageManager() ;
        
        

        num1.img = im2.img.getSubimage(13, 0, (79-13),(85-0) ) ;  // w =66 h = 85
        num1.write("num1.bmp") ;
        int imgType = num1.getType() ;
        temp.read("num1.bmp") ;
        temp.resizeBilinear(5, 5);
        num1.img = temp.rotateImg(1,(temp.width)/2,(temp.height)/2) ;
        num1.img = num1.img.getSubimage( 86,70,(261-86), (310-70)) ;
    
        num1.write("num1.bmp") ;
        temp.read("num1.bmp") ;

        //System.out.println(temp.width+" "+temp.height);

        // re create new img : 177 * 243 
        BufferedImage tempBuffer = new BufferedImage(177, 243,imgType) ;

        for (int x = temp.width-1; x >=0 ; x--) {  
            for (int y = 0; y < temp.height; y++) {
                if (temp.getColor(x, y) == 0x000000) { //
                    tempBuffer.setRGB(x+(177-temp.width-1), y, 0xffffff);
                }

            }
        }
        num1.img = tempBuffer ;
        num1.write("num1.bmp") ;
       // num1.write("num1.jpg") ;
    

        num2.img = im2.img.getSubimage(69, 0, (126-69),(85-0) ) ;
        num2.write("num2.bmp") ;
        temp.read("num2.bmp") ;
        num2.img = temp.rotateImg(3,(126-69)/2,(85-0)/2) ;
        temp.resizeBilinear(5, 5);
        num2.img = temp.rotateImg(2,(temp.width)/2,(temp.height)/2) ;
        num2.img = num2.img.getSubimage( 130,80 ,(237-127),(323-80)) ;

        num2.write("num2.bmp") ;
        temp.read("num2.bmp") ;

        tempBuffer = new BufferedImage(177, 243,imgType) ;

        for (int x = temp.width-1; x >=0 ; x--) {  
            for (int y = 0; y < temp.height; y++) {
                if (temp.getColor(x, y) == 0x000000) { //
                    tempBuffer.setRGB(x+(177-temp.width-1), y, 0xffffff);
                }

            }
        }
        num2.img = tempBuffer ;
        num2.write("num2.bmp") ;
       // num2.write("num2.jpg") ;
    

        num3.img = im2.img.getSubimage(121, 0, (185-121),(85-0) ) ;
        num3.write("num3.bmp") ;
        temp.read("num3.bmp") ;
        temp.resizeBilinear(5, 5);
        num3.img = temp.rotateImg(2,(temp.width)/2,(temp.height)/2) ;
        num3.img = num3.img.getSubimage( 47,81, (222-46),(323-81)) ;
        num3.write("num3.bmp") ;
        //num3.write("num3.jpg") ;
        
        temp.read("num3.bmp") ;

        tempBuffer = new BufferedImage(177, 243,imgType) ;

        for (int x = temp.width-1; x >=0 ; x--) {  
            for (int y = 0; y < temp.height; y++) {
                if (temp.getColor(x, y) == 0x000000) { //
                    tempBuffer.setRGB(x+(177-temp.width-1), y, 0xffffff);
                }

            }
        }
        num3.img = tempBuffer ;
        num3.write("num3.bmp") ;
      // num3.write("num3.jpg") ;
    
 

        num4.img = im2.img.getSubimage(174, 0, (220-174),(85-0) ) ;
        num4.write("num4.bmp") ;
        temp.read("num4.bmp") ;
        temp.resizeBilinear(5, 5);
        num4.img = temp.rotateImg(2.5,(temp.width)/2,(temp.height)/2) ;
        num4.img = num4.img.getSubimage( 68,87, (191-68),(329-87)) ;
        num4.write("num4.bmp") ;
        temp.read("num4.bmp") ;

        tempBuffer = new BufferedImage(177, 243,imgType) ;

        for (int x = temp.width-1; x >=0 ; x--) {  
            for (int y = 0; y < temp.height; y++) {
                if (temp.getColor(x, y) == 0x000000) { //
                    tempBuffer.setRGB(x+(177-temp.width-1), y, 0xffffff);
                }

            }
        }
        num4.img = tempBuffer ;
        num4.write("num4.bmp") ;
       // num4.write("num4.jpg") ;
        

        num5.img = im2.img.getSubimage(220, 0, (272-220),(85-0) ) ;
        num5.write("num5.bmp") ;
        temp.read("num5.bmp") ;
        temp.resizeBilinear(5, 5);
        num5.img = temp.rotateImg(8,(temp.width)/2,(temp.height)/2) ;
        num5.img = num5.img.getSubimage( 42,95, (175-42),(328-95)) ;
        num5.write("num5.bmp") ;
        temp.read("num5.bmp") ;

        tempBuffer = new BufferedImage(177, 243,imgType) ;

        for (int x = temp.width-1; x >=0 ; x--) {  
            for (int y = 0; y < temp.height; y++) {
                if (temp.getColor(x, y) == 0x000000) { //
                    tempBuffer.setRGB(x+(177-temp.width-1), y, 0xffffff);
                }

            }
        }
        num5.img = tempBuffer ;
        num5.write("num5.bmp") ;
       // num5.write("num5.jpg") ;
        
    
        num6.img = im2.img.getSubimage(265, 0, (315-265),(85-0) ) ;
        num6.write("num6.bmp") ;
        temp.read("num6.bmp") ;
        temp.resizeBilinear(5, 5);
        num6.img = temp.rotateImg(8,(temp.width)/2,(temp.height)/2) ;
        num6.img = num6.img.getSubimage(70,91, (169-70),(329-91)) ;
        num6.write("num6.bmp") ;

        temp.read("num6.bmp") ;

        tempBuffer = new BufferedImage(177, 243,imgType) ;

        for (int x = temp.width-1; x >=0 ; x--) {  
            for (int y = 0; y < temp.height; y++) {
                if (temp.getColor(x, y) == 0x000000) { //
                    tempBuffer.setRGB(x+(177-temp.width-1), y, 0xffffff);
                }

            }
        }
        num6.img = tempBuffer ;
        num6.write("num6.bmp") ;
       // num6.write("num6.jpg") ;

       // Draw table for see how to encode on img

       num1.read("num1.bmp") ;
       temp.read("num1.bmp") ;
       temp.testDrawTableOnImageForSeeToEncode(17, 24) ;
       num1.img = temp.img ;
       num1.write("draw_num1.bmp") ;

       num2.read("num2.bmp") ;
       temp.read("num2.bmp") ;
       temp.testDrawTableOnImageForSeeToEncode(17, 24) ;
       num2.img = temp.img ;
       num2.write("draw_num2.bmp") ;

       num3.read("num3.bmp") ;
       temp.read("num3.bmp") ;
       temp.testDrawTableOnImageForSeeToEncode(17, 24) ;
       num3.img = temp.img ;
       num3.write("draw_num3.bmp") ;

       num4.read("num4.bmp") ;
       temp.read("num4.bmp") ;
       temp.testDrawTableOnImageForSeeToEncode(17, 24) ;
       num4.img = temp.img ;
       num4.write("draw_num4.bmp") ;

       num5.read("num5.bmp") ;
       temp.read("num5.bmp") ;
       temp.testDrawTableOnImageForSeeToEncode(17, 24) ;
       num5.img = temp.img ;
       num5.write("draw_num5.bmp") ;

       num6.read("num6.bmp") ;
       temp.read("num6.bmp") ;
       temp.testDrawTableOnImageForSeeToEncode(17, 24) ;
       num6.img = temp.img ;
       num6.write("draw_num6.bmp") ;
       
       // encode 
       String six = "10733452244" ;
       String five = "0005533335" ;
       String one = "0000079544" ;
       String nine = "0005222379" ;
       String two = "0000574476" ;
       String zero = "8722222348" ;

       // other num use the same way



       // decode
       String decodeNum1 = "" ;
       String decodeNum2 = "" ;
       String decodeNum3 = "" ;
       String decodeNum4 = "" ;
       String decodeNum5 = "" ;
       String decodeNum6 = "" ;

       temp.read("num1.bmp") ;
       decodeNum1 =  temp.countNumValueForEachColum(17, 24);
       System.out.println(decodeNum1);
              
       temp.read("num2.bmp") ;
       decodeNum2 =  temp.countNumValueForEachColum(17, 24);
       System.out.println(decodeNum2);

       temp.read("num3.bmp") ;
       decodeNum3 =  temp.countNumValueForEachColum(17, 24);
       System.out.println(decodeNum3);

       temp.read("num4.bmp") ;
       decodeNum4 = temp.countNumValueForEachColum(17, 24);
       System.out.println(decodeNum4);

       temp.read("num5.bmp") ;
       decodeNum5 = temp.countNumValueForEachColum(17, 24);
       System.out.println(decodeNum5);

      temp.read("num6.bmp") ;
       decodeNum6 = temp.countNumValueForEachColum(17, 24);
       System.out.println(decodeNum6);

       decode(decodeNum1) ;
    //    decode(decodeNum2) ;
    //    decode(decodeNum3) ;
    //    decode(decodeNum4) ;
    //    decode(decodeNum5) ;
    //    decode(decodeNum6) ;
       


       


    








       
        
       

    



        
    }// main

    public static void decode (String decodeNum) {
        switch (decodeNum) {
            case "0000079544"  : // one
              System.out.println(1);
                break;
        
             case "0000574476": // two
                System.out.println("2");
                break;
              case "0005533335" : // five
                System.out.println("5");
                break;
              case "10733452244": // six
                System.out.println("6");
                break;
              case "0005222379": // nine
                System.out.println("9");
                break;
              case "8722222348": //zero
                System.out.println("0");
                break;
        }
    }


} // class