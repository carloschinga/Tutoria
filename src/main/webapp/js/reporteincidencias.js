$(document).ready(function () {


    $('#btnBuscarAlumno').on('click', function () {
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

        let params = {nombre: nombreAlumno}; // Pasamos nombreCompleto como parámetro

        // Realizamos la petición AJAX
        $.getJSON("buscaralumno", params, function (data) {

            console.log(data);
            let tablaResultados = $('#tablaResultados');
            let tbody = tablaResultados.find('tbody');
            tbody.empty(); // Limpiamos la tabla

            if (data.length > 0) {
                $.each(data, function (index, al) {
                    let fila = `<tr>                     
                        
                         <td>${al.Alumno}</td>
                     <td><button class="btn btn-primary btn-accion" data-id=${al.CodigoUniversitario}>Imprimir</button></td>
                    </tr>`;
                    tbody.append(fila); // Añadimos los resultados en la tabla
                });
                tablaResultados.removeClass('d-none'); // Mostramos la tabla
            } else {
                alert("No hay datos");
                $.fn.mostrarPopup("No se encontraron resultados.");
                tablaResultados.addClass('d-none'); // Ocultamos la tabla si no hay resultados
            }
        }).fail(function () {
            $.fn.mostrarPopup('<b>Error:</b> No se pudo conectar con el servidor. Intente nuevamente.');
        });
    });

    $(document).on('click', 'button.btn-accion', function () {
        var id = $(this).data('id'); // Obtener el ID del botón

    


        $('#alerta').removeClass('d-none').text('Generando reporte...');

        $.ajax({
            url: "ReporteIncidencias",
            method: "GET",
            dataType: "json",
            data: {termino: id}, // ✅ Se envía todo el texto como 'termino'
            success: function (data) {
               // console.log(data);
                if (data.resultado === "OK" && data.lista.length > 0) {
                    exportToPDF(data.lista, {alumno: data[0]});
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

    $('#btnImprimir').click(function () {

    });
});

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
        headStyles: {fillColor: [0, 68, 136], textColor: 255, fontSize: 12, halign: "center"},
        bodyStyles: {fontSize: 10},
        margin: {top: 55, right: 20, bottom: 30, left: 20}
    });
}

function saveFile(doc) {
    const fileName = `REPORTE_INCIDENCIAS_${new Date().toISOString().replace(/[:.]/g, "-")}.pdf`;
    doc.save(fileName);
}
