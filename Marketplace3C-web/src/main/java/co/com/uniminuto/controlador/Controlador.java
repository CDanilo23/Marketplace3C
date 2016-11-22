/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.uniminuto.controlador;

import co.com.uniminuto.ejb.ArchivoFacadeLocal;
import co.com.uniminuto.ejb.HotelFacadeLocal;
import co.com.uniminuto.ejb.ParqueFacadeLocal;
import co.com.uniminuto.ejb.PlanFacadeLocal;
import co.com.uniminuto.ejb.UsuarioFacadeLocal;
import co.com.uniminuto.entities.Archivo;
import co.com.uniminuto.entities.Hotel;
import co.com.uniminuto.entities.Parque;
import co.com.uniminuto.entities.Plan;
import co.com.uniminuto.entities.Rol;
import co.com.uniminuto.entities.Ubicacion;
import co.com.uniminuto.entities.Usuario;
import co.com.uniminuto.util.ControladorEnvioCorreo;
import co.com.uniminuto.util.EstadoEnum;
import co.com.uniminuto.util.GeneradorMD5;
import co.com.uniminuto.util.RolEnum;
import co.com.uniminuto.util.TipoDocumentoEnum;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author cristian
 */
@MultipartConfig(maxFileSize = 16177215)
@ManagedBean(name = "controlador")
@ViewScoped
public class Controlador implements Serializable {

    private String usuario;
    private String password;
    private Plan planCurrent;
    private Hotel hotelCurrent;
    private Parque parqueCurrent;
    private String destination = "C:\\Users\\pc\\Documents\\NetBeansProjects\\Marketplace2Corte\\Marketplace2Corte\\Marketplace3C-web\\src\\main\\webapp\\img\\";
    private File file;

    @EJB(mappedName = "UsuarioFacadeBean", lookup = "java:app/Marketplace3C-ejb-1.0-SNAPSHOT/UsuarioFacade!co.com.uniminuto.ejb.UsuarioFacadeLocal")
    private UsuarioFacadeLocal usuarioFacadeLocal;

    @EJB(mappedName = "ParqueFacadeBean", lookup = "java:app/Marketplace3C-ejb-1.0-SNAPSHOT/ParqueFacade!co.com.uniminuto.ejb.ParqueFacadeLocal")
    protected ParqueFacadeLocal parqueFacadeLocal;

    @EJB(mappedName = "ArchivoFacadeBean", lookup = "java:app/Marketplace3C-ejb-1.0-SNAPSHOT/ArchivoFacade!co.com.uniminuto.ejb.ArchivoFacadeLocal")
    protected ArchivoFacadeLocal archivoFacadeLocal;

    @EJB(mappedName = "PlanFacadeBean", lookup = "java:app/Marketplace3C-ejb-1.0-SNAPSHOT/PlanFacade!co.com.uniminuto.ejb.PlanFacadeLocal")
    protected PlanFacadeLocal planFacadeLocal;

    @EJB(mappedName = "HotelFacadeBean", lookup = "java:app/Marketplace3C-ejb-1.0-SNAPSHOT/HotelFacade!co.com.uniminuto.ejb.HotelFacadeLocal")
    protected HotelFacadeLocal hotelFacadeLocal;

    public Controlador() {
        this.planCurrent = new Plan();
        this.hotelCurrent = new Hotel();
        this.parqueCurrent = new Parque();
        this.planCurrent.setIdHotel(hotelCurrent);
        this.planCurrent.setIdParque(parqueCurrent);
    }

    public void login() {
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            List<Usuario> listUser = usuarioFacadeLocal.findUserByIdAndPass(usuario, password);            
            if (!listUser.isEmpty()) {

                switch (listUser.get(0).getRol().getIdRol()) {
                    case 1:
                        externalContext.redirect("faces/configuracion/indexConfig.xhtml");
                        break;
                    case 2:
                        externalContext.redirect("faces/Cliente/indexConfig.xhtml?idUser=" + listUser.get(0).getIdUsuario());
                        break;
                    case 3:
                        externalContext.redirect("faces/Cliente/indexCliente.xhtml");
                        break;
                    default:
                        externalContext.redirect("login.xhtml");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Alvertencia!", "El usuario no tiene rol asignado en el sistema"));
                        break;
                }
            } else {
                externalContext.redirect("login.xhtml");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Alvertencia!", "El usuario no existe en el sistema"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Error al redireccionar a la pagina solicitada"));
        }
    }

    public void modificarPlan(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Plan plan = planFacadeLocal.find(Integer.valueOf(request.getParameter("idPlan")));
        request.getSession().removeAttribute("plan");
        plan.setNombrePlan(request.getParameter("nombre"));
        plan.setCosto(Integer.valueOf(request.getParameter("costo")));
        plan.setDescripcion(request.getParameter("descripcion"));
        plan.setDias(Integer.valueOf(request.getParameter("dias")));
        plan.setNoches(Integer.valueOf(request.getParameter("noches")));
        Parque parque = new Parque();
        parque.setIdParque(Integer.valueOf(request.getParameter("idParque")));
        plan.setIdParque(parque);
        Hotel hotel = new Hotel();
        hotel.setIdHotel(Integer.valueOf(request.getParameter("idHotel")));
        plan.setIdHotel(hotel);
        planFacadeLocal.merge(plan);
        response.sendRedirect("configuracion/plan/configuracionPlanes.jsp");
    }

    public void crearPlan() throws IOException, ServletException {

      if(file != null){  
        Archivo archivo = new Archivo();
        archivo.setNombre(file.getName());
        archivo.setImg(file.getName());
        archivo = archivoFacadeLocal.merge(archivo);
        planCurrent.setIdArchivo(archivo);
        planFacadeLocal.create(planCurrent);
        
        FacesContext.getCurrentInstance().getExternalContext().redirect("configuracionPlanes.xhtml");
      }
    }

    public void crearProveedor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Usuario usuProve = new Usuario();
        usuProve.setUsuario(request.getParameter("usuario"));
        usuProve.setEmpresa(request.getParameter("empresa"));
        usuProve.setNombre(request.getParameter("nombreProveedor"));
        usuProve.setContrasena(request.getParameter("password"));
        usuProve.setTipoDocumento(Integer.valueOf(request.getParameter("tipoDocumento")));
        usuProve.setNumeroDocumento(Integer.valueOf(request.getParameter("numDocumento")));
        usuProve.setDireccion(request.getParameter("direccion"));
        usuProve.setTelefono(request.getParameter("telefono"));
        usuProve.setCorreo(request.getParameter("correo"));
        usuProve.setRol(new Rol(RolEnum.PROVEEDOR.getValor()));
        usuProve.setEstado(EstadoEnum.ACTIVO.getValor());
        usuarioFacadeLocal.create(usuProve);
        response.sendRedirect("configuracion/proveedor/configuracionProveedores.jsp");
    }

    private void eliminarProveedor(Usuario usuario) throws IOException {
        usuarioFacadeLocal.remove(usuario);
    }

//    private void crearPaquete(HttpServletRequest request, HttpServletResponse response) {
//        Plan plan =new  Plan();
//        Parque parque = new Parque();
//        Proveedor proveedor = new Proveedor();
//        Hotel hotel = new Hotel();
//        hotel.setIdHotel(Integer.valueOf(request.getParameter("Hotel")));
//        parque.setIdParque(Integer.valueOf(request.getParameter("Parque")));
//        proveedor.setIdProveedor(Integer.valueOf(request.getParameter("Proveedor")));
//        PlanDAO planDAO = new PlanDAO();
//                plan.setNombrePlan(request.getParameter("NombrePaquete"));
//                plan.setDescripcion(request.getParameter("Descripcion"));
//                plan.setHotel(hotel); 
//                plan.setParque(parque);
//                plan.setProveedor(proveedor);
//                plan.setCosto(Integer.valueOf(request.getParameter("Costo")));
//                plan.setNoches(Integer.valueOf(request.getParameter("Noche")));
//                plan.setDias(Integer.valueOf(request.getParameter("Noche"))+1);
//                plan.setDescuento(Integer.valueOf(request.getParameter("Descuento")));
//                
    //planDAO.agregar();
//    }
    public void aceptarCliente(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        Usuario usuario = usuarioFacadeLocal.find(request.getSession().getAttribute("idCliente"));
        request.getSession().removeAttribute("idCliente");
        usuario.setEstado(EstadoEnum.ACTIVO.getValor());
        usuarioFacadeLocal.edit(usuario);
        ControladorEnvioCorreo.envioCorreo(usuario);
        response.sendRedirect("configuracion/cliente/configuracionClientes.jsp");
    }

    private void registroCliente(HttpServletRequest request, HttpServletResponse response) {
        String usu = request.getParameter("usuario");
        String nom = request.getParameter("nombre");
        String ced = request.getParameter("cedula");
        String correo = request.getParameter("correo");
        if (usuarioFacadeLocal.findUserByUserAndName(usu, nom).isEmpty()) {
            Usuario user = new Usuario();
            user.setNombre(nom);
            user.setUsuario(usu);
            user.setNumeroDocumento(Integer.valueOf(ced));
            user.setContrasena(GeneradorMD5.getRandomPass());
            user.setCorreo(correo);
            user.setTipoDocumento(TipoDocumentoEnum.CEDULA.getValor());
            Rol rol = new Rol(RolEnum.CLIENTE.getValor());
            user.setRol(rol);
            user.setEstado(EstadoEnum.INACTIVO.getValor());
            usuarioFacadeLocal.create(user);
            try {
                request.getSession().setAttribute("ex", new Exception("El usuario fue registrado exitosamente. En unos momentos llegara a su correo las credenciales de ingreso.", new Throwable("Info")));
                response.sendRedirect("registro/register.jsp");
            } catch (IOException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            request.getSession().setAttribute("ex", new Exception("El usuario ya existe en el sistema."));
            try {
                response.sendRedirect("registro/register.jsp");
            } catch (IOException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void registroParcialCliente(Usuario u) throws GeneralSecurityException {
        Usuario user = usuarioFacadeLocal.find(u.getIdUsuario());
        ControladorEnvioCorreo.envioCorreo(user);
        user.setEstado(EstadoEnum.ACTIVO.getValor());
        usuarioFacadeLocal.edit(user);
    }

    public void elimiarPaquete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        planFacadeLocal.remove(new Plan(Integer.valueOf(request.getParameter("idPaquete"))));
        response.sendRedirect("Proveedor/ConsultarPaquetes.jsp?idUser=" + request.getParameter("idUser"));
    }

    public void salir() {
        try {
            ExternalContext ex = FacesContext.getCurrentInstance().getExternalContext();
            ex.redirect("../login.xhtml");
        } catch (Exception e) {

        }
    }

    public void upload(FileUploadEvent event) {
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
            FacesMessage message = new FacesMessage("El archivo se ha subido con éxito!");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void copyFile(String fileName, InputStream in) {
        try {
            file = new File(destination, fileName);
            OutputStream out = new FileOutputStream(new File(destination + fileName));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();
//            System.out.println("El archivo se ha creado con éxito!");

//            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");
//            Date date = new Date();
//            String ruta1 = destination + fileName;
//            String ruta2 = destination + dateFormat.format(date)+"-"+fileName;
//            System.out.println("Archivo: "+ruta1+" Renombrado a: "+ruta2);           
//            File archivo=new File(ruta1);
//            archivo.renameTo(new File(ruta2));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public SelectItem[] getComboParques() {

        SelectItem[] items;
        List<Parque> parques = parqueFacadeLocal.listarParques();

        int size = true ? parques.size() + 1 : parques.size();
        items = new SelectItem[size];
        int i = 0;
        if (true) {
            items[0] = new SelectItem("", "-seleccione uno-");
            i++;
        }
        for (Parque parque : parques) {
            items[i++] = new SelectItem(parque.getIdParque(), parque.getParque());
        }
        return items;

    }

    public SelectItem[] getComboHoteles() {

        SelectItem[] items;
        List<Hotel> hoteles = hotelFacadeLocal.findAllHoteles();

        int size = true ? hoteles.size() + 1 : hoteles.size();
        items = new SelectItem[size];
        int i = 0;
        if (true) {
            items[0] = new SelectItem("", "-seleccione uno-");
            i++;
        }
        for (Hotel hotel : hoteles) {
            items[i++] = new SelectItem(hotel.getIdHotel(), hotel.getNombre());
        }
        return items;

    }

    public List<Hotel> getListaHoteles() {
        return hotelFacadeLocal.findAllHoteles();
    }

    public List<Parque> getListaParques() {
        return parqueFacadeLocal.listarParques();
    }

    public List<Plan> getListaPlanes() {
        return planFacadeLocal.findAll();
    }
    
    public List<Usuario> getListaProveedores(){
        return usuarioFacadeLocal.findByRol(new Rol(RolEnum.PROVEEDOR.getValor()));
    }

    public UsuarioFacadeLocal getUsuarioFacadeLocal() {
        return usuarioFacadeLocal;
    }

    public void setUsuarioFacadeLocal(UsuarioFacadeLocal usuarioFacadeLocal) {
        this.usuarioFacadeLocal = usuarioFacadeLocal;
    }

    public List<Usuario> getListaClientesPendientes() {
        return usuarioFacadeLocal.findByRol(new Rol(RolEnum.CLIENTE.getValor()));
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Plan getPlanCurrent() {
        return planCurrent;
    }

    public void setPlanCurrent(Plan planCurrent) {
        this.planCurrent = planCurrent;
    }

}
