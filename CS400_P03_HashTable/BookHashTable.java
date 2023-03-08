import java.util.ArrayList;

//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: P03 Hash Table
// Files: BookHashTable.java, BookHashTableTest.java, Book.java, 
//        HashTableADT.java, BookParser.java
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
// Persons: NONE
// Online Sources: NONE
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////


// Collision Resolution Scheme: Bucket
// if open addressing: describe probe sequence 
// if buckets: describe data structure for each bucket
// 
// CHAINED BUCKET: array list of array lists
// A Chained Bucket method of array list of array lists has been used to 
// handle collisions. Each insert is placed at the arrayList bucket that 
// corresponds to the hash index of the key. If their are numerous collisions
// that occur the book is added to the end of the array list. 
// With a good table size and hash function very few collisions should take
// place so ideally each bucket will only contain a key
//
//
// The java hashCode function is being used to calculate the hash code for each 
// key.
//
// Algorithm: Each index of the string key is extracted. Then each part of the 
// key is weighted by a multiplication 31^(index). The weighed parts are then
// folded back into an integer.
//  
// sum = s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
// 
// The absolute value of the sum is then taken to ensure a positive hash index.
// then the positive sum is % with the table size so the index fits in the 
// table.

/** HashTable implementation that uses:
 * @param <K> unique comparable identifier for each <K,V> pair, may not be null
 * @param <V> associated value with a key, value may be null
 */
public class BookHashTable implements HashTableADT<String, Book> {

  /** 
   * Inner class used to create the objects stored in the hash table 
   * @author Sal
   *
   */
  private static class BookKey{
    private Book bookPair; // holds the book object  
    private String keyPair; // holds the key that is inserted in the hash table

    /**
     * BookKey constructor
     * @param book
     * @param key
     */
    private BookKey(Book book, String key) { 
      this.keyPair = key;
      this.bookPair = book;
    }
  }

  /** The initial capacity that is used if none is specified user */
  static final int DEFAULT_CAPACITY = 101;

  /** The load factor that is used if none is specified by user */
  static final double DEFAULT_LOAD_FACTOR_THRESHOLD = 0.75;


  private int tableSize;  // current capacity of hash table
  private int numKeys;    // number of items in table
  private double loadFactorThreshold; // threshold to determine when resizing is needed
  private double loadFactor; // number of items per table size
  private ArrayList<ArrayList<BookKey>> hashTable; // The storage for the hash table


  /**
   * REQUIRED default no-arg constructor
   * Uses default capacity and sets load factor threshold 
   * for the newly created hash table.
   */
  public BookHashTable() {
    this(DEFAULT_CAPACITY,DEFAULT_LOAD_FACTOR_THRESHOLD);
  }

  /**
   * Creates an empty hash table with the specified capacity 
   * and load factor.
   * @param initialCapacity number of elements table should hold at start.
   * @param loadFactorThreshold the ratio of items/capacity that causes table to resize and rehash
   */
  public BookHashTable(int initialCapacity, double loadFactorThreshold) {
    this.tableSize = initialCapacity;
    this.loadFactorThreshold = loadFactorThreshold;
    this.hashTable =  new ArrayList<ArrayList<BookKey>>(initialCapacity); // declare an arrayList of arrayLists
    this.numKeys = 0;             // no items so numKey and LF set to 0
    this.loadFactor = 0.0;

    for(int i = 0; i < tableSize; i++) {    // initialize array list to table size
      hashTable.add(new ArrayList<BookKey>());
    }
  }

  /**
   * Helper method used to calculate the loadFactor of the Hashtable
   * @return loadFactor
   */
  private double getLoadFactor() {
    return (double) this.numKeys / this.tableSize;
  }

  /**
   * Resizes the hash table by effectively doubling the size of the table.
   * Then rehashes all indexes of the previous table to the new hash table 
   */
  public void resize() {
    int newCapacity = this.tableSize * 2 + 1;  // doubled and + 1 to get prime number

    // Initialize new table with the larger capacity 
    ArrayList<ArrayList<BookKey>> newHashTable = new ArrayList<ArrayList<BookKey>>(newCapacity);
    for(int i = 0; i < newCapacity; i++) {
      newHashTable.add(new ArrayList<BookKey>());
    }
    // traverse the previous table and rehash the keys into the larger table
    for(int bucketIndex = 0; bucketIndex < this.tableSize; bucketIndex++) {
      if(!hashTable.get(bucketIndex).isEmpty()) { // adds all items in bucket to new table
        for(int index = 0; index < hashTable.get(bucketIndex).size(); index++) { 

          // calculates new hash Index
          int newHashIndex = hashFunction(hashTable.get(bucketIndex).get(index).keyPair, newCapacity);

          // inserts key into new table 
          newHashTable.get(newHashIndex).add(hashTable.get(bucketIndex).get(index));
        }
      }
    }
    this.hashTable = newHashTable; // update table and table size;
    this.tableSize = newCapacity;
  }

  /**
   * The absolute value of the java hashCode is used and then % with the capacity
   * of the hash table the value will be inserted into
   * 
   * @param key String key used to be hashed
   * @param size capacity of the hash table the index will belong to
   * @return hash index
   */
  private int hashFunction(String key, int size) {
    int sum = key.hashCode(); // call java hash code function
    sum = Math.abs(sum); // get abs of function
    return sum % size; // Ensure index fits in table
  }

  /**
   * Adds a key value pair to the hash table and increases the number of elements
   * in the hash table.
   * Calls the hash algorithm to find what index the key value pair should be 
   * inserted at. All keys are added to the last index of the bucket.
   * @param key, key index to be inserted
   * @param value, book to be inserted into table
   * @throws IllegalNullKeyException if null key is passed
   * @throws DuplicateKeyException if key is already in hash table
   */
  @Override
  public void insert(String key, Book value) throws IllegalNullKeyException, DuplicateKeyException {

    if(key == null) {  // check for valid key
      throw new IllegalNullKeyException();
    }
    // creates a BookKey object to hold the values passed in
    BookKey newBook = new BookKey(value, key);

    if(loadFactor >= loadFactorThreshold) { // checks that threshold limit is not breached
      resize();  // calls the resized method to fix threshold violations
    }
    int bucketIndex = hashFunction(newBook.keyPair, this.tableSize); // gets hash table index

    // parses each value in bucket to see if key is already in bucket
    if(!hashTable.get(bucketIndex).isEmpty()) { 
      for(int index = 0; index < hashTable.get(bucketIndex).size(); index++) {
        if(hashTable.get(bucketIndex).get(index).keyPair.equals(newBook.keyPair)){
          throw new DuplicateKeyException(); // key was found in bucket
        }
      }
    }
    hashTable.get(bucketIndex).add(newBook); // adds key value pair, increases key number
    this.numKeys += 1;
    this.loadFactor = getLoadFactor();   // gets new load factor
  }

  /**
   * Searches for a key in the hash table and if the key is found will remove
   * the key from the table and update the number of keys.
   * @param key, key to be removed
   * @throws IllegalNullKeyException if the key is null
   * @return true if the key was found and removed false otherwise
   */
  @Override
  public boolean remove(String key) throws IllegalNullKeyException {
    if(key == null) {
      throw new IllegalNullKeyException();
    }
    int bucketIndex = hashFunction(key, this.tableSize); // get hash table index of key

    if(hashTable.get(bucketIndex).isEmpty()) { // if bucket is empty key is not in the table
      return false;
    }
    else {
      // parse each index in the bucket looking for the key
      for(int index = 0; index < hashTable.get(bucketIndex).size(); index++) {
        if(hashTable.get(bucketIndex).get(index).keyPair.equals(key)) { // key was found in bucket
          hashTable.get(bucketIndex).remove(index); // remove key from bucket and decrease size
          this.numKeys -= 1;
          this.loadFactor = getLoadFactor();   // gets new load factor
          return true;
        }
      }
      return false; // key is never found so will not remove a key
    }
  }

  /**
   * Returns the value associated with the specified key
   * does not remove key or decrease number of keys.
   * @param key, key of book to be returned
   * @throws IllegalNullKeyException if a null key is passed
   * @throws KeyNotFoundException if a key is not found in the hash table
   * @return the value associated with the key passed
   */
  @Override
  public Book get(String key) throws IllegalNullKeyException, KeyNotFoundException {
    if(key == null) {
      throw new IllegalNullKeyException();
    }
    int bucketIndex = hashFunction(key, this.tableSize); // gets index of bucket to search
    if(hashTable.get(bucketIndex).isEmpty()) { // throw error since bucket is empty
      throw new KeyNotFoundException();
    }
    else { 
      // parse through each index in bucket to find key value pair
      for(int index = 0; index < hashTable.get(bucketIndex).size(); index++) {
        // key was found in current bucket
        if(hashTable.get(bucketIndex).get(index).keyPair.toString().equals(key.toString())) {         
          Book returnBook = hashTable.get(bucketIndex).get(index).bookPair;
          return returnBook;
        }
      }
      throw new KeyNotFoundException(); // key was not in bucket
    }
  }

  /**
   * @return number of items in Hash Table
   */
  @Override
  public int numKeys() {
    return this.numKeys;
  }

  /**
   * @return load factor threshold set for hash table
   */
  @Override
  public double getLoadFactorThreshold() {
    return this.loadFactorThreshold;
  }

  /**
   * @return the maximum amount of indexes in the hashTable  
   */
  @Override
  public int getCapacity() {
    return this.tableSize;
  }

  /**
   * 4 Chained Bucket: arrayList of arrayLists
   */
  @Override
  public int getCollisionResolutionScheme() {
    return 4;
  }
}