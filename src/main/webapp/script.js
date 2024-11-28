const apiBaseUrl = "http://localhost:8080/Front_Categorias/api/categorias";

document.getElementById("btnListar").addEventListener("click", listarCategorias);
document.getElementById("formAgregar").addEventListener("submit", agregarCategoria);
document.getElementById("formActualizar").addEventListener("submit", actualizarCategoria);
document.getElementById("formEliminar").addEventListener("submit", eliminarCategoria);



function limpiarFormulario(formId) {
    const form = document.getElementById(formId);
    if (form) {
        form.reset();
    }
}

// Función para listar categorías
function listarCategorias() {
    fetch(`${apiBaseUrl}/lista`)
            .then(response => response.json())
            .then(data => {
                const tbody = document.querySelector("#tablaCategorias tbody");
                tbody.innerHTML = ""; // Limpiar la tabla
                data.forEach(categoria => {
                    const row = document.createElement("tr");
                    row.innerHTML = `
                    <td>${categoria.idCategoria}</td>
                    <td>${categoria.descripcion}</td>
                    <td>${categoria.tipo}</td>
                    <td>${categoria.activo === 1 ? "Activo" : "Inactivo"}</td>
                    <td>
                        <button onclick="llenarFormularioActualizar(${categoria.idCategoria}, '${categoria.descripcion}', '${categoria.tipo}', ${categoria.activo})">Editar</button>
                        <button onclick="eliminarCategoria(${categoria.idCategoria})">Eliminar</button>
                    </td>
                `;
                    tbody.appendChild(row);
                });
            })
            .catch(error => alert("Error al listar categorías: " + error));
}

// Función para agregar categoría
function agregarCategoria(event) {
    event.preventDefault();
    const descripcion = document.getElementById("descripcionAgregar").value;
    const tipo = document.getElementById("tipoAgregar").value;
    fetch(`${apiBaseUrl}/agregar`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({descripcion, tipo})
    })
            .then(response => response.text())
            .then(message => {
                alert(message);
                listarCategorias();
                limpiarFormulario("formAgregar");
            })
            .catch(error => alert("Error al agregar categoría: " + error));
}

// Función para actualizar categoría
function actualizarCategoria(event) {
    event.preventDefault();
    const idCategoria = document.getElementById("idActualizar").value;
    const descripcion = document.getElementById("descripcionActualizar").value;
    const tipo = document.getElementById("tipoActualizar").value;

    fetch(`${apiBaseUrl}/actualizar`, {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({idCategoria, descripcion, tipo})
    })
            .then(response => response.text())
            .then(message => {
                alert(message);
                listarCategorias();
                limpiarFormulario("formActualizar");

            })
            .catch(error => alert("Error al actualizar categoría: " + error));
}

// Función para eliminar categoría directamente desde la tabla
function eliminarCategoria(idCategoria) {
    if (confirm("¿Estás seguro de eliminar esta categoría?")) {
        fetch(`${apiBaseUrl}/eliminar`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({idCategoria})
        })
                .then(response => response.text())
                .then(message => {
                    alert(message);
                    listarCategorias();
                    limpiarFormulario("formEliminar");
                })
                .catch(error => alert("Error al eliminar categoría: " + error));
    }
}


// Función para llenar el formulario de actualización
function llenarFormularioActualizar(idCategoria, descripcion, tipo, activo) {
    document.getElementById("idActualizar").value = idCategoria;
    document.getElementById("descripcionActualizar").value = descripcion;
    document.getElementById("tipoActualizar").value = tipo;
}
