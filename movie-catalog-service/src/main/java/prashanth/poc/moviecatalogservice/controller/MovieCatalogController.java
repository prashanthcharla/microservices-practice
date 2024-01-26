package prashanth.poc.moviecatalogservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import prashanth.poc.moviecatalogservice.dataModel.CatalogItem;
import prashanth.poc.moviecatalogservice.dataModel.Movie;
import prashanth.poc.moviecatalogservice.dataModel.UserRating;

@RestController
@RequestMapping(value = "/catalog")
public class MovieCatalogController {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping(value = "/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

		List<CatalogItem> catalog = new ArrayList<>();

		/**
		 * Flow:
		 * 
		 * 1. Based on user id, get the list of ratings with movie ids.
		 * 2. Iterate through ratings list & in each iteration, based on movie id, get the details like name - call to movie info service. 
		 * 3. Create catalog item and add it to final list.
		 */

		UserRating userRating = restTemplate.getForObject("http://localhost:8083/ratings/users/" + userId, UserRating.class);

		userRating.getUserRating().forEach(ratingDetails -> {
			Movie movieDetails = restTemplate.getForObject("http://localhost:8082/movies/" + ratingDetails.getMovieId(),
					Movie.class);
			CatalogItem item = new CatalogItem(movieDetails.getMovieName(), "Love Story", ratingDetails.getRating());
			catalog.add(item);
		});

		return catalog;
	}

}
