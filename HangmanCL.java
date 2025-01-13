package hangman;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;


public class HangmanCL extends JLabel {
    
   private final int pref_width;
   private final int pref_height;
   private final String BaseName;
   private final String img_dir;
   private final String ph_type;
   private String path;
   private BufferedImage image;
   
   
   
    public HangmanCL()
    {                          
        this("hangman", 
        "C:\\Users\\elias\\OneDrive\\Desktop\\Hangman\\src\\hangman\\images\\", ".png");
    }
    
    
      public HangmanCL(String imageBaseName, String imageDirectory, 
            String imageType)
    {
        pref_width = 440;
        pref_height = 255;
        
        BaseName = imageBaseName;
        img_dir = imageDirectory;
        ph_type = imageType;
        
        // you must suffix all images with _(image number) for this to work
        setPreferredSize(new Dimension(pref_width, pref_height));
        path = img_dir + BaseName + "_0" + ph_type;
         System.out.println(path);
        image = load(path);
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
    
    
    
    
    
    
     public void nextImage(int imageNumber) 
    { 
        loadNewImage(String.valueOf(imageNumber));
    }
    
    private void loadNewImage(String suffix)
    {
        path = img_dir + BaseName + "_" + suffix + ph_type;
        image = load(path);
        repaint();  
    }
    
    
   public void loseImage() { loadNewImage("lose"); }  
   
   public void winImage() { loadNewImage("win"); }
    
    
    
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
    
    
    
    
    
    
    
    
    
    

