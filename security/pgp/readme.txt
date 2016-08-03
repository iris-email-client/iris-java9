
* add to iris.properties (example):
gpg.file.private=/home/pedro/.gnupg/secring.gpg
gpg.file.public=/home/pedro/.gnupg/pubring.gpg
gpg.file.secret=123456


* Generate keys:
gpg --gen-key

* List keys:
gpg --list-keys




EXEMPLO:
gpg --gen-key
Por favor selecione o tipo de chave desejado:
   (1) RSA e RSA (padrão)
   (2) DSA e Elgamal
   (3) DSA (apenas assinatura)
   (4) RSA (apenas assinar)
Sua opção? 1
RSA chaves podem ter o seu comprimento entre 1024 e 4096 bits.
Que tamanho de chave você quer? (2048) 
O tamanho de chave pedido é 2048 bits
Por favor especifique por quanto tempo a chave deve ser válida.
         0 = chave não expira
      <n>  = chave expira em n dias
      <n>w = chave expira em n semanas
      <n>m = chave expira em n meses
      <n>y = chave expira em n anos
A chave é valida por? (0) 0
A chave não expira nunca
Está correto (s/N)? s
Você precisa de um identificador de usuário para identificar sua chave; o
programa constrói o identificador a partir do Nome Completo, Comentário e
Endereço Eletrônico desta forma:
    "Heinrich Heine (Der Dichter) <heinrichh@duesseldorf.de>"

Nome completo: Iris Mail Client
Endereço de correio eletrônico: br.unb.cic.iris@gmail.com
Comentário: 
Você selecionou este identificador de usuário:
    "Iris Mail Client <br.unb.cic.iris@gmail.com>"

Muda (N)ome, (C)omentário, (E)ndereço ou (O)k/(S)air? o
Você precisa de uma frase secreta para proteger sua chave.