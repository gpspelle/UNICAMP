#include <stdio.h>

int main() { 

	int i;
	int a, b, c, d, e;
	a = 5, b = 4, c = -10, d = 3, e = 0;
	
	i = (a>b) + (b>c) + (d>e);
	printf("i: %d ", i);
	
	printf("a>b: %d e>d: %c", (a>b), (e>d));

	return 0;
}
