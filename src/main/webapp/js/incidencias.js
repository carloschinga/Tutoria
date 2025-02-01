$(document).ready(function () {
    let tabla;

    $.getJSON("SemestreAcademicoCRUD", function (data) {
        if (data.resultado === "ok") {
            $('#semestre').text(data.semestre);
            console.log(data.semestre);
        } else {
            if (data.mensaje === 'nopermiso') {
                alert("Error: No tienes permiso para acceder aqui");
            } else {
                alert("Error general: No se pudo cargar las escuelas.");
            }
        }

    })
    $.getJSON("TutoriaCRUD", {opcion: 3}, function (data) {
        if (data.resultado === "ok") {
            let alumno = $('#alumno');
            alumno.empty().append('<option value="" selected disabled>Selecciona un alumno</option>');

            $.each(data.data, function (key, value) {
                let CodigoUniversitario = $('<div>').text(value.CodigoUniversitario).html();
                let CodigoSede = $('<div>').text(value.CodigoSede).html();
                let Nombres = $('<div>').text(value.Nombres).html();
                let Apellidos = $('<div>').text(value.Apellidos).html();
                alumno.append('<option value="' + CodigoUniversitario + '" data-codigo-sede="' + CodigoSede + '">' + Apellidos + " " + Nombres + '</option>');
            });
        } else {
            if (data.mensaje === 'nopermiso') {
                alert("Error: No tienes permiso para acceder aqui");
            } else {
                alert("Error general: No se pudo cargar los alumnos.");
            }
        }
    });
    $.getJSON("TipoIncidenciaCRUD", {opcion: 1}, function (data) {
        if (data.resultado === "ok") {
            let tipo = $('#tipo');
            let tipom = $('#tipo-modificar');
            tipo.empty().append('<option value="" selected disabled>Selecciona el tipo</option>');

            $.each(data.data, function (key, value) {
                let codigo = $('<div>').text(value.codigo).html();
                let incidencia = $('<div>').text(value.incidencia).html();
                tipo.append('<option value="' + codigo + '">' + incidencia + '</option>');
                tipom.append('<option value="' + codigo + '">' + incidencia + '</option>');
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
        $('#modal-incidencia').modal('show');
    });
    $.fn.listar = function () {
        let codsede = $("#alumno").find('option:selected').data('codigo-sede');
        let coduniv = $("#alumno").val();
        if (coduniv !== null && coduniv !== undefined && coduniv !== "") {
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
                    "url": "IncidenciasTutoriaCRUD",
                    "data": function (d) {
                        d.opcion = 2
                                , d.coduniv = coduniv, d.codsede = codsede;
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
                    {
                        "data": null,
                        render: function (data, type, row, meta) {
                            // Escapar datos antes de usarlos
                            let semestre = $('<div>').text(row.semestre).html();
                            let anio = $('<div>').text(row.anio).html();
                            return anio + "-" + semestre;
                        }
                    },
                    {"data": "fecha"},
                    {
                        "data": "observacion",
                        "render": function (data, type, row, meta) {
                            if (data) {
                                // Escapar el texto para evitar problemas de interpretación de HTML
                                var textoEscapado = $('<div>').text(data).html();

                                // Reemplazar los saltos de línea (\n) con <br> para mostrar correctamente en HTML
                                var textoConSaltos = textoEscapado.replace(/\n/g, '<br>');

                                // Devolver el texto con saltos de línea
                                return textoConSaltos;
                            }
                            return '';
                        }
                    },
                    {"data": "tipo"},
                    {
                        "data": "modificable",
                        "render": function (data, type, row, meta) {
                            if (data) {
                                return '<button type="button" data-codigotipo="' + row.codigotipo + '" data-item="' + row.item + '" class="btn btn-primary mr-1 btn-sm btn-modificar">Modificar</button><button type="button" class="btn btn-danger btn-sm btn-eliminar"  data-item="' + row.item + '" >Eliminar</button>';
                            }
                            return '';
                        }
                    }
                ]
            });
        }
    };
    $('#alumno').change(function () {
        $.fn.listar();
    });

    // Guardar actividad
    $('#guardar').on('click', function () {
        let codsede = $("#alumno").find('option:selected').data('codigo-sede');
        let coduniv = $("#alumno").val();
        let observacion = $('#observacion').val();
        let tipo = $('#tipo').val();

        // Validación básica
        if (observacion && tipo && coduniv && codsede) {

            $.ajax({
                url: "IncidenciasTutoriaCRUD?opcion=1&codsede=" + codsede + "&coduniv=" + coduniv + "&tipo=" + tipo,
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify({observacion: encodeURIComponent(observacion)}),
                dataType: "json",
                success: function (data) {
                    if (data.resultado === "ok") {
                        if (tabla) {
                            tabla.ajax.reload();
                        }
                        $('#modal-incidencia').modal('hide');
                    } else {
                        alert("Error, no se pudo agregar la incidencia.");
                    }
                },
                error: function (xhr, status, error) {
                    alert("Error de conexion con el servidor.");
                }
            });
        } else {
            alert('Por favor, complete todos los campos.');
        }
    });
    $('#tabla-estudiantes').on('click', '.btn-modificar', function () {
        let codtip = String($(this).data("codigotipo"));
        let item = String($(this).data("item"));
        // Seleccionar el tipo actual
        $('#tipo-modificar').val(codtip);

        var row = tabla.row($(this).closest('tr'));

        var data = row.data();
        var contenidoTextarea = data.observacion.replace(/<br\s*\/?>/gi, '\n');

// Escapar el contenido para manejar <br> como texto plano si es necesario
        $('#observacion-modificar').val(contenidoTextarea);

        // Guardar el ID de la incidencia en un atributo de botón para su uso posterior
        $('#guardar-modificacion').data('id', item);

        // Mostrar el modal de modificar
        $('#modal-modificar').modal('show');
    });

    $('#guardar-modificacion').on('click', function () {
        var id = $(this).data('id');
        var tipo = $('#tipo-modificar').val();
        var observacion = $('#observacion-modificar').val();
        let codsede = $("#alumno").find('option:selected').data('codigo-sede');
        let coduniv = $("#alumno").val();

        if (observacion && tipo && coduniv && codsede && id) {

            $.ajax({
                url: "IncidenciasTutoriaCRUD?opcion=3&codsede=" + codsede + "&coduniv=" + coduniv + "&tipo=" + tipo + "&item=" + id,
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify({observacion: encodeURIComponent(observacion)}),
                dataType: "json",
                success: function (data) {
                    if (data.resultado === "ok") {
                        if (tabla) {
                            $('#modal-modificar').modal('hide');
                            tabla.ajax.reload();
                        }
                        $('#modal-incidencia').modal('hide');
                    } else {
                        alert("Error, no se pudo agregar la incidencia.");
                    }
                },
                error: function (xhr, status, error) {
                    alert("Error de conexion con el servidor.");
                }
            });
        } else {
            alert('Error, no se encontraron todos los datos completos, vuelva a cargar la pagina y vuelva a intentarlo.');
        }
    });
    $('#tabla-estudiantes').on('click', '.btn-eliminar', function () {
        var id = $(this).data('item');
        let codsede = $("#alumno").find('option:selected').data('codigo-sede');
        let coduniv = $("#alumno").val();

        if (coduniv && codsede && id) {
            if (confirm("¿Quieres eliminar esta incidencia?")) {
                $.getJSON("IncidenciasTutoriaCRUD", {opcion: 4, item: id, codsede: codsede, coduniv: coduniv}, function (data) {
                    if (data.resultado === "ok") {
                        tabla.ajax.reload();
                    } else {
                        if (data.mensaje === 'nopermiso') {
                            alert("Error: No tienes permiso para acceder aqui");
                        } else {
                            alert("Error general: No se pudo eliminar la incidencia.");
                        }
                    }
                });
            } else {
            }
        } else {
            alert('Error, no se encontraron todos los datos completos, vuelva a cargar la pagina y vuelva a intentarlo.');
        }
    });
});