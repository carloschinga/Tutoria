$(document).ready(function () {
    let asignar = false;
    let editar = false;
    let listadocentes;
    
    //cargar datos inicales
    $.getJSON("RegistrarSession", {rol: 1}, function (data) {
        $("#resultado").text(data.resultado);

        $.getJSON("SemestreAcademicoCRUD",{opcion: 1}, function (data) {
            if (data.resultado === "ok") {
                $('#semestre').text(data.semestre);
                console.log(data.semestre);
            } else {
                if (data.mensaje === 'nopermiso') {
                    alert("Error: No tienes permiso para acceder aqui");
                } else {
                    alert("Error general: No se pudo cargar los semestres academicos.");
                }
            }

        });
        $.getJSON("EscuelasCRUD", {opcion: 1}, function (data) {
            if (data.resultado === "ok") {
                let escuelas = $('#escuelas');
                escuelas.empty().append('<option value="" selected disabled>Selecciona una escuela</option>');

                $.each(data.escuelas, function (key, value) {
                    let codigoEscuela = $('<div>').text(value.codigoEscuela).html();
                    let escuela = $('<div>').text(value.escuela).html();
                    escuelas.append('<option value="' + codigoEscuela + '">' + escuela + '</option>');
                });

                let ciclos = $('#ciclos');
                ciclos.empty().append('<option value="" selected disabled>Selecciona un ciclo</option>');

                $.each(data.ciclos, function (key, value) {
                    let ciclo = $('<div>').text(value).html();
                    ciclos.append('<option value="' + ciclo + '">' + ciclo + '</option>');
                });
            } else {
                if (data.mensaje === 'nopermiso') {
                    alert("Error: No tienes permiso para acceder aqui");
                } else {
                    alert("Error general: No se pudo cargar las escuelas.");
                }
            }
        });
        $.getJSON("DocentesCRUD", {opcion: 1}, function (data) {
            if (data.resultado === "ok") {
                let Docentes = $('#Docentes');
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
    });

    //funciones
    $.fn.listar = function () {
        let ciclos = $('#ciclos').val();
        let escuela = $('#escuelas').val();
        if (escuela !== null && ciclos !== null) {
            var tabla = $('#tabla-estudiantes').DataTable();
            if (tabla) {
                tabla.destroy();
            }
            tabla = $('#tabla-estudiantes').DataTable({
                searching: false,
                paging: false,
                info: false,
                "ajax": {
                    "url": "AlumnosCRUD",
                    "type": "POST",
                    "data": function (d) {
                        d.opcion = 1,
                                d.escuela = escuela,
                                d.ciclo = ciclos;
                    },
                    "dataSrc": function (json) {
                        // Verificar si la respuesta contiene "OK"
                        if (json.resultado === "ok") {
                            return json.data;
                        } else {
                            alert('Error: ' + json.mensaje);
                            return []; // Retorna un array vacío si no es "OK"
                        }
                    }
                },
                "columns": [
                    {"data": "CodigoUniversitario", "width": '20%'},
                    {
                        "data": "Nombres",
                        render: function (data, type, row, meta) {
                            // Escapar datos antes de usarlos
                            let nombres = $('<div>').text(data).html();
                            let apellidos = $('<div>').text(row.Apellidos).html();
                            return apellidos + " " + nombres;
                        }, "width": '40%'
                    },
                    {
                        "data": "codigodocente",
                        render: function (data, type, row, meta) {
                            let codigoUniversitario = $('<div>').text(row.CodigoUniversitario).html();
                            let codigoSede = $('<div>').text(row.CodigoSede).html();
                            if (data === undefined) {
                                return "<input type='checkbox' style='display:none' class='asignar-checkbox' data-coduniv='" + codigoUniversitario + "' data-codsede='" + codigoSede + "'>";
                            } else {
                                let nombrecompleto = $('<div>').addClass('nombre-docente')
                                        .text(row.nombredocente).prop('outerHTML');
                                let selectDocentes = $('<select></select>')
                                        .addClass('form-control select-docente')
                                        .css('display', 'none')
                                        .attr('data-coduniv', codigoUniversitario)
                                        .attr('data-codsede', codigoSede)
                                        .attr('data-valoractual', data);

                                // Iterar sobre la lista de docentes y agregar las opciones
                                $.each(listadocentes, function (key, value) {
                                    if (data === value.CodigoDocente) {
                                        selectDocentes.append('<option value="' + value.CodigoDocente + '" selected>' + value.Nombres + '</option>');
                                    } else {
                                        selectDocentes.append('<option value="' + value.CodigoDocente + '">' + value.Nombres + '</option>');
                                    }
                                });

                                // Retornar el select como un string HTML
                                return nombrecompleto + selectDocentes.prop('outerHTML');
                            }
                        }, "width": '40%'
                    }
                ]
            });
        }
    };
   
    //eventos
    $('#ciclos').change(function () {
        $.fn.listar();
        asignar = false;
        $("#asignar").text("Asignar");
        $(".asignar-checkbox").css("display", 'none');
        $("#Grabar").css("display", 'none');
        $("#div-todos").css("display", 'none');
        editar = false;
        $("#modificar").text("Modificar");
        $(".select-docente").css("display", 'none');
        $("#modificar").css("display", 'inline-block');
        $("#asignar").css("display", 'inline-block');
        $(".nombre-docente").css("display", 'display-block');
        $("#tutor-select").css("display", 'none');
        $("#Grabarmodificar").css("display", 'none');
    });

    $('#escuelas').change(function () {
        $.fn.listar();
        asignar = false;
        $("#asignar").text("Asignar");
        $(".asignar-checkbox").css("display", 'none');
        $("#Grabar").css("display", 'none');
        $("#div-todos").css("display", 'none');
        editar = false;
        $("#modificar").text("Modificar");
        $(".select-docente").css("display", 'none');
        $("#modificar").css("display", 'inline-block');
        $("#asignar").css("display", 'inline-block');
        $(".nombre-docente").css("display", 'display-block');
        $("#tutor-select").css("display", 'none');
        $("#Grabarmodificar").css("display", 'none');
    });

    $("#asignar").click(function () {
        if (asignar) {
            asignar = false;
            $("#asignar").text("Asignar");
            $(".asignar-checkbox").css("display", 'none');
            $("#Grabar").css("display", 'none');
            $("#div-todos").css("display", 'none');
            $("#tutor-select").css("display", 'none');

            $("#modificar").css("display", 'inline-block');
            $("#Grabarmodificar").css("display", 'none');
        } else {
            asignar = true;
            $("#asignar").text("Cancelar");
            $(".asignar-checkbox").css("display", 'inline-block');
            $("#Grabar").css("display", 'inline-block');
            $("#div-todos").css("display", 'inline-block');
            $('#select-all-checkbox').prop('checked', false);
            $("#tutor-select").css("display", 'block');

            editar = false;
            $(".select-docente").css("display", 'none');
            $("#modificar").css("display", 'none');
            $(".nombre-docente").css("display", 'display-block');
            $("#Grabarmodificar").css("display", 'none');
        }
    });

    $("#modificar").click(function () {
        if (editar) {
            editar = false;
            $("#modificar").text("Modificar");
            $(".select-docente").css("display", 'none');
            $(".nombre-docente").css("display", 'block');
            $("#Grabarmodificar").css("display", 'none');

            $("#asignar").css("display", 'inline-block');
            $("#Grabar").css("display", 'none');
            $("#div-todos").css("display", 'none');
            $("#tutor-select").css("display", 'none');
        } else {
            editar = true;
            $("#modificar").text("Cancelar");
            $(".select-docente").css("display", 'inline-block');
            $("#Grabarmodificar").css("display", 'inline-block');

            asignar = false;
            $("#asignar").text("Asignar");
            $("#asignar").css("display", 'none');

            $("#tutor-select").css("display", 'none');
            $(".asignar-checkbox").css("display", 'none');
            $(".nombre-docente").css("display", 'none');
            $("#Grabar").css("display", 'none');
            $("#div-todos").css("display", 'none');
        }
    });

    $("#Grabar").click(function () {
        let datos = [];
        const Docente = $("#Docentes").val();

        // Seleccionar todos los checkboxes con la clase .checkbox-plantilla que estén marcados
        $('.asignar-checkbox:checked').each(function () {
            // Obtener los valores de data-exacod y data-item
            const coduniv = $(this).data('coduniv');
            const codsede = $(this).data('codsede');

            // Agregar los datos al array
            datos.push({
                coduniv: coduniv,
                codsede: codsede
            });
        });

        if (datos.length > 0) {
            if (Docente !== null) {
                console.log(datos);
                $.ajax({
                    url: "TutoriaCRUD?opcion=1&docente=" + Docente,
                    type: "POST",
                    contentType: "application/json",
                    data: JSON.stringify(datos),
                    dataType: "json",
                    success: function (data) {
                        if (data.resultado === "ok") {
                            var tabla = $('#tabla-estudiantes').DataTable();
                            if (tabla) {
                                tabla.ajax.reload();
                                asignar = false;
                                $("#asignar").text("Asignar");
                                $(".asignar-checkbox").css("display", 'none');
                                $("#Grabar").css("display", 'none');
                                $("#div-todos").css("display", 'none');
                                $("#modificar").css("display", 'inline-block');
                                $("#tutor-select").css("display", 'none');
                                $("#Grabarmodificar").css("display", 'none');
                            }
                        } else {
                            alert("Error al grabar.");
                        }
                    },
                    error: function (xhr, status, error) {
                        alert("Error con la conexion con el servidor");
                    }
                });
            } else {
                alert("Seleccione al tutor a asignar.");
            }
        } else {
            alert("Seleccione almenos un alumno.");
        }
    });

    // Evento para el checkbox "Seleccionar Todos"
    $('#select-all-checkbox').on('click', function () {
        var isChecked = $(this).prop('checked');

        // Selecciona o deselecciona todos los checkboxes de la tabla
        $('.asignar-checkbox').each(function () {
            $(this).prop('checked', isChecked);
        });
    });

    // Si algún checkbox individual es deseleccionado, desmarca el "Seleccionar Todos"
    $('#tabla-estudiantes').on('click', '.asignar-checkbox', function () {
        if (!$(this).prop('checked')) {
            $('#select-all-checkbox').prop('checked', false);
        } else {
            // Si todos los checkboxes están seleccionados, marca el "Seleccionar Todos"
            if ($('.asignar-checkbox:checked').length === $('.asignar-checkbox').length) {
                $('#select-all-checkbox').prop('checked', true);
            }
        }
    });
    $("#Grabarmodificar").click(function () {
        let datos = [];

        // Seleccionar todos los select con la clase .select-docente que estén visibles
        $('.select-docente').each(function () {
            // Obtener los valores de data-coduniv y data-codsede
            const coduniv = $(this).data('coduniv');
            const codsede = $(this).data('codsede');
            const valoractual = parseInt($(this).data('valoractual'));
            const docente = parseInt($(this).val()); // Valor seleccionado en el select}
            if (docente !== valoractual) {
                datos.push({
                    codsede: codsede,
                    coduniv: coduniv,
                    anterior: valoractual,
                    nuevo: docente
                });
            }
        });

        if (datos.length > 0) {
            console.log(datos);
            $.ajax({
                url: "TutoriaCRUD?opcion=2",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(datos),
                dataType: "json",
                success: function (data) {
                    if (data.resultado === "ok") {
                        var tabla = $('#tabla-estudiantes').DataTable();
                        if (tabla) {
                            tabla.ajax.reload();
                            editar = false;
                            $("#modificar").text("Modificar");
                            $(".select-docente").css("display", 'none');
                            $(".nombre-docente").css("display", 'block');
                            $("#Grabarmodificar").css("display", 'none');

                            $("#asignar").css("display", 'inline-block');
                            $("#Grabar").css("display", 'none');
                            $("#div-todos").css("display", 'none');
                            $("#tutor-select").css("display", 'none');
                        }
                    } else {
                        alert("Error al grabar.");
                    }
                },
                error: function (xhr, status, error) {
                    alert("Error con la conexion con el servidor");
                }
            });
        } else {
            alert("Cambie almenos un tutor para grabar cambios.");
        }
    });
    
    
});