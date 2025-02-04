$(document).ready(function () {
    // Cargar escuelas
    $.getJSON("EscuelasCRUD", {opcion: 1}, function (data) {
        if (data.resultado === "ok") {
            let escuelas = $('#cmbEscuelas');
            escuelas.empty().append('<option value="" selected disabled>Selecciona una escuela</option>');

            $.each(data.escuelas, function (key, value) {
                let codigoEscuela = $('<div>').text(value.codigoEscuela).html();
                let escuela = $('<div>').text(value.escuela).html();
                escuelas.append('<option value="' + codigoEscuela + '">' + escuela + '</option>');
            });
        } else {
            $.fn.manejarError(data.mensaje, "escuelas");
        }
    });

    // Cargar semestres
    $.getJSON("SemestreAcademicoCRUD", {opcion: 2}, function (data) {
        if (data.resultado === "ok") {
            let semestres = $('#cmbSemestreAcademico');
            semestres.empty().append('<option value="" selected disabled>Selecciona un Semestre</option>');
            $.each(data.semestres, function (key, value) {
                let semestre = $('<div>').text(value.Semestre).html();
                semestres.append('<option value="' + value.CodigoSemestre + '">' + semestre + '</option>');
            });
            seleccionarSemestreActual();
        } else {
            alert("Error al cargar los semestres.");
        }
    });

    // Función para mostrar el modal con mensaje
    $.fn.mostrarPopup = function (mensaje) {
        $('#MensajeModal').html(mensaje);
        $('#mensajeModal').modal('show');
    };

    // Cerrar modal al hacer clic en el botón de cerrar o cancelar
    $('#mensajeModal .close, #mensajeModal .btn-secondary').on('click', function () {
        $('#mensajeModal').modal('hide');
    });

    // Ejecutar búsqueda al hacer clic en el botón
    $('#buscarxtutor').on('click', function () {
        let nombreAlumno = $('#inputAlumno').val().trim();
        if (!nombreAlumno) {
            // Mostramos alerta si el campo está vacío
            $('#alerta').html("<b>Error:</b> Por favor, ingrese el nombre o apellido del alumno.");
            let alerta = $("#alerta");
            alerta.removeClass("d-none").hide().fadeIn(300); // Mostrar con fadeIn

            setTimeout(function () {
                alerta.fadeOut(1000, function () {
                    alerta.addClass("d-none"); // Ocultar después del fadeOut
                });
            }, 3000);
            return;
        }

        let codiSeme = $('#cmbSemestreAcademico').val(); // Obtenemos el semestre seleccionado

        // Validación de semestre
        if (!codiSeme) {
            $('#alerta').html("<b>Error:</b> Por favor, seleccione un semestre.");
            let alerta = $("#alerta");
            alerta.removeClass("d-none").hide().fadeIn(300);

            setTimeout(function () {
                alerta.fadeOut(1000, function () {
                    alerta.addClass("d-none");
                });
            }, 3000);
            return;
        }

        let params = {nombre: nombreAlumno, codiSeme: codiSeme}; // Pasamos nombreCompleto como parámetro

        // Realizamos la petición AJAX
        $.getJSON("BuscarTutor", params, function (data) {
            let tablaResultados = $('#tablaResultados');
            let tbody = tablaResultados.find('tbody');
            tbody.empty(); // Limpiamos la tabla

            if (data.length > 0) {
                $.each(data, function (index, tutor) {
                    let fila = `<tr>
                        <td>${tutor.Alumno}</td>
                        <td>${tutor.Tutor}</td>
                        <td>${tutor.Ciclo}</td>
                    </tr>`;
                    tbody.append(fila); // Añadimos los resultados en la tabla
                });
                tablaResultados.removeClass('d-none'); // Mostramos la tabla
            } else {
                $.fn.mostrarPopup("No se encontraron resultados.");
                tablaResultados.addClass('d-none'); // Ocultamos la tabla si no hay resultados
            }
        }).fail(function () {
            $.fn.mostrarPopup('<b>Error:</b> No se pudo conectar con el servidor. Intente nuevamente.');
        });
    });

    // Ejecutar búsqueda al presionar Enter
    $('#inputNombreCompleto').on('keydown', function (event) {
        if (event.key === 'Enter') {
            $('#buscarxtutor').trigger('click'); // Simulamos clic en el botón al presionar Enter
        }
    });
});
function seleccionarSemestreActual() {
    $.getJSON("SemestreAcademicoCRUD", {opcion: 1}, function (data) {
        if (data.resultado === "ok") {
            let semestreActual = data.semestre.split(":")[1].trim();
            $("#cmbSemestreAcademico option").each(function () {
                if ($(this).text().trim() === semestreActual) {
                    $(this).prop("selected", true);
                    return false;
                }
            });
        } else {
            console.error("Error al obtener el semestre actual.");
        }
    });
}