package recipes.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.businesslayer.Recipe;
import recipes.businesslayer.RecipeService;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @PostMapping("/new")
    public Map<String, Long> save(@Valid @RequestBody Recipe recipe) {
        return Map.of("id", recipeService.saveRecipe(recipe));
    }

    @GetMapping("/{id}")
    public Recipe get(@PathVariable Long id) {
        return recipeService.getRecipe(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id,
                                 @Valid @RequestBody Recipe recipe) {
        recipeService.updateRecipe(id, recipe);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public Object search(@RequestParam(name = "category", required = false) Optional<String> category,
                         @RequestParam(name = "name", required = false) Optional<String> name) {
        if (category.isPresent()) {
            return recipeService.searchByCategory(category.get());
        }

        if (name.isPresent()) {
            return recipeService.searchByName(name.get());
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
