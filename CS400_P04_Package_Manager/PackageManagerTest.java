import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PackageManagerTest {
  
  static PackageManager packageManager;
  
  /** Initialize empty hash table to be used in each test */
  @BeforeEach
  public void setUp() throws Exception {
    packageManager = new PackageManager();
  }

  /** Not much to do, just make sure that variables are reset     */
  @AfterEach
  public void tearDown() throws Exception {
    packageManager = null;
  }
  
  
  /**
   * Tests that the package manager can parse the files
   * @throws FileNotFoundException
   * @throws IOException
   * @throws ParseException
   */
  @Test
  void test() throws FileNotFoundException, IOException, ParseException {
    packageManager.constructGraph("shared_dependencies.json");
    
  }

  /**
   * Test the getInstallationOrder method
   * 
   * This method fails tests. There are times where it gets empty stack errors 
   * and null pointers
   * @throws FileNotFoundException
   * @throws IOException
   * @throws ParseException
   * @throws CycleException
   * @throws PackageNotFoundException
   */
  @Test
  void test01_make_a_graph_with_packages() throws FileNotFoundException, IOException, ParseException, CycleException, PackageNotFoundException {
    packageManager.constructGraph("shared_dependencies.json");
    String[] expected = {"D","C", "B", "A"};
    Set<String> packageList = packageManager.getAllPackages();
    List<String> dut = packageManager.getInstallationOrder("A");
    for(int i = 0; i < dut.size(); i++) {
      System.out.println(dut.get(i));
      if(!dut.get(i).equals(expected[i])) {
        fail();
      }
    }
    
    try {
      PackageManager pm = new PackageManager();
      pm.constructGraph("shared_dependencies.json");
      pm.getInstallationOrder("A");
      fail();
    }catch (CycleException e) {
      // pass //
    }
  } 
  
}

