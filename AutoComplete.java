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
  private int maxdepth;
  private int prefixlength;
  private int cNodelength;
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

      if(root==null){                                         //If root does not exist (new)
          root = newroot;
        }

      currentNode = root;                                     //Sets Currentnode back to root
      addindex = 0;                                           //Counter


      while(addindex<length){

        char Phillies = word.charAt(addindex);                //Char of string at index[addindex]
        DLBNode Bohm = new DLBNode(Phillies);                 //DLB node created using 

        if(addindex==0&&root.data!=Bohm.data){                //If we're at root and its not right, we gotta go to the siblings
          if(root.nextSibling==null){                         //IF the childs sibling is null
              root.nextSibling = Bohm;                        //Attach the new DLB as a sibling to root
              Bohm.previousSibling = root;
              currentNode = Bohm;
            }

            else{                                             //IF sibling is not null
              DLBNode Stott = root.nextSibling;               //The sibling node
            
              boolean VORWARTS = true;                        //Condition to keep on looking for ther ight sibling
              while(VORWARTS){                                //We check the siblings till we reach either the correct one, or a null in which we make a new one

                if(Stott.data==Bohm.data){                        //If Praise be to God we find the right sibling
                  currentNode = Stott;                            //Curr node is now this sibling
                  VORWARTS = false;                               //KILL THE LOOPING
                }
                else if(Stott.nextSibling==null){                 //If we find the edge, which means we gotta make a new Sibling
                  Stott.nextSibling = Bohm;                       //Next Sibling = the DLB we made earlier
                  Bohm.previousSibling=Stott;                     //The BLB prev points back to the iterator
                  currentNode = Bohm;                             //Curnode = new DLB
                  VORWARTS = false;                               //STOP LOOPING
                  }
                else{                                             //KEEP GOING
                  Stott = Stott.nextSibling;
                }

              }

            }
        }
        else if(currentNode.data==Bohm.data){           //If current level is the right letter 
          if((addindex>0)&&(addindex<word.length()-1)){
            if(word.charAt(addindex)==word.charAt(addindex-1)){   //BRUH WE GOT BACK TO BACK SAME CHARACTERS
              if(currentNode.child==null){                    //If there is no child 
                
                currentNode.child = Bohm;                     //Currnode's child is now the new DLB
                Bohm.parent = currentNode;                    //DLBS parent is now currnode
                currentNode = Bohm;                           //Currnode is new child

              }
              else{                                           //If there is a child
              if(currentNode.child.data!=Phillies){                   //BUT its not the right letter

                if(currentNode.child.nextSibling==null){              //IF the childs sibling is null
                  currentNode.child.nextSibling = Bohm;               //Attach a new sibling to child
                  Bohm.previousSibling = currentNode.child;
                  currentNode = Bohm;
                }

              else{                                                 //IF child sibling is not null
                DLBNode Stott = currentNode.child.nextSibling;      //New Potential Sibling
            
                boolean VORWARTS = true;
                while(VORWARTS){                                    //We check the siblings till we reach either the correct one, or a null in which we make a new one

                
                if(Stott.nextSibling==null){                      //If we find the edge, which means we gotta make a new Sibling
                  Stott.nextSibling = Bohm;                       //Next Sibling = the DLB we made earlier
                  Bohm.previousSibling=Stott;                     //The next siblings prev points back to the iterator
                  currentNode = Bohm;                             //Cur node = new DLB
                  VORWARTS = false;                               //STOP LOOPING
                  }
                else if(Stott.data==Phillies){                         //If Praise be to God we find the right sibling
                  currentNode = Stott;                            //Curr node is now this sibling
                  VORWARTS = false;                               //KILL THE LOOPING
                }
                else{                                             //KEEP GOING
                  Stott = Stott.nextSibling;
                }

              }

            }
            
            
          }
          else{                                     //If the child is the RIGHT letter, move down
            currentNode = currentNode.child;
          }
              }
            }
          }
        //THIS IS THE PROBLEM
        }

        else if(currentNode.child==null){               //If child dosen't exist, we straight adding down
          currentNode.child = Bohm;                     //Currnode's child is now the new DLB
          Bohm.parent = currentNode;                    //DLBS parent is now currnode
          currentNode = Bohm;                           //Currnode is new child

        }
        else if(currentNode.child!=null){                         //If there already IS a Child
          if(currentNode.child.data!=Phillies){                   //BUT its not the right letter

            if(currentNode.child.nextSibling==null){              //IF the childs sibling is null
              currentNode.child.nextSibling = Bohm;               //Attach a new sibling to child
              Bohm.previousSibling = currentNode.child;
              currentNode = Bohm;
            }

            else{                                                 //IF child sibling is not null
              DLBNode Stott = currentNode.child.nextSibling;      //New Potential Sibling
            
              boolean VORWARTS = true;
              while(VORWARTS){                                    //We check the siblings till we reach either the correct one, or a null in which we make a new one

                
                if(Stott.nextSibling==null){                      //If we find the edge, which means we gotta make a new Sibling
                  Stott.nextSibling = Bohm;                       //Next Sibling = the DLB we made earlier
                  Bohm.previousSibling=Stott;                     //The next siblings prev points back to the iterator
                  currentNode = Bohm;                             //Cur node = new DLB
                  VORWARTS = false;                               //STOP LOOPING
                  }
                else if(Stott.data==Phillies){                         //If Praise be to God we find the right sibling
                  currentNode = Stott;                            //Curr node is now this sibling
                  VORWARTS = false;                               //KILL THE LOOPING
                }
                else{                                             //KEEP GOING
                  Stott = Stott.nextSibling;
                }

              }

            }
            
            
          }
          else{                                     //If the child is the RIGHT letter, move down
            currentNode = currentNode.child;
          }
          
        }
        added[addindex] = currentNode;             //Add to array whichever node curr node is
        currentNode.size++;                        //Everything before this guarantees that currnode is on the correct node, be it a new one or finding the correct one

        if(addindex==length-1){                    //If we just added the last letter of the string
          if(currentNode.isWord==true){            //But turns out this word already exists
            REVERSE();
            alreadyexists = false;
            return false;                          //Too bad, already exists.
          }
          currentNode.isWord=true;                 //Dosent exist, therefore this is the  end of the new word
          return true;                             //Return baby
          
        }
        addindex++;                                //Keep er goin
      }
      

      return true;

    }
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
     
      boolean foundit = false;
      if(currentPrefix.length()==0){                                            //If we are starting from root
        currentNode = root;
        if(currentNode.data==c){
            currentPrefix.append(c);
            foundit = true;
            cNodelength++;
            
        }
      else{
          DLBNode Stott = currentNode.nextSibling;                              //Next Sibling
          boolean RIDERIDERSOFTHEODEN = false;
          if(Stott!=null){
            RIDERIDERSOFTHEODEN = true;
          }
              while(RIDERIDERSOFTHEODEN){                                   //We check the siblings till we reach either the correct one, or a null in which we make a new one
                if(Stott.data==c){                                              //If Praise be to God we find the right sibling
                  foundit = true;
                  currentNode = Stott;                                          //Curr node is now this sibling
                  currentPrefix.append(c);
                  cNodelength++;
                  
                  RIDERIDERSOFTHEODEN = false;
                  
                }
                else if(Stott.nextSibling==null){
                  RIDERIDERSOFTHEODEN = false;
                }
                else{                                                           //KEEP GOING
                  Stott = Stott.nextSibling;
                }
              }
          }
      }

      else{
        if(currentNode.child!=null){
          if(currentNode.child.data==c){
            foundit = true;
            currentNode=currentNode.child;
            currentPrefix.append(c);
            cNodelength++;
            
          }
          else{
            DLBNode Stott = currentNode.nextSibling;                              //Next Sibling
            boolean RIDERIDERSOFTHEODEN = false;
            if(Stott!=null){
              RIDERIDERSOFTHEODEN = true;
            }
              while(RIDERIDERSOFTHEODEN){                                               //We check the siblings till we reach either the correct one, or a null in which we make a new one
                if(Stott.data==c){                                              //If Praise be to God we find the right sibling
                  foundit = true;
                  currentNode = Stott;                                          //Curr node is now this sibling
                  currentPrefix.append(c);
                  cNodelength++;
                  RIDERIDERSOFTHEODEN = false;

                }
                else if(Stott.nextSibling==null){
                  RIDERIDERSOFTHEODEN = false;
                }
                else{                                                           //KEEP GOING
                  Stott = Stott.nextSibling;
                }
              }
          }
        }
      }
       prefixlength++;
      //printTrie(root, maxdepth);
      if(foundit != true){
        currentPrefix.append(c);
      }
      if(prefixlength==cNodelength){
        return true;
      }
      else{
        return false;
      }
      
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
      System.out.println(currentNode.data);
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
      System.out.println("NumberOfPredictions:" + currentNode.size);
      System.out.println("CNodeLength:" + cNodelength);
      System.out.println("prefixlength:" + prefixlength);
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
      if(cNodelength!=prefixlength){
        return null;
      }
      if(currentNode.isWord){
        return currentPrefix.toString();
      }
      else{
        if(currentNode.child==null){
          return null;
        }
        StringBuilder temp = currentPrefix;
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