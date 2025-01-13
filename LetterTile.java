
package hangman;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;


public class LetterTile extends JLabel{
    
    private final char img_l;
    private final String img_dir;
    private final String img_type;
    private final int pref_width;
    private final int pref_height;
    private String path;
    private BufferedImage image;
    private MouseListener tileListener;
    
    
    
    public LetterTile() { 
        this(' ', "C:\\Users\\elias\\OneDrive\\Desktop\\Hangman\\src\\hangman\\images\\", ".png"); 
    }
    
    
    
    public LetterTile(char imageLetter, String imageDirectory, String imageType)
    {
        img_l = imageLetter;
        img_dir = imageDirectory;
        img_type = imageType;
        
        pref_width = pref_height = 50;
        
        setPreferredSize(new Dimension(pref_width,pref_height));
        path = img_dir + img_l  + img_type;
        image = load(path);
    }
    
     private void loadNewImage(String suffix)
    {
        path = img_dir + img_l + "_" + suffix +img_type;
        image = load(path);
        repaint();  
    }
    
     
     
     private BufferedImage load(String imagePath)
    {
        BufferedImage img = null;

        try 
        {
            img = ImageIO.read(new File(imagePath));
        } 

        catch (IOException ex) 
        {
            System.err.println("loadImage(): Error: Image at "
                    + imagePath + " could not be found");
            System.exit(1);
        }

        return img;
    }
   
      public char guess() 
    { 
        loadNewImage("guessed");
        removeTileListener();
        return img_l;
    }
    public void addTileListener(MouseListener l) 
    { 
        tileListener = l;
        addMouseListener(tileListener);
    }
      
      
      public void removeTileListener() { removeMouseListener(tileListener); } 
      
      
      
      
     
      
         @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        g.drawImage(image, 
                0, 
                0, 
                pref_width, 
                pref_height, 
                null);
    }
}
      

