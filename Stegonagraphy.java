import java.awt.Color;
import java.util.ArrayList;
import java.awt.Point; 


public class Steganography  {
public static void clearLow(Pixel p) {
    
    int numRed = p.getRed();
    int numGreen = p.getGreen();
    int numBlue = p.getBlue();
    numRed = (numRed /4)*4;
    numGreen = (numGreen /4)*4;
    numBlue = (numBlue /4)*4;
    p.setRed(numRed);
    p.setGreen(numGreen);
    p.setBlue(numBlue);
}
public static Picture testClearLow(Picture p)
{
    Picture pictureThing = new Picture(p);
        Pixel [][] pixels = pictureThing.getPixels2D();
        for(int r = 0; r < pixels.length; r++)
        {
            //finds the row length. V
                for(int c = 0; c<pixels[0].length; c++)
                {
                  Steganography .clearLow(pixels[r][c]);

                }



        }
   
    return pictureThing;
}
 public static void setLow (Pixel p, Color c){
 clearLow(p);
     //clears the 6 rightmost bits from the color
  int  numRed = (c.getRed() /64);
   int numGreen = (c.getGreen() /64);
  int  numBlue = (c.getBlue() /64);
   
    p.setRed(p.getRed()+numRed);
    p.setGreen(p.getGreen()+numGreen);
    p.setBlue(p.getBlue()+numBlue);
}
public static Picture testSetLow(Picture p, Color col)
{
    Picture pictureYes = new Picture(p);
        Pixel [][] pixels = pictureYes.getPixels2D();
        for(int r = 0; r < pixels.length; r++)
        {
            //finds the row length. V
                for(int c = 0; c<pixels[0].length; c++)
                {
                  Steganography .setLow(pixels[r][c], col);
                }
        }
   return pictureYes;
}
public static Picture revealPicture(Picture hidden) 
{ 
Picture copy = new Picture(hidden); 
Pixel[][] pixels = copy.getPixels2D();
Pixel[][] source = hidden.getPixels2D(); 
for (int r = 0; r < pixels.length; r++)
{ 
for (int c = 0; c < pixels[0].length; c++)
{ 	
Color col = source[r][c].getColor();
 //Code for revealPicture
pixels[r][c].setRed((col.getRed() % 4) * 64); 
pixels[r][c].setGreen((col.getGreen() % 4) * 64); 
pixels[r][c].setBlue((col.getBlue() % 4) * 64); 
// % 4 gets the leftmost 2 bits and times it by 64 to clear the rest of it

}
		}
return copy; 
}
public static boolean canHide (Picture source, Picture secret) {
    if(source.getHeight() >= secret.getHeight() && source.getWidth() >= secret.getWidth()){
       return true;  //this checks the picture sizes to make sure you can hide the secret in the source 
    }
   else{
       return false;
   }
}

public static Picture hidePicture(Picture source, Picture secret) {
  Picture mySource = new Picture(source); //creates new pictures
  Picture mySecret = new Picture(secret);
  Pixel[][] pixelMySource = mySource.getPixels2D();
  Pixel[][] pixelMySecret = mySecret.getPixels2D();
  for (int rows = 0; rows < pixelMySource.length; rows++)
    {
    for(int cols = 0; cols < pixelMySource[rows].length; cols++){ // this hides the secret picture in the source
          setLow(pixelMySource[rows][cols], pixelMySecret[rows][cols].getColor());

      }
    }
  return mySecret;
}

public static Picture hidePicture(Picture source, Picture secret, int staRow, int staCol){
Picture mySource = new Picture(source); // creates a new picture
Picture mySecret = new Picture(secret);
Pixel[][] pixelMySource = mySource.getPixels2D();
Pixel[][] pixelMySecret = mySecret.getPixels2D();
for(int rows = staRow, i = 0; rows < pixelMySource.length && i < pixelMySecret.length; rows++,i++){ //allows for the picture to be hidden anywhere but 
											           //but in this case we hide it in the middle.
  for (int cols = staCol, c = 0; cols< pixelMySource[0].length && c < pixelMySecret[0].length; cols++,c++){
    setLow(pixelMySource[rows][cols], pixelMySecret[i][c].getColor()); 
  }
} 
  return mySource; 
}

public static boolean isSame(Picture picture1, Picture picture2){
  Picture pic1 = new Picture (picture1); //creates two new pictures
  Picture pic2 = new Picture (picture2); 

    Pixel [][] pixel1Pic = pic1.getPixels2D(); 
    Pixel [][] pixel2Pic = pic2.getPixels2D(); 
     for(int rows = 0, r = 0; rows < pixel1Pic.length && r < pixel2Pic.length; rows++, r++) //iterates through the rows and columns
     {
        for (int cols = 0, c = 0; cols < pixel1Pic[0].length && c < pixel2Pic[0].length; cols++, c++)
            {
            if(!pixel1Pic[rows][cols].getColor().equals((pixel2Pic[rows][cols].getColor()))) // checks to see if the pictures are the same but since we 
		    // know what he have been looking at to see if they are different we must use the getColor method to check if they are the same. 
		    //But in this case we check when they are not equal to each other and returning false. Overall it checks to see if the 2 pictures are the same
            {
                return false; 
            }
        }
    }

return true;

}


// have to use pictures as parameters because the method is taking in pictures 
  public static ArrayList<Point> findDifferences(Picture One, Picture Two) {
    Picture Copy1 = new Picture(One);//creates two pictures 
    Picture Copy2 = new Picture(Two);
    Pixel[][] one = Copy1.getPixels2D();//creates new pixel
    Pixel[][] two = Copy2.getPixels2D(); 
    ArrayList<Point> differences = new ArrayList<Point>();//creates an arrayList of points
    for (int r = 0; r < one.length; r++){ 
      for (int c = 0; c < one[0].length; c++){
        if (!one[r][c].getColor().equals(two[r][c].getColor())) {
          differences.add(new Point(r,c));// here this for loop with iterate through the rows and columns and find when they are different since 
	    				  //we negated the if statement and then we check the corrdinates where the pictures are different and add
					//the new point of differences to the arraylist and return the arrayList
        }
      }
    }
    return differences; 
  }

  
  public static Picture showDifferentArea(Picture source, ArrayList<Point> differences){
    Picture change = new Picture(source);
    Pixel[][] changePixels = change.getPixels2D();
    int maxX = (int)differences.get(0).getX(); // Since these are points we have to cast them to integers and that is why we used(int)
    int minX = (int)differences.get(0).getX(); //Looks at the next item since we used (0)
    int maxY = (int)differences.get(0).getY();
    int minY =(int)differences.get(0).getY();
    for (int i = 0; i < differences.size(); i++) {
      Point current = differences.get(i);
      if (current.getX() > maxX) { // we get the current x and if its bigger than the maxX we set the current x to the new max. 
        maxX = (int)current.getX();
      }
      if (current.getX()< minX){	// we do this for the rest of the X and Y's 
        minX = (int)current.getX();
      }
      if (current.getY()> maxY){
        maxY =(int) current.getY();
      }
      if (current.getY()< minY){
        minY = (int)current.getY();
      }
    }
    for (int r = minX; r <=maxX; r++){  // this borders the outline of where the differences are located 
        changePixels[r][minY].setRed(255); // here in these two we get the outer boardes on the left and right of the differences 
	    					//so we look at the edge of the rows 
        changePixels[r][minY].setGreen(0);
        changePixels[r][minY].setBlue(0);	// this for statement will use the arraylist of point to draw a rectangle around the differnces

        changePixels[r][maxY].setRed(255);
        changePixels[r][maxY].setGreen(0);
        changePixels[r][maxY].setBlue(0);

       

    }

    for (int c= minY; c<=maxY;c++){	//this is the same as the one above it, instead we look for the top and bottom borders using the columns
         				//this again will help draw a rectangle around the part where the differences are shown
        changePixels[maxX][c].setRed(255);
        changePixels[maxX][c].setGreen(0);
        changePixels[maxX][c].setBlue(0);
        changePixels[minX][c].setRed(255);
        changePixels[minX][c].setGreen(0);
        changePixels[minX][c].setBlue(0);
    }
    return change;
    }

    public static void main(String[] args)
    {
	    //all this is testing our methods to ensure they work properly 
        Picture hall = new Picture("femaleLionAndHall.jpg");
        Picture robot2 = new Picture("robot.jpg");
        Picture flower2 = new Picture("flower1.jpg"); 
        
        // hide pictures
        Picture hall2 = hidePicture(hall, robot2, 50, 300);
        Picture hall3 = hidePicture(hall2, flower2, 115, 275);
        hall3.explore();
        if(!isSame (hall, hall3))
        {
        Picture hall4 = showDifferentArea(hall, findDifferences(hall, hall3));
        hall4.show();
        Picture unhiddenHall3 = revealPicture(hall3);
        unhiddenHall3.show();
        }
        
        
    }
  }
  

