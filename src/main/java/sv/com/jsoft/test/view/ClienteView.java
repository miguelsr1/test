package sv.com.jsoft.test.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.SelectEvent;
import sv.com.jsoft.test.model.Cliente;
import sv.com.jsoft.test.model.Municipio;
import sv.com.jsoft.test.model.enums.TipoMensaje;
import sv.com.jsoft.test.services.UbicacionService;
import sv.com.jsoft.test.util.JsfUtil;
import sv.com.jsoft.test.util.RestUtil;

/**
 *
 * @author migue
 */
@Named
@ViewScoped
public class ClienteView implements Serializable {

    private int tipoDoc;
    @Getter
    @Setter
    private String codigoDepa;
    @Getter
    @Setter
    private String duiContacto;
    @Getter
    @Setter
    private String nitContacto;
    @Getter
    @Setter
    private Integer idMuni;

    private List<Municipio> lstMunicipio;

    private Cliente cliente;
    @Getter
    private List<Cliente> lstCliente;

    private RestUtil res;

    @Inject
    UbicacionService ubicacionService;

    {
        tipoDoc = 1;
        cliente = new Cliente();
        lstMunicipio = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        res = RestUtil
                .builder()
                .clazz(Cliente.class)
                .endpoint("cliente/")
                .build();

        lstCliente = res.callGet();
    }

    public Cliente getCliente() {
        if(cliente == null){
            cliente = new Cliente();
        }
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        if (cliente != null) {
            this.cliente = cliente;
        }
    }

    public int getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(int tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public void nuevo() {
        cliente = new Cliente();
        duiContacto = "";
    }

    public void guardar() {
        if (cliente != null) {
            tipoDoc = (duiContacto.length() == 10) ? 1 : 2;
            cliente.setTipoDocumento(tipoDoc);
            cliente.setDocumentoContacto(duiContacto);
            cliente.setActivo(true);

            int codeResponse = res.callPersistir(cliente);

            JsfUtil.mensajeFromEnum(codeResponse != 200 ? TipoMensaje.ERROR : (cliente.esNuevo() ? TipoMensaje.INSERT : TipoMensaje.UPDATE));

            lstCliente = res.callGet();
            nuevo();
        }
    }

    public List<Municipio> getLstMunicipio() {
        return ubicacionService.findMunicipioByDepa(codigoDepa);
    }

    public void onRowSelect(SelectEvent<Cliente> event) {
        cliente = event.getObject();
        idMuni = cliente.getIdMunicipio();
        Municipio m = (Municipio) RestUtil.builder()
                .clazz(Municipio.class)
                .endpoint("catalogos/municipio/" + idMuni)
                .build().callGetById();
        codigoDepa = m.getCodigoDepartamento();
        tipoDoc = cliente.getTipoDocumento();
        duiContacto = cliente.getDocumentoContacto();
    }
}
