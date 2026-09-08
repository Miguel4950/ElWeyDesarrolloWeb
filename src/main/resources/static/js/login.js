/**
 * login.js - Lógica interactiva para la pantalla de inicio de sesión de Restaurante El Wey
 */

function seleccionarRol(rol, elementoBoton) {
    // Actualizar estilos activos de los botones de rol
    document.querySelectorAll('.btn-rol-rapido').forEach(btn => {
        btn.classList.remove('btn-rol-activo');
    });
    if (elementoBoton) {
        elementoBoton.classList.add('btn-rol-activo');
    }

    // Sincronizar el elemento <select>
    const selectRol = document.getElementById('rol');
    if (selectRol) {
        selectRol.value = rol;
    }

    // Actualizar el texto del botón principal
    actualizarTextoBoton(rol);
}

function actualizarBotonPorSelect(rol) {
    // Sincronizar los botones de acceso rápido con el <select>
    document.querySelectorAll('.btn-rol-rapido').forEach(btn => {
        if (btn.innerText.trim().toUpperCase() === rol.toUpperCase()) {
            btn.classList.add('btn-rol-activo');
        } else {
            btn.classList.remove('btn-rol-activo');
        }
    });

    // Actualizar el texto del botón principal
    actualizarTextoBoton(rol);
}

function actualizarTextoBoton(rol) {
    const btnSubmit = document.getElementById('btn-submit-login');
    if (btnSubmit) {
        btnSubmit.innerText = 'INGRESAR COMO ' + rol.toUpperCase();
    }
}

