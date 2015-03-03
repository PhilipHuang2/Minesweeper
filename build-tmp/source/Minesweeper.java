import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import de.bezier.guido.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Minesweeper extends PApplet {


public static final int NUM_ROWS = 20;
public static final int NUM_COLS = 20;
private MSButton[][] buttons; //2d array of minesweeper buttons
private ArrayList <MSButton> bombs = new ArrayList <MSButton>(); //ArrayList of just the minesweeper buttons that are mined

public void setup ()
{
    size(400, 400);
    textAlign(CENTER,CENTER);
    
    // make the manager
    Interactive.make( this );
    
    

    buttons = new MSButton [NUM_ROWS][NUM_COLS];
    for(int r = 0; r < NUM_ROWS; r++)
    {
        for(int c = 0; c < NUM_COLS; c++)
            buttons[r][c] = new MSButton(r, c);
    }

    setBombs();
}
public void setBombs()
{
    int bombRow, bombCol;
    while(bombs.size() < NUM_ROWS*NUM_COLS*.05f)
    {
        bombRow = (int)(Math.random()*NUM_ROWS);
        bombCol = (int)(Math.random()*NUM_COLS);
        if(bombs.contains(buttons[bombRow][bombCol])!= true)
        bombs.add(buttons[bombRow][bombCol]);
            
    }
}

public void draw ()
{
    background( 0 );
    if(isWon())
        displayWinningMessage();
}
public boolean isWon()
{
    //your code here
    return false;
}
public void displayLosingMessage()
{
    //your code here
}
public void displayWinningMessage()
{
    //your code here
}

public class MSButton
{
    private int r, c;
    private float x,y, width, height;
    private boolean clicked, marked;
    private String label;
    
    public MSButton ( int rr, int cc )
    {
        width = 400/NUM_COLS;
        height = 400/NUM_ROWS;
        r = rr;
        c = cc; 
        x = c*width;
        y = r*height;
        label = "";
        marked = clicked = false;
        Interactive.add( this ); // register it with the manager
    }
    public boolean isMarked()
    {
        return marked;
    }
    public boolean isClicked()
    {
        return clicked;
    }
    // called by manager
    
    public void mousePressed () 
    {
        clicked = true;
        if(keyPressed)
            marked = !marked;
        else if(bombs.contains(this))
            displayLosingMessage();
         else if (countBombs(r,c) > 0)
            setLabel(""+countBombs(r,c));
        else 
        {
            if(isValid(r - 1, c-1)&& clicked == false)
                buttons[r-1][c-1].mousePressed();
             if(isValid(r - 1, c)&& clicked == false)
                buttons[r-1][c].mousePressed(); 
            if(isValid(r - 1, c+1)&& clicked == false)
                buttons[r-1][c+1].mousePressed(); 

            if(isValid(r , c-1)&& clicked == false)
                buttons[r][c-1].mousePressed(); 
            if(isValid(r , c+1)&& clicked == false)
                buttons[r][c+1].mousePressed(); 

            if(isValid(r + 1, c-1)&& clicked == false)
                buttons[r+1][c-1].mousePressed(); 
            if(isValid(r + 1, c)&& clicked == false)
                buttons[r+1][c].mousePressed(); 
            if(isValid(r + 1, c+1)&& clicked == false)
                buttons[r+1][c+1].mousePressed(); 
        }
        
        //your code here
    }

    public void draw () 
    {    
        if (marked)
            fill(0);
         else if( clicked && bombs.contains(this) ) 
             fill(255,0,0);
        else if(clicked)
            fill( 200 );
        else 
            fill( 100 );

        rect(x, y, width, height);
        fill(0);
        text(label,x+width/2,y+height/2);
    }
    public void setLabel(String newLabel)
    {
        label = newLabel;
    }
    public boolean isValid(int r, int c)
    {
        if(r >= 0 && r <= NUM_ROWS-1)
            if(c >= 0 && c <= NUM_COLS-1)
                return true;
       return false;
    }
    public int countBombs(int row, int col)
    {
        int numBombs = 0;
            if(isValid(row - 1, col-1)&& clicked == false)
                numBombs++;
             if(isValid(row - 1, col)&& clicked == false)
                numBombs++;
            if(isValid(row - 1, col+1)&& clicked == false)
                numBombs++;

            if(isValid(row , col-1)&& clicked == false)
                numBombs++; 
            if(isValid(row , col+1)&& clicked == false)
                numBombs++;

            if(isValid(row + 1, col-1)&& clicked == false)
                numBombs++; 
            if(isValid(row + 1, col)&& clicked == false)
                numBombs++;
            if(isValid(row + 1, col+1)&& clicked == false)
                numBombs++; 
        return numBombs;
    }
}



  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Minesweeper" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
