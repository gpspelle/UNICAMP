//Matheus Rotta Alves (184403)

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#define MAX 100000

typedef struct node {
	int key;
	struct node *right;
	struct node *left;
	double right_edge;
	double left_edge;
} node;

void destruir(node * root) {
    if (root != NULL) {
	destruir(root->left);
	destruir(root->right);
	free(root);
    }
}

node * rebuild (char arvore[], int i) {
	if (arvore[i + 1] == ')') return NULL; //se o segundo elemento da string é um ')', a árvore é nula. Portanto, nao faca nada e retorne null para a chamada
	int count = 0, k;
	for (k = i + 1;; k++) { //este ciclo caminha na string ate encontrar o valor ideal para comecar a trabalhar
		if (arvore[k] == '(') count++;
		else if (arvore[k] == ')') {
			count--;
			if (count == 0) break;
		}
	}
	/*Observemos que neste ponto, arvore[k] eh o ')' fecha parenteses que vem logo antes da aresta esquerda ou da chave*/
	node *new = malloc(sizeof(node));
	char aux[20];
	int pos;
	int j = 0;
	if (arvore[k-1] == '(') { //analisando a representacao parentizada, se o parenteses que vem antes de arvore[k] for '(', a subarvore esquerda eh nula e o primeiro numero sera a chave.
		new->left_edge = 0;
		new->left = NULL;
		pos = k + 1;
		while (arvore[pos] != ':' && arvore[pos] != '(') { //esse ciclo recebe a chave como vetor de char
			aux[j] = arvore[pos];
			j++;
			pos++;
		}
		new->key = atoi(aux);
		strcpy(aux, "hahahahahahahahahah"); //aqui eu reseto o valor de aux para uso futuro, senao valores antigos em aux modificam o output de atoi/atof.
		if (arvore[pos] == '(') { //quer dizer que nao ha subarvore direita
			new->right_edge = 0;
			new->right = NULL;
		}
		else { //quer dizer que ha subarvore direita
			pos++; //pule o : no qual vc havia parado
			j = 0;
			while (arvore[pos] != '(') {
				aux[j] = arvore[pos];
				pos++;
				j++;
			}
			new->right_edge = atof(aux);
			strcpy(aux, "hahahahahahahahahah"); //aqui eu reseto o valor de aux para uso futuro.
			new->right = rebuild (arvore, pos);
		}
		
	}
	else {
		pos = k + 1;
		while (arvore[pos] != ':') { //esse ciclo recebe a aresta esquerda como vetor de char
			aux[j] = arvore[pos];
			j++;
			pos++;
		}
		new->left_edge = atof(aux);
		strcpy(aux, "hahahahahahahahahah"); //aqui eu reseto o valor de aux para uso futuro.
		new->left = rebuild(arvore, i + 1); //pegue o parenteses mais interno agora
		pos++;
		j = 0;
		while (arvore[pos] != ':' && arvore[pos] != '(') { //esse ciclo recebe a chave como vetor de char
			aux[j] = arvore[pos];
			j++;
			pos++;
		}
		new->key = atoi(aux);
		strcpy(aux, "hahahahahahahahahah"); //aqui eu reseto o valor de aux para uso futuro.
		if (arvore[pos] == '(') { //quer dizer que nao ha subarvore direita
			new->right_edge = 0;
			new->right = NULL;
		}
		else { //quer dizer que ha subarvore direita
			pos++; //pule o : no qual vc havia parado
			j = 0;
			while (arvore[pos] != '(') {
				aux[j] = arvore[pos];
				pos++;
				j++;
			}
			new->right_edge = atof(aux);
			strcpy(aux, "hahahahahahahahahah"); //aqui eu reseto o valor de aux para uso futuro.
			new->right = rebuild(arvore, pos); //pegue o parenteses que vem depois do ultimo valor analisado
		}
	}
	return new;
} 

void em_ordem (node *root) {
	if (root) {
		em_ordem(root->left);
		printf("%d\n", root->key);
		em_ordem(root->right);
	}
}

double max_weight (node *root) {
	if (!root) return 0;
	if (root->left_edge + max_weight(root->left) >= root->right_edge + max_weight(root->right)) return root->left_edge + max_weight(root->left);
	else return root->right_edge + max_weight(root->right);
}

int main(void) {
	node *root = NULL;
	char arvore[MAX];
	while (scanf("%s", arvore) != EOF) {
		root = rebuild(arvore, 0); //agora root aponta para nossa arvore ja reconstruida
		printf("%.3lf\n", max_weight(root));
		destruir(root);
		root = NULL;
    }
	return 0;
}
