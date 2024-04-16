/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitcoin_price;

/**
 *
 * @author admin
 */
public class Coindata 
{
    private String date; 
    private double open; 
    private double close;
    private double low; 
    private double high; 
    private double volume;

    public Coindata () {}

    public Coindata (String date,  double open,  double high, double low,double close, double volume) 
    {     
        this.date = date;      
        this.open = open;
        this.close = close;
        this.low = low;
        this.high = high;
        this.volume = volume;
    }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

        
    public double getOpen() { return open; }
    public void setOpen(double open) { this.open = open; }

    public double getClose() { return close; }
    public void setClose(double close) { this.close = close; }

    public double getLow() { return low; }
    public void setLow(double low) { this.low = low; }

    public double getHigh() { return high; }
    public void setHigh(double high) { this.high = high; }

    public double getVolume() { return volume; }
    public void setVolume(double volume) { this.volume = volume; }
}
