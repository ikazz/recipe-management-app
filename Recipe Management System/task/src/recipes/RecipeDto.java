package recipes;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@RequiredArgsConstructor
public class RecipeDto {

    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @NotBlank
    private String description;

    @NotNull
    @Size(min = 1)
    private List<String> ingredients;

    @NotNull
    @Size(min = 1)
    private List<String> directions;
}
