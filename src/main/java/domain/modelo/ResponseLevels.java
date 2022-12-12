package domain.modelo;

import lombok.Data;

import java.util.List;

@Data
public class ResponseLevels {
    private List<ResponseLevelsItem> responseLevelsList;
}