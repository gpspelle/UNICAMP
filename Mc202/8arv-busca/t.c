#include <stdio.h>
#include <stdlib.h>
int main(void) { 

	int *a, *b;
	a = malloc(sizeof(int)*3);
	b = NULL;
	if(a)
		printf("a\n");
	if(b)
		printf("b\n");
	

	return 0;
} 
