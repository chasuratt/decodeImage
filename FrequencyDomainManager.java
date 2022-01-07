import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

class FrequencyDomainManager
{
    Complex[][] transformedImg;
    int width, height, type;
    int imgWidth, imgHeight;
    private Complex[][] original;

    public FrequencyDomainManager(BufferedImage img, boolean isUsingFFT)
    {
        imgWidth = img.getWidth();
        imgHeight = img.getHeight();
        width = nextPowerOf2(imgWidth);
        height = nextPowerOf2(imgHeight);
        type = img.getType();

        transformedImg = new Complex[height][width];

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                int color = img.getRGB(x, y);
                int gray = color & 0xff;

                transformedImg[y][x] = new Complex(gray, 0);
            }
        }

        if (!isUsingFFT) dft2d(false);
        else fft2d(false);

        shifting();

        //store original
        original = new Complex[height][width];
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                original[y][x] = new Complex(transformedImg[y][x].re, transformedImg[y][x].im);
            }
        }
    }

    public static int nextPowerOf2(final int a)
    {
        int b = 1;
        while (b < a)
        {
            b = b << 1;
        }
        return b;
    }

    private void shifting()
    {
        int halfWidth = width / 2;
        int halfHeight = height / 2;

        for (int y = 0; y < halfHeight; y++)
        {
            for (int x = 0; x < width; x++)
            {
                Complex temp = new Complex(transformedImg[y][x].re, transformedImg[y][x].im);

                transformedImg[y][x].re = transformedImg[y + halfHeight][x].re;
                transformedImg[y][x].im = transformedImg[y + halfHeight][x].im;
                
                transformedImg[y + halfHeight][x].re = temp.re;
                transformedImg[y + halfHeight][x].im = temp.im;
            }
        }
        
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < halfWidth; x++)
            {
                Complex temp = new Complex(transformedImg[y][x].re, transformedImg[y][x].im);

                transformedImg[y][x].re = transformedImg[y][x + halfWidth].re;
                transformedImg[y][x].im = transformedImg[y][x + halfWidth].im;
                
                transformedImg[y][x + halfWidth].re = temp.re;
                transformedImg[y][x + halfWidth].im = temp.im;
            }
        }
    }
    
    public BufferedImage getInverse(boolean isUsingFFT)
    {
        BufferedImage temp = new BufferedImage(imgWidth, imgHeight, type);

        shifting();

        if (!isUsingFFT)    dft2d(true);
        else    fft2d(true);

        for (int y = 0; y < imgHeight; y++)
        {
            for (int x = 0; x < imgWidth; x++)
            {
                int gray = (int) transformedImg[y][x].re;
                gray = gray > 255? 255: gray;
                gray = gray < 0? 0: gray;

                int color = (gray << 16) | (gray << 8) | gray;

                temp.setRGB(x, y, color);
            }
        }

        return temp;
    }

    private void dft2d(boolean invert)
    {
        Complex[] temp = new Complex[width];
        //horizontal first
        for (int y = 0; y < height; y++)
        {
            for (int u = 0; u < width; u++)
            {
                temp[u] = new Complex();
                temp[u].re = transformedImg[y][u].re;
                temp[u].im = transformedImg[y][u].im;
            }

            if (!invert)    temp = dft(temp);
            else temp = idft(temp);

            for (int u = 0; u < width; u++)
            {
                transformedImg[y][u].re = temp[u].re;
                transformedImg[y][u].im = temp[u].im;
            }
        }


        //then vertical
        temp = new Complex[height];
        for (int x = 0; x < width; x++)
        {
            for (int v = 0; v < height; v++)
            {
                temp[v] = new Complex();
                temp[v].re = transformedImg[v][x].re;
                temp[v].im = transformedImg[v][x].im;
            }

            if (!invert)    temp = dft(temp);
            else temp = idft(temp);

            for (int v = 0; v < height; v++)
            {
                transformedImg[v][x].re = temp[v].re;
                transformedImg[v][x].im = temp[v].im;
            }
        }
    }

    // compute the DFT of x[] via brute force (n^2 time)
    public static Complex[] dft(Complex[] x) 
    {
        int n = x.length;
        Complex ZERO = new Complex();
        Complex[] y = new Complex[n];
        for (int k = 0; k < n; k++) {
            y[k] = ZERO;
            for (int j = 0; j < n; j++) {
                int power = (k * j) % n;
                double kth = -2 * power *  Math.PI / n;
                Complex wkj = new Complex(Math.cos(kth), Math.sin(kth));
                y[k] = y[k].add(x[j].mult(wkj));
            }
        }
        return y;
    }

    // compute the inverse DFT of x[]
    public static Complex[] idft(Complex[] x) 
    {
        int n = x.length;
        Complex[] y = new Complex[n];

        // take conjugate
        for (int i = 0; i < n; i++) {
            y[i] = x[i].conjugate();
        }

        // compute forward FFT
        y = dft(y);

        // take conjugate again
        for (int i = 0; i < n; i++) {
            y[i] = y[i].conjugate();
        }

        // divide by n
        for (int i = 0; i < n; i++) {
            y[i] = y[i].scale(1.0 / n);
        }

        return y;

    }

    private void fft2d(boolean invert)
    {
        Complex[] temp = new Complex[width];
        //horizontal first
        for (int y = 0; y < height; y++)
        {
            for (int u = 0; u < width; u++)
            {
                temp[u] = new Complex();
                temp[u].re = transformedImg[y][u].re;
                temp[u].im = transformedImg[y][u].im;
            }

            if (!invert)    temp = fft(temp);
            else temp = ifft(temp);

            for (int u = 0; u < width; u++)
            {
                transformedImg[y][u].re = temp[u].re;
                transformedImg[y][u].im = temp[u].im;
            }
        }


        //then vertical
        temp = new Complex[height];
        for (int x = 0; x < width; x++)
        {
            for (int v = 0; v < height; v++)
            {
                temp[v] = new Complex();
                temp[v].re = transformedImg[v][x].re;
                temp[v].im = transformedImg[v][x].im;
            }

            if (!invert)    temp = fft(temp);
            else temp = ifft(temp);

            for (int v = 0; v < height; v++)
            {
                transformedImg[v][x].re = temp[v].re;
                transformedImg[v][x].im = temp[v].im;
            }
        }
    }

    // compute the FFT of x[], assuming its length n is a power of 2
    public static Complex[] fft(Complex[] x) 
    {        
        int n = nextPowerOf2(x.length);
        
        //padding
        Complex[] fftArray = new Complex[n];
        //initialise the value
        for (int i = 0; i < n; i++)
        {
            fftArray[i] = new Complex();
        }
        
        for (int i = 0; i < x.length; i++)
        {
            fftArray[i].re = x[i].re;
            fftArray[i].im = x[i].im;
        }

        // base case
        if (n == 1) return new Complex[] { fftArray[0] };

        // radix 2 Cooley-Tukey FFT
        if (n % 2 != 0) {
            throw new IllegalArgumentException("n is not a power of 2");
        }

        // compute FFT of even terms
        Complex[] even = new Complex[n/2];
        for (int k = 0; k < n/2; k++) {
            even[k] = fftArray[2*k];
        }
        Complex[] evenFFT = fft(even);

        // compute FFT of odd terms
        Complex[] odd  = even;  // reuse the array (to avoid n log n space)
        for (int k = 0; k < n/2; k++) {
            odd[k] = fftArray[2*k + 1];
        }
        Complex[] oddFFT = fft(odd);

        // combine
        Complex[] y = new Complex[n];
        for (int k = 0; k < n/2; k++) {
            double kth = -2 * k * Math.PI / n;
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            y[k]       = evenFFT[k].add(wk.mult(oddFFT[k]));
            y[k + n/2] = evenFFT[k].sub(wk.mult(oddFFT[k]));
        }
        return y;
    }

    // compute the inverse FFT of x[]
    public static Complex[] ifft(Complex[] x) 
    {
        int n = x.length;
        Complex[] y = new Complex[n];

        // take conjugate
        for (int i = 0; i < n; i++) {
            y[i] = x[i].conjugate();
        }

        // compute forward FFT
        y = fft(y);

        // take conjugate again
        for (int i = 0; i < n; i++) {
            y[i] = y[i].conjugate();
        }

        // divide by n
        for (int i = 0; i < n; i++) {
            y[i] = y[i].scale(1.0 / n);
        }

        return y;

    }

    public boolean writeSpectrumLogScaled(String fileName)
    {
        try
        {
            BufferedImage tempimg = new BufferedImage(width, height, type);

            double max = Double.MIN_VALUE, min = Double.MAX_VALUE;

            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    double spectrum = transformedImg[y][x].length();
    
                    if (spectrum > max) max = spectrum;
                    if (spectrum < min) min = spectrum;
                }
            }

            if (min < 1.0)
                min = 0f;
            else
                min = Math.log10(min);

            if (max < 1.0)
                max = 0f;
            else
                max = Math.log10(max);

            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    double spectrum = transformedImg[y][x].length();

                    if (spectrum < 1)
                        spectrum = 0f;
                    else
                        spectrum = Math.log10(spectrum);
                    
                    spectrum = ((spectrum-min)*255 / (max-min));

                    int color = ((int)spectrum << 16) | ((int)spectrum << 8) | (int)spectrum;      
    
                    tempimg.setRGB(x, y, color);
                }
            }

            BufferedImage img = new BufferedImage(width, height, type);

            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    img.setRGB(x, y, tempimg.getRGB(x, y));
                }
            }

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
    }

    public boolean writeSpectrum(String fileName)
    {
        try
        {

            BufferedImage img = new BufferedImage(width, height, type);

            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    double spectrum = transformedImg[y][x].length();
                    
                    spectrum = spectrum > 255? 255: spectrum;
                    spectrum = spectrum < 0? 0: spectrum;

                    int color = ((int)spectrum << 16) | ((int)spectrum << 8) | (int)spectrum;      
    
                    img.setRGB(x, y, color);
                }
            }

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
    }

    public boolean writePhase(String fileName)
    {
        try
        {
            BufferedImage img = new BufferedImage(width, height, type);

            double max = Double.MIN_VALUE, min = Double.MAX_VALUE;

            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    double phase = (Math.atan2(transformedImg[y][x].im, transformedImg[y][x].re));
    
                    if (phase > max) max = phase;
                    if (phase < min) min = phase;
                }
            }

            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    double phase = (Math.atan2(transformedImg[y][x].im, transformedImg[y][x].re));
    
                    phase = ((phase-min)*255 / (max-min));

                    int color = ((int)phase << 16) | ((int)phase << 8) | (int)phase;      
    
                    img.setRGB(x, y, color);
                }
            }
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
    }    

    public void restoreToOriginal()
    {
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                transformedImg[y][x].re = original[y][x].re;
                transformedImg[y][x].im = original[y][x].im;
            }
        }
    }

    public void ILPF(double radius)
    {
        if (radius <= 0 || radius > Math.min(width/2, height/2))
        {
            System.out.println("INVALID Radius!");
            return;
        }

        int centerX = width/2;
        int centerY = height/2;

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                if ((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY) > radius *radius)
                {
                    transformedImg[y][x].re = 0;
                    transformedImg[y][x].im = 0;
                }
            }
        }
    }

    public void GLPF(double radius)
    {
        if (radius <= 0 || radius > Math.min(width/2, height/2))
        {
            System.out.println("INVALID Radius!");
            return;
        }

        int centerX = width/2;
        int centerY = height/2;

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                double distance = Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));
                double h = Math.pow(Math.E, -(distance * distance)/(2 * radius * radius));
                transformedImg[y][x] = transformedImg[y][x].scale(h);
            }
        }
    }

    public void IHPF(double radius)
    {
        if (radius <= 0 || radius > Math.min(width/2, height/2))
        {
            System.out.println("INVALID Radius!");
            return;
        }

        int centerX = width/2;
        int centerY = height/2;

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                if ((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY) <= radius * radius)
                {
                    transformedImg[y][x].re = 0;
                    transformedImg[y][x].im = 0;
                }
            }
        }
    }





} // class

class Complex // 
{
    public double re;
    public double im;

    public Complex()
    {
        this(0, 0);
    }

    public Complex(double re, double im)
    {
        this.re = re;
        this.im = im;
    }

    public Complex add(Complex b)
    {
        return new Complex(this.re + b.re, this.im + b.im);
    }

    public Complex sub(Complex b)
    {
        return new Complex(this.re - b.re, this.im - b.im);
    }

    public Complex mult(Complex b)
    {
        return new Complex((this.re * b.re) - (this.im * b.im),
                        (this.re * b.im) + (this.im * b.re));
    }

    public Complex conjugate()
    {
        return new Complex(re, -im);
    }

    public Complex scale(double b)
    {
        return new Complex(re * b, im * b);
    }

    public double length()
    {
        return Math.sqrt((re * re) + (im * im));
    }

    @Override
    public String toString() {
        return String.format("(%f,%f)", re, im);
    }
}