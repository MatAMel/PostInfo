import javax.swing.*;
import java.awt.Font;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.*;

class gui{
    //Checks if String is a number
    public static Boolean isNumber(String input){
        return input.matches("-?\\d+");
    }

    //Checks if String doesnt contain any characters other than A-Åa-å
    public static Boolean onlyLetters(String input){
        return input.matches("[a-zA-ZæøåÆØÅ]+");
    }
    
    //Scans through poststed.csv and return either poststed or postnummer
    public static String scanner(String name) throws IOException{
        List<String> lines = Files.readAllLines(Paths.get("poststed.csv"));
        Pattern re = Pattern.compile("^([0-9]{4})(;)([a-zA-ZæøåÆØÅ]*)$");
        ArrayList<String> resultat = new ArrayList<String>();
        
        //group(1) = postnummer
        //group(3) = poststed
        for (String line : lines){
            Matcher match = re.matcher(line);
            if (match.find()){
                if (isNumber(name)){
                    if (match.group(1).equals(name)){
                        resultat.add(match.group(3));
                    }
                }
                else{
                    if (match.group(3).equals(name.toUpperCase()))
                        resultat.add(match.group(1));
                }
            }
        }
        
        if (resultat.size() > 1){
            StringBuilder langtResultat = new StringBuilder();
            for(int i=0; i<resultat.size(); i++){
                langtResultat.append(resultat.get(i) + ",");
            }
            //Returns every postnummer, formated to be comma-seperated and removes the last comma
            return langtResultat.toString().substring(0, langtResultat.toString().length()-1);
        }
        else if (resultat.size() == 1){
            return resultat.get(0);
        }
        else
            return "Ingen Resultat";

    }

    
    public static void createGUI(){
        JFrame frame = new JFrame("Postnummer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(280*2,140*2);
        

        JTextField postnummer = new JTextField();
        postnummer.setBounds(128*2,25*2,120*2,20*2);
        frame.getContentPane().add(postnummer);
        postnummer.setFont(new Font("Dialog", Font.PLAIN, 32));
        
        JLabel postnummerNavn = new JLabel("Postnummer");
        postnummerNavn.setBounds(25*2, 25*2, 100*2, 14*2);
        frame.getContentPane().add(postnummerNavn);
        postnummerNavn.setFont(new Font("Dialog", Font.PLAIN, 32));

        JTextField poststed = new JTextField();
        poststed.setBounds(128*2,50*2,120*2,20*2);
        frame.getContentPane().add(poststed);
        poststed.setFont(new Font("Dialog", Font.PLAIN, 32));

        JLabel poststedNavn = new JLabel("Poststed");
        poststedNavn.setBounds(25*2, 50*2, 80*2, 14*2);
        frame.getContentPane().add(poststedNavn);
        poststedNavn.setFont(new Font("Dialog", Font.PLAIN, 32));

        JButton submit = new JButton("Submit");
        submit.setBounds(30*2, 75*2, 80*2, 14*2);
        frame.getContentPane().add(submit);

        JButton clear = new JButton("Clear");
        clear.setBounds(130*2, 75*2, 80*2, 14*2);
        frame.getContentPane().add(clear);

        //When the sumbit button is pressed
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try {
                    if (isNumber(postnummer.getText()) && postnummer.getText().length() == 4){
                        poststed.setText(scanner(postnummer.getText()));
                    }
                    else{}

                    if (onlyLetters(poststed.getText())){
                        postnummer.setText(scanner(poststed.getText()));
                    }
                    else{}
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            };
        });
        //When the clear button is pressed
        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                postnummer.setText("");
                poststed.setText("");  
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
    public static void main(String args[]) throws IOException{
        createGUI();
    }
}