/* 	Nome: Gabriel Pellegrino da Silva RA: 172358

	O programa a seguir tem as funcionalidades de uma agenda telefônica, para até 10 contatos pessoais e 10 contatos profissionais.
	Logo, apresenta funcoes como inserir, apagar, alterar, buscar e listar os contatos dessa lista. 
*/
	
#include <stdio.h>
#include <string.h>

int i=0, k=0; //posicao dos contatos utilizados atualmente
int tipo, contato;

typedef char string[60]; 

string nome[11][3], endereco[11][3];   
long long int numero_res[11][3], numero_cel[11][3], cpf[11][3];  //strings para armazenar as informacoes dos contatos

void inserir(int j, int tipo);
int apagar(char a[], int tipo);
int alterar(char a[], int tipo); 	
int buscar(char a[], int tipo);	//pragma das funcoes utilizadas
void listpess(void);
void listprof(void);
void listall(void);

int main(void) 
 { 
	int opcao;
	scanf("%d" , &opcao);   //descobrindo qual opcao sera realizada, antes do loop comecar, e 
				//para cada opcao, o programa entra em uma sequencia de comandos específicos
	while(1) 
	{ 
		if(opcao==0)  //Sair
		{ 
			printf("Obrigado!\n");		
			break; //Unica saida desse while(1)
		}
		else if(opcao==1)  //Inserir
		{ 	
			scanf("%d", &tipo); //descobrindo qual classe o contato a ser inserido se encaixa
			
			if(tipo==1) 
			{ 
				inserir(i, tipo); //inserindo o i-ésimo contato do tipo 1
				if(i>9) { //testando se a agenda esta lotada     
					printf("Desculpe, agenda lotada!\n"); 
				}
				else {
					printf("Inserido com sucesso!\n"); //mensagem de sucesso pedida
					i++;  //atualizando o valor de i
				}
			}
			else
			{
				inserir(k, tipo); //inserindo o k-ésimo contato do tipo 2
				if(k>9) { //testando se a agenda esta lotada
					printf("Desculpe, agenda lotada!\n");
				}
				else {
					printf("Inserido com sucesso!\n"); //mensagem de sucesso pedida
					k++; //atualizando o valor de k
				}
			}
		}
		else if(opcao==2)  //Excluir
		{
			char procurado[60];  //variável para armazenar o nome procurado
			scanf("%d", &tipo);  
			scanf(" %[^\n]s", procurado);
			if(apagar(procurado, tipo)) //apagar(procurado,tipo) retorna 1 se o nome foi encontrado, e 0 caso contrário
			{
				printf("Excluido com sucesso!\n"); //mensagem de sucesso pedida
				if(tipo==1) 
					 i--; //atualizando o valor de i
				else if(tipo==2)
					 k--; //atualizando o valor de k
			}
			else 
				printf("Desculpe, contato %s nao existe!\n", procurado); //imprimindo mensagem de erro se o contato não foi encontrado		
		}
		else if(opcao==3) //Alterar
		{ 	
			char a[60]; 
			scanf("%d", &tipo);
			scanf(" %[^\n]s", a);
			if(alterar(a, tipo)) //alterar(a,tipo) retorna 1 caso o contato tenha sido alterado - ele exista.  
				printf("Alterado com sucesso!\n");
			else 
				printf("Desculpe, contato %s nao existe!\n", a); //caso contrário, retorna que o contato não existe		
		}
		else if(opcao==4) //Buscar
		{ 
			char procurado[60];
			scanf("%d", &tipo);
			scanf(" %[^\n]s", procurado);
	
			if(buscar(procurado, tipo)) //buscar(procurado, tipo) retorna 1 caso tenha sido encontrado o contato
			{ 
				printf("%s\n", nome[contato][tipo]); 			
				printf("%lld\n", cpf[contato][tipo]);
				printf("%s\n", endereco[contato][tipo]);		//imprimindo as informações
				printf("%lld\n", numero_res[contato][tipo]);
				printf("%lld\n", numero_cel[contato][tipo]);
				printf("Buscado com sucesso!\n");
			}
			else 
				printf("Desculpe, contato %s nao existe!\n", procurado);		 
		}
		else if(opcao==5) //Listar pessoais
		{ 
			if(i!=0) //testando se a agenda está vazia
			{
				listpess();
				printf("Listado com sucesso!\n");	
			}
			else
				printf("Desculpe, agenda vazia!\n");			
		}
		else if(opcao==6) //Listar profissionais, com funcionalidades semelhantes a opcao 5
		{ 	
			if(k!=0) 
			{
				listprof(); 
				printf("Listado com sucesso!\n");
			}	
			else 
				printf("Desculpe, agenda vazia!\n");
		}
		else if(opcao==7) //Listar todos os contatos
		{ 
			if(i!=0)  //Se a agenda pessoal nao estiver vazia, imprimir os pessoais
			{
				listpess();
				printf("Listado com sucesso!\n");
			}	
			else
				printf("Desculpe, agenda vazia!\n");
			if(k!=0) //Se a agenda profissional nao estiver vazia, imprimir os profissionais
			{
				listprof();
				printf("Listado com sucesso!\n");
			}	
			else 
				printf("Desculpe, agenda vazia!\n");
		}
		scanf("%d" , &opcao); //leitura da nova opcao
	}
	return 0;
}

void inserir(int j, int tipo) //leitura dos dados a serem inseridos
{
	scanf(" %[^\n]s", nome[j][tipo]);
	scanf("%lld", &cpf[j][tipo]); 
	scanf(" %[^\n]s", endereco[j][tipo]);
	scanf("%lld", &numero_res[j][tipo]);
	scanf("%lld", &numero_cel[j][tipo]);

	return;
}
int apagar(char a[], int tipo) //comparacao do nome procurado aos nomes listados para efetuar a exclusao
{
	int achou=0;	
	int j;
	if(tipo==1) //excluindo um contato j do tipo 1, jogando as informacoes do contato j+1 no contato j.
	{  
		for(j=0; j<i; j++)
		{
			if( strcmp(a, nome[j][tipo]) == 0 ) 
			{ 
				achou=1;
				for(; j<i; j++) 
				{
					strcpy(nome[j][tipo], nome[j+1][tipo]); 
					cpf[j][tipo] = cpf[j+1][tipo];
					strcpy(endereco[j][tipo], endereco[j+1][tipo]); 
					numero_res[j][tipo] = numero_res[j+1][tipo];
					numero_cel[j][tipo] = numero_cel[j+1][tipo];
				}
			}
		}
	}
	else { //excluindo um contato j do tipo 2, jogando as informacoes do contato j+1 no contato j.
		for(j=0; j<k; j++)
		{
			if( strcmp(a, nome[j][tipo]) == 0 ) 
			{ 
				achou=1;
				for(; j<k; j++) 
				{
					strcpy(nome[j][tipo], nome[j+1][tipo]); 
					cpf[j][tipo] = cpf[j+1][tipo];
					strcpy(endereco[j][tipo], endereco[j+1][tipo]); 
					numero_res[j][tipo] = numero_res[j+1][tipo];
					numero_cel[j][tipo] = numero_cel[j+1][tipo];
				}
			}
		}
	}
	return achou;	
}
int alterar(char a[], int tipo) //comparacao do nome procurado aos nomes listados para efetuar a alteracao
{
	int j, achou=0; 

	for(j=0; j<i; j++)
	{
		if( strcmp(a, nome[j][tipo]) == 0 ) 
		{ 
			achou=1;
			scanf("%lld", &cpf[j][tipo]); 
			scanf(" %[^\n]s", endereco[j][tipo]);
			scanf("%lld", &numero_res[j][tipo]);
			scanf("%lld", &numero_cel[j][tipo]);
		}
	}
	return achou;	
}	
int buscar(char a[], int tipo) //indentificando o contato procurado 
{	
	int m, achou=0;
	for(m=0; m<i; m++) 
	{ 
		if ( strcmp(a, nome[m][tipo]) == 0 ) 
		{
			contato=m;
			achou=1;
		}
	}
	return achou;
}
void listpess(void) //lista contatos profissionais
{
	int j;
	for(j=0; j<i; j++) 
	{
		printf("%s\n", nome[j][1]); 
		printf("%lld\n", cpf[j][1]);
		printf("%s\n", endereco[j][1]);
		printf("%lld\n", numero_res[j][1]);
		printf("%lld\n", numero_cel[j][1]);		
	}
	return;
}
void listprof(void) //lista contatos profissionais
{	
	int j;
	for(j=0; j<k; j++) 
	{
		printf("%s\n", nome[j][2]); 
		printf("%lld\n", cpf[j][2]);
		printf("%s\n", endereco[j][2]);
		printf("%lld\n", numero_res[j][2]);
		printf("%lld\n", numero_cel[j][2]);			
	}
	return;
}
