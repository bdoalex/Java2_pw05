package fr.isen.java2.db.daos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import fr.isen.java2.db.entities.Film;
import fr.isen.java2.db.entities.Genre;

public class FilmDao {

	public List<Film> listFilms() {
		List<Film> listOfFilms = new ArrayList<>();

		try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
			try (Statement statement = connection.createStatement()) {
				try (ResultSet results = statement.executeQuery("SELECT * FROM film JOIN genre ON film.genre_id = genre.idgenre")) {
					while (results.next()) {
						Genre genre = new Genre(results.getInt("genre_id"), results.getString("name"));

						Film film = new Film(
								results.getInt("idfilm"),
								results.getString("title"),
								results.getDate("release_date").toLocalDate(),
								genre,
								results.getInt("duration"),
								results.getString("director"),
								results.getString("summary"));
						listOfFilms.add(film);
					}
					results.close();
					statement.close();
				}
			}
		} catch (SQLException e) {
			// Manage Exception
			e.printStackTrace();
		}
		return listOfFilms;
	}

	public List<Film> listFilmsByGenre(String genreName) {
		List<Film> listOfFilmsByGenre = new ArrayList<>();

		try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
			try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM film JOIN genre ON film.genre_id = genre.idgenre WHERE genre.name = ?")) {
				statement.setString(1,genreName);
				try (ResultSet results = statement.executeQuery()) {
					while (results.next()) {
						Genre genre = new Genre(results.getInt("genre_id"), results.getString("name"));

						Film film = new Film(
								results.getInt("idfilm"),
								results.getString("title"),
								results.getDate("release_date").toLocalDate(),
								genre,
								results.getInt("duration"),
								results.getString("director"),
								results.getString("summary"));
						listOfFilmsByGenre.add(film);
					}
					results.close();
					statement.close();
				}
			}
		} catch (SQLException e) {
			// Manage Exception
			e.printStackTrace();
		}
		return listOfFilmsByGenre;
	}

	public Film addFilm(Film film) {
		try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
			String sqlQuery = "INSERT INTO film(title,release_date,genre_id,duration,director,summary) VALUES(?,?,?,?,?,?)";
			try (PreparedStatement statement = connection.prepareStatement(
					sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
				statement.setString(1,film.getTitle());
				statement.setDate(2,java.sql.Date.valueOf(film.getReleaseDate()));
				statement.setInt(3,film.getGenre().getId());
				statement.setInt(4, film.getDuration());
				statement.setString(5, film.getDirector());
				statement.setString(6,film.getSummary());
				statement.executeUpdate();
				ResultSet ids = statement.getGeneratedKeys();
				if (ids.next()) {
					return new Film(ids.getInt(1), film.getTitle(), film.getReleaseDate(), film.getGenre(), film.getDuration(), film.getDirector(), film.getSummary());
				}
				statement.close();
				ids.close();
			}

		}catch (SQLException e) {
			// Manage Exception
			e.printStackTrace();
		}
		return film;
	}
}
