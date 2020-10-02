/**
* The TextAnalyzer class compares the similarites between texts.
*
* @author Kathleen Wong

**/
import java.util.Scanner;
import java.util.HashSet;
import java.io.File;
import java.util.ArrayList;

public class TextAnalyzer{
  FrequencyTable frequencyTable;

  /**
  * Analyzers passages.
  * @param String[] args
  *    Command-line arguments.
  **/
  public static void main(String[] args){
    ArrayList<String> titles = new ArrayList<String>();
    ArrayList<String> values = new ArrayList<String>();
    System.out.print("Enter the directory of a folder of text files: ");
    Scanner sc = new Scanner(System.in);
    String dirName = sc.nextLine();
    System.out.println("It takes a minute ... sorry for the wait.");
    File[] directoryOfFiles = new File(dirName).listFiles();
    File stop = new File(dirName + "StopWords.txt");
    ArrayList<Passage> passages = new ArrayList<Passage>();
    for(File i : directoryOfFiles){
      String title = i.getName().substring(0,i.getName().indexOf("."));
      if(!title.equals("StopWords")){
        Passage adding = new Passage(title,i,stop);
        passages.add(adding);
      }
    }

    System.out.println(String.format("%-25s%-100s","Text (title)", "| Similarites(%)"));
    double cs =0;
    for(int i = 1 ; i < passages.size(); i++){
      for(int j = 1 ; j < passages.size(); j++){
        if(i == j){
        }
        else{
          cs = passages.get(i).cosineSimilarity(passages.get(i),passages.get(j));
          if((cs> .60) && (i > j)){
            titles.add(passages.get(i).getTitle());
            titles.add(passages.get(j).getTitle());
            values.add(Double.toString(cs).substring(2,4));
          }

        }
      }
      String lines = "";
      for(int k = 0; k < 100 ; k++){
        lines += "-";
      }
      System.out.println(lines);
      System.out.println(String.format("%-25s%-100s",passages.get(i).getTitle(),passages.get(i)));
    }

    System.out.println("Suspected Texts With Same Authors");
    System.out.println("------------------------------------------------------------");
    int counter =0;
    for(int a = 0 ; a < titles.size() ; a +=2){
      System.out.println("'" + titles.get(a) + "'" + "and"  + "'" + titles.get(a+1) + "'" +
      " may have the same author" + "(" + values.get(counter) + "% similar).");
      counter ++;
    }
  }
}
