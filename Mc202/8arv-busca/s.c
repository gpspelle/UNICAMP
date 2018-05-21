#include <stdio.h>
#include <stdlib.h>
int main(void) { 

	int *no;
	
	no = malloc(sizeof(int));
	printf("%p\n", no);
	free(no);
	no=NULL;
	printf("%p\n", &no);
	
	return 0;
	
}
