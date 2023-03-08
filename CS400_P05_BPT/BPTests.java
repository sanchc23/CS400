import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Random;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BPTests {

  static BPTree<String, String> idut;
  static BPTree<Integer, Integer> idut2;
  static BPTree<Double, Double> idut3;

  /** Create a large array of keys and matching values for use in any test */
  @BeforeAll
  public static void beforeClass() throws Exception{
  }

  /** Initialize empty hash table to be used in each test */
  @BeforeEach
  public void setUp() throws Exception {
    idut = new BPTree<>(3);
    idut2 = new BPTree<>(3);
    idut3 = new BPTree<>(3);
    

    // TODO: change HashTable for final solution
  }

  /** Not much to do, just make sure that variables are reset     */
  @AfterEach
  public void tearDown() throws Exception {
    idut = null;
    idut2 = null;
    idut3 = null;
  }

  
  @Test
  public void test001() {
    idut.insert("9", "9");
    idut.insert("1", "1");
    idut.insert("5", "5");

    assertEquals(3, idut.size());
    System.err.println(idut.toString());
  }
  
  @Test
  public void test002() {
    idut.insert("0", "9");
    idut.insert("1", "1");
    idut.insert("2", "2");
    idut.insert("3", "3");
    idut.insert("4", "4");
    idut.insert("5", "5");
    idut.insert("6", "6");
    //idut.insert("5", "5");

    assertEquals(7, idut.size());
    System.err.println(idut.toString());
  }
  
  
  @Test
  public void test003() {
    idut2.insert(0, 0);
    idut2.insert(1, 1);
    idut2.insert(2, 2);
    idut2.insert(3, 3);
    idut2.insert(4, 4);
    idut2.insert(5, 5);
    idut2.insert(6, 6);
    idut2.insert(7, 7);
  }

  @Test
  public void test004() {
    idut2.insert(0, 0);
    idut2.insert(1, 1);
    idut2.insert(2, 2);
    idut2.insert(3, 3);
    idut2.insert(4, 4);
    idut2.insert(5, 5);
    idut2.insert(6, 6);
    idut2.insert(7, 7);
    idut2.insert(8, 8);
    idut2.insert(9, 9);
    idut2.insert(10, 10);
    idut2.insert(11, 11);
    idut2.insert(12, 12);
    idut2.insert(13, 13);
    idut2.insert(14, 14);
    idut2.insert(15, 15);
    idut2.insert(16, 16);
    idut2.insert(17, 17);
    idut2.insert(18, 18);
    idut2.insert(19, 19);
    System.out.println(idut2.toString());
    
    System.out.println(idut2.rangeSearch(0, ">="));
    
  }
  
  @Test
  public void sequential() {
    
    idut3.insert(0.0, 0.0);
    idut3.insert(0.1, 6.0);
    idut3.insert(0.1, 8.0);
    idut3.insert(0.2, 4.0);
    idut3.insert(0.2, 17.0);
    idut3.insert(0.2, 19.0);
    idut3.insert(0.5, 9.0);
    idut3.insert(0.5, 13.0);
    idut3.insert(0.5, 16.0);
    idut3.insert(0.8, 5.0);
    idut3.insert(0.8, 7.0);
    idut3.insert(0.8, 10.0);
    idut3.insert(0.8, 11.0);
    idut3.insert(0.8, 12.0);
    idut3.insert(0.8, 14.0);
    idut3.insert(0.8, 15.0);
    idut3.insert(0.8, 18.0);
    idut3.insert(1.0, 1.0);
    idut3.insert(2.0, 2.0);
    idut3.insert(3.0, 3.0);
    
    System.out.println(idut3.toString());
    System.out.println(idut3.rangeSearch(0.1, ">="));
  }
  
}
