/**
* The FrequencyList class keeps the frequencies of a single word across
* all passages.
*
* @author Kathleen Wong

**/
import java.util.ArrayList;
import java.util.Hashtable;

public class FrequencyList{
  String word;
  ArrayList<Integer> frequencies; //arraylist containing frequencies
  Hashtable<String,Integer> passageIndices; //hashtable for passage location

  /**
  * gets the word
  * @return word
  **/
  public String getWord(){
    return word;
  }

  /**
  * Builds the frequencylist
  * @param word
  *   word
  * @param passages
  *   arraylist containing all passages
  **/
  public FrequencyList(String word, ArrayList<Passage> passages){
    word = word;
    for(int i = 0; i < passages.size(); i++){
      addPassage(passages.get(i));
    }
  }

  /**
  * Adds a passage to frequencylist
  * @param p
  *    passage to add
  **/
  public void addPassage(Passage p){
    String title = p.getTitle();
    int freq = p.countFrequency(word);
    passageIndices.put(title,frequencies.size());
    frequencies.add(freq);
  }

  /**
  * Gets the frequency of word in a specific passage
  * @param p
  *    passage to look in
  * @return frequency of word in passage
  **/
  public int getFrequency(Passage p){
    String title = p.getTitle();
    int search = passageIndices.get(title);
    return frequencies.get(search);
  }
}
