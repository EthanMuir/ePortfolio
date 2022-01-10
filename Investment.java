package ePortfolio;

/**Investment superclass to define the instance variables and include methods
 * to simplify the program
 */
public class Investment{
    public String symbol;
    public String name;
    public int quantity;
    public double price;
    public double bookValue;
    public double gain;

    /** Constructor method to initialize investment */
    public Investment(){
        this.symbol = "";
        this.name = "";
        this.quantity = 0;
        this.price = 0.0;
        this.bookValue = 0.0;
        this.gain = 0.0;
    }

    /** Acessor method to get symbol */
    public String getSymbol(){
        return symbol;
    }

    /** Acessor method to get Name */
    public String getName(){
        return name;
    }

    /** Acessor method to get quantity */
    public int getQuantity(){
        return quantity;
    }

    /** Acessor method to get price */
    public double getPrice(){
        return price;
    }

    /** Acessor method to get bookValue */
    public double getBookValue(){
        return bookValue;
    }

    /** Acessor method to get gain */
    public double getGain(){
        return gain;
    }

    /** Mutator method to set symbol */
    public void setSymbol(String symbol) throws Exception{
        if(symbol.isBlank())
            throw new Exception("Invalid input for Symbol");
        else
            this.symbol = symbol;
    }

    /** Mutator method to set name */
    public void setName(String name)throws Exception{
        if(name.isBlank())
            throw new Exception("Invalid input for Name");
        else
            this.name = name;
    }

    /** Mutator method to set quantity */
    public void setQuantity(int quantity)throws Exception{
        if(quantity < 1)
            throw new Exception("Invalid input for Quantity");
        else
            this.quantity = quantity;
    }

    /** Mutator method to set price */
    public void setPrice(double price)throws Exception{
        if(price < 1)
            throw new Exception("Invalid input for Price");
        else
            this.price = price;
    }

    /** Mutator method to set bookvalue */
    public void setBookValue(double bookValue)throws Exception{
        if(bookValue < 1)
            throw new Exception("Invalid input for BookValue");
        else
            this.bookValue = bookValue;
    }

    /** Mutator method to set gain */
    public void setGain(double gain)throws Exception{
        if(gain < 1)
            throw new Exception("Invalid input for Gain");
        else
            this.gain = gain;
    }

    /** Equals method to compare two investments, returns true if the are equal
     * and false if they are not
     */
    public boolean equals(Object other){
        if(this.equals(other))
            return true;
        return false;
    }
}
