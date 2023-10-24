package sv.com.jsoft.test.model;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author migue
 */
@Data
public class TipoUnidadMedida implements Serializable, EntityPk {

    private static final long serialVersionUID = 1L;
    private Integer idUnidadMedida;
    private String codigo;
    private String descripcion;

    public TipoUnidadMedida() {
    }

    @Override
    public Integer getId() {
        return idUnidadMedida;
    }

    @Override
    public boolean esNuevo() {
        return idUnidadMedida == null;
    }
}
