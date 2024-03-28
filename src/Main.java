import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		{
			int graphsize=40;
			int start=0;
			List<Edge> edges=null;
			if(((args == null) || (args. length == 0)))
			{
				graphsize=40;
				System.out.println("Generating a graph of size "+ graphsize +". This can be adjusted by entering program arguments.");
				edges = generateRandomishAcyclicGraph(graphsize);
				start=generateRandomStart(graphsize);
			}
			else
			{
				try
				{
					graphsize=Integer.parseInt(args[0]);
					if(graphsize<2)
					{
					System.out.println("Please select a size for a randomly generated graph of at least 2, otherwise no edges can be created");
					System.exit(0);
					}
					else
					{
					System.out.println("Generating a graph of size "+ graphsize);
					edges = generateRandomishAcyclicGraph(graphsize);
					start=generateRandomStart(graphsize);
					}
				}
				catch(NumberFormatException e)
				{
					edges=BasicCsvToEdgesParser.parseCsvIntoEdges(args[0]);
					try
					{
					start=Integer.parseInt(args[1]);
					}
					catch(NumberFormatException f)
					{
						System.out.println("Please select the start location for your selected graph as the second argument");
						System.exit(0);
					}
				}
			}


            Graph graph = new Graph(edges,true);//the boolean here negates the graph, for the purposes of turning this into a shortest-path algorithm
            graph.printAdjacencyList();//therefore this printout will have the distances display negated

            try
            {
            System.out.println(Arrays.toString(Graph.topologicalSort(graph).toArray()));
            }
            catch(NullPointerException e)
            {
            System.out.println("This is not an acyclic graph, it contains a cycle, please try again");
            System.exit(0);
            }
            Map <Integer, Integer> results = graph.shortestPathsDistances(start);
            int destination = Graph.getFurthestDestination(results);
            System.out.println("The destination is " + destination);
            System.out.println("The longest path originating at Vertex "+ start+ " is: "+ Arrays.toString(graph.shortestPath(start, destination).toArray()));
            System.out.println("The weighted length of this path is: " + graph.shortestPathsDistances(start).get(destination)*-1);
        }
	}

	public static List<Edge> generateRandomishAcyclicGraph(int totalVertices) {
        List<Edge> edges = new ArrayList<>();
        Random random = new Random();

        // Generate edges for each vertex
        for (int source = 0; source < totalVertices; source++) {
           	if(source>0)
            	{
	           		for(int i=0;i<5;i++) //generate 5 edges per vertex, chosen just as a number to allow multiple paths. No particular reason, just for testing
	           		{
		                int destination = random.nextInt(source); // Ensure no cycles by selecting destination < source
		                int weight = random.nextInt(100) -15; // Assign random weight (-14 to 85) - tests negative weights also

		                edges.add(new Edge(source, destination, weight));
		                //System.out.println(""+source+","+destination+","+weight); used for debugging
	           		}
            	}
            }

        return edges;
    }
	public static int generateRandomStart(int max)
	{
		Random random = new Random();
		return random.nextInt(max);
	}
}
