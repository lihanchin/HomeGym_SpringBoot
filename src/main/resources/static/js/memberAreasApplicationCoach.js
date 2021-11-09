(function () {

  window.addEventListener('load', function () {
    var needs_validation_applicationCoach = document.getElementById('needs_validation_applicationCoach');
    needs_validation_applicationCoach.addEventListener('submit', function (event) {
      if (needs_validation_applicationCoach.checkValidity() === false) {
        event.preventDefault();
        event.stopPropagation();
      }
      needs_validation_applicationCoach.classList.add('was-validated');
    }, false);
  }, false);
})();