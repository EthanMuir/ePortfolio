package ePortfolio;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**Main class that contains the GUI */
public class InvestmentGUI extends JFrame{
    boolean repeat1;
    boolean exists;
    boolean endProg = false;
    double totGain = 0;
    int count = 0;

    String[] tokenizedName;
    HashMap<String, ArrayList<Integer>> hashMap = new HashMap<String, ArrayList<Integer>>();
    ArrayList<Investment> investments = new ArrayList<Investment>();
    private JPanel initPanel;
    private JPanel panel = new JPanel();
    private JLabel text, title, typeLabel, symbolLabel, nameLabel, quantityLabel,
                   priceLabel, messagesLabel, sResultsLabel, keywordsLabel, lowPriceLabel,
                   highPriceLabel, totalGainLabel,  iGainLabel;
    private JTextField symbolIn, nameIn, quantityIn, priceIn, nameKeysIn, lowPriceIn, highPriceIn;
    private JTextArea totalGainArea, messages, iGains;
    private JButton reset, buyButton, sellButton, prev, next, save, searchButton;
    private JScrollPane scrollBars;
    private JComboBox<String> typeDrop;
    private JMenu commands = new JMenu("Commands");
    private JMenuItem buy = new JMenuItem("Buy New Investments");
    private JMenuItem sell = new JMenuItem("Sell Existing Investments");
    private JMenuItem update = new JMenuItem("Update Prices of Existing Investments");
    private JMenuItem getGain = new JMenuItem("Get The Gain of Your Portfolio");
    private JMenuItem search = new JMenuItem("Search For An Investment");
    private JMenuItem exit = new JMenuItem("Quit");
    private JMenuBar menuBar = new JMenuBar();

    boolean buyTrue, sellTrue, searchTrue;
    Font header = new Font(Font.SERIF, Font.BOLD, 30);
    Font label = new Font(Font.SERIF, Font.PLAIN, 30);
    ButtonListener back = new ButtonListener();
    MenuListener ear = new MenuListener();
    String[] types = {"Stock", "MutualFund"};
    
    /**Constructor to call on the first prep method*/
    public InvestmentGUI() {
        super();
        prepareGUI();
    }
    /**Method to set up the gui and format it properly*/
    public void prepareGUI(){
        setTitle("ePortfolio");
        setSize(1000,1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Initianlize the panel
        initPanel = new JPanel();
        initPanel.setLayout(new GridLayout());

        //Adding the text to the panel
        text = new JLabel("<html>Welcome to ePortfolio.<br><br><br><br><br>Choose a command from the Commands menu to buy or sell an investment, update prices for all investments, get gain for the portfolio, search for relevant investments, or quit the program.<html>", JLabel.LEFT);
        text.setFont(header);
        add(text);

        buy.addActionListener(ear);
        sell.addActionListener(ear);
        if(investments.size() == 0){    //Only clickable if there are existing investments
            sell.setEnabled(false);
        }
        update.addActionListener(ear);
        if(investments.size() == 0){    //Only clickable if there are existing investments
            update.setEnabled(false);
        }
        getGain.addActionListener(ear);
        search.addActionListener(ear);
        if(investments.size() == 0){    //Only clickable if there are existing investments
            search.setEnabled(false);
        }
        exit.addActionListener(ear);

        commands.add(buy);
        commands.add(sell);
        commands.add(update);
        commands.add(getGain);
        commands.add(search);
        commands.add(exit);
        menuBar.add(commands);
        initPanel.add(menuBar);
        setJMenuBar(menuBar);
    }

    /**This is responsible for tracking the menu bar and 
     * reacting appropriately when a command is chosen 
     */
    private class MenuListener implements ActionListener{
        /**
         * Opens a new panel depending on the user's command
         */
        public void actionPerformed(ActionEvent e){
            String userOption = e.getActionCommand();
            buyTrue = false;
            sellTrue = false;
            searchTrue = false;
            if(userOption.compareTo("Buy New Investments") == 0){
                buyTrue = true;
                //Initializing the panel
                panel.setVisible(false);
                initPanel.remove(panel);
                panel = new JPanel();
                text.setText("");
                panel.setLayout(null);
                panel.setBackground(Color.lightGray);

                //Title of the page
                title = new JLabel("Buying an investment");
                title.setFont(header);
                title.setSize(500,35);
                title.setLocation(0,0);
                
                //Drop down box for the types
                typeDrop = new JComboBox<>(types);
                typeDrop.addActionListener(back);
                typeDrop.setSize(300,35);
                typeDrop.setLocation(220,80);

                //Text labels
                typeLabel = new JLabel("Type");
                symbolLabel = new JLabel("Symbol");
                nameLabel = new JLabel("Name");
                quantityLabel = new JLabel("Quantity");
                priceLabel = new JLabel("Price");
                messagesLabel = new JLabel("Messages");

                typeLabel.setFont(label);
                symbolLabel.setFont(label);
                nameLabel.setFont(label);
                quantityLabel.setFont(label);
                priceLabel.setFont(label);
                messagesLabel.setFont(label);

                typeLabel.setSize(150, 35);
                symbolLabel.setSize(150, 35);
                nameLabel.setSize(150, 35);
                quantityLabel.setSize(150, 35);
                priceLabel.setSize(150, 35);
                messagesLabel.setSize(150, 35);

                typeLabel.setLocation(50, 80);
                symbolLabel.setLocation(50, 140);
                nameLabel.setLocation(50, 200);
                quantityLabel.setLocation(50, 260);
                priceLabel.setLocation(50, 320);
                messagesLabel.setLocation(20, 450);

                //Text Fields
                symbolIn = new JTextField(20);
                quantityIn = new JTextField(20);
                priceIn = new JTextField(20);
                nameIn = new JTextField(20);

                symbolIn.setSize(200, 35);
                nameIn.setSize(425,35);
                quantityIn.setSize(150, 35);
                priceIn.setSize(150, 35);

                symbolIn.setLocation(220, 140);
                nameIn.setLocation(220, 200);
                quantityIn.setLocation(220, 260);
                priceIn.setLocation(220, 320);

                //Messages Area
                messages = new JTextArea();
                messages.setEditable(false);
                messages.setSize(940, 420);
                messages.setLocation(20, 500);

                //Scroll bars
                scrollBars = new JScrollPane(messages);
                scrollBars.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                scrollBars.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrollBars.setBounds(20, 500, 940, 420);

                //Buttons
                reset = new JButton("Reset");
                buyButton = new JButton("Buy");

                reset.setSize(150,60);
                buyButton.setSize(150, 60);

                reset.setLocation(750, 110);
                buyButton.setLocation(750, 260);

                reset.addActionListener(back);
                buyButton.addActionListener(back);
                
                //Adding elements to the panel
                panel.add(symbolIn);
                panel.add(quantityIn);
                panel.add(nameIn);
                panel.add(priceIn);
                panel.add(typeLabel);
                panel.add(symbolLabel);
                panel.add(nameLabel);
                panel.add(quantityLabel);
                panel.add(priceLabel);
                panel.add(messagesLabel);
                panel.add(scrollBars);
                panel.add(title);
                panel.add(typeDrop);
                panel.add(reset);
                panel.add(buyButton);
                initPanel.add(panel);
                add(initPanel);
            }
            else if(userOption.compareTo("Sell Existing Investments") == 0){
                sellTrue = true;
                //Initializing the panel
                panel.setVisible(false);
                initPanel.remove(panel);
                panel = new JPanel();
                text.setText("");
                panel.setLayout(null);
                panel.setBackground(Color.lightGray);

                //Title of the page
                title = new JLabel("Selling an investment");
                title.setFont(header);
                title.setSize(500,35);
                title.setLocation(0,0);

                //Text labels
                symbolLabel = new JLabel("Symbol");
                quantityLabel = new JLabel("Quantity");
                priceLabel = new JLabel("Price");
                messagesLabel = new JLabel("Messages");

                symbolLabel.setFont(label);
                quantityLabel.setFont(label);
                priceLabel.setFont(label);
                messagesLabel.setFont(label);

                symbolLabel.setSize(150, 35);
                quantityLabel.setSize(150, 35);
                priceLabel.setSize(150, 35);
                messagesLabel.setSize(150, 35);

                symbolLabel.setLocation(50, 140);
                quantityLabel.setLocation(50, 230);
                priceLabel.setLocation(50, 320);
                messagesLabel.setLocation(20, 450);

                //Text Fields
                symbolIn = new JTextField(20);
                quantityIn = new JTextField(20);
                priceIn = new JTextField(20);

                symbolIn.setSize(200, 35);
                quantityIn.setSize(150, 35);
                priceIn.setSize(150, 35);

                symbolIn.setLocation(220, 140);
                quantityIn.setLocation(220, 230);
                priceIn.setLocation(220, 320);

                //Messages Area
                messages = new JTextArea();
                messages.setEditable(false);
                messages.setSize(940, 420);
                messages.setLocation(20, 500);

                //Scroll bars
                scrollBars = new JScrollPane(messages);
                scrollBars.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                scrollBars.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrollBars.setBounds(20, 500, 940, 420);

                //Buttons
                reset = new JButton("Reset");
                sellButton = new JButton("Sell");

                reset.setSize(150,60);
                sellButton.setSize(150, 60);

                reset.setLocation(750, 110);
                sellButton.setLocation(750, 260);

                reset.addActionListener(back);
                sellButton.addActionListener(back);

                //Adding elements to panel
                panel.add(title);
                panel.add(symbolLabel);
                panel.add(quantityLabel);
                panel.add(priceLabel);
                panel.add(symbolIn);
                panel.add(quantityIn);
                panel.add(priceIn);
                panel.add(messagesLabel);
                panel.add(scrollBars);
                panel.add(reset);
                panel.add(sellButton);
                initPanel.add(panel);
                add(initPanel);
            }
            else if(userOption.compareTo("Update Prices of Existing Investments") == 0){
                //Initializing the panel
                panel.setVisible(false);
                initPanel.remove(panel);
                panel = new JPanel();
                text.setText("");
                panel.setLayout(null);
                panel.setBackground(Color.lightGray);

                //Title of the page
                title = new JLabel("Updating Investments");
                title.setFont(header);
                title.setSize(500,35);
                title.setLocation(0,0);

                //Text Labels
                symbolLabel = new JLabel("Symbol");
                nameLabel = new JLabel("Name");
                priceLabel = new JLabel("Price");
                messagesLabel = new JLabel("Messages");

                symbolLabel.setFont(label);
                nameLabel.setFont(label);
                priceLabel.setFont(label);
                messagesLabel.setFont(label);

                symbolLabel.setSize(150, 35);
                nameLabel.setSize(150, 35);
                priceLabel.setSize(150, 35);
                messagesLabel.setSize(150, 35);

                symbolLabel.setLocation(50, 140);
                nameLabel.setLocation(50, 230);
                priceLabel.setLocation(50, 320);
                messagesLabel.setLocation(20, 450);

                //Text Fields
                symbolIn = new JTextField(20);
                nameIn = new JTextField(20);
                priceIn = new JTextField(20);

                symbolIn.setEditable(false);
                nameIn.setEditable(false);

                symbolIn.setText(investments.get(0).getSymbol());
                nameIn.setText(investments.get(0).getName());

                symbolIn.setSize(200, 35);
                nameIn.setSize(425, 35);
                priceIn.setSize(150, 35);

                symbolIn.setLocation(220, 140);
                nameIn.setLocation(220, 230);
                priceIn.setLocation(220, 320);

                //Messages Area
                messages = new JTextArea();
                messages.setEditable(false);
                messages.setSize(940, 420);
                messages.setLocation(20, 500);

                //Scroll bars
                scrollBars = new JScrollPane(messages);
                scrollBars.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                scrollBars.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrollBars.setBounds(20, 500, 940, 420);

                //Buttons
                prev = new JButton("Prev");
                save = new JButton("Save");
                next = new JButton("Next");

                prev.setSize(150,60);
                save.setSize(150, 60);
                next.setSize(150, 60);

                prev.setLocation(750, 110);
                save.setLocation(750, 260);
                next.setLocation(750, 185);

                prev.addActionListener(back);
                save.addActionListener(back);
                next.addActionListener(back);

                //Adding elements to panel
                panel.add(title);
                panel.add(symbolLabel);
                panel.add(nameLabel);
                panel.add(priceLabel);
                panel.add(symbolIn);
                panel.add(nameIn);
                panel.add(priceIn);
                panel.add(messagesLabel);
                panel.add(scrollBars);
                panel.add(next);
                panel.add(save);
                panel.add(prev);
                initPanel.add(panel);
                add(initPanel);
            }
            else if(userOption.compareTo("Search For An Investment") == 0){
                searchTrue = true;
                //Initializing the panel
                panel.setVisible(false);
                initPanel.remove(panel);
                panel = new JPanel();
                text.setText("");
                panel.setLayout(null);
                panel.setBackground(Color.lightGray);

                //Title of the page
                title = new JLabel("Searching investments");
                title.setFont(header);
                title.setSize(500,35);
                title.setLocation(0,0);

                //Text Labels
                symbolLabel = new JLabel("Symbol");
                nameLabel = new JLabel("Name");
                sResultsLabel = new JLabel("Search results");
                keywordsLabel = new JLabel("keywords");
                lowPriceLabel = new JLabel("Low Price");
                highPriceLabel = new JLabel("High Price");

                symbolLabel.setFont(label);
                nameLabel.setFont(label);
                lowPriceLabel.setFont(label);
                highPriceLabel.setFont(label);
                keywordsLabel.setFont(label);
                sResultsLabel.setFont(label);

                symbolLabel.setSize(150, 35);
                nameLabel.setSize(150, 30);
                lowPriceLabel.setSize(150, 35);
                keywordsLabel.setSize(150, 35);
                highPriceLabel.setSize(150, 35);
                sResultsLabel.setSize(200, 35);

                symbolLabel.setLocation(50, 80);
                nameLabel.setLocation(50, 160);
                keywordsLabel.setLocation(50, 190);
                lowPriceLabel.setLocation(50, 260);
                highPriceLabel.setLocation(50, 320);
                sResultsLabel.setLocation(20, 450);

                //Text fields
                symbolIn = new JTextField(20);
                nameKeysIn = new JTextField(20);
                lowPriceIn = new JTextField(20);
                highPriceIn = new JTextField(20);

                symbolIn.setSize(200, 35);
                nameKeysIn.setSize(400, 35);
                lowPriceIn.setSize(150, 35);
                highPriceIn.setSize(150, 35);

                symbolIn.setLocation(220, 80);
                nameKeysIn.setLocation(220, 175);
                lowPriceIn.setLocation(220, 260);
                highPriceIn.setLocation(220, 320);

                //Messages Area
                messages = new JTextArea();
                messages.setEditable(false);
                messages.setSize(940, 420);
                messages.setLocation(20, 500);

                //Scroll bars
                scrollBars = new JScrollPane(messages);
                scrollBars.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                scrollBars.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrollBars.setBounds(20, 500, 940, 420);

                //Buttons
                reset = new JButton("Reset");
                searchButton = new JButton("Search");

                reset.setSize(150,60);
                searchButton.setSize(150, 60);

                reset.setLocation(750, 110);
                searchButton.setLocation(750, 260);

                reset.addActionListener(back);
                searchButton.addActionListener(back);

                //Adding elements to the panel
                panel.add(symbolLabel);
                panel.add(sResultsLabel);
                panel.add(nameLabel);
                panel.add(keywordsLabel);
                panel.add(lowPriceLabel);
                panel.add(highPriceLabel);
                panel.add(symbolIn);
                panel.add(nameKeysIn);
                panel.add(lowPriceIn);
                panel.add(highPriceIn);
                panel.add(reset);
                panel.add(searchButton);
                panel.add(title);
                panel.add(scrollBars);    
                initPanel.add(panel);
                add(initPanel);
            }
            else if(userOption.compareTo("Get The Gain of Your Portfolio") == 0){
                //Initializing the panel
                panel.setVisible(false);
                initPanel.remove(panel);
                panel = new JPanel();
                text.setText("");
                panel.setLayout(null);
                panel.setBackground(Color.lightGray);

                //Title of the page
                title = new JLabel("Getting total gain");
                title.setFont(header);
                title.setSize(500,35);
                title.setLocation(0,0);

                //Text labels
                totalGainLabel = new JLabel("Total gain");
                iGainLabel = new JLabel("Individual gains");

                totalGainLabel.setFont(label);
                iGainLabel.setFont(label);

                totalGainLabel.setSize(200, 35);
                iGainLabel.setSize(200, 35);

                totalGainLabel.setLocation(50, 80);
                iGainLabel.setLocation(20, 165);

                //Gain text area
                totalGainArea = new JTextArea();
                totalGainArea.setEditable(false);
                totalGainArea.setSize(200, 35);
                totalGainArea.setLocation(220, 80);

                //Messages Area
                iGains = new JTextArea();
                iGains.setEditable(false);
                iGains.setSize(940, 720);
                iGains.setLocation(20, 200);

                //Scroll bars
                scrollBars = new JScrollPane(iGains);
                scrollBars.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                scrollBars.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrollBars.setBounds(20, 200, 940, 720);

                //Populating the panel
                for(int i = 0; i < investments.size(); i++){
                    iGains.append(investments.get(i).getSymbol() + ": $" + String.valueOf(investments.get(i).getGain()) + "\n");
                    totGain += investments.get(i).getGain();
                }
                totalGainArea.setText(String.valueOf(totGain));

                //Adding elements to panel
                panel.add(title);
                panel.add(totalGainArea);
                panel.add(totalGainLabel);
                panel.add(scrollBars);
                //panel.add(iGains);
                panel.add(iGainLabel);
                initPanel.add(panel);
                add(initPanel);
                
            }
            else if(userOption.compareTo("Quit") == 0){
                System.exit(0);
            }
        }
    }
    /**This listens for any button press and reacts accordingly */
    private class ButtonListener implements ActionListener{
        //**This performs backend calculations and declarations when buttons are pressed */
        public void actionPerformed(ActionEvent e){
            String buttonPressed = e.getActionCommand();
            //Code for the reset buttons
            if(buttonPressed.compareTo("Reset") == 0 && buyTrue){
                symbolIn.setText("");
                nameIn.setText("");
                quantityIn.setText("");
                priceIn.setText("");
            }
            else if(buttonPressed.compareTo("Reset") == 0 && sellTrue){
                symbolIn.setText("");
                quantityIn.setText("");
                priceIn.setText("");
            }
            else if(buttonPressed.compareTo("Reset") == 0 && searchTrue){
                symbolIn.setText("");
                nameIn.setText("");
                nameKeysIn.setText("");
                lowPriceIn.setText("");
                highPriceIn.setText("");
            }

            if(buttonPressed.compareTo("Buy") == 0){
                messages.setText("");
                boolean found = false;
                boolean error = false;
                exists = false;
                String type = (String)typeDrop.getSelectedItem();
                int quantity = 0;
                String symbol = symbolIn.getText();
                String name = nameIn.getText();
                Double price = 0.0;

                if(symbol.isBlank() || name.isBlank() || quantityIn.getText().isBlank() || priceIn.getText().isBlank()){
                    messages.append("Please enter a value for all fields");
                    error = true;
                }
                if(!error){
                    try{
                        quantity = Integer.parseInt(quantityIn.getText());
                        price = Double.parseDouble(priceIn.getText());
                    }catch(NumberFormatException nfe){
                        messages.append("Error| invalid input for price or quantity");
                        error = true;
                    }
                    
                    if(!error){
                        if(type.compareTo("Stock") == 0){
                            Stock currStock = new Stock();
                            int idx = 0;
                            //Checking if there's a mutual fund with the same symbol
                            for(int i = 0; i < investments.size(); i++){
                                if (investments.get(i).getSymbol().compareToIgnoreCase(symbol) == 0){
                                    idx = i;
                                    found = true;
                                    break;  //save the index if the smbol exists
                                }
                            }
                            if(found){
                                if(investments.get(idx) instanceof MutualFund){ //check if the symbol is a mutual fund
                                    messages.append("Error| Symbol exists in MutualFunds");
                                    error = true;
                                }
                            }

                            if(!error){
                                //if the symbol already exists
                                for(int i = 0; i < investments.size(); i++){
                                    if(investments.get(i).getSymbol().compareToIgnoreCase(symbol) == 0 ){
                                        exists = true;
                                        ((Stock)investments.get(i)).buy(quantity, price);

                                        messages.append("|Buy Sucsessful|");
                                        update.setEnabled(true);
                                        sell.setEnabled(true);
                                        search.setEnabled(true);

                                        symbolIn.setText("");
                                        nameIn.setText("");
                                        quantityIn.setText("");
                                        priceIn.setText("");
                                    }
                                }
                                if(!exists){ //If the symbol doesn't already exists in the database
                                    //Initialize the variables
                                    try{
                                        currStock.setName(name);
                                        currStock.setQuantity(quantity);
                                        currStock.setPrice(price);
                                        currStock.setSymbol(symbol);
                                        currStock.setBookValue(quantity * price + 9.99);
                                    }catch(Exception k){
                                        messages.append(k.getMessage());
                                        error = true;
                                    }
                                    
                                    if(!error){
                                        //Add the stock to the ArrayList
                                        investments.add(currStock);

                                        //Adding the name to the hashMap
                                        tokenizedName = currStock.getName().toLowerCase().split("[ ]+");
                                        for(int j = 0; j < tokenizedName.length; j++){

                                            ArrayList<Integer> indecies = new ArrayList<Integer>();
                                            if(hashMap.containsKey(tokenizedName[j])){
                                                hashMap.get(tokenizedName[j]).add(investments.size() - 1);
                                            } else{        
                                                indecies.add(investments.size() - 1);
                                                hashMap.put(tokenizedName[j], indecies);
                                            }
                                        }
                                        messages.append("|Buy Sucsessful|");
                                        update.setEnabled(true);
                                        sell.setEnabled(true);
                                        search.setEnabled(true);

                                        symbolIn.setText("");
                                        nameIn.setText("");
                                        quantityIn.setText("");
                                        priceIn.setText("");
                                    }
                                    
                                }
                            }

                            
                        }
                        else if(type.compareTo("MutualFund") == 0){
                            MutualFund currMutualFund = new MutualFund();
                            int idx = 0;
                            //Checking if there's a stock fund with the same symbol
                            for(int i = 0; i < investments.size(); i++){
                                if (investments.get(i).getSymbol().compareToIgnoreCase(symbol) == 0){
                                    idx = i;
                                    found = true;
                                    break;  //save the index if the smbol exists
                                }
                            }
                            if(found){
                                if(investments.get(idx) instanceof Stock){ //check if the symbol is a stock
                                    messages.append("Error| Symbol exists in Stocks");
                                    error = true;
                                }
                            }

                            if(!error){
                                //if the symbol already exists
                                for(int i = 0; i < investments.size(); i++){
                                    if(investments.get(i).getSymbol().compareToIgnoreCase(symbol) == 0 ){
                                        exists = true;
                                        ((MutualFund)investments.get(i)).buy(quantity, price);
                                        messages.append("|Buy Sucsessful|");
                                        update.setEnabled(true);
                                        sell.setEnabled(true);
                                        search.setEnabled(true);

                                        symbolIn.setText("");
                                        nameIn.setText("");
                                        quantityIn.setText("");
                                        priceIn.setText("");
                                    }
                                    
                                }
                                if(!exists){ //If the symbol doesn't already exists in the database
                                    //Initialize the variables
                                    try{
                                        currMutualFund.setName(name);
                                        currMutualFund.setQuantity(quantity);
                                        currMutualFund.setPrice(price);
                                        currMutualFund.setSymbol(symbol);
                                        currMutualFund.setBookValue(quantity * price);
                                    }catch(Exception k){
                                        messages.append(k.getMessage());
                                        error = true;
                                    }
                                    
                                    if(!error){
                                        //Add the stock to the ArrayList
                                        investments.add(currMutualFund);

                                        //Adding the name to the hashMap
                                        tokenizedName = currMutualFund.getName().toLowerCase().split("[ ]+");
                                        for(int j = 0; j < tokenizedName.length; j++){

                                            ArrayList<Integer> indecies = new ArrayList<Integer>();
                                            if(hashMap.containsKey(tokenizedName[j])){
                                                hashMap.get(tokenizedName[j]).add(investments.size() - 1);
                                            } else{        
                                                indecies.add(investments.size() - 1);
                                                hashMap.put(tokenizedName[j], indecies);
                                            }
                                        }
                                        messages.append("|Buy Sucsessful|");
                                        update.setEnabled(true);
                                        sell.setEnabled(true);
                                        search.setEnabled(true);

                                        symbolIn.setText("");
                                        nameIn.setText("");
                                        quantityIn.setText("");
                                        priceIn.setText("");
                                    }
                                    
                                }
                            }
                        }
                    }
                }
            }
            else if(buttonPressed.compareTo("Sell") == 0){
                messages.setText("");
                boolean error = false;
                boolean stock = false;
                boolean mutualFund = false;
                String symbol = symbolIn.getText();
                int index = 0;
                int quantity = 0;
                double price = 0.0;
                

                if(symbol.isBlank() || quantityIn.getText().isBlank() || priceIn.getText().isBlank()){
                    messages.append("Please enter a value for all fields");
                    error = true;
                }
                if(!error){
                    try{
                        quantity = Integer.parseInt(quantityIn.getText());
                        price = Double.parseDouble(priceIn.getText());
                    }catch(NumberFormatException nfe){
                        messages.append("Error| invalid input for quantity or price");
                        error = true;
                    }
                    
                    if(!error){
                        //Checking to see if the symbol is a stock or mutual fund
                        for(int i = 0; i < investments.size(); i++){
                            if((investments.get(i)).getSymbol().compareToIgnoreCase(symbol) == 0 && investments.get(i) instanceof Stock){
                                stock = true;
                                index = i;
                            }
                            else if((investments.get(i)).getSymbol().compareToIgnoreCase(symbol) == 0 && investments.get(i) instanceof MutualFund){
                                mutualFund = true;
                                index = i;
                            }
                        }
                        if(stock){
                            if(quantity > (investments.get(index)).getQuantity()){ //If the user tries to buy more than they have
                                messages.append("You cannot sell more than you have");
                                error = true;
                            }
                            else if(quantity < (investments.get(index)).getQuantity()){    //If they sell less than than they have
                                ((Stock)investments.get(index)).sellPartial(quantity, price);
                            }
                            else{   //if they sell the whole stock
                                totGain += (quantity * price - 9.99) - investments.get(index).getBookValue(); //Add gain to the total now
                                investments.remove(index);   //Remove the stock from the system

                                //Correcting the hashMap
                                for(Map.Entry<String, ArrayList<Integer>> set : hashMap.entrySet()){
                                    for(int i = 0; i < set.getValue().size(); i++){
                                        if(set.getValue().get(i) == index)
                                            set.getValue().remove(i);
                                        else if(set.getValue().get(i) > index)
                                            set.getValue().set(i, set.getValue().get(i) - 1);
                                    }
                                }
                            }    
                        }
                        else if(mutualFund){
                            if(quantity > (investments.get(index)).getQuantity()){ //If the user tries to buy more than they have
                                System.out.println("You cannot sell more than you have");
                                error = true;
                            }
                            else if(quantity < (investments.get(index)).getQuantity()){    //If they sell less than than they have
                                ((MutualFund)investments.get(index)).sellPartial(quantity, price);
                            }
                            else{   //if they sell the whole stock
                                totGain += ((quantity * price - 45) - investments.get(index).getBookValue()); //Add gain to the total now
                                investments.remove(index);   //Remove the stock from the system

                                //Correcting the hashMap
                                for(Map.Entry<String, ArrayList<Integer>> set : hashMap.entrySet()){
                                    for(int i = 0; i < set.getValue().size(); i++){
                                        if(set.getValue().get(i) == index)
                                            set.getValue().remove(i);
                                        else if(set.getValue().get(i) > index)
                                            set.getValue().set(i, set.getValue().get(i) - 1);
                                    }
                                }
                            }
                        }
                        else{
                            messages.append("Error| This symbol does not exist in the database");
                            error = true;
                        }

                        if(!error){
                            messages.append("|Sold sucsessfully|");
                            symbolIn.setText("");
                            priceIn.setText("");
                            quantityIn.setText("");
                        }
                    }
                }
                
                
            }
            else if(buttonPressed.compareTo("Prev") == 0){
                if(count <= 0)  //If they press prev when they are on the first one
                    count = investments.size() - 1;
                else    
                    count--;
                
                symbolIn.setText(investments.get(count).getSymbol());
                nameIn.setText(investments.get(count).getName());
            }
            else if(buttonPressed.compareTo("Next") == 0){
                count++;
                if(count >= investments.size())  //If they press next when they are on the last one
                    count = 0;
                
                symbolIn.setText(investments.get(count).getSymbol());
                nameIn.setText(investments.get(count).getName());
            }
            else if(buttonPressed.compareTo("Save") == 0){
                messages.setText("");
                boolean error = false;
                Double price = 0.0;
                if(priceIn.getText().isBlank()){
                    messages.append("Please enter a price");
                    error = true;
                }
                if(!error){
                    try{
                        price = Double.parseDouble(priceIn.getText());
                    }catch(NumberFormatException nfe){
                        messages.append("Error| invalid input for price");
                        error = true;
                    }
                    if(!error){
                        try{
                        investments.get(count).setPrice(price);
                        }catch(Exception k){
                            messages.append(k.getMessage());
                            error = true;
                        }
                        if(!error){
                            messages.append("Price updated sucsessfully");
                            priceIn.setText("");
                        }
                    }
                    
                    
                }
                
            }
            else if(buttonPressed.compareTo("Search") == 0){
                messages.setText("");
                String symbol = symbolIn.getText();
                String keywords[] = new String[10];
                double lowNum = 0;
                double highNum = 0;
                boolean error = false;
                boolean emptySymbol = false;
                boolean emptyKeywords = false;
                boolean emptyRange = false;
                boolean searchLower = false;
                boolean searchHigher = false;
                boolean found = false;
                ArrayList<Investment> tempInvestments = new ArrayList<Investment>();

                if(symbol.isBlank()){ //If the user left blank
                    emptySymbol = true;
                }
                
                if(nameKeysIn.getText().isBlank()) //If there are no keywords
                    emptyKeywords = true;
                else
                    keywords = nameKeysIn.getText().split("[ ]+");

                
                if(lowPriceIn.getText().isBlank() && highPriceIn.getText().isBlank()){//If the user left it blank
                    emptyRange = true;
                } 
                else if(!lowPriceIn.getText().isBlank() && highPriceIn.getText().isBlank()){   //If the input is in the form num-
                    searchLower = true;
                    try{
                        lowNum = Double.parseDouble(lowPriceIn.getText());
                    }catch(NumberFormatException nfe){
                        messages.append("Error| invalid input for low price");
                        error = true;
                    }
                    
                }
                else if(lowPriceIn.getText().isBlank() && !highPriceIn.getText().isBlank()){ //If the input is in the form -num
                    searchHigher = true;
                    try{
                        highNum = Double.parseDouble(highPriceIn.getText());
                    }catch(NumberFormatException nfe){
                        messages.append("Error| invalid input for high price");
                        error = true;
                    }
                    
                }
                else{
                    try{
                        lowNum = Double.parseDouble(lowPriceIn.getText());
                        highNum = Double.parseDouble(highPriceIn.getText());
                    }catch(NumberFormatException nfe){
                        messages.append("Error| invalid input for price");
                        error = true;
                    }
                    
                    if(lowNum > highNum){
                        messages.append("Error| Low price cannot be higher than high price");
                        error = true;
                    }
                }
            
                if(!error){
                    //Making a tempList of only the investments with the right symbols if there are no keywords
                    if(emptyKeywords){
                        for(int i = 0; i < investments.size(); i++){
                            if(!emptySymbol){   //If there is a symbol to search for
                                if(investments.get(i).getSymbol().compareToIgnoreCase(symbol) == 0){
                                    tempInvestments.add(investments.get(i));  //Add the investments with the wanted symbol into the temp list
                                }
                            }
                            else{
                                tempInvestments.add(investments.get(i));
                            }
                        }
                    }
                    else{   //If there are keywords then make the temp list based off them
                        ArrayList<Integer> indecies = new ArrayList<Integer>();
                        ArrayList<ArrayList<Integer>> storeIndex = new ArrayList<ArrayList<Integer>>();

                        //Checking if each keyword is contained in the map
                        for(int i = 0; i < keywords.length; i++){
                            for(Map.Entry<String, ArrayList<Integer>> set : hashMap.entrySet()){
                                if(set.getKey().equalsIgnoreCase(keywords[i])){
                                    indecies.addAll(set.getValue());
                                    storeIndex.add(set.getValue());
                                }
                            }
                        }

                        if(keywords.length > 1){

                            //Obtaining intersecting indecies
                            for(int i = 0; i < storeIndex.size(); i++){
                                storeIndex.get(0).retainAll(storeIndex.get(i));
                            }

                            //Assigning all the indecies to the temp list
                            for(int i = 0; i < storeIndex.get(0).size(); i++){
                                tempInvestments.add(investments.get(storeIndex.get(0).get(i)));
                            }  
                        }
                        else{
                            for(int i = 0; i < indecies.size(); i++){
                                tempInvestments.add(investments.get(indecies.get(i)));
                            }  
                        }
                    }
                    
                    if(!emptyRange){
                        //Searching the tempInvestments and removing any investments that dont match the search
                        if(searchLower){
                            for(int i = 0; i < tempInvestments.size(); i++){
                                found = false;
                                if((tempInvestments.get(i)).getPrice() >= lowNum){
                                    found = true;
                                }
                                if(!found){
                                    tempInvestments.remove(i);
                                    i = 0;
                                }
                            }
                        }
                        else if(searchHigher){
                            for(int i = 0; i < tempInvestments.size(); i++){
                                found = false;
                                if((tempInvestments.get(i)).getPrice() <= highNum){
                                    found = true;
                                }
                                if(!found){    
                                    tempInvestments.remove(i);
                                    i = 0;
                                }
                            }
                        }
                        else{

                            for(int i = 0; i < tempInvestments.size(); i++){
                                found = false;

                                if((tempInvestments.get(i)).getPrice() >= lowNum && (tempInvestments.get(i)).getPrice() <= highNum){
                                    found = true;
                                }
                                if(!found){
                                    tempInvestments.remove(i);
                                    i = 0;
                                }

                            }
                        }
                    }

                    //Print what remains in the lists
                    messages.append("All the investments that matched your search:\n");
                    for(int i = 0; i < tempInvestments.size(); i++){
                        messages.append((tempInvestments.get(i)).toString());
                    }
                }
            }
        }
    }
    /**Main method to run the GUI */
    public static void main(String args[]){
        InvestmentGUI gui = new InvestmentGUI();
        gui.setVisible(true);
     }
}
