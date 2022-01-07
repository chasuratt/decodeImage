import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.awt.* ;

public class ImageManager {

    public int width;
    public int height;
    public int bitDepth;
  //  private BufferedImage img; // 
    public BufferedImage img; // 
    private BufferedImage original; //
    
    public ImageManager() { 
 
    } // constructor
    
    public BufferedImage getImg() {
        return img ;
    }

    public int getType() {
        return img.getType();
    }

 //double[] lapacian1 = {1,1,1,1,-8,1,1,1,1} ;
 //double[] lapacian2 = {-1,-1,-1,-1,+8,-1,-1,-1,-1} ;
 //double[] sobelHorizontal = {-1,-2,-1,0,0,0,1,2,1} ;
 //double[] sobelVertical = {-1,0,1,-2,0,2,-1,0,1} ;
    

    public boolean read(String fileName) {
        try
            {
                img = ImageIO.read(new File(fileName));
                width = img.getWidth();
                height = img.getHeight();
                bitDepth = img.getColorModel().getPixelSize();

                System.out.println("Image "+fileName+" with "+width+" x "+height+" pixels ("+bitDepth+" bitsper pixel) has been read!");
                
                original = new BufferedImage(width, height, img.getType());
                for (int y = 0; y < height; y++)
                {
                    for (int x = 0; x < width; x++)
                    {
                        original.setRGB(x, y, img.getRGB(x, y));
                    }
                } // orginal in img buffer
                
                return true;
            }

        catch (IOException e)
            {
                System.out.println(e);
                return false;
            }

        } // read 

    public boolean write (String fileName) {

            try
            {
                ImageIO.write(img, "bmp", new File (fileName));
                System.out.println("Image "+fileName+" has been written!");
                return true;
            }
    
            catch (IOException e)
            {
                System.out.println(e);
                return false;
            }
    
            catch (NullPointerException e)

            {
               System.out.println(e);
                return false;
            }
         
        } // write

    public void convertToRed () { 
            if (img == null) return ;

            for (int y =0 ; y < height ; y++)
            {
                for (int x =0 ; x < width ; x++)
                {
                    int color = img.getRGB(x, y) ;
                    int r = (color >> 16) & 0xff;
                    int g = (color >> 8) & 0xff;
                    int b = color & 0xff;
                    color = (r << 16) | (0 << 8) | 0; // เลื่อนสีแดงกลับ
                    img.setRGB(x, y, color);

                }
            }


        } // convertToRed

    public void maskBuleChanel (int p1,int p2) {  // test
        if (img == null) return ;
        int yellow = ( 255 << 16) | (255 << 8) | 0; 
    
        for (int y =0 ; y < height ; y++)  {
            for (int x =0 ; x < width ; x++)  {

                int color = img.getRGB(x, y) ;
                int b = color & 0xff;

                if (b >=p1 && b<=p2 ) {
                        img.setRGB(x, y, yellow);
                    }
                }
            } 

        }  // test

    public void maskGreenChanel (int p1,int p2) {  // test
            if (img == null) return ;
            int yellow = ( 255 << 16) | (255 << 8) | 0; 
        
            for (int y =0 ; y < height ; y++)  {
                for (int x =0 ; x < width ; x++)  {
    
                    int color = img.getRGB(x, y) ;
                    int g = (color >> 8) & 0xff;
                    
                    if (g >=p1 && g <=p2 ) {
                            img.setRGB(x, y, yellow);
                        }
                    }
                } 
    
            }  // test

    public void maskRedChanel (int p1,int p2) {  // test
                if (img == null) return ;
                int yellow = ( 255 << 16) | (255 << 8) | 0; 
            
                for (int y =0 ; y < height ; y++)  {
                    for (int x =0 ; x < width ; x++)  {
        
                        int color = img.getRGB(x, y) ;
                        int r = (color >> 16) & 0xff;
        
                        if (r >=p1 && r <=p2 ) {
                                img.setRGB(x, y, yellow);
                            }
                        }
                    } 
        
                }  // test

                
    public void maskGrayChanel (int p1,int p2) {  // test
                if (img == null) return ;
                int red = ( 255 << 16) | (0 << 8) | 0; 
            
                for (int y =0 ; y < height ; y++)  {
                    for (int x =0 ; x < width ; x++)  {
        
                        int color = img.getRGB(x, y) ;
                        int r = (color >> 16) & 0xff;
                        int g = (color >> 8) & 0xff;
                        int b = color & 0xff;
                        int gray = (int) ( (0.2126*r) + (0.7152*g) + (0.0722*b) ) ;
        
                        if (gray >=p1 && gray <=p2 ) {
                                img.setRGB(x, y, red);
                            }
                        }
                    } 
        
                }  // test


    public void maskAllChannel (int r1,int r2,int g1,int g2,int b1 ,int b2 ) {  // test
   
    if (img == null) return ;
        int yellow = ( 255 << 16) | (255 << 8) | 0; 
                
        for (int y =0 ; y < height ; y++)  {
            for (int x =0 ; x < width ; x++)  {
            
                int color = img.getRGB(x, y) ;
                int r = (color >> 16) & 0xff;
                int g = (color >> 8 ) & 0xff;
                int b = (color >> 0 ) & 0xff;
            
                if (r >=r1 && r <=r2 && g >=g1 && g<=g2 && b >=b1 && b<=b2   ) {
                     img.setRGB(x, y, yellow);
                }
            }
        } 

    }  // 

    public void setColor (int r1, int g1, int b1 ,int r2, int g2, int b2 ) {  // 
    
        /*
            rgb1 = original color
            rgb2 = replace color
        */

        if (img == null) return ;
        
        int replaceColor = ( r2 << 16) | (g2 << 8) | b2 ; 
       
        for (int x = 0 ; x < width ; x++)  {
             for (int y = 0 ; y < height ; y++)  {


                int color = img.getRGB(x, y) ;
                int r = (color >> 16) & 0xff;
                int g = (color >> 8 ) & 0xff;
                int b = (color >> 0 ) & 0xff;
                
                if ( r==r1 && g==g1 && b==b1) {
                        
                        img.setRGB(x, y, replaceColor );
                    }
                else img.setRGB(x, y, 0x000000); // ถมดำ
                    
                }
            } 
        

        }  // set color

        
    public void setColor (int x,int y ,int r1, int g1, int b1 ) {  // 
    
        if (img == null) return ;
        
        int replaceColor = ( r1 << 16) | (g1 << 8) | b1 ; 
         
            img.setRGB(x, y, replaceColor );

        }  // set color

    public void pad_fill () {
        // check from 8 neigbor , with the mask
        for (int y =0 ; y < height ; y++)  {
            for (int x =0 ; x < width ; x++)  {
            }}



    } // 
    


    public void convertToGreen () { 
            if (img == null) return ;

            for (int y =0 ; y < height ; y++)
            {
                for (int x =0 ; x < width ; x++)
                {
                    int color = img.getRGB(x, y) ;
                    int r = (color >> 16) & 0xff;
                    int g = (color >> 8) & 0xff;
                    int b = color & 0xff;
                    color = (0 << 16) | (g << 8) | 0; // เลื่อนสีเขียวกลับ
                    img.setRGB(x, y, color);

                }
            }


        } // convertToGreen 

        public void closeGreen () { 
            if (img == null) return ;

            for (int y =0 ; y < height ; y++)
            {
                for (int x =0 ; x < width ; x++)
                {
                    int color = img.getRGB(x, y) ;
                    int r = (color >> 16) & 0xff;
                    int g = 0;
                    int b = color & 0xff;
                    color = (r << 16) | (g << 8) | b; // เลื่อนสีเขียวกลับ
                    img.setRGB(x, y, color);

                }
            }


        } // convertToGreen 

    public void convertToBule () { 
            if (img == null) return ;

            for (int y =0 ; y < height ; y++)
            {
                for (int x =0 ; x < width ; x++)
                {
                    int color = img.getRGB(x, y) ;
                    int r = (color >> 16) & 0xff;
                    int g = (color >> 8) & 0xff;
                    int b = color & 0xff;
                    color = (0 << 16) | (0 << 8) | b; // // เลื่อนสีน้ำเงินกลับ
                    img.setRGB(x, y, color);

                }
            }


        } // convertToBlue 

        public void closeBule () { 
            if (img == null) return ;

            for (int y =0 ; y < height ; y++)
            {
                for (int x =0 ; x < width ; x++)
                {
                    int color = img.getRGB(x, y) ;
                    int r = (color >> 16) & 0xff;
                    int g = (color >> 8) & 0xff;
                    int b = 0 ;
                    color = (r << 16) | (g << 8) | b; // เลื่อนสีเขียวกลับ
                    img.setRGB(x, y, color);

                }
            }


        } // 

        public void closeRed () { 
            if (img == null) return ;

            for (int y =0 ; y < height ; y++)
            {
                for (int x =0 ; x < width ; x++)
                {
                    int color = img.getRGB(x, y) ;
                    int r = 0;
                    int g = (color >> 8) & 0xff;
                    int b = color & 0xff;
                    color = (r << 16) | (g << 8) | b; // เลื่อนสีเขียวกลับ
                    img.setRGB(x, y, color);

                }
            }


        } // convertToGreen 

    public void convertToGray () { 
            if (img == null) return ;

            for (int y =0 ; y < height ; y++)
            {
                for (int x =0 ; x < width ; x++)
                {
                
                    int color = img.getRGB(x, y) ;
                    int r = (color >> 16) & 0xff;
                    int g = (color >> 8) & 0xff;
                    int b = color & 0xff;
                    int gray = (int) Math.round (0.2126*r + 0.7152*g + 0.0722*b ) ;
                    color = (gray  << 16) | (gray  << 8) | gray ;   
                    img.setRGB(x, y, color);

                }
            }


        } // convertToGray


    public void restoreToOriginal() {

        width = original.getWidth();
        height = original.getHeight();
        
        
        for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++)  {
                    img.setRGB(x, y, original.getRGB(x, y)); 
                }
         }

    } // restoreToOriginal

        
    public void setTemperature(int rTemp, int gTemp, int bTemp) {
    
        if (img == null)    return;

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                int color = img.getRGB(x, y);
                int r = (color >> 16) & 0xff;
                int g = (color >> 8) & 0xff;
                int b = color & 0xff;

                r *= (rTemp/255.0);
                r = r > 255? 255: r;
                r = r < 0? 0: r;

                g *= (gTemp/255.0);
                g = g > 255? 255: g;
                g = g < 0? 0: g;
 
                b *= (bTemp/255.0);           
                b = b > 255? 255: b;
                b = b < 0? 0: b;

                color = (r << 16) | (g << 8) | b; 

                img.setRGB(x, y, color);
            }
        }
    } // setTemperature
        

    public void grayScaleHistogramEqualisation() {
            if (img == null) return;
 
            int[] histogram = new int[256];
 
            for (int i = 0; i < 256; i++)
                {
                  histogram[i] = 0;
                 }
 
            for (int y = 0; y < height; y++)
             {
               for (int x = 0; x < width; x++)
               {
                   int color = img.getRGB(x, y);
                   int r = (color >> 16) & 0xff;
                   int g = (color >> 8) & 0xff;
                   int b = color & 0xff;
 
                   int gray = (int)(0.2126*r + 0.7152*g + 0.0722*b);  // 
 
                   histogram[gray]++;
               }
            }
 
            int[] histogramCDF = new int [256];
            int cdfMin = 0;
 
            for (int i = 0; i < 256; i++)
            {
            histogramCDF[i] = 0;
            }
 
            for (int i = 0; i < 256; i++) {
                if (i == 0) histogramCDF[i] = histogram[i];
                    else histogramCDF[i] = histogramCDF[i-1] + histogram[i];
                if (histogram[i] > 0 && cdfMin == 0)    cdfMin = i;
            } //cdf
 
          for (int y = 0; y < height; y++)
          {
               for (int x = 0; x < width; x++)
             {
                 int color = img.getRGB(x, y);
                 int r = (color >> 16) & 0xff;
                 int g = (color >> 8) & 0xff;
                 int b = color & 0xff;
 
                 int gray = (int)(0.2126*r + 0.7152*g + 0.0722*b);
 
                gray = (int)Math.round(255.0*(histogramCDF[gray] - cdfMin)/(width*height-cdfMin)); //  ตามสูตร
                gray = gray > 255? 255: gray;
                gray = gray < 0? 0: gray; 
 
                color = (gray << 16) | (gray << 8) | gray;
                img.setRGB(x, y, color);
             }   
          }
     } // grayscaleHistogramEqualisation


    public void colorHistogramEqualisation() { // whiteBalance เพี้ยน

        if (img == null)    return;
 
        int[] histogramRed = new int[256];
        int[] histogramGreen = new int[256];
        int[] histogramBlue = new int[256];

        for (int i = 0; i < 256; i++)
        {
            histogramRed[i] = 0;
            histogramGreen [i] = 0;
            histogramBlue[i] = 0;
       
        } // initial bucket Array index 0 - 255


        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                int color = img.getRGB(x, y);
                int r = (color >> 16) & 0xff;
                int g = (color >> 8) & 0xff;
                int b = color & 0xff;
     
                histogramRed[r]++;
                histogramGreen[g]++;
                histogramBlue[b]++;
            }
        }

        float pixelNum = width * height;
        int histogramRedMin = 0, histogramGreenMin = 0, histogramBlueMin = 0;

        int[] histogramRedCDF = new int[histogramRed.length];
        int[] histogramGreenCDF = new int[histogramGreen.length];
        int[] histogramBlueCDF = new int[histogramBlue.length];
    
   
        for (int i = 0; i < 256; i++)
        {
            histogramRedCDF[i] = 0;
            histogramGreenCDF [i] = 0;
            histogramBlueCDF[i] = 0;
        }
     

        for (int i = 0; i < 256; i++)
        {
            if (histogramRed[i] > 0 && histogramRedMin == 0)  histogramRedMin = i; // findMin

            if (i == 0)  histogramRedCDF[i] = histogramRed[i];
            else  histogramRedCDF[i] = histogramRedCDF[i - 1] + histogramRed[i];
            
            if (histogramGreen[i] > 0 && histogramGreenMin == 0)  histogramGreenMin = i; // findMin 

            if (i == 0)  histogramGreenCDF[i] = histogramGreen[i];
            else  histogramGreenCDF[i] = histogramGreenCDF[i - 1] + histogramGreen[i];
     
            if (histogramBlue[i] > 0 && histogramBlueMin == 0)  histogramBlueMin = i; // findMin

            if (i == 0)  histogramBlueCDF[i] = histogramBlue[i];
            else  histogramBlueCDF[i] = histogramBlueCDF[i - 1] + histogramBlue[i];
       
        } // cdf
     
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                int color = img.getRGB(x, y);
                int r = (color >> 16) & 0xff;
                int g = (color >> 8) & 0xff;
                int b = color & 0xff;
     
                r = (int)(255.0 * (histogramRedCDF[r] - histogramRedCDF[histogramRedMin])/(pixelNum - histogramRedCDF[histogramRedMin])); // ตามสูตร
                r = r > 255? 255: r;
                r = r < 0? 0: r;
     
                g = (int)(255.0 * (histogramGreenCDF[g] - histogramGreenCDF[histogramGreenMin])/(pixelNum - histogramGreenCDF[histogramGreenMin]));
                g = g > 255? 255: g;
                g = g < 0? 0: g;
     
                b = (int)(255.0 * (histogramBlueCDF[b] - histogramBlueCDF[histogramBlueMin])/(pixelNum - histogramBlueCDF[histogramBlueMin]));
                b = b > 255? 255: b;
                b = b < 0? 0: b;
     
                color = (r << 16) | (g << 8) | b;
     
                img.setRGB(x, y, color);
            }
        }        
    
    

    } // colorHistogramEqualisation 
              
    public void adjustBrightness (int brightness) {
        if (img == null) return;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int color = img.getRGB(x, y);

                int r = (color >> 16) & 0xff;
                int g = (color >> 8) & 0xff;
                int b = color & 0xff;

                r = r + brightness;
                r = r > 255? 255: r; // check upper range
                r = r < 0? 0: r; // check Lower range
 
                g = g + brightness;
                g = g > 255? 255: g;
                g = g < 0? 0: g;
 
                b = b + brightness;
                b = b > 255? 255: b;
                b = b < 0? 0: b;
                color = (r << 16) | (g << 8) | b;
                img.setRGB(x, y, color);
            }
        }
    } // adjustBrightness

    public void negative () {

        if (img == null) return;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int color = img.getRGB(x, y);
                int r = (color >> 16) & 0xff;
                int g = (color >> 8) & 0xff;
                int b = color & 0xff;

                r = 255 - r;
                g = 255 - g;
                b = 255 - b;
                color = (r << 16) | (g << 8) | b;
                img.setRGB(x, y, color);
            }
        }
    
    } // negative

    public void writeHistogramToCSV(int[] histogram, String fileName) {
       
        try {
            FileWriter fw = new FileWriter(fileName);
            for (int i = 0; i < histogram.length; i++) {
                    fw.write(histogram[i] + ",");
            }
         //   fw.write("\n");
            fw.close();
            System.out.println(fileName + " has been Saved");
         }
        catch (IOException e) {
            System.out.println(e);
        }
    } // writeHistogramToCSV

    public void writeColorHistogramToCSV(int[] histogramRed,int[] histogramGreen,int[] histogramBule, String fileName) {
        // lenge his_red = bule = green = 256 (0-255)
       
        try {
            FileWriter fw = new FileWriter(fileName);
            for (int i = 0; i < histogramRed.length; i++) {
                    fw.write(histogramRed[i] + ",");
            }
          //  fw.write(",Red");
            fw.write("\n");

            for (int i = 0; i < histogramGreen.length; i++) {
                fw.write(histogramGreen[i] + ",");
             }
           // fw.write(",Green");
          fw.write("\n");

            for (int i = 0; i < histogramBule.length; i++) {
                fw.write(histogramBule[i] + ",");
             }
            //fw.write(",Bule");
            fw.write("\n");

            fw.close();
            System.out.println(fileName + " has been Saved");
         }
        catch (IOException e) {
            System.out.println(e);
        }
    } // writeHistogramToCSV

    public int[] getGrayScaleHistogram() {

        if (img == null) return null;

        convertToGray();
        int[] histogram = new int[256];

        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){

                int color = img.getRGB(x, y);
                int gray = color & 0xff; // color & 0..0000000011111111 = color (0-255)
                histogram[gray]++;
            }
        }
        restoreToOriginal();
        return histogram;
    } // getGrayscaleHistogram

    public int[] getRedScaleHistogram() {

        if (img == null) return null;

        convertToRed();
        int[] histogram = new int[256];

        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){

                int color = img.getRGB(x, y);
                int red = (color >> 16) & 0xff; 
                histogram[red]++;
            }
        }
        restoreToOriginal();
        return histogram;
    } // getRednscaleHistogram

    public int[] getGreenScaleHistogram() {
        
        if (img == null) return null;

        convertToGreen();
        int[] histogram = new int[256];

        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){

                int color = img.getRGB(x, y);
                int green = (color >> 8) & 0xff; 
                histogram[green]++;
            }
        }
        restoreToOriginal();
        return histogram;
    } // getGreennscaleHistogram



    public int[] getBuleScaleHistogram() {

        if (img == null) return null;

        convertToBule();
        int[] histogram = new int[256];

        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){

                int color = img.getRGB(x, y);
                int bule = (color >> 0) & 0xff; 
                histogram[bule]++;
            }
        }
        restoreToOriginal();
        return histogram;
    } // getBulescaleHistogram

    public float getContrast() {
        if (img == null) return 0;
        float contrast = 0;
 
        int[] histogram = getGrayScaleHistogram();
        float avgIntensity = 0;
        float pixelNum = width * height;
        for (int i = 0; i < histogram.length; i++) {
            avgIntensity += histogram[i] * i;
        }
        avgIntensity /= pixelNum;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int color = img.getRGB(x, y);
                int value = color & 0xff;
 
                contrast += Math.pow((value) - avgIntensity, 2);
            }
        }
        contrast = (float)Math.sqrt(contrast / pixelNum); // SD
        return contrast;
    } // getContrast

    public void adjustContrast (int contrast) { // contrast stretching 
    
        if (img == null) return;
        float currentContrast = getContrast();
        int[] histogram = getGrayScaleHistogram();
        float avgIntensity = 0;
        float pixelNum = width * height;
    
        for (int i = 0; i < histogram.length; i++) {
            avgIntensity += histogram[i] * i;
        }
        
        avgIntensity /= pixelNum; // avg

        float min = avgIntensity - currentContrast;
        float max = avgIntensity + currentContrast;
        float newMin = avgIntensity - currentContrast - contrast / 2;
        float newMax = avgIntensity + currentContrast + contrast / 2;

        newMin = newMin < 0? 0: newMin;
        newMax = newMax < 0? 0: newMax;
        newMin = newMin > 255? 255: newMin;
        newMax = newMax > 255? 255: newMax;
        
        if (newMin > newMax) {
             float temp = newMax;
             newMax = newMin;
             newMin = temp;
        }

        float contrastFactor = (newMax - newMin) / (max - min);
       
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++){
                int color = img.getRGB(x, y);
                int r = (color >> 16) & 0xff;
                int g = (color >> 8) & 0xff;
                int b = color & 0xff;

                r = (int)((r - min) * contrastFactor + newMin);
                r = r > 255? 255: r;
                r = r < 0? 0: r;
                g = (int)((g - min) * contrastFactor + newMin);
                g = g > 255? 255: g;
                g = g < 0? 0: g;
                b = (int)((b - min) * contrastFactor + newMin);
                b = b > 255? 255: b;
                b = b < 0? 0: b;
                color = (r << 16) | (g << 8) | b;
                img.setRGB(x, y, color);
            }
        }
    } // adjustContrast // contrast streching
    
    public void powerLow (float gramma) {

        if (img == null) return;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int color = img.getRGB(x, y);
                int r = (color >> 16) & 0xff;
                int g = (color >> 8) & 0xff;
                int b = color & 0xff;

                r = (int) (Math.pow (r/255f,gramma) *255f) ; // c = 255
                r = r > 255? 255: r;
                r = r < 0? 0: r;  

                g = (int) (Math.pow (g/255f,gramma) *255f) ;
                g = g > 255? 255: g;
                g = g < 0? 0: g;

                b = (int) (Math.pow (b/255f,gramma) *255f) ;
                b = b > 255? 255: b;
                b = b < 0? 0: b;

           
                
                color = (r << 16) | (g << 8) | b;
                img.setRGB(x, y, color);
            }
        }


    } // powerLow


 public void averagingFilter(int size) {

    if (img == null) return;

    if (size % 2 == 0) { // filter size
        System.out.println ("Size Invalid: must be odd number!"); // ถ้า even จะหา pixel กึ่งกลางไม่ได้
        return;
    }

    BufferedImage tempBuf = new BufferedImage(width, height, img.getType());

    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {

            int sumRed = 0, sumGreen = 0, sumBlue = 0;

            for (int i = y - size/2; i <= y + size/2; i++){  // filter ของแต่ละจุด (x,y)
                for (int j = x - size/2; j <= x + size/2; j++){

                    if (i >= 0 && i < height && j >= 0 && j < width){ // ไม่เอาค่าเลยขอบมารวม (zero pading)

                    int color = img.getRGB(j, i);
                    int r = (color >> 16) & 0xff;
                    int g = (color >> 8) & 0xff;
                    int b = color & 0xff;
                    sumRed += r;
                    sumGreen += g;
                    sumBlue += b;

                    }
                }
             } 

            sumRed /= (size*size); // avg 
            sumRed = sumRed > 255? 255: sumRed;
            sumRed = sumRed < 0? 0: sumRed;

            sumGreen /= (size*size);
            sumGreen = sumGreen > 255? 255: sumGreen;     
            sumGreen = sumGreen < 0? 0: sumGreen;

            sumBlue /= (size*size);
            sumBlue = sumBlue > 255? 255: sumBlue;
            sumBlue = sumBlue < 0? 0: sumBlue;

            int newColor = (sumRed << 16) | (sumGreen << 8) | sumBlue;
            tempBuf.setRGB(x, y, newColor); // เก็บเอาไว้ก่อนค่อยเปลี่ยนพร้อมกันทีเดียว ถ้าเปลี่ยนเลยจะทำให้ค่าที่เอาไปคำนวณเปลี่ยนไป
        }
    }

    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            img.setRGB(x, y, tempBuf.getRGB(x, y)); // set intensity
        }
    }

 } // avg fillter


 
 public void medianNonLinerFilter(int size) {

    if (img == null) return;

    if (size % 2 == 0) { // filter size
        System.out.println ("Size Invalid: must be odd number!"); // ถ้า even จะหา pixel กึ่งกลางไม่ได้
        return;
    }

    BufferedImage tempBuf = new BufferedImage(width, height, img.getType());
   
    int indexArray = 0 ;
    

    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            
            int red [] = new int[size*size] ; // int--> initial = 0 
            int green [] = new int[size*size] ;
            int blue [] = new int[size*size] ;

            int medRed = 0;
            int medGreen = 0;
            int medBlue = 0;

            indexArray = 0 ;

            for (int i = y - size/2; i <= y + size/2; i++){  // filter ของแต่ละจุด (x,y)
                
                for (int j = x - size/2; j <= x + size/2; j++){

                    if (i >= 0 && i < height && j >= 0 && j < width){ // ไม่เอาค่าเลยขอบมารวม (zero pading)

                    int color = img.getRGB(j, i);
                    int r = (color >> 16) & 0xff;
                    int g = (color >> 8) & 0xff;
                    int b = color & 0xff;

                    red[indexArray] = r ;
                    green[indexArray] = g ;
                    blue[indexArray] = b ;

                    }
                    indexArray++ ; // ค่าจะไม่เกิน i*j (ขนาดfilter)
                }
             } 

             Arrays.sort(red) ;
             Arrays.sort(green) ;
             Arrays.sort(blue) ;


             
            int median = indexArray / 2 ; // odd * odd = odd (หา med จากจำนวณเลขคี่อย่างเดี่ยวได้เลย ขนาด filter เป็น จำนวณคี่อยู่แล้ว)
            

            medRed = red[median] ;   
            medRed = medRed > 255? 255: medRed;
            medRed = medRed < 0? 0: medRed;

            medGreen = green[median] ;
            medGreen  = medGreen  > 255? 255: medGreen ;     
            medGreen  = medGreen  < 0? 0: medGreen ;

            medBlue = blue[median] ;
            medBlue   = medBlue  > 255? 255: medBlue  ;     
            medBlue   = medBlue  < 0? 0: medBlue  ;

            int newColor = (medRed << 16) | (medGreen << 8) | medBlue;
            tempBuf.setRGB(x, y, newColor); // เก็บเอาไว้ก่อนค่อยเปลี่ยนพร้อมกันทีเดียว ถ้าเปลี่ยนเลยจะทำให้ค่าที่เอาไปคำนวณเปลี่ยนไป
        }
    }

    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            img.setRGB(x, y, tempBuf.getRGB(x, y)); // set intensity
        }
    }

 } // med fillter
 


 public void unSharpMaskingFilter(int size) {

    if (img == null) return;

    if (size % 2 == 0) { // filter size
        System.out.println ("Size Invalid: must be odd number!"); // ถ้า even จะหา pixel กึ่งกลางไม่ได้
        return;
    }

    averagingFilter(size); // img ->avg filter

    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            
            int color = original.getRGB(x,y ); // ori
            int r = (color >> 16) & 0xff ;
            int g = (color >> 8) & 0xff ;
            int b = color & 0xff ;

            int color2 = img.getRGB(x,y ); // Avg
            int r2 = (color2 >> 16) & 0xff ;
            int g2 = (color2 >> 8) & 0xff ;
            int b2 = color2 & 0xff ;

            // g(x,y) = ori + k(ori - avg) ; k=1  // mark & sharp ไปเลยทีเดียว ค่าจะออกมาตรง ถ้าแยก จะทำให้ตอน mask ที่มีค่าติดลบได้(ดูจากกราฟ) ไปเก็บใน buffer.set RGB ใหม่ อาจจะโดนปัด หรือ เพียน
            //        = 2ori - avg

            int sharp_red = 2*r-r2 ;
            sharp_red = sharp_red > 255? 255: sharp_red;
            sharp_red = sharp_red < 0? 0: sharp_red;

            int sharp_green = 2*g-g2 ;
            sharp_green = sharp_green > 255? 255: sharp_green;
            sharp_green = sharp_green < 0? 0: sharp_green;

            int sharp_blue = 2*b-b2 ;
            sharp_blue = sharp_blue > 255? 255: sharp_blue;
            sharp_blue = sharp_blue < 0? 0: sharp_blue;

            int newcolor = (sharp_red << 16) | sharp_green << 8 | sharp_blue; 

            // int newcolor = (Math.abs(r-r2) << 16) | Math.abs(g-g2<< 8) | Math.abs(b-b2);  
            
            img.setRGB(x, y, newcolor); 
        }
                
     } 

 } // unsharp mark
 
 




public void spatial_Filter (double [][] filter)  { // ที่มีค่าคงที่อยู่แล้ว
  // for any filter แล้วก็ทำ array operation ที่ตำแหน่งเดี่ยวกัน
    // เอา filter มาวางทาบแล้วเลื่อนไปเรื่อยๆ 
     
 } // sF


public void addSaltNoise(double percent) { // white brigth pixel
    
    if (img == null) return;
double noOfPX = height * width;
int noiseAdded = (int)(percent * noOfPX);
Random rnd = new Random();
int whiteColor = 255 << 16 | 255 << 8 | 255;
for (int i = 1; i <= noiseAdded; i++)
{
int x = rnd.nextInt(width);
int y = rnd.nextInt(height);
img.setRGB(x, y, whiteColor);
}

    
} // addSaltNoise

public void addPapperNoise(double percent) { // black  pixel
    
    if (img == null) return;
        double noOfPX = height * width;
        int noiseAdded = (int)(percent * noOfPX);
        Random rnd = new Random();
        int blackColor = 0; // (0,0,0)

        for (int i = 1; i <= noiseAdded; i++) {
            int x = rnd.nextInt(width);
            int y = rnd.nextInt(height);
            img.setRGB(x, y, blackColor);
        }
    
} // addPapperNoise

public void addSaltAndPapperNoise(double papperNoisePercent,double slatNoisePercent) {
    
    if (img == null) return;

        double noOfPX = height * width;

        int slatNoiseAdded = (int)(slatNoisePercent * noOfPX); // white brigth
        int papperNoiseAdded =  (int)(papperNoisePercent * noOfPX); // black
        // total noise = black noise + papper noise
        
        Random rnd = new Random();

        int whiteColor = 255 << 16 | 255 << 8 | 255; // (255,255,255)
        int blackColor = 0 ; // (0,0,0)

        for (int i = 1; i <= slatNoiseAdded; i++) {
            int x = rnd.nextInt(width);
            int y = rnd.nextInt(height);
            img.setRGB(x, y, whiteColor);
        }

        for (int i = 1; i <= papperNoiseAdded; i++) {
            int x = rnd.nextInt(width);
            int y = rnd.nextInt(height);
            img.setRGB(x, y, blackColor);
        }
    
} // addSaltAndPapperNoise

public void addUniformNoise (double percent, int distribution)   { // distribution กระจายไปจากกึ่งกลาง +- distribution

    if (img == null) return;
    double noOfPX = height * width;
    int noiseAdded = (int)(percent * noOfPX);
    Random rnd = new Random();

    for (int i = 1; i <= noiseAdded; i++) {
         int x = rnd.nextInt(width);
         int y = rnd.nextInt(height);
         int color = img.getRGB(x, y);

         int r = (color >> 16) & 0xff ;
         int g = (color >> 8) & 0xff ;
         int b = color & 0xff ;
         int gray = (int)(0.2126*r + 0.7152*g + 0.0722*b);
         
         gray += (rnd.nextInt(distribution * 2) - distribution); // (10*2)-10 = -10,10 : (-distribution,distribution)
         gray = gray > 255? 255: gray;
         gray = gray < 0? 0: gray;
         int newColor = gray << 16 | gray << 8 | gray;
         img.setRGB(x, y, newColor);
      }
    
    

} //  addUniformNoise

public void contraharmonicFilter (int size ,double Q) {

    if(img == null) return ;
    if(size % 2 == 0) { 
        System.out.println("Size Invalid: must be odd number!"); 
        return ;
    }
   
    BufferedImage tempBuf = new BufferedImage (width, height, img.getType ()) ;
    for(int y =0; y < height ; y++){
        for(int x = 0; x < width ; x++) {

            double sumRedAbove = 0, sumGreenAbove = 0 , sumBlueAbove = 0 ;
            double sumRedBelow = 0, sumGreenBelow = 0 , sumBlueBelow = 0 ;
            
        
            for (int i = y - size/2; i <= y + size/2; i++)   {
                for (int j = x - size/2; j <= x + size/2; j++) {
                    if (i >= 0 && i < height && j >= 0 && j < width) {
            
                        int color = img.getRGB(j, i);
                        int r = (color >> 16) & 0xff;
                        int g = (color >> 8) & 0xff;
                        int b = color & 0xff;
            
                        sumRedAbove += Math.pow(r, Q + 1);
                        sumGreenAbove += Math.pow(g, Q + 1);
                        sumBlueAbove += Math.pow(b, Q + 1);
                        sumRedBelow += Math.pow(r, Q);
                        sumGreenBelow += Math.pow(g, Q);
                        sumBlueBelow += Math.pow(b, Q);
                    }
                }
            }
            
    
            sumRedAbove /= sumRedBelow;
            sumRedAbove = sumRedAbove > 255? 255: sumRedAbove;
            sumRedAbove = sumRedAbove < 0? 0: sumRedAbove;

            sumGreenAbove /= sumGreenBelow;
            sumGreenAbove = sumGreenAbove > 255? 255: sumGreenAbove;
            sumGreenAbove = sumGreenAbove < 0? 0: sumGreenAbove;
            
            sumBlueAbove /= sumBlueBelow;
            sumBlueAbove = sumBlueAbove > 255? 255: sumBlueAbove;
            sumBlueAbove = sumBlueAbove < 0? 0: sumBlueAbove;

 

            int newColor = ((int)sumRedAbove << 16) | ((int)sumGreenAbove << 8) | (int)sumBlueAbove;
            tempBuf.setRGB(x, y, newColor);
        }
    } //
    
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            img.setRGB(x, y, tempBuf.getRGB(x, y)); 
        }
    } //

} //  contraharmonicFilter


public FrequencyDomainManager getFrequencyDomainManager () {

    convertToGray();
    FrequencyDomainManager fft = new FrequencyDomainManager(img,true) ; // ture = slow
    restoreToOriginal();
    return fft ;

} // getFreq Domain

public void setBufferImage ( BufferedImage img ) {

    for (int y = 0 ; y < height ; y++ ) {
        for ( int x = 0 ; x < width ; x++) {
            this.img.setRGB(x,y,img.getRGB(x,y) ) ;
        }
    } 
    
    original = new BufferedImage (width,height,img.getType()) ;
    for (int y = 0 ; y < height ; y++ ) {
        for ( int x = 0 ; x < width ; x++) {
            original.setRGB(x,y,img.getRGB(x,y) ) ;
        }
    } 






} // setBufferImage

public void alphaTrimFilter ( int size ,int d ) {


    if (img == null) return;
    if (size % 2 == 0) {
        System.out.println("Size Invalid: must be odd number!");
        return;
    }

    BufferedImage tempBuf = new BufferedImage(width, height, img.getType());
    
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {

            int[] kernelRed = new int[size * size];
            int[] kernelGreen = new int[size * size];
            int[] kernelBlue = new int[size * size];
                
                for (int i = y - size/2; i <= y + size/2; i++) {
                    for (int j = x - size/2; j <= x + size/2; j++) {
                        int r, g, b, k;
                        if (i >= 0 && i < height && j >= 0 && j < width) {
                            int color = img.getRGB(j, i);
                            r = (color >> 16) & 0xff;
                            g = (color >> 8) & 0xff;
                            b = color & 0xff;
                            kernelRed[(i - (y - size/2)) * size + (j - (x - size/2))] = r;
                            kernelGreen[(i - (y - size/2)) * size + (j - (x - size/2))] = g;
                            kernelBlue[(i - (y - size/2)) * size + (j - (x - size/2))] = b; 
                        }
                    }
                }
                
                for (int i = 0; i < size * size - 1; i++) {
                    for (int j = 0; j < size * size - i - 1; j++) {
                        
                        int temp;

                        if(kernelRed[j] > kernelRed[j+1]) {
                            temp = kernelRed[j];
                            kernelRed[j] = kernelRed[j+1];
                            kernelRed[j+1] = temp;
                        }
                        
                        if(kernelGreen[j] > kernelGreen[j+1]) {
                            temp = kernelGreen[j];
                            kernelGreen[j] = kernelGreen[j+1];
                            kernelGreen[j+1] = temp;
                        }
                        
                        if(kernelBlue[j] > kernelBlue[j+1]) {
                             temp = kernelBlue[j];
                             kernelBlue[j] = kernelBlue[j+1];
                             kernelBlue[j+1] = temp;
                        }
                    }
                }
                
                int remainingPixel = size * size - d;
                int red = 0, green = 0, blue = 0;
                
                for (int i = 0; i < remainingPixel; i++) {
                    red += kernelRed[(d / 2) + i];
                    green += kernelGreen[(d / 2) + i];
                    blue += kernelBlue[(d / 2) + i];
                }
                        
                red /= remainingPixel;
                red = red > 255? 255: red;
                red = red < 0? 0: red;
                green /= remainingPixel;
                green = green > 255? 255: green;
                green = green < 0? 0: green;
                        
                blue /= remainingPixel;
                blue = blue > 255? 255: blue;
                blue = blue < 0? 0: blue;
                int newColor = (red << 16) | (green << 8) | blue;
                tempBuf.setRGB(x, y, newColor);
            }
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                img.setRGB(x, y, tempBuf.getRGB(x, y));
            }        
        }

} // alphaTrimFilter 



public void maxFilter(int size) {

    if (img == null) return;

    if (size % 2 == 0) { // filter size
        System.out.println ("Size Invalid: must be odd number!"); // ถ้า even จะหา pixel กึ่งกลางไม่ได้
        return;
    }

    BufferedImage tempBuf = new BufferedImage(width, height, img.getType());

    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {

            
            int   maxR =  -1 ;
            int   maxG =  -1 ;
            int   maxB =  -1 ;
           // int count = 0; ;
          //  int temp = -1 ; // make sure it is max
            

            for (int i = y - size/2; i <= y + size/2; i++){  // filter ของแต่ละจุด (x,y)
                for (int j = x - size/2; j <= x + size/2; j++){

                    if (i >= 0 && i < height && j >= 0 && j < width){ // ไม่เอาค่าเลยขอบมารวม (zero pading) //  

                    int color = img.getRGB(j, i);
                    int r = (color >> 16) & 0xff;
                    int g = (color >> 8) & 0xff;
                    int b = color & 0xff;

                    if (r > maxR ) maxR=r;
                    if (g > maxG ) maxG=g;
                    if (b > maxB ) maxB=b;
                    }
                }
             } 

           
             maxR  = maxR  > 255? 255: maxR ;
             maxR  = maxR  < 0? 0: maxR ;

             maxG  = maxG    > 255? 255: maxG  ;
             maxG   = maxG    < 0? 0: maxG  ;

             maxB  = maxB  > 255? 255: maxB  ;
             maxB  = maxB  < 0? 0: maxB ;


            int newColor = ( maxR  << 16) | (maxG << 8) |  maxB ;
            tempBuf.setRGB(x, y, newColor); // เก็บเอาไว้ก่อนค่อยเปลี่ยนพร้อมกันทีเดียว ถ้าเปลี่ยนเลยจะทำให้ค่าที่เอาไปคำนวณเปลี่ยนไป
        }
    }

    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            img.setRGB(x, y, tempBuf.getRGB(x, y)); // set intensity
        }
    }

 } // avg fillter




public void resizeNearestNeighbour(double scaleX, double scaleY) {

   if (img == null) return;

    int newWidth = (int) Math.round(width * scaleX);
    int newHeight = (int) Math.round(height * scaleY);
    BufferedImage tempBuf = new BufferedImage(newWidth, newHeight, img.getType());
        
    for (int y = 0; y < newHeight; y++) {
         for (int x = 0; x < newWidth; x++) {

                int xNearest = (int) Math.round(x / scaleX);
                int yNearest = (int) Math.round(y / scaleY);
                xNearest = xNearest >= width? width - 1: xNearest;
                xNearest = xNearest < 0? 0: xNearest;
                yNearest = yNearest >= height? height - 1: yNearest;
                yNearest = yNearest < 0? 0: yNearest;
                tempBuf.setRGB(x, y, img.getRGB(xNearest, yNearest)); 
         }
     }

    img = new BufferedImage(newWidth, newHeight, img.getType());
    width = newWidth;
    height = newHeight;

     for (int y = 0; y < newHeight; y++) {
        for (int x = 0; x < newWidth; x++) {
                img.setRGB(x, y, tempBuf.getRGB(x, y));
         }
    }

 } // resizeNearestNeighbour



public void resizeBilinear(double scaleX, double scaleY) {
    if (img == null) return;

    int newWidth = (int) Math.round(width * scaleX);
    int newHeight = (int) Math.round(height * scaleY);
    BufferedImage tempBuf = new BufferedImage(newWidth, newHeight, img.getType());
        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++){
                double oldX = x / scaleX;
                double oldY = y / scaleY;
//get 4 coordinates
                int x1 = Math.min((int) Math.floor(oldX), width - 1);
                int y1 = Math.min((int) Math.floor(oldY), height - 1);
                int x2 = Math.min((int) Math.ceil(oldX), width - 1);
                int y2 = Math.min((int) Math.ceil(oldY), height - 1);
//get colours
                int color11 = img.getRGB(x1, y1);
                int r11 = (color11 >> 16) & 0xff;
                int g11 = (color11 >> 8) & 0xff;
                int b11 = color11 & 0xff;

                int color12 = img.getRGB(x1, y2);
                int r12 = (color12 >> 16) & 0xff;
                int g12 = (color12 >> 8) & 0xff;
                int b12 = color12 & 0xff;

                int color21 = img.getRGB(x2, y1);
                int r21 = (color21 >> 16) & 0xff;
                int g21 = (color21 >> 8) & 0xff;
                int b21 = color21 & 0xff;

                int color22 = img.getRGB(x2, y2);
                int r22 = (color22 >> 16) & 0xff;
                int g22 = (color22 >> 8) & 0xff;
                int b22 = color22 & 0xff;
//interpolate x
                double P1r = (x2 - oldX) * r11 + (oldX - x1) * r21;
                double P1g = (x2 - oldX) * g11 + (oldX - x1) * g21;
                double P1b = (x2 - oldX) * b11 + (oldX - x1) * b21;
                double P2r = (x2 - oldX) * r12 + (oldX - x1) * r22;
                double P2g = (x2 - oldX) * g12 + (oldX - x1) * g22;
                double P2b = (x2 - oldX) * b12 + (oldX - x1) * b22;
   
                if (x1 == x2) {
                    P1r = r11;
                    P1g = g11;
                    P1b = b11;
                    P2r = r22;
                    P2g = g22;
                    P2b = b22;
                }
//interpolate y
                double Pr = (y2 - oldY) * P1r + (oldY - y1) * P2r;
                double Pg = (y2 - oldY) * P1g + (oldY - y1) * P2g;
                double Pb = (y2 - oldY) * P1b + (oldY - y1) * P2b;

                if (y1 == y2)  {
                    Pr = P1r;
                    Pg = P1g;
                    Pb = P1b;
                }

                int r = (int) Math.round(Pr);
                int g = (int) Math.round(Pg);
                int b = (int) Math.round(Pb);

                r = r > 255? 255: r;
                r = r < 0? 0: r;
                g = g > 255? 255: g;
                g = g < 0? 0: g;
                b = b > 255? 255: b;
                b = b < 0? 0: b;

                int newColor = (r << 16) | (g << 8) | b;
                tempBuf.setRGB(x, y, newColor);
             }
        }
    img = new BufferedImage(newWidth, newHeight, img.getType());
    width = newWidth;
    height = newHeight;
    
    for (int y = 0; y < newHeight; y++) {
        for (int x = 0; x < newWidth; x++) {
            img.setRGB(x, y, tempBuf.getRGB(x, y)); 
        }
    }
}

public void erosion(StructuringElement se) {

    if (img == null) return;
    convertToGray();
    BufferedImage tempBuf = new BufferedImage(width, height, img.getType());
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            boolean isEroded = true;
            int min = Integer.MAX_VALUE;
    
            se_check: 
            for (int i = y - se.origin.y; i < y + se.height - se.origin.y; i++) {
            
                for (int j = x - se.origin.x; j < x + se.width - se.origin.x; j++) {
                    int seCurrentX = j - (x - se.origin.x); 
                    int seCurrentY = i - (y - se.origin.y);

                if (i >= 0 && i < height && j >= 0 && j < width) {
                    if (!se.ignoreElements.contains(new Point(seCurrentX, seCurrentY))) {
                    int color = img.getRGB(j, i);
                    int gray = color & 0xff; 
                        if (se.elements[seCurrentX][seCurrentY] != gray) {
                        isEroded = false;
                        break se_check;
                        } 
                        else if (min > gray) min = gray;
                        
                    }
                }
                else {
                    isEroded = false;
                    break se_check;
                }
            }
        } // 

        int newGray = 0;
        if (isEroded){
            newGray = min;
        }
   
        int newColor = (newGray << 16) | (newGray << 8) | newGray;
        tempBuf.setRGB(x, y, newColor);
        }
    }
    
    for (int y = 0; y < height; y++){
        for (int x = 0; x < width; x++){
            img.setRGB(x, y, tempBuf.getRGB(x, y));
        }
    }
} // erosion
    

public void dilation (StructuringElement se) {
    
    if (img == null) return;
    
    convertToGray();

    BufferedImage tempBuf = new BufferedImage(width, height, img.getType());
    
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            boolean isDilated = false;

            se_check: 
            for (int i = y - (se.height - se.origin.y - 1); i < y + se.height - (se.height - se.origin.y - 1); i++) {
                for (int j = x - (se.width - se.origin.x - 1); j < x + se.width - (se.width - se.origin.x - 1); j++) {
                    int seCurrentX = se.width - (j - x + se.origin.x) - 1;
                    int seCurrentY = se.height - (i - y + se.origin.y) - 1;
                    if (i >= 0 && i < height && j >= 0 && j < width) {
                        if (!se.ignoreElements.contains(new Point(seCurrentX, seCurrentY))) {
                        int color = img.getRGB(j, i);
                        int gray = color & 0xff;
                            if (se.elements[seCurrentX][seCurrentY] == gray) {
                                 isDilated = true;
                                break se_check;
                            }
                        }
                    } 

                    else {
                    isDilated = false;
                    break se_check;
                    }
                }
            } // 

            if (isDilated) {
                int max = Integer.MIN_VALUE;
                for (int i = y - (se.height - se.origin.y - 1); i < y + se.height - (se.height - se.origin.y - 1); i++) {
                    for (int j = x - (se.width - se.origin.x - 1); j < x + se.width -(se.width - se.origin.x - 1); j++) { 
                        if (i >= 0 && i < height && j >= 0 && j < width) {
                            int color = img.getRGB(j, i);
                            int gray = color & 0xff;
                            if (max < gray) max = gray; 
                        }
                    }
                }
            
            int newGray = max;
            int newColor = (newGray << 16) | (newGray << 8) | newGray;
            tempBuf.setRGB(x, y, newColor);
            }
        }
    }
    
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            img.setRGB(x, y, tempBuf.getRGB(x, y));
        }
    }
} // dilation

public void closing (StructuringElement se) {
    if (img == null) return ;
    dilation(se);
    erosion(se);

} // basic closing

public void opening (StructuringElement se) {
    if (img == null) return ;
    erosion(se);
    dilation(se);

    
} // basic opening

public void boundaryExtraction (StructuringElement se) { // A -(A erode B)
    
    if (img == null) return ;
    erosion(se); // img
   // dilation(se) ;

    BufferedImage  temp = new BufferedImage (width,height,img.getType()) ;

    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            temp.setRGB(x, y,img.getRGB(x, y));
        }
    } // temp erosion img

    restoreToOriginal(); // A

    minus(temp); 

} // boundaryExtraction

public void minus (BufferedImage img2) {
   
    if (img == null || img2 == null ) return ;

    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {

            int color1 = img.getRGB(x,y); // img1
            int r1 = (color1 >> 16) & 0xff ;
            int g1 = (color1 >> 8) & 0xff ;
            int b1 = color1 & 0xff ;

            int color2 = img2.getRGB(x,y ); // img2
            int r2 = (color2 >> 16) & 0xff ;
            int g2 = (color2 >> 8) & 0xff ;
            int b2 = color2 & 0xff ;

            int dr = r1-r2 ;
            int dg = g1-g2 ;
            int db = b1-b2 ;

            dr = dr > 255? 255: dr;
            dr = dr < 0? 0: dr;

            dg = dg > 255? 255: dg;
            dg = dg < 0? 0: dg;

            db = db > 255? 255: db;
            db = db < 0? 0: db;

            int newColor = (dr << 16) | (dg << 8) |(db << 0) ;
            img.setRGB(x, y, newColor);



        }
    }

} // minus

public void thresholding(int threshold) {
 if (img == null) return;
 convertToGray();
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            int color = img.getRGB(x, y);
            int gray = color & 0xff;
            gray = gray < threshold? 0: 255;
            color = (gray << 16) | (gray << 8) | gray;
            img.setRGB(x, y, color);
        }
    } 
 }// thresholding

 public void linearSpatialFilter(double[] kernel, int size) {
    if (img == null) return;

    if (size % 2 == 0) {
        System.out.println("Size Invalid: must be odd number!");
        return;
    } // 
    
    BufferedImage tempBuf = new BufferedImage(width, height, img.getType());
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            double sumRed = 0, sumGreen = 0, sumBlue = 0;
            for (int i = y - size/2; i <= y + size/2; i++) {
                for (int j = x - size/2; j <= x + size/2; j++){
                    if (i >= 0 && i < height && j >= 0 && j < width){
                        int color = img.getRGB(j, i);
                        int r = (color >> 16) & 0xff;
                        int g = (color >> 8) & 0xff;
                        int b = color & 0xff;
                        sumRed += r * kernel[(i - (y - size/2)) * size + (j - (x - size/2))];
                        sumGreen += g * kernel[(i - (y - size/2)) * size + (j - (x - size/2))];
                        sumBlue += b * kernel[(i - (y - size/2)) * size + (j - (x - size/2))];
                    }
                }
            }
            sumRed = sumRed > 255? 255: sumRed;
            sumRed = sumRed < 0? 0: sumRed;
            sumGreen = sumGreen > 255? 255: sumGreen;
            sumGreen = sumGreen < 0? 0: sumGreen;
            sumBlue = sumBlue > 255? 255: sumBlue;
            sumBlue = sumBlue < 0? 0: sumBlue;
            int newColor = ((int)sumRed << 16) | ((int)sumGreen << 8) | (int)sumBlue;
            tempBuf.setRGB(x, y, newColor);
        }
    }
    for (int y = 0; y < height; y++){
        for (int x = 0; x < width; x++){
            img.setRGB(x, y, tempBuf.getRGB(x, y));
        }   
    }
} // linearSpatialFilter

public void otsuThreshold() {
    if (img == null) return;
    convertToGray();
    int[] histogram = new int[256];
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
        int color = img.getRGB(x, y);
        int gray = color & 0xff;
        histogram[gray]++;
        }
    }
    float[] histogramNorm = new float[histogram.length];
    float pixelNum = width * height;
    for (int i = 0; i < histogramNorm.length; i++) {
        histogramNorm[i] = histogram[i] / pixelNum;
    }
    float[] histogramCS = new float [256];
    float[] histogramMean = new float[256];
    for (int i = 0; i < 256; i++) {
        if (i == 0) {
            histogramCS[i] = histogramNorm[i];
            histogramMean[i] = 0;
        }
        else {
        histogramCS[i] = histogramCS[i - 1] + histogramNorm[i];
        histogramMean[i] = histogramMean[i - 1] + histogramNorm[i] * i;
        }
    }
    float globalMean = histogramMean[255];
    float max = Float.MIN_VALUE;
    float maxVariance = Float.MIN_VALUE;
    int countMax = 0;
    for (int i = 0; i < 256; i++){
        float variance = (float)Math.pow(globalMean * histogramCS[i] - histogramMean[i], 2) / (histogramCS[i] * (1 - histogramCS[i]));
        if (variance > maxVariance){
            maxVariance = variance;
            max = i;
            countMax = 1;
        }
        else if (variance == maxVariance){
            countMax++;
            max = ((max * (countMax - 1)) + i) / countMax;
        }
    }
    System.out.print(max);
    thresholding((int)Math.round(max));
} // otsuThreshold

public void cannyEdgeDetector (int lower, int upper) {
 
    //Step 1 - Apply 5 x 5 Gaussian filter
 double[] gaussian = {  2.0 / 159.0, 4.0 / 159.0, 5.0 / 159.0, 4.0 / 159.0, 2.0 / 159.0,
                        4.0 / 159.0, 9.0 / 159.0, 12.0 / 159.0, 9.0 / 159.0, 4.0 / 159.0,
                        5.0 / 159.0, 12.0 / 159.0, 15.0 / 159.0, 12.0 / 159.0, 5.0 / 159.0,
                        4.0 / 159.0, 9.0 / 159.0, 12.0 / 159.0, 9.0 / 159.0, 4.0 / 159.0,
                        2.0 / 159.0, 4.0 / 159.0, 5.0 / 159.0, 4.0 / 159.0, 2.0 / 159.0 } ;
 linearSpatialFilter(gaussian, 5);

 //double[] lapacian1 = {1,1,1,1,-8,1,1,1,1} ;
 //double[] lapacian2 = {-1,-1,-1,-1,+8,-1,-1,-1,-1} ;
 //double[] sobelHorizontal = {-1,-2,-1,0,0,0,1,2,1} ;
 //double[] sobelVertical = {-1,0,1,-2,0,2,-1,0,1} ;
    

 convertToGray();

    //===========================
 

 //Step 2 - Find intensity gradient
 double[] sobelX = { 1, 0, -1, 2, 0, -2, 1, 0, -1};
 double[] sobelY = { 1, 2, 1, 0, 0, 0, -1, -2, -1};
 
 double[][] magnitude = new double[height][width];
 double[][] direction = new double[height][width];
 
 for (int y = 3; y < height - 3; y++) {
    for (int x = 3; x < width - 3; x++) {
        double gx = 0, gy = 0;
        for (int i = y - 1; i <= y + 1; i++){
            for (int j = x - 1; j <= x + 1; j++) {
                 if (i >= 0 && i < height && j >= 0 && j < width) {
                    int color = img.getRGB(j, i);
                    int gray = color & 0xff;
                    gx += gray * sobelX[(i - (y - 1)) * 3 + (j - (x - 1))];
                    gy += gray * sobelY[(i - (y - 1)) * 3 + (j - (x - 1))];
                }
            }
        }
        magnitude[y][x] = Math.sqrt(gx * gx + gy * gy);
        direction[y][x] = Math.atan2(gy, gx) * 180 / Math.PI;
    }
}
//===========================



//Step 3 - Nonmaxima Suppression

 double[][] gn = new double[height][width];

    for (int y = 3; y < height - 3; y++) {
        for (int x = 3; x < width - 3; x++) {
        int targetX = 0, targetY = 0;
        //find closest direction
            if (direction[y][x] <= -157.5) {

                targetX = 1; targetY = 0; 
            }
            else if (direction[y][x] <= -112.5) { 

                    targetX = 1; targetY = -1; 
            }
            else if (direction[y][x] <= -67.5) { 

                    targetX = 0; targetY = 1; 
            }
            else if (direction[y][x] <= -22.5) { 

                    targetX = 1; targetY = 1; 
            }
            else if (direction[y][x] <= 22.5){ 

                    targetX = 1; targetY = 0; 
            }

            else if (direction[y][x] <= 67.5) {  

                    targetX = 1; targetY = -1; 
            }
            else if (direction[y][x] <= 112.5) { 

                    targetX = 0; targetY = 1;
            }
            else if (direction[y][x] <= 157.5) { 

                    targetX = 1; targetY = 1; 
            }
            else {  targetX = 1; targetY = 0; 
            }

            if (y + targetY >= 0 && y + targetY < height && x + targetX >= 0 && x + targetX < width && magnitude[y][x] < magnitude[y + targetY][x + targetX]){
                gn[y][x] = 0;
            }
            else if (y - targetY >= 0 && y - targetY < height && x - targetX >= 0 && x - targetX < width && magnitude[y][x] < magnitude[y - targetY][x - targetX]) {
                gn[y][x] = 0;
            }
            else { gn[y][x] = magnitude[y][x];
            }   

        }
    } 
//===========================

//Step 4 - Hysteresis Thresholding
 
//set back first
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            int newGray = (int) gn[y][x]; 
            newGray = newGray > 255? 255: newGray;
            newGray = newGray < 0? 0: newGray;
            int newColor = (newGray << 16) | (newGray << 8) | newGray;
            img.setRGB(x, y, newColor);
        }
    }
 // upper threshold checking with recursive
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            int checking = img.getRGB(x, y) & 0xff;
            if (checking >= upper) {
                checking = 255;
            int newColor = (checking << 16) | (checking << 8) | checking;
            img.setRGB(x, y, newColor);

            hystConnect(x, y, lower);
            }
        }
    }
 
 //clear unwanted values
    for (int y = 0; y < height; y++){
        for (int x = 0; x < width; x++){
            int checking = img.getRGB(x, y) & 0xff;
            if (checking != 255) {
                int newColor = (0 << 16) | (0 << 8) | 0;
                img.setRGB(x, y, newColor);
            }
        }
    }
}

 private void hystConnect(int x, int y, int threshold) {
    int value = 0; 
    for (int i = y - 1; i <= y + 1; i++) {
        for (int j = x - 1; j <= x + 1; j++) {
            if ((j < width) && (i < height) && (j >= 0) && (i >= 0) && (j != x) && (i != y)) {
                value = img.getRGB(j, i) & 0xff;
            if (value != 255) {
                if (value >= threshold) {
                int newColor = (255 << 16) | (255 << 8) | 255;
                img.setRGB(j, i, newColor);
                hystConnect(j, i, threshold);
                }
                else {
                    int newColor = (0 << 16) | (0 << 8) | 0;
                    img.setRGB(j, i, newColor);
                }
            }
        }
    }
} // hystConnect


} // cannyEdgeDetector

public void houghTransform (double percent) {
 //The image should be converted to edge map first
 
 //Work out how the hough space is quantized
    int numOfTheta = 720;
    double thetaStep = Math.PI / numOfTheta;
    int highestR = (int)(Math.max(width, height) * Math.sqrt(2));
    int centreX = width / 2;
    int centreY = height / 2;
    System.out.println("Hough array w: "+numOfTheta+" height: "+2*highestR);
 
 //Create the hough array and initialize to zero
     int[][] houghArray = new int[numOfTheta][2*highestR];
    for(int i = 0; i < numOfTheta; i++) {
        for(int j = 0; j < 2*highestR; j++) {
        houghArray[i][j] = 0;
        }
    }

   //Step 1 - find each edge pixel
    //Find edge points and vote in array
    for(int y = 0; y < height; y++) {
        for(int x = 0; x < width; x++) {
            int pointColor = img.getRGB(x, y) & 0xff;
            if (pointColor != 0) {
    //Edge pixel found
                for (int i = 0; i < numOfTheta; i++) {
    //Step 2 - Apply the line equation and update hough array
    //Work out the r values for each theta step

                    int r = (int)((x - centreX) * Math.cos(i * thetaStep) + (y - centreY) * Math.sin(i * thetaStep));
//Move all values into positive range for display purposes
                     r = r + highestR;
                    if (r < 0 || r >= 2 * highestR) continue;

        //Increment hough array
                         houghArray[i][r]++;
                    }
                }
            }
        }

    


 //Step 3 - Apply threshold to hough array to find line
    int maxHough = 0;
    for(int i = 0; i < numOfTheta; i++) {
        for(int j = 0; j < 2 * highestR; j++) {
 //Find the max hough value for the thresholding operation
           if(houghArray[i][j] > maxHough) {
            maxHough = houghArray[i][j];
          }
        }
    }
 //Set the threshold limit
 int threshold = (int)(percent * maxHough);

//Step 4 - Draw lines
 // Search for local peaks above threshold to draw
     for(int i = 0; i < numOfTheta; i++) {
        for(int j = 0; j < 2 * highestR; j++) {
 // only consider points above threshold
        if(houghArray[i][j] >= threshold) {
 // see if local maxima
        boolean draw = true;
        int peak = houghArray[i][j];
        for(int k = -1; k <= 1; k++) {
            for(int l = -1; l <= 1; l++) {
 //not seeing itself
            if (k == 0 && l == 0) continue;
                int testTheta = i + k;
                int testOffset = j + l;
                if (testOffset < 0 || testOffset >= 2*highestR) continue;
                    if (testTheta < 0) testTheta = testTheta + numOfTheta;
                        if (testTheta >= numOfTheta) testTheta = testTheta - numOfTheta;
                            if (houghArray[testTheta][testOffset] > peak) {
 //found bigger point
                                draw = false;
                                break;
                            }
                        }
                    }
 //point found is not local maxima
                    if (!draw) continue;
 //if local maxima, draw red back
                    double tsin = Math.sin(i*thetaStep);
                    double tcos = Math.cos(i*thetaStep);

                    if (i <= numOfTheta / 4 || i >= (3 * numOfTheta) / 4) {
                        for(int y = 0; y < height; y++){
 //vertical line
                        int x = (int) (((j - highestR) - ((y - centreY) * tsin)) / tcos) + centreX;
                        if(x < width && x >= 0) {
                            int redColor = (255 << 16) | (0 << 8) | 0;
                            img.setRGB(x, y, redColor);
                        }  
                    }
                }
                else {
                    for(int x = 0; x < width; x++) {
 //horizontal line
                        int y = (int) (((j - highestR) - ((x - centreX) * tcos)) / tsin) + centreY;
                        if(y < height && y >= 0){
                            int redColor = (255 << 16) | (0 << 8) | 0;
                            img.setRGB(x, y, redColor);
                        }
                    }
                }
            }
        }
    }
    
 } // houghTransform

 public void ADIAbsolute (String[] sequences, int threshold, int step) {
    if (img == null) return;
    BufferedImage tempBuf = new BufferedImage(width, height, img.getType());
    for (int n = 0; n < sequences.length; n++) {
        BufferedImage otherImage = null;
        try{
            otherImage = ImageIO.read(new File(sequences[n]));
        }
        catch (IOException e){
            System.out.println(e);
            return;
        }
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                int color1 = img.getRGB(x, y);
                int r1 = (color1 >> 16) & 0xff;
                int g1 = (color1 >> 8) & 0xff;
                int b1 = color1 & 0xff;
                int color2 = otherImage.getRGB(x, y);
                int r2 = (color2 >> 16) & 0xff;
                int g2 = (color2 >> 8) & 0xff;
                int b2 = color2 & 0xff;
                int dr = r1 - r2;
                int dg = g1 - g2;
                int db = b1 - b2;

                int dGray = (int) Math.round(0.2126*dr + 0.7152*dg + 0.0722*db);
                if (Math.abs(dGray) > threshold) {
                    int currentColor = tempBuf.getRGB(x, y) & 0xff;
                    currentColor += step;
                    currentColor = currentColor > 255 ? 255: currentColor;
                    currentColor = currentColor < 0? 0: currentColor;
                    int newColor = (currentColor << 16) | (currentColor << 8) | currentColor;
                    tempBuf.setRGB(x, y, newColor);
                }
            }
        }
    }
for (int y = 0; y < height; y++) {
    for (int x = 0; x < width; x++) {
        img.setRGB(x, y, tempBuf.getRGB(x, y));
    }
}


} // ADIAbsolute

public void ADIPositive (String[] sequences, int threshold, int step) {
    if (img == null) return;
    BufferedImage tempBuf = new BufferedImage(width, height, img.getType());
    for (int n = 0; n < sequences.length; n++) {
        BufferedImage otherImage = null;
        try{
            otherImage = ImageIO.read(new File(sequences[n]));
        }
        catch (IOException e){
            System.out.println(e);
            return;
        }
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                int color1 = img.getRGB(x, y);
                int r1 = (color1 >> 16) & 0xff;
                int g1 = (color1 >> 8) & 0xff;
                int b1 = color1 & 0xff;
                int color2 = otherImage.getRGB(x, y);
                int r2 = (color2 >> 16) & 0xff;
                int g2 = (color2 >> 8) & 0xff;
                int b2 = color2 & 0xff;
                int dr = r1 - r2;
                int dg = g1 - g2;
                int db = b1 - b2;

                int dGray = (int) Math.round(0.2126*dr + 0.7152*dg + 0.0722*db);
                if (dGray > threshold) {
                    int currentColor = tempBuf.getRGB(x, y) & 0xff;
                    currentColor += step;
                    currentColor = currentColor > 255 ? 255: currentColor;
                    currentColor = currentColor < 0? 0: currentColor;
                    int newColor = (currentColor << 16) | (currentColor << 8) | currentColor;
                    tempBuf.setRGB(x, y, newColor);
                }
            }
        }
    }
for (int y = 0; y < height; y++) {
    for (int x = 0; x < width; x++) {
        img.setRGB(x, y, tempBuf.getRGB(x, y));
    }
}


} // ADI positive


public void ADINegative (String[] sequences, int threshold, int step) {
    if (img == null) return;
    BufferedImage tempBuf = new BufferedImage(width, height, img.getType());
    for (int n = 0; n < sequences.length; n++) {
        BufferedImage otherImage = null;
        try{
            otherImage = ImageIO.read(new File(sequences[n]));
        }
        catch (IOException e){
            System.out.println(e);
            return;
        }
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                int color1 = img.getRGB(x, y);
                int r1 = (color1 >> 16) & 0xff;
                int g1 = (color1 >> 8) & 0xff;
                int b1 = color1 & 0xff;
                int color2 = otherImage.getRGB(x, y);
                int r2 = (color2 >> 16) & 0xff;
                int g2 = (color2 >> 8) & 0xff;
                int b2 = color2 & 0xff;
                int dr = r1 - r2;
                int dg = g1 - g2;
                int db = b1 - b2;

                int dGray = (int) Math.round(0.2126*dr + 0.7152*dg + 0.0722*db);
                if (dGray < -threshold) {
                    int currentColor = tempBuf.getRGB(x, y) & 0xff;
                    currentColor += step;
                    currentColor = currentColor > 255 ? 255: currentColor;
                    currentColor = currentColor < 0? 0: currentColor;
                    int newColor = (currentColor << 16) | (currentColor << 8) | currentColor;
                    tempBuf.setRGB(x, y, newColor);
                }
            }
        }
    }
for (int y = 0; y < height; y++) {
    for (int x = 0; x < width; x++) {
        img.setRGB(x, y, tempBuf.getRGB(x, y));
    }
}


} // ADI positive

public int getColor(int x, int y) {

    int color = img.getRGB(x, y) ;
    int r = (color >> 16) & 0xff;
    int g = (color >> 8) & 0xff;
    int b = color & 0xff;
    
    color = (r << 16) | (g << 8) | b; 

    return color ; 
} // getColor

public BufferedImage rotateImg(double degrees,int x,int y) {

        BufferedImage rotateImg = new BufferedImage(width,height,img.getType()) ;
        Graphics2D g = rotateImg.createGraphics();
        
        g.rotate(Math.toRadians(degrees),x,y);
        g.drawImage(img, null, 0, 0);
        return rotateImg ;
    }

    public BufferedImage rotateImg(double degrees) {

        BufferedImage rotateImg = new BufferedImage(width,height,img.getType()) ;
        Graphics2D g = rotateImg.createGraphics();
        
        g.rotate(Math.toRadians(degrees));
        g.drawImage(img, null, 0, 0);
        return rotateImg ;
    }


public void CropImg(int x,int y, int w , int h) {
    img.getSubimage(x, y, w, h);

}

public void testDrawTableOnImageForSeeToEncode(int sizeX,int sizeY) {

    for (int x = sizeX ; x <= width ; x+=sizeX) {

        for (int y = 0; y < height; y++) {
           
                img.setRGB(x, y, 0xff0000);
        }
       
        
        
    }

    for (int y = sizeY ; y <= height; y+=sizeY) {

        for (int x = 0; x < width; x++) {
           
                img.setRGB(x, y, 0xff0000);
        }
       
        
        
    }



    // for (int i = 0; i+sizeY < height ; i+=sizeX) {
    //     g.drawLine(0, width, i, i+sizeY);
        
    // }
    
  

}  

public String countNumValueForEachColum(int sizeX,int sizeY) {
    double TotalPixInChannel = sizeX * sizeY ;
    
    int pix = 0 ;
    int count = 0 ;
    double percen = 0.7 ;
    String decode = "" ;

    for (int x = sizeX ; x < width ; x+=sizeX) {
        count = 0 ;

        for (int y = sizeY ; y < height; y+=sizeY) {
           // System.out.println(x + " " +y);
            pix = 0 ;

            for (int i = x-sizeX ; i <x; i++) {
                for (int j = y-sizeY ; j < y; j++) {
                  //  System.out.println(img.getRGB(x-i, y-j));
                    if(img.getRGB(i, j) == -1) {
                        pix ++ ;
                        
                    }
                }
            }
            //System.out.println(pix);
            //System.out.println(pix/TotalPixInChannel);
            
           if (pix/TotalPixInChannel >= percen) count ++ ;
           
            
        }
      //  return ;
    
   // System.out.println(count);
    decode+=count ;
    
    
    }
   // System.out.println(decode);
    return decode ;
}



    

    // for (int i = 0; i+sizeY < height ; i+=sizeX) {
    //     g.drawLine(0, width, i, i+sizeY);
        
    // }
    
  


 
 
 
 
    

 

} // img manager class

class StructuringElement {

    public int[][] elements;
    public int width, height;
    public Point origin;
    public ArrayList<Point> ignoreElements;
   
    public StructuringElement(int width, int height, Point origin) {
    this.width = width;
    this.height = height;
    if (origin.x < 0 || origin.x >= width || origin.y < 0 || origin.y >= height){
        this.origin = new Point();
    } 
    else { 
        this.origin = new Point(origin);
    
    }
    ignoreElements = new ArrayList<>();
    elements = new int[width][height];
    }
} // StructuringElement
        

