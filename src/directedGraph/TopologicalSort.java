// O. Bittel;
// 22.02.2017

package directedGraph;

import java.util.*;

/**
 * Klasse zur Erstellung einer topologischen Sortierung.
 * @author Oliver Bittel
 * @since 22.02.2017
 * @param <V> Knotentyp.
 */
public class TopologicalSort<V> {
    private List<V> ts = new LinkedList<>(); // topologisch sortierte Folge
	private Map<V, Integer> inDeg;
	private Deque<V> q = new ArrayDeque<>();
	// ...

	/**
	 * Führt eine topologische Sortierung für g durch.
	 * @param g gerichteter Graph.
	 */
	public TopologicalSort(DirectedGraph<V> g) {
        inDeg = new TreeMap<>();
        for(V v: g.getVertexSet()){
        	inDeg.put(v,g.getPredecessorVertexSet(v).size());
        	if(inDeg.get(v) == 0){
        		q.add(v);
			}
		}

		while(!q.isEmpty()){
			V v = q.remove();
			ts.add(v);
			for(V succ :g.getSuccessorVertexSet(v)){
				int neighbours = inDeg.get(succ);
				if(neighbours == 1){
					q.add(succ);
				}
				inDeg.put(succ, neighbours-1);
				inDeg.put(v, inDeg.get(v)-1);

			}
		}

		ts = ts.size() != g.getNumberOfVertexes() ? null : ts;
    }
    
	/**
	 * Liefert eine nicht modifizierbare Liste (unmodifiable view) zurück,
	 * die topologisch sortiert ist.
	 * @return topologisch sortierte Liste
	 */
	public List<V> topologicalSortedList() {
        return Collections.unmodifiableList(ts);
    }
    

	public static void main(String[] args) {
		DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
		g.addEdge(1, 2);
		g.addEdge(2, 3);
		g.addEdge(3, 4);
		g.addEdge(3, 5);
		g.addEdge(4, 6);
		g.addEdge(5, 6);
		g.addEdge(6, 7);
		System.out.println(g);

		TopologicalSort<Integer> ts = new TopologicalSort<>(g);
		
		if (ts.topologicalSortedList() != null) {
			System.out.println(ts.topologicalSortedList()); // [1, 2, 3, 4, 5, 6, 7]
		}
	}
}
