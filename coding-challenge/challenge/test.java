import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

public class test
{  
  public test()
  {
    String csvFile = "prerequisites.csv";
    BufferedReader br = null;
    BufferedReader brf = null;
    String line = "";
    String cvsSplitBy = ",";
    ArrayList<String> finList = new ArrayList<String>();    
    
    
    try
    {
      //Assigning Two dimensional Array of course and pre-requisits   
      String[][] list = new String[13][3];
      for (String[] row : list) {
        Arrays.fill(row, "");  //fill empty values as default values in the array
      }
      for (int a = 0; a < 13; a++)
        list[a][0] = String.valueOf(a);
      //Read courses.csv file into the array  
      br = new BufferedReader(new FileReader(csvFile));   
      int i = 0;
      int temp = 0;
      while ((line = br.readLine()) != null) {
        i++;
        
        if (i != 1)
        {
          String[] unit = line.split(cvsSplitBy);
          list[Integer.parseInt(unit[0])][1] += unit[1] + ",";   //combine all the prerequisits into one seperated with ',' delimiter 
          //System.out.println("UNITS [Unit= " + unit[0] + " , PreReq=" + unit[1] + "]");
        }
      }
      

      for (int j = 0; j < 13; j++) {
        list[j][2] = "n";
      }
      // for (int t = 0; t < 13; t++) {
        // System.out.println("UNITS [Unit= " + list[t][0] + " , PreReq=" + list[t][1] + "completed" + list[t][2] + "]");
      // }
      
      //finList is the final Arraylist which is having course list order to complete the enrollment
      finList = new ArrayList();
      for (int c = 0; c <= 12; c++)
      {
        if (list[c][1].equals(""))
        {
          if (c != 0)
          {
            ((ArrayList)finList).add(list[c][0]);  //Adding courses which dont have any prerequisis into the final Arraylist 
            list[c][2] = "y";   // Add flag 'y' to units which are in the final List
          }
        } 
        // else {
          // System.out.println(list[c][0] + " ---- " + list[c][1]);
        // }
      }
      
      //Infinite loop to find the order of the units for successful enrollment
      //Loop will break when no unit enters in the final List.In our case loop breaks in the third iteration
      for (;;) {
        int loopBreak = 0;
        String str = "";
        for (int count = 0; count <= 12; count++) {          

          if ((!list[count][1].equals("")) && (list[count][2].equals("n")))
          {
            str = list[count][1].substring(0, list[count][1].length() - 1);
            //System.out.println(str);
            //Search the prerequisits string for each unit matches with the units in the final array
            if(str.length()==1)
            {
                if (search(str, (ArrayList)finList) == 1)
                {
                    ((ArrayList)finList).add(list[count][0]);
                      list[count][2] = "y";  //Change flag to "yes" if the string found in te final array list
                      loopBreak = 1;
                }                
            }

            else 
            {                
                    String[] prereqs = str.split(",");           
                    int n = 0;                    
                    while (n<prereqs.length)
                    {
                      if (search(prereqs[n], (ArrayList)finList) == 0)
                        break;
                      n++;
                    }
                    
                    if (n == prereqs.length) {
                      ((ArrayList)finList).add(list[count][0]);
                      list[count][2] = "y"; //Change flag to "yes" if the string found in te final array list
                      loopBreak = 1;
                    }                
            }            
          }
        }        

        if (loopBreak == 0) //If no unit enters the final list,Infinite loop will break
         break;        
      }
      
       //Read the courses.csv file to display the names of the units in the output
       String[][] unitName = new String[13][2];
       brf = new BufferedReader(new FileReader("courses.csv"));
       int flp=0;
            while ((line = brf.readLine()) != null) {
                // use comma as separator
               String[] units = line.split(",");
                //System.out.println("Country [Unit= " + units[0] + " , name=" + units[1] + "]");
                unitName[flp][0] = units[0];
                unitName[flp][1] = units[1];
                flp++;
            }
      
      //Final Output screen
      for (int lp = 0; lp < finList.size(); lp++) {
          System.out.println(unitName[Integer.parseInt(finList.get(lp))][0]+" -> "+unitName[Integer.parseInt(finList.get(lp))][1]);
      }      
      System.out.println(finList); 
      return;

    }
    catch (FileNotFoundException e)
    {

      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    finally
    {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
        
  }

  //Search function to check whether the unit is in the final Arraylist or not 
  public int search(String prereq, ArrayList<String> list)
  {
    for (int i = 0; i < list.size(); i++)
    {
      if (((String)list.get(i)).equals(prereq)) {
        return 1;
      }
    }
    return 0;
  }
  
  
  public static void main(String[] args)
  {
    test obj = new test();
  }
  
  
  
}