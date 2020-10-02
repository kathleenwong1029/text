/**
* The FrequencyTable class uses FrequencyList to store the frequency of
* every word across every passage.
*
* @author Kathleen Wong

**/
import java.util.ArrayList;
import java.util.Set;
import java.util.*;
import java.util.HashSet;
import java.lang.IllegalArgumentException;
public class FrequencyTable extends ArrayList<FrequencyList>{

  private static ArrayList<Passage> pList; //table to store lists
  private static FrequencyTable table;

  /**
  * Builds the frequencytable
  * @param passages
  *   arraylist containing all passages
  * @return frequencytable
  **/
  public static FrequencyTable buildTable(ArrayList<Passage> passages){
    pList = passages;
    for(int i = 0; i < passages.size(); i++){
      Set<String> words = passages.get(i).getWords();
      Iterator iter = words.iterator();
      while(iter.hasNext()){
        String word = (String)iter.next();
        if(!table.contains(word)){
          FrequencyList w = new FrequencyList(word, passages);
          table.add(w);
        }
      }
    }
    return table;
  }

  /**
  * Adds a passage to frequencytable
  * @param p
  *    passage to add
  * @exception IllegalArgumentException
  *    Indicates that the passage is null or empty.
  **/
  public void addPassage(Passage p) throws IllegalArgumentException{
    if(p == null){
      throw new IllegalArgumentException("Passage doesn't exist");
    }
    Iterator iter = table.iterator();
    while(iter.hasNext()){
      FrequencyList n = (FrequencyList)iter.next();
      n.addPassage(p);
    }
  }

  /**
  * Gets the frequency of a given word in a specific passage
  * @param word
  *    word to look for
  * @param p
  *    passage to look in
  * @return frequency of word in passage
  **/
  public int getFrequency(String word,Passage p){
    FrequencyList lookup = new FrequencyList(word,pList);
    int index = 0;
    Iterator iter = table.iterator();
    while(iter.hasNext()){
      index ++;
      FrequencyList n = (FrequencyList)iter.next();
      if(word.equals(n.getWord())){
        lookup = table.get(index - 1);
      }
    }
    int ret = lookup.getFrequency(p);
    return ret;
}
}
