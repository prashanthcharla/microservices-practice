package prashanth.poc.ratingsdataservice.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import prashanth.poc.ratingsdataservice.dataModel.Rating;
import prashanth.poc.ratingsdataservice.dataModel.UserRating;

@RestController
@RequestMapping("/ratings")
public class RatingsDatController {

	@GetMapping("/{movieId}")
	public Rating getRating(@PathVariable("movieId") String movieId) {
		return new Rating(movieId, 3);
	}

	@GetMapping("/users/{userId}")
	public UserRating getRatingsGivenByUser(@PathVariable("userId") String userId) {
		List<Rating> list = Arrays.asList(new Rating("m1", 3), new Rating("m2", 2), new Rating("m3", 5));
		UserRating userRating = new UserRating(list);
		return userRating;
	}

}
