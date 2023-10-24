package sv.com.jsoft.test.view;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Setter;
import org.primefaces.event.SelectEvent;
import sv.com.jsoft.test.model.Producto;
import sv.com.jsoft.test.model.enums.TipoMensaje;
import sv.com.jsoft.test.services.CatalogoService;
import sv.com.jsoft.test.util.JsfUtil;
import sv.com.jsoft.test.util.RestUtil;

/**
 *
 * @author migue
 */
@Named
@ViewScoped
public class ProductoView implements Serializable {

    @Setter
    private Producto producto;

    {
        producto = new Producto();
    }

    @Inject
    CatalogoService catalogoService;

    private RestUtil res;

    @PostConstruct
    public void init() {
        res = RestUtil
                .builder()
                .clazz(Producto.class)
                .endpoint("item/")
                .build();
    }

    public void guardar() {
        int codeResponse = res.callPersistir(producto);
        JsfUtil.mensajeFromEnum(codeResponse != 200 ? TipoMensaje.ERROR : (producto.esNuevo() ? TipoMensaje.INSERT : TipoMensaje.UPDATE));
        catalogoService.loadProduct();
        nuevo();
    }

    public void nuevo() {
        producto = new Producto();
    }

    public void onRowSelect(SelectEvent<Producto> event) {
        producto = event.getObject();
    }

    public Producto getProducto() {
        if (producto == null) {
            producto = new Producto();
        }
        return producto;
    }

}
