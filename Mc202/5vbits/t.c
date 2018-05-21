#include <stdio.h>

int main(void) { 

	int i, j=0;
	
	while(j<=100) { 
		printf("create %d\n", j);
		for(i=100; i<100; i++)
		printf("add %d\n", i);
		for(i=100; i<200; i++) {
		printf("select %d\n", i);
		printf("rangecount %d %d\n", i, i+1);
		printf("print\n");
		printf("in %d\n", i);
		printf("rank %d\n", i);
		printf("remove %d\n", i);
		printf("in %d\n", i);
		printf("select %d\n", i);
		printf("rangecount %d %d\n", i, i+1);
		}

		j++;
	}
	printf("exit\n");
	
	return 0;
}
