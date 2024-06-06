// *******************************************************************************
// ***  SOMENTE A PARTIR DESTE PONTO VOCÊ PODERÁ ALTERAR O CÓDIGO
// *******************************************************************************



enviaEdit = function(id) {

    let dto = {
        id: id,
        turma: document.getElementById('turma').value,
        nome: document.getElementById('nome').value,
        matricula: document.getElementById('matricula').value,
        nota: document.getElementById('nota').value
    };

    let validation = validarEnvio(dto);
    if (validation.status){
        let json = JSON.stringify(dto);
        let request = new XMLHttpRequest();
        request.open('POST', '/updateAluno', true);
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
        console.log(json);
    }else{
        document.getElementById('mensagem').style.display = 'block';
        document.getElementById('mensagem').innerText = "Dados inválidos.";

        document.getElementById('turma').style.borderColor = validation.details['turma'] ? 'light-dark' : "red";
        document.getElementById('nome').style.borderColor = validation.details['nome'] ? 'light-dark' : "red";
        document.getElementById('matricula').style.borderColor = validation.details['matricula'] ? 'light-dark' : "red";
        document.getElementById('nota').style.borderColor = validation.details['nota'] ? 'light-dark' : "red";

    }


}

function deleteAluno(id) {
    console.log("Chamou a função de remoção")
    let request = new XMLHttpRequest();
    request.open('POST', '/delete', true);
    request.setRequestHeader("Content-Type", "application/json");
    request.onreadystatechange = function () {
        if (request.readyState === XMLHttpRequest.DONE) {
            if (request.status === 200) {
                window.location.href = '/';
            } else {
                // TODO: FAZER ALGO SE DEU ERRADO
            }
        }
    }
    request.send(JSON.stringify(id)); // Enviar o ID como JSON
}