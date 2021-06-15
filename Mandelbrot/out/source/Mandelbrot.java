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


double xmin = -2.5f;
double ymin = -2;
double wh = 4;
double downX, downY, startX, startY, startWH;
int maxiterations = 250;
boolean shift=false;

public void setup() {
  
  colorMode(HSB, 255);
  loadPixels();
}

public void mousePressed() {
  downX=mouseX;
  downY=mouseY;
  startX=xmin;
  startY=ymin;
  startWH=wh;
}

public void keyPressed() {
  if (keyCode==SHIFT) shift=true;
}

public void keyReleased() {
  if (keyCode==SHIFT) shift=false;
}

public void mouseDragged() {
  double deltaX=(mouseX-downX)/width;
  double deltaY=(mouseY-downY)/height;

  if (!shift) {
    xmin = startX-deltaX*wh;
    ymin = startY-deltaY*wh;
  } 
  else {
    if (wh>10) wh=10;
    if (deltaX>1) deltaX=1;
    wh = startWH-deltaX*wh;
    xmin = startX+deltaX*wh/2;
    ymin = startY+deltaX*wh/2;
  }
}

public void draw() {
  double xmax = xmin + wh;
  double ymax = ymin + wh;

  // Calculam cu cat incrementam x si y pt fiecare pixel
  // ganditiva la acest procesul ca o convertrie dintre un pixel si un Punct geometric
  double dx = (xmax-xmin) / width;
  double dy = (ymax-ymin) / height;


  //Initilaizam y cu cea mai mica valoare
  double y = ymin;
  for (int j = 0; j < height; j++) 
  {
    //Initilaizam x cu cea mai mica valoare
    double x = xmin;
    for (int i = 0; i < width; i++) 
    {
      //Testam daca prin iteratii fc(z) = z^2 +c tinde spre infinit sau spre 0
      //mandelbrotFunction(x,y)
      //
      double a = x;
      double b = y;
      int t= 0;

      float iterationsToBreak = 8.0f;
      for(int n = 0; n < maxiterations; n++) { 
        double aa = a * a; 
        double bb = b * b; 
        b = 2.0f * a * b + y; 
        a = aa - bb + x; 
        if (aa + bb > iterationsToBreak) 
        {
          break;
        }  
        t++;
      }

      pixels[i+j*width] = (t==maxiterations) ? color(0) : color(t*16 % 255, 255, 255);

      x += dx;
    }
    y += dy;
  }
  updatePixels();
}
  public void settings() {  size(500, 500); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Mandelbrot" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
