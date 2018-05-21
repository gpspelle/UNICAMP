/* 	 Nome: Gabriel Pellegrino da Silva - EC 016 - Turma: S - RA: 172358

         O programa a seguir coleta as informacoes preenchidas em um formulario, monta a partir deste, um grafo composto pelos vértices, 
	 que correspondem, as informacoes pessoais, e arestas, que relacionam-se a media geometrica entre essas duas pessoas. 
	 A partir dos dados pessoais coletados e das notas fornecidas por cada pessoa as outras, desenvolvemos uma sequencia de cálculos 
	 que visavam calcular a sintonia entre a pessoa mais popular do grupo e as pessoas pelas quais o mais popular se interessaria. E tam-
	 bem escolhe a pessoa com maior sintonia para com o popular.  
																																		   */

#include <stdio.h> 
#include <stdlib.h> 
#include <math.h>
#include <string.h>
#include <ctype.h>

//Typedef nas variaveis que serao utilizadas
typedef char string[200];
typedef struct { //definindo uma struct para armazenar os dados pessoais
	char nome[200];
	struct{
		int dia;
		int mes;
		int ano;
	}Nasc;
	char sexo;
	char preferencia;
}PESSOA;
typedef struct { //definindo uma struct para coletar as notas dadas aos outros participantes e a média
	int nota[40];
}NOTA;

//Funcoes usadas pelo programa
void zeradorDeMatriz(int N, double *ponteiroMedia)
{	//zera uma matriz	
	int i;
	for(i=0; i<N*N; i++) 
		*(ponteiroMedia + i) = 0; 
}
void zeradorDeVetor_int(int N, int *ponteiroP)
{	//zera um vetor de inteiros
	int i;
	for(i=0; i<N; i++) 
		*(ponteiroP+i) = 0;
}
void zeradorDeVetor_double(int N, double *ponteiroSint)
{	//zera um vetor de double
	int i;
	for(i=0; i<N; i++) 
		*(ponteiroSint+i) = 0;
}
void pegaDados(int N, PESSOA *ponteiroStrP, NOTA *ponteiroStrN)
{	//funcao que coleta os dados fornecidos pelo formulario
	int i, j;
	for(i=0; i<N; i++) //leitura das informações de cada pessoa, tais como nome, data de nascimento, sexo, preferencia sexual e 
			   //as notas que foram dadas aos outros alunos
	{ 
		scanf(" %[^\n]s", (ponteiroStrP+i)->nome);
		scanf("%d/%d/%d", &(ponteiroStrP+i)->Nasc.dia, &(ponteiroStrP+i)->Nasc.mes, &(ponteiroStrP+i)->Nasc.ano);
		scanf(" %c", &(ponteiroStrP+i)->sexo);
		scanf(" %c", &(ponteiroStrP+i)->preferencia);
		for(j=0; j<N; j++) 
		{
			if(j!=i)
				scanf(" %d", &(ponteiroStrN+i)->nota[j]);
		}
	} 
}
void calculaMedia_Aresta(int N, NOTA *ponteiroStrN, int *ponteiroP, double *ponteiroMedia)
{	//funcao que calcula o valor da Aresta (media geometrica entre duas pessoas)
	int i, j;
	for(i=0; i<N; i++) //1. para cada pessoa
			for(j=0; j<N; j++) //2. vai comparar com todas as outras pessoas
			{
				if((sqrt((ponteiroStrN+i)->nota[j]*(ponteiroStrN+j)->nota[i])>=5) && i!=j) //3. o valor da média geométrica da nota entre essas 2  
				{						 //pessoas tem de ser maior do que 5
					*(ponteiroP+i)+=1; 	//e caso seja, declarando que o aluno tem aresta++
					*(ponteiroMedia+N*i+j)=sqrt((ponteiroStrN+i)->nota[j]*(ponteiroStrN+j)->nota[i]); //4. e armazenando esse valor da aresta
				}				
			}
}
int maisPopular(int N, int *ponteiroP) 
{	//averiguando qual eh o aluno mais popular
	int i, Pop, MP;
	Pop = 0; //o primeiro contato (0) eh o mais popular ate o momento
	MP = *ponteiroP; //jogando quantas arestas o contato 0 tem em MP
	for(i=1; i<N; i++) 
		if(*(ponteiroP+i)>MP) //caso o contato i tenha mais ligacoes que o MP
		{
			MP = *(ponteiroP+i); //atualiza o valor de MP
			Pop = i; //transforma o contato i em mais popular
		}
	//Devolve o mais popular
	return Pop;
}
void fazSoundex(int N, int Pop, PESSOA *ponteiroStrP, double *ponteiroMedia, char *ponteiroSoundex)
{	//Algoritmo Soundex, que codifica o nome de uma pessoa do interesse do mais popular de acordo com um padrao fornecido
	int i, j, letra, consoante=1, repet, para;  
	for(i=0; i<N; i++) 
	{
		para = 1; //variavel auxiliar para saber quando parar
		if( ( ( ((ponteiroStrP+Pop)->preferencia) == ( (ponteiroStrP+i)->sexo) && *(ponteiroMedia+N*Pop+i)>=5) && i!=Pop) || i==Pop) 		
		{ //condicoes: sexo da pessoa i igual à preferencia do mais popular, a pessoa i nao ser a pessoa mais popular, 
		  //e a media entre a pessoa i e a pessoa mais popular ser maior do que 5, mas tambem realizar o Soundex para o mais popular		
			repet=0; //variavel para armazenar quantas vezes a execucao abaixo foi realizada
			letra=tolower((ponteiroStrP+i)->nome[0]); //variavel para armazenar a primeira letra do nome da pessoa
			*(ponteiroSoundex+4*i) = (ponteiroStrP+i)->nome[0]; //colocando no Soundex a primeira letra do nome da pessoa
			for(j=1; para && repet<3; j++) 
			{ //Coletando as primeiras tres consoantes do nome e criando o nome codificado das pessoas
				switch((ponteiroStrP+i)->nome[j]) //cada letra do nome da pessoa eh a variavel do switch
				{ 
					case 'j': 
					case 'g': if((letra=='g' && consoante) || (letra=='j' && consoante)) { break;} repet++; *(ponteiroSoundex+4*i+repet) = 1; letra = 'g'; consoante =1;  break;
					case 's':
					case 'z':
					case 'x': if((letra=='x' && consoante) || (letra=='s' && consoante) || (letra=='z' && consoante)) { break;} repet++; *(ponteiroSoundex+4*i+repet) = 2; letra = 'x'; consoante =1;  break;
					case 'c':
					case 'k':
					case 'q': if((letra=='q' && consoante) || (letra=='c' && consoante) || (letra=='k' && consoante)) { break;} repet++; *(ponteiroSoundex+4*i+repet)=3; letra = 'q'; consoante =1; break;
					case 'b':
					case 'p': if((letra=='p' && consoante) || (letra=='b' && consoante)) { break;} repet++; *(ponteiroSoundex+4*i+repet)=4; letra = 'p'; consoante =1; break;
					case 'm':
					case 'n': if((letra='n' && consoante) || (letra=='m' && consoante)) { break;} repet++; *(ponteiroSoundex+4*i+repet)=5; letra = 'n'; consoante =1;  break;
					case 'd':
					case 't': if((letra=='t' && consoante) || (letra=='d' && consoante)) { break;} repet++; *(ponteiroSoundex+4*i+repet)=6; letra = 't'; consoante=1;  break;
					case 'f': 
					case 'v': if((letra=='v' && consoante) || (letra=='f' && consoante)) { break;}  repet++; *(ponteiroSoundex+4*i+repet)=7; letra = 'v'; consoante=1; break;
					case 'l': if(letra=='l' && consoante) { break;} repet++; *(ponteiroSoundex+4*i+repet)=8; letra = 'l'; consoante=1; break;
					case 'r': if(letra=='r' && consoante) { break;} repet++; *(ponteiroSoundex+4*i+repet)=9; letra = 'r'; consoante=1; break;
					case ' ': para=0; break;
					default: consoante =0; break; 
				}	
			}	
			//adicionando a quantidade de zeros necessário, dependendo de quantas vezes o bloco acima foi executado					
			if(repet==0) { *(ponteiroSoundex+4*i +1)= 0; *(ponteiroSoundex+4*i+2) = 0; *(ponteiroSoundex+4*i+3) = 0; } 
			else if(repet==1) { *(ponteiroSoundex+4*i+2) = 0; *(ponteiroSoundex+4*i+3) = 0; } 
			else if(repet==2) { *(ponteiroSoundex+4*i+3) = 0; } 
		}	
	}
}
void fazLogia(int N, int Pop, int d, int m, int a, PESSOA *ponteiroStrP, int *ponteiroLogia, double *ponteiroMedia)
{ 	//funcao que calcula a numerologia de cada pessoa que interessa ao mais popular
	int i; 
	for(i=0; i<N; i++) 
	{		//Caso a pessoa seja do interesse, e a media entre eles seja maior, ou igual, do que 5
			if(((ponteiroStrP+Pop)->preferencia==(ponteiroStrP+i)->sexo && *(ponteiroMedia+N*Pop+i)>=5) || i==Pop) 
			{	//Calculo do primeiro numero da numerologia
				*(ponteiroLogia+3*i) = (ponteiroStrP+i)->Nasc.dia + (ponteiroStrP+i)->Nasc.mes + ((ponteiroStrP+i)->Nasc.ano%10) +
				(((ponteiroStrP+i)->Nasc.ano/10)%10) + (((ponteiroStrP+i)->Nasc.ano/100)%10) + (((ponteiroStrP+i)->Nasc.ano/1000)%10); 
				while(*(ponteiroLogia+3*i)>=10) 
					*(ponteiroLogia+3*i) = (*(ponteiroLogia+3*i)/10) + (*(ponteiroLogia+3*i)%10);
				//Agora, o calculo dos numeros restantes, de acordo com a idadade da pessoa
				if(a - ((ponteiroStrP+i)->Nasc.ano)<10)
				{ 
					*(ponteiroLogia+3*i+1) = (a - (ponteiroStrP+i)->Nasc.ano)%10;
					*(ponteiroLogia+3*i+2) = 0;
				}
				else if(a - (ponteiroStrP+i)->Nasc.ano>100) 
				{ 
					*(ponteiroLogia+3*i+1) = ((a - (ponteiroStrP+i)->Nasc.ano)/10)/10;
					*(ponteiroLogia+3*i+2) = ((a - (ponteiroStrP+i)->Nasc.ano)/10)%10;
				}
				else if((m>(ponteiroStrP+i)->Nasc.mes) || (m==(ponteiroStrP+i)->Nasc.mes && d>=(ponteiroStrP+i)->Nasc.dia))
				{
					*(ponteiroLogia+3*i+1) = (a - (ponteiroStrP+i)->Nasc.ano)/10;
					*(ponteiroLogia+3*i+2) = (a - (ponteiroStrP+i)->Nasc.ano)%10; 
				}
				else if((m<(ponteiroStrP+i)->Nasc.mes) || (m==(ponteiroStrP+i)->Nasc.mes && d<(ponteiroStrP+i)->Nasc.dia))
				{
					*(ponteiroLogia+3*i+1) = (a - (ponteiroStrP+i)->Nasc.ano -1)/10;
					*(ponteiroLogia+3*i+2) = (a - (ponteiroStrP+i)->Nasc.ano -1)%10;
				}	
			}	
	}
}
void calculaSintonia(int N, int Pop, PESSOA *ponteiroStrP, double *ponteiroMedia, char *ponteiroSoundex, int *ponteiroLogia, double *ponteiroSint, NOTA *ponteiroStrN)
{	//funcao que calcula a sintonia das pessoas que interessam o mais popular
	int i, j;
	double AchouLogia, AchouSoundex;
	for(i=0; i<N; i++) 
	{
		AchouLogia=0;
		AchouSoundex=0;
		if((ponteiroStrP+Pop)->preferencia==(ponteiroStrP+i)->sexo && *(ponteiroMedia+N*Pop+i)>=5) 
		{	//Primeiro, calcula-se a porcentagem de semelhanca do Soundex
			for(j=0; j<4; j++) 
				if((*(ponteiroSoundex+4*Pop+j))==(*(ponteiroSoundex+4*i+j))) 
					AchouSoundex++;	
			//E colocamos esse valor na sintonia da i-esima pessoa
			*(ponteiroSint+i)=3*(AchouSoundex/(double)4); 
			//Depois calculamos a porcentagem de semelhanca da numerologia
			for(j=0; j<3; j++)
				if(*(ponteiroLogia+3*Pop+j)==*(ponteiroLogia+3*i+j)) 
					AchouLogia++; 
			//E somamos esse valor ao acumulado na numerologia dessa i-esima pessoa
			*(ponteiroSint+i)+=5*(AchouLogia/(double)3); 
			//E tambem somamos a media geometrica entre eles dividido por 10
			*(ponteiroSint+i)+=(2*(sqrt((ponteiroStrN+Pop)->nota[i]*(ponteiroStrN+i)->nota[Pop])))/(double)10;
			//E entao dividimos o resultado final por 10
			*(ponteiroSint+i)/=(double)10;
		}
	}
}
int maiorSintonia(int N, double *ponteiroSint)
{	//Funcao que descobre qual das pessoas tem mais sintonia com a mais popular
	int i, escolhidx;
	double maiorSint = 0;  
		for(i=0; i<N; i++) 
			if(*(ponteiroSint+i)>maiorSint) 
			{
				maiorSint = *(ponteiroSint+i);
				escolhidx = i;	
			}
	//Devolve a pessoa escolhida
	return escolhidx; 
}
int main(int argc, char* argv[]) 
{
	NOTA n[40]; //criando um vetor de 40 posições do tipo NOTA(struct) chamado n
	PESSOA peop[40]; //criando um vetor de 40 posições do tipo PESSOA(struct) chamado peop

	int N, d, m, a, Pop;
	int P[40];
	double media[40][40];
	int Logia[40][3];	//Blocos de informacoes utilizados pelo programa
	double sintonia[40];	
	int escolhidx;
	char Soundex[40][4];
	
	NOTA *ponteiroStrN = &n[0];
	PESSOA *ponteiroStrP = &peop[0];
	double *ponteiroMedia = &media[0][0];
	int *ponteiroP = &P[0];		//Ponteiros utilizados pelo programa
	double *ponteiroSint = &sintonia[0];
	char *ponteiroSoundex = &Soundex[0][0];
	int *ponteiroLogia = &Logia[0][0];

	//Leitura da data atual, e tambem do numero de alunos
	scanf("%d/%d/%d", &d, &m, &a); //d = dia, m = mes, a = ano
	scanf("%d", &N); 	
	//Chamando um conjunto de funcoes para zerar os vetores utilizados
	zeradorDeMatriz(N, ponteiroMedia);
	zeradorDeVetor_int(N, ponteiroP);
	zeradorDeVetor_double(N, ponteiroSint);
	//Chamando uma funcao para coletar os dados nas estruturas Pessoas (peop) e sobre as Notas (n);
	pegaDados(N, ponteiroStrP, ponteiroStrN);
	//Chamando uma funcao para calcular as medias e estabelecer as Arestas, com bases nas Notas coletadas
	calculaMedia_Aresta(N, ponteiroStrN, ponteiroP, ponteiroMedia);
	//Chamando uma funcao que devolve qual dos alunos eh o mais popular
	Pop = maisPopular(N, ponteiroP);	
	//Chamando uma funcao para realizar o algoritmo Soundex
	fazSoundex(N, Pop, ponteiroStrP, ponteiroMedia, ponteiroSoundex);
	//Chamando uma funcao para calcular a numerologia 
	fazLogia(N, Pop, d, m, a, ponteiroStrP, ponteiroLogia, ponteiroMedia);  
	//Chamando uma funcao para calcular a sintonia 
	calculaSintonia(N, Pop, ponteiroStrP, ponteiroMedia, ponteiroSoundex,  ponteiroLogia, ponteiroSint, ponteiroStrN); 
	//Chamando uma funcao que devolve o contato "escolhidx"
	escolhidx = maiorSintonia(N, ponteiroSint);
	//Imprimindo a saída 
	printf("%s combina com %s com %.2lf de sintonia s2\n", peop[Pop].nome, peop[escolhidx].nome, sintonia[escolhidx]);		

	return 0;
}
