$(document).ready(function () {
    // Asigna evento al botón de cerrar sesión
    $("#btnCerrarSesion").click(cerrarSesion);

    function cerrarSesion() {
        fetch('LogoutServlet', {
            method: 'GET',
            credentials: 'same-origin'
        })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`Error HTTP: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    if (data.status === "success") {
                        window.location.href="cerrarsesion.html";
                    } else {
                        alert("Error al cerrar sesión. Inténtelo de nuevo.");
                    }
                })
                .catch(error => {
                    console.error("Error al cerrar sesión:", error);
                    alert("Ocurrió un error al cerrar sesión.");
                });
    }
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
        $("#cardDdetalle").load("reporteincidencias.html");
    });
});