#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int fimFila = 0, inicFila = 0;

typedef struct no {
	int data;
    struct no *esq;
    struct no *dir;
    struct no *pai;
} TipoNo;

TipoNo* buscar(TipoNo* raiz, int valor) {
	
	if(raiz==NULL)
		return raiz;
	
	if(valor==raiz->data)
		return raiz;
		
	else if(valor>raiz->data)
		raiz = buscar(raiz->dir, valor);
		
	else
		raiz = buscar(raiz->esq, valor);
	
	return raiz;
}
TipoNo* cria_no(TipoNo *raiz, int valor) {

		TipoNo *no = malloc(sizeof(TipoNo));
		no->data = valor;
		no->esq = NULL;
		no->dir = NULL;
		no->pai = raiz;		
		return no;
}
int inserir(TipoNo* raiz, int valor) {
	
	if(raiz==NULL)
		return 1;
	while(1) {
		if(valor>raiz->data) { 
			if(raiz->dir==NULL) { 
				raiz->dir=cria_no(raiz, valor);
				break;
				}
			else
				raiz = raiz->dir;
		}
		else if(valor<raiz->data) { 
			if(raiz->esq==NULL) {
				raiz->esq=cria_no(raiz, valor);
				break;
				}
			else
				raiz = raiz->esq;
	
		}
	}
	return 0;
}
TipoNo* minimo(TipoNo* raiz) { 

	while(raiz->esq!=NULL)
		raiz=raiz->esq;

	return raiz;
}
TipoNo* maximo(TipoNo* raiz) { 

	while(raiz->dir!=NULL)
		raiz=raiz->dir;
	
	return raiz;
}
void imprimir_pos(TipoNo * raiz) {

    if (raiz == NULL)
		return;
		
    imprimir_pos(raiz->esq);
    imprimir_pos(raiz->dir);
    printf("%d ", raiz->data);
    
}
void imprimir_pre(TipoNo * raiz) {

    if (raiz==NULL)
		return;
		
    printf("%d ", raiz->data);
    imprimir_pre(raiz->esq);
    imprimir_pre(raiz->dir);
    
}
void imprimir_em(TipoNo * raiz) {

    if (raiz == NULL)
		return;
	
    imprimir_em(raiz->esq);
    printf("%d ", raiz->data);
    imprimir_em(raiz->dir);
	
}
int excluir(TipoNo* raiz, int valor) {
	
	TipoNo *guarda;
	raiz = buscar(raiz, valor);
	if(raiz->pai==NULL) {
		if(raiz->esq==NULL && raiz->dir==NULL)
			free(raiz);
			
		else if (raiz->dir==NULL) {
			guarda = raiz->esq;
			if(guarda->esq!=NULL)
				guarda->esq->pai = raiz;
			if(guarda->dir!=NULL)
				guarda->dir->pai = raiz;
			raiz->data = guarda->data; 
			raiz->dir = guarda->dir; 
			raiz->esq = guarda->esq; 
			free(guarda);
		}
		else {
			if(raiz->dir!=NULL) {
				guarda = minimo(raiz->dir);
			}
			raiz->data = guarda->data; 		
			if (raiz->dir==guarda) {
				raiz->dir = guarda->dir;
				if(guarda->dir!=NULL)
					guarda->dir->pai = raiz; 
				free(guarda);
			}
			else if(guarda->dir==NULL) {
				if(guarda->pai->dir==guarda)
					guarda->pai->dir=NULL;
				else
					guarda->pai->esq=NULL;
				free(guarda);		
			}
			else {
				guarda->dir->pai = guarda->pai;
				guarda->pai->esq = guarda->dir;
				free(guarda);
			}
		}
		return 1;
	}
	if(raiz!=NULL) {
		if(raiz->dir==NULL || raiz->esq==NULL) {
			if(raiz->dir==NULL) { 
				if(raiz->pai->dir==raiz) {
					raiz->pai->dir = raiz->esq;
					if(raiz->esq!=NULL)
						raiz->esq->pai = raiz->pai;
					free(raiz);
				}
				else { 
					raiz->pai->esq = raiz->esq;
					if(raiz->esq!=NULL)
						raiz->esq->pai = raiz->pai;
					free(raiz);
				}
			}
			else { 
				if(raiz->pai->dir==raiz) { 
					raiz->pai->dir = raiz->dir;
					if(raiz->dir!=NULL)
						raiz->dir->pai = raiz->pai;
					free(raiz);
				}
				else { 
					raiz->pai->esq = raiz->dir;
					if(raiz->dir!=NULL)
						raiz->dir->pai = raiz->pai;
					free(raiz);
				}
			}
			return 1;
		}
		else {
			guarda=minimo(raiz->dir);
			raiz->data = guarda->data;
			if(guarda->dir!=NULL) { 
				raiz->dir=guarda->dir;
				guarda->dir->pai = raiz;
				free(guarda);	
			}
			else {
				if(guarda->pai->dir==guarda)
					guarda->pai->dir=NULL;
				else
					guarda->pai->esq=NULL;
				free(guarda);
			}
			return 1;	
		}
	}
	return 0;
}
void set(TipoNo **fila, TipoNo *raiz, int quantidade) { 

	if(inicFila==quantidade)
		return;
			
	if(raiz->esq!=NULL) 
		fila[++fimFila]=raiz->esq;
	if(raiz->dir!=NULL)
		fila[++fimFila]=raiz->dir; 
	
	set(fila, fila[++inicFila], quantidade);	
}
int imprimir_largura(TipoNo **fila, int quantidade) { 
		
	int i;
	for(i=0; i<quantidade; i++)	
		printf("%d ", fila[i]->data);

	printf("\n");
	return 1;
}
void destruir(TipoNo *raiz) {
    if (raiz == NULL) {
    	free(raiz);
		return;
	}
    destruir(raiz->esq);
    destruir(raiz->dir);

    free(raiz);
}
void menu() {
	
	int quantidade = 0, valor;
	TipoNo *raiz = NULL;
	char opcao[15];
	while(1) {
		scanf("%s", opcao);
		if(!strcmp(opcao, "inserir")) {
			scanf(" %d", &valor);
			if(!buscar(raiz, valor)) {
				quantidade++;
				if(inserir(raiz, valor))
					raiz = cria_no(NULL, valor);
			}
		}
		else if(!strcmp(opcao, "excluir")) {
			scanf(" %d", &valor);
			if(buscar(raiz, valor)) 
				if(excluir(raiz, valor))
					quantidade--;
			if(quantidade==0)
				raiz=NULL;
		}
		else if(!strcmp(opcao, "buscar")) {
			scanf(" %d", &valor);
			if(buscar(raiz, valor))
				printf("pertence\n");
			else
				printf("nao pertence\n");
		}
		else if(!strcmp(opcao, "minimo")) {
			if(quantidade==0)
				printf("vazia\n");
			else
				printf("%d\n", minimo(raiz)->data);
		}
		else if(!strcmp(opcao, "maximo")) {
			//printf("Quantidade: %d\n", quantidade);
			if(quantidade==0)
				printf("vazia\n");
			else
				printf("%d\n", maximo(raiz)->data);
		}
		else if(!strcmp(opcao, "pos-ordem")) {
			if(quantidade==0)
				printf("vazia\n");
			else {
				imprimir_pos(raiz); 
				printf("\n");
			}
			
		}
		else if(!strcmp(opcao, "em-ordem")) {
			if(quantidade==0)
				printf("vazia\n");
			else {
				imprimir_em(raiz);
				printf("\n");
			}
		}
		else if(!strcmp(opcao, "pre-ordem")) {
			if(quantidade==0)
				printf("vazia\n");
			else {
				imprimir_pre(raiz);
				printf("\n");			
			}	
		}
		else if(!strcmp(opcao, "largura")) {
			if(quantidade==0)
				printf("vazia\n");
			
			else { 
				TipoNo **fila = (TipoNo **)malloc(sizeof(TipoNo*)*quantidade);
				fila[0] = raiz;
				set(fila, raiz, quantidade);
				imprimir_largura(fila, quantidade);
				free(fila);
				fimFila = 0, inicFila = 0;		
			}
		}
		else if(!strcmp(opcao, "terminar")) {
			if(quantidade)
				destruir(raiz);
			break;
		}
	}		 
}
int main(int argc, char *argv[]) {

    menu();

    return 0;
}
