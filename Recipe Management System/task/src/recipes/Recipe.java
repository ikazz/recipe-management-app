package recipes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Data // automatically generates getters/setters, hashCode, equals, toString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotBlank
    private String category;

    @Column(nullable = false)
    private LocalDateTime date;

    @PrePersist
    protected void onCreate() {
        date = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        date = LocalDateTime.now();
    }

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    @ElementCollection
    @Size(min = 1)
    @NotEmpty
    @CollectionTable(name = "recipe_ingredients", joinColumns = @JoinColumn(name = "recipe_id"))
    @OrderColumn(name = "ingredient_order")
    @Column(name = "ingredient")
    private List<String> ingredients;

    @NotNull
    @ElementCollection
    @Size(min = 1)
    @NotEmpty
    @CollectionTable(name = "recipe_directions", joinColumns = @JoinColumn(name = "recipe_id"))
    @OrderColumn(name = "direction_order")
    @Column(name = "direction")
    private List<String> directions;

    @NotBlank
    @JsonIgnore
    @Column(nullable = false)
    private String authorEmail;
}
