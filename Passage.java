/**
* The Passage class represents a text file.
*
* @author Kathleen Wong

**/
import java.util.*;
import java.io.File;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.Hashtable;
import java.io.FileNotFoundException;
import java.lang.Math;

public class Passage {
  private String t;
  private File f;
  private File s;
  private int wordCount;
  private static Hashtable<String,Double> similarTitles;
  private Hashtable<String,Integer> h;
  private Set<String> stopWords;

  /**
  * Builds a Passage
  * @param title
  *   title of passage
  * @param file
  *   file of passage
  * @param stop
  *   file containing stop words
  **/
  public Passage(String title, File file, File stop){
    h = new Hashtable<String,Integer>();
    wordCount = 0;
    f = file;
    s = stop;
    t = title;
    stopWords = new HashSet<String>();
    similarTitles = new Hashtable<String,Double>();
    try{
      Scanner scan = new Scanner(s);
      while(scan.hasNextLine()){
        String line = scan.nextLine();
        String sWords[] = line.split("\\s+");
        for(int i = 0; i < sWords.length; i++){
          stopWords.add(sWords[i]);
        }
      }
      parseFile(file);
    }
    catch(FileNotFoundException e){}
    }

    /**
    * Gets title of passage
    * @return title of passage
    **/
    public String getTitle(){
      return t;
    }

    /**
    * Sets title of passage
    * @param title
    *    title of passage
    **/
    public void setTitle(String title){
      title = title;
    }

    /**
    * Gets word count of passage
    * @return word count
    **/
    public int getWordCount(){
      return wordCount;
    }

    /**
    * Sets word count of passage
    * @param wordCount
    *    word count of passage
    **/
    public void setWordCount(int wordCount){
      wordCount = wordCount;
    }

    /**
    * Gets similartitles hashtable of passage
    * @return similartitles
    **/
    public Hashtable<String,Double> getSimilarTitles(){
      return similarTitles;
    }

    /**
    * Sets similartitles hashtable of passage
    * @param newHash
    *    new hashtable
    **/
    public void setSimilarTitles(Hashtable<String,Double> newHash){
      similarTitles = newHash;
    }

    /**
    * Gets h hashtable of passage
    * @return h
    **/
    public Hashtable<String,Integer> getH(){
      return h;
    }

    /**
    * creates hashtable of words and frequency for file
    * @param file
    *   file to parse
    **/
    public void parseFile(File file){
      Set<String> words = getWords();
      for(String w : words){
        int freq = countFrequency(w);
        wordCount += freq;
        h.put(w,freq);
      }
    }

    /**
    * Counts occurences of a word in passage
    * @param passage1
    *   first passage to compare
    * @param passage2
    *   second passage to compare
    * @return
    *  cosine similarity
    **/
    public static double cosineSimilarity(Passage passage1, Passage passage2){
      double firstV = 0;
      double secondV = 0;
      double sum = 0;
      Hashtable<String,Integer> firstH= passage1.getH();
      Hashtable<String,Integer> secondH= passage2.getH();
      double wc1 = passage1.getWordCount();
      double wc2 = passage2.getWordCount();
      double u =0;
      double v =0;

      Set<String> keys = new HashSet<String>();
      keys.addAll(firstH.keySet());
      keys.addAll(secondH.keySet());

      for(String word : keys){
        if(firstH.get(word) == null){
          u = 0;
        }
        else{
          u = firstH.get(word)/wc1;
        }
        if(secondH.get(word) == null){
          v = 0;
        }
        else{
          v = secondH.get(word)/wc2;
        }
        sum += u*v;
        firstV += Math.pow(u,2);
        secondV += Math.pow(v,2);
      }

      double percent = sum / (Math.sqrt(firstV) * Math.sqrt(secondV));
      double rounded = Math.floor(percent * 100) /100;
      similarTitles.put(passage2.getTitle(),rounded);
      return percent;
    }

    /**
    * Counts occurences of a word in passage
    * @param word
    *   word
    * @return
    *  frequency of word
    * @exception FileNotFoundException
    *    Indicates that the passage does not refer to a file
    **/
    public int countFrequency(String word){
      word = word.toLowerCase();
      int count = 0;
      try{
        Scanner scan = new Scanner(f);
        while(scan.hasNextLine()){
          String line = scan.nextLine();
          String words[] = line.split("\\s+");
          for(int i = 0; i < words.length; i++){
            words[i] = words[i].replaceAll("[^a-zA-Z]", "").toLowerCase();
            if(word.equals(words[i])){
              count ++;
            }
          }
        }

      }
      catch(FileNotFoundException e){
      }
      return count;
    }

    /**
    * Gets word frequency of a word
    * @param word
    *   word
    * @return
    *  frequency of word divided by word count
    **/
    public double getWordFrequency(String word){
      return (h.get(word))/wordCount;
    }

    /**
    * Stringifies similar tables hashmap
    * @return
    *   set containing alll words in passage
    * @exception FileNotFoundException
    *    Indicates that the passage does not refer to a file
    **/
    public Set<String> getWords(){
      Set<String> set = new HashSet<>();
      try{
        Scanner scan = new Scanner(f);
        while(scan.hasNextLine()){
          String line = scan.nextLine();
          String words[] = line.split("\\s+");
          for(int i = 0; i < words.length; i++){
            words[i] = words[i].replaceAll("[^a-zA-Z]", "").toLowerCase();
            if(!set.contains(words[i]) && !stopWords.contains(words[i]) && !words[i].equals("")){
              set.add(words[i]);
            }
          }
        }
      }
      catch(FileNotFoundException e){
      }
      return set;
    }

    /**
    * Stringifies similar tables hashmap
    * @return
    *   similar titles and percentages
    **/
    public String toString(){
      String retStr = "| ";
      int newLine = 0;
      for (String titles: similarTitles.keySet()){
        if(!t.equals(titles)){
          if(newLine == 2){
            newLine = 0;
            retStr += "\n" ;
            for(int i = 0; i < 25 ; i ++){
              retStr+= " ";
            }
            retStr += "| ";
          }
          double val = similarTitles.get(titles);
          String valStr = Double.toString(val).substring(Double.toString(val).indexOf(".") + 1);
          retStr += (titles + " (" + valStr + "%), ");
          newLine ++;
        }
      }
      return retStr;
    }
  }
