/**
 * An implementation of the AutoCompleteInterface using a DLB Trie.
 * jrh229
 * JHeinzBurger
 * anno Domini 2023
 */

public class AutoComplete implements AutoCompleteInterface {

  private DLBNode root;
  private StringBuilder currentPrefix;
  private DLBNode currentNode;
  private int addindex;
  private DLBNode[] added;
  private DLBNode advancenextnode;
  private DLBNode prevaddnode;
  private int maxdepth;
  private int prefixlength;
  private int cNodelength;
  private int depth;
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
      if(word.length()==0)                                      // throws IllegalArgumentException if word is the empty string
        throw new IllegalArgumentException();
      int length = word.length();
      boolean alreadyexists = true;

      if(length > maxdepth){                                    //Sets new deepest length
        maxdepth = length;
      }
      added = new DLBNode[length];                              //Creates backtracking array
      DLBNode newroot = new DLBNode(word.charAt(0));
      boolean isAlready = true;
      if(root==null){                                         //If root does not exist (new)
          root = newroot;
        }
      currentNode = root;
      depth = 0;
      addindex = 0;                                           //Counter

      while(addindex<length){

        char Phillies = word.charAt(addindex);                //Char of string at index[addindex]
        DLBNode Bohm = new DLBNode(Phillies);                 //DLB node created using 
        //printTrie(root, maxdepth);
        if(currentNode==null){                                //If there is nothing in this slot
          
            Bohm.parent=prevaddnode;
            prevaddnode.child = Bohm;
            currentNode = Bohm;
            currentNode.size++;
        }
        else if(currentNode.data==Bohm.data){
          currentNode.size++;

        }
        else{
          if(currentNode.nextSibling==null){
            Bohm.previousSibling=currentNode;
            currentNode.nextSibling=Bohm;
            Bohm.size++;
            currentNode = Bohm;
          }
          else{
            DLBNode Stott = currentNode.nextSibling;      //New Potential Sibling
                boolean VORWARTS = true;
                while(VORWARTS){                                    //We check the siblings till we reach either the correct one, or a null in which we make a new one

                if(Stott.data==Bohm.data){                         //If Praise be to God we find the right sibling

                  VORWARTS = false;                               //KILL THE LOOPING
                  Stott.size++;
                  currentNode = Bohm;
                }

                else if(Stott.nextSibling==null){                      //If we find the edge, which means we gotta make a new Sibling
                  Bohm.previousSibling=Stott;
                  Stott.nextSibling=Bohm;
                  Bohm.size++;
                  VORWARTS = false;                               //STOP LOOPING
                  currentNode = Bohm;
                  }
                else{                                             //KEEP GOING
                  Stott = Stott.nextSibling;
                }
              }

          }
        }
        if(addindex==length-1){
          if(currentNode.isWord){
            isAlready = false;
          }
          else{
          currentNode.isWord=true;
          isAlready = true;
          }
        }
        addindex++;
        prevaddnode = currentNode;
        currentNode=currentNode.child;
        }
        return isAlready;
      }




      //return alreadyexists;
    

    public void REVERSE(){
      for(int a = 0; a<added.length;a++){
        DLBNode alfredo = added[a];
        int minus = alfredo.size;
        alfredo.size =minus-1;
      }
    }
  /**
   * appends the character c to the current prefix in O(alphabet size) time.
   * This method doesn't modify the dictionary.
   * @param c: the character to append
   * @return true if the current prefix after appending c is a prefix to a word
   * in the dictionary and false otherwise
   */
    public boolean advance(char c){
      printTrie(root, maxdepth);
      boolean ispre = false;
      currentPrefix.append(c);
      if(prefixlength==0){            //We are at root
        currentNode = root;
        
      }
      else{
        currentNode =advancenextnode;
      }
      //System.out.println("Current Node:" + currentNode.data);
      if(currentNode.data==c){
        cNodelength++;
        ispre = true;
        if(currentNode.child!=null){
          advancenextnode=currentNode.child;
        }
      }
      else if(currentNode.nextSibling!=null){
        DLBNode Stott = currentNode.nextSibling;      //New Potential Sibling
                boolean VORWARTS = true;
                while(VORWARTS){                                    //We check the siblings till we reach either the correct one, or a null in which we make a new one
                
                if(Stott.nextSibling==null){                      //If we find the edge, which means we gotta make a new Sibling
                  ispre = false;                             //Cur node = new DLB
                  VORWARTS = false;                               //STOP LOOPING
                  }
                else if(Stott.data==c){                         //If Praise be to God we find the right sibling
                  advancenextnode = Stott;                            //Curr node is now this sibling
                  VORWARTS = false;                               //KILL THE LOOPING
                  cNodelength++;
                  ispre = true;
                }
                else{                                             //KEEP GOING
                  Stott = Stott.nextSibling;
                }
              }
      }
      prefixlength++;
      return ispre;
    }

  /**
   * removes the last character from the current prefix in O(alphabet size) time. This 
   * method doesn't modify the dictionary.
   * @throws IllegalStateException if the current prefix is the empty string
   */
    public void retreat(){
      if(currentNode==root){
        throw new IllegalStateException();
      }
      if(currentPrefix.length()==0){
        throw new IllegalStateException();
      }
      currentPrefix.deleteCharAt(currentPrefix.length() - 1);
      if(cNodelength!=prefixlength){
        prefixlength--;
      }
      else{
        if(currentNode.parent==null){                                             //If currnode has no parents go all the way back to whichever prev sibling has a parent
          boolean RUNYOUFOOLS = true;
          DLBNode Gandalf = currentNode.previousSibling;

          while(RUNYOUFOOLS){
            if(Gandalf.parent!=null){
              currentNode=Gandalf.parent;
              RUNYOUFOOLS=false;
            }
            else{
              Gandalf=Gandalf.previousSibling;
            }
          }
        }
      else{
        currentNode = currentNode.parent;
      }
      prefixlength--;
      cNodelength--;
    }
      //System.out.println(currentNode.data);
    }

  /**
   * resets the current prefix to the empty string in O(1) time
   */
    public void reset(){
      currentPrefix.setLength(0);
      currentNode = root;
      cNodelength = 0;
      prefixlength = 0;
    }
    
  /**
   * @return true if the current prefix is a word in the dictionary and false
   * otherwise. The running time is O(1).
   */
    public boolean isWord(){
      //printTrie(root, maxdepth);
      //System.out.println(cNodelength);
      //System.out.println(prefixlength);
      return(cNodelength==prefixlength);
    }

  /**
   * adds the current prefix as a word to the dictionary (if not already a word)
   * The running time is O(alphabet size*length of the current prefix). 
   */
    public void add(){
     add(currentPrefix.toString());
      cNodelength = prefixlength;
      //printTrie(root, maxdepth);
      
      
    }

  /** 
   * @return the number of words in the dictionary that start with the current 
   * prefix (including the current prefix if it is a word). The running time is 
   * O(1).
   */
    public int getNumberOfPredictions(){
      //System.out.println("NumberOfPredictions:" + currentNode.size);
      //System.out.println("CNodeLength:" + cNodelength);
      //System.out.println("prefixlength:" + prefixlength);
      if(cNodelength==prefixlength){
        return currentNode.size;
      }
      else{
        return 0;
      }
    }
  
  /**
   * retrieves one word prediction for the current prefix. The running time is 
   * O(prediction.length())
   * @return a String or null if no predictions exist for the current prefix
   */
    public String retrievePrediction(){
      if(currentNode.size<1){
        return null;
      }
      if(cNodelength<prefixlength){
        return null;
      }
      if(currentNode.isWord){
        return currentPrefix.toString();
      }
      else{
        if(currentNode.child==null){
          return null;
        }
        StringBuilder temp = new StringBuilder(currentPrefix);
        DLBNode Castellanos = currentNode.child;
        boolean keepergoing = true;
        while(keepergoing){
          if(Castellanos.isWord){
            temp.append(Castellanos.data);
            return temp.toString();
          }
          else if(Castellanos.child==null){
            return null;
          }
          else{
            Castellanos=Castellanos.child;
          }
        }
      }
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