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
            console.log(data);
            let semestres = $('#cmbSemestreAcademico');
            semestres.empty().append('<option value="" selected disabled>Selecciona un Semestre</option>');

            $.each(data.semestres, function (key, value) {
                let semestre = $('<div>').text(value.Semestre).html();
                semestres.append('<option value="' + value.CodigoSemestre + '">' + semestre + '</option>');
            });
        } else {
            $.fn.manejarError(data.mensaje, "semestres");
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
            //$.fn.mostrarPopup('');
            $('#alerta').html("<b>Error:</b> Por favor, ingrese el nombre del alumno.");
            let alerta = $("#alerta");

            alerta.removeClass("d-none").hide().fadeIn(300); // Mostrar con fadeIn

            setTimeout(function () {
                alerta.fadeOut(1000, function () {
                    alerta.addClass("d-none"); // Ocultar después del fadeOut
                });
            }, 3000);
            return;
        }
        let codiSeme = $('#cmbSemestreAcademico').val();


        let params = {nombre: nombreAlumno, codiSeme: codiSeme};
        //let url = `BuscarTutor?nombre=${encodeURIComponent(nombreAlumno)}&codiSeme:`;

        $.getJSON("BuscarTutor", params, function (data) {
            // aqui poner la grilla
        }).fail(function () {
            $.fn.mostrarPopup('<b>Error:</b> No se pudo conectar con el servidor. Intente nuevamente.');
        });
    });

    // Ejecutar búsqueda al presionar Enter
    $('#inputAlumno').on('keydown', function (event) {
        if (event.key === 'Enter') {
            $('#buscarxtutor').trigger('click');
        }
    });
});