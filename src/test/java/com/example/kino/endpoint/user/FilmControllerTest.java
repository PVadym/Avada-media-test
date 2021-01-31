package com.example.kino.endpoint.user;

import com.example.kino.config.dto.FilmAdminDto;
import com.example.kino.config.dto.FilmDto;
import com.example.kino.config.security.JwtConfigurer;
import com.example.kino.config.security.JwtTokenFilter;
import com.example.kino.service.api.FilmService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FilmController.class)
class FilmControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private FilmService service;
    @MockBean
    private JwtTokenFilter jwtTokenFilter;
    @MockBean
    private JwtConfigurer jwtConfigurer;
    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    public void givenFilm_whenGetFilm_thenReturnJson()    throws Exception {

        FilmDto film = new FilmDto();
        film.setName("Film");

        given(service.getFilmById(1L)).willReturn(film);

        mvc.perform(get("/rest/film/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
