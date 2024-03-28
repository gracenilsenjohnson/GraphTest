Does the solution work for larger graphs?

Yes, I tested it on randomly generated graphs with a million edges and a few million vertices and 
it responded quickly.

I'd need to add a better import function for the parser, presumably hooked up to an external data source, 
but yes, it should work for fairly large graphs. 

A 2-d array might be more space efficient implementation of the graph, but that would depend on 
how sparse or dense the data is, namely comparing the overhead of the edge-vertex list versus a
potentially sparse array being filled with lots of zeroes.

Can you think of any optimizations?

I think it would start to depend on the expected data at this point. I believe the negation/shortest
path algorithm using a topological sorting of the nodes is the expected way to solve this for directed,
acyclic graphs. It would also depend on if we expect the graph to change - if not, one could save the
topological map and just run repeated longest paths starting from other vertices, instead of regenerating
it for every different source vertex

What’s the computational complexity of your solution?
Time: O(Edges + Vertices)
Space: O(Edges + Vertices)

Are there any unusual cases that aren't handled?

I put in handling for detecting cyclic graphs, as those were outside the scope of the problem (and
make it NP-hard). 

Other edge cases such as:
Vertices with no edges (The Vertex 0 in every randomly generated one is one of these)
Negative edge weights (The random generator has a 14% chance of generating a negative edge weight)
Zero weight edges (The random generator has a 1% chance of generating this) 
Graphs with too few edges 

I believe are handled by this implementation, but there may be other edge cases I didn't think of.


Notes about implementation:
I also tested some specific, hand-created acyclic graphs (and cyclic ones to make sure it kicks them out),
but mostly used the random generator.

The program takes as input parameters either a size of graph to randomly generate OR a filename 
(expecting a csv with weights) and a specific starting vertex to find the longest path of.