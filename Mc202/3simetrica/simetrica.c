#include <stdio.h>
#include <stdlib.h>

typedef struct no { 
	double val;
	struct no* B;
	struct no* A;
}no;
typedef struct {
	int num;
	no *inicio;
	no *fim;
}lista;
void imprimir(lista *L) { 
	
	no *p, *pprev; 
	p = L->inicio->B, pprev = L->inicio;
	
	while(p->B!=NULL) {
		if(p->B==pprev) {
			printf("%.4lf ", p->val);
			pprev=p;
			p=p->A;
		}
		else {
			printf("%.4lf ", p->val);
			pprev=p;
			p=p->B;
		}	
	}
	printf("\n");
}
void criar_lista(lista *L) { 
	
	no *cabeca = (no *)malloc(sizeof(no));
	no *cauda = (no *)malloc(sizeof(no));
	
	L->num =0;
	
	L->inicio = cabeca;
	L->fim = cauda; 
	cabeca->B = cauda;
	cabeca->A = NULL;
	cauda->A = cabeca;
	cauda->B = NULL;
}	
void inserir(lista *L) {

	int posicao, i=0;
	double valor;
	no *p = L->inicio, *pprev = L->inicio;
	scanf(" %d %lf", &posicao, &valor);
	
	while(i<=posicao && p->B!=NULL) {
		if(p->B==pprev) {
			pprev=p;
			p=p->A;	
		}
		else {
			pprev=p;
			p=p->B;
		}
		i++;
	}
	no *novo = (no *)malloc(sizeof(no));
	if(L->num!=0) {
		novo->A=pprev;
		novo->B=p;
		if(pprev->A==p)
			pprev->A=novo;
		else
			pprev->B=novo;
		if(p->B==pprev)
			p->B = novo;
		else
			p->A = novo;
		
	}
	else if(L->num==0) {
		pprev->B=novo;
		p->A=novo;	
		novo->B=p;
		novo->A=pprev;
	}
	novo->val = valor;
	L->num++;
}
void reverter(lista *L) {

	int i=0, inicio, termino;
	scanf(" %d %d", &inicio, &termino);
	no *p = L->inicio, *q = L->fim, *pprev = L->inicio, *qprox = L->fim;
	for(i=0; i<=inicio; i++) {
		if(p->B!=pprev && p->B!=NULL) {
			pprev = p;
			p = p->B;
		}
		else if (p->A!=NULL) { 
			pprev = p;
			p = p->A;
		}
	}
	for(i=L->num-1; i>=termino; i--) { 
		if(q->B!=qprox && q->B!=NULL) {
			qprox = q;
			q = q->B;
		}
		else if (q->A!=NULL) { 
			qprox = q;
			q = q->A;
		}
	}
	if(inicio!=termino) {
		if(p->A==pprev)  
			p->A=qprox;
		else
			p->B=qprox;
	
		if(q->B==qprox)
			q->B=pprev;
		else 
			q->A = pprev;
	
		if(pprev->A==p)
			pprev->A=q;
		else
			pprev->B=q;	
	
		if(qprox->A==q)
			qprox->A=p;
		else 
			qprox->B=p;
	}
	else {
		if(p->A==pprev)
			p->A=qprox;
		else
			p->A=pprev;
		if(p->B==qprox)
			p->B=pprev;
		else
			p->B=qprox;
		
		
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
