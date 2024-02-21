package recipes;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe createRecipe(RecipeDto recipeDto) {
        Recipe recipe = new Recipe();
        recipe.setCategory(recipeDto.getCategory());
        recipe.setName(recipeDto.getName());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setIngredients(recipeDto.getIngredients());
        recipe.setDirections(recipeDto.getDirections());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authorEmail = authentication.getName();
        recipe.setAuthorEmail(authorEmail);

        return saveRecipe(recipe);
    }

    public Optional<Recipe> updateRecipe(Long id, RecipeDto recipeDto) {
        Optional<Recipe> existingRecipe = recipeRepository.findById(id);

        if (existingRecipe.isPresent()) {
            Recipe recipe = existingRecipe.get();
            recipe.setName(recipeDto.getName());
            recipe.setCategory(recipeDto.getCategory());
            recipe.setDescription(recipeDto.getDescription());
            recipe.setIngredients(recipeDto.getIngredients());
            recipe.setDirections(recipeDto.getDirections());

            saveRecipe(recipe);
            return Optional.of(recipe);
        }
        return Optional.empty();
    }

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public Optional<Recipe> getRecipe(Long id) {
        return recipeRepository.findById(id);
    }

    public boolean deleteRecipe(Long id) {
        if (recipeRepository.existsById(id)) {
            recipeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category) {
        return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> findByNameContainingIgnoreCaseOrderByDateDesc(String name) {
        return recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
    }
}
