package cl.duoc.fichaSaludMS.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroVacunaDTO {

    private Integer idVacuna;
    private Date fecha;
    private Integer dosis;
}
