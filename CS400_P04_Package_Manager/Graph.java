import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Filename:   Graph.java
 * Project:    p4
 * Authors:    
 * 
 * Directed and unweighted graph implementation
 */

public class Graph implements GraphADT {
  private class GraphNode{
    private String data;
    private List<GraphNode> successors; 

    public GraphNode(String data) {
      this.data = data;
      this.successors = new ArrayList<GraphNode>();
    }
  }

  private int numVertices; // Used to hold values of the graph
  private int numEdges;
  private List<GraphNode> adjacencyList; 


  /*
   * Default no-argument constructor
   */ 
  public Graph() {
    this.numVertices = 0;
    this.numEdges = 0;
    adjacencyList = new ArrayList<GraphNode>();
  }

  /**
   * Add new vertex to the graph.
   *
   * If vertex is null or already exists,
   * method ends without adding a vertex or 
   * throwing an exception.
   * 
   * Valid argument conditions:
   * 1. vertex is non-null
   * 2. vertex is not already in the graph 
   */
  public void addVertex(String vertex) {
    if(vertex == null) {
      return;
    }
    // dont add vertex if it is already in the adjacencyList
    for(int index = 0; index < adjacencyList.size(); index++) { 
      if(adjacencyList.get(index).data.equals(vertex)) {
        return;
      }
    }
    // add vertex and update the vertices number
    adjacencyList.add(new GraphNode(vertex));
    this.numVertices++;
  }

  /**
   * Remove a vertex and all associated 
   * edges from the graph.
   * 
   * If vertex is null or does not exist,
   * method ends without removing a vertex, edges, 
   * or throwing an exception.
   * 
   * Valid argument conditions:
   * 1. vertex is non-null
   * 2. vertex is not already in the graph 
   */
  public void removeVertex(String vertex) {
    if(vertex == null) { // if vertex is not valid 
      return;
    }

    // need to check all edges
    for(int index = 0; index < adjacencyList.size(); index++) {

      // goes to through the adjacencyList and checks the successors
      for(int indexSuc = 0; indexSuc < adjacencyList.get(index).successors.size(); indexSuc++) {
        if(adjacencyList.get(index).successors.get(indexSuc).equals(vertex)) {
          adjacencyList.get(index).successors.remove(indexSuc);  // removes vertex from successor list
          this.numEdges--;
        }
      }

      // Removes vertecy from adjacencyList
      if(adjacencyList.get(index).data.equals(vertex)) {
        adjacencyList.remove(index);
        this.numVertices--;
      }
    }
  }

  /**
   * Add the edge from vertex1 to vertex2
   * to this graph.  (edge is directed and unweighted)
   * If either vertex does not exist,
   * add vertex, and add edge, no exception is thrown.
   * If the edge exists in the graph,
   * no edge is added and no exception is thrown.
   * 
   * Valid argument conditions:
   * 1. neither vertex is null
   * 2. both vertices are in the graph 
   * 3. the edge is not in the graph
   */
  public void addEdge(String vertex1, String vertex2) {
    if(vertex1 == null || vertex2 == null) {
      return;
    }

    GraphNode v2 = null;

    // used to determine if vertex2 needs to be added
    for(int v2Index = 0; v2Index < adjacencyList.size(); v2Index++) {
      if(adjacencyList.get(v2Index).data.equals(vertex2)) {
        v2 = adjacencyList.get(v2Index);
      }
    }

    // goes through and checks that edge doesn't exist
    for(int v1Index = 0; v1Index < adjacencyList.size(); v1Index++) {
      if(adjacencyList.get(v1Index).data.equals(vertex1)) {
        for(int sucIndex = 0; sucIndex < adjacencyList.get(v1Index).successors.size(); sucIndex++) {
          if(adjacencyList.get(v1Index).successors.get(sucIndex).data.equals(vertex2)) {
            return;
          }
        }
        // add the edge from v1 to v2
        if(v2 != null) {
          adjacencyList.get(v1Index).successors.add(v2);
          this.numEdges++;
          return;
        }
        else {
          GraphNode newNode = new GraphNode(vertex2);
          adjacencyList.add(newNode);
          adjacencyList.get(v1Index).successors.add(newNode);
          this.numEdges++;
          this.numVertices++;
          return;
        }
      }
    }

    // neither vertex are in the graph so both need to be added
    GraphNode v1 = new GraphNode(vertex1);
    adjacencyList.add(v1);
    int index = adjacencyList.size() - 1; 
    this.numVertices++;
    if(v2 != null) {
      adjacencyList.get(index).successors.add(v2);
      this.numEdges++;
    }
    else {
      GraphNode newNode = new GraphNode(vertex2);
      adjacencyList.add(newNode);
      adjacencyList.get(index).successors.add(newNode);
      this.numEdges++;
      this.numVertices++;
    }
  }

  /**
   * Remove the edge from vertex1 to vertex2
   * from this graph.  (edge is directed and unweighted)
   * If either vertex does not exist,
   * or if an edge from vertex1 to vertex2 does not exist,
   * no edge is removed and no exception is thrown.
   * 
   * Valid argument conditions:
   * 1. neither vertex is null
   * 2. both vertices are in the graph 
   * 3. the edge from vertex1 to vertex2 is in the graph
   */
  public void removeEdge(String vertex1, String vertex2) {
    if(vertex1 == null || vertex2 == null) {
      return;
    }
    
    int index1, index2;
    // checks if vertex2 is in the adjacency list for vertex1 and removes it
    for(int i = 0; i < adjacencyList.size(); i++) {
      if(adjacencyList.get(i).data.equals(vertex1)) {
        for(int j = 0; j < adjacencyList.get(i).successors.size(); j++) {
          if(adjacencyList.get(i).successors.get(j).data.equals(vertex2)) {
            adjacencyList.get(i).successors.remove(j);
            this.numEdges--;
          }
        }
      }
    }
    
  }	

  /**
   * Returns a Set that contains all the vertices
   * 
   */
  public Set<String> getAllVertices() {
    Set<String> returnSet = new LinkedHashSet<String>(); // keeps order for packageManager
    for(int i = 0; i< adjacencyList.size(); i++) {
      returnSet.add(adjacencyList.get(i).data);
    }
    return returnSet;
  }

  /**
   * Get all the neighbor (adjacent) vertices of a vertex
   *
   */
  public List<String> getAdjacentVerticesOf(String vertex) {
    List<String> returnList = new LinkedList<String>();
    for(int i = 0; i < adjacencyList.size(); i++) {
      if(adjacencyList.get(i).data.equals(vertex)) {
        for(int j = 0; j < adjacencyList.get(i).successors.size(); j++) { // returns all successors
          returnList.add(adjacencyList.get(i).successors.get(j).data);
        }
      }  
    }
    return returnList;
  }

  /**
   * Returns the number of edges in this graph.
   */
  public int size() {
    return this.numEdges;
  }

  /**
   * Returns the number of vertices in this graph.
   */
  public int order() {
    return this.numVertices;
  }
}
