/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitcoin_price;
import java.util.ArrayList;
/**
 *
 * @author admin
 */
public class ReadData 
{
    Details dt=new Details();
    ReadData()
    {
        
    }
    
    public ArrayList<Coindata> getYearData(String yr)
    {
        ArrayList<Coindata> at=new ArrayList();
        
        for(int j=0;j<dt.CoinDataList.size();j++)
        {
            Coindata sd=dt.CoinDataList.get(j);
            if(sd.getDate().endsWith(yr))
            {
                at.add(sd);
            }
        }
        return at;
    }
    
    public ArrayList<Coindata> getMonAvgData(String yr)
    {
         ArrayList<Coindata> at=new ArrayList();
         for(int i=0;i<12;i++)
         {
            String mon=String.valueOf(i+1);
            ArrayList<Coindata> lt=getMonthData(yr,mon);
            
            String date=lt.get(0).getDate();
            
            double cavg=0;
            double oavg=0;
            double havg=0;
            double lavg=0;                    
            double vavg=0;             
            
            for(int j=0;j<lt.size();j++)
            {
                Coindata sd=lt.get(j);
                cavg=cavg+sd.getClose();
                oavg=oavg+sd.getOpen();
                havg=havg+sd.getHigh();
                lavg=lavg+sd.getLow();
                vavg=vavg+sd.getVolume();
            }
            cavg=cavg/(double)lt.size();
            oavg=oavg/(double)lt.size();
            havg=havg/(double)lt.size();
            lavg=lavg/(double)lt.size();
            vavg=vavg/(double)lt.size();
            
            Coindata sd=new Coindata(date,oavg,havg,lavg,cavg,vavg);
            at.add(sd);
         }
         return at;
    }
    public ArrayList<Coindata> getMonthData(String yr,String mon)
    {
        ArrayList<Coindata> at=new ArrayList();
        
        for(int j=0;j<dt.CoinDataList.size();j++)
        {
            Coindata sd=dt.CoinDataList.get(j);
            if(sd.getDate().endsWith(mon+"-"+yr))
            {
                at.add(sd);
            }
        }
        return at;
    }
    
    public ArrayList<Coindata> getMonData(String yr)
    {
        ArrayList<Coindata> at=new ArrayList();
        int k=1;
        for(int i=0;i<12;i++)
        {
            String mon=String.valueOf(k++);
            if(k<10)
                mon="0"+mon;            
               
            
            ArrayList<Coindata> lt=getMonthData(yr,mon);
            
            Coindata sd=lt.get(lt.size()-1);                                        
                     
            at.add(sd);
        }
        return at;
    }
    
    public double[] computeACF(String year)
    {
        //ArrayList<CoinData> at=getYearData(year);
        ArrayList<Coindata> at=getMonData(year);
        
        double y[]=new double[at.size()];
        for(int i=0;i<at.size();i++)
            y[i]=at.get(i).getClose();
        
        double ymean=0;
        for(int i=0;i<y.length;i++)
            ymean=ymean+y[i];
        ymean=ymean/(double)y.length;
        
        double sk[]=new double[10];
        for(int k=0;k<10;k++)
        {
            double sum=0;
            for(int i=k+1;i<y.length;i++)
            {
                sum=sum+((y[i]-ymean)*(y[i-k]-ymean));
            }
            sum=sum/(double)y.length;            
            sk[k]=sum;
        }
        for(int k=0;k<10;k++)
        {
            double rk=sk[k]/sk[0];
            System.out.println(rk);
        }
        return sk;
    }
}
