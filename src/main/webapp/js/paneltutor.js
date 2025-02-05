$(document).ready(function () {
    $("#cardDdetalle").load("creacionactividad.html");

   $("#mnuActividad").click(function () {
        $("#cardDdetalle").load("creacionactividad.html");
    });
    $("#mnuAsistencia").click(function () {
        $("#cardDdetalle").load("asistencias.html");
    });
    $("#mnuIncidencias").click(function () {
        $("#cardDdetalle").load("incidencias.html");
    });
    $("#mnuReporteActividades").click(function () {
        $("#cardDdetalle").load("ReporteActividades.html");
    });
     $("#mnulistaReporteAsistencias").click(function () {
        $("#cardDdetalle").load("reporteasistencias.html");
    });
   
   
});