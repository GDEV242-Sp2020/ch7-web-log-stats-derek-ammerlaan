/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version    2016.02.29
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    //Where to calculate daily access counts;
    private int[] dayCounts;
    // Where monthly access counts are calculated
    private int[] monthCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer()
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        //Create array object to hold daily access counts
        dayCounts = new int[31];
        //Create array object to hold monthly access counts
        monthCounts = new int[12];
        // Create the reader to obtain the data.
        reader = new LogfileReader();
    }
    
    /**
     * Create a LogFileReader that will supply data
     * from a particular log file
     * @param logFileName The file of log data
     */
    public LogAnalyzer(String logFileName)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        //Create array object to hold daily access counts
        dayCounts = new int[31];
        //Create array object to hold monthly access counts
        monthCounts = new int[12];
        // Create the reader to obtain the data.
        reader = new LogfileReader(logFileName);
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }
    
    /**
     * Analyze the Daily access data from the log file.
     */
    public void analyzeDailyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int day = entry.getDay();
            dayCounts[day-1]++;
        }
    }
    
    /**
     * Analyze the Daily access data from the log file.
     */
    public void analyzeMonthlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int month = entry.getMonth();
            monthCounts[month-1]++;
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the daily counts.
     * These should have been initialized 
     * with a call to analyzeDailyData
     */
    public void printDailyCounts()
    {
        System.out.println("Day: Count");
        for(int day = 0; day < dayCounts.length; day++) {
            System.out.println((day + 1) + ": " + dayCounts[day]);
        }
    }
    
    /**
     * Print the monthly counts.
     * These should have been initialized 
     * with a call to analyzeMonthlyData
     */
    public void printMonthlyCounts()
    {
        System.out.println("Month: Count");
        for(int month = 0; month < monthCounts.length; month++) {
            System.out.println((month+1) + ": " + monthCounts[month]);
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
    
    /*
     * Return the number of accesses recorded in the log file.
     * @return The total number of hourly accesses
     */
    public int numberOfAccesses()
    {
        int total = 0;
        for(int i = 0; i < hourCounts.length; i++)
        {
            total += hourCounts[i];
        }
        return total;
    }
    
    /*
     * Return the number of accesses recorded in the log file.
     * @return The total number of hourly accesses
     */
    public int averageAccessesPerMonth()
    {
        int total = 0;
        for(int i = 0; i < monthCounts.length; i++)
        {
            total += monthCounts[i];
        }
        return total/12;
    }
    
    /*
     * Compare all hourly accesses and return
     * the hour with the most accesses
     * @return Te hour with the most accesses
     */
    public int busiestHour()
    {
      int busiest = 0;
      for(int i = 0; i < hourCounts.length; i++)
      {
         if(hourCounts[busiest] < hourCounts[i])
         {
             busiest = i;
         }
      }
      return busiest;
    }
    
    /*
     * Compare all hourly accesses and return
     * the hour with the least accesses
     * @return quietest hour
     */
    public int quietestHour()
    {
      int quietest = 0;
      for(int i = 0; i < hourCounts.length; i++)
      {
         if(hourCounts[quietest] > hourCounts[i])
         {
             quietest = i;
         }
      }
      return quietest;
    }
    
    /*
     * Calculte and compare all two hour period total
     * accesses and return the busiest two hour period
     * @return two hour period with the most accesses
     */
    public int busiestDoubleHour()
    {
        int busiest = 0;
        int biggestDuoTotal = 0;
        for(int i = 0; i < hourCounts.length - 1; i++)
          {
             int duoTotal = hourCounts[i] + hourCounts[i+1];
             if(duoTotal > biggestDuoTotal)
             {
                 busiest = i;
                 biggestDuoTotal = duoTotal;
             }
             
          }
        return busiest;
    }
    
    /*
     * Search through all the day total accesses and return 
     * the busiest day
     * @return The day with the most accesses
     */
    public int busiestDay()
    {
          int busiest = 0;
          for(int i = 0; i < dayCounts.length; i++)
          {
             if(dayCounts[busiest] < dayCounts[i])
             {
                 busiest = i;
             }
          }
          return busiest + 1;
    }
    
    /*
     * Search through all the daily accesses and return 
     * the quietest day
     * @return The day with the least accesses
     */
    public int quietestDay()
    {
          int quietest = 0;
          for(int i = 0; i < dayCounts.length; i++)
          {
             if(dayCounts[quietest] > dayCounts[i])
             {
                 quietest = i;
             }
          }
          return quietest + 1;
    }
    
    /*
     * Search through all the monthly accesses and return 
     * the busiest month
     * @return The month with the most accesses
     */
    public int busiestMonth()
    {
          int busiest = 0;
          for(int i = 0; i < monthCounts.length; i++)
          {
             if(monthCounts[busiest] < monthCounts[i])
             {
                 busiest = i;
             }
          }
          return busiest + 1;
    }
    
    /*
     * Search through all the monthly accesses and return 
     * the quietest month
     * @return The month with the least accesses
     */
    public int quietestMonth()
    {
          int quietest = 0;
          for(int i = 0; i < monthCounts.length; i++)
          {
             if(monthCounts[quietest] < monthCounts[i])
             {
                 quietest = i;
             }
          }
          return quietest + 1;
    }
    
    
}
