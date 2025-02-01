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

                    return `<a class="btn btn-primary asignar-alumnos" href="asignacionalumnos.html?sesion=${row.sesion}" 
                data-sesion="${row.sesion}">
                Asignar Alumnos
                </a>`;
                }
            }
        ]
    });

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

    // Guardar actividad
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
    $('#miTabla').on('click', '.asignar-alumnos', function () {
        const sesion = $(this).data('sesion');

    });

});