# pmfinal

##Intruções básicas git
###Baixar
- Para windows:
 - https://git-scm.com/download/win
- Linux já vem instalado na maioria das distribuições

###Workflow
1. Copie este repositório com o comando: `git clone https://github.com/dfavato/pmfinal.git`
 - Isso irá copiar todos os arquivos disponíveis aqui para seu computador
 - Você só precisa fazer isso 1 vez
2. Faça as modificações nos arquivos, pastas, que achar necessário
3. Decida quais modificações você quer que estejam no próximo pacote (commit) com o comando
 - `git add .` para adicionar todos os arquivos modificados ou
 - `git add nome_do_arquivo` para adcionar apenas um arquivo
4. Feche o commit com o comando `git commit -m "Escreva uma mensagem aqui dizendo o que você mudou (seja claro e breve)"`
5. Mande o seu pacote (commit) para o github para que todos possam ver o que foi feito com `git push`
 - Faça isso sempre antes de parar de mexer no projeto, caso contrário alguém pode mudar o código que você também mudou ai fica difícil arrumar as divergências
6. Repita os passos trocando o passo 1 pelo comando `git pull`

###Outros comandos
- `git status` use esse comando o tempo todo, ele mostra o status de cada arquivo e te ajuda a entender como o git funciona
- Fez um commit errado e quer reverter o que foi feito? `git reset --hard HEAD` (não há como desfazer essa ação, tenha certeza que você quer excluir o que foi feito.
- Mais ajuda: https://www.atlassian.com/git/tutorials/what-is-version-control

