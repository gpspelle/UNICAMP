#include <stdio.h>
#include <string.h>
int i=0;
int d=0;

int main(void) { 

	char vetor [1000];
	scanf("%s", vetor);
	while(i<200) {
		if(vetor[i]=='(' && vetor[i+1] != ')') { 
			printf("i: %d\n", i);
			d++;
		}
		i++;
	}
	printf("Qntd: %d\n", d);
	return 0; 
}
