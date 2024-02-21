package recipes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/new")
    public Map<String, Long> createRecipe(@Valid @RequestBody RecipeDto recipeDto) {
        Recipe savedRecipe = recipeService.createRecipe(recipeDto);

        return Map.of("id", savedRecipe.getId());
        // return ResponseEntity.status(HttpStatus.CREATED).body(savedRecipe);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Long id) {
        Optional<Recipe> recipe = recipeService.getRecipe(id);
        return recipe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRecipe(@PathVariable Long id, @Valid @RequestBody RecipeDto recipeDto) {

        Optional<Recipe> recipe = recipeService.getRecipe(id);

        if (recipe.isPresent()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            if (!recipe.get().getAuthorEmail().equals(email)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            Optional<Recipe> updatedRecipe = recipeService.updateRecipe(id, recipeDto);
            if (updatedRecipe.isPresent()) {
                return ResponseEntity.noContent().build();
            }

        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        Optional<Recipe> recipe = recipeService.getRecipe(id);

        if (recipe.isPresent()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            if (!recipe.get().getAuthorEmail().equals(email)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            boolean deletedRecipe = recipeService.deleteRecipe(id);
            if (deletedRecipe) {
                return ResponseEntity.noContent().build();
            }

        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> searchRecipes(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String name
    ) {
        if ((category == null && name == null) || (category != null && name != null)) {
            return ResponseEntity.badRequest().build();
        }

        List<Recipe> recipes;

        if (category != null) {
            // Search by category
            recipes = recipeService.findByCategoryIgnoreCaseOrderByDateDesc(category);
        } else {
            // Search by name
            recipes = recipeService.findByNameContainingIgnoreCaseOrderByDateDesc(name);
        }

        return ResponseEntity.ok(recipes);
    }
}
