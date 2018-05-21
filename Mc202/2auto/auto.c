/* Gabriel Pellegrino da Silva - Ra: 172358
** O programa a seguir calcula o numero de acessos a uma regiao da memoria contendo uma lista, usando 
** 3 algoritmos de reordenacao da lista, de acordo com os acessos que sao feitos. No entanto, ha de se
** considerar o gasto de tempo de implementacao para fazer com que a lista seja percorrida apenas uma
** vez pelo algoritmo de 'count'. Nesse programa, nao foram utilizados "dummies", mas eles poderiam
** ser uteis para eliminar o numero de casos a serem tratadas no algoritmo de 'count', por exemplo.
*/

#include <stdio.h>
#include <stdlib.h>

typedef struct no {	//Definicao das estruturas
	short num;
	short count;
	struct no *prox;
	}Tno;
	
typedef struct cabeca{ //A cabeca contem um apontador para o numero de acessos
	short MTF, T, C;	//a memoria para cada algoritmo
	struct no *inic;
	}Tcabeca;
	
void criar(Tcabeca *cabeca, short N) { //Criando uma lista, na ordem invertida, ou seja
	short i; //para N = 5: 5->4->3->2->1-|
	Tno *no = (Tno *)malloc(sizeof(Tno)); 
	cabeca->inic=no;
	for(i=0; i<=N; i++) { 
		no->count = 0;
		Tno *novo = (Tno *)malloc(sizeof(Tno));
		no->prox=novo;
		no->num=i;
		no = novo;
		no->prox = NULL;
	}
	return;
}

void move_to_front(Tcabeca *cabeca, short *acessos, int R) {
	/*Algoritmo de move_to_front, movendo para a primeira posicao da lista
	**um no, sempre que ele for acessado. 
	**Esquema utilizado: faz duas auxiliares apontarem para o inicio da lista, 
	**para percorrer com 'auxiliar' a lista ate encontrar o ponto acessado.
	**Depois, caminha-se com a 'auxiliar2' ate um ponto antes da 'auxiliar' 
	**e 'isola-se' 'auxiliar' da lista, para depois transforma-lo na primeira posicao
	*/
	Tno *auxiliar;
	Tno *auxiliar2;//auxiliares usadas para percorrer a lista e salvar as posicoes, bem como manipular a lista
	cabeca->MTF = 0;
	int j;
	short para=1;
	for(j=0; j<R; j++) {
		auxiliar = cabeca->inic;
		auxiliar2 = cabeca->inic;
		para = 0;
		do {
			if(*(acessos+j)==auxiliar->num) {
				if(cabeca->inic == auxiliar) 
					para = 1;
				else {
					while(auxiliar2->prox!=auxiliar)
						auxiliar2 = auxiliar2->prox;
					auxiliar2->prox = auxiliar->prox;
					auxiliar2 = cabeca->inic;
					cabeca->inic = auxiliar;
					auxiliar->prox = auxiliar2; 
				
					para = 1;
				}
			}
			else
				auxiliar = auxiliar->prox;
			cabeca->MTF+=1;
		}while(!para);
	}
	return;
}
void ordenar(Tcabeca *cabeca, short i) {

	/*Funcao simples usada para ordenar uma lista de duas formas, 
	**na ordem crescente (i=1) e decrescente (i=0)
	*/
	Tno *auxiliar;
	Tno *auxiliar2;
	Tno *posicao;
	short procura, guarda;
	auxiliar = cabeca->inic;

	do {
		auxiliar2 = auxiliar;
		if(i)
			procura = 101;
		else
			procura = 0;
		do { 
			if(i) {
				if(procura>(auxiliar2->num)) {
					procura = auxiliar2->num;
					posicao = auxiliar2;
				}
			}
			else {
				if(procura<(auxiliar2->num)) {
						procura = auxiliar2->num;
						posicao = auxiliar2;
				}
			}
			auxiliar2=auxiliar2->prox;
		}while(auxiliar2->prox!=NULL); 
		guarda = auxiliar->num;
		auxiliar->num = procura; 
		posicao->num = guarda; 
		
		auxiliar = auxiliar->prox;
	}while(auxiliar->prox!=NULL);
	
	return;
}
void transpose(Tcabeca *cabeca, short *acessos, int R) {
	/*Algoritmo transpose para verificar quantas vezes a memoria alocada para
	**a lista foi acessada. Nesse metodo, utilizamos tambem duas variaveis auxiliares
	**para percorrer a lista ate acharmos o ponto que queremos e depois o seu ponto anteces-
	**sor. Depois disso, realizamos a troca numerica dos valores contidos nos apontadores,
	**dando assim a imagem de que a ordem da lista foi trocada
	*/
	int j;
	short para;
	cabeca->T=0;
	Tno *auxiliar;
	Tno *auxiliar2;
	for(j=0; j<R; j++) {
		para = 0;
		auxiliar = cabeca->inic;
		auxiliar2 = auxiliar; 
		do {
			if(*(acessos+j)==auxiliar->num) {
				if(cabeca->inic==auxiliar)
					para = 1;
				else {
					while(auxiliar2->prox!=auxiliar)
						auxiliar2 = auxiliar2->prox;	
						
					para = auxiliar2->num;
					auxiliar2->num = auxiliar->num;
					auxiliar->num = para;
				}
			}
			else
				auxiliar = auxiliar->prox;
			cabeca->T+=1;
		}while(!para);
	}
	return;
}
void imprimir(Tcabeca *cabeca) {

	Tno *auxiliar;
	auxiliar = cabeca->inic;
	do {
		printf("%d ", auxiliar->num); 
		auxiliar = auxiliar->prox;
	}while(auxiliar->prox!=NULL);
	auxiliar = cabeca->inic;
	do {
		printf("%hi ", auxiliar->count); 
		auxiliar = auxiliar->prox;
	}while(auxiliar->prox!=NULL);
	printf("\n");
	
	return;
}
void count(Tcabeca *cabeca, short *acessos, int R) { 

	/*O algoritmo 'count', para que ele percorresse a lista do inicio ate o fim
	**apenas uma vez, requeriu um grande gasto de tempo de implementacao, para tratar
	**os casos possiveis de movimentacao dos nos, bem como alocacao de algumas variaveis
	**para controlar o fluxo de informacoes. 
	**Mas, de modo geral, em muito se assemelha com os outros, variando que a lista agora
	**se ordena pela quantidade de vezes que um no ja foi acessado. 
	*/
	Tno *auxiliar;
	Tno *auxiliar2;
	Tno *auxiliar3;	//alocando auxiliares
	Tno *guarda;
	short j, maior=0;
	for(j=0; j<R; j++) {
		auxiliar = cabeca->inic;
		auxiliar2 = cabeca->inic; //Fazendo 1 e 2 apontarem para o inicio da lista 
		if(cabeca->inic->num==*(acessos+j))
			cabeca->inic = cabeca->inic->prox; //Caso o primeiro no seja o procurado, faz-se o segundo no ser o novo primeiro
		else { 
			do {			
				auxiliar2 = auxiliar; //Do contrario, movimenta-se 1 e 2, mantendo-se 2 sempre atras de 1
				auxiliar = auxiliar->prox; //Ate acharmos o elemento que queremos
			}while(auxiliar->num!=*(acessos+j));
		}
		if(auxiliar->prox->num!=0) { //Caso o elemento que achamos nao seja o ultimo
			auxiliar->count+=1;
			if(auxiliar->count>=auxiliar->prox->count) //E tambem o seu contador seja maior do que o contador do no da frente
				auxiliar2->prox = auxiliar->prox;
			if(auxiliar->count>maior) //Atualizando valor do maior contador
				maior = auxiliar->count;
			auxiliar2 = auxiliar;
			if(auxiliar->count>=auxiliar->prox->count) {
				do {
					cabeca->C+=1; //Percorrendo o restante da lista, da posicao encontrada
					guarda = auxiliar2; //Ate o seu fim, para contar os acessos a memoria
					auxiliar2 = auxiliar2->prox;
				}while(auxiliar->count>=auxiliar2->count && auxiliar2->prox->num!=0);
			}
			if(auxiliar->count<maior) { //Caso o contador daquele no seja menor que o maior
				auxiliar3 = auxiliar2; //O se movera para tras do primeiro no com contador maior do que ele
				auxiliar2 = guarda;
			}
			else
				auxiliar3 = auxiliar2; //Caso contrario, ele se movera para depois do do maior contador
			while(auxiliar3->num!=0) { //Que ainda seja menor que ele
				auxiliar3 = auxiliar3->prox;
				cabeca->C+=1;
			}
			if(auxiliar->count>=auxiliar->prox->count) {
				auxiliar->prox = auxiliar2->prox; //Ajustando para onde o no transferido aponta
				auxiliar2->prox = auxiliar;
			}
		}
		else {
			cabeca->C+=1;
			auxiliar->count+=1;
			if(auxiliar->count>maior)
				maior = auxiliar->count;
		}
				imprimir(cabeca);
	}
	return;
}
int main(void) { 

	short N, i;
	int R;
	Tcabeca *cabeca = (Tcabeca *)malloc(sizeof(Tcabeca));
	scanf("%hi %d", &N, &R);
	short *acessos = (short *)malloc(sizeof(short)*R);
	for(i=0; i<R; i++)
		scanf(" %hi", &acessos[i]);
	criar(cabeca, N);
	ordenar(cabeca, 1); //ordenando em ordem crescente
	move_to_front(cabeca, acessos, R);
	ordenar(cabeca, 1);
	transpose(cabeca, acessos, R);
	ordenar(cabeca, 0); //ordenando em ordem decrescente
	count(cabeca, acessos, R);;
	printf("%hi %hi %hi\n", cabeca->MTF, cabeca->T, cabeca->C);
	
	return 0;
}
