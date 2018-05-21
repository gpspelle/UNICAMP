#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>
#include "funcoes.h"

//A SEGUIR, TODAS AS FUNÇÕES QUE SERÃO UTILIZADAS PELO ALGORITMO, COMENTADAS INDIVIDUALMENTE
void zera_struct(dados *pointerFuncionario)
{
	int i=0;
	for(i=0; i<100; i++)
	{
		(pointerFuncionario+i)->produtividade =0;
		(pointerFuncionario+i)->produtividadeRel =0;
		(pointerFuncionario+i)->salario =0;
		(pointerFuncionario+i)->nivelHierarquico =0;
		(pointerFuncionario+i)->qualidade =0;
		(pointerFuncionario+i)->presidente =0;
	}
}
void pega_nome_produtividade(int numFuncionarios, dados *pointerFuncionario)
{	/* ESSA FUNCAO COLETA OS DADOS DE ENTRADA, NOME E PRODUTIVIDADE E TAMBEM 
	   COLOCA COMO 1 O NIVEL HIERARQUICO DE TODOS OS FUNCIONARIOS E O PATRAO */
	int i=0;
	for(i=0; i<numFuncionarios; i++) 
	{ 
		scanf(" %s", (pointerFuncionario+i)->nomeFuncionario);
		scanf(" %d", &(pointerFuncionario+i)->produtividade);
		(pointerFuncionario+i)->nivelHierarquico = 1;
	}
}
void pega_nome_chefe(int numFuncionarios, dados *pointerFuncionario)
{	/* ESSA FUNCAO USA A SEGUNDA PARTE DAS ENTRADAS PARA ORGANIZAR NA STRUCT FUNCIONARIOS 
	   QUEM EH O "BOSS" DE CADA FUNCIONARIO, E CONSEQUENTEMENTE, DESCOBRE QUEM EH O PRESI
	   DENTE, JÁ QUE ELE NÃO POSSUI UM "BOSS".
	   A IDENTIFICACAO DO PRESIDENTE SE DA POR PRESIDENTE = 0. ADEMAIS, NESSA FUNCAO TAM 
	   BEM ZERAMOS A PRODUTIVIDADE RELATIVA DOS FUNCIONARIOS 			     */
	int i=0, j=0;
	string subordinado, chefe;
	for(i=0; i<numFuncionarios-1; i++)
	{
		scanf(" %s", subordinado); 
		scanf(" %s", chefe);
		for(j=0; j<numFuncionarios; j++)
			if(!strcmp(subordinado, (pointerFuncionario+j)->nomeFuncionario))
			{
				strcpy((pointerFuncionario+j)->boss, chefe);
				(pointerFuncionario+i)->produtividadeRel = 0;
				(pointerFuncionario+j)->presidente = 1;
			}	 
	} 
} 
double calcula_hierarquia(int numFuncionarios, dados *pointerFuncionario, int i)
{	/* ESSA FUNCAO CALCULA RECURSIVAMENTE A HIERARQUIA DOS FUNCIONARIOS, USANDO O CASO
	   BASE: NIVEL HIERARQUICO DO PRESIDENTE = 1. POR CONSEQUENCIA, O NIVEL HIERARQUICO
	   DAQUELES QUE TEM O PRESIDENTE COMO "BOSS" EH 1+1 = 2. ANALOGAMENTE, AQUELES QUE 
	   TEM ESSE ULTIMO COMO CHEFE, POSSUEM NIVEL HIERARQUICO 2+1 = 3. ASSIM POR DIANTE */
	int j=0;
	if(!(pointerFuncionario+i)->presidente)
	{
		return (pointerFuncionario+i)->nivelHierarquico;
	}
	for(j=0; j<numFuncionarios; j++)
	{
		if(!strcmp((pointerFuncionario+i)->boss, (pointerFuncionario+j)->nomeFuncionario))
		{
			return 1+calcula_hierarquia(numFuncionarios, pointerFuncionario, j);	
		}
	}
	return (double)0; //ELIMINANDO UM WARNING DE QUE UMA FUNCAO COM RETORNO "NAO TINHA" RETORNO
}	
void descobre_hierarquia(int numFuncionarios, dados *pointerFuncionario)
{
	/* NESSA FUNCAO, COLOCAMOS 1 NO NIVEL HIERARQUICO DO PRESIDENTE E DEPOIS CALCULAMOS,
	   CHAMANDO A FUNCAO ACIMA (calcula_hierarquia), A HIERARQUIA DOS FUNCIONARIOS.    */
	int i=0, j=0;
	for(i=0; i<numFuncionarios; i++)
	{
		if(!(pointerFuncionario+i)->presidente)
		{
			(pointerFuncionario+i)->nivelHierarquico = 1;		
		}
	}
	for(j=0; j<numFuncionarios; j++)
	{
		for(i=0; i<numFuncionarios; i++)
		{
			if(!strcmp((pointerFuncionario+j)->boss, (pointerFuncionario+i)->nomeFuncionario))
			{
				(pointerFuncionario+j)->nivelHierarquico = 1 + calcula_hierarquia(numFuncionarios, pointerFuncionario, i);			
			} 		
		}			
	}
}
double calcula_relativa(int numFuncionarios, int i, dados *pointerFuncionario)
{	/* AQUI IREMOS CALCULAR A PRODUTIVIDADE RELATIVA DE TODOS OS FUNCIONARIOS RECURSIVAMENTE.
	   PARA ISSO, UTILIZAREMOS O CASO BASE: A PRODUTIVIDADE RELATIVA DOS FUNCIONARIOS SEM SU
	   BORDINADOS EH IGUAL A SUA PRODUTIVIDADE. JA QUE A PRODUTIVIDADE RELATIVA DOS DEMAIS  
	   FUNCIONARIOS DEPENDE DA PRODUTIVIDADE RELATIVA DE SEUS SUBORDINADOS. 	
	   int cont: contador de quantas subordinados o i-esimo funcionario possui. 			
	   double aux[101]: variável para armazenar a soma das produtividades relativas de todos
	   os subordinados do i-esimo funcionario. 						 */
	int j=0, cont=0;
	double aux[101];
	aux[i] = 0; 
	for(j=0; j<numFuncionarios; j++)
	{
		if(!strcmp((pointerFuncionario+i)->nomeFuncionario, (pointerFuncionario+j)->boss))
		{
			cont++;
			aux[i]+= calcula_relativa(numFuncionarios, j, pointerFuncionario); 			
		}		
	}
	if(cont)
	{
		return ((pointerFuncionario+i)->produtividade + aux[i]/(double)cont)/(double)2;
	}
	return (double)(pointerFuncionario+i)->produtividade;
}
void produtividade_relativa(int numFuncionarios, dados *pointerFuncionario)
{	/* FUNCAO QUE SIMPLESMENTE CHAMA A FUNCAO ACIMA (calcula_relativa) E ALOCA ESSE VALOR */ 
	int i=0;
	for(i=0; i<numFuncionarios; i++)
	{
		(pointerFuncionario+i)->produtividadeRel += calcula_relativa(numFuncionarios, i, pointerFuncionario);
	}
}
double fracao_salario(int numFuncionarios, dados *pointerFuncionario, int i)
{	/* ESSA FUNCAO CALCULA A FRACAO DO SALARIO DO PRESIDENTE QUE CADA FUNCIONARIO POSSUI, RECURSIVAMENTE. 
	   double cont: contador que armazena quantos subordinados, alem do proprio i, tem o chefe do i-esimo
 	   funcionario. 		 								      */		
	int j=0, k=0;
	double cont=0;
	if(!(pointerFuncionario+i)->presidente)
	{
		return (double)1;
	}
	for(j=0; j<numFuncionarios; j++)
	{
		if(!strcmp((pointerFuncionario+i)->boss, (pointerFuncionario+j)->nomeFuncionario))
		{
			for(k=0; k<numFuncionarios; k++) 
			{
				if(!strcmp((pointerFuncionario+k)->boss, (pointerFuncionario+j)->nomeFuncionario))
				{
					cont++;
				}			
			}
			return ((cont/(cont+1))*fracao_salario(numFuncionarios, pointerFuncionario, j));
		}
	}
	return 0; //ELIMINANDO UM WARNING DE QUE UMA FUNCAO COM RETORNO "NAO TINHA" RETORNO
}
void salario(int numFuncionarios, dados *pointerFuncionario)
{	/* FUNCAO QUE CHAMA TODOS OS FUNCIONARIOS E CALCULA A SUA FRACAO DO SALARIO EM RELACAO AO PRESIDENTE */
	int i=0;
	for(i=0; i<numFuncionarios; i++)
	{
		(pointerFuncionario+i)->salario = fracao_salario(numFuncionarios, pointerFuncionario, i);
	}
}
void calcula_salario(int numFuncionarios, dados *pointerFuncionario, double soma, double gasto)
{	/* AGORA, COM O VALOR DA FRACAO CALCULADA, CALCULAMOS O SALARIO INDIVIDUAL DOS FUNCIONARIOS, UTILI-
	   ZANDO O GASTO TOTAL DA EMPRESA, E A SOMA DAS FRACOES CALCULADAS PELA FUNCAO (fracao_salario)    */
	int j=0;
	for(j=0; j<numFuncionarios; j++)
	{
		(pointerFuncionario+j)->salario = (gasto/soma)*(pointerFuncionario+j)->salario;
	}
}
void soma_salario(int numFuncionarios, dados *pointerFuncionario, double gasto)
{	/* NESSA FUNCAO, SOMAMOS AS FRACOES DE SALARIO DE TODOS OS FUNCIONARIOS PARA CALCULAR O SALARIO
	   DO PRESIDENTE E POR CONSEQUENCIA DOS FUNCIONARIOS RESTANTEs					*/
	int j=0;
	double soma=0;
	for(j=0; j<numFuncionarios; j++) 	
	{
		soma+=(pointerFuncionario+j)->salario;
	}
	calcula_salario(numFuncionarios, pointerFuncionario, soma, gasto);
}
double calcula_log(dados *pointerFuncionario, int j)
{	/* NESSA FUNCAO CALCULAMOS O LOG* DE CADA FUNCIONARIO RECURSIVAMENTE, ATENTANDO PARA O USO DE DOUBLE NO NIVEL
	   HIERARQUICO DOS FUNCIONARIOS								   		      */
	if((pointerFuncionario+j)->nivelHierarquico<=1)
	{
		return 0;
	}
	(pointerFuncionario+j)->nivelHierarquico = log2 ((pointerFuncionario+j)->nivelHierarquico);
	return (1 + calcula_log(pointerFuncionario, j));
}
double calcula_qualidade(dados *pointerFuncionario, double fator, int j)
{	/* FUNCAO QUE CHAMA A FUNCAO ACIMA (calcula_log) PARA DEVOLVER A QUALIDADE DO FUNCIONARIO */
	return fator*(calcula_log(pointerFuncionario, j)+1)*(((pointerFuncionario+j)->produtividadeRel)/((double) log2 ((pointerFuncionario+j)->salario)));
}	
void qualidade(int numFuncionarios, dados *pointerFuncionario, double fator)
{ 	/* ESSA FUNCAO CHAMA A FUNCAO ACIMA (calcula_qualidade) PARA ALOCAR NA POSICAO CORRETA O VALOR DA QUALIDADE 
	   DO J-ESIMO FUNCIONARIO										    */
	int j=0;
	for(j=0; j<numFuncionarios; j++)
	{
		(pointerFuncionario+j)->qualidade = calcula_qualidade(pointerFuncionario, fator, j);
	}
}
int maior_qualidade(int numFuncionarios, dados *pointerFuncionario)
{	/* ESSA FUNCAO SIMPLESMENTE CALCULA QUAL EH O FUNCIONARIO ESCOLHIDO, OU SEJA,
	   AQUELE COM A MAIOR QUALIDADE						      */
	int j=0;
	double maior = 0;
	int escolhidx=0;
	for(j=0; j<numFuncionarios; j++)
	{
		if((pointerFuncionario+j)->qualidade>maior)
		{
			maior = (pointerFuncionario+j)->qualidade; 
			escolhidx = j;
		}
	}
	return escolhidx;
}	
