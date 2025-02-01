
$(document).ready(function () {
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
            if (data.mensaje === 'nopermiso') {
                alert("Error: No tienes permiso para acceder aqui");
            } else {
                alert("Error general: No se pudo cargar las escuelas.");
            }
        }
    });
    $.getJSON("SemestreAcademicoCRUD", {opcion: 2}, function (data) {
        if (data.resultado === "ok") {
            let semestres = $('#cmbSemestreAcademico');
            semestres.empty().append('<option value="" selected disabled>Selecciona un Semestre</option>');
            $.each(data.semestres, function (key, value) {
                let semestre = $('<div>').text(value.Semestre).html();
                semestres.append('<option value="' + value.CodigoSemestre + '">' + semestre + '</option>');
            });
        } else {
            if (data.mensaje === 'nopermiso') {
                alert("Error: No tienes permiso para acceder aquí");
            } else {
                alert("Error general: No se pudo cargar los semestres.");
            }
        }
    });
      // Elementos del popup
    const inputAlumno = document.getElementById('inputAlumno');
    const popup = document.getElementById('popup');
    const resultadoTutor = document.getElementById('resultadoTutor');
    const cerrarPopup = document.getElementById('cerrarPopup');

    // Función para mostrar el popup con mensaje formateado
    function mostrarPopup(mensaje) {
        resultadoTutor.innerHTML = mensaje;
        popup.style.display = 'block';
    }

    // Cerrar popup al hacer clic en el botón
    cerrarPopup.addEventListener('click', function () {
        popup.style.display = 'none';
    });

    // Función para buscar tutor por nombre de alumno
    function buscarTutor(nombreAlumno) {
        if (!nombreAlumno) {
            mostrarPopup('<b>Error:</b> Por favor, ingrese el nombre del alumno.');
            return;
        }

        // Definir la URL del servidor (ajustar si el contexto de despliegue es diferente)
        let url = `BuscarTutor?nombre=${encodeURIComponent(nombreAlumno)}`;

        // Realizar la solicitud al servidor
        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Error del servidor: ${response.status} - ${response.statusText}`);
                }
                return response.json();
            })
            .then(data => {
                if (data.resultado === 'OK') {
                    mostrarPopup(`<b>Alumno:</b> ${data.alumno} <br> <b>Tutor:</b> ${data.tutor} <br><b>Ciclo:</b> ${data.ciclo}</b>`);
                } else {
                    mostrarPopup(`<b>Error:</b> ${data.message}`);
                }
            })
            .catch(error => {
                mostrarPopup('<b>Error:</b> No se pudo conectar con el servidor. Intente nuevamente.');
                console.error('Error:', error);
            });
    }

    // Escuchar evento de búsqueda cuando el usuario presiona "Enter"
    inputAlumno.addEventListener('keydown', function (event) {
        if (event.key === 'Enter') {
            buscarTutor(inputAlumno.value.trim());
        }
    });
});





