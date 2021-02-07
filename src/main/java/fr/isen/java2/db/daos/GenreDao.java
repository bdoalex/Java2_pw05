package fr.isen.java2.db.daos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import fr.isen.java2.db.entities.Genre;

/**
 * @author Alexandre BARBOSA DE OLIVEIRA
 */
public class GenreDao {


	/**
	 * @return returns a list containing the genres
	 */
	public List<Genre> listGenres() {
		List<Genre> listOfGenres = new ArrayList<>();
		try (Connection connection = DataSourceFactory.getDataSource()) {
			try (Statement statement = connection.createStatement()) {
				try (ResultSet results = statement.executeQuery("SELECT * FROM genre")) {
					while (results.next()) {
						Genre genre = new Genre(results.getInt("idgenre"), results.getString("name"));

						listOfGenres.add(genre);
					}

				}
			}
		} catch (SQLException e) {
			// Manage Exception
			e.printStackTrace();
		}
		return listOfGenres;

	}

	/**
	 * @param name name of the genre to retrieve from the database
	 * @return returns the genre of the result found in the database
	 */
	public Genre getGenre(String name) {
		try (Connection connection = DataSourceFactory.getDataSource()) {
			try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM genre WHERE name =?")) {
				statement.setString(1,name);
				try (ResultSet results = statement.executeQuery()) {
					if (results.next()) {
						return new Genre(results.getInt("idgenre"),results.getString("name"));
					}

				}

			}

		} catch (SQLException e) {
			// Manage Exception
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * @param name Name of the genre to add to the database
	 */
	public void addGenre(String name) {
		try (Connection connection = DataSourceFactory.getDataSource()) {
			String sqlQuery = "INSERT INTO genre(name) VALUES(?)";
			try (PreparedStatement statement = connection.prepareStatement(
					sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, name);
				statement.executeUpdate();
			}
		}catch (SQLException e) {
			// Manage Exception
			e.printStackTrace();
		}
	}
}
