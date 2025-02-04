function exportToPDF(data, params) {
    const {jsPDF} = window.jspdf;
    const doc = new jsPDF();

    addHeader(doc, params);

    if (data.length === 0) {
        doc.text("No hay datos disponibles para mostrar.", 20, 70);
    } else {
        addTable(doc, data);
    }

    saveFile(doc);
}

function addHeader(doc, params) {
    const pageWidth = doc.internal.pageSize.getWidth(); // Ancho de la página

    // Lado izquierdo: Información de la institución
    doc.setFontSize(11);
    doc.setFont("helvetica", "bold");
    doc.text("UNJFSC", 20, 20); // Nombre de la institución
    doc.setFontSize(10);
    doc.setFont("helvetica", "normal");
    doc.text("Gestión de Alumnos", 20, 25);

    // Centro: Título del reporte
    const title = "REPORTE DE ALUMNOS SIN TUTORÍA";
    const titleWidth = doc.getTextWidth(title);
    const centerX = (pageWidth - titleWidth) / 2;
    doc.setFontSize(11);
    doc.setFont("helvetica", "bold");
    doc.text(title, centerX, 20);

    // Lado derecho: Fecha y hora
    const currentDate = new Date();
    const formattedDate = `${currentDate.getDate()}/${(currentDate.getMonth() + 1)
            .toString()
            .padStart(2, "0")}/${currentDate.getFullYear()}`;
    const formattedTime = `${currentDate.getHours()}:${currentDate
            .getMinutes()
            .toString()
            .padStart(2, "0")}`;

    doc.setFontSize(10);
    doc.setFont("helvetica", "normal");
    doc.text(`FECHA: ${formattedDate}`, pageWidth - 60, 20);
    doc.text(`HORA: ${formattedTime}`, pageWidth - 60, 25);

    // Segunda línea: Información adicional
    doc.setFontSize(10);
    doc.text(`Escuela: ${params.escuela}`, 20, 35);
    doc.text(`Código Escuela: ${params.codiEscuela}`, 20, 40);
    doc.text(`Año: ${params.codiAño}`, 20, 45);
    doc.text(`Semestre: ${params.codiSemestre}`, 20, 50);
}

$(document).ready(function () {
    // Cargar las escuelas (el código de la escuela)
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

    // Cargar los semestres académicos
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

    // Acción cuando se presiona el botón de imprimir
    $('#btnImprimir').click(function () {
        // Obtener los valores seleccionados
        let codiEscuela = $('#cmbEscuelas').val();
        let Semestre = $('#cmbSemestreAcademico option:selected').text();

        // Validar que los datos estén completos
        if (!codiEscuela || !Semestre) {
            alert("Por favor, selecciona una escuela y un semestre.");
            return;
        }

        // Dividir el semestre en año y parte académica
        let parts = Semestre.split("-");
        if (parts.length < 2) {
            alert("Formato de semestre inválido. Asegúrate de seleccionar un semestre correcto.");
            return;
        }

        let codiAño = parts[0].trim();
        let codiSemestre = parts[1].trim();
        let escuela = $("#cmbEscuelas option:selected").text();

        // Mostrar mensaje de carga mientras se procesa la solicitud
        const loadingMessage = $("<div>")
                .attr("id", "loadingMessage")
                .css({
                    position: "fixed",
                    top: "50%",
                    left: "50%",
                    transform: "translate(-50%, -50%)",
                    background: "#fff",
                    padding: "10px 20px",
                    borderRadius: "5px",
                    boxShadow: "0 0 10px rgba(0, 0, 0, 0.2)",
                    zIndex: 1000
                })
                .text("Generando reporte, por favor espera...");
        $('body').append(loadingMessage);

        // Realizar solicitud AJAX al servidor
        $.ajax({
            url: "AlumnosSinTutor",
            method: "GET",
            dataType: "json",
            data: {
                codiEscuela: codiEscuela,
                codiAño: codiAño,
                codiSemestre: codiSemestre
            },
            beforeSend: function () {
                console.log("Procesando solicitud para generar el reporte...");
            },
            success: function (data) {
                if (!data.lista || data.lista.length === 0) {
                    alert("No se encontraron datos para el reporte.");
                } else {
                    exportToPDF(data.lista, {
                        escuela: escuela,
                        codiEscuela: codiEscuela,
                        codiAño: codiAño,
                        codiSemestre: codiSemestre
                    });
                }
            },
            error: function (xhr, status, error) {
                console.error("Error al comunicarse con el servidor:", status, error);
                alert("Error al comunicarse con el servidor: " + error);
            },
            complete: function () {
                $("#loadingMessage").remove();
            }
        });
    });
});

// Función para agregar la tabla
function addTable(doc, data) {
    const headers = ["#", "Nombre", "Código Universitario", "Ciclo"];

    // Crear el cuerpo de la tabla con el contador
    const body = data.map((row, index) => [
            (index + 1).toString(), // Contador
            row.nombres, // Nombres
            row.codigoUniversitario, // Código Universitario
            row.ciclo               // Ciclo
        ]);

    doc.autoTable({
        head: [headers],
        body: body,
        startY: 55,
        headStyles: {
            fillColor: [0, 68, 136], // Azul oscuro (RGB)
            textColor: 255, // Texto blanco
            fontSize: 12,
            halign: "center"         // Centrar texto
        },
        bodyStyles: {
            fontSize: 10
        },
        columnStyles: {
            0: {halign: 'center'}, // Centrado para el contador
            1: {halign: 'left'}, // Alineado a la izquierda para Nombre
            2: {halign: 'center'}, // Centrado para Código Universitario
            3: {halign: 'center'}  // Centrado para Ciclo
        },
        margin: {
            top: 90,
            right: 20,
            bottom: 30,
            left: 20
        },
        drawCell: function (data) {
            // Dibujar líneas solo para las celdas del encabezado
            if (data.section === 'head') {
                doc.setDrawColor(0, 0, 0); // Color negro para las líneas divisorias
                doc.setLineWidth(0.5);     // Grosor de las líneas
                doc.rect(data.cell.x, data.cell.y, data.cell.width, data.cell.height); // Rectángulo de la celda
            }
        }
    });
}

// Función para guardar el archivo PDF
function saveFile(doc) {
    const fileName = `REPORTE_ALUMNOS_SIN_TUTOR.pdf`;
    doc.save(fileName);
}
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
