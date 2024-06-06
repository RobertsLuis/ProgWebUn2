// *******************************************************************************
// ***  SOMENTE A PARTIR DESTE PONTO VOCÊ PODERÁ ALTERAR O CÓDIGO
// *******************************************************************************

document.addEventListener("DOMContentLoaded", function () {

    var dadosArquivados = sessionStorage.getItem('turmaSelecionada');
    console.log(dadosArquivados)
    if (dadosArquivados) {
        document.getElementById('turma').value = dadosArquivados;
    }
});

async function enviaNovo() {
    let dto = {
        id: '',
        turma: document.getElementById('turma').value,
        nome: document.getElementById('nome').value,
        matricula: document.getElementById('matricula').value,
        nota: document.getElementById('nota').value
    };
    let validation = await validarEnvio(dto);
    if (validation.status) {
        let json = JSON.stringify(dto);
        let request = new XMLHttpRequest();
        request.open('POST', '/novoAluno', true);
        request.setRequestHeader("Content-Type", "application/json");
        request.onreadystatechange = function () {
            if (request.readyState === XMLHttpRequest.DONE) {
                if (request.status === 200) {
                    window.location.href = '/';
                } else {
                    document.getElementById('mensagem').style.display = 'block';
                    document.getElementById('mensagem').innerText = request.responseText;
                    console.log(request.responseText);
                }
            }
        }
        request.send(json);
    } else {
        document.getElementById('mensagem').style.display = 'block';
        document.getElementById('mensagem').innerText = "Dados inválidos.";

        document.getElementById('turma').style.borderColor = validation.details['turma'] ? '#d3d3d3' : "red";
        document.getElementById('nome').style.borderColor = validation.details['nome'] ? '#d3d3d3' : "red";
        document.getElementById('matricula').style.borderColor = validation.details['matricula'] ? '#d3d3d3' : "red";
        document.getElementById('nota').style.borderColor = validation.details['nota'] ? '#d3d3d3' : "red";
    }
}