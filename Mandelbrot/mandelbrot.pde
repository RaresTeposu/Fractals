
double xmin = -2.5;
double ymin = -2;
double wh = 4;
double downX, downY, startX, startY, startWH;
int maxiterations = 250;
boolean shift=false;

void setup() {
  size(500, 500);
  colorMode(HSB, 255);
  loadPixels();
}

void mousePressed() {
  downX=mouseX;
  downY=mouseY;
  startX=xmin;
  startY=ymin;
  startWH=wh;
}

void keyPressed() {
  if (keyCode==SHIFT) shift=true;
}

void keyReleased() {
  if (keyCode==SHIFT) shift=false;
}

void mouseDragged() {
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

void draw() {
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

      float iterationsToBreak = 8.0;
      for(int n = 0; n < maxiterations; n++) { 
        double aa = a * a; 
        double bb = b * b; 
        b = 2.0 * a * b + y; 
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