package fr.isen.java2.db.daos;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.isen.java2.db.entities.Film;
import fr.isen.java2.db.entities.Genre;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.tuple;

public class FilmDaoTestCase {

    private FilmDao filmDao = new FilmDao();

    @Before
    public void initDb() throws Exception {
        Connection connection = DataSourceFactory.getDataSource().getConnection();
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS genre (idgenre INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT , name VARCHAR(50) NOT NULL);");
        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS film (\r\n"
                        + "  idfilm INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\r\n" + "  title VARCHAR(100) NOT NULL,\r\n"
                        + "  release_date DATETIME NULL,\r\n" + "  genre_id INT NOT NULL,\r\n" + "  duration INT NULL,\r\n"
                        + "  director VARCHAR(100) NOT NULL,\r\n" + "  summary MEDIUMTEXT NULL,\r\n"
                        + "  CONSTRAINT genre_fk FOREIGN KEY (genre_id) REFERENCES genre (idgenre));");
        stmt.executeUpdate("DELETE FROM film");
        stmt.executeUpdate("DELETE FROM genre");
        stmt.executeUpdate("INSERT INTO genre(idgenre,name) VALUES (1,'Drama')");
        stmt.executeUpdate("INSERT INTO genre(idgenre,name) VALUES (2,'Comedy')");
        stmt.executeUpdate("INSERT INTO film(idfilm,title, release_date, genre_id, duration, director, summary) "
                + "VALUES (1, 'Title 1', '2015-11-26 12:00:00.000', 1, 120, 'director 1', 'summary of the first film')");
        stmt.executeUpdate("INSERT INTO film(idfilm,title, release_date, genre_id, duration, director, summary) "
                + "VALUES (2, 'My Title 2', '2015-11-14 12:00:00.000', 2, 114, 'director 2', 'summary of the second film')");
        stmt.executeUpdate("INSERT INTO film(idfilm,title, release_date, genre_id, duration, director, summary) "
                + "VALUES (3, 'Third title', '2015-12-12 12:00:00.000', 2, 176, 'director 3', 'summary of the third film')");
        stmt.close();
        connection.close();
    }

    @Test
    public void shouldListFilms() {
        //WHEN
        List<Film> films = filmDao.listFilms();
        List<Genre> genres = new ArrayList<>();


        for (Film film : films) {//Ajout des genres des différents films dans une liste
            genres.add(film.getGenre());
        }

        //Then

        assertThat(genres).hasSize(3);
        assertThat(genres).extracting("id", "name").containsOnly(tuple(1, "Drama"), tuple(2, "Comedy"), tuple(2, "Comedy"));

        assertThat(films).hasSize(3);
        assertThat(films).extracting("id", "title", "releaseDate", "duration", "director", "summary").containsOnly(
                tuple(1, "Title 1", LocalDate.of(2015, 11, 26), 120, "director 1", "summary of the first film"),
                tuple(2, "My Title 2", LocalDate.of(2015, 11, 14), 114, "director 2", "summary of the second film"),
                tuple(3, "Third title", LocalDate.of(2015, 12, 12), 176, "director 3", "summary of the third film"));

    }

    @Test
    public void shouldListFilmsByGenre() {
        fail("Not yet implemented");
    }

    @Test
    public void shouldAddFilm() throws Exception {
        fail("Not yet implemented");
    }
}
