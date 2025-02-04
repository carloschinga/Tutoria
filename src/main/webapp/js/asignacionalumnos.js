$(document).ready(function () {
    
    
    function getParameter(name) {
        name = name.replace(/[\[\]]/g, '\\$&');
        let url = window.location.href;
        let regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)');
        let results = regex.exec(url);
        if (!results)
            return null;
        if (!results[2])
            return '';
        return decodeURIComponent(results[2].replace(/\+/g, ' '));
    }
    const sesion =  $("#txtSesion").val();//   getParameter("sesion");
    $.fn.traercabecera = function () {
        $.getJSON("AsistenciaTutoriaCRUD", {opcion: 2, sesion: sesion}, function (data) {
            if (data.resultado === "ok") {
                $("#titulo").text("Asignación de alumnos: " + data.Actividad);
                if (data.CantidadAsistencias > 0) {
                    asistencias = true;
                } else {
                    asistencias = false;
                }
            } else {
                if (data.mensaje === 'nopermiso') {
                    alert("Error: No tienes permiso para acceder aqui");
                } else {
                    alert("Error general: No se pudo cargar.");
                }
            }
        });
    };
    $.fn.traercabecera();
    $.fn.listar = function () {
        asignar = false;
            tabla = $('#tabla-estudiantes').DataTable();
            if (tabla) {
                tabla.destroy();
            }
            tabla = $('#tabla-estudiantes').DataTable({
                searching: false,
                paging: false,
                info: false,
                "ajax": {
                    "url": "AsistenciaTutoriaCRUD",
                    "data": function (d) {
                        d.opcion = 1
                                , d.sesion = sesion;
                    },
                    "dataSrc": function (json) {
                        // Verificar si la respuesta contiene "OK"
                        if (json.resultado === "ok") {
                            return json.data;
                        } else {
                            alert('Error: ' + json.mensaje);
                            return []; // Retorna un array vacío si no es "OK"
                        }
                    }
                },
                "columns": [
                    {"data": "CodigoUniversitario", "width": '20%'},
                    {
                        "data": "Nombres",
                        render: function (data, type, row, meta) {
                            // Escapar datos antes de usarlos
                            let nombres = $('<div>').text(data).html();
                            let apellidos = $('<div>').text(row.Apellidos).html();
                            return apellidos + " " + nombres;
                        }, "width": '40%'
                    },
                    {
                        "data": "Sesion",
                        render: function (data, type, row, meta) {
                            let codigoUniversitario = $('<div>').text(row.CodigoUniversitario).html();
                            let codigoSede = $('<div>').text(row.CodigoSede).html();
                            if (data === undefined) {
                                return "<input type='checkbox' class='asignar-checkbox' data-coduniv='" + codigoUniversitario + "' data-codsede='" + codigoSede + "' disabled>";
                            } else {
                                return "<input type='checkbox' class='asignar-checkbox' data-coduniv='" + codigoUniversitario + "' data-codsede='" + codigoSede + "'checked disabled>";
                            }
                        }, "width": '40%'
                    }
                ]
            });
    };
    $.fn.listar();
   

});