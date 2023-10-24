package sv.com.jsoft.test.view;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Setter;
import org.primefaces.event.SelectEvent;
import sv.com.jsoft.test.model.TipoUnidadMedida;
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
public class TipoUnidadView implements Serializable {

    @Setter
    private TipoUnidadMedida tipoUnidad;

    {
        tipoUnidad = new TipoUnidadMedida();
    }

    @Inject
    CatalogoService catalogoService;

    private RestUtil res;

    @PostConstruct
    public void init() {
        res = RestUtil
                .builder()
                .clazz(TipoUnidadMedida.class)
                .endpoint("tipoUnidad/")
                .build();
    }

    public void guardar() {
        int codeResponse = res.callPersistir(tipoUnidad);
        JsfUtil.mensajeFromEnum(codeResponse != 200 ? TipoMensaje.ERROR : (tipoUnidad.esNuevo() ? TipoMensaje.INSERT : TipoMensaje.UPDATE));
        catalogoService.loadTipoUnidadMedida();
        nuevo();
    }

    public void nuevo() {
        tipoUnidad = new TipoUnidadMedida();
    }

    public void onRowSelect(SelectEvent<TipoUnidadMedida> event) {
        tipoUnidad = event.getObject();
    }

    public TipoUnidadMedida getTipoUnidad() {
        if (tipoUnidad == null) {
            tipoUnidad = new TipoUnidadMedida();
        }
        return tipoUnidad;
    }

}
