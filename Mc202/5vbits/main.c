#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <limits.h>

typedef char com[20];

unsigned char* create(int n) {
		 	
	n = (n/CHAR_BIT) +1;
	
	unsigned char *vetorB = (unsigned char *)calloc(n, sizeof(unsigned char));
	
	return vetorB; 
}
void add(unsigned char* vetorB, int n) {

	int i;
	scanf(" %d", &i);
	if(i<=n)
		vetorB[i/8] = vetorB[i/8] | (1<<(i%8));
}
void remover(unsigned char* vetorB, int n) {
	
	int i;
	scanf(" %d", &i);
	if(i<=n) {
		int mask = ~(1<<(i%8));
		vetorB[i/8] = vetorB[i/8] & mask;
	}
} 
int in(unsigned char* vetorB, int i) {
	
	if( vetorB[i/8] & (1<<(i%8) )) 
		return 1;	
		
	return 0;
}

void rank(unsigned char *vetorB, int n) { 

	int i, j, imprime=0;
	scanf(" %d", &i);
	for(j=1; j<=i; j++) 
		if(in(vetorB, j))
			imprime++;
	printf("rank(%d) = %d\n", i, imprime);
}
void rangecount(unsigned char *vetorB, int n) { 

	int i, j, k, imprime=0;
	scanf(" %d %d", &j, &k);
	for(i=j; i<=k; i++)
		if(in(vetorB, i))
			imprime++;
	
	printf("rangecount(%d,%d) = %d\n", j, k, imprime);	
}
void seleccion(unsigned char *vetorB, int n) { 

	int i, j, k=0;
	scanf(" %d", &i);
	
	for (j=1; j<=n; j++) {
		  k+=(in(vetorB, j) != 0);
		if (k==i) {
			printf("select(%d) = %d\n", i, j);
			break;
		}
	}
	if (k==0)
		printf("select(%d) = 0\n", i);
}
void print(unsigned char *vetorB, int n) { 

	int i;
	printf("S = {"); 
	for(i=1; i<=n; i++) {  
		if(in(vetorB, i)) {
			printf("%d", i);
			i++;
			break;
		}
	}
	for(; i<=n; i++)
		if(in(vetorB, i))
			printf(",%d", i);
	
	printf("}\n");
}
void opcao() { 
	
	int n;
	unsigned char* vetorB = NULL;
	com comando;
	while(1) { 
		scanf(" %s", comando);
		
		if(!strcmp("create", comando)) {
			scanf(" %d", &n);
			if(vetorB!=NULL)
				free(vetorB); 
			vetorB = create(n);
		}
		else if(!strcmp("add", comando)) {
			add(vetorB, n);
		}
		else if(!strcmp("in", comando)) {
			int i;
			scanf(" %d", &i);
			if(i<=n && i>=0 ) { 
				if(in(vetorB, i))
					printf("belongs(%d) = true\n", i);
				else
					printf("belongs(%d) = false\n", i);
			}
		}
		else if(!strcmp("remove", comando)) {
			remover(vetorB, n);
		}	
		else if(!strcmp("rank", comando)) {
			rank(vetorB, n);
		}
		else if(!strcmp("select", comando)) {
			seleccion(vetorB, n);
		}
		else if(!strcmp("rangecount", comando)) {
			rangecount(vetorB, n);
		}
		else if(!strcmp("print", comando)) {
			print(vetorB, n);
		}
		else if(!strcmp(comando, "exit")) {
			break;
		}
	}
	free(vetorB);
}
int main(void) {

	opcao();
	return 0;
}

