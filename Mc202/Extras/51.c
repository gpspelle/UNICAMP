#include <stdio.h>
#include <stdlib.h>

typedef struct no { 
	double valor;
	struct no *prox;
}no;
typedef struct lista { 
	no *inicio;
}lista;
 
void imprimir(lista *L);

void criar_lista(lista *L) { 
	
	no *cabeca = (no *)malloc(sizeof(no));
	no *cauda = (no *)malloc(sizeof(no));
	
	L->inicio = cabeca; 
	cabeca->prox = cauda;
	cauda->prox = NULL;
}
void reverter(lista *L) { 

	int inicio, termino, i;
	scanf(" %d %d", &inicio, &termino);
	no *p = L->inicio, *pprev = L->inicio, *guardap;
	no *q = L->inicio, *guarda;
	for(i=0; i<=inicio; i++) { 
		pprev = p;
		p = p->prox;
	}
	for(i=0; i<=termino; i++) 
		q = q->prox;
	
	if(inicio!=termino) { 	
		pprev->prox = q;
	
		guardap = p;
		pprev = p;
		p = p->prox;
	
		do { 	
			guarda = p->prox;
			p->prox = pprev;
			pprev = p;
			p = guarda;
		
		}while(pprev!=q);
	
		guardap->prox = p;
	}
}	
void transpor(lista *L) {
	
	int i;
	int inicio, termino, lugar;
	no *pprev = L->inicio, *p = L->inicio;
	no *q = L->inicio, *posicao = L->inicio;
	no *guarda, *guarda2; 
	
	scanf(" %d %d %d", &inicio, &termino, &lugar);
	
	if(termino<lugar) { 
		for(i=0; i<=inicio; i++) {
			pprev = p;
			p = p->prox;
		}
		for(i=0; i<=termino; i++)
			q = q->prox;
		
		for(i=0; i<lugar; i++)
			posicao = posicao->prox;

		pprev->prox = q->prox;
		guarda = posicao->prox;
		posicao->prox = p;
		q->prox = guarda;
	}
	else if (termino>lugar) { 
	
		for(i=0; i<lugar; i++)
			posicao = posicao->prox;
		
		for(i=0; i<=inicio; i++) { 
			pprev = p;
			p = p->prox;
		}
		for(i=0; i<=termino; i++)
			q = q->prox; 
		
		
		guarda = posicao->prox;
		posicao->prox = p;
		guarda2 = q->prox;
		q->prox = guarda;
		pprev->prox = guarda2;
	}
}
void inserir(lista *L) {

	int posicao, i;
	double val;
	no *auxiliar, *guarda;
	scanf(" %d %lf", &posicao, &val);
	auxiliar=L->inicio;
	
	for(i=0; i<posicao && auxiliar->prox->prox!=NULL; i++)
		auxiliar = auxiliar->prox;
	
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
			case 'v': reverter(L);
				break;
			case 'x': transpor(L);
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
