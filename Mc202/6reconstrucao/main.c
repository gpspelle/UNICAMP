#include <stdio.h>
#include <stdlib.h>
#include <string.h>

static int posicaoPre = 0;
static int count = 0;
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

    if (comeco == fim) {
	return no;
    }
    for (i = comeco; i <= fim; i++)
	if (em_or[i] == no->chave)
	    break;

    no->esq = reconstruir(pre_or, em_or, comeco, i - 1);
    no->dir = reconstruir(pre_or, em_or, i + 1, fim);

    return no;
}

void imprimir(TipoNo * raiz)
{

    if (raiz == NULL)
	return;

    imprimir(raiz->esq);
    imprimir(raiz->dir);

    printf("%c", raiz->chave);
}

void destruir(TipoNo * raiz)
{

    if (raiz == NULL)
	return;

    destruir(raiz->esq);
    destruir(raiz->dir);

    free(raiz);
}
int quantidade(TipoNo *no) {
	if (no)	
		return quantidade(no->esq) + quantidade(no->dir) + 1;
		
	return 0;
}
int main(int argc, char *argv[])
{

    while (1) {
	int qt;
	string pre_or;
	string em_or;
	int comeco = 0, fim = 0;

	if (scanf("%s %s", pre_or, em_or) != 2)
	    break;
	fim = strlen(em_or) / sizeof(char) - 1;
	TipoNo *raiz = reconstruir(pre_or, em_or, comeco, fim);
	qt = quantidade(raiz);
	printf("Quantidade: %d\n", qt);
	imprimir(raiz);
	printf("\n");
	destruir(raiz);
	count = 0;
	posicaoPre = 0;
    }

    return 0;
}
