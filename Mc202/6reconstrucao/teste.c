#include <stdio.h>
#define TRUE 1
#define FALSE 0 

int main(int argc, char* argv[]) { 

	char c;
	char buffer[100];
	FILE* arquivo;
	arquivo = fopen("t.in", "r");
	
	while(1) {
		fscanf(arquivo, "%s", buffer);
		
		c = fgetc(arquivo);
		
		fscanf(arquivo, "%s", buffer);
		
		c=fgetc(arquivo);
		c=fgetc(arquivo);
		
		if(c==EOF || c=='\n') { 
			break;
		}
		else  {
			ungetc(c, arquivo);
		}	
	}
	return 0;
	
}
