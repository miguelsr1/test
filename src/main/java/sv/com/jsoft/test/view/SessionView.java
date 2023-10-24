package sv.com.jsoft.test.view;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import lombok.Getter;

/**
 *
 * @author migue
 */
@Named
@SessionScoped
public class SessionView implements Serializable {

    @Getter
    private String user;

    @Inject
    SecurityContext securityContext;

    @PostConstruct
    public void init() {
        user = securityContext.getCallerPrincipal().getName();
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().clear();
        context.getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";
    }

    public String goHome() {
        return "/app/home?faces-redirect=true";
    }

    public String getUser() {
        return user;
    }
}
