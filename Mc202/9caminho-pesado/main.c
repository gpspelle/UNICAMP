#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

typedef struct no{

	struct no *left;
	struct no *right;
	int data;
	float pesoL;
	float pesoR;
}Tno;

int i=0;
double maximo=0;

float caminho(Tno *raiz) {

	if(raiz==NULL)
		return 0;
	
	float a, b;

	a = raiz->pesoL + caminho(raiz->left);
	b = raiz->pesoR + caminho(raiz->right);
	
	if(a>=b)
		return a;
	
	else 
		return b;
				
}
void imprimir_em(Tno *raiz) {

    if (raiz == NULL)
		return;
	
    imprimir_em(raiz->left);
    printf("%d ", raiz->data);
    imprimir_em(raiz->right);
}
void destruir(Tno *raiz) {
    if (raiz == NULL) {
    	free(raiz);
		return;
	}
    destruir(raiz->left);
    destruir(raiz->right);

    free(raiz);
}
char quantidade(char *cadeia, int k) { 

	if(cadeia[k]!=':' && cadeia[k]!= '(') 
		return cadeia[k];

	return ':';	
}
char *letra_num(char *cadeia) {

		int k=0;
		char c=0;
		char *aux = calloc(sizeof(char), 50);
		while(!isdigit(cadeia[i+1]))
				i++;
		while(c!=':') {
			i++;
			c = quantidade(cadeia, i);
			if(c!=':')
				aux[k++] = c;
		}
		return aux;
}
Tno *leitura(char *cadeia) { 

	if(cadeia[i+1] == ')') {
		i++;
		return NULL;
	}
	char *aux; 
	Tno *no = calloc(sizeof(Tno), 1);
	no->pesoR = 0;
	no->pesoL = 0;
	
	if(cadeia[i] == '(') {
		i++;
		no->left = leitura(cadeia);
		if(no->left==NULL){
			aux = letra_num(cadeia);
			no->data = atoi(aux); 
			free(aux);
		}
		else{
			aux = letra_num(cadeia);
			no->pesoL = atof(aux);
			free(aux);
			aux=letra_num(cadeia);
			no->data = atoi(aux);
			free(aux);
			if(cadeia[i]==':') {
				aux = letra_num(cadeia);
				no->pesoR = atof(aux);
				free(aux);
			}
		}	
	}
	if(cadeia[i]== '(') {
		no->right = leitura(cadeia);
		i++;
	}
	else if(cadeia[i]==':') { 
		aux = letra_num(cadeia);
		no->pesoR = atof(aux);
		free(aux);
		no->right = leitura(cadeia); 
	}
	return no;
}
int main(void) { 
	char cadeia[200000];
	Tno *raiz = NULL;
	while(scanf("%s", cadeia)!=EOF) {
		raiz = leitura(cadeia);
		printf("%.3f\n", caminho(raiz));
		//imprimir_em(raiz);
		destruir(raiz);
		raiz = NULL;
		i=0; maximo=0;
	}
	return 0;
}
