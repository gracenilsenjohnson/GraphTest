import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BasicCsvToEdgesParser {

	public static List<Edge> parseCsvIntoEdges(String csvFilename)
	{

	String csvFile = csvFilename;
	        String line;
	        String cvsSplitBy = ",";
	        List<Edge> edges = new ArrayList<>();
	        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	            while ((line = br.readLine()) != null) {
	                // Split the line by commas
	                String[] values = line.split(cvsSplitBy);
	                if (values.length != 3) {
	                    System.out.println("Invalid line: " + line);
	                    System.out.println("Error reading file at: " +csvFilename);
	                    System.exit(0);
	                }
	                int source=Integer.parseInt(values[0]);
	                int destination=Integer.parseInt(values[1]);
	                int weight=Integer.parseInt(values[2]);

	                edges.add(new Edge(source,destination,weight));
	                // Do something with the values
	                //System.out.println("Source: " + source + ", Destination: " + destination + ", Weight: " + weight); used for debugging
	            }
	        } catch (IOException e) {
	            System.out.println("Error reading file at: " +csvFilename);
	        }

	return edges;
	}
}
