$(document).ready(function () {

    $.getJSON("DocentesCRUD", {opcion: 2}, function (data) {
        console.log(data.nombre);
        if(data.resultado==="ok")
            $("#lbldocente").text("DOCENTE: " + data.nombre);
        else
            $("#lbldocente").text("DOCENTE: ");
    });
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