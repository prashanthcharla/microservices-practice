package prashanth.poc.moviecatalogservice.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import prashanth.poc.moviecatalogservice.dataModel.CatalogItem;
import prashanth.poc.moviecatalogservice.dataModel.Movie;
import prashanth.poc.moviecatalogservice.dataModel.Rating;

@RestController
@RequestMapping(value = "/catalog")
public class MovieCatalogController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private WebClient.Builder webClientBuilder;

	@GetMapping(value = "/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

		List<CatalogItem> catalog = new ArrayList<>();

		/**
		 * Flow:
		 * 
		 * 1. Based on user id, get the list of movies (movie ids). 
		 * 2. Based on movie id, get the details like name - call to movie info service 
		 * 3. Based on movie id, get the rating - call to ratings data service
		 */

		// 1st step
		List<String> movieIds = Arrays.asList("m1", "m2", "m3");

		// 2nd step
		Map<String, Movie> movieDetails = movieIds.stream().map(id -> webClientBuilder.build().get()
				.uri("http://localhost:8082/movies/" + id).retrieve().bodyToMono(Movie.class).block())
				.collect(Collectors.toMap(Movie::getMovieId, obj -> obj));

		// 3rd step
		Map<String, Rating> ratingDetails = movieIds.stream().map(id -> webClientBuilder.build().get()
				.uri("http://localhost:8083/ratings/" + id).retrieve().bodyToMono(Rating.class).block())
				.collect(Collectors.toMap(Rating::getMovieId, obj -> obj));

		// Create catalog items from above details
		movieIds.forEach(id -> {
			CatalogItem item = new CatalogItem(movieDetails.get(id).getMovieName(), "Love Story",
					ratingDetails.get(id).getRating());
			catalog.add(item);
		});

		return catalog;
	}

}
