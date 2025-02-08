$(document).ready(function () {
    const ahora = new Date();
    var tabla;

    // Ajustar los minutos a 00
    ahora.setMinutes(0, 0, 0);

    // Obtener la fecha en formato 'YYYY-MM-DDTHH:mm'
    const fechaFormateada = ahora.toISOString().slice(0, 16);

    // Asignar la fecha al input con jQuery
    $('#actividad-fecha').val(fechaFormateada);

    if (tabla) {
        tabla.destroy();
    }

    tabla = $('#tabla-actividades').DataTable({
        searching: false,
        paging: false,
        info: false,
        "ajax": {
            "url": "ActividadTutoriaCRUD",
            "type": "POST",
            "data": function (d) {
                d.opcion = 2;
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
            {"data": "sesion"},
            {"data": "actividad"},
            {"data": "nombretipoactividad"},
            {
                "data": "fecha",
                "render": function (data, type, row, meta) {
                    // Formatear la fecha para que muestre hasta los minutos
                    if (data) {
                        const fecha = new Date(data);
                        const opciones = {
                            year: 'numeric',
                            month: '2-digit',
                            day: '2-digit',
                            hour: '2-digit',
                            minute: '2-digit'
                        };
                        return fecha.toLocaleDateString('es-ES', opciones).replace(',', '');
                    }
                    return data;
                }
            },
            {"data": "lugar"},
            {"data": null,
                render: function (data, type, row, meta) {
                    return `<a class="btn btn-primary" id="btnAsignarAlumnos" href="#" data-sesion="${row.sesion}" >Asignar Alumnos</a>`;
                }
            }
        ]
    });

    $.getJSON("SemestreAcademicoCRUD", {opcion: 1}, function (data) {
        if (data.resultado === "ok") {
            $('#semestre').text(data.semestre);
            console.log(data.semestre);
        } else {
            if (data.mensaje === 'nopermiso') {
                alert("Error: No tienes permiso para acceder aqui");
            } else {
                alert("Error general: No se pudo cargar las semestres.");
            }
        }

    });
    $.getJSON("TipoActividadCRUD", {opcion: 1}, function (data) {
        if (data.resultado === "ok") {
            let actividad = $('#actividad-tipo');
            actividad.empty().append('<option value="" selected disabled>Selecciona el tipo</option>');

            $.each(data.data, function (key, value) {
                let codigoactividad = $('<div>').text(value.codtipact).html();
                let acti = $('<div>').text(value.nombtipact).html();
                actividad.append('<option value="' + codigoactividad + '">' + acti + '</option>');
            });

        } else {
            if (data.mensaje === 'nopermiso') {
                alert("Error: No tienes permiso para acceder aqui");
            } else {
                alert("Error general: No se pudo cargar Los datos.");
            }
        }
    });

    $('#crear').on('click', function () {
        $('#modal-agregar').modal('show');
    });
    $('#tabla-actividades').on('click', '#btnAsignarAlumnos', function () {

        let sesion = this.getAttribute("data-sesion");
        $('#txtSesion').val(sesion);

        $.fn.traercabecera();
        $.fn.detalle();

        $('#modal-asignara').modal('show');

    });
    $('#guardar-actividad').on('click', function () {
        let actividad = $('#actividad-nombre').val();
        let tipo = $('#actividad-tipo').val();
        let fecha = $('#actividad-fecha').val();
        let lugar = $('#actividad-lugar').val();

        // Validación básica
        if (actividad && tipo && fecha && lugar) {

            $.getJSON("ActividadTutoriaCRUD", {opcion: 1, actividad: actividad, tipo: tipo, fecha: fecha, lugar: lugar}, function (data) {
                if (data.resultado === "ok") {

                    tabla.ajax.reload();
                    $('#modal-agregar').modal('hide');

                } else {
                    if (data.mensaje === 'nopermiso') {
                        alert("Error: No tienes permiso para acceder aqui");
                    } else {
                        alert("Error general: No se pudo cargar.");
                    }
                }
            });
        } else {
            alert('Por favor, complete todos los campos.');
        }
    });

    //-----------------------------------------------------
    let tablaAlumno;
    let asignar = false;
    let asistencias = false;
    $.fn.getParameter = function (name) {
        name = name.replace(/[\[\]]/g, '\\$&');
        let url = window.location.href;
        let regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)');
        let results = regex.exec(url);
        if (!results)
            return null;
        if (!results[2])
            return '';
        return decodeURIComponent(results[2].replace(/\+/g, ' '));
    };
    $.fn.traercabecera = function () {
        const sesion = $("#txtSesion").val();


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

    $.fn.detalle = function () {
        const sesion = $("#txtSesion").val();
        asignar = false;
        tablaAlumno = $('#tabla-estudiantes').DataTable();
        if (tablaAlumno) {
            tablaAlumno.destroy();
        }


        tablaAlumno = $('#tabla-estudiantes').DataTable({
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

    $('#asignar').click(function () {
        if (asignar) {
            asignar = false;
            $("#asignar").text("Asignar");
            $("#Grabar").css("display", 'none');
            $("#div-todos").css("display", 'none');
            $('.asignar-checkbox').prop('disabled', true);
            tablaAlumno.ajax.reload();
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
        const sesion = $("#txtSesion").val();
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