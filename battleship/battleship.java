import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// Used to generate random boat position
import java.util.Random;

/**********
 * @author Jack O'Keeffe
 * @dueDate Dec 15, 2021
 * @description A battleship program where the user tries to find the location of randomly generated boats hidden on a board.
 ***********/

public class battleship {
    JLabel misses;
    JLabel hits;
    JButton instructions;
    // 2D Array used to create the game board
    JButton[][] buttons;
    // Used to create jet ski ship groups (2 boats)
    boolean again = false;

    // Used to track GUI information (scores & winner)
    byte missCount = 0;
    byte hitCount = 0;
    byte numBoats = 0;

    public static void main(String[] args){
        new battleship();
    }
    /**
     * Create the battleship board and randomly generate the boat locations.
     **/
    public battleship(){
        // Create a random object used to randomly place ships on the board
        Random rand = new Random();

        JFrame frame = new JFrame("Battleship");
        JPanel panel = new JPanel(new GridLayout(7, 7));
        JPanel playerScores = new JPanel(new GridLayout(2, 3));
        JLabel title = new JLabel("Battleship");

        // Score Information
        hits = new JLabel("Hits: 0");
        misses = new JLabel("Misses: 0");

        // Button: when clicked it calls the openInstructions() method creating a new window with the game instructions
        instructions = new JButton("Instructions");
        instructions.addActionListener(new openInstructions());

        // Create a 2D array to create the grid of buttons
        buttons = new JButton[7][7];

        // Call the findImage function to prepare the water img to assign to each button
        ImageIcon image = findImage("water.jpg", 100, 100);

        // Iterate through the buttons in the 2D array
        for(byte i = 0; i < buttons.length; i++){
            for(byte j = 0; j < buttons[i].length; j++){
                // Create integer to be used to store the random number
                int random = 0;

                // Set a maximum of 10 boats
                if (numBoats >= 10){
                    random = 0;
                } else{
                    // If there are less than ten boats, generate a random number 1-6
                    random = rand.nextInt(6);
                }

                // If again (boolean variable) is true (only after a jet ski has been created)...
                if(again) {
                    // Create a new button with the water image
                    buttons[i][j] = new JButton(image);
                    // Give it the actionCommand of "Double" (meaning it is a Jet Ski and another Jet Ski must be right beside it
                    buttons[i][j].setActionCommand("Double");
                    numBoats++;
                    // Set again to false, second Jet Ski has been made, next to the last Jet Ski
                    again = false;
                    // If random number = 3, if there are less than 9 boats (since we want to make only 10 boats)
                } else if (random == 3 && numBoats < 9 && i < 6) {
                    buttons[i][j] = new JButton(image);
                    // Create a Jet Ski (1 in 6 chance)
                    buttons[i][j].setActionCommand("Double");
                    numBoats++;
                    // Set again = true, next iteration it will fulfil the above statement and create the second Jet Ski next to this Jet Ski
                    again = true;
                } else if (random == 1){
                    buttons[i][j] = new JButton(image);
                    // Create regular boat (1 in 6 chance)
                    buttons[i][j].setActionCommand("(" + i + ", " + j + ") = " + 1);
                    numBoats++;
                } else {
                    // Create a blank button (no boat)
                    buttons[i][j] = new JButton(image);
                    buttons[i][j].setActionCommand("(" + i + ", " + j + ") = " + null);
                }
                buttons[i][j].addActionListener(new onClick());
                panel.add(buttons[i][j]);
            }
        }

        // A loop is not used here as instructions must be called after the first place (title) to align hits and misses
        place(title, playerScores);

        // Cannot use the function as this is a JButton, not a JLabel
        instructions.setHorizontalAlignment(0);
        instructions.setFont(new Font("Arial", Font.BOLD, 20));
        playerScores.add(instructions, BorderLayout.PAGE_START);

        place(hits, playerScores);
        place(misses, playerScores);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(playerScores, BorderLayout.PAGE_START);
        frame.setSize(600,600);
        // Exit the program when the "X" button is clicked
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Show the window
        frame.setVisible(true);
    }

    /***
     * Used to place JLabels with less repeated code
     * @param label Label to place.
     * @param panel Panel to place label within.
     ***/
    public static void place(JLabel label, JPanel panel){
        label.setHorizontalAlignment(0);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(label, BorderLayout.PAGE_START);
    }

    /***
     * This function is used to disable the remaining buttons after the user wins.
     * @param buttons Button array given to disable all buttons on the board after the game ends.
     ***/
    public static void removeEnable(JButton[][] buttons){
        for(byte i = 0; i < buttons.length; i++) {
            for (byte j = 0; j < buttons[i].length; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    /***
     * This is used to create images for the buttons, it reduces the amount of code re-used throughout the file.
     * @param filename Name of file displayed on GUI.
     * @param width Desired width for image (can be changed).
     * @param height Desired height for image (can be changed).
     * @return Returns the ImageIcon object to be assigned to a button.
     ***/
    public ImageIcon findImage(String filename, int width, int height){
        ImageIcon image = new ImageIcon(filename);
        Image imageImg = image.getImage();
        // Set width & height based on parameters given (adjustable)
        imageImg = imageImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        image = new ImageIcon(imageImg);
        return image;
    }

    public class openInstructions implements ActionListener{
        @Override
        /**
         * When the "Instructions" button is clicked, this method is called and create a new window displaying the instructions.
         **/
        public void actionPerformed(ActionEvent e) {
            JFrame frame = new JFrame("Battleship Instructions");
            JPanel panel = new JPanel();
            // Create the instructions using HTML (bullet quotes & indentation)
            JLabel instructions = new JLabel("<html><blockquote><h2>Battleship Instructions</h2></blockquote>\n" +
                    "<ul>\n" +
                    "<li>Try to find the ships hiding under the water.</li>\n" +
                    "<li>Click a water block to reveal if a ship is located there.</li>\n" +
                    "<li>If you miss, a rock will be revealed.</li>\n" +
                    "<li>Jet Skis will always have another boat very close by.</li>\n" +
                    "<li>Your score is counted by how many shots you miss.</li>\n" +
                    "</ul>\n" +
                    "<blockquote><h4>Have Fun!</h4></blockquote>\n<html>");
            frame.add(panel, BorderLayout.CENTER);
            frame.add(instructions, BorderLayout.CENTER);
            frame.setSize(500,300);
            frame.setVisible(true);
        }
    }

    public class onClick implements ActionListener{
        @Override
        /**
         * Check if the button clicked is a boat or a rock (blank) and display this to the user.
         **/
        public void actionPerformed(ActionEvent e) {
            JButton b = (JButton) e.getSource();

            // If the button clicked is one of two Jet Skis...
            if (b.getActionCommand().contains("Double")){
                // Change the image to a Jet Ski
                ImageIcon jetski = findImage("jetski.png", 60, 60);
                b.setIcon(jetski);

                // Do not let the button be clicked anymore
                b.setEnabled(false);

                // Increase the hit count label on the GUI
                hitCount++;
                hits.setText("Hits: " + hitCount);

                // If every boat has been hit, let the user know
                if (hitCount == numBoats) {
                    hits.setText("You win! You missed " + missCount + " shots!");
                    // Call the removeEnable function with the buttons list to disable the rest of the buttons (after the player wins)
                    removeEnable(buttons);
                }
            } else{
                // If the button is not a boat
                if (b.getActionCommand().contains("null")){
                    ImageIcon rock = findImage("rock.png", 60, 60);
                    b.setEnabled(false);
                    b.setIcon(rock);

                    // Increase the miss count label on the GUI
                    missCount++;
                    misses.setText("Misses: " + missCount);
                } else{
                    // If the button clicked is a regular battleship (single boat)
                    ImageIcon ship = findImage("battleship.png", 60, 60);
                    b.setIcon(ship);
                    b.setEnabled(false);

                    hitCount++;
                    hits.setText("Hits: " + hitCount);

                    if (hitCount == numBoats) {
                        hits.setText("You win! You missed " + missCount + " shots!");
                        removeEnable(buttons);
                    }
                }
            }
        }
    }
}