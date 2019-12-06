# iris-java9

__TODO:__ migrar o restante dos módulos relacionados a persistência. Apenas os módulos JDBC foram migrados.


### Configurar produtos

Cada produto deve configurado em um profile específico no 'projeto' _iris.produtos_

Já foram configurados 2 _produtos/profiles_ como exemplo (um CLI e outro GUI)


### Gerar artefatos

Na raiz do projeto executar: `mvn -P simple-cli` (trocando o _simple-cli_ pelo nome do profile)


### Executar produto

Por enquanto está sendo executado por script bash.

Se o produto gerado for CLI, executar o __run-cli.sh__

Se o produto gerado for GUI, executar o __run-gui.sh__
 
