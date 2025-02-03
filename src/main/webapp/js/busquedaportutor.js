
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
            manejarError(data.mensaje, "escuelas");
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
        } else {
            manejarError(data.mensaje, "semestres");
        }
    });

    // Elementos del popup
    const inputAlumno = document.getElementById('inputAlumno');
    const botonBuscar = document.getElementById('buscarxtutor');
    const popup = document.getElementById('popup');
    const popupOverlay = document.getElementById('popupOverlay');
    const resultadoTutor = document.getElementById('resultadoTutor');
    const cerrarPopup = document.getElementById('cerrarPopup');

    // Función para mostrar el popup con mensaje
    function mostrarPopup(mensaje) {
        resultadoTutor.innerHTML = mensaje;
        popup.style.display = 'block';
        popupOverlay.style.display = 'block';
    }

    // Función para cerrar el popup
    function cerrarPopupHandler() {
        popup.style.display = 'none';
        popupOverlay.style.display = 'none';
    }

    // Cerrar popup al hacer clic en el botón
    cerrarPopup.addEventListener('click', cerrarPopupHandler);

    // Cerrar popup al hacer clic fuera del cuadro
    popupOverlay.addEventListener('click', cerrarPopupHandler);

    // Función para buscar tutor por nombre de alumno
    function buscarTutor() {
        let nombreAlumno = inputAlumno.value.trim();
        if (!nombreAlumno) {
            mostrarPopup('<b>Error:</b> Por favor, ingrese el nombre del alumno.');
            return;
        }

        let url = `BuscarTutor?nombre=${encodeURIComponent(nombreAlumno)}`;

        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Error del servidor: ${response.status} - ${response.statusText}`);
                }
                return response.json();
            })
            .then(data => {
                if (data.resultado === 'OK') {
                    mostrarPopup(`<b>Alumno:</b> ${data.alumno} <br> <b>Tutor:</b> ${data.tutor} <br> <b>Ciclo:</b> ${data.ciclo}`);
                } else {
                    mostrarPopup(`<b>Error:</b> ${data.message}`);
                }
            })
            .catch(error => {
                mostrarPopup('<b>Error:</b> No se pudo conectar con el servidor. Intente nuevamente.');
                console.error('Error:', error);
            });
    }

    // Ejecutar búsqueda al presionar Enter
    inputAlumno.addEventListener('keydown', function (event) {
        if (event.key === 'Enter') {
            buscarTutor();
        }
    });

    // Ejecutar búsqueda al hacer clic en el botón
    botonBuscar.addEventListener('click', buscarTutor);
});






