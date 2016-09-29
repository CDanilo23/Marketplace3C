/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.uniminuto.controlador;

//import co.com.uniminuto.dao.HotelDAO;
//import co.com.uniminuto.dao.ParqueDAO;
//import co.com.uniminuto.dao.PlanDAO;
//import co.com.uniminuto.dao.ProveedorDAO;
//import co.com.uniminuto.dao.UsuarioDAO;
//import co.com.uniminuto.modelo.Hotel;
//import co.com.uniminuto.modelo.Parque;
//import co.com.uniminuto.modelo.Plan;
//import co.com.uniminuto.modelo.Proveedor;
//import co.com.uniminuto.modelo.Usuario;
import co.com.uniminuto.ejb.HotelFacadeLocal;
import co.com.uniminuto.ejb.UsuarioFacadeLocal;
import co.com.uniminuto.entities.Hotel;
import co.com.uniminuto.entities.Usuario;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 
 */
public class Controlador extends HttpServlet {
    
    @EJB(mappedName = "HotelFacadeBean", lookup = "java:app/Marketplace2C-ejb-1.0-SNAPSHOT/HotelFacade!co.com.uniminuto.ejb.HotelFacadeLocal")
    private HotelFacadeLocal hotelFacadeLocal;
    
    @EJB(mappedName = "UsuarioFacadeBean", lookup = "java:app/Marketplace2C-ejb-1.0-SNAPSHOT/UsuarioFacade!co.com.uniminuto.ejb.UsuarioFacadeLocal")
    private UsuarioFacadeLocal usuarioFacadeLocal;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Para controlar peticiones del tipo POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //Un metodo que recibe todas las peticiones a si sea GET y POST

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        //La accion se va a guardar en un caja de texto oculto que nos dira que accion
        //debemos hacer
//        response.sendRedirect("configuracion/indexConfig.jsp");
        String accion = request.getParameter("accion");
        
        if (accion.equals("login")) {
            this.login(request, response);
        } 
//        else if (accion.equals("CrearParque")) {
//            this.crearParque(request, response);
//        } else if (accion.equals("ModificarParque")) {
//            this.modificarParque(request, response);
//        } else if (accion.equals("CrearHotel")) {
//            this.crearHotel(request, response);
//        }
    if (accion.equals("Modificar Hotel")) {
            this.modificarHotel(request, response);
    }
//        } else if (accion.endsWith("CrearPlan")) {
//            this.crearPlan(request, response);
//        } else if (accion.equals("ModificarPlan")) {
//            this.modificarPlan(request, response);
//        } else if (accion.equals("CrearProveedor")) {
//            this.crearProveedor(request, response);
//        } else if (accion.equals("ModificarProveedor")) {
//            this.modificarProveedor(request, response);
//        } else if (accion.equals("CrearPaquete")) {
//            this.crearPaquete(request, response);
//        }
    }
    
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Usuario> listUser = usuarioFacadeLocal.findUserByIdAndPass(request.getParameter("user"),request.getParameter("password"));
        if (listUser!=null && !listUser.isEmpty()) {
            response.sendRedirect("configuracion/indexConfig.jsp");
        }
    }
    
//    public void modificarParque(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        Parque parque = new Parque(request.getParameter("ciudadParque"),request.getParameter("paisParque"),request.getParameter("nombreParque"),request.getParameter("img"),null);
//        parque.setIdParque(Integer.valueOf(request.getParameter("idParque")));
//        ParqueDAO parqueDAO = new ParqueDAO();
//        parqueDAO.modificar(parque);
//        request.getSession().removeAttribute("parque");
//        response.sendRedirect("configuracion/ConfiguracionParques.jsp");
//    }
//    
//    public void crearParque(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        ParqueDAO parqueDAO = new ParqueDAO();
//        parqueDAO.eliminarPorId(Integer.valueOf(request.getParameter("idParque")));
//        response.sendRedirect("configuracion/EliminarParque.jsp");
//    }
//    
//    public void crearHotel(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        Hotel hotel = new Hotel(request.getParameter("nombreHotel"), request.getParameter("nivelHotel"), request.getParameter("direccion"), null);
//        HotelDAO hotelDAO = new HotelDAO();
//        hotelDAO.agregar(hotel);
//        response.sendRedirect("configuracion/ConfiguracionHoteles.jsp");
//    }
//    
    public void modificarHotel(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        
        Hotel hotel = new Hotel();
        hotel.setIdHotel(Integer.valueOf(request.getParameter("id")));
        hotel.setNombre(request.getParameter("nombre"));
        hotel.setNivel(request.getParameter("nivel"));
        hotel.setDireccion(request.getParameter("direccion"));
        hotelFacadeLocal.edit(hotel);
        response.sendRedirect("configuracion/ConfiguracionHoteles.jsp");
    }
//    
//    public void eliminarHotel(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        HotelDAO hotelDAO = new HotelDAO();
//        hotelDAO.eliminarHotelPorId(Integer.valueOf(request.getParameter("idHotel")));
//        response.sendRedirect("configuracion/EliminarHotel.jsp");
//    }
//    
//    public void modificarPlan(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        Plan plan = new Plan();
//        plan.setIdPlan(Integer.valueOf(request.getParameter("idPlan")));
//        plan.setNombrePlan(request.getParameter("nombre"));
//        plan.setCosto(Integer.valueOf(request.getParameter("costo")));
//        plan.setDescripcion(request.getParameter("descripcion"));
//        plan.setDias(Integer.valueOf(request.getParameter("dias")));
//        plan.setNoches(Integer.valueOf(request.getParameter("noches")));
//        Parque parque = new Parque();
//        parque.setIdParque(Integer.valueOf(request.getParameter("idParque")));
//        plan.setParque(parque);
//        PlanDAO planDAO = new PlanDAO();
//        planDAO.modificar(plan);
//        response.sendRedirect("configuracion/ConfiguracionPlanes.jsp");
//    }
//    
//    public void crearPlan(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        Plan plan = new Plan();
//        plan.setNombrePlan(request.getParameter("nombre"));
//        plan.setCosto(Integer.valueOf(request.getParameter("costo")));
//        plan.setDescripcion(request.getParameter("descripcion"));
//        plan.setDias(Integer.valueOf(request.getParameter("dias")));
//        plan.setNoches(Integer.valueOf(request.getParameter("noches")));
//        Parque parque = new Parque();
//        parque.setIdParque(Integer.valueOf(request.getParameter("idParque")));
//        plan.setParque(parque);
//        PlanDAO planDao = new PlanDAO();
//        planDao.agregar(plan);
//        response.sendRedirect("configuracion/ConfiguracionPlanes.jsp");
//    }
//    
//    public void crearProveedor(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        ProveedorDAO proveedorDAO = new ProveedorDAO();
//        Proveedor proveedor = new Proveedor();
//        proveedor.setEmpresa(request.getParameter("empresa"));
//        proveedor.setNombre(request.getParameter("nombreProveedor"));
//        proveedor.setNit(request.getParameter("nit"));
//        proveedor.setDireccion(request.getParameter("direccion"));
//        proveedor.setTelefono(Integer.valueOf(request.getParameter("telefono")));
//        proveedorDAO.agregar(proveedor);
//        response.sendRedirect("configuracion/ConfiguracionProveedores.jsp");
//    }
//    
//    public void modificarProveedor(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        Proveedor proveedor = new Proveedor();
//        proveedor.setIdProveedor(Integer.valueOf(request.getParameter("idProveedor")));
//        proveedor.setEmpresa(request.getParameter("empresa"));
//        proveedor.setNombre(request.getParameter("nombre"));
//        proveedor.setNit(request.getParameter("nit"));
//        proveedor.setDireccion(request.getParameter("direccion"));
//        proveedor.setTelefono(Integer.valueOf(request.getParameter("telefono")));
//        ProveedorDAO proveedorDAO = new ProveedorDAO();
//        proveedorDAO.modificar(proveedor);
//        request.getSession().removeAttribute("proveedor");
//        response.sendRedirect("configuracion/ConfiguracionProveedores.jsp");
//    }
//
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
}
