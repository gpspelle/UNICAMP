#include <stdio.h>
#include <string.h>
int i=0;

int main(void) { 

	char vetor [1000];
	scanf("%s", vetor);
	while(i<strlen(vetor)) {
		if(i<10)
			printf("[%c]", vetor[i]);
		else if(i<100)
			printf("[ %c]", vetor[i]);
		else 
			printf("[  %c]", vetor[i]);		
		i++;
	}
	printf("\n");
	i=0;
	while(i<strlen(vetor))
		printf("[%d]", i++);
	return 0; 
}
