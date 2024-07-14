// document.addEventListener('DOMContentLoaded', function() {
//     const HamBurger = document.querySelectorAll('.hamburger');
    
//     HamBurger.forEach(item => {
//         item.addEventListener('click', function() {

//             HamBurger.forEach(i => i.classList.remove('active'));
            

//             this.classList.add('active');
//         });
//     });
// });

document.addEventListener('DOMContentLoaded', function() {
    const navoptions = document.querySelectorAll('.nav_options');
    const sections = document.querySelectorAll('.section');

    function setActiveMenuItem() {
        let currentSection = '';

        sections.forEach(section => {
            const sectionTop = section.offsetTop - 70; // Account for fixed menu height
            const sectionHeight = section.clientHeight;

            if (pageYOffset >= sectionTop && pageYOffset < sectionTop + sectionHeight) {
                currentSection = section.getAttribute('id');
            }
        });

        navoptions.forEach(item => {
            item.classList.remove('active');
            if (item.getAttribute('data-section') === currentSection) {
                item.classList.add('active');
            }
        });
    }

    window.addEventListener('scroll', setActiveMenuItem);
    window.addEventListener('load', setActiveMenuItem);

    navoptions.forEach(item => {
        item.addEventListener('click', function() {
            document.getElementById(this.getAttribute('data-section')).scrollIntoView({
                behavior: 'smooth'
            });
        });
    });
});

function toggleMenu() {
    const menu = document.querySelector('.nav-links');
    menu.classList.toggle('active');
}



document.addEventListener("DOMContentLoaded", function() {
    const sections = document.querySelectorAll('.section');
    const observerOptions = {
        root: null,
        rootMargin: '0px',
        threshold: 0.1
    };

    const observerCallback = (entries, observer) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.remove('zoom-out');
            } else {
                entry.target.classList.add('zoom-out');
            }
        });
    };

    const observer = new IntersectionObserver(observerCallback, observerOptions);

    sections.forEach(section => {
        observer.observe(section);
    });
});



