const verifyButtons = document.querySelectorAll('.verify-button');

verifyButtons.forEach(button => {
    button.addEventListener('click', () => {
        alert('Successfully Verified');
    });
});
