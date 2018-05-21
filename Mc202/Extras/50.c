#include <stdio.h>
#include <stdlib.h>

typedef struct no { 
	double valor;
	struct no *prox;
}no;
typedef struct lista { 
	no *inicio;
}lista;
 
void criar_lista(lista *L) { 
	
	no *cabeca = (no *)malloc(sizeof(no));
	no *cauda = (no *)malloc(sizeof(no));
	
	L->inicio = cabeca; 
	cabeca->prox = cauda;
	cauda->prox = NULL;
}
void inserir(lista *L) {

	int posicao, i;
	double val;
	no *auxiliar, *guarda;
	scanf(" %d %lf", &posicao, &val);
	auxiliar=L->inicio;
	
	for(i=0; i<posicao && auxiliar->prox->prox!=NULL; i++, auxiliar = auxiliar->prox);
	
	no *novo = (no *)malloc(sizeof(no));
	guarda = auxiliar->prox;
	auxiliar->prox = novo;
	novo->prox = guarda; 
	
	novo->valor = val;
}
void imprimir(lista *L) { 
	
	no *auxiliar; 
	auxiliar = L->inicio;
	while(auxiliar->prox->prox!=NULL) {
		auxiliar = auxiliar->prox;
		printf("%.2lf ", auxiliar->valor);
	}
	printf("\n");
}
void remover(lista *L) { 

	int i, posicao;
	no *auxiliar, *auxiliar2;
	auxiliar = L->inicio;
	scanf(" %d", &posicao);
	for(i=0; i<=posicao && auxiliar->prox->prox!=NULL; i++, auxiliar = auxiliar->prox) 
		auxiliar2=auxiliar;
	
	if(i>posicao) {
		auxiliar2->prox = auxiliar->prox;
		free(auxiliar);
	}
	
}
void comando(lista *L) {

	char operacao = 1; 
	while(operacao) { scanf(" %c", &operacao);
		
		switch(operacao) {
			case 'i': inserir(L); 
				break;
			case 'r': remover(L);
				break;
			case 'p': imprimir(L);
				break;
			case 't': operacao = 0;
				break;
		}
	}	
}
	
int main(void) { 

	lista *L = (lista *)malloc(sizeof(lista));
	criar_lista(L);
	comando(L);
	return 0;
}
