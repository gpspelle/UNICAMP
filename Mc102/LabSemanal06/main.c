
#include <stdio.h>
#include <stdlib.h>
#include "funcoes.h"

int main(int argc, char* argv[]) 
{	/* DECLARACAO DAS LIDAS NA MAIN, E DA STRUCT QUE ARMAZENARÁ AS INFORMACOES E UTILIZADAS
	   PARA IMPRIMIR O ESCOLHIDX PERTINENTES A CADA FUNCIONARIO 			        */	
	double gasto=0; 
	int fator=0;
	int numFuncionarios=0;
	int escolhidx=0;
	dados Funcionarios[101];
 	/* DECLARACAO DOS POINTEIRO UTILIZADO */
	dados *pointerFuncionario = &Funcionarios[0];

	/*LEITURA DO GASTO, FATOR E NUMERO DE FUNCIONARIOS */
	scanf("%lf", &gasto);
	scanf("%d", &fator);
	scanf("%d", &numFuncionarios);
	
	/* CHAMADA DAS FUNCOES */
	zera_struct(pointerFuncionario);
	pega_nome_produtividade(numFuncionarios, pointerFuncionario);
	pega_nome_chefe(numFuncionarios, pointerFuncionario);
	descobre_hierarquia(numFuncionarios, pointerFuncionario);
	produtividade_relativa(numFuncionarios, pointerFuncionario);
	salario(numFuncionarios, pointerFuncionario);
	soma_salario(numFuncionarios, pointerFuncionario, gasto);
	qualidade(numFuncionarios, pointerFuncionario, fator);
	escolhidx = maior_qualidade(numFuncionarios, pointerFuncionario);

	/* IMPRIMINDO A RESPOSTA */ 
	printf("%s %.2lf\n", (pointerFuncionario+escolhidx)->nomeFuncionario, (pointerFuncionario+escolhidx)->qualidade);

	// SYSTEM ("PAUSE")
	return 0;
}	
