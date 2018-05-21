#include <stdio.h>
#include <stdlib.h>

int posicao = 0;
int novo = 0;

typedef struct destino {

	int j;
	struct destino *prox;
	
}destino;

typedef struct V {

	int num;
	int cor;
	int dist;
	struct destino *prox;
	
}vertice;

void bfs(vertice *lista_adj, int n_vertices, int inicio) {

	int i, lugar;
	vertice **pilha = malloc(sizeof(vertice*)*n_vertices);
	destino *viz;
	vertice *aux;
	for(i=1; i<=n_vertices; i++)
		lista_adj[i].cor = 0;
	
	for(i=0; i<n_vertices; i++)
		pilha[i] = malloc(sizeof(vertice)*1);

	pilha[0][0] = lista_adj[inicio];
	lista_adj[inicio].dist = 0;	
	lista_adj[inicio].cor = 1;
	
	while(pilha[posicao]!=NULL && posicao < n_vertices) {
		aux = pilha[posicao];
		viz = pilha[posicao++][0].prox;
		while(viz!=NULL) {
			lugar = viz->j;
			if(lista_adj[lugar].cor==0) {
				lista_adj[lugar].dist = aux->dist +1;			
				pilha[novo++][0] = lista_adj[lugar];
				lista_adj[lugar].cor = 1;			
			}
			viz = viz->prox;
		}
	}
}

void insere_lista(vertice *lista_adj, int i, int j, int n_vertices) {

	destino *novo = malloc(sizeof(destino));
	destino *novo2 = malloc(sizeof(destino));
	
	novo->prox = lista_adj[i].prox;
	novo->j = j;
	lista_adj[i].prox = novo;
	
	novo2->prox = lista_adj[j].prox;
	lista_adj[j].prox = novo2;
	novo2->j = i;
}
void ordena(vertice *lista_adj, int n_vertices) {

	int i, j;
	vertice auxiliar;
	for(i=2; i<=n_vertices; i++) {
		j=i;
		while(j>0) {
			if(lista_adj[j].dist < lista_adj[j-1].dist) {
				auxiliar = lista_adj[j];
				lista_adj[j] = lista_adj[j-1];
				lista_adj[j-1] = auxiliar;
			}
			j--;
		}
	}
}
int main(void) {

	int n_vertices, i, j=1, inicio;
	scanf("%d\n", &n_vertices);
	vertice *lista_adj = malloc(sizeof(vertice)*(n_vertices+1));
	destino *aux;
	destino *libera;
		
	for(i=0; i<=n_vertices; i++) {
		lista_adj[i].dist = -1;
		lista_adj[i].num = i;	
		lista_adj[i].prox = NULL;
	}
		
	while(1) {
		scanf("%d,%d", &i, &j);	
		if(!i)
			break;
			
		insere_lista(lista_adj, i, j, n_vertices);					
	}
	
	scanf(" %d", &inicio);
	
	bfs(lista_adj, n_vertices, inicio);
	
	ordena(lista_adj, n_vertices);

	printf("Origem da busca: %d\n", inicio);
	printf("Vertices alcancados e distancias:\n");	
	for(i=1; i<=n_vertices; i++) {
		if(lista_adj[i].dist<0)
			continue;
		printf("%d %d\n", lista_adj[i].num, lista_adj[i].dist);
	}
	
	for(i=0; i<=n_vertices; i++) {
		aux = lista_adj[i].prox;
		while(aux!=NULL) { 
			libera = aux;
			aux = aux->prox;
			free(libera);
		}
	}
	free(lista_adj);
	
	
	return 0;	
}
