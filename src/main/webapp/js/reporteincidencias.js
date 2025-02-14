$(document).ready(function () {
    $('#btnBuscarAlumno').on('click', function () {
        let nombreAlumno = $('#inputAlumno').val().trim();
        if (!nombreAlumno) {
            let alerta = $("#alerta");
            alerta.html("<b>Error:</b> Por favor, ingrese el nombre o apellido del alumno.");
            alerta.removeClass("d-none").hide().fadeIn(300);
            setTimeout(function () {
                alerta.fadeOut(1000, function () {
                    alerta.addClass("d-none");
                });
            }, 3000);
            return;
        }

        let params = { nombre: nombreAlumno };

        $.getJSON("buscaralumno", params, function (data) {
            let tablaResultados = $('#tablaResultados');
            let tbody = tablaResultados.find('tbody');
            tbody.empty();

            if (data.length > 0) {
                $.each(data, function (index, al) {
                    let fila = `<tr>
                        <td>${al.Alumno}</td>
                        <td><button class="btn btn-primary btn-accion" data-id="${al.CodigoUniversitario}">Imprimir</button></td>
                    </tr>`;
                    tbody.append(fila);
                });
                tablaResultados.removeClass('d-none');
            } else {
                alert("No se encontraron resultados.");
                tablaResultados.addClass('d-none');
            }
        }).fail(function () {
            alert("Error: No se pudo conectar con el servidor. Intente nuevamente.");
        });
    });

    $(document).on('click', 'button.btn-accion', function () {
        var id = $(this).data('id');
        $('#alerta').removeClass('d-none').text('Generando reporte...');

        $.ajax({
            url: "ReporteIncidencias",
            method: "GET",
            dataType: "json",
            data: { termino: id },
            success: function (data) {
                if (data.resultado === "OK" && data.lista.length > 0) {
                    exportToPDF(data.lista, { alumno: data.lista[0].alumno });
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
        centerText(doc, "No hay datos disponibles para mostrar.", 70);
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

function centerText(doc, text, y) {
    const pageWidth = doc.internal.pageSize.getWidth();
    const textWidth = doc.getTextWidth(text);
    doc.text(text, (pageWidth - textWidth) / 2, y);
}

function addTable(doc, data) {
    const headers = ["#", "Fecha", "Incidencia", "Observación"];
    const body = data.map((row, index) => [
        (index + 1).toString(),
        row.fecha || "-",
        row.incidencia || "-",
        row.observacion || "-"
    ]);

    doc.autoTable({
        head: [headers],
        body: body,
        startY: 50,
        headStyles: { fillColor: [0, 68, 136], textColor: 255, fontSize: 12, halign: "center" },
        bodyStyles: { fontSize: 10, halign: "center" },
        margin: { top: 55, right: 20, bottom: 30, left: 20 }
    });
}

function saveFile(doc) {
    const fileName = `REPORTE_INCIDENCIAS_${new Date().toISOString().replace(/[:.]/g, "-")}.pdf`;
    doc.save(fileName);
}
