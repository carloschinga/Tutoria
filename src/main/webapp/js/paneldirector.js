$(document).ready(function () {
    document.getElementById('btnCerrarSesion').addEventListener('click', function () {
        document.getElementById('cardDdetalle').style.display = 'none';
        $("#mensajeAccesoDenegado").show();

    });
    $("#cardDdetalle").load("asignaciontutores.html");

    $("#mnuAsignacionTutores").click(function () {
        $("#cardDdetalle").load("asignaciontutores.html");
    });
    $("#mnuReporteTutores").click(function () {
        $("#cardDdetalle").load("reportetutores.html");
    });
    $("#mnulistaReporteAlumnos").click(function () {
        $("#cardDdetalle").load("reportealumnos.html");
    });
    $("#mnulistaReporteAlumnossintutor").click(function () {
        $("#cardDdetalle").load("reportealumnossintutor.html");
    });
    $("#mnubusquedaportutor").click(function () {
        $("#cardDdetalle").load("busquedaportutor.html");
    });
    $("#mnuReporteActividades").click(function () {
        $("#cardDdetalle").load("reporteactividaddirector.html");
    });
    $("#mnulistaReporteAsistencias").click(function () {
        $("#cardDdetalle").load("reporteasistenciasdirector.html");
    });
    $("#mnulistaReporteincidencias").click(function () {
        $("#cardDdetalle").load("ReporteIncidenciasDirector.html");
    });
});