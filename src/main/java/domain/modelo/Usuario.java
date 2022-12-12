package domain.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Usuario {
    private String username;
    private int stars;
    private int diamonds;
    private int coins;
    private int userCoins;
    private int demons;
    private int cp;
}