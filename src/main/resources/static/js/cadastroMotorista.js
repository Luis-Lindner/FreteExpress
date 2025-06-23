document.addEventListener('DOMContentLoaded', function() {
    // Garante que apenas o primeiro formulário seja visível ao carregar
    showStep(1);
});

function showStep(stepNumber) {
    // Esconde todos os passos
    document.querySelectorAll('.form-step').forEach(step => {
        step.style.display = 'none';
    });
    // Mostra o passo solicitado
    const activeStep = document.getElementById(`step-${stepNumber}`);
    if (activeStep) {
        activeStep.classList.add('active');
        activeStep.style.display = 'block';
    }
}

// Função para avançar para o próximo formulário
function nextStep(currentStep) {
    const currentStepDiv = document.getElementById(`step-${currentStep}`);
    const form = document.getElementById('cadastro-completo-form');

    // Pega todos os inputs e selects DENTRO do passo atual que são obrigatórios
    const inputs = currentStepDiv.querySelectorAll('input[required], select[required]');
    let allValid = true;

    // Checa a validade de cada campo no passo atual
    for (const input of inputs) {
        if (!input.checkValidity()) {
            allValid = false;
            break; // Para no primeiro campo inválido
        }
    }

    if (allValid) {
        // Se tudo estiver válido, avança para o próximo passo
        showStep(currentStep + 1);
    } else {
        // Se houver campos inválidos, o navegador exibirá a mensagem de erro padrão
        form.reportValidity();
    }
}

// Função para voltar ao formulário anterior
function previousStep(currentStep) {
    showStep(currentStep - 1);
}