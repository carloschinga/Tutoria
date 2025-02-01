$(document).ready(function () {
    let asignar = false;
    let tabla;
    let asistencias = false;
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
    const sesion = getParameter("sesion");
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
    $('#asignar').click(function () {
        if (asignar) {
            asignar = false;
            $("#asignar").text("Asignar");
            $("#Grabar").css("display", 'none');
            $("#div-todos").css("display", 'none');
            $('.asignar-checkbox').prop('disabled', true);
            tabla.ajax.reload();
        } else {
            if (asistencias) {
                if (confirm("Ya han sido asignados los alumnos anteriormente ¿Estas seguro que quieres volver a asignar? si ya registro asistencias, tendra que verificarlas.")) {
                    asignar = true;
                    $("#asignar").text("Cancelar");
                    $("#Grabar").css("display", 'inline-block');
                    $("#div-todos").css("display", 'inline-block');
                    $('.asignar-checkbox').prop('disabled', false);

                    if ($('.asignar-checkbox:checked').length === $('.asignar-checkbox').length) {
                        $('#select-all-checkbox').prop('checked', true);
                    } else {
                        $('#select-all-checkbox').prop('checked', false);
                    }
                } else {
                }
            } else {
                asignar = true;
                $("#asignar").text("Cancelar");
                $("#Grabar").css("display", 'inline-block');
                $("#div-todos").css("display", 'inline-block');
                $('.asignar-checkbox').prop('disabled', false);

                if ($('.asignar-checkbox:checked').length === $('.asignar-checkbox').length) {
                    $('#select-all-checkbox').prop('checked', true);
                } else {
                    $('#select-all-checkbox').prop('checked', false);
                }
            }
        }
    });


    // Evento para el checkbox "Seleccionar Todos"
    $('#select-all-checkbox').on('click', function () {
        var isChecked = $(this).prop('checked');

        // Selecciona o deselecciona todos los checkboxes de la tabla
        $('.asignar-checkbox').each(function () {
            $(this).prop('checked', isChecked);
        });
    });

    // Si algún checkbox individual es deseleccionado, desmarca el "Seleccionar Todos"
    $('#tabla-estudiantes').on('click', '.asignar-checkbox', function () {
        if (!$(this).prop('checked')) {
            $('#select-all-checkbox').prop('checked', false);
        } else {
            // Si todos los checkboxes están seleccionados, marca el "Seleccionar Todos"
            if ($('.asignar-checkbox:checked').length === $('.asignar-checkbox').length) {
                $('#select-all-checkbox').prop('checked', true);
            }
        }
    });
    $("#Grabar").click(function () {
        let datos = [];
        // Seleccionar todos los checkboxes con la clase .checkbox-plantilla que estén marcados
        $('.asignar-checkbox:checked').each(function () {
            // Obtener los valores de data-exacod y data-item
            const coduniv = $(this).data('coduniv');
            const codsede = $(this).data('codsede');

            // Agregar los datos al array
            datos.push({
                coduniv: coduniv,
                codsede: codsede
            });
        });

        if (datos.length > 0) {
            if (sesion !== null && sesion !== undefined && sesion !== "") {
                console.log(datos);
                $.ajax({
                    url: "AsistenciaTutoriaCRUD?opcion=3&sesion=" + sesion,
                    type: "POST",
                    contentType: "application/json",
                    data: JSON.stringify(datos),
                    dataType: "json",
                    success: function (data) {
                        if (data.resultado === "ok") {
                            asignar = false;
                            $("#asignar").text("Asignar");
                            $("#Grabar").css("display", 'none');
                            $("#div-todos").css("display", 'none');
                            $('.asignar-checkbox').prop('disabled', true);
                            tabla.ajax.reload();
                        } else {
                            alert("Error al grabar.");
                        }
                    },
                    error: function (xhr, status, error) {
                        alert("Error con la conexion con el servidor");
                    }
                });
            } else {
                alert("No se encontro la sesion a asignar, vuelva a ingresar a esta ventana.");
            }
        } else {
            alert("Seleccione almenos un alumno.");
        }
    });

});