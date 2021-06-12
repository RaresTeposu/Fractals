import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Mandelbrot extends PApplet {



class Point
{
    boolean inauntru;
    int i;

    Point(boolean inauntru2, int i2)
    {
        inauntru = inauntru2;
        i = i2;
    }
}
public void setup() {
  
  colorMode(RGB, 1);

  drawBrot();

}

int scale = 2;

public PVector pixelPoint(float x,float y)
{
    PVector p = new PVector(
    (x-width/2) * (4/width) * (16/9*scale) +width/2,
    (y-height/2) * (4/height) * (1/scale) +height/2);

    return p;
}

public void drawBrot()
{
    for (int x = 0; x < height; x++) 
    {
        for (int y = 0; y < width; y++) 
        {
            PVector c = pixelPoint(x,y);
            Point result = calculatePoint(c);

            if(result.inauntru == true)
            {
                set(x, y, color(0));
            }
            else if(result.inauntru == false && result.i > 1)
            {
                int col = color(150 + 200 - pow(result.i/(50), 0.5f) * 200 % 255, 80 , 100);
                set(x, y, col);
            }
            else
            {
                set(x, y, color(55));
            }
        }
        
    }
    updatePixels();

}

//calculeaza daca un punct e inauntru sau inafara setului (daca merge sa nu spre infinit)
public Point calculatePoint(PVector c)
{
    PVector z0 = new PVector(0,0);
    int i = 0;
    int margins = 2;
    boolean inauntru = true;

    Point r = new Point(inauntru, i);

    while(i < 50 && inauntru == true)
    {
        z0 = new PVector(z0.x*z0.x - z0.y*z0.y + c.x, 2*z0.x*z0.y + c.y);
        i++;
        if(z0.mag() > margins)
        {
            inauntru = false;
        }
    }

    r.i = i;
    r.inauntru = inauntru;

    return r;

}
/*
void draw() {
  background(255);

  // Establish a range of values on the complex plane
  // A different range will allow us to "zoom" in or out on the fractal

  // It all starts with the width, try higher or lower values
  float w = 1;
  float h = (w * height) / width;

  // Start at negative half the width and height
  float xmin = -w/2;
  float ymin = -h/2;

  // Make sure we can write to the pixels[] array.
  // Only need to do this once since we don't do any other drawing.
  loadPixels();

  // Maximum number of iterations for each point on the complex plane
  int maxiterations = 100;

  // x goes from xmin to xmax
  float xmax = xmin + w;
  // y goes from ymin to ymax
  float ymax = ymin + h;

  // Calculate amount we increment x,y for each pixel
  float dx = (xmax - xmin) / (width);
  float dy = (ymax - ymin) / (height);

  // Start y
  float y = ymin;
  for (int j = 0; j < height; j++) {
    // Start x
    float x = xmin;
    for (int i = 0; i < width; i++) {

      // Now we test, as we iterate z = z^2 + cm does z tend towards infinity?
      float a = x;
      float b = y;
      int n = 0;
      while (n < maxiterations) {
        float aa = a * a;
        float bb = b * b;
        float twoab = 2.0 * a * b;
        a = aa - bb + x;
        b = twoab + y;
        // Infinty in our finite world is simple, let's just consider it 16
        if (a*a + b*b > 16.0) {
          break;  // Bail
        }
        n++;
      }

      // We color each pixel based on how long it takes to get to infinity
      // If we never got there, let's pick the color black
      if (n == maxiterations) {
        pixels[i+j*width] = color(0);
      } else {
        // Gosh, we could make fancy colors here if we wanted
        pixels[i+j*width] = color(sqrt(float(n) / maxiterations));
      }
      x += dx;
    }
    y += dy;
  }
  updatePixels();
}
*/
  public void settings() {  size(640, 480); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Mandelbrot" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
