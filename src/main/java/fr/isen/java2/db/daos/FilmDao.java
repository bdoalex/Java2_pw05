package fr.isen.java2.db.daos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
						GenreDao genreDao = new GenreDao();
						Genre genre = genreDao.getGenreById(results.getInt("genre_id"));
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
				}
			}
		} catch (SQLException e) {
			// Manage Exception
			e.printStackTrace();
		}
		return listOfFilms;
	}

	public List<Film> listFilmsByGenre(String genreName) {
		throw new RuntimeException("Method is not yet implemented");
	}

	public Film addFilm(Film film) {
		throw new RuntimeException("Method is not yet implemented");
	}
}
