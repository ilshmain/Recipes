package recipes;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RECIPE")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Expose(serialize = false)
    private Long id;

    @NonNull
    @NotBlank
    @Expose(deserialize = false)
    private String name;

    @NonNull
    @NotBlank
    @Expose(deserialize = false)
    private String category;

    @Expose(deserialize = false)
    private String date;

    @NonNull
    @NotBlank
    @Expose(deserialize = false)
    private String description;

    @NonNull
    @NotEmpty
    @Expose(deserialize = false)
    private String[] ingredients;

    @NonNull
    @NotEmpty
    @Expose(deserialize = false)
    private String[] directions;
}
