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

  PVector pctStart()
  {
    return start.get();
  }
  
  /*
  PVector pctA()
  {
    PVector a = pctStart();

    PVector length = PVector.div(PVector(start, end), 3);

    a.add(length);

    return a;
  }

  PVector pctB()
  {
    PVector b = pctStart();

    PVector length = PVector.div(PVector(start, end), 1/3);

    a.add(length);

    return a;
  }

  PVector pctC()
  {
    PVector c = pctStart;

    PVector length = PVector.mult(PVector(start, end), 2/3.0);

    a.add(length);

    return a;
  }
  */
  PVector pctA() 
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

  PVector pctB() 
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

  PVector pctC() 
  {
    // Aceelasi lucru doar ca pe invers
    PVector length = PVector.div(PVector.sub(start, end), 3);
    // Adaug sfarsitul la linie
    length.add(end);
    return length;
  }

  PVector pctEnd()
  {
    return end.get();
  }
  
  //Functie de creeare a unei liniei propriu zise
  void display(float zoom) {
    stroke(0);
    line(start.x, start.y, end.x, end.y);
  }
}

ArrayList<KochLine> lineArray;

//Numarul de iteratii a procesului de recsivitate
float nrIteratii=1.0;

//Cu cat se da zoom
//Datorita faptului ca zoomul se face in cel mai tampit mod posbil, are nevoie de o acceleratie
float acc = millis()/1000;
float zoom = 0.0*acc;

//Input de la tase
void keyPressed() 
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


//UPDATED: am mutat tot in draw pt a putea updata in fucnite de Input
//(setup() ruleaza o singura data)
void setup() {

  frameRate(12);
  size(600, 600);
}


void draw() {
  background(255);
  //creem pt prima oara lista
  lineArray = new ArrayList<KochLine>();

  //initilizam punctele triunghiului principal, pe care le putem updatata in functie de zoom
  //se da zoom la coltul de sus (zoom/2 = cos(60))
  PVector start = new PVector(100-zoom, 450+zoom/2);
  PVector end   = new PVector(width-100+zoom, 450+zoom/2);
  PVector top   = new PVector(width/2, 450-346.41);

  //adaugam o linie (muchie a triunghiului) pt fiecare 2 puncte la lista
  lineArray.add(new KochLine(end, start));
  lineArray.add(new KochLine(start, top));
  lineArray.add(new KochLine(top, end));

  //Se schimba nr. de recursivitati in functie de locatia mouseului
  //float nrIteratii = map(mouseX,1,width-1,1,5);
  for (int i = 0; i < nrIteratii; i++) 
  {
    generate();
  }

  for (KochLine i : lineArray) {
    i.display(zoom);
  }
}

void generate() {
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
  }
  // Noua generatie devine generatia principala 
  lineArray =  newLineArray;
}