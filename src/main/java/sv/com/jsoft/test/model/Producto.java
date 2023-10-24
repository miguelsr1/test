package sv.com.jsoft.test.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 *
 * @author migue
 */
@Data
public class Producto implements Serializable, EntityPk {

    private static final long serialVersionUID = 1L;
    private Integer idProducto;
    private String codigo;
    private String nombre;
    private Double precioUnitario;
    private Boolean activo = Boolean.TRUE;
    private Integer idUnidadMedida;

    private Boolean eliminado;

    {
        precioUnitario = 0d;
    }

    public Producto() {
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre;
    }

    @Override
    public Integer getId() {
        return idProducto;
    }

    @Override
    public boolean esNuevo() {
        return idProducto == null;
    }
}
