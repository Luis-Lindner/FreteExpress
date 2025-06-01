// Função para inicializar a página
document.addEventListener('DOMContentLoaded', function() {
    // Garante que apenas o primeiro formulário seja visível ao carregar
    document.getElementById('step-1').style.display = 'block';
    document.getElementById('step-2').style.display = 'none';
    document.getElementById('step-3').style.display = 'none';
    document.getElementById('step-4').style.display = 'none';
});

// Função para avançar para o próximo formulário
function nextStep(currentStep) {
    // Valida o formulário atual antes de avançar
    const form = document.getElementById(`form-${currentStep}`);
    if (form.checkValidity()) {
        document.getElementById(`step-${currentStep}`).style.display = 'none';
        document.getElementById(`step-${currentStep + 1}`).style.display = 'block';
    } else {
        form.reportValidity(); // Mostra mensagens de validação se houver campos inválidos
    }
}

// Função para voltar ao formulário anterior
function previousStep(currentStep) {
    document.getElementById(`step-${currentStep}`).style.display = 'none';
    document.getElementById(`step-${currentStep - 1}`).style.display = 'block';
}