package it.polito.tdp.porto.model;

import java.util.*;


import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;


import it.polito.tdp.porto.db.PortoDAO;
public class Model {
UndirectedGraph<Author, DefaultEdge> grafo;  
HashMap<Integer,Author> mappaAutori=new HashMap<Integer,Author>();
List<Author> autori;

public Model(){	
	
}
   
public List<Author> getAutori(){
	PortoDAO dao=new PortoDAO();
	if (this.autori == null) {
		this.autori =dao.getAllAuthors(mappaAutori);
	}

	return autori;
}




public void creaGrafo(){
	PortoDAO dao=new PortoDAO();
	this.grafo=new SimpleGraph<Author, DefaultEdge>(DefaultEdge.class);
		Graphs.addAllVertices(grafo,this.autori);
		for(AuthorPair ap:dao.listaCoppieAdacenti()){
			Author a1=mappaAutori.get(ap.a1);
			Author a2=mappaAutori.get(ap.a2);
            grafo.addEdge(a1,a2);
		}
		
	}

public List<Author> displayNeighbours(Author autore) {
	this.creaGrafo();
	if(grafo.containsVertex(autore))
	  return Graphs.neighborListOf(grafo,autore );
	return null;
}

public List<Author> getNonCoautore(Author autore) {
	List<Author> lista=new ArrayList<Author>();
	lista.addAll(autori);
	lista.removeAll(this.displayNeighbours(autore));
	return lista;
}

public List<Paper> getListaArticoli(Author partenza,Author arrivo){
	PortoDAO dao=new PortoDAO();
	List<Paper> listaArticoli=new ArrayList<Paper>();
	DijkstraShortestPath<Author,DefaultEdge> d=new DijkstraShortestPath<Author,DefaultEdge>(grafo,partenza,arrivo);
	for(DefaultEdge e:d.getPathEdgeList()){
		Author a1=grafo.getEdgeSource(e);
		Author a2=grafo.getEdgeTarget(e);
		listaArticoli.add(dao.getAllPapers(a1, a2));
	}
	return listaArticoli;

}

public Paper getArticolo(int eprintid) {
	PortoDAO dao=new PortoDAO();
	return dao.getArticolo(eprintid);
}





}
