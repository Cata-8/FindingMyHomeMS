package cl.duoc.fichaSaludMS.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FichaSaludDTO {

    private Integer id;
    private String enfermedades;
    private String estadoReproductivo;
    private boolean microship;
    private boolean desparasitado;
    //nombre mascota
    private Integer idMascota;

    private List<RegistroVacunaDTO> vacunas;
}
