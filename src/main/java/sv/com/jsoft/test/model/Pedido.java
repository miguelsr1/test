package sv.com.jsoft.test.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 *
 * @author migue
 */
@Data
public class Pedido implements Serializable, EntityPk {

    private static final long serialVersionUID = 1L;
    private Integer idPedido;
    private LocalDateTime fechaRegistro;
    private List<DetallePedido> detallePedidoList;
    private Integer idCliente;
    private Integer idEstadoPedido;
    private String user;

    public Pedido() {
    }

    public List<DetallePedido> getDetallePedidoList() {
        if (detallePedidoList == null) {
            detallePedidoList = new ArrayList<>();
        }
        return detallePedidoList;
    }

    @Override
    public Integer getId() {
        return idPedido;
    }

    @Override
    public boolean esNuevo() {
        return idPedido == null;
    }
}
