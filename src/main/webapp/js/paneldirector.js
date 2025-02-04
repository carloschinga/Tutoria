$(document).ready(function () {
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
});