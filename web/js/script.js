const urlBase = 'http://localhost:8080/AxolotlZarape/api/bebida';

document.addEventListener('DOMContentLoaded', () => {
    cargarBebida();
});

function cargarBebida() {
    fetch(`${urlBase}/getAll`)
        .then(response => response.json())
        .then(bebidas => {
            const tabla = document.getElementById('tablaBebidas');
            tabla.innerHTML = '';
            bebidas.forEach(bebida => {
                tabla.innerHTML += `
                    <tr>
                        <td>${bebida.id}</td>
                        <td>${bebida.nombre}</td>
                        <td>${bebida.descripcion}</td>
                        <td><img src="${bebida.foto}" width="50"></td>
                        <td>${bebida.precio}</td>
                        <td>${bebida.categoria}</td>
                        <td>
                            <button class="btn btn-sm btn-warning" onclick="editarBebida(${bebida.id}, '${bebida.nombre}', '${bebida.descripcion}', '${bebida.foto}', ${bebida.precio}, ${bebida.idCategoria})">
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="btn btn-sm btn-danger" onclick="eliminarBebida(${bebida.id})">
                                <i class="fas fa-trash"></i>
                            </button>
                        </td>
                    </tr>`;
            });
        })
        .catch(error => console.error('Error al cargar las bebidas:', error));
}

function editarBebida(id = 0, nombre = '', descripcion = '', foto = '', precio = '', idCategoria = '') {
    Swal.fire({
        title: id ? 'Editar Bebida' : 'Agregar Bebida',
        html: `
            <input type="hidden" id="idBebida" value="${id || 0}">
            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" id="nombre" class="form-control" value="${nombre}">
            </div>
            <div class="mb-3">
                <label for="descripcion" class="form-label">Descripción</label>
                <input type="text" id="descripcion" class="form-control" value="${descripcion}">
            </div>
            <div class="mb-3">
                <label for="foto" class="form-label">URL de la Foto</label>
                <input type="text" id="foto" class="form-control" value="${foto}">
            </div>
            <div class="mb-3">
                <label for="precio" class="form-label">Precio</label>
                <input type="number" id="precio" class="form-control" step="0.01" value="${precio}">
            </div>
            <div class="mb-3">
                <label for="idCategoria" class="form-label">ID Categoría</label>
                <input type="number" id="idCategoria" class="form-control" value="${idCategoria}">
            </div>
        `,
        showCancelButton: true,
        confirmButtonText: 'Guardar',
        preConfirm: () => {
            const bebida = obtenerDatosFormulario();
            if (!bebida.nombre || !bebida.descripcion || !bebida.precio || !bebida.idCategoria) {
                Swal.showValidationMessage('Por favor completa todos los campos');
                return;
            }

            if (id === 0) {
                insertarBebida(bebida);
            } else {
                actualizarBebida(id, bebida);
            }
        }
    });
}

function insertarBebida(bebida) {
    fetch(`${urlBase}/insert`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: new URLSearchParams(bebida)
    })
        .then(response => response.json())
        .then(() => {
            Swal.fire('Agregado', 'Bebida agregada con éxito', 'success');
            cargarBebida();
        })
        .catch(error => console.error('Error al insertar bebida:', error));
}

function actualizarBebida(id, bebida) {
    bebida.idBebida = id;

    fetch(`${urlBase}/update`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: new URLSearchParams(bebida)
    })
        .then(response => response.json())
        .then(() => {
            Swal.fire('Actualizado', 'Bebida actualizada con éxito', 'success');
            cargarBebida();
        })
        .catch(error => console.error('Error al actualizar bebida:', error));
}

function eliminarBebida(id) {
    Swal.fire({
        title: '¿Estás seguro?',
        text: 'Esta acción no se puede deshacer',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            fetch(`${urlBase}/delete`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: new URLSearchParams({ idBebida: id })
            })
                .then(response => response.json())
                .then(() => {
                    Swal.fire('Eliminado', 'Bebida eliminada con éxito', 'success');
                    cargarBebida();
                })
                .catch(error => console.error('Error al eliminar bebida:', error));
        }
    });
}

function obtenerDatosFormulario() {
    return {
        nombre: document.getElementById('nombre').value,
        descripcion: document.getElementById('descripcion').value,
        foto: document.getElementById('foto').value,
        precio: document.getElementById('precio').value,
        idCategoria: document.getElementById('idCategoria').value
    };
}
