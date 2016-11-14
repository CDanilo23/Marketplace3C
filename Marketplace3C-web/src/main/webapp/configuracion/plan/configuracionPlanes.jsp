<%-- 
    Document   : ConfiguracionPlanes
    Created on : 14/09/2016, 07:26:57 AM
    Author     : cristian.ordonez
--%>

<%@page import="co.com.uniminuto.util.AccionesEnum"%>
<%@page import="java.util.List"%>
<%@page import="co.com.uniminuto.util.Conexion"%>
<%@page import="java.util.LinkedList"%>
<%@page import="co.com.uniminuto.entities.Plan"%>
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
        <!--<title>Configuracion Planes</title>-->
        <title> Planes</title>
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
                            <a href="#"><i class="fa fa-wrench fa-fw"></i>  Planes ofertados<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">

                                <li>
                                    <!--<a href="crearPlan.jsp">Crear nuevo Plan</a>-->
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <div id="page-wrapper">
                <form action="datosModificarPlan.jsp" method="get">
                    <table class='table table-hover table-bordered'>
                        <br>    
                        <!--<caption><h1>Configuracion Planes</h1></caption>-->
                        <caption><h1> Planes ofertados</h1></caption>
                        <br>
                        <tr>
                            <th>Nombre del plan</th>  
                            <th>Descripcion</th> 
                            <th>Costo</th>
                            <th>Dias</th>
                            <th>Noches</th>
                            <th>Parque</th>
                            <th>Hotel</th>
                            <th></th>
                            <th></th>
                            <th></th>
                        </tr>
                        <%
                            List<Plan> lp = new Conexion(AccionesEnum.ConsultarPlanes).getLpla();
                            for (Plan plan : lp) {
                                out.println("<tr>");
                                out.println("<td>" + plan.getNombrePlan() + "</td>");
                                out.println("<td>" + plan.getDescripcion() + "</td>");
                                out.println("<td>" + plan.getCosto() + "</td>");
                                out.println("<td>" + plan.getDias() + "</td>");
                                out.println("<td>" + plan.getNoches() + "</td>");
                                out.println("<td>" + plan.getIdParque().getParque()+ "</td>");
                                out.println("<td>" + plan.getIdHotel().getNombre()+ "</td>");
                                out.println("<td align='center'><img src='data:image/jpg;base64," + plan.getArchivo().getImgString() + "' alt='IMG DESC'></img></td>");
                                out.println("<td><a href='datosModificarEliminarPlan.jsp?idPlan=" + plan.getIdPlan()
                                        + "&nombrePlan=" + plan.getNombrePlan()
                                        + "&descripcion=" + plan.getDescripcion()
                                        + "&costo=" + plan.getCosto()
                                        + "&dias=" + plan.getDias()
                                        + "&noches=" + plan.getNoches()
                                        + "&idParque=" + plan.getIdParque().getIdParque() 
                                        + "&parque="+plan.getIdParque().getParque()
                                        + "&idHotel="+plan.getIdHotel().getIdHotel()
                                        + "&nomArchivo="+plan.getArchivo().getNombre()
                                        + "&idArchivo="+plan.getArchivo().getIdArchivo()
                                        + "&nomHotel="+plan.getIdHotel().getNombre()+"'><img src='../../img/modificar.jpg'/></a></td>");
                                out.println("<td><a href='datosModificarEliminarPlan.jsp?idPlan=" + plan.getIdPlan() +"'><img src='../../img/eliminar.jpg'/></a></td>");
                                out.println("</tr>");
                            }
                        %>

                    </table>
                </form> 
            </div>
        </div>
        <!-- jQuery -->
        <script src="../../bower_components/jquery/dist/jquery.min.js"></script>

        <!-- Bootstrap Core JavaScript -->
        <script src="../../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

        <!-- Metis Menu Plugin JavaScript -->
        <script src="../../bower_components/metisMenu/dist/metisMenu.min.js"></script>

        <!-- Custom Theme JavaScript -->
        <script src="../../dist/js/sb-admin-2.js"></script>
    </body>
</html>

