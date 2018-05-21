#include <stdio.h>
#include <stdlib.h>

int main(void) { 
	
	char a, b, c;
	a = getchar();
	b = getchar();
	ungetc(b, stdin);
	c = getchar();	
	printf("%c", a);
	printf("%c", b);
	printf("%c\n", c);
	
	return 0;
	
}
