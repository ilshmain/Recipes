package recipes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.Recipe;

@Repository
public interface RecipeRep extends CrudRepository<Recipe, Long> {
}
