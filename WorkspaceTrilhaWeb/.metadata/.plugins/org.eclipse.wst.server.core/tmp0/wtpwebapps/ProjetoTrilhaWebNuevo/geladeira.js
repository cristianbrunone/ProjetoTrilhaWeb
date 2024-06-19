// Obtener referencias a los elementos del slider
const slider = document.querySelector('.slider');
const slides = document.querySelectorAll('.slider img');
const sliderNav = document.querySelector('.slider-nav');
var navLinks = document.querySelectorAll('.slider-nav a');

// Configuración para el slider automático
const intervalo = 5000; // Intervalo en milisegundos (5 segundos)

let indice = 0; // Índice de la imagen actual

// Función para cambiar las imágenes del slider
function cambiarSlide() {
    // Ocultar la imagen actual
    slides[indice].style.display = 'none';

    // Eliminar la clase 'active' del círculo de navegación actual
    navLinks[indice].classList.remove('active');

    // Incrementar el índice o volver al inicio si llegamos al final
    indice = (indice + 1) % slides.length;

    // Mostrar la siguiente imagen
    slides[indice].style.display = 'block';

    // Agregar la clase 'active' al nuevo círculo de navegación
    navLinks[indice].classList.add('active');
}

// Iniciar el slider automático
setInterval(cambiarSlide, intervalo);

// Manejar el clic en los enlaces de navegación
navLinks.forEach(function(link, index) {
    link.addEventListener('click', function() {
        // Ocultar la imagen actual
        slides[indice].style.display = 'none';

        // Eliminar la clase 'active' del círculo de navegación actual
        navLinks[indice].classList.remove('active');

        // Establecer el índice al valor correspondiente al enlace de navegación actual
        indice = index;

        // Mostrar la nueva imagen
        slides[indice].style.display = 'block';

        // Agregar la clase 'active' al enlace de navegación actual
        navLinks[indice].classList.add('active');
    });
});


