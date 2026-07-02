package com.obra.obra.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponseDTO {

    private List<ItemAgregadoDTO> materiaisMaisComprados;
    private List<CategoriaGastoDTO> gastoPorCategoria;
}
