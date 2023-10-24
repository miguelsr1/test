package sv.com.jsoft.test.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;
import sv.com.jsoft.test.model.Cliente;
import sv.com.jsoft.test.model.Producto;
import sv.com.jsoft.test.services.CatalogoService;

/**
 *
 * @author migue
 */
@Named
@FacesConverter(value = "clienteConverter", managed = true)
public class ClienteConverter implements Converter<Cliente> {

    @Inject
    private CatalogoService catService;

    @Override
    public Cliente getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                return catService.getClienteByCodigo(value);
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid cliente."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Cliente value) {
        if (value != null) {
            return value.getCodigo();
        } else {
            return null;
        }
    }
}
