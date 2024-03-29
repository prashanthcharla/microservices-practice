package prashanth.poc.movieinfoservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import prashanth.poc.movieinfoservice.dataModel.Movie;

@RestController
@RequestMapping("/movies")
public class MovieDetailsController {

	@GetMapping(value = "/{movieId}")
	public Movie getMovieDetails(@PathVariable("movieId") String movieId) {
		return new Movie(movieId, "Titanic");
	}
}
