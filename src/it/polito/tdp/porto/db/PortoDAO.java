package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.AuthorPair;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {

	/*
	 * Dato l'id ottengo l'autore.
	 */
	public Author getAutore(int id) {

		final String sql = "SELECT * FROM author where id=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				return autore;
			}

			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(int eprintid) {

		final String sql = "SELECT * FROM paper where eprintid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				return paper;
			}
			
			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	public List<AuthorPair>listaCoppieAdacenti(){
		final String sql = "SELECT DISTINCT c1.authorid as a1, c2.authorid as a2 FROM creator c1, creator c2 where  c1.eprintid=c2.eprintid AND c1.authorid<> c2.authorid";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			

			
			List<AuthorPair> coautori= new ArrayList<AuthorPair>();
			ResultSet rs = st.executeQuery();
			


			while (rs.next()) {
				AuthorPair c= new AuthorPair(rs.getInt("a1"),rs.getInt("a2"));
				coautori.add(c);		
				
			}

			return coautori;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}

	}
	
	
	
	
	public List<Author> getAllAuthors(HashMap<Integer,Author> mappaAutori){
	 	final String sql = "SELECT * FROM author ";
	 	List<Author> autori=new ArrayList<Author>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			

			while (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				autori.add(autore);
				mappaAutori.put(autore.getId(),autore);
			}
		
			return autori;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	public Paper getAllPapers(Author a1,Author a2){
		final String sql="SELECT c1.eprintid as a1  FROM creator c1,creator c2 WHERE c1.authorid=? AND c2.authorid=? AND c1.eprintid=c2.eprintid";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,a1.getId());
			st.setInt(2,a2.getId());

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Paper paper = new Paper(rs.getInt("a1"));
				return paper;
			}

			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}

	
}
