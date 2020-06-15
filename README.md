# ESII-G28
Nicole Carolina Abreu Freitas 82829
João Pedro Dias Constantino 82418
Margarida Fortes Realista 77603, ficheiro Pom com um erro e alínea c) do requisito 6 parcialmente implementada
Ana Mariana Louro 83634 Falta os coverage e JavaDoc 
Gonçalo Nuno Pacheco Silva 82295
Afonso Carvalho Alves 82420
Instruções para correr a aplicação:
1-Colocar o ficheiro .tar mais as pastas de conteúdo na diretoria wordpress 
2- No terminal do docker, entrar na diretoria wordpress na localização onde esta estiver
3- Executar o comando "load < nome.tar"
4- Garantir que as imagens do wordpress e do Mysql são as corretas (ficheiro .yml)
5- Correr o comando "docker-compose up -d"
6- Abrir o word press através do browser usando o link "192.168.99.100:8000"
Alternativamente aos pontos 1, 2 e 3 pode ser feito o pull da imagem no link https://hub.docker.com/r/gnpsa/wordpress através do comando "docker pull gnpsa/wordpress", de seguida seguir os passos a partir do 4
