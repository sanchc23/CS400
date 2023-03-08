//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: P05 BPTree
// Files: BPTree
// Course: CS400, Fall Semester, 2019
//
// Author: Salvatore Mingari
// Email: mingari@wisc.edu
// Lecturer's Name: Deb Deppeler
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// Persons: Aaron chamberlain (highschool teacher)
//          I used the binary search method my ap-cs teacher showed me in high
//          school
// Online Sources: https://www.javatpoint.com/b-tree
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * Implementation of a B+ tree to allow efficient access to
 * many different indexes of a large data set. 
 * BPTree objects are created for each type of index
 * needed by the program.  BPTrees provide an efficient
 * range search as compared to other types of data structures
 * due to the ability to perform log_m N lookups and
 * linear in-order traversals of the data items.
 * 
 * @author sapan (sapan@cs.wisc.edu)
 *
 * @param <K> key - expect a string that is the type of id for each item
 * @param <V> value - expect a user-defined type that stores all data for a food item
 */
public class BPTree<K extends Comparable<K>, V> implements BPTreeADT<K, V> {

  // Root of the tree
  private Node root;

  // Branching factor is the number of children nodes 
  // for internal nodes of the tree
  private int branchingFactor;
  private int size; // holds the number of key value pairs inserted


  /**
   * Public constructor
   * 
   * @param branchingFactor 
   */
  public BPTree(int branchingFactor) {
    if (branchingFactor <= 2) {
      throw new IllegalArgumentException(
          "Illegal branching factor: " + branchingFactor);
    }
    root = new LeafNode();  // at begin the root is a leaf
    this.branchingFactor = branchingFactor;
    size = 0; 
  }


  /*
   * (non-Javadoc)
   * @see BPTreeADT#insert(java.lang.Object, java.lang.Object)
   */
  @Override
  public void insert(K key, V value) {
    if(key == null) { // check that input is valid
      throw new IllegalArgumentException();
    }
    root.insert(key, value); 
    this.size += 1; // increase size for each insert
  }


  /*
   * (non-Javadoc)
   * @see BPTreeADT#rangeSearch(java.lang.Object, java.lang.String)
   */
  @Override
  public List<V> rangeSearch(K key, String comparator) {
    // checks that if comparator is wrong an empty list will be returned
    if (!comparator.contentEquals(">=") && 
        !comparator.contentEquals("==") && 
        !comparator.contentEquals("<=") ) {
      return new ArrayList<V>();
    }  
    return root.rangeSearch(key, comparator); // calls search on the root
  }

  /*
   * (non-Javadoc)
   * @see BPTreeADT#get(java.lang.Object)
   */
  @Override
  public V get(K key) {
    return root.get(key); // call get method for either leaf or internal
  }

  /*
   * (non-Javadoc)
   * @see BPTreeADT#size()
   */
  @Override
  public int size() {
    return this.size;  // returns size field
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    Queue<List<Node>> queue = new LinkedList<List<Node>>();
    queue.add(Arrays.asList(root));
    StringBuilder sb = new StringBuilder();
    while (!queue.isEmpty()) {
      Queue<List<Node>> nextQueue = new LinkedList<List<Node>>();
      while (!queue.isEmpty()) {
        List<Node> nodes = queue.remove();
        sb.append('{');
        Iterator<Node> it = nodes.iterator();
        while (it.hasNext()) {
          Node node = it.next();
          sb.append(node.toString());
          if (it.hasNext())
            sb.append(", ");
          if (node instanceof BPTree.InternalNode)
            nextQueue.add(((InternalNode) node).children);
        }
        sb.append('}');
        if (!queue.isEmpty())
          sb.append(", ");
        else {
          sb.append('\n');
        }
      }
      queue = nextQueue;
    }
    return sb.toString();
  }


  /**
   * This abstract class represents any type of node in the tree
   * This class is a super class of the LeafNode and InternalNode types.
   * 
   * @author sapan
   */
  private abstract class Node {

    // List of keys
    List<K> keys;

    /**
     * Package constructor
     */
    Node() {
      this.keys = new ArrayList<K>(); // Initialize to an empty list
    }


    // Method learned in AP-CS in HighSchool
    // Credit to Aaron Chamberlain; my high school teacher
    int binarySearch(List<K> arr, K key) {      
      int low = 0;
      int high = arr.size() - 1;
      while(low <= high){
        int middle = (low + high) >>> 1;
      if(key.compareTo(arr.get(middle)) < 0){
        high = middle - 1;
      }
      else if(key.compareTo(arr.get(middle)) > 0){
        low = middle +1;
      }
      else return middle;
        }   
        return -(low + 1); // if value is never found it will return the location of where it would go
      }

    /**
     * Returns the size of the node
     * @return
     */
    int NumKeys() {
      return keys.size(); 
    }


    /**
     * Inserts key and value in the appropriate leaf node 
     * and balances the tree if required by splitting
     *  
     * @param key
     * @param value
     */
    abstract void insert(K key, V value);


    abstract V get(K key);

    /**
     * Gets the first leaf key of the tree
     * 
     * @return key
     */
    abstract K getFirstLeafKey();

    /**
     * Gets the new sibling created after splitting the node
     * 
     * @return Node
     */
    abstract Node split();


    /*
     * (non-Javadoc)
     * @see BPTree#rangeSearch(java.lang.Object, java.lang.String)
     */
    abstract List<V> rangeSearch(K key, String comparator);

    /**
     * 
     * @return boolean
     */
    abstract boolean isOverflow();

    public String toString() {
      return keys.toString();
    }

  } // End of abstract class Node

  /**
   * This class represents an internal node of the tree.
   * This class is a concrete sub class of the abstract Node class
   * and provides implementation of the operations
   * required for internal (non-leaf) nodes.
   * 
   * @author sapan
   */
  private class InternalNode extends Node {

    // List of children nodes
    List<Node> children;

    /**
     * Package constructor
     * makes a call to the super constructor
     * instantiate a new children list
     */
    InternalNode() {
      super();
      this.children = new ArrayList<Node>();
    }

    /**
     * (non-Javadoc)
     * @see BPTree.Node#getFirstLeafKey()
     */
    K getFirstLeafKey() {
      return children.get(0).getFirstLeafKey(); // goes to the first child
    }

    /**
     * (non-Javadoc)
     * @see BPTree.Node#isOverflow()
     */
    boolean isOverflow() {
      return children.size() > branchingFactor; // checks that branching factor isn't broken
    }

    /**
     * (non-Javadoc)
     * @see BPTree.Node#insert(java.lang.Comparable, java.lang.Object)
     */
    void insert(K key, V value) {
      Node childToInsert = getChild(key); // find the leaf where the new key should go
      childToInsert.insert(key, value); // insert the new key

      if(childToInsert.isOverflow()) {  // need to check that overflow did not occur
        Node newNode = childToInsert.split();
        insertChild(newNode.getFirstLeafKey(), newNode);
      }

      if(root.isOverflow()) {
        Node newNode = split();  // split internal
        InternalNode newRoot = new InternalNode();
        newRoot.keys.add(newNode.getFirstLeafKey());  // add keys to new root
        newRoot.children.add(this);
        newRoot.children.add(newNode); // add children to root
        root = newRoot;
      }
    }

    void insertChild(K key, Node child) {
      int loc = binarySearch(keys, key); // binary search to find location
      int childIndex;
      if(loc >= 0) { // location exists so set value to child
        childIndex = loc + 1;
        children.set(childIndex, child);
      }
      else {
        childIndex = -loc - 1; // location does not exist so correct the index
        keys.add(childIndex, key); // then add the key and child
        children.add(childIndex + 1, child);
      }
    }


    /**
     * (non-Javadoc)
     * @see BPTree.Node#split()
     */
    Node split() {
      InternalNode newNode = new InternalNode();
      int previous = (NumKeys() + 1) / 2;
      int end = NumKeys();

      // get values from old node and split to new node
      for(int index = previous; index < end; index++ ) {
        newNode.keys.add(keys.get(index));  
        newNode.children.add(children.get(index));
      }
      newNode.children.add(children.get(end)); // adds the far right child

      int range = end - previous ;
      keys.remove(previous - 1);  // to account for the duplicate in the far right of internal node

      // removes the keys and children after the values are added in new node
      while(range != 0) {
        keys.remove(previous - 1);
        children.remove(previous);
        range --;
      }
      children.remove(previous); // removes the extra child
      return newNode;

    }

    /**
     * (non-Javadoc)
     * @see BPTree.Node#rangeSearch(java.lang.Comparable, java.lang.String)
     */
    List<V> rangeSearch(K key, String comparator) {
        return children.get(0).rangeSearch(key, comparator); // start search at first leaf value
    }

    @Override
    V get(K key) {
      return getChild(key).get(key); // uses binary search method get to find location
    }

    /*
     * Binary search method to get the and return the passed key
     * A binary search is used to get the correct node
     */
    Node getChild(K key) {
      int location = binarySearch(keys, key);
      int index;

      if(location >= 0) {
        index = location + 1;
      }
      else {
        index = (-location) - 1;
      }
      return children.get(index);
    }
  } 
  
  
  /**
   * This class represents a leaf node of the tree.
   * This class is a concrete sub class of the abstract Node class
   * and provides implementation of the operations that
   * required for leaf nodes.
   * 
   * @author sapan
   */
  private class LeafNode extends Node {

    // List of values
    List<V> values;

    // Reference to the next leaf node
    LeafNode next;

    // Reference to the previous leaf node
    LeafNode previous;

    /**
     * Package constructor
     */
    LeafNode() {
      super(); // call to super constructor is made 
      values = new ArrayList<V>();
    }


    /**
     * (non-Javadoc)
     * @see BPTree.Node#getFirstLeafKey()
     */
    K getFirstLeafKey() {
      return keys.get(0); // returns first value of the arraylist
    }

    /**
     * (non-Javadoc)
     * @see BPTree.Node#isOverflow()
     */
    boolean isOverflow() {
      return values.size() > branchingFactor - 1;
    }

    /**
     * (non-Javadoc)
     * @see BPTree.Node#insert(Comparable, Object)  /// leaf
     */
    void insert(K key, V value) {
      int location = binarySearch(keys, key);
      int index = location;
      if(location < 0) {
        index = -location - 1;
      }

      if(location >= 0) {
        keys.add(index, key);
        values.add(index, value);
      }
      else { // tree does not have a key w/ this value in it yet
        keys.add(index, key);
        values.add(index, value);
      }
      if(root.isOverflow()) {
        Node newNode = split(); // fix root overflow
        InternalNode newRoot = new InternalNode();
        newRoot.keys.add(newNode.getFirstLeafKey());
        newRoot.children.add(this);
        newRoot.children.add(newNode);
        root = newRoot;
      }

    }

    /**
     * (non-Javadoc)
     * @see BPTree.Node#split()
     */
    Node split() {
      LeafNode newNode = new LeafNode();
      int previous = (NumKeys() + 1) / 2;
      int end = NumKeys();

      for(int index = previous; index < end; index++ ) {
        newNode.keys.add(keys.get(index));  // get values from old node and split to new node
        newNode.values.add(values.get(index));
      }

      // removes all previous keys and values that were added
      int range = end - previous ;
      while(range != 0) {
        keys.remove(previous);
        values.remove(previous);
        range --;
      }

      // sets the next field to make the structure a b+ tree
      newNode.next = next;
      next = newNode;
      return newNode;
    }

    /**
     * (non-Javadoc)
     * @see BPTree.Node#rangeSearch(Comparable, String)
     */
    List<V> rangeSearch(K key, String comparator) {

      List<V> searched = new ArrayList<>();
      LeafNode currentLeafNode = this;

      while (currentLeafNode != null) { // all leafs are linked so when we reach the end of the linked nodes we are done
        Iterator<K> keyIterator = currentLeafNode.keys.iterator();
        Iterator<V> valueIterator = currentLeafNode.values.iterator();

        while(keyIterator.hasNext()) {
          K keyI = keyIterator.next();  // iterators for both lists
          V valueI = valueIterator.next();

          int compare = keyI.compareTo(key);  // compare the key        
          if(comparator.equals("==") && compare == 0) { 
            searched.add(valueI);
          }

          // inclusive for the = by checking for compare == 0
          else if ((comparator.equals(">=") && compare == 1)||
              (comparator.equals(">=") && compare == 0)) {
            searched.add(valueI);
          }
          else if ((comparator.equals("<=") && compare == -1) ||
              (comparator.equals("<=") && compare == 0)){
            searched.add(valueI);
          } 
        }
        // set current leaf to the next leaf
        currentLeafNode = currentLeafNode.next;
      }
      return searched;
    }


    @Override
    V get(K key) {
      int location =  binarySearch(keys, key); // call binary search method
      if(location >= 0) {
        return values.get(location);
      }
      return null; // if key doesn't exist return null
    }

  } // End of class LeafNode


    /**
     * Contains a basic test scenario for a BPTree instance.
     * It shows a simple example of the use of this class
     * and its related types.
     * 
     * @param args
     */
    public static void main(String[] args) {
      // create empty BPTree with branching factor of 3
      BPTree<Double, Double> bpTree = new BPTree<>(3);
  
      // create a pseudo random number generator
      Random rnd1 = new Random();
  
      // some value to add to the BPTree
      Double[] dd = {0.0d, 0.5d, 0.2d, 0.8d};
  
      // build an ArrayList of those value and add to BPTree also
      // allows for comparing the contents of the ArrayList 
      // against the contents and functionality of the BPTree
      // does not ensure BPTree is implemented correctly
      // just that it functions as a data structure with
      // insert, rangeSearch, and toString() working.
      List<Double> list = new ArrayList<>();
      for (int i = 0; i < 400; i++) {
        Double j = dd[rnd1.nextInt(4)];
        list.add(j);
        bpTree.insert(j, j);
        System.out.println("\n\nTree structure:\n" + bpTree.toString());
      }
      List<Double> filteredValues = bpTree.rangeSearch(0.2d, ">=");
      System.out.println("Filtered values: " + filteredValues.toString());
    }

} // End of class BPTree