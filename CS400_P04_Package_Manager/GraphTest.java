import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GraphTest {

  static Graph graph;

  /** Initialize empty hash table to be used in each test */
  @BeforeEach
  public void setUp() throws Exception {
    graph = new Graph();
  }

  /** Not much to do, just make sure that variables are reset     */
  @AfterEach
  public void tearDown() throws Exception {
    graph = null;
  }


  /*
   * Tests the constructor and default fields
   */
  @Test
  void test00_Makes_an_empty_graph(){
    assertEquals(0, graph.size());
    assertEquals(0, graph.order());
  }

  /**
   * Tests that adding to the graph works
   */
  @Test
  void test01_can_add_to_graph() {
    graph.addVertex("A");
    assertEquals(1, graph.order());
    assertEquals(0, graph.size());

    graph.addVertex("B");
    assertEquals(2, graph.order());
    assertEquals(0, graph.size());
  }

  /**
   * check that the graph does not allow duplicates
   */
  @Test
  void test02_add_does_not_at_dupes() {
    graph.addVertex("A");
    graph.addVertex("A");
    assertEquals(1, graph.order());
  }

  /**
   * test a vertex can be removed
   */
  @Test
  void test03_remove_vertex_basic() {
    graph.addVertex("A");
    graph.getAllVertices();
    graph.removeVertex("A");
    graph.getAllVertices();
  }

  /**
   * Test that an edge can be added between vertices
   */
  @Test
  void test04_add_edge() {
    graph.addVertex("A");
    graph.addVertex("B");
    graph.addEdge("A", "B");
    graph.addEdge("A", "C");
    graph.addEdge("A", "D");
    graph.addEdge("A", "E");
    assertEquals(4, graph.size());
    assertEquals(5, graph.order());

    graph.addEdge("B", "D");
    graph.addEdge("B", "A");
    graph.addEdge("B", "C");
    graph.addEdge("C", "D");
    graph.addEdge("E", "D");
    assertEquals(9, graph.size());
    assertEquals(5, graph.order());

    List<String> aSet = graph.getAdjacentVerticesOf("A");
    List<String> expectedAList = new ArrayList<>(Arrays.asList(
        "B","C","D","E"));
    assertTrue(aSet.containsAll(expectedAList));

    List<String> bSet = graph.getAdjacentVerticesOf("B");
    List<String> expectedBList = new ArrayList<>(Arrays.asList(
        "D","A","C"));
    assertTrue(bSet.containsAll(expectedBList));

  }

  /**
   * Test that duplicate edges can not be added
   */
  @Test
  void test05_add_edge_when_edge_exsits() {
    graph.addVertex("A");
    graph.addVertex("B");
    graph.addEdge("A", "B");
    graph.addEdge("A", "B");

    List<String> aSet = graph.getAdjacentVerticesOf("A");
    List<String> expectedAList = new ArrayList<>(Arrays.asList(
        "B"));
    assertTrue(aSet.containsAll(expectedAList));
    assertEquals(1, graph.size());
  }
  
  /**
   * Test that an edge can be removed
   */
  @Test
  void test06_remove_edge() {
    graph.addEdge("A", "B");
    graph.removeEdge("A", "B");
    assertEquals(2, graph.order());
    assertEquals(0, graph.size());
  }

  /**
   * Test that an edge can be removed with out affecting the rest of the graph
   */
  @Test
  void test07_remove_edge_02() {
    graph.addEdge("A", "B");
    graph.addEdge("A", "C");
    graph.addEdge("A", "D");
    graph.addEdge("B", "A");
    graph.removeEdge("A", "B");
    assertEquals(4, graph.order());
    assertEquals(3, graph.size());

    List<String> aSet = graph.getAdjacentVerticesOf("A");
    List<String> expectedAList = new ArrayList<>(Arrays.asList(
        "B")); // B should not be in list
    assertFalse(aSet.containsAll(expectedAList));

    List<String> bSet = graph.getAdjacentVerticesOf("B");
    List<String> expectedBList = new ArrayList<>(Arrays.asList(
        "A"));
    assertTrue(bSet.containsAll(expectedBList));

  }
}
