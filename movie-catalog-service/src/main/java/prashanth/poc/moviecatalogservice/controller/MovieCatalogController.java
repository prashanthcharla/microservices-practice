package prashanth.poc.moviecatalogservice.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import prashanth.poc.moviecatalogservice.dataModel.CatalogItem;

@RestController
@RequestMapping(value = "/catalog")
public class MovieCatalogController {

	@GetMapping(value = "/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		return Collections.singletonList(new CatalogItem("Titanic", "Love Story", 4));
	}

}
