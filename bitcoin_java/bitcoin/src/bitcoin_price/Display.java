/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitcoin_price;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.jfree.chart.ChartFrame;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import org.jfree.data.statistics.BoxAndWhiskerCalculator;
import org.jfree.data.statistics.BoxAndWhiskerXYDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerXYDataset;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.CategoryPlot;
/**
 *
 * @author admin
 */
public class Display 
{
    Details dt=new Details();
    Display()
    {
        
    }
    
    public void displayChart1(ArrayList<Coindata> at,String year)
    {
        try
        {
            Date date[]=new Date[at.size()];
            double open[]=new double[at.size()];
            double close[]=new double[at.size()];
            double high[]=new double[at.size()];
            double low[]=new double[at.size()];
            double vol[]=new double[at.size()];
            
            SimpleDateFormat sf=new SimpleDateFormat("MMMM-yyyy");
            
            for(int i=0;i<at.size();i++)
            {
                Coindata sd=at.get(i);
                String sa1[]=sd.getDate().split("-");
                //date[i]=sf.parse(sd.getDate());
                //date[i]=(new Date(Integer.parseInt(sa1[0]),Integer.parseInt(sa1[1])+1,Integer.parseInt(sa1[2])+1900));
                date[i]=(new Date(Integer.parseInt(sa1[2])-1900,Integer.parseInt(sa1[1])-1,1));
                open[i]=sd.getOpen();
                close[i]=sd.getClose();
                low[i]=sd.getLow();
                high[i]=sd.getHigh();
                vol[i]=sd.getVolume();
            }
            
            DefaultHighLowDataset data = new DefaultHighLowDataset("", date, high, low, open, close,vol);
            JFreeChart chart = ChartFactory.createCandlestickChart("Candlestick for "+dt.inName+" - "+year, "Month", "Price", data, false);
            
            ChartFrame frame1=new ChartFrame("Candlestick for "+dt.inName+" - "+year,chart);
            frame1.setSize(1050, 710);        
            frame1.setVisible(true);
            frame1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void displayChart2(ArrayList<Coindata> at,String year)
    {
        try
        {
            DefaultBoxAndWhiskerXYDataset dataset = new  DefaultBoxAndWhiskerXYDataset("Coin");
            for(int i=0;i<at.size();i++)
            {
                Coindata sd=at.get(i);
                String sa1[]=sd.getDate().split("-");
                
                Date dt=(new Date(Integer.parseInt(sa1[2])-1900,Integer.parseInt(sa1[1])-1,1));
                
                List values = new ArrayList();
                values.add(sd.getHigh());
                values.add(sd.getLow());
                values.add(sd.getOpen());
                values.add(sd.getClose());               
                
                values.add(sd.getVolume());
                dataset.add(dt, BoxAndWhiskerCalculator.calculateBoxAndWhiskerStatistics(values));
            }
            
            JFreeChart chart = ChartFactory.createBoxAndWhiskerChart("Boxplot for "+dt.inName+" - "+year, "Month", "Price", dataset, true);
             ChartFrame frame1=new ChartFrame("Boxplot for "+dt.inName+" - "+year,chart);
            frame1.setSize(1050, 710);        
            frame1.setVisible(true);
            frame1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
            
    public void displayChart3(ArrayList lt)
    {
        try
        {
            XYSeries series1 = new XYSeries("Actual");
            XYSeries series2 = new XYSeries("Predicted");
            
            for(int i=0;i<lt.size();i++)
            {
                String sg[]=lt.get(i).toString().split("#");
                series1.add(i+1, Double.parseDouble(sg[0]));
                series2.add(i+1, Double.parseDouble(sg[1]));
            }
            
            XYSeriesCollection dataset = new XYSeriesCollection();
            dataset.addSeries(series1);
            dataset.addSeries(series2);
            
            JFreeChart chart = ChartFactory.createXYLineChart(
            "Performance",   
            "Month",         
            "Price",         
            dataset,         
            PlotOrientation.VERTICAL,
            true,            
            true,            
            false            
            );

        
            chart.setBackgroundPaint(Color.white);
       
            final XYPlot plot = chart.getXYPlot();
            plot.setBackgroundPaint(Color.lightGray);
            plot.setDomainGridlinePaint(Color.white);
            plot.setRangeGridlinePaint(Color.white);
        
            final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
            renderer.setSeriesLinesVisible(0, true);
            renderer.setSeriesShapesVisible(1, true);
            plot.setRenderer(renderer);
            
            ChartFrame frame1=new ChartFrame("Prediction Chart",chart);
            frame1.setSize(600,500);        
            frame1.setVisible(true);
            frame1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void displayChart4(double sk[])
    {
        try
        {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for(int i=0;i<sk.length;i++)
            {
                dataset.setValue(sk[i]/sk[0], "Lag", String.valueOf(i+1));
            }
            
            JFreeChart chart = ChartFactory.createBarChart
  
            ("ACF","", "ACF", dataset,   
             PlotOrientation.VERTICAL, true,true, false);
  
             chart.getTitle().setPaint(Color.blue);            
  
                CategoryPlot p = chart.getCategoryPlot(); 
  
                p.setRangeGridlinePaint(Color.red); 
                
                ChartFrame frame1=new ChartFrame("ACF Chart",chart);
                frame1.setSize(600,500);        
                frame1.setVisible(true);
                frame1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
