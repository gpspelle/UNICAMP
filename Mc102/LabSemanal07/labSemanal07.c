/* Nome: Gabriel Pellegrino da Silva - Turma: S - Ra: 172358
	
	O programa a seguir realiza os procedimentos necessarios para automatizar uma
	lista de informacoes pertinentes a um consultorio medico. Que consta com um ve
	tor de doenças e medicos e uma lista encadeada de consultas. Podendo ser inserida,
	removida ou alteradas informacoes dessa lista. E ao fim, gera-se um relatorio con
	tendo as informacoes, ordenadas, da lista. 
								*/	
#include "inicio.h"					
#include "operacoes.h"		
#include <time.h>		
//FUNCAO PRINCIPAL
int main(int argc, char* argv[]) {
	long int cpu_inic, cpu_fim;
	double tiq_sec;
	cpu_inic = clock();
	tiq_sec = (double)CLOCKS_PER_SEC; 
	int para = 0;
	int numDoencas, numMedicos;
	char *letraInicial = (char *)malloc(sizeof(char));
	int determinante;
	char *operacao = (char *)malloc(sizeof(char)*8);
	Doencas *pointerDoencas; 
	int *pointerMatriz;
	Medicos *pointerMedicos;
	Controle *ctrl = (Controle *)malloc(sizeof(Controle));
	ctrl->qntd = 0; 
	pointerMatriz = (int *)malloc(sizeof(int)*4);

	scanf(" %d", &numDoencas); 
	pointerDoencas = (Doencas *)malloc(numDoencas*sizeof(Doencas));
	le_doencas(numDoencas, pointerDoencas);

	scanf(" %d", &numMedicos);
	pointerMedicos = (Medicos *) malloc(numMedicos*sizeof(Medicos));
	le_medicos(numMedicos, pointerMedicos);

	determinante = le_matriz(pointerMatriz);
	scanf(" %c", letraInicial);
	do {	
		//ESQUEMA DE LEITURA DA OPCAO
		scanf(" %s", operacao);
		if(!strcmp(operacao, "0")) { para = 1; }
		else if(!strcmp(operacao, "add")) { operacao_add(determinante, pointerMatriz, ctrl); }
		else if(!strcmp(operacao, "rm")) { operacao_rm(ctrl, pointerMatriz); }
		else if(!strcmp(operacao, "altdiag")) { operacao_diag(numDoencas, pointerDoencas, ctrl, pointerMatriz); }
		else if(!strcmp(operacao, "altdata")) { operacao_data(ctrl, pointerMatriz); }
		else { operacao_med(ctrl, numMedicos, pointerMedicos, pointerMatriz); }	
	}while(!para);

	free(pointerDoencas);
	free(pointerMedicos);

	//CONDICOES ESPECIAIS PARA CASO NAO TENHA MEMBROS NA LISTA, OU TENHA APENAS 1
	if(ctrl->qntd==1)
		ctrl->qntd++;
	else if(ctrl->qntd==0)
		return 0;
	//CHAMADA DA FUNCAO QUE IMPRIMEM A SAIDA
	imprime_lista(ctrl, letraInicial, pointerMatriz, determinante);		
	cpu_fim = clock();
	printf("Tempo: %f\n", (cpu_fim-cpu_inic)/tiq_sec);	 	
	return 0;
}
