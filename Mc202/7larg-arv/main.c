#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int posicaoPre = 0;
int fimFila = 0, inicFila = 0;


typedef char string[1000];
typedef struct no {

    char chave;
    struct no *esq;
    struct no *dir;
} TipoNo;

TipoNo *reconstruir(string pre_or, string em_or, int comeco, int fim)
{

    int i;

    if (comeco > fim)
		return NULL;

    TipoNo *no = (TipoNo *) malloc(sizeof(TipoNo));

    no->chave = pre_or[posicaoPre];
    no->dir = NULL;
    no->esq = NULL;

    posicaoPre += 1;

    if (comeco == fim)
		return no;
		
    for (i = comeco; i <= fim; i++)
		if (em_or[i] == no->chave)
	    	break;

    no->esq = reconstruir(pre_or, em_or, comeco, i - 1);
    no->dir = reconstruir(pre_or, em_or, i + 1, fim);

    return no;
}

void imprimir_pos(TipoNo * raiz)
{

    if (raiz == NULL)
		return;

    imprimir_pos(raiz->esq);
    imprimir_pos(raiz->dir);

    printf("%c", raiz->chave);
}
void set(TipoNo *fila, TipoNo *raiz) { 

	if(fila[inicFila]==NULL) 
		return;
	
	if(raiz->esq!=NULL) 
		fila[++fimFila]=raiz->esq;
	if(raiz->dir!=NULL)
		fila[++fimFila]=raiz->dir; 
	
	set(fila, fila[++inicFila]);
	
}
void imprimir_largura(TipoNo *raiz, int fim) { 

	int i;
	for(i=0; i<fim; i++)	
		printf("%c", raiz[i]->chave);
	
	
}
void destruir(TipoNo * raiz)
{

    if (raiz == NULL) {
    	free(raiz);
		return;
	}

    destruir(raiz->esq);
    destruir(raiz->dir);

    free(raiz);
}

int main(int argc, char *argv[])
{

    while (1) {
	string pre_or;
	string em_or;
	int comeco = 0, fim = 0;
	if (scanf("%s %s", pre_or, em_or) != 2)
	    break;
	fim = strlen(em_or) / sizeof(char) - 1;
	
	TipoNo *fila = (TipoNo *)malloc(sizeof(TipoNo)*fim);
	
	TipoNo *raiz = reconstruir(pre_or, em_or, comeco, fim);
	
	fila[fimFila] = raiz;
	set(fila, raiz);
	
	imprimir_pos(raiz);
	printf(" ");
	imprimir_largura(fila, fim);
	printf("\n");
	destruir(raiz);
	posicaoPre = 0;
    }

    return 0;
}
