package snake.game;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;




public class Board extends JPanel implements ActionListener{

    private Image apple;
    private Image dot;
    private Image head;
    
    private final int DOT_SIZE = 10;    // 300 * 300 = 90000 / 100 = 900
    private final int ALL_DOTS = 900;
    private final int RANDOM_POSITION = 29;
    
 
    private  int score = 0;
    private int highScore = 0;


   
    
    private int apple_x;
    private int apple_y;
    
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];
    
    private boolean leftDirection = false;
    private boolean rightDirection =  true;
    private boolean upDirection =  false;
    private boolean downDirection =  false;
    private boolean inGame = true;
    
    private int dots;
    
    private Timer timer;
    

    Board(){
        
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(300, 300));
        
        setFocusable(true);
        
        loadImages();
        initGame();
    }
    

    
    
    public void loadImages(){
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("apple.png"));
        apple  = i1.getImage();
        
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("dot.png"));
        dot = i2.getImage();
        
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("head.png"));
        head = i3.getImage();
    }
    
    public void initGame(){
        
        dots = 5;
        
        for(int z = 0 ; z < dots ; z++){
            x[z] = 50 - z * DOT_SIZE; // x[0] y[0] // x[1] y[1] // x[2] y[2]
            y[z] = 50;
        }
        
        locateApple();
        
        timer = new Timer(100, this);
        timer.start();
       
    }
    
    
    public void locateApple(){
        
        int r = (int)(Math.random() * RANDOM_POSITION); // 0 and 1 =>  0.6 * 20 = 12* 10 = 120
        apple_x = (r * DOT_SIZE); 
        
        r = (int)(Math.random() * RANDOM_POSITION); // 0 and 1 =>  0.6 * 20 = 12* 10 = 120
        apple_y = (r * DOT_SIZE); 
    }
    
    
    public void checkApple(){
        if((x[0] == apple_x) && (y[0] == apple_y)){
            dots++;
            score++;
            locateApple();
        }  
    }
    
    @Override
    public void paintComponent(Graphics g){
    
        super.paintComponent(g);
        
        draw(g);

    }
    
    public void draw(Graphics g){
        if(inGame){
            g.drawImage(apple, apple_x, apple_y, this);
            
            for(int z = 0; z < dots ; z++){
                if(z == 0){
                    g.drawImage(head, x[z], y[z], this);
                }else{
                    g.drawImage(dot, x[z], y[z], this);
                }
            }    
        String msg3 = "SCORE " +score ;
        Font font = new Font("SAN_SERIF", Font.BOLD, 16);
        FontMetrics metrices = getFontMetrics(font);
        
        g.setColor(Color.WHITE);
        g.setFont(font);

        g.drawString(msg3, (300 - metrices.stringWidth(msg3)) / 2 , 30/2);

        
           
        }else{

             gameOver(g);   
             
        }
    }

        
        public void gameOver(Graphics g){
        
        
        if(score > highScore)
        {try {
                PrintWriter writer = new PrintWriter(new FileWriter("C:\\User \\(spath of scorce data)\\ scoredata.txt", true));
                writer.println(score);     
                writer.close();
                } catch(Exception ex){
                System.err.println("ERROR storing scores from file");
                }   }

          File file = new File("C:\\Users\\(spath of scorce data)\\scoredata.txt");

           try  {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                Scanner scanner = new Scanner(new File("C:\\Users\\(spath of scorce data)\\scoredata.txt"));
                while (line != null)                 
                { 
                    try {
                           // parse each line as an int
                          int score2 = scanner.nextInt();
                    
                         if (score2 >= highScore)                       
                        {  
                            highScore = score2; 
                        }

                        line = reader.readLine();
                    } catch (NumberFormatException e1) {

                    }
                    
                }
                reader.close();

            } catch (IOException ex) {
                System.err.println("ERROR reading scores from file");
            }

     
        String msg1 = "Game Over ";
        String msg2 = "SCORE IS " +score ;
        String msg4 = "HIGHSCORE  " +highScore ; 
        
 
        Font font = new Font("SAN_SERIF", Font.BOLD, 14);
        FontMetrics metrices = getFontMetrics(font);
        
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg1, (300 - metrices.stringWidth(msg1)) / 2 , 300/2);
        g.setColor(Color.BLUE);
        g.drawString(msg2, (300 - metrices.stringWidth(msg2)) / 2 , 250/2);
        g.setColor(Color.RED);
        g.drawString(msg4, (300 - metrices.stringWidth(msg4)) / 2 , 200/2);
       
        
    
    }
    
    
    
       public void checkCollision(){
        
        for(int z = dots ; z > 0 ; z--){
            if((z > 4) && (x[0] == x[z]) && (y[0] == y[z])){
                inGame = false;
            }
        } 
        
        if(y[0] >= 900){
            inGame = false;
        }
        
        if(x[0] >= 300){
            inGame = false;
        }
        
        if(x[0] < 0){
            inGame = false;
        }
        
        if(y[0] < 0 ){
            inGame = false;
        }
        
        if(!inGame){
            timer.stop();
        }
    }

    
    public void move(){
        
        for(int z = dots ; z > 0 ; z--){
            x[z] = x[z - 1]; 
            y[z] = y[z - 1];
        }
        
        if(leftDirection){
            x[0] = x[0] -  DOT_SIZE;
        }
        if(rightDirection){
            x[0] += DOT_SIZE;
        }
        if(upDirection){
            y[0] = y[0] -  DOT_SIZE;
        }
        if(downDirection){
            y[0] += DOT_SIZE;
        }
        // 240 + 10 = 250
    }
    
    @Override
    public void actionPerformed(ActionEvent ae){
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }
        
        repaint();
    }
    
    
    private class TAdapter extends KeyAdapter{
    
        @Override
        public void keyPressed(KeyEvent e){
           int key =  e.getKeyCode();
           
           if(key == KeyEvent.VK_LEFT && (!rightDirection)){
               leftDirection = true;
               upDirection = false;
               downDirection = false;
           }
           
           if(key == KeyEvent.VK_RIGHT && (!leftDirection)){
               rightDirection = true;
               upDirection = false;
               downDirection = false;
           }
           
           if(key == KeyEvent.VK_UP && (!downDirection)){
               leftDirection = false;
               upDirection = true;
               rightDirection = false;
           }
           
           if(key == KeyEvent.VK_DOWN && (!upDirection)){
               downDirection = true;
               rightDirection = false;
               leftDirection = false;
           }
        }
    }
    
}
