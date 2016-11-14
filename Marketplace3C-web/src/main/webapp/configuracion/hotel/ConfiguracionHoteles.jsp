<%-- 
    Document   : ConfiguracionHoteles
    Created on : 13/09/2016, 08:04:19 AM
    Author     : cristian.ordonez
--%>

<%@page import="co.com.uniminuto.util.AccionesEnum"%>
<%@page import="java.util.List"%>
<%@page import="co.com.uniminuto.util.Conexion"%>
<%@page import="co.com.uniminuto.ejb.HotelFacadeLocal"%>
<%@page import="co.com.uniminuto.ejb.UsuarioFacadeLocal"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="java.util.Properties"%>
<%@page import="co.com.uniminuto.entities.Hotel"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
         <!-- Bootstrap Core CSS -->
        <link href="../../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- MetisMenu CSS -->
        <link href="../../bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

        <!-- Custom CSS -->
        <link href="../../dist/css/sb-admin-2.css" rel="stylesheet">

        <!-- Custom Fonts -->
        <link href="../../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <title>Configuracion Hoteles</title>
    </head>
    <body>
        <div id="wrapper">
            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                        <li>
                            <a href="../indexConfig.jsp"><i class="fa fa-dashboard fa-fw"></i> Index</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-wrench fa-fw"></i> Configuracion Hoteles<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                               
                                <li>
                                    <a href="CrearHotel.jsp">Crear nuevo Hotel</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <div id="page-wrapper">
                <form action="datosModificarHotel.jsp" method="get"> 
                    <table class='table table-hover'>
                        <br>    
                        <caption><h1>Configuracion Hoteles</h1></caption>
                        <br>
                        <tr>
                            <th>Nombre del hotel</th>  
                            <th>Nivel</th> 
                            <th>Direccion</th> 
                            <th></th>
                            <th></th>
                        </tr>
                        <%
                           List<Hotel> lh = new Conexion(AccionesEnum.ConsultarHoteles).getLh();
                            for (Hotel hotel : lh) {
                                out.println("<tr>");
                                out.println("<td>" + hotel.getNombre() + "</td>");
                                out.println("<td>" + hotel.getNivel() + "</td>");
                                out.println("<td>" + hotel.getDireccion() + "</td>");
                                out.println("<td><a href='datosModificarHotel.jsp?id="+hotel.getIdHotel()+
                                        "&nombre="+hotel.getNombre()+
                                        "&nivel="+hotel.getNivel()+
                                        "&direccion="+hotel.getDireccion()+"'><img src='../../img/modificar.jpg'/></a></td>");
                                out.println("<td><a href='datosModificarHotel.jsp?id="+hotel.getIdHotel()+"'><img src='../../img/eliminar.jpg'/></a></td>");
                                out.println("</tr>");
                            }
                        %>

                    </table>
                </form>
            </div>
        </div>
         <script src="../../bower_components/jquery/dist/jquery.min.js"></script>

        <!-- Bootstrap Core JavaScript -->
        <script src="../../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

        <!-- Metis Menu Plugin JavaScript -->
        <script src="../../bower_components/metisMenu/dist/metisMenu.min.js"></script>

        <!-- Custom Theme JavaScript -->
        <script src="../../dist/js/sb-admin-2.js"></script>
    </body>
</html>

