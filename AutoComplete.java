/**
 * An implementation of the AutoCompleteInterface using a DLB Trie.
 */

public class AutoComplete implements AutoCompleteInterface {

  private DLBNode root;
  private StringBuilder currentPrefix;
  private DLBNode currentNode;
  private int addindex;

  //TODO: Add more instance variables as needed

  public AutoComplete(){
    root = null;
    currentPrefix = new StringBuilder();
    currentNode = null;
  }

  /**
   * Adds a word to the dictionary in O(alphabet size*word.length()) time
   * @param word the String to be added to the dictionary
   * @return true if add is successful, false if word already exists
   * @throws IllegalArgumentException if word is the empty string
   */
    public boolean add(String word){
      //TODO: implement this method
      if(word.length()==0)
        throw new IllegalArgumentException();

      int length = word.length();
      while(addindex!=length){
        char Phillies = word.charAt(addindex);
        DLBNode Bohm = new DLBNode(Phillies);

        if(root==null){                            //If root does not exist (new)
          root = Bohm;
          currentNode = root;
        }

        else if(currentNode.child==null){               //If child dosen't exist, we straight adding down
          currentNode.child = Bohm;
          Bohm.parent = currentNode;
          currentNode = Bohm;

        }
        else if(currentNode.child!=null){                         //If there already IS a Child

          if(currentNode.child.data!=Phillies){                   //BUT its not the right letter
            DLBNode Stott = currentNode.child.nextSibling;        //New Potential Sibling
            
              boolean VORWARTS = true;
              while(VORWARTS){                                    //We check the siblings till we reach either the correct one, or a null in which we make a new one

                if(Stott.data==Phillies){                         //If Praise be to God we find the right sibling
                  currentNode = Stott;                            //Curr node is now this sibling
                  VORWARTS = false;                               //KILL THE LOOPING
                }
                if(Stott.nextSibling==null){                      //If we find the edge, which means we gotta make a new Sibling
                  Stott.nextSibling = Bohm;                       //Next Sibling = the DLB we made earlier
                  Bohm.previousSibling=Stott;                     //The next siblings prev points back to the iterator
                  currentNode = Bohm;                             //Cur node = new DLB
                  VORWARTS = false;                               //STOP LOOPING
                  }
                else{                                             //KEEP GOING
                  Stott = Stott.nextSibling;
                }

              }
            
          }

          else{                                     //It IS the right letter
            currentNode = Bohm;
          }
          
        }

        currentNode.size++;                        //Everything before this should increment a letter, wether a double up or not

        if(addindex==length-1){                    //If we just added the last letter of the string
          if(currentNode.isWord==true){            //But turns out this word already exists
            return false;                          //Too bad, already exists.
          }
          currentNode.isWord=true;                 //Dosent exist, therefore this is the  end of the new word
          return true;                             //Return baby
        }
        addindex++;                                //Keep er goin
      }
        return false;
      
    }

  /**
   * appends the character c to the current prefix in O(alphabet size) time. 
   * This method doesn't modify the dictionary.
   * @param c: the character to append
   * @return true if the current prefix after appending c is a prefix to a word 
   * in the dictionary and false otherwise
   */
    public boolean advance(char c){
      if(currentPrefix.length()==0){
        currentNode = root;
      }

      //TODO: implement this method

      return false;
    }

  /**
   * removes the last character from the current prefix in O(alphabet size) time. This 
   * method doesn't modify the dictionary.
   * @throws IllegalStateException if the current prefix is the empty string
   */
    public void retreat(){
      if(currentPrefix.length()==0){
        throw new IllegalStateException();
      }
      currentPrefix.deleteCharAt(currentPrefix.length() - 1);
      //TODO: implement this method
    }

  /**
   * resets the current prefix to the empty string in O(1) time
   */
    public void reset(){
      //TODO: implement this method
      currentPrefix.setLength(0);
      currentNode = root;
    }
    
  /**
   * @return true if the current prefix is a word in the dictionary and false
   * otherwise. The running time is O(1).
   */
    public boolean isWord(){
      //TODO: implement this method
      return currentNode.isWord;
    }

  /**
   * adds the current prefix as a word to the dictionary (if not already a word)
   * The running time is O(alphabet size*length of the current prefix). 
   */
    public void add(){
      //TODO: implement this method
      
    }

  /** 
   * @return the number of words in the dictionary that start with the current 
   * prefix (including the current prefix if it is a word). The running time is 
   * O(1).
   */
    public int getNumberOfPredictions(){
      //TODO: implement this method

      return currentNode.size;
    }
  
  /**
   * retrieves one word prediction for the current prefix. The running time is 
   * O(prediction.length())
   * @return a String or null if no predictions exist for the current prefix
   */
    public String retrievePrediction(){
      //TODO: implement this method
      return null;
    }


  /* ==============================
   * Helper methods for debugging.
   * ==============================
   */

  //print the subtrie rooted at the node at the end of the start String
  public void printTrie(String start){
    System.out.println("==================== START: DLB Trie Starting from \""+ start + "\" ====================");
    if(start.equals("")){
      printTrie(root, 0);
    } else {
      DLBNode startNode = getNode(root, start, 0);
      if(startNode != null){
        printTrie(startNode.child, 0);
      }
    }
    
    System.out.println("==================== END: DLB Trie Starting from \""+ start + "\" ====================");
  }

  //a helper method for printTrie
  private void printTrie(DLBNode node, int depth){
    if(node != null){
      for(int i=0; i<depth; i++){
        System.out.print(" ");
      }
      System.out.print(node.data);
      if(node.isWord){
        System.out.print(" *");
      }
      System.out.println(" (" + node.size + ")");
      printTrie(node.child, depth+1);
      printTrie(node.nextSibling, depth);
    }
  }

  //return a pointer to the node at the end of the start String 
  //in O(start.length() - index)
  private DLBNode getNode(DLBNode node, String start, int index){
    if(start.length() == 0){
      return node;
    }
    DLBNode result = node;
    if(node != null){
      if((index < start.length()-1) && (node.data == start.charAt(index))) {
          result = getNode(node.child, start, index+1);
      } else if((index == start.length()-1) && (node.data == start.charAt(index))) {
          result = node;
      } else {
          result = getNode(node.nextSibling, start, index);
      }
    }
    return result;
  }

  //The DLB node class
  private class DLBNode{
    private char data;
    private int size;
    private boolean isWord;
    private DLBNode nextSibling;
    private DLBNode previousSibling;
    private DLBNode child;
    private DLBNode parent;

    private DLBNode(char data){
        this.data = data;
        size = 0;
        isWord = false;
        nextSibling = previousSibling = child = parent = null;
    }
  }
}
