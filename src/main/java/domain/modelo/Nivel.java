package domain.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Nivel {
    private String name;
    private String id;
    private String author;
    private String difficulty;
    private String length;
}
