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

public class KochProcessing extends PApplet {

//Pentru Debugg : Ctrl + Shift + B SAU Ctrl + Shift + p -> Processing: Run Processing Project

//Clasa ce creaza o linie

class KochLine 
{

  //Startul si sfarsitul linii
  PVector start;
  PVector end;

  //Un constructor ce initializeaza punctele de start si end
  KochLine(PVector a, PVector b) {
    start = a.get();
    end = b.get();
  }

  public PVector pctStart()
  {
    return start.get();
  }
  public PVector pctA() 
  {
    // .sub() scade dintr-un vector alt vector (defapt ii aduna, daca amandoi vectorii au baza in colt,
    // (vezi https://p5js.org/reference/#/p5.Vector/sub la img dinamica),
    // func .sub() face un vector cu inceputul la primu si sf la al doile) =>
    //=> putem scadea din vectorul de end vectorul de start 
    //pentru a creea un vector now nou, pe care sa il impartim la 3
    PVector length = PVector.div(PVector.sub(end, start), 3);
    
    // aduna noul vector format la linia veche ca sa aflam urm. punct
    length.add(start);
    return length;
  }

  public PVector pctB() 
  {
    //Creem un vecotr de inceput, pe care sa il modificam
    PVector pct = start.get();

    PVector length = PVector.div(PVector.sub(end, start), 3);
    pct.add(length);

    // rotim vectorul de length cu 60 
    length.rotate(-radians(60));
    // ne miscam cu pct pe vectorul length pana la pctA
    pct.add(length);
    return pct;
  }

  public PVector pctC() 
  {
    // Aceelasi lucru doar ca pe invers
    PVector length = PVector.div(PVector.sub(start, end), 3);
    // Adaug sfarsitul la linie
    length.add(end);
    return length;
  }

  public PVector pctEnd()
  {
    return end.get();
  }
  
  //Functie de creeare a unei liniei propriu zise
  public void display() {
    stroke(0);
    if(start.x > -50 && start.y < 650 && end.x < 650 && end.y < 650)
    {
      line(start.x, start.y, end.x, end.y);
    }
    
  }
}

ArrayList<KochLine> lineArray;

//Numarul de iteratii a procesului de recsivitate
float nrIteratii=1.0f;

//Cu cat se da zoom
//Datorita faptului ca zoomul se face in cel mai tampit mod posbil, are nevoie de o acceleratie
float acc = millis()/1000;
float zoom = 300.0f;

//Input de la taste
public void keyPressed() 
{

    if (key == 'd') 
    {
      nrIteratii += 1;
    } 
    else if (key == 'a') 
    {
      nrIteratii -= 1;
    }
    else if (key == 'w') 
    {
      zoom += 20;
    }
    else if (key == 's') 
    {
      zoom -= 20;
    }
}


//UPDATED: am mutat tot in draw pt a putea updata in funnctie de Input
//(setup() ruleaza o singura data)
public void setup() {

  frameRate(12);
  
}


public void draw() {
  background(255);
  //creem pt prima oara lista
  lineArray = new ArrayList<KochLine>();

  //initilizam punctele triunghiului principal, pe care le putem updatata in functie de zoom
  //se da zoom la coltul de sus (zoom/2 = cos(60))
  PVector start = new PVector(100-zoom, 450+zoom/2);
  PVector end   = new PVector(width-100+zoom, 450+zoom/2);
  PVector top   = new PVector(width/2, 450-346.41f);

  //adaugam o linie (muchie a triunghiului) pt fiecare 2 puncte la lista
  lineArray.add(new KochLine(end, start));
  lineArray.add(new KochLine(start, top));
  lineArray.add(new KochLine(top, end));
  //Se schimba nr. de recursivitati in functie de Input-ul de la tastatura
  //float nrIteratii = map(mouseX,1,width-1,1,5);
  for (int i = 0; i < nrIteratii; i++) 
  {
    generate();
  }

  for (KochLine i : lineArray) {
    i.display();
  }

  int size = lineArray.size();
  println(size);
}

//TODO: Sterge ce nu se vede in ecran
public void generate() 
{
  // Creaza urmatoare generatie din lista de Arrayuri
  ArrayList newLineArray = new ArrayList<KochLine>();

  for (KochLine i : lineArray) 
  {
  //aici se initliazeaza 5 pucnte pe linie, dupa reguluile impuse de Koch
    PVector start = i.pctStart();
    PVector a = i.pctA();
    PVector b = i.pctB();
    PVector c = i.pctC();
    PVector end = i.pctEnd();

    newLineArray.add(new KochLine(start,a));
    newLineArray.add(new KochLine(a,b));
    newLineArray.add(new KochLine(b,c));
    newLineArray.add(new KochLine(c,end));

    /*
    if(start.x > 0 && start.y < 600 && b.x < 600 && b.y < 600)
    {
      newLineArray.add(new KochLine(start,b)); 
    }
    if(a.x > 0 && a.y < 600 && b.x < 600 && b.y < 600)
    {
      newLineArray.add(new KochLine(a,b)); 
    }
    if(b.x > 0 && b.y < 600 && c.x < 600 && c.y < 600)
    {
      newLineArray.add(new KochLine(b,c)); 
    }
    if(c.x > 0 && c.y < 600 && end.x < 600 && end.y < 600)
    {
      newLineArray.add(new KochLine(c,end)); 
    }
    */
  }
  // Noua generatie devine generatia principala 
  lineArray =  newLineArray;
}
  public void settings() {  size(600, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "KochProcessing" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
