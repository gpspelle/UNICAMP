#include <stdio.h>

int main(int argc, char* argv[]) { 

	char c, d;
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
