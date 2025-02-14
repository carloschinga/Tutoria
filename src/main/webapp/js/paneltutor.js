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
    $.getJSON("DocentesCRUD", {opcion: 2, _: new Date().getTime()}, function (data) {
      
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
    $("#mnuIncidencias").click(function () {
        $("#cardDdetalle").load("incidencias.html");
    });


});