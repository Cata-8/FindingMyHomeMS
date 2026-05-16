package cl.duoc.donacionMS.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonacionDetalleDTO {

    private Integer id;
    private Date fecha;
    
    private AdoptanteDTO adoptante;
    private RefugioDTO refugio;
}
