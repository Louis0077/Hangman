package hangman;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class Board extends JFrame {
    
    //Δηλωση Μεταβλητων 
    private final int Width;
    private final int Height;
    
    //Προσπαθειες
    private final int max_p;
    
    //Μεγεθος λεξης   
    private final int max_length;
    
    //Path photo
    private final String img_dir;
    
    //Photo type
    private final String ph_type;
    
    //Base img 
    private final String base_img;
    
    //Letter dir 
    private final String limg_dir;
    
    //Letter type
    private final String l_type;
    
    //Rack(attack)
     private LetterRack gameRack;
     
     //Img plc 
     private HangmanCL gameHangman;
     
     //Λαθος
     private int incor;
     
     //Σωστα γραμματα 
     private JLabel correct;
     
     //Label λαθων
     private JLabel incorrect;
     
     //Λεξη
     private String word;
     
     //Hiding word 
     private StringBuilder passwordHidden;
    
    
    
    public Board(){
    Width=500;
    Height=500;

    max_p=5;
    max_length=10;
    
    //dirs   
    img_dir = limg_dir ="C:\\Users\\elias\\OneDrive\\Desktop\\Hangman\\src\\hangman\\images\\";
    ph_type = l_type = ".png";
    base_img= "hangman"; 
    
    setTitle("Hangman with Jswing");
    setSize(Width,Height);
    setResizable(false);
    addCloseWindowListener();
    
    start();  
    }
    
    
    //Arxh paixnidiou
    private void start(){
    
    //Μετρητης Λαθων
    incor=0;
    
    correct=new JLabel("Λεξη: ");
    incorrect=new JLabel("Λάθος: ");
    word=new String(); 
    passwordHidden=new StringBuilder();
    
    
    getPassword();
    addTpanel();
    addRack();
    addHangman();
            
            
   Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
   setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2 - 200);
   setVisible(true);    
 }
    
    
    
    
    //Close with options
   private void addCloseWindowListener(){
   
   
   setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
   
   addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent we)
            {
                int prompt = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to quit?",
                        "Quit?", 
                        JOptionPane.YES_NO_OPTION);
                
                if (prompt == JOptionPane.YES_OPTION)
                    System.exit(0);
            }
        });   
   }
   
   
   
    private void addTpanel()
    {
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(1,2));
        textPanel.add(correct);
        textPanel.add(incorrect);
        // use BorderLayout to set the components of the gameboard in
        //     "visually agreeable" locations
        add(textPanel, BorderLayout.NORTH);
    }
    
   //Letter Rack(attack)
   private void addRack(){
   
    gameRack=new LetterRack (word,limg_dir,l_type);   
    gameRack.attachListeners(new TileListener());
    add(gameRack,BorderLayout.SOUTH);
   }
   
   private void addHangman(){
   JPanel hPanel=new JPanel();
   gameHangman =new HangmanCL(base_img,img_dir,ph_type);
   hPanel.add(gameHangman);
   add(hPanel,BorderLayout.CENTER);
   }
   
   
   private void getPassword(){
   
       
       
   String[] epiloges ={"Ας παιξουμε","Εξοδος"};    
   JPanel passPanel=new JPanel();
   JLabel passLabel=new JLabel("Δώσε λέξη για να μαντέψει ο αλλος παίκτης");
   JTextField passText= new JTextField(max_length);
   passPanel.add(passLabel);
   passPanel.add(passText);
   int confirm =-1;
   
   while (word.isEmpty()){
       
       confirm=JOptionPane.showOptionDialog(null, passPanel, "Δώσε λέξη", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, epiloges, epiloges[0]);
   
   
      if (confirm == 0)
            {
                word = passText.getText();
                
                
                if (!word.matches("[a-zA-Z]+") || 
                    word.length() > max_length)
                {
                    JOptionPane.showMessageDialog(null, 
                            "Password must be less than 10 characters and " +
                            "only contain letters A-Z.", 
                            "Invalid Password", 
                            JOptionPane.ERROR_MESSAGE);
                    word = ""; 
                }
            }
      else if (confirm == 1)
                System.exit(0);
        }
   
   passwordHidden.append(word.replaceAll(".", "*"));
   correct.setText(correct.getText() + passwordHidden.toString());
  
   }
   
   //Nέο παιχνιδι
   
   private void newGame(){
   
   int apant = JOptionPane.showConfirmDialog(null, 
                "Η λέξη ήταν: " + word +
                "\n Θες να ξαναπαίξεις;",
                "Άλλη μία;",
                JOptionPane.YES_NO_OPTION);
   
   if (apant==JOptionPane.YES_OPTION){
       start();
   }
   else {
       System.exit(0);
   }
   
  }
   
     
    private class TileListener implements MouseListener  {
        @Override   
        public void mousePressed(MouseEvent e) 
        {
            Object source = e.getSource();
            if(source instanceof LetterTile)
            {
                char c = ' ';
                int index = 0;
                boolean updated = false;
                
                // cast the source of the click to a LetterTile object
                LetterTile tilePressed = (LetterTile) source;
                c = tilePressed.guess();
                
                // reveal each instance of the character if it appears in the
                //     the password
                while ((index = word.toLowerCase().indexOf(c, index)) != -1)
                {
                    passwordHidden.setCharAt(index, word.charAt(index));
                    index++;
                    updated = true;
                }
                
                // if the guess was correct, update the GameBoard and check
                //     for a win
                if (updated)
                {
                    correct.setText("Word: " + passwordHidden.toString());
                    
                    if (passwordHidden.toString().equals(word))
                    {
                        gameRack.removeListeners();
                        gameHangman.winImage();
                        newGame();
                    }
                }
                
                // otherwise, add an incorrect guess and check for a loss
                else
                {
                    incorrect.setText("Incorrect: " + ++incor);
                    
                    if (incor >= max_p)
                    {
                        gameHangman.loseImage();
                        gameRack.removeListeners();
                        newGame();
                    }
                    
                    else
                        gameHangman.nextImage(incor);
                }
            }
        }
   
   
   
   
   
  @Override
        public void mouseClicked(MouseEvent e) {}  

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}
        
        @Override
        public void mouseExited(MouseEvent e) {}  
   
   
   
   

   
   
   
   
   
   
   }
}
    
    
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

