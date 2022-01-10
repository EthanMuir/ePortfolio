package ePortfolio;
/**Stock class to define the instance variables and include methods
 * to simplify the program
 */
public class Stock extends Investment{

    /** toString method to print the information about the investment is an
     * easily readable way, returns the string with the information
     */
    @Override
    public String toString(){
        return "--------\n| " + this.symbol + " |\n--------\nName: " + this.name + "\nQuantity: " + this.quantity + " shares\nPrice: $" + this.price + "\nBook Value: $" + this.bookValue + "\n";
    }


    /** Buy method takes in the quantity and price, calculates the bookvalue, then adds the quantity
     * and updates the price
     */
    public void buy(int quantity, double price){
        this.bookValue += (quantity*price) + 9.99;
        this.quantity += quantity;
        this.price = price;
    }

    /**Sell partial method that takes quantity and price as parameters,
     * calculates the gain, updates the bookValue, and the quantity
     */
    public void sellPartial(int quantity, double price){
        double payment;
        double temp;
        payment = (quantity*price) - 9.99;
        
        temp = this.bookValue * (((double)quantity)/this.quantity);
        this.gain = payment - temp;
        this.bookValue -= temp;
        this.quantity -= quantity;
    }
}
