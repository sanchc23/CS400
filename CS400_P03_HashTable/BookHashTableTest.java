/**
 * Filename:   TestHashTableDeb.java
 * Project:    p3
 * Authors:    Debra Deppeler (deppeler@cs.wisc.edu)
 * 
 * Semester:   Fall 2018
 * Course:     CS400
 * 
 * Due Date:   before 10pm on 10/29
 * Version:    1.0
 * 
 * Credits:    None so far
 * 
 * Bugs:       TODO: add any known bugs, or unsolved problems here
 */

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


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/** 
 * Test HashTable class implementation to ensure that required 
 * functionality works for all cases.
 */
public class BookHashTableTest {

  // Default name of books data file
  public static final String BOOKS = "books.csv";

  // Empty hash tables that can be used by tests
  static BookHashTable bookObject;
  static ArrayList<Book> bookTable;

  static final int INIT_CAPACITY = 2;
  static final double LOAD_FACTOR_THRESHOLD = 0.49;

  static Random RNG = new Random(0);  // seeded to make results repeatable (deterministic)

  /** Create a large array of keys and matching values for use in any test */
  @BeforeAll
  public static void beforeClass() throws Exception{
    bookTable = BookParser.parse(BOOKS);
  }

  /** Initialize empty hash table to be used in each test */
  @BeforeEach
  public void setUp() throws Exception {
    // TODO: change HashTable for final solution
    bookObject = new BookHashTable(INIT_CAPACITY,LOAD_FACTOR_THRESHOLD);
  }

  /** Not much to do, just make sure that variables are reset     */
  @AfterEach
  public void tearDown() throws Exception {
    bookObject = null;
  }

  private void insertMany(ArrayList<Book> bookTable) 
      throws IllegalNullKeyException, DuplicateKeyException {
    for (int i=0; i < bookTable.size(); i++ ) {
      bookObject.insert(bookTable.get(i).getKey(), bookTable.get(i));
    }
  }

  /** IMPLEMENTED AS EXAMPLE FOR YOU
   * Tests that a HashTable is empty upon initialization
   */
  @Test
  public void test000_collision_scheme() {
    if (bookObject == null)
      fail("Gg");
    int scheme = bookObject.getCollisionResolutionScheme();
    if (scheme < 1 || scheme > 9) 
      fail("collision resolution must be indicated with 1-9");
  }


  /** IMPLEMENTED AS EXAMPLE FOR YOU
   * Tests that a HashTable is empty upon initialization
   */
  @Test
  public void test000_IsEmpty() {
    //"size with 0 entries:"
    assertEquals(0, bookObject.numKeys());
  }

  /** IMPLEMENTED AS EXAMPLE FOR YOU
   * Tests that a HashTable is not empty after adding one (key,book) pair
   * @throws DuplicateKeyException 
   * @throws IllegalNullKeyException 
   */
  @Test
  public void test001_IsNotEmpty() throws IllegalNullKeyException, DuplicateKeyException {
    bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));
    String expected = ""+1;
    //"size with one entry:"
    assertEquals(expected, ""+bookObject.numKeys());
  }

  /** IMPLEMENTED AS EXAMPLE FOR YOU 
   * Test if the hash table  will be resized after adding two (key,book) pairs
   * given the load factor is 0.49 and initial capacity to be 2.
   */

  @Test 
  public void test002_Resize() throws IllegalNullKeyException, DuplicateKeyException {
    bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));
    int cap1 = bookObject.getCapacity(); 
    bookObject.insert(bookTable.get(1).getKey(),bookTable.get(1));
    int cap2 = bookObject.getCapacity();
    System.out.print(cap1 + " " + cap2);
    //"size with one entry:"
    assertTrue(cap2 > cap1 & cap1 ==2);
  }

  /**
   * adds a few inserts to the hash table. enusres that the number of items
   * in the hash table is updated
   * @throws IllegalNullKeyException
   * @throws DuplicateKeyException
   */
  @Test
  public void test003_CorrectSize() throws IllegalNullKeyException, DuplicateKeyException {
    bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));
    bookObject.insert(bookTable.get(1).getKey(),bookTable.get(1));
    bookObject.insert(bookTable.get(2).getKey(),bookTable.get(2));
    bookObject.insert(bookTable.get(3).getKey(),bookTable.get(3));

    assertEquals(4, bookObject.numKeys());
    assertTrue(bookObject.remove(bookTable.get(1).getKey()));
    assertEquals(3, bookObject.numKeys());
  }

  /**
   * Tests that the constructor allows for a specific capacity and loadfactorThreashold
   */
  @Test
  public void test004_Constructor() {
    BookHashTable bookObject1 = new BookHashTable(51,0.25);
    assertEquals(51, bookObject1.getCapacity());
    assertEquals(0.25, bookObject1.getLoadFactorThreshold());

    // class default
    BookHashTable bookObject2 = new BookHashTable();
    assertEquals(101, bookObject2.getCapacity());
    assertEquals(0.75, bookObject2.getLoadFactorThreshold());
  }


  /**
   * Tests that the get method has a correct implementation on a small table
   * @throws IllegalNullKeyException
   * @throws DuplicateKeyException
   * @throws KeyNotFoundException
   */
  @Test
  public void test006_Test_Get() throws IllegalNullKeyException, DuplicateKeyException, KeyNotFoundException {
    bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));
    bookObject.insert(bookTable.get(1).getKey(),bookTable.get(1)); 
    assertEquals(bookTable.get(0), bookObject.get(bookTable.get(0).getKey()));
  }

  /**
   * Checks that get will work on a table with only one element
   * @throws IllegalNullKeyException
   * @throws DuplicateKeyException
   * @throws KeyNotFoundException
   */
  @Test
  public void test005_Insert_and_Get_on_one_element() throws 
   IllegalNullKeyException, DuplicateKeyException, KeyNotFoundException {
    bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));
    assertEquals(bookTable.get(0), bookObject.get(bookTable.get(0).getKey()));
  }


  /**
   * Checks that get will work on a table with numerous elements
   * @throws IllegalNullKeyException
   * @throws DuplicateKeyException
   * @throws KeyNotFoundException
   */
  @Test
  public void test007_Insert_and_Get_in_a_larger_hashtable() throws 
   IllegalNullKeyException, DuplicateKeyException, KeyNotFoundException {
    bookObject.insert(bookTable.get(1).getKey(),bookTable.get(1));
    bookObject.insert(bookTable.get(2).getKey(),bookTable.get(2));
    bookObject.insert(bookTable.get(3).getKey(),bookTable.get(3));
    bookObject.insert(bookTable.get(4).getKey(),bookTable.get(4));
    bookObject.insert(bookTable.get(5).getKey(),bookTable.get(5));
    bookObject.insert(bookTable.get(6).getKey(),bookTable.get(6));
    bookObject.insert(bookTable.get(7).getKey(),bookTable.get(7));
    bookObject.insert(bookTable.get(8).getKey(),bookTable.get(8));

    assertEquals(bookTable.get(5), bookObject.get(bookTable.get(5).getKey()));
  }

  /**
   * Tests the basic case of inserting few book and removing a single book
   * checks that key was successfully removed and size was decreased
   * @throws IllegalNullKeyException
   * @throws DuplicateKeyException
   */
  @Test
  public void test008_Remove_One_Book_in_Larger_Table() throws IllegalNullKeyException, DuplicateKeyException {
    bookObject.insert(bookTable.get(1).getKey(),bookTable.get(1));
    bookObject.insert(bookTable.get(2).getKey(),bookTable.get(2));
    bookObject.insert(bookTable.get(3).getKey(),bookTable.get(3));
    bookObject.insert(bookTable.get(4).getKey(),bookTable.get(4));
    bookObject.insert(bookTable.get(5).getKey(),bookTable.get(5));
    bookObject.insert(bookTable.get(6).getKey(),bookTable.get(6));
    bookObject.insert(bookTable.get(7).getKey(),bookTable.get(7));
    bookObject.insert(bookTable.get(8).getKey(),bookTable.get(8));
    assertTrue(bookObject.remove(bookTable.get(5).getKey()));
    try {
      bookObject.get(bookTable.get(5).getKey());
      fail();
    } catch (KeyNotFoundException e) {
      assertEquals(7, bookObject.numKeys());
    }
  }

  /**
   * Tests the basic case of inserting one book and removing a single book
   * checks that key was successfully removed and size was decreased
   * @throws IllegalNullKeyException
   * @throws DuplicateKeyException
   */
  @Test
  public void test008_Remove_One_Book() throws IllegalNullKeyException, DuplicateKeyException{
    bookObject.insert(bookTable.get(1).getKey(),bookTable.get(1));
    assertTrue(bookObject.remove(bookTable.get(1).getKey()));
    try {
      bookObject.get(bookTable.get(1).getKey());
      fail();
    } catch (KeyNotFoundException e) {
      assertEquals(0, bookObject.numKeys());
    }
  }

  /**
   * Tests that hundreds of items can be inserted and retrieved in the table
   */
  @Test
  public void test009_insert_many() throws IllegalNullKeyException, DuplicateKeyException, KeyNotFoundException {
    insertMany(bookTable);
    for(int i=0; i < bookTable.size(); i++ ) {  // Ensures each object is inserted
      assertEquals(bookTable.get(i).toString(), bookObject.get(bookTable.get(i).getKey()).toString());
    }
  }

  /**
   * Tests that the hash table works when the threshold is at max capacity
   * @throws IllegalNullKeyException
   * @throws DuplicateKeyException
   * @throws KeyNotFoundException
   */
  @Test
  public void test010_full_capacity() throws IllegalNullKeyException, DuplicateKeyException, KeyNotFoundException {
    BookHashTable bookHash = new BookHashTable(INIT_CAPACITY,1.5);
    for(int i=0; i < bookTable.size(); i++ ) {
      bookHash.insert(bookTable.get(i).getKey(), bookTable.get(i));
    }
    for(int i=0; i < bookTable.size(); i++ ) {  // Ensures each object is inserted
      assertEquals(bookTable.get(i), bookHash.get(bookTable.get(i).getKey()));
    }
  }

  /**
   * Tests that remove can remove all items from hash table
   * @throws IllegalNullKeyException
   * @throws DuplicateKeyException
   * @throws KeyNotFoundException
   */
  @Test
  public void test011_insert_and_remove_all() throws IllegalNullKeyException, DuplicateKeyException, KeyNotFoundException {
    insertMany(bookTable);
    for(int i=0; i < bookTable.size(); i++ ) {  // Ensures each object is inserted
      assertEquals(bookTable.get(i), bookObject.get(bookTable.get(i).getKey()));
    }

    for(int i=0; i < bookTable.size(); i++ ) {  // Removes all items
      assertTrue(bookObject.remove(bookTable.get(i).getKey()));
    }
    try {
      bookObject.get(bookTable.get(117).getKey());
      fail();
    } catch (KeyNotFoundException e) {
      assertEquals(0, bookObject.numKeys());
    }
  }

  /**
   * Tests that an IllegalNullKeyException is thrown if key is invalid during
   * remove
   */
  @Test
  public void test012_remove_nullkey() {
    try {
      bookObject.remove(null);
    }catch (IllegalNullKeyException e) {
      assertEquals(null, e.getMessage());
    } 
    catch (Exception e) {
      fail();
    }
  }

  /**
   * Tests that an IllegalNullKeyException is thrown if key is invalid during
   * insert
   */
  @Test
  public void test013_insert_nullkey() {
    try {
      bookObject.insert(null, null);
    }catch (IllegalNullKeyException e) {
      assertEquals(null, e.getMessage());
    } 
    catch (Exception e) {
      fail();
    }  
  }

  /**
   * Tests that an IllegalNullKeyException is thrown if key is invalid during
   * get
   */
  @Test
  public void test014_get_nullkey() {
    try {
      bookObject.get(null);
    }catch (IllegalNullKeyException e) {
      assertEquals(null, e.getMessage());
    } 
    catch (Exception e) {
      fail();
    }
  }

  /**
   * Tests that false is returned if a key is not in the table that the user
   * is trying to remove
   * @throws IllegalNullKeyException
   * @throws DuplicateKeyException
   */
  @Test
  public void test015_remove_key_not_in_table() throws IllegalNullKeyException, DuplicateKeyException {
    bookObject.insert(bookTable.get(1).getKey(),bookTable.get(1));
    bookObject.insert(bookTable.get(2).getKey(),bookTable.get(2));
    bookObject.insert(bookTable.get(3).getKey(),bookTable.get(3));
    bookObject.insert(bookTable.get(4).getKey(),bookTable.get(4));
    bookObject.insert(bookTable.get(5).getKey(),bookTable.get(5));
    bookObject.insert(bookTable.get(6).getKey(),bookTable.get(6));
    bookObject.insert(bookTable.get(7).getKey(),bookTable.get(7));
    bookObject.insert(bookTable.get(8).getKey(),bookTable.get(8));

    assertFalse(bookObject.remove(bookTable.get(10).getKey()));
  }

  /**
   * Tests that a duplicate exception is thrown if a duplicate item is added to
   * the table
   * @throws IllegalNullKeyException
   * @throws DuplicateKeyException
   */
  @Test
  public void test016_insert_Duplicate() throws IllegalNullKeyException, DuplicateKeyException {
    bookObject.insert(bookTable.get(1).getKey(),bookTable.get(1));
    bookObject.insert(bookTable.get(2).getKey(),bookTable.get(2));
    bookObject.insert(bookTable.get(3).getKey(),bookTable.get(3));
    bookObject.insert(bookTable.get(4).getKey(),bookTable.get(4));
    try {
      bookObject.insert(bookTable.get(2).getKey(),bookTable.get(2));
      fail();
    }catch (DuplicateKeyException e) {
      /* pass  */
    } catch (Exception e) {
      fail("should be duplicate");
    }    
  }

  /**
   * Tests that get throws a KeyNotFoundException if the key is not in the table
   * @throws IllegalNullKeyException
   * @throws DuplicateKeyException
   */
  @Test
  public void test017_get_not_found() throws IllegalNullKeyException, DuplicateKeyException {
    bookObject.insert(bookTable.get(1).getKey(),bookTable.get(1));
    bookObject.insert(bookTable.get(2).getKey(),bookTable.get(2));
    bookObject.insert(bookTable.get(3).getKey(),bookTable.get(3));
    bookObject.insert(bookTable.get(4).getKey(),bookTable.get(4));
    try {
      bookObject.get(bookTable.get(117).getKey());
      fail();
    }catch (KeyNotFoundException e) {
      /* pass */
    } catch (Exception e) {
      fail("should be not found");
    }
  }
  
  
  /**
   * Tests inserting two of the same books with two different keys
   * @throws IllegalNullKeyException
   * @throws DuplicateKeyException
   * @throws KeyNotFoundException 
   */
  @Test
  public void test018_dupe_books() throws IllegalNullKeyException, DuplicateKeyException, KeyNotFoundException {
    
    Book bCloneBook1 = new Book("123", "authors", "original_publication_year", "title", "language_code", "average_rating", "cover_type", "pages");
    Book bCloneBook2 = new Book("321", "authors", "original_publication_year", "title", "language_code", "average_rating", "cover_type", "pages");
 
    bookObject.insert(bCloneBook1.getKey(), bCloneBook1);
    bookObject.insert(bCloneBook2.getKey(), bCloneBook2);
   
    assertEquals(bCloneBook1, bookObject.get(bCloneBook1.getKey()));
    assertEquals(bCloneBook2, bookObject.get(bCloneBook2.getKey()));
  }
}
