// Variable global para controlar el estado de carga de jsPDF
let jsPDFLoaded = false;

// Función para verificar si jsPDF está listo
function verificarJsPDF() {
    return new Promise((resolve, reject) => {
        let intentos = 0;
        const maxIntentos = 10;
        
        function verificar() {
            if (window.jspdf) {
                jsPDFLoaded = true;
                resolve(true);
            } else if (intentos < maxIntentos) {
                intentos++;
                setTimeout(verificar, 500);
            } else {
                reject(new Error('No se pudo cargar jsPDF'));
            }
        }
        
        verificar();
    });
}

// Función para agregar el encabezado
function agregarEncabezado(doc, parametros) {
    const anchosPagina = doc.internal.pageSize.getWidth();

    // Lado izquierdo: Información de la institución
    doc.setFontSize(11);
    doc.setFont("helvetica", "bold");
    doc.text("UNJFSC", 20, 20);
    doc.setFontSize(10);
    doc.setFont("helvetica", "normal");
    doc.text("Gestión de Alumnos", 20, 25);

    // Centro: Título del reporte
    const titulo = "REPORTE DE ALUMNOS SIN TUTORÍA";
    const anchoTitulo = doc.getTextWidth(titulo);
    const centroX = (anchosPagina - anchoTitulo) / 2;
    doc.setFontSize(11);
    doc.setFont("helvetica", "bold");
    doc.text(titulo, centroX, 20);

    // Lado derecho: Fecha y hora
    const fechaActual = new Date();
    const fechaFormateada = `${fechaActual.getDate()}/${(fechaActual.getMonth() + 1)
        .toString()
        .padStart(2, "0")}/${fechaActual.getFullYear()}`;
    const horaFormateada = `${fechaActual.getHours()}:${fechaActual
        .getMinutes()
        .toString()
        .padStart(2, "0")}`;

    doc.setFontSize(10);
    doc.setFont("helvetica", "normal");
    doc.text(`FECHA: ${fechaFormateada}`, anchosPagina - 60, 20);
    doc.text(`HORA: ${horaFormateada}`, anchosPagina - 60, 25);

    // Segunda línea: Información adicional
    doc.setFontSize(10);
    doc.text(`Escuela: ${parametros.escuela}`, 20, 35);
    doc.text(`Código Escuela: ${parametros.codiEscuela}`, 20, 40);
    doc.text(`Año: ${parametros.codiAño}`, 20, 45);
    doc.text(`Semestre: ${parametros.codiSemestre}`, 20, 50);
}

// Función para agregar la tabla
function agregarTabla(doc, datos) {
    const encabezados = ["#", "Nombre", "Código Universitario", "Ciclo"];

    const cuerpo = datos.map((fila, indice) => [
        (indice + 1).toString(),
        fila.nombres,
        fila.codigoUniversitario,
        fila.ciclo
    ]);

    doc.autoTable({
        head: [encabezados],
        body: cuerpo,
        startY: 55,
        headStyles: {
            fillColor: [0, 68, 136],
            textColor: 255,
            fontSize: 12,
            halign: "center"
        },
        bodyStyles: {
            fontSize: 10
        },
        columnStyles: {
            0: { halign: 'center' },
            1: { halign: 'left' },
            2: { halign: 'center' },
            3: { halign: 'center' }
        },
        margin: {
            top: 90,
            right: 20,
            bottom: 30,
            left: 20
        },
        drawCell: function (data) {
            if (data.section === 'head') {
                doc.setDrawColor(0, 0, 0);
                doc.setLineWidth(0.5);
                doc.rect(data.cell.x, data.cell.y, data.cell.width, data.cell.height);
            }
        }
    });
}

// Función para guardar el archivo
function guardarArchivo(doc) {
    const nombreArchivo = `REPORTE_ALUMNOS_SIN_TUTOR.pdf`;
    doc.save(nombreArchivo);
}

// Función principal para exportar a PDF
async function exportarAPDF(datos, parametros) {
    try {
        if (!jsPDFLoaded) {
            await verificarJsPDF();
        }

        const { jsPDF } = window.jspdf;
        const doc = new jsPDF();

        agregarEncabezado(doc, parametros);

        if (!datos || datos.length === 0) {
            doc.text("No hay datos disponibles para mostrar.", 20, 70);
        } else {
            agregarTabla(doc, datos);
        }

        guardarArchivo(doc);
    } catch (error) {
        console.error('Error al generar PDF:', error);
        alert('Ocurrió un error al generar el PDF. Por favor, intente nuevamente.');
    }
}

// Inicialización cuando el documento está listo
$(document).ready(async function () {
    try {
        // Intentar cargar jsPDF al inicio
        await verificarJsPDF();
        console.log('jsPDF cargado correctamente');
    } catch (error) {
        console.error('Error al cargar jsPDF:', error);
    }

    // Cargar las escuelas
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
                alert("Error: No tienes permiso para acceder aquí");
            } else {
                alert("Error: No se pudieron cargar las escuelas.");
            }
        }
    });

    // Cargar los semestres académicos
    $.getJSON("SemestreAcademicoCRUD", { opcion: 2 }, function (data) {
        if (data.resultado === "ok") {
            let semestres = $('#cmbSemestreAcademico');
            semestres.empty().append('<option value="" selected disabled>Selecciona un Semestre</option>');
            $.each(data.semestres, function (key, value) {
                let semestre = $('<div>').text(value.Semestre).html();
                semestres.append('<option value="' + value.CodigoSemestre + '">' + semestre + '</option>');
            });
        } else {
            alert("Error al cargar los semestres.");
        }
    });

    // Manejador del botón de imprimir
    $('#btnImprimir').click(async function (e) {
        e.preventDefault();

        const mostrarMensajeCarga = () => {
            $("#mensajeCarga").remove();
            const mensajeCarga = $("<div>")
                .attr("id", "mensajeCarga")
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
            $('body').append(mensajeCarga);
        };

        // Validaciones iniciales
        let codiEscuela = $('#cmbEscuelas').val();
        let Semestre = $('#cmbSemestreAcademico option:selected').text();

        if (!codiEscuela || !Semestre) {
            alert("Por favor, selecciona una escuela y un semestre.");
            return;
        }

        let parts = Semestre.split("-");
        if (parts.length < 2) {
            alert("Formato de semestre inválido.");
            return;
        }

        // Verificar jsPDF
        try {
            if (!jsPDFLoaded) {
                await verificarJsPDF();
            }
        } catch (error) {
            alert('Error: La librería PDF no está cargada correctamente. Por favor, recarga la página.');
            return;
        }

        let codiAño = parts[0].trim();
        let codiSemestre = parts[1].trim();
        let escuela = $("#cmbEscuelas option:selected").text();

        mostrarMensajeCarga();

        // Realizar la solicitud AJAX
        $.ajax({
            url: "AlumnosSinTutor",
            method: "GET",
            dataType: "json",
            data: {
                codiEscuela: codiEscuela,
                codiAño: codiAño,
                codiSemestre: codiSemestre
            },
            success: function (respuesta) {
                if (!respuesta.lista || respuesta.lista.length === 0) {
                    alert("No se encontraron datos para el reporte.");
                    return;
                }

                exportarAPDF(respuesta.lista, {
                    escuela: escuela,
                    codiEscuela: codiEscuela,
                    codiAño: codiAño,
                    codiSemestre: codiSemestre
                });
            },
            error: function (xhr, status, error) {
                console.error("Error en la solicitud:", error);
                alert("Error al obtener los datos del servidor: " + error);
            },
            complete: function () {
                $("#mensajeCarga").remove();
            }
        });
    });
});