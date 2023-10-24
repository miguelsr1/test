package sv.com.jsoft.test.view;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import lombok.Getter;
import lombok.Setter;
import sv.com.jsoft.test.model.Cliente;
import sv.com.jsoft.test.model.DetallePedido;
import sv.com.jsoft.test.model.EstadoPedido;
import sv.com.jsoft.test.model.Operador;
import sv.com.jsoft.test.model.Pedido;
import sv.com.jsoft.test.model.Producto;
import sv.com.jsoft.test.model.enums.TipoMensaje;
import sv.com.jsoft.test.util.JsfUtil;
import sv.com.jsoft.test.util.RestUtil;

/**
 *
 * @author migue
 */
@ViewScoped
@Named
public class PedidoView implements Serializable {

    @Getter
    @Setter
    private String nit;
    private Integer numeroPedido;

    private Date fechaPedido;
    private Cliente cliente;
    private Pedido pedido;
    @Getter
    @Setter
    private DetallePedido detPedido;
    @Getter
    @Setter
    private Producto producto;

    @Inject
    SecurityContext securityContext;

    private RestUtil res;

    {
        fechaPedido = new Date();
        cliente = new Cliente();
        pedido = new Pedido();
        numeroPedido = 0;
        detPedido = new DetallePedido();
    }

    @PostConstruct
    public void PedidoView() {
        res = RestUtil
                .builder()
                .clazz(Cliente.class)
                .endpoint("pedido/")
                .build();
    }

    //==========================================================================
    public Date getFechaPedido() {
        return fechaPedido;
    }

    public Integer getNumeroPedido() {
        return numeroPedido;
    }

    public Cliente getCliente() {
        if (cliente == null) {
            cliente = new Cliente();
        }
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Pedido getPedido() {
        return pedido;
    }

    //==========================================================================
    public void agregarProductoADetallePedido() {
        detPedido.setIdProducto(producto);
    }

    public void agregarClienteAPedido() {
        pedido.setIdCliente(cliente.getId());
    }

    public void agregarItem() {
        detPedido.setActivo(Boolean.TRUE);
        
        pedido.getDetallePedidoList().add(detPedido);
        producto = new Producto();
        detPedido = new DetallePedido();
    }

    public Double getSubTotal() {
        return pedido.getDetallePedidoList().stream()
                .mapToDouble(d -> d.getSubTotal())
                .sum();
    }

    public Double getIva() {
        return 0d;
    }

    public Double getTotal() {
        return pedido.getDetallePedidoList().stream()
                .mapToDouble(d -> d.getSubTotal())
                .sum();
    }

    public void nuevo() {
        pedido = new Pedido();
        cliente = new Cliente();
    }

    public void guardar() {
        if (pedido != null) {
            pedido.setIdCliente(cliente.getId());
            pedido.setIdEstadoPedido(1);
            pedido.setUser(securityContext.getCallerPrincipal().getName());
            pedido.setFechaRegistro(LocalDateTime.now());

            int codeResponse = res.callPersistir(pedido);

            JsfUtil.mensajeFromEnum(codeResponse != 200 ? TipoMensaje.ERROR : (pedido.esNuevo() ? TipoMensaje.INSERT : TipoMensaje.UPDATE));

            nuevo();
        }
    }
}
