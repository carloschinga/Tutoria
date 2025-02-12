$(document).ready(function () {
    $('#btnImprimir').click(function () {
        let inputTexto = $('#inputAlumno').val().trim();

        if (!inputTexto) {
            alert("Por favor, ingresa un nombre o apellido.");
            return;
        }

        $('#alerta').removeClass('d-none').text('Generando reporte...');

        $.ajax({
            url: "ReporteIncidencias",
            method: "GET",
            dataType: "json",
            data: { termino: inputTexto },  // ✅ Se envía todo el texto como 'termino'
            success: function (data) {
                console.log("Respuesta del servidor:", data);
                if (data.resultado === "OK" && data.lista.length > 0) {
                    exportToPDF(data.lista, { alumno: inputTexto });
                } else {
                    alert("No se encontraron incidencias para el alumno ingresado.");
                }
            },
            error: function (xhr, status, error) {
                console.error("Error al comunicarse con el servidor:", status, error);
                alert("Error al obtener los datos.");
            },
            complete: function () {
                $('#alerta').addClass('d-none');
            }
        });
    });
});

function exportToPDF(data, params) {
    const { jsPDF } = window.jspdf;
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
    const pageWidth = doc.internal.pageSize.getWidth();
    doc.setFontSize(11);
    doc.setFont("helvetica", "bold");
    doc.text("UNJFSC", 20, 20);
    doc.setFontSize(10);
    doc.setFont("helvetica", "normal");
    doc.text("Gestión de Incidencias", 20, 25);

    const title = "REPORTE DE INCIDENCIAS";
    const titleWidth = doc.getTextWidth(title);
    doc.text(title, (pageWidth - titleWidth) / 2, 20);

    const currentDate = new Date();
    doc.text(`FECHA: ${currentDate.toLocaleDateString()}`, pageWidth - 60, 20);
    doc.text(`HORA: ${currentDate.toLocaleTimeString()}`, pageWidth - 60, 25);

    if (params.alumno) {
        doc.text(`Alumno: ${params.alumno}`, 20, 35);
    }
}

function addTable(doc, data) {
    const headers = ["#", "Fecha", "Alumno", "Incidencia", "Observación"];
    const body = data.map((row, index) => [
        (index + 1).toString(),
        row.fecha || "-",
        row.alumno || "-",
        row.incidencia || "-",
        row.observacion || "-"
    ]);

    doc.autoTable({
        head: [headers],
        body: body,
        startY: 50,
        headStyles: { fillColor: [0, 68, 136], textColor: 255, fontSize: 12, halign: "center" },
        bodyStyles: { fontSize: 10 },
        margin: { top: 55, right: 20, bottom: 30, left: 20 }
    });
}

function saveFile(doc) {
    const fileName = `REPORTE_INCIDENCIAS_${new Date().toISOString().replace(/[:.]/g, "-")}.pdf`;
    doc.save(fileName);
}
