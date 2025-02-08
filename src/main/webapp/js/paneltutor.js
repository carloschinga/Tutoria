$(document).ready(function () {
    document.getElementById('btnCerrarSesion').addEventListener('click', function () {
        document.getElementById('lbldocente').style.display = 'none';
        document.getElementById('cardDdetalle').style.display = 'none';
    });
    $.getJSON("DocentesCRUD", {opcion: 2, _: new Date().getTime()}, function (data) {
        console.log("Respuesta del servidor:", data);

        if (data.resultado === "ok" && data.nombre) {
            $("#lbldocente").text("DOCENTE: " + data.nombre);
        } else {
            console.warn("Error o docente no encontrado:", data.mensaje);
            $("#lbldocente").text("DOCENTE: No disponible");
        }
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.error("Error en la petición AJAX:", textStatus, errorThrown);
        $("#lbldocente").text("DOCENTE: Error al obtener datos");
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
    $("#mnulistaReporteincidencias").click(function () {
        $("#cardDdetalle").load("reporteincidencias.html");
    });


});