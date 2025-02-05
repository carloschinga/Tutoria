$(document).ready(function () {
    let asignar = false;
    let tabla;
    let asistencias = false;

    $.getJSON("SemestreAcademicoCRUD",{opcion:1}, function (data) {
        if (data.resultado === "ok") {
            $('#semestre').text(data.semestre);
            console.log(data.semestre);
        } else {
            if (data.mensaje === 'nopermiso') {
                alert("Error: No tienes permiso para acceder aqui");
            } else {
                alert("Error general: No se pudo cargar los semestres academicos.");
            }
        }
    });
    $.getJSON("ActividadTutoriaCRUD", {opcion: 2}, function (data) {
        if (data.resultado === "ok") {
            let sesion = $('#sesion');
            sesion.empty().append('<option value="" selected disabled>Selecciona una sesion</option>');

            $.each(data.data, function (key, value) {
                let sesiontext = $('<div>').text(value.sesion).html();
                let actividad = $('<div>').text(value.actividad).html();
                sesion.append('<option value="' + sesiontext + '">' + actividad + '</option>');
            });
        } else {
            if (data.mensaje === 'nopermiso') {
                alert("Error: No tienes permiso para acceder aqui");
            } else {
                alert("Error general: No se pudo cargar las escuelas.");
            }
        }
    });
    $.fn.listar = function () {
        if ($('#sesion').val() !== null && $('#sesion').val() !== undefined && $('#sesion').val() !== "") {
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
                        d.opcion = 4
                                , d.sesion = $('#sesion').val();
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
                    {"data": "CodigoUniversitario"},
                    {
                        "data": "Nombres",
                        render: function (data, type, row, meta) {
                            // Escapar datos antes de usarlos
                            let nombres = $('<div>').text(data).html();
                            let apellidos = $('<div>').text(row.Apellidos).html();
                            return apellidos + " " + nombres;
                        }
                    },
                    {
                        "data": "Sesion",
                        render: function (data, type, row, meta) {
                            let dato = "";
                            if (row.Sesion === "A") {
                                dato = "Asistió";
                            } else if (row.Sesion === "F") {
                                dato = "Faltó";
                            } else if (row.Sesion === "J") {
                                dato = "Justificó";
                            }
                            let asistio = $('<div>')
                                    .addClass('Asistio')
                                    .text(dato)
                                    .prop('outerHTML');

                            let select = $('<select>')
                                    .css('display', 'none')
                                    .addClass('asistencia-select')
                                    .attr('data-codigouniversitario', row.CodigoUniversitario)
                                    .attr('data-codigosede', row.CodigoSede);

                            let options = {
                                'N': '',
                                'A': 'Asistió',
                                'F': 'Faltó',
                                'J': 'Justificó'
                            };

                            $.each(options, function (key, value) {
                                let option = $('<option>')
                                        .val(key)
                                        .text(value);
                                select.append(option);
                                if (key === String(data)) {
                                    option.attr('selected', 'selected'); // Añadir el atributo selected explícitamente
                                }
                            });

                            // Aplicar el valor y depurar el resultado
                            select.val(String(data));
                            console.log('Valor seleccionado:', select.val(), 'esperado:', String(data));
                            let container = $('<div>').append(select).append(asistio);
                            console.log(container.html());

                            // Retornar el HTML del contenedor
                            return container.html();
                        }

                    }
                ]
            });
        }
    };

    $("#asignar").click(function () {
        if (asignar) {
            asignar = false;
            $("#asignar").text("Asignar");
            $("#Grabar").css("display", 'none');
            $(".asistencia-select").css("display", 'none');
            $(".Asistio").css("display", 'block');
        } else {
            asignar = true;
            $("#asignar").text("Cancelar");
            $(".asistencia-select").css("display", 'inline-block');
            $("#Grabar").css("display", 'inline-block');
            $(".Asistio").css("display", 'none');
        }
    });
    $('#sesion').change(function () {
        asignar = false;
        $("#asignar").text("Asignar");
        $("#Grabar").css("display", 'none');
        $(".asistencia-select").css("display", 'none');
        $(".Asistio").css("display", 'block');
        $.fn.listar();
    });
    $("#Grabar").click(function () {
        let datos = [];
        const Docente = $("#Docentes").val();

        // Seleccionar todos los select con la clase .asistencia-select que estén visibles
        $('.asistencia-select').each(function () {
            // Obtener los valores de data-codigouniversitario y data-codigosede
            const coduniv = $(this).data('codigouniversitario');
            const codsede = $(this).data('codigosede');
            const asistencia = $(this).val(); // Valor seleccionado en el select

            // Agregar los datos al array
            datos.push({
                coduniv: coduniv,
                codsede: codsede,
                asistencia: asistencia
            });
        });

        if (datos.length > 0) {
            console.log(datos);
            $.ajax({
                url: "AsistenciaTutoriaCRUD?opcion=5&sesion=" + $('#sesion').val(),
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(datos),
                dataType: "json",
                success: function (data) {
                    if (data.resultado === "ok") {
                        var tabla = $('#tabla-estudiantes').DataTable();
                        if (tabla) {
                            tabla.ajax.reload();
                            asignar = false;
                            $("#asignar").text("Asignar");
                            $("#Grabar").css("display", 'none');
                            $(".asistencia-select").css("display", 'none');
                            $(".Asistio").css("display", 'block');
                        }
                    } else {
                        alert("Error al grabar.");
                    }
                },
                error: function (xhr, status, error) {
                    alert("Error con la conexion con el servidor");
                }
            });
        } else {
            alert("Error, No se encontraron Alumnos.");
        }
    });
});