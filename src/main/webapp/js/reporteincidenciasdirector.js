$(document).ready(function () {
    // Cargar los semestres
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
    $.getJSON("DocentesCRUD", {opcion: 1}, function (data) {
        if (data.resultado === "ok") {
            let Docentes = $('#cmbDocentes');
            Docentes.html('<option value=""selected disabled>Selecciona un docente</option>');
            listadocentes = data.data;
            $.each(listadocentes, function (key, value) {
                Docentes.append('<option value="' + value.CodigoDocente + '">' + value.Nombres + '</option>');
            });
        } else {
            if (data.mensaje === 'nopermiso') {
                alert("Error: No tienes permiso para acceder aqui");
            } else {
                alert("Error general: No se pudo cargar los docentes.");
            }
        }
    });

    $('#btnImprimir').click(function () {
        // Obtener el valor del semestre seleccionado
        let semestreSeleccionado = $('#cmbSemestreAcademico').val();
        let Docentesseleccionado = $('#cmbDocentes').val();

        if (!semestreSeleccionado || !Docentesseleccionado) {
            alert("Por favor, selecciona un semestre.");
            return;
        }

        let semestreTexto = $('#cmbSemestreAcademico option:selected').text();
        let parts = semestreTexto.split("-");

        if (parts.length !== 2) {
            alert("Formato de semestre inválido.");
            return;
        }

        let codiAño = parts[0].trim();
        let codiSemestre = parts[1].trim();

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

        // Realizar solicitud AJAX al servlet ReporteIncidencias
        $.ajax({
            url: "ReporteIncidenciasDirector", // Servlet correcto para generar el reporte
            method: "GET",
            dataType: "json",
            data: {
                anio: codiAño,
                semestre: codiSemestre,
                codidocente:Docentesseleccionado
            },
            success: function (data) {
                 if (data.resultado === "OK" && data.lista.length > 0) {
                    // Pasar los datos y los parámetros al generador de PDF
                    exportToPDF(data.lista, {
                        anio: codiAño,
                        semestre: codiSemestre,
                        codidocente:Docentesseleccionado
                    });
                } else {
                    alert("No se encontraron incidencias para este semestre,con el tutor seleccionado.");
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

function exportToPDF(data, params) {
    const {jsPDF} = window.jspdf;
    const doc = new jsPDF();

    // Añadir la cabecera
    addHeader(doc, params);

    // Verificar si hay datos para mostrar
    if (data.length === 0) {
        doc.text("No hay datos disponibles para mostrar.", 20, 70);
    } else {
        // Añadir la tabla
        addTable(doc, data, params);
    }

    // Guardar el archivo PDF
    saveFile(doc);
}

function addHeader(doc, params) {
    const pageWidth = doc.internal.pageSize.getWidth();
    doc.setFontSize(11);
    doc.setFont("helvetica", "bold");
    doc.text("UNJFSC", 20, 20);
    doc.setFontSize(10);
    doc.setFont("helvetica", "normal");
    doc.text("Gestión de Incidencias", 20, 25);

    const title = "REPORTE DE INCIDENCIAS";
    const titleWidth = doc.getTextWidth(title);
    const centerX = (pageWidth - titleWidth) / 2;
    doc.setFontSize(11);
    doc.setFont("helvetica", "bold");
    doc.text(title, centerX, 20);

    const currentDate = new Date();
    const formattedDate = `${currentDate.getDate()}/${(currentDate.getMonth() + 1).toString().padStart(2, "0")}/${currentDate.getFullYear()}`;
    const formattedTime = `${currentDate.getHours()}:${currentDate.getMinutes().toString().padStart(2, "0")}`;

    doc.setFontSize(10);
    doc.setFont("helvetica", "normal");
    doc.text(`FECHA: ${formattedDate}`, pageWidth - 60, 20);
    doc.text(`HORA: ${formattedTime}`, pageWidth - 60, 25);

    doc.setFontSize(10);
    doc.text(`Año: ${params.anio}`, 20, 35);
    doc.text(`Semestre: ${params.semestre}`, 20, 40);
    if (params.tutorNombre) {
        doc.text(`Tutor: ${params.tutorNombre}`, 20, 45); // Ajusta la posición según necesites
    }

}

function addTable(doc, data, params) {
    const headers = ["#", "FECHA", "INCIDENCIA", "OBSERVACION", "CODIGO INCIDENCIA", "TUTOR"];
    const body = data.map((row, index) => [
            (index + 1).toString(),
            row.fecha, // Fecha
            row.incidencia, // Incidencia
            row.observacion, // Observación
            row.codigoIncidencia, // Código Incidencia
            row.tutor       // Código Incidencia
        ]);

    const firstPageStartY = 55;
    let currentY = firstPageStartY;

    doc.autoTable({
        head: [headers],
        body: body,
        startY: currentY,
        headStyles: {
            fillColor: [0, 68, 136],
            textColor: 255,
            fontSize: 12,
            halign: "center"
        },
        bodyStyles: {
            fontSize: 10
        },
        margin: {
            top: 55,
            right: 20,
            bottom: 30,
            left: 20
        },
        pageBreak: 'auto',
        didDrawPage: function (data) {
            addHeader(doc, params);
        }
    });
}

function saveFile(doc) {
    const fileName = `REPORTE_INCIDENCIAS_${new Date().toISOString()}.pdf`;
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
        }
    });
}
