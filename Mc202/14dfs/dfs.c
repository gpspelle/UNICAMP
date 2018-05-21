#include <stdio.h>
#include <stdlib.h>

int time;
int ciclo = 0;

typedef struct destino {

	int j;
	struct destino *prox;
	
}destino;

typedef struct V {

	int num;
	int cor;
	int tf, ti;
	struct destino *prox;
	
}vertice;

void dfs_visit(vertice *lista_adj, int i) {

	destino *atual = lista_adj[i].prox;
	int posicao = 0;
	time++;
	lista_adj[i].ti = time;
	lista_adj[i].cor = 1;
	
	while(atual!=NULL) {
		posicao = atual->j;
		if(lista_adj[posicao].cor==0) {
			dfs_visit(lista_adj, posicao);
		}
		else if(lista_adj[posicao].cor==1)
			ciclo = 1;
		
		atual = atual->prox;
	}
	
	lista_adj[i].cor = 2;
	time++;
	lista_adj[i].tf = time;
}
void dfs(vertice *lista_adj, int n_vertices) {

	int i;
	for(i=1; i<=n_vertices; i++)
		lista_adj[i].cor = 0;
		
	time = 0;
	for(i=1; i<=n_vertices; i++)
		if(lista_adj[i].cor==0)
			dfs_visit(lista_adj, i); 
}

void insere_lista(vertice *lista_adj, int i, int j, int n_vertices) {

	destino *novo = malloc(sizeof(destino));
	
	novo->prox = lista_adj[i].prox;
	novo->j = j;
	lista_adj[i].prox = novo;
	
}
void ordena(vertice *lista_adj, int n_vertices) {

	int i, j;
	vertice auxiliar;
	for(i=2; i<=n_vertices; i++) {
		j=i;
		while(j>0) {
			if(lista_adj[j].ti < lista_adj[j-1].ti) {
				auxiliar = lista_adj[j];
				lista_adj[j] = lista_adj[j-1];
				lista_adj[j-1] = auxiliar;
			}
			j--;
		}
	}
}
int main(void) {

	int n_vertices, i, j=1;
	scanf("%d\n", &n_vertices);
	vertice *lista_adj = malloc(sizeof(vertice)*(n_vertices+1));
	destino *aux;
	destino *libera;
		
	for(i=1; i<=n_vertices; i++) {
		lista_adj[i].num = i;	
		lista_adj[i].prox = NULL;
	}
		
	while(1) {
		scanf("%d,%d", &i, &j);	
		if(!i)
			break;
			
		insere_lista(lista_adj, i, j, n_vertices);					
	}
	
	dfs(lista_adj, n_vertices);
	
	ordena(lista_adj, n_vertices);
	for(i=1; i<=n_vertices; i++)
		printf("%d [%d,%d]\n", lista_adj[i].num, lista_adj[i].ti, lista_adj[i].tf);

	for(i=0; i<=n_vertices; i++) {
		aux = lista_adj[i].prox;
		while(aux!=NULL) { 
			libera = aux;
			aux = aux->prox;
			free(libera);
		}
	}
	free(lista_adj);
	
	if(ciclo) 
		printf("aciclico: nao\n");
	else
		printf("aciclico: sim\n");	
	
	return 0;	
}
