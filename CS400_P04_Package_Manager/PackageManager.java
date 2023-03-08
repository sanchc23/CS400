import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Filename:   PackageManager.java
 * Project:    p4
 * Authors:    
 * 
 * PackageManager is used to process json package dependency files
 * and provide function that make that information available to other users.
 * 
 * Each package that depends upon other packages has its own
 * entry in the json file.  
 * 
 * Package dependencies are important when building software, 
 * as you must install packages in an order such that each package 
 * is installed after all of the packages that it depends on 
 * have been installed.
 * 
 * For example: package A depends upon package B,
 * then package B must be installed before package A.
 * 
 * This program will read package information and 
 * provide information about the packages that must be 
 * installed before any given package can be installed.
 * all of the packages in
 * 
 * You may add a main method, but we will test all methods with
 * our own Test classes.
 */

public class PackageManager {

  private Graph predecestorsGraph;
  private Set<String> packSet;
  private Graph succesorGraph;

  /*
   * Package Manager default no-argument constructor.
   */
  public PackageManager() {
    this.predecestorsGraph = new Graph(); // Graphs of all vertices and dependencies
    this.succesorGraph = new Graph();
    this.packSet = new LinkedHashSet<String>(); // Gives a list of all packages
  }

  /**
   * Takes in a file path for a json file and builds the
   * package dependency graph from it. 
   * 
   * @param jsonFilepath the name of json data file with package dependency information
   * @throws FileNotFoundException if file path is incorrect
   * @throws IOException if the give file cannot be read
   * @throws ParseException if the given json cannot be parsed 
   */
  public void constructGraph(String jsonFilepath) throws FileNotFoundException, IOException, ParseException {
    Object obj = new JSONParser().parse(new FileReader(jsonFilepath));
    JSONObject jo = (JSONObject)obj;
    JSONArray arr = (JSONArray) jo.get("packages"); // parse json to get Packages

    for(int index = 0; index < arr.size(); index++) {   // traverse array to get all packages
      JSONObject packageObject = (JSONObject) arr.get(index);
      String name = (String) packageObject.get("name"); // get the objects in the package
      JSONArray dependencies = (JSONArray) packageObject.get("dependencies");

      // change the JSON Array to a regular String[]
      if(dependencies != null) {    // parse the dependencies array
        String[] depArr = new String[dependencies.size()];
        for (int i = 0; i < depArr.length; i++) {
          depArr[i] = (String) dependencies.get(i);
        }
  
        // Create a package for each value in JSON
        Package pac = new Package(name, depArr);
        this.packSet.add(name);
  
        // A depends on B ... B -> A
        
        // create the graphs by adding vertex and edges
        this.predecestorsGraph.addVertex(pac.getName());
        this.succesorGraph.addVertex(name);
        for(int dep = 0; dep < depArr.length; dep++) {
          this.packSet.add(depArr[dep]);
          this.predecestorsGraph.addEdge(depArr[dep], name);
          this.succesorGraph.addEdge(name, depArr[dep]);
        }     
      }
      else {
        this.predecestorsGraph.addVertex(name);
        this.succesorGraph.addVertex(name);
      }
    }
  }


  /**
   * Helper method to get all packages in the graph.
   * 
   * @return Set<String> of all the packages
   */
  public Set<String> getAllPackages() {
    return predecestorsGraph.getAllVertices();
  }

  /**
   * Given a package name, returns a list of packages in a
   * valid installation order.  
   * 
   * Valid installation order means that each package is listed 
   * before any packages that depend upon that package.
   * 
   * @return List<String>, order in which the packages have to be installed
   * 
   * @throws CycleException if you encounter a cycle in the graph while finding
   * the installation order for a particular package. Tip: Cycles in some other
   * part of the graph that do not affect the installation order for the 
   * specified package, should not throw this exception.
   * 
   * @throws PackageNotFoundException if the package passed does not exist in the 
   * dependency graph.
   */
  public List<String> getInstallationOrder(String pkg) throws CycleException, PackageNotFoundException {
    List<String> visted = new ArrayList<String>();
    if(!packSet.contains(pkg)) {
      throw new PackageNotFoundException();
    }
    
    Stack<String> stack = new Stack<>(); // set up stack for algo
    stack.push(pkg);
    while(!stack.isEmpty()) {
      String c = stack.peek(); // get value to refernce
      List<String> suc = succesorGraph.getAdjacentVerticesOf(c);

      if(suc.isEmpty()) { // if the vertex doesn't have any successors add to 
        c = stack.pop();  // visited and remove from stack
        visted.add(c);
        continue;
      }
      for(String v : suc) {
        if(!visted.contains(v)){ // check that each successor has been visited
          if(stack.contains(v)) {   
            throw new CycleException(); // since it has already been visited it would be cycle
          }
          stack.push(v); // add to the stack
        }
        else{
          c = stack.pop();  // vertex has no more unvisited successor 
          visted.add(c);
        }
      }
    }
  
    return visted;
  }

  /**
   * Given two packages - one to be installed and the other installed, 
   * return a List of the packages that need to be newly installed. 
   * 
   * For example, refer to shared_dependecies.json - toInstall("A","B") 
   * If package A needs to be installed and packageB is already installed, 
   * return the list ["A", "C"] since D will have been installed when 
   * B was previously installed.
   * 
   * @return List<String>, packages that need to be newly installed.
   * 
   * @throws CycleException if you encounter a cycle in the graph while finding
   * the dependencies of the given packages. If there is a cycle in some other
   * part of the graph that doesn't affect the parsing of these dependencies, 
   * cycle exception should not be thrown.
   * 
   * @throws PackageNotFoundException if any of the packages passed 
   * do not exist in the dependency graph.
   */
  public List<String> toInstall(String newPkg, String installedPkg) throws CycleException, PackageNotFoundException {
    return null;
  }

  /**
   * Return a valid global installation order of all the packages in the 
   * dependency graph.
   * 
   * assumes: no package has been installed and you are required to install 
   * all the packages
   * 
   * returns a valid installation order that will not violate any dependencies
   * 
   * @return List<String>, order in which all the packages have to be installed
   * @throws CycleException if you encounter a cycle in the graph
   */
  public List<String> getInstallationOrderForAllPackages() throws CycleException {
    return null;
  }

  /**
   * Find and return the name of the package with the maximum number of dependencies.
   * 
   * Tip: it's not just the number of dependencies given in the json file.  
   * The number of dependencies includes the dependencies of its dependencies.  
   * But, if a package is listed in multiple places, it is only counted once.
   * 
   * Example: if A depends on B and C, and B depends on C, and C depends on D.  
   * Then,  A has 3 dependencies - B,C and D.
   * 
   * @return String, name of the package with most dependencies.
   * @throws CycleException if you encounter a cycle in the graph
   */
  public String getPackageWithMaxDependencies() throws CycleException {
    return "";
  }

  public static void main (String [] args) {
    System.out.println("PackageManager.main()");
  }

}
