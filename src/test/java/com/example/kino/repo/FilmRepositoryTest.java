package com.example.kino.repo;

import com.example.kino.entity.film.Film;
import com.example.kino.entity.film.FilmType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class FilmRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FilmRepository filmRepository;

    @Test
    public void whenFindById_thenReturnFilm() {
        // given
        Film film = new Film();
        film.setName("Film");
        film.setType(FilmType.D3);
        entityManager.persist(film);
        entityManager.flush();

        // when
        Film found = filmRepository.getOne(1L);

        // then
        assertThat(found.getName())
                .isEqualTo(film.getName());
    }


}
