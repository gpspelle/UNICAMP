#include <stdio.h>
#include <stdlib.h> 

int main(void) { 

	FILE* ler, *escrever;

	int s;
	double valor=0, aux=0;
	ler = fopen("quick7.txt", "r");
	escrever = fopen("quick.txt", "a");
	for(s=0; s<10; s++) {
		fscanf(ler, " %lf\n", &aux);
		valor+=aux;
	}
	fprintf(escrever,"%lf\n", valor/10);

	fclose(ler);
	fclose(escrever);
	
	return 0;
}
