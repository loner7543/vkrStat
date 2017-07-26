
package model.Calculation;
import model.Auxiliary.TabularData;
import model.Data.Data;
import model.Data.RawData;
import model.Data.StatisticsData;
import model.Exception.LittleStatisticalDataException;

import javax.swing.*;
import java.util.ArrayList;


public class ConsiderStatistics //extends Thread
{
    private ArrayList<Data> dataSet;
    private int discretePoints;

    private ArrayList<Double> borders;
    private double minLengthBorderc;

    //    private CommutatorPanel pane;
    private RawData[] rawData;
    private ArrayList<String> fileNameList;
//    private volatile JProgressBar bar;
//    private JPanel barPane;

    private final double epsilon=0.000001;

    public  ConsiderStatistics()
    {
        dataSet=null;
        discretePoints=0;
    }

    //    public  ConsiderStatistics(CommutatorPanel pane)
//    {
//        dataSet=null;
//        discretePoints=0;
////        this.pane=pane;
//        barPane=new JPanel();
//        barPane.setLayout(new BorderLayout());
//    }
    public StatisticsData createStatisticsData(ArrayList<RawData> rawData)throws LittleStatisticalDataException
    {
        int numberRadial=1;
        dataSet=new ArrayList<Data>();
        discretePoints=rawData.get(0).getDiscretePoints();
        //double stepBar=100/(double)rawData.size();
        for(int i=0; i<rawData.size(); i++)
        {
            dataSet.add(new Converter(rawData.get(i),false).createData(false, 16, 150, numberRadial));
//            bar.setValue((int)(i*stepBar));
        }
        return this.createStatisticsData();
    }
    public StatisticsData createStatisticsData(double[] arrayAmplitudes)throws LittleStatisticalDataException
    {
        double avg=avgProfile(arrayAmplitudes);
        double sko=sko(arrayAmplitudes,avg);
        borders=calculationOfBordersInterval(arrayAmplitudes);
        int[] M=calculationFrequency(arrayAmplitudes,borders);
        double[] relativeFrequency=calculationRelativeFrequency(arrayAmplitudes.length,M);
        double KxSquare=calculationKxSquare(arrayAmplitudes.length,M,calculationTheoreticalRelativeFrequency(avg,sko,M.length));
        double levelTovalue=TabularData.checkingForNormalCurve(KxSquare,M.length);
        return new StatisticsData(0, avg, 0, sko, arrayAmplitudes, borders, minLengthBorderc, relativeFrequency, KxSquare, levelTovalue);
    }
    public StatisticsData createStatisticsData() throws LittleStatisticalDataException
    {
        double[] arrayAmplitudes=new double[dataSet.size()];
        for(int i=0; i<dataSet.size(); i++)arrayAmplitudes[i]=dataSet.get(i).getAmp();
        double point=point(dataSet,discretePoints);
        double avg=avgProfile(arrayAmplitudes);
        int closest=closest(arrayAmplitudes,avg);
        double sko=sko(arrayAmplitudes,arrayAmplitudes[closest]);
        borders=calculationOfBordersInterval(arrayAmplitudes);
        int[] M=calculationFrequency(arrayAmplitudes,borders);
        double[] relativeFrequency=calculationRelativeFrequency(arrayAmplitudes.length,M);
        double KxSquare=calculationKxSquare(arrayAmplitudes.length,M,calculationTheoreticalRelativeFrequency(avg,sko,M.length));
        double levelTovalue=TabularData.checkingForNormalCurve(KxSquare,M.length);
        return new StatisticsData(point, avg, closest, sko, arrayAmplitudes, borders, minLengthBorderc, relativeFrequency, KxSquare, levelTovalue);
    }
    public void setParameters(ArrayList<String> listFileName, JProgressBar bar)
    {
        this.fileNameList=listFileName;
//        this.bar=bar;
    }
    public void run()
    {
        ConsiderStatistics consider=this;
//        try
//        {

//            JLabel barLabel=new JLabel("Чтение файлов: ");
//            barPane.add(barLabel,BorderLayout.WEST);
//            barPane.add(bar,BorderLayout.CENTER);
//            pane.getStatisticPanel().add(barPane,BorderLayout.SOUTH);
//            pane.repaint();
        //rawData=ConsiderStatistics.readRawStatisticsData(fileNameList/*,bar*/); перенес считывание сырой статистики в класс контроллера
//            bar.setValue(0);
//            barLabel.setText("Обработка данных: ");
        //pane.getStatisticPanel().openStatisticsData(consider.createStatisticsData(rawData, bar), pane.getSizeNumerals());
//            bar.setValue(100);
    }
//        catch (IOException ex)
//        {
//            String message=ex.getMessage().substring(0,ex.getMessage().indexOf("Файл: "));
//            String name=ex.getMessage().substring(ex.getMessage().indexOf("Файл: "));
//            message=message+"\n Произошла ошибка при чтение файла.\n"+name;
////            JOptionPane.showMessageDialog(pane,message,"Error  of the format of the file", JOptionPane.ERROR_MESSAGE);
//        }
//        catch (FileFormatException ex)
//        {
//             String message=ex.getMessage().substring(0,ex.getMessage().indexOf("Файл: "));
//             String name=ex.getMessage().substring(ex.getMessage().indexOf("Файл: "));
////             message=message+"\n Произошла ошибка при чтение файла.\n"+name+"\nСодержит некоректные данные.";
//////             JOptionPane.showMessageDialog(pane,message,"Error  of the format of the file", JOptionPane.ERROR_MESSAGE);
//        }
//        finally
//        {
//            pane.setConsider(consider);
//            pane.getStatisticPanel().remove(barPane);
//            pane.getFilePanel().releasePane();
//            pane.repaint();
//        }

//    public static RawData[] readRawStatisticsData(ArrayList<String> listFileName)throws IOException,FileFormatException
//    {
//        double stepBar=100/(double)listFileName.size();
//        RawData[] rawData=new RawData[listFileName.size()];
//        for(int i=0; i<listFileName.size(); i++)
//        {
//           // rawData[i]=ReaderRawData.readData(listFileName.get(i)); счситывание реализовано в класссе контроллера
////            bar.setValue((int)(i*stepBar));
//        }
//        return rawData;
//    }

    //Погрешность в точке
    private static double point(ArrayList<Data> dataSet, int discretePoints)
    {
        double ret=Double.NEGATIVE_INFINITY;
        double reti, tmp, av;
        for(int i=0;i<discretePoints;i++)
        {
            reti=0;
            av=0;
            for(int j=0; j<dataSet.size(); j++) av+=dataSet.get(j).getH()[i];
            av=av/dataSet.size();
            for(int j=0;j<dataSet.size(); j++)
            {
                tmp=dataSet.get(j).getH()[i];
                reti+=(tmp-av)*(tmp-av);
            }
            reti=Math.sqrt(reti/dataSet.size());
            if(reti>ret)ret=reti;
        }
        return ret;
    }
    //Усреднённый профиль
    private static double avgProfile(double[] arrayAmplitudes)
    {
        double ret=0;
        for(int i=0; i<arrayAmplitudes.length; i++) ret+=arrayAmplitudes[i];
        return ret/arrayAmplitudes.length;
    }
    //Профиль, наиболее близкий к усреднённому
    private static int closest(double[] arrayAmplitudes, double avg)
    {
        int ret=-1;
        double dif=Double.POSITIVE_INFINITY;
        double tmp;
        for(int i=0; i<arrayAmplitudes.length; i++)
        {
            tmp=Math.abs(arrayAmplitudes[i]-avg);
            if(tmp<dif)
            {
                dif=tmp;
                ret=i;
            }
        }
        return ret;
    }
    //Среднеквадратическое отклонение
    private static double sko(double[] arrayAmplitudes, double avg)
    {
        double ret=0;
        double tmp;
        for(int i=0; i<arrayAmplitudes.length; i++)
        {
            tmp=arrayAmplitudes[i];
            ret+=(tmp-avg)*(tmp-avg);
        }
        return Math.sqrt(ret/(arrayAmplitudes.length-1));
    }
    //---------------------------------------Проверка гепотизы о нориальном распределении----------------------------------------------------------
    //создание отрезков borders-границы отрезков
    private ArrayList<Double> calculationOfBordersInterval(double[] Amp)
    {
        double maxAmp=Double.NEGATIVE_INFINITY;
        double minAmp=Double.POSITIVE_INFINITY;
        for(int i=0; i<Amp.length; i++)
        {
            if(maxAmp<Amp[i])maxAmp=Amp[i];
            if(minAmp>Amp[i])minAmp=Amp[i];
        }
        int k=(int)Math.round(5*Math.log10(Amp.length));
        ArrayList<Double> border=new ArrayList<Double>();
        border.add(minAmp-epsilon);
        for(int i=1; i<=k; i++)
        {
            border.add(minAmp+((maxAmp-minAmp)*i/k));
        }
        return border;
    }
    //M[i] количетво попаданий случайной величины в заданный отрезок
    private int[] calculationFrequency(double[] Amp, ArrayList<Double> borders) throws LittleStatisticalDataException
    {
        int[] M;
        boolean flagK=false;
        do
        {
            M= new int[borders.size()-1];
            int j=0;
            for(int i=0; i<M.length; i++)
            {
                M[i]=frequency(Amp, borders.get(j+1))- frequency(Amp, borders.get(j));
                j++;
            }
            for(int i=0; i<M.length; i++)
            {
                if(M[i]<5)
                {
                    if(i==0)borders.remove(i+1);
                    else if(i==M.length-1)borders.remove(i);
                    else if(M[i-1]<=M[i+1])borders.remove(i);
                    else borders.remove(i+1);
                    flagK=true;
                    break;
                }
                else flagK=false;
            }
            if(M.length==1)flagK=false;
        }while(flagK);

        if(M.length<4) throw new LittleStatisticalDataException();

        minLengthBorderc=Double.POSITIVE_INFINITY;
        for(int i=0; i<M.length; i++)
        {
            if((borders.get(i+1)-borders.get(i))<minLengthBorderc) minLengthBorderc=borders.get(i+1)-borders.get(i);
        }

        return M;
    }
    //вероятность попадания
    private double[] calculationRelativeFrequency(int n,int[] M)
    {
        double[] relativeFrequency=new double[M.length];
        for(int i=0; i<M.length; i++)
        {
            relativeFrequency[i]=M[i]/(double)n;
        }
        return relativeFrequency;
    }
    //theoreticalRelativeFrequency-теоретическая(нормальный закон распределения) вероятность
    private double[] calculationTheoreticalRelativeFrequency(double avg, double sko, int n)
    {
        double[] theoreticalRelativeFrequency=new double[n];
        for(int i=0; i<n; i++)
        {
            double begin=(borders.get(i)+epsilon-avg)/sko;
            double end=(borders.get(i+1)-avg)/sko;
            theoreticalRelativeFrequency[i]=TabularData.getF(end)-TabularData.getF(begin);
        }
        return theoreticalRelativeFrequency;
    }
    //величина расстояния кси квадрат
    private double calculationKxSquare(int n, int[] M, double[] theoreticalRelativeFrequency)
    {
        double Z=0;
        for(int i=0; i<M.length; i++)
        {
            Z+=((M[i]-n*theoreticalRelativeFrequency[i])*(M[i]-n*theoreticalRelativeFrequency[i]))/(n*theoreticalRelativeFrequency[i]);
        }
        return Z;
    }

    //число вхождений в заданный интервал
    private static int frequency(double X[], double value)
    {
        int count=0;
        for(int i=0; i<X.length; i++)
        {
            if(value>=X[i]) count++;
        }
        return count;
    }
    //-----------------------------------------------------------------------------------------------------
    //удаление Data из dataSet
    public void removeData(int index)
    {
        dataSet.remove(index);
    }
    public ArrayList<Data> getDataSet()
    {
        return dataSet;
    }
}
