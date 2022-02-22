package recipes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.repository.RecipeRep;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@RestController
public class Controller {

    @Autowired
    private RecipeRep recipeRep;

    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

    @PostMapping("/api/recipe/new")
    public ResponseEntity<?> saveRecipe(@Valid @RequestBody Recipe recipe) {
        recipe.setDate(LocalDateTime.now().toString());
        System.out.println(recipe.getDate());
        recipeRep.save(recipe);
        return new ResponseEntity<>(gson.toJson(Collections.singletonMap("id", recipe.getId())), HttpStatus.OK);
    }

    @GetMapping("/api/recipe/{id}")
    public ResponseEntity<?> returnRecipeId(@PathVariable long id) {
        try {
            Recipe recipeOptional = recipeRep.findById(id).orElseThrow();
            return new ResponseEntity<>(gson.toJson(recipeOptional), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/recipe/search/")
    public ResponseEntity<?> returnIngridients(@RequestParam(required = false) String category, @RequestParam(required = false) String name) {
        List<Recipe> list = new ArrayList<>();
        Iterable<Recipe> dopRecipe = recipeRep.findAll();

        if (name != null) {
            for (Recipe treadmill : dopRecipe) {
                if (treadmill.getName().toLowerCase().contains(name)) {
                    list.add(treadmill);
                }
            }
            return new ResponseEntity<>(gson.toJson(list), HttpStatus.OK);
        }
        if (category != null) {
            for (Recipe treadmill : dopRecipe) {
                if (treadmill.getCategory().toLowerCase().equals(category)) {
                    list.add(treadmill);
                }
            }
            return new ResponseEntity<>(gson.toJson(list), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/api/recipe/{id}")
    public  ResponseEntity<?> update(@PathVariable long id, @Valid @RequestBody Recipe recipe){
        try {
            recipeRep.findById(id).orElseThrow();
            recipe.setId(id);
            recipe.setDate(LocalDateTime.now().toString());
            recipeRep.save(recipe);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity<?> deleteRecipeId(@PathVariable long id) {
        try {
            recipeRep.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/recipe/delete")
    public ResponseEntity<?> deleteAllRecipe() {
        try {
            recipeRep.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
