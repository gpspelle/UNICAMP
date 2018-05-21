#include <stdio.h>
#include <stdlib.h>

typedef struct no { 
	double val;
	struct no* prox;
	struct no* tras;
}no;
typedef struct {
	int num;
	no *inicio;
	no *fim;
}lista;
void imprimir(lista *L) { 
	
	no *auxiliar; 
	auxiliar = L->inicio;
	while(auxiliar->prox->prox!=NULL) {
		auxiliar = auxiliar->prox;
		printf("%.4lf ", auxiliar->val);
	}
	printf("\n");
}
void criar_lista(lista *L) { 
	
	no *cabeca = (no *)malloc(sizeof(no));
	no *cauda = (no *)malloc(sizeof(no));
	
	L->inicio = cabeca;
	L->fim = cauda; 
	cabeca->prox = cauda;
	cabeca->tras = NULL;
	cauda->tras = cabeca;
	cauda->prox = NULL;
}
void inserir(lista *L) {

	int posicao, i;
	double valor;
	L->num++;
	no *auxiliar, *guarda;
	scanf(" %d %lf", &posicao, &valor);
	auxiliar=L->inicio;
	
	for(i=0; i<posicao && auxiliar->prox->prox!=NULL; i++, auxiliar = auxiliar->prox);
	
	no *novo = (no *)malloc(sizeof(no));
	guarda = auxiliar->prox;
	novo->tras = auxiliar;
	auxiliar->prox = novo;
	novo->prox = guarda;
	guarda->tras = novo;
	
	novo->val = valor;
}
void reverter(lista *L) {

	int i;
	int inicio, termino;
	no *p = L->inicio, *pprev;
	no *q = L->fim, *qnext, *guarda;
	scanf(" %d %d", &inicio, &termino);
	for(i=0; i<=inicio; i++) { 
		pprev = p;
		p = p->prox;
	}
	for(i=L->num; i>termino; i--)
		q = q->tras;
	guarda = q->prox;
	while(pprev!=p) {
		pprev->prox = q;
		qnext = q->tras;
		q->tras = pprev;
		pprev = pprev->prox;
		if(pprev!=p)
			q = qnext;
	}
	qnext = guarda;
	while(qnext!=NULL && qnext!=pprev) {
		q->prox = qnext; 
		qnext->tras = q;
		qnext = qnext->prox;
		q = q->prox;
	}
} 
void comando(lista *L) {

	char operacao = 1; 
	while(operacao) { scanf(" %c", &operacao);
		
		switch(operacao) {
			case 'i': inserir(L); 
				break;
			case 'v': reverter(L);
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
