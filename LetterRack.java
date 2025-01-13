package hangman;


import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JPanel;



public class LetterRack extends JPanel {
    //Seires/sthles
    private final int col;
    private final int row;
    
    private final GridLayout layout;
    
    private final int cap;
    private final String img_dir;
    private final String img_type;
    private final String word;
     private final ArrayList<LetterTile> rack;
     
     public LetterRack()
    {
        this("password", 
        "C:\\Users\\elias\\OneDrive\\Desktop\\Hangman\\src\\hangman\\images\\",".png");
    }
     
     
    public LetterRack(String pword,String imgdir, String imgtype){
    
      col=8;
      row=2;
      
      layout= new GridLayout(row,col);
      cap=row*col;
      img_dir=imgdir;
      img_type=imgtype;
      
      rack=new ArrayList<>();
      
      word=pword;
      
      setBorder(BorderFactory.createEmptyBorder(10,17,10,10));
      setLayout(layout);
      loadRack();
    
    
    }
    
    
    private void loadRack()
    {
        buildRack();
        for (LetterTile tile : rack)
            add(tile);
    }
    
    
    private void buildRack()
    {
        StringBuilder passwordBuilder = 
                new StringBuilder(word
                        .toLowerCase());
        ArrayList<Character> tiles = new ArrayList<>(); 
        Random rand = new Random();
        int i = 0, j = 0;
        
        // xarakthres sto rack
        while (passwordBuilder.length() > 0)
        {
            // elegxos gia dipla grammata
            if (!tiles.contains(passwordBuilder.charAt(0)))
            {
                tiles.add(passwordBuilder.charAt(0));
                i++;
            }
            passwordBuilder.deleteCharAt(0);
        }
        
        // random sto upoloipo
        for (; i < cap; i++)
        {
            Character c = 'a';  //The letter "a" is only used as a default value.
            do{
             c = (char)((rand.nextInt(26))+97); //kwdikas ascii gia ta mikra
            }while(tiles.contains(c));
            tiles.add(c);
        }
    
        
        for (i = 0; i < cap; i++)
        {
            j = rand.nextInt(tiles.size());
            rack.add(new LetterTile(tiles.get(j), 
                    img_dir, 
                    img_type));
            tiles.remove(j);
        }
    }
    
       public void attachListeners(MouseListener l)
    {
        for (LetterTile tile : rack)
            tile.addTileListener(l);
    }
       
       
       
    public void removeListeners()
    {
        for (LetterTile tile : rack)
            tile.removeTileListener();
    }   
       
       }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    


   